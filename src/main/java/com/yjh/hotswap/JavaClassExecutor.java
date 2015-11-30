package com.yjh.hotswap;

import java.lang.reflect.Method;

/**
 * JavaClass 执行工具
 * 将带有main函数（用于调试）的class动态的加载到正在运行的J2EE服务器上
 *
 * Created by yjh on 15-11-30.
 */
public class JavaClassExecutor {

    /**
     * 每个新建一个类加载器，加载字节码包含的class，实现热部署
     *
     *
     * @param classByte
     * @return
     */
    public static String execute(byte[] classByte) {
        HackSystem.cleanBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modifiedBytes = cm.modifyUTF8Constant("java/lang/System", "com/yjh/hotswap/HackSystem");

        HotSwapClassLoader loader = new HotSwapClassLoader();
        try {
            Class clazz = loader.loadByte(modifiedBytes);
            Method method = clazz.getMethod("main", new Class[] {String[].class});
            method.invoke(null, new String[]{null});
        } catch (Throwable e) {
            //异常信息也输出到指定的输出流中
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferingString();
    }
}
