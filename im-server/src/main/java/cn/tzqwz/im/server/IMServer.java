package cn.tzqwz.im.server;

import cn.tzqwz.config.ApplicationConfig;
import cn.tzqwz.im.init.IMServerInitializer;
import cn.tzqwz.zk.IMServerRegistration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;

/**
 * IM服务类
 */
@Component
public class IMServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServer.class);


    @Value("${im.server.port}")
    private Integer imServerPort;


    @Autowired
    private IMServerRegistration imServerRegistration;

    /**
     * 启动IMServer
     */
    public void start() throws Exception{
        //创建两个工作线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                .group(boosGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new IMServerInitializer());
        //绑定端口号,并启动服务器
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(imServerPort).sync();
            LOGGER.info("IM服务器端启动成功,监听端口号为:{}",imServerPort);
            //服务信息注册
            imServerRegistration.serverRegister();
            //等待服务器端关闭
            channelFuture.channel().closeFuture().sync();

            LOGGER.info("IM服务器端已经关闭了");
        } catch (InterruptedException e) {
            e.printStackTrace();
            LOGGER.error("IM服务器端启动失败");
        }finally {
            //关闭线程组
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
