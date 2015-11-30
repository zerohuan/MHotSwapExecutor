package com.yjh.hotswap;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 将out和err重定向成一个PrintStream实例
 *
 * 由于这里直接使用
 *
 * Created by yjh on 15-11-30.
 */
public final class HackSystem {
    public static final InputStream in = System.in;
    //默认的缓冲区大小是32
    private static ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    public static final PrintStream out = new PrintStream(buffer);
    public static final PrintStream err = out;

    public static String getBufferingString() {
        return buffer.toString();
    }

    public static void cleanBuffer() {
        buffer.reset();
    }

    /*
        兼容System的public static方法
     */

    public static void setSecurityManager(SecurityManager s) {
        System.setSecurityManager(s);
    }

    public static void setErr(PrintStream err) {
        System.setErr(err);
    }

    public static Console console() {
        return System.console();
    }
}
