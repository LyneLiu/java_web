package chapter01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by nn_liu on 2017/4/17.
 */
public class HTTPClient {

    public static final String uri = "/logback.xml";

    public static final String host = "localhost";

    public static final int port = 8080;

    public static void main(String[] args) {
        doGet(host,port,uri);
    }

    private static void doGet(String host, int port, String uri) {
        Socket socket = null;

        try{
            socket = new Socket(host,port);

            StringBuffer requestFirstLineBuffer = new StringBuffer("GET "+uri+" HTTP/1.1\r\n");
            requestFirstLineBuffer.append("Accept: */*\r\n");
            requestFirstLineBuffer.append("Accept-Language: zh-cn\r\n");
            requestFirstLineBuffer.append("Accept-Encoding: gzip, deflate\r\n");
            requestFirstLineBuffer.append("User-Agent: HTTPClient\r\n");
            requestFirstLineBuffer.append("Host: localhost:8080\r\n");
            requestFirstLineBuffer.append("Connection: Keep-Alive\r\n\r\n");

            OutputStream socketOut = socket.getOutputStream();
            socketOut.write(requestFirstLineBuffer.toString().getBytes());

            Thread.sleep(2000);

            InputStream socketIn = socket.getInputStream();
            int size = socketIn.available();
            byte[] buffer = new byte[size];
            socketIn.read(buffer);
            System.out.println(new String(buffer));

        } catch (Exception e) {
            // catch exception
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                // do nothing
            }
        }

    }
}
