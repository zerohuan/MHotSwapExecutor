package com.yjh.hotswap;

import com.yjh.util.ByteUtils;

/**
 * 类修改器，修改class字节码
 *
 * Created by yjh on 15-11-30.
 */
public class ClassModifier {
    private static final int CONSTANTS_POOL_COUNT_INDEX = 8;

    private static final int CONSTANT_Utf8_info = 1;

    private static final int[] CONSTANT_ITEM_LENGTH = {-1,-1,-1,5,5,9,9,3,3,5,5,5,5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 在常量池中找到对应的字符串，替换成目标字符串
     * @param oldStr
     * @param newStr
     * @return
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        //获取常量池中元素的个数
        int cpc = getConstantPoolCount();
        int offset = CONSTANTS_POOL_COUNT_INDEX + 2;
        for(int i = 0; i < cpc; i++) {
            //获取tag，tag的类型是u1的；
            int tag = ByteUtils.bytes2Int(classByte, offset, u1);
            if(tag == CONSTANT_Utf8_info) { //因为Utf8型的长度是不定的
                //CONSTANT_Utf8_info的第二项是长度
                int len = ByteUtils.bytes2Int(classByte, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2String(classByte, offset, len);
                if(str.equalsIgnoreCase(oldStr)) {
                    //找到开始替换
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    //根据Java虚拟机规范，这个strLen应该是byte[]数组的长度，而不是String的长度
                    byte[] strLen = ByteUtils.int2Bytes(strBytes.length, u2);
                    //替换长度
                    classByte = ByteUtils.bytesReplace(classByte, offset - u2, u2, strLen);
                    //替换内容
                    classByte = ByteUtils.bytesReplace(classByte, offset, len, strBytes);
                    HackSystem.out.println("替换成功");
                    return classByte;
                } else {
                    offset += len;
                }
            } else {
                //如果不是CONSTANT_utf8_info，取得对应常量表类型的长度
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }

        return classByte;
    }

    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classByte, 0, classByte.length);
    }
}
