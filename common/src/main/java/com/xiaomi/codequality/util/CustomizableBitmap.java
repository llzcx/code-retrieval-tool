package com.xiaomi.codequality.util;

import lombok.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * bitmap实现
 * cx
 */
public class CustomizableBitmap implements Serializable {

    private int[] bits;

    private int size;
    private final String path;

    private static final int EMPTY = 0x00000000;
    private static final int FULL = 0xffffffff;

    private transient Object LOCK = new Object();

    private CustomizableBitmap(Path path, int size) {
        this.size = size;
        bits = new int[upward(size)];
        this.path = path.toString();
    }

    public int getSize() {
        return size;
    }

    private int upward(int n) {
        return (n >> 5) + ((n & 31) == 0 ? 0 : 1);
    }

    public int downward(int n) {
        return n >> 5;
    }

    public void setBit(int n, int value) {
        synchronized (LOCK) {
            if (n >= size) throw new RuntimeException("beyond the boundary.");
            if (value != 0 && value != 1) throw new RuntimeException("value error.");
            int index = downward(n);
            if (value == 1) {
                bits[index] = bits[index] | (1 << (31 - n % 32));
            } else {
                bits[index] = bits[index] & ~(1 << (31 - n % 32));
            }
        }
    }

    public void clear() {
        synchronized (LOCK) {
            Arrays.fill(bits, EMPTY);
        }
    }

    public int getBit(int n) {
        synchronized (LOCK) {
            if(n >= size) throw new RuntimeException("beyond the boundary.");
            int index = downward(n);
            return (bits[index] >> (31 - n % 32)) & 1;
        }
    }

    public int countSetBits() {
        synchronized (LOCK) {
            int count = 0;
            for (int bit : bits) {
                int c = Integer.bitCount(bit);
                count += c;
            }
            return count;
        }
    }
    public boolean isFULL() {
        synchronized (LOCK) {
            for (int i = 0; i < bits.length - 1; i++)
                if (bits[i] != FULL) return false;
            return bits[bits.length - 1] == FULL << (32 - size % 32);
        }
    }

    // 新增保存对象到文件的方法
    public void saveToFile() throws IOException {
        synchronized (LOCK) {
            File filePath = Paths.get(path).toFile();
            try (FileOutputStream fos = new FileOutputStream(filePath); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 新增从文件加载对象的方法
    public static CustomizableBitmap loadFromFile(String filePath, int size) throws IOException, ClassNotFoundException {
        if(filePath == null) throw new NullPointerException();
        Path p = Paths.get(filePath);
        if(!Files.exists(p)) {
            return new CustomizableBitmap(p, size);
        }
        if(Files.isDirectory(p)) {
            throw new RuntimeException("Invalid file path!");
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(filePath);
            ois = new ObjectInputStream(fis);
            CustomizableBitmap bitmap = (CustomizableBitmap) ois.readObject();
            if(bitmap.getSize() != size) throw new RuntimeException("Size mismatch!");
            bitmap.LOCK = new Object();
            return bitmap;
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}
