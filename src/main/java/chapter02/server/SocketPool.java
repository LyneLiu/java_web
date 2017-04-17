package chapter02.server;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * 通过pool的方式创建socket链接，避免socket创建、销毁造成额外的性能消耗
 * Created by nn_liu on 2017/4/17.
 */
public class SocketPool {

    private GenericObjectPool<Socket> socketPool;

    public SocketPool(InputStream in) {
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(pro.getProperty("blockWhenExhausted")));
        poolConfig.setMaxWaitMillis(Long.parseLong(pro.getProperty("maxWait")));
        poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("minIdle")));
        poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));
        poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("testOnBorrow")));
        poolConfig.setTestOnReturn(Boolean.parseBoolean(pro.getProperty("testOnReturn")));
        poolConfig.setTestOnCreate(Boolean.parseBoolean(pro.getProperty("testOnCreate")));
        poolConfig.setTestWhileIdle(Boolean.parseBoolean(pro.getProperty("testWhileIdle")));
        poolConfig.setLifo(Boolean.parseBoolean(pro.getProperty("lifo")));
        // 初始化对象池
        socketPool = new GenericObjectPool<Socket>(
                new SocketFactory(pro.getProperty("ip"), Integer.parseInt(pro.getProperty("port"))), poolConfig);
    }

    public Socket borrowObject() throws Exception {
        return socketPool.borrowObject();
    }

    public void returnObject(Socket socket) {
        socketPool.returnObject(socket);
    }
}
