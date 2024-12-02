# 工程背景

提供一个工具可以扫描gitlab仓库代码的工具。

# 快速开始
service-aggregate/src/main/resources/config.yml 当中可以配置账号、git group信息（ai-service）、白名单（跳过某个项目比如ai-open-platform）

可以根据查询条件构造对应的文件的query，例如:

"包含cluster_01或者cluster_01_with_password或者RedisConfig() 但不包含 cluster_01_proxy 的 行
"
```java
public static void main(String[] args) throws Exception {
List<CodeSearchRequestHandler.FileTypeAndExp> list = new ArrayList<>();
Exp confCondition
= or(new TextMatchRequirement("^(?!.*cluster_01_proxy)(?=.*cluster_01(?:_with_password)?).*", true), new TextMatchRequirement("RedisConfig()", false));
//conf
list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.CONF));
//scala
list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.SCALA));
//Java
list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.JAVA));
//python
list.add(new CodeSearchRequestHandler.FileTypeAndExp(confCondition, FileType.PY));
CodeSearchRequestHandler handler = new CodeSearchRequestHandler(list);
handler.search().get();
handler.close();
}

```


com/xiaomi/codequality/constant/Const.java当中可以配置项目文件路径。

注：result/record文件为持久化文件。

# 如何扩展
实现TextExp，可以自定义将匹配的条目放入context当中。
```java
public class ScalaHeaderRequirement extends TextExp {

    private String header;

    public ScalaHeaderRequirement(String header) {
        this.header = header;
    }

    @Override
    public boolean eva(ExpContext<TextMatchEntity> context, String content) {
        String importPrefix = "import " + header.substring(0, header.lastIndexOf(".") + 1);
        String className = header.substring(header.lastIndexOf(".") + 1);
        String[] lines = content.split("\\R");
        List<TextMatchEntity> matches = context.getMatchEntities();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith(importPrefix)) {
                String suffix = line.substring(importPrefix.length()).trim();
                if (suffix.equals("_") || suffix.contains(className)) {
                    TextMatchEntity entity = new TextMatchEntity();
                    entity.setLineNumber(i + 1);
                    entity.setMatch(lines[i]);
                    matches.add(entity);
                }
            }
        }

        if (!matches.isEmpty()) {
            context.setMatchEntities(matches);
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("ScalaHeader[%s]", header);
    }
}
```


实现模板FileHandlerXiaoAiService，可以让扫描只关注某一类文件。

```java
public class ConfFileHandlerXiaoAiService extends FileHandlerXiaoAiService {
    public ConfFileHandlerXiaoAiService(Exp exp, Set<String> whiteList) {
        super(exp, whiteList);
    }

    @Override
    public boolean filter(File file) {
        return file.getName().endsWith(".conf") || file.getName().endsWith(".yml") || file.getName().endsWith(".yaml")
                || file.getName().endsWith(".properties");
    }

}
```

