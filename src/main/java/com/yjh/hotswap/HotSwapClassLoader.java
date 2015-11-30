package com.yjh.hotswap;

/**
 * 热部署类加载器
 *
 * Created by yjh on 15-11-29.
 */
public class HotSwapClassLoader extends ClassLoader {
    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    /**
     * 将ClassLoader的defineClass方法“公开”
     *
     * @param classByte
     * @return
     */
    public Class<?> loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }

//    /**
//     * 提高findClass方法的访问级别为public
//     *
//     * @param name
//     * @return
//     * @throws ClassNotFoundException
//     */
//    @Override
//    public Class<?> findClass(String name) throws ClassNotFoundException {
//        byte[]
//
//        return super.findClass(name);
//    }
}


