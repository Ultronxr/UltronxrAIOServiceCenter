package cn.ultronxr.common.util;

import java.net.URL;

/**
 * @author Ultronxr
 * @date 2023/01/14 14:13
 * @description
 */
public class SystemUtils {

    /**
     * 获取指定 class 所处的运行路径<br/>
     *
     * @param clazz 指定的某一个 class
     * @return   IDE 中，返回：file:/D:/AllFilesCode/workspace/IntelliJIDEA/UltronxrAIOServiceCenter/UAIOSC-web/target/classes/<br/>
     *         jar 包中，返回：file:/D:/AllFilesCode/workspace/IntelliJIDEA/UltronxrAIOServiceCenter/UAIOSC-web/target/UltronxrAIOServiceCenter-0.0.1-SNAPSHOT.jar
     */
    public static String getRuntimePath(Class<?> clazz) {
        String classPath = clazz.getName().replaceAll("\\.", "/") + ".class";
        URL resource = clazz.getClassLoader().getResource(classPath);
        if (resource == null) {
            return null;
        }
        String urlString = resource.toString();
        int insidePathIndex = urlString.indexOf('!');
        boolean isInJar = insidePathIndex > -1;
        if (isInJar) {
            urlString = urlString.substring(urlString.indexOf("file:"), insidePathIndex);
            return urlString;
        }
        return urlString.substring(urlString.indexOf("file:"), urlString.length() - classPath.length());
    }

    public static void main(String[] args) {
        System.out.println(getRuntimePath(SystemUtils.class));
    }

}
