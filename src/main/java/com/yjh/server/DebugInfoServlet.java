package com.yjh.server;

import com.yjh.hotswap.JavaClassExecutor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 用于信息调试信息的servlet
 * Created by yjh on 15-11-30.
 */
public class DebugInfoServlet extends HttpServlet {
    /**
     * 接受二进制字节流class文件
     * 使用{@link JavaClassExecutor}加载并执行该类的main函数
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        //解析传入的字节流
        ByteArrayOutputStream bos = null;
        BufferedInputStream din = null;
        try {
            din = new BufferedInputStream(req.getInputStream());
            bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while((len = din.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] classByte = bos.toByteArray();
            out.println("开始接收处理字节码：");
            out.println(new String(classByte, "UTF8"));
            out.println(JavaClassExecutor.execute(classByte));

        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            if(bos != null)
                bos.close();
            if(din != null)
                din.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
