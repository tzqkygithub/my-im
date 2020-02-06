package cn.tzqwz.im.handler;

import cn.tzqwz.common.base.constants.CommandType;
import cn.tzqwz.common.im.protocolbuf.IMRequestProto;
import cn.tzqwz.common.im.protocolbuf.IMResponseProto;
import cn.tzqwz.im.server.IMServer;
import cn.tzqwz.im.session.SocketSessionHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义拦截器
 */
public class IMServerHandler extends SimpleChannelInboundHandler<IMRequestProto.IMRequest> {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext cx, IMRequestProto.IMRequest imRequest) throws Exception {
      LOGGER.info("客户端[{}]向服务器端发送消息:{}",cx.channel().remoteAddress(),imRequest.getReqmsg());
        //判断客户端发送的消息
        int reqtype = imRequest.getReqtype();
        if(reqtype == CommandType.LOGIN_TYPE){
            //报文为登录类型
              //保存连接信息到服务器端
            SocketSessionHolder.putChannel(imRequest.getRequestid(), (SocketChannel) cx.channel());
            SocketSessionHolder.saveSession(imRequest.getRequestid(),imRequest.getReqmsg());
            LOGGER.info("userId:{};userName:{}",imRequest.getRequestid(),imRequest.getReqmsg());
            LOGGER.info("【{}】上线成功");
        }else if(reqtype == CommandType.PING_TYPE){
            //报文为检测心跳类型
        }
        //向客户端回消息
        IMResponseProto.IMResponse imResponse = IMResponseProto.IMResponse.newBuilder()
                .setResponseid("456789")
                .setResmsg("服务器端:收到你送的消息,那我们开始传送数据吧")
                .setRestype(1)
                .build();
        cx.channel().writeAndFlush(imResponse);
    }

    /**
     * 客户端连接服务器后回调方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端[{}]连接到服务器",ctx.channel().remoteAddress());
    }

    /**
     * 客户端连接断开回调方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端[{}]连接断开",ctx.channel().remoteAddress());
    }


    /**
     * 拦截异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        //断开连接
        ctx.close();
    }
}
