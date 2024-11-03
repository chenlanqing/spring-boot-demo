package com.qing.fan.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2023年11月10日 21:50
 */
public class ClassUtils {

    public static List<Class<?>> getClasses(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".", "/");
        File dir = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(path)).getFile());
        if (dir.exists()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    List<Class<?>> subClasses = getClasses(packageName + "." + fileName);
                    classes.addAll(subClasses);
                } else if (fileName.endsWith(".class")) {
                    String className = fileName.substring(0, fileName.length() - 6);
                    try {
                        classes.add(Class.forName(packageName + "." + className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }

}
