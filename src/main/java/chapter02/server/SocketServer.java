package chapter02.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by nn_liu on 2017/4/14.
 */
public class SocketServer {

    public static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private int port;

    private ServerSocket serverSocket;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketServer() {
        this.port = 8080;
    }

    public SocketServer(int port) {
        this.port = port;
    }


    public void start() {
        SocketServer httpServer = new SocketServer(8080);
        try {
            serverSocket = new ServerSocket(httpServer.getPort());
            logger.info(String.format("server listening port: %d ......", serverSocket.getLocalPort()));

            while (true) {
                try {
                    serverSocket.accept();
                } catch (Exception e) {
                    logger.error(String.format("socket error:" + e.getStackTrace()));
                }

            }
        } catch (IOException e) {
            logger.error("init server error:", e);
        }

    }

    public void stop(){
        if (serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }
}
