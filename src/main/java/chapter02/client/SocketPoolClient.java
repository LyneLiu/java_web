package chapter02.client;

import chapter02.server.SocketPool;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/4/17.
 */
public class SocketPoolClient {

    public static final Logger logger = LoggerFactory.getLogger(SocketPoolClient.class);

    private static SocketPool rpcSocketPool;

    static {
        rpcSocketPool = new SocketPool(Thread.currentThread().getContextClassLoader().getResourceAsStream("rpc.properties"));
    }

    public static void callSyn() {
        try {
            Socket socket = null;
            try {
                // 从池中获取对象
                socket = rpcSocketPool.borrowObject();

                //Thread.sleep(3000);
            } catch (Exception e1) {
                logger.error("Could not get a Socket from the pool", e1);
            }
            if (null == socket) {
                return;
            }
            try {
                /* 处理socket请求 */
                service(socket);

            } catch (Exception e) {
                logger.error("", e);
            } finally {
                rpcSocketPool.returnObject(socket);// 对象返回到池中
            }
        } catch (Throwable e) {
            logger.error("", e);
        }
    }

    /**
     * 响应客户端的HTTP请求
     *
     * @param socket
     */
    private static void service(Socket socket) {

        try {
            InputStream socketIn = socket.getInputStream();
            //Thread.sleep(500);

            String uri = "/rpc.properties";

            /* path为resources路径，如/logback.xml获取配置信息 */
            InputStream resourceInputStream = HttpServer.class.getResourceAsStream(uri);

            OutputStream socketOut = socket.getOutputStream();

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = resourceInputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer));
            }

            //Thread.sleep(1000);

        } catch (Exception e) {
            logger.error("service error:" + e.getStackTrace());
        }

    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(){
                public void run(){
                    callSyn();
                }
            };
            thread.start();
        }
        long endTime = System.currentTimeMillis();
        //System.out.println("程序运行时间： " + (endTime - startTime) / 1000 + "s");
    }

}
