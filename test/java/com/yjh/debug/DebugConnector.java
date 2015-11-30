package com.yjh.debug;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 *
 * Created by yjh on 15-11-30.
 */
public class DebugConnector {

    public static void test2() throws Exception {
        URL url = new URL("http://localhost:8080/debug");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);

        connection.setRequestProperty("Content-Type", "application/octet-stream");

        FileInputStream fi = new FileInputStream
                ("/home/yjh/dms/wks/MHotSwapExecutor/target/test-classes/com/yjh/example/Test2.class");
        byte[] bytes = new byte[fi.available()];
        connection.setRequestProperty("Content-length", "" + bytes.length);

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        dos.write(bytes, 0, fi.read(bytes));
        System.out.println("本地字节码信息：长度" + bytes.length + "\n");

        dos.flush();
        dos.close();
        fi.close();

        BufferedInputStream bi = new BufferedInputStream(connection.getInputStream());
        byte[] output = new byte[bi.available()];
        int len = bi.read(output);
        System.out.println("\n返回消息：长度" + len);
        String response;
        System.out.println(response = new String(output, "utf8"));

        //保存到temp文件
        File temp = new File("/tmp/debug.tmp");
        if(!temp.exists()) {
            temp.createNewFile();
        }
        FileWriter writer = new FileWriter(temp);
        writer.write(response);
        writer.close();
        bi.close();
        connection.disconnect();
    }

    public static void main(String[] args) throws Exception {
        test2();
    }
}
