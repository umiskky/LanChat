package org.umiskky.service.task.socket.utils;

import java.io.*;

/***
 * @apiNote IO流操作相关工具类
 */
public class IOUtil {
    /** 关闭对象输入流 */
    public static void close(ObjectInputStream is){
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 关闭对象输出流 */
    public static void close(ObjectOutputStream os){
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 关闭字节输入流 */
    public static void close(InputStream is){
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 关闭字节输出流 */
    public static void close(OutputStream os){
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /** 关闭字节输入流和输出流 */
    public static void close(InputStream is, OutputStream os){
        close(is);
        close(os);
    }
}
