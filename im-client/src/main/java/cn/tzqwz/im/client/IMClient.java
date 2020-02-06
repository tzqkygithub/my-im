package cn.tzqwz.im.client;

import cn.tzqwz.common.base.constants.BaseConstants;
import cn.tzqwz.common.base.constants.CommandType;
import cn.tzqwz.common.base.res.BaseResponse;
import cn.tzqwz.common.dto.input.LoginImInDTO;
import cn.tzqwz.common.entity.IMServerAddressEntity;
import cn.tzqwz.common.im.protocolbuf.IMRequestProto;
import cn.tzqwz.im.init.IMClientInitializer;
import cn.tzqwz.service.RoteRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * IM客户端
 */
@Component
public class IMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMClient.class);

    private SocketChannel socketChannel;

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
                socketChannel = (SocketChannel) future.channel();
                //将自己的信息注册到服务器端上
                registerIMServer("1580808193262","tangziqianr");
            }
            //异步等待关闭与服务器端连接
            socketChannel.closeFuture().sync();

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
        loginImInDTO.setUserName("tangziqianr");
        loginImInDTO.setPassword("123456");
        BaseResponse<String> routeRes = roteRequest.getIMServer(loginImInDTO);
        if(!routeRes.getRetCode().equals(BaseConstants.HTTP_RES_CODE_200)){
            LOGGER.error(routeRes.getRetMsg());
            throw new RuntimeException("连接服务器失败,"+routeRes.getRetMsg());
        }
      return new IMServerAddressEntity(routeRes.getRetMsg());
    }

    /**
     * 将自己的客户端信息注册到服务器端上
     */
    private void registerIMServer(String userId,String userName){
        //向服务器端发送消息
        IMRequestProto.IMRequest imRequest = IMRequestProto.IMRequest.newBuilder()
                .setRequestid(userId)
                .setReqmsg(userName)
                .setReqtype(CommandType.LOGIN_TYPE).build();
        ChannelFuture future = socketChannel.writeAndFlush(imRequest);
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                LOGGER.info("userid:{};userName:{}",userId,userName);
                LOGGER.info("registry cim server success!");
            }
        });
    }
}
