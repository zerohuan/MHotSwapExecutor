package com.yjh.example;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by yjh on 15-12-1.
 */
public class Test2 {
    private static final String BR = "\n";
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        try {
            System.out.println("开始调试：");
            //应该是WebAppClassLoader
            URLClassLoader webAppClassLoader = (URLClassLoader)Thread.currentThread().getContextClassLoader();
            sb.append(webAppClassLoader).append(BR);

            printURLs(webAppClassLoader.getURLs(), sb);

            //默认模式下没有启用Shared和Catalina，应该是CommonClassLoader它是URLClassLoader一个对象实例
            //本例中启用了shared.loader，因此这里取到的是SharedClassLoader，也是URLClassLoader对象实例
            showClassLoader((URLClassLoader)webAppClassLoader.getParent(),
                    "SharedClassLoader", sb);

            //commonClassLoader类加载，加载Tomcat和应用程序共同使用的类
            showClassLoader((URLClassLoader)webAppClassLoader.getParent().getParent(),
                    "commonClassLoader", sb);

            //应用程序类加载器，加载Tomcat本身的类库
            showClassLoader((URLClassLoader)webAppClassLoader.getParent().getParent().getParent(),
                    "SystemClassLoader", sb);

            //扩展类加载器，加载Tomcat本身的类库
//            showClassLoader((URLClassLoader)webAppClassLoader.getParent().getParent().getParent().getParent(),
//                    "ExtClassLoader", sb);
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private static void showClassLoader(URLClassLoader classLoader, String name, StringBuilder sb) {
        if(classLoader != null) {
            sb.append(name).append("加载路径").append(BR);
            printURLs(classLoader.getURLs(), sb);
        } else {
            sb.append(name).append("没有对应类加载器实例").append(BR);
        }
    }

    private static void printURLs(URL[] urls, StringBuilder sb) {
        for(URL url : urls) {
            sb.append(url).append(BR);
        }
        sb.append(BR);
    }
}
