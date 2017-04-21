package chapter01;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/4/14.
 */
public class HTTPServer {

    public static final Logger logger = LoggerFactory.getLogger(HTTPServer.class);

    public static final int DEFAULT_PORT = 8080;

    public static final int BYTELEN = 1024;

    public static final int SLEEP_MILLIONSECONDS = 1000;

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HTTPServer(){
        this.port = DEFAULT_PORT;
    }

    public HTTPServer(int port){
        this.port = DEFAULT_PORT;
    }


    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer(DEFAULT_PORT);
        try {
            ServerSocket serverSocket = new ServerSocket(httpServer.getPort());
            logger.info(String.format("server listening port: %d ......",serverSocket.getLocalPort()));

            Socket socket = null;
            while(true){
                try{
                    socket = serverSocket.accept();
                    logger.info(String.format("build a new tcp connection with client:"+socket.getInetAddress()+":"+socket.getPort()));
                    service(socket);
                }catch (Exception e){
                    logger.error(String.format("socket error:"+e.getStackTrace()));
                }finally {
                    socket.close();
                }

            }
        }catch (IOException e) {
            logger.error("init server error:",e);
        }

    }

    /**
     * 响应客户端的HTTP请求
     * @param socket
     */
    private static void service(Socket socket) {

        try {
            InputStream socketIn  = socket.getInputStream();
            Thread.sleep(SLEEP_MILLIONSECONDS);

            int size = socketIn.available();
            byte[] buffer = new byte[size];
            socketIn.read(buffer);
            String request = new String(buffer);
            logger.info(String.format("requst content:"+request));

        /*解析HTTP请求第一行数据*/
            String firstLineOfRequest = request.substring(0,request.indexOf("\r\n"));
            String[] parts = firstLineOfRequest.split(" ");

            String uri = parts[1];

            String contentType;

            if (uri.indexOf("html") != -1 || uri.indexOf("html") != -1){
                contentType = "text/html";
            }else if(uri.indexOf("jpg") != -1 || uri.indexOf("jpeg") != -1){
                contentType = "image/jpeg";
            }else if (uri.indexOf("gif") != -1) {
                contentType = "image/gif";
            }else{
                contentType = "application/octet-stream";
            }


            String responseFirstLine = "HTTP/1.1 200 OK\r\n";
            /*响应状态及响应头需要与响应正文隔一行*/
            String responseHeader = "Content-Type:"+contentType+"\r\n\r\n";

            /* path为resources路径，如/logback.xml获取配置信息 */
            InputStream resourceInputStream = HttpServer.class.getResourceAsStream(uri);

            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(responseFirstLine.getBytes());
            socketOut.write(responseHeader.getBytes());

            int len = 0;
            buffer = new byte[BYTELEN];
            while ((len = resourceInputStream.read(buffer)) != -1){
                socketOut.write(buffer,0,len);
            }

            Thread.sleep(SLEEP_MILLIONSECONDS);

        }catch (Exception e){
            logger.error("service error:"+e.getStackTrace());
        }

    }

}
