package org.umiskky.service.task.socket.utils;

import org.slf4j.Logger;

import java.io.*;

/***
 * @apiNote IO流操作相关工具类
 */
public class IOUtil {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(IOUtil.class);

    /** 关闭对象输入流 */
    public static void close(ObjectInputStream is){
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    /** 关闭对象输出流 */
    public static void close(ObjectOutputStream os){
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    /** 关闭字节输入流 */
    public static void close(InputStream is){
        if(null != is){
            try {
                is.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    /** 关闭字节输出流 */
    public static void close(OutputStream os){
        if(null != os){
            try {
                os.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /** 关闭字节输入流和输出流 */
    public static void close(InputStream is, OutputStream os){
        close(is);
        close(os);
    }
}
