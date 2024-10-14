package com.xiaomi.codequality.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.NamedThreadFactory;
import static cn.hutool.core.util.CharsetUtil.GBK;
import com.xiaomi.codequality.log.CodeQualityLogger;
import org.apache.commons.lang3.SystemUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CommandUtil implements Closeable, CodeQualityLogger {
	/**
	 * 该线程用于异步读取流中的数据
	 */
	private final ExecutorService executorService;

	public CommandUtil(int threadCount) {
		executorService = ExecutorBuilder.create()
				.setCorePoolSize(threadCount)
				.setMaxPoolSize(threadCount)
				.setKeepAliveTime(0)
				.setThreadFactory(new NamedThreadFactory("Command-Task-",true))
				.setWorkQueue(new ArrayBlockingQueue<>(5000))
				.build();
	}

	public CommandUtil() {
		executorService = ExecutorBuilder.create()
				.setCorePoolSize(16)
				.setMaxPoolSize(16)
				.setKeepAliveTime(0)
				.setThreadFactory(new NamedThreadFactory("Command-Task-",true))
				.setWorkQueue(new ArrayBlockingQueue<>(5000))
				.build();
	}
	private static final String LINUX_CMD = "/bin/sh";
	private static final String WIN_CMD = "cmd.exe";
	// time out
	private static final long TIME_OUT =  2 * 3600;
	// time out unit
	private static final TimeUnit TIME_OUT_UNIT = TimeUnit.SECONDS;

	public String runCmd(String command) {
		return runCmd(command, TIME_OUT, TIME_OUT_UNIT);
	}
	public String runCmd(String command, long time, TimeUnit unit) {
		return runCmd(command, null, Paths.get("."), time, unit);
	}

	public String runCmd(String command, Map<String,String> env, Path dir) {
		return runCmd(command, env, dir, TIME_OUT, TIME_OUT_UNIT);
	}

	public String runCmd(String command, Map<String,String> env, Path dir, long time, TimeUnit unit) {
		String[] cmds = createCommand(command);
		ProcessBuilder pb = new ProcessBuilder();
		Process ps;
		try {
			pb.command(cmds);
			pb.directory(dir.toFile());
			if(env!=null) pb.environment().putAll(env);
			ps = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("cmd exec error");
		}
		Future<String> result = executorService.submit(new ReaderTask(ps.getInputStream()));
		Future<String> errResult = executorService.submit(new ReaderTask(ps.getErrorStream()));
		String r1 = resultHandle(result, time, unit);
		String r2 = resultHandle(errResult, time, unit);
		if(r2 != null) {
			LOGGER.warn("exc command: {} in {} with env {}: {}",command, dir, env, r2);
		}
		return r1;
	}

	private String[] createCommand(String command) {
		if (SystemUtils.IS_OS_LINUX) {
			// linux -c
			return new String[] { LINUX_CMD, "-c", command };
		} else if (SystemUtils.IS_OS_WINDOWS) {
			// win10 /c
			return new String[] { "powershell", "-Command", command };
		} else {
			String systemInfo = SystemUtils.OS_NAME + " " + SystemUtils.OS_ARCH + " " + SystemUtils.OS_VERSION;
			throw new RuntimeException("OS Not Supported , the current OS is :" + systemInfo);
		}
	}

	private String resultHandle(Future<String> result, long time, TimeUnit unit) {
		String tmp = null;
		try {
			tmp = result.get(time, unit);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	@Override
	public void close() throws IOException {
		executorService.shutdown();
	}

	private static class ReaderTask implements Callable<String> {
		private final InputStream inputStream;
		public ReaderTask(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public String call() {
			return IoUtil.read(inputStream, GBK);
		}
	}
}
