package chapter02.server;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by nn_liu on 2017/4/17.
 */
public class SocketFactory extends BasePooledObjectFactory<Socket>{

    private String ip;
    private int port;

    public SocketFactory(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * 创建对象
     * @return
     * @throws Exception
     */
    public Socket create() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
        return socket;
    }

    /**
     * 将TSocket封装为PooledObject对象放入池中
     * @param socket
     * @return
     */
    public PooledObject<Socket> wrap(Socket socket) {
        return new DefaultPooledObject<Socket>(socket);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<Socket> p) throws Exception {
        Socket socket = p.getObject();
        socket.close();
        super.destroyObject(p);
    }

    /**
     * 验证对象
     */
    @Override
    public boolean validateObject(PooledObject<Socket> p) {
        Socket t = p.getObject();
        boolean closed = t.isClosed();
        boolean connected = t.isConnected();
        boolean outputShutdown = t.isOutputShutdown();
        boolean inputShutdown = t.isInputShutdown();

        boolean urgentFlag = false;
        try {
            t.sendUrgentData(0xFF);
            urgentFlag = true;
        } catch (Exception e) {

        }

        boolean isShutdown = !(closed || inputShutdown || outputShutdown);

        return urgentFlag && connected && isShutdown;
    }
}
