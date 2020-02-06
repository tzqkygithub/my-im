package cn.tzqwz.im.session;

import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Socket连接
 */
public class SocketSessionHolder {

    //socket会话连接
    private static Map<String, SocketChannel> channelMap = new ConcurrentHashMap<String,SocketChannel>();

    //在服务器端保存连接对象的信息
    private static Map<String,String> socketSessionMap = new ConcurrentHashMap<String, String>();

    /**
     * 将SocketChannel连接保存到服务器端上
     * @param userId
     * @param socketChannel
     */
    public static void putChannel(String userId,SocketChannel socketChannel){
        //判断服务器端连接中是否存在当前连接
        SocketChannel serverChannel = channelMap.get(userId);
        if(serverChannel==null){
            channelMap.put(userId,socketChannel);
        }
    }

    /**
     *获取服务器端上面的连接对象
     * @param userId
     */
    public static SocketChannel getChannel(String userId){
        return channelMap.get(userId);
    }

    /**
     * 删除SocketChannel
     * @param userId
     */
    public static void removeChannel(String userId){
        channelMap.remove(userId);
    }

    /**
     * 保存会话信息
     */
    public static void saveSession(String userId,String userName){
        String serverUserName = socketSessionMap.get(userId);
        if(serverUserName==null){
            socketSessionMap.put(userId,userName);
        }
    }

    /**
     * 获取会话信息
     * @param userId
     * @return
     */
    public static String getSession(String userId){
         return socketSessionMap.get(userId);
    }

    /**
     * 删除会话信息
     * @param userId
     */
    public static void removeSession(String userId){
        socketSessionMap.remove(userId);
    }
}
