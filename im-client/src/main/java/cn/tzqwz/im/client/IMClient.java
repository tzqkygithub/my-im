package cn.tzqwz.im.client;

import cn.tzqwz.common.base.constants.BaseConstants;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.dto.input.LoginImInDTO;
import cn.tzqwz.common.entity.IMServerAddressEntity;
import cn.tzqwz.im.init.IMClientInitializer;
import cn.tzqwz.service.RoteRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * IM客户端
 */
@Component
public class IMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMClient.class);


    @Autowired
    private RoteRequest roteRequest;

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
            IMServerAddressEntity imServer = getIMServer();
            ChannelFuture future = bootstrap.connect(imServer.getImServerUrl(), imServer.getImServerPort()).sync();
            //判断与服务器端是否连接成功
            if(future.isSuccess()){
                LOGGER.info("connect server is success");
                LOGGER.info("服务器地址为[{}],监听端口号为[{}]",imServer.getImServerUrl(),imServer.getImServerPort());
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

    /**
     * 获取连接服务器端的地址信息
     * @return
     */
    private IMServerAddressEntity getIMServer(){
        LoginImInDTO loginImInDTO = new LoginImInDTO();
        loginImInDTO.setUserName("tangziqiang");
        loginImInDTO.setPassword("123456");
        BaseResponse<String> routeRes = roteRequest.getIMServer(loginImInDTO);
        if(!routeRes.getRetCode().equals(BaseConstants.HTTP_RES_CODE_200)){
            LOGGER.error(routeRes.getRetMsg());
            throw new RuntimeException("连接服务器失败,"+routeRes.getRetMsg());
        }
      return new IMServerAddressEntity(routeRes.getRetMsg());
    }
}
