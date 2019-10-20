package cn.tzqwz.im.client;

import cn.tzqwz.im.init.IMClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * IM客户端
 */
@Component
public class IMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMClient.class);

    @Value("${im.server.address}")
    private String imServerAddress;

    @Value("${im.server.port}")
    private Integer imserverPort;

    /**
     * 启动客户端
     */
    public void start(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new IMClientInitializer());
            ChannelFuture future = bootstrap.connect(imServerAddress, imserverPort).sync();
            //判断与服务器端是否连接成功
            if(future.isSuccess()){
                LOGGER.info("connect server is success");
                LOGGER.info("client start is success");
            }
            //异步等待关系与服务器连接
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("connect server is error");
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
