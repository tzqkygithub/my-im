package cn.tzqwz.im.handler;

import cn.tzqwz.common.im.protocolbuf.IMRequestProto;
import cn.tzqwz.common.im.protocolbuf.IMResponseProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义客户端拦截器
 */
public class IMClientHandler extends SimpleChannelInboundHandler<IMResponseProto.IMResponse> {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IMResponseProto.IMResponse imResponse) throws Exception {
        LOGGER.info("服务器端:"+imResponse.getResmsg());
    }

    /**
     * 连接服务器端成功
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("连接服务器端成功");
        //向服务器端发送消息
        IMRequestProto.IMRequest imRequest = IMRequestProto.IMRequest.newBuilder()
                .setRequestid("123456")
                .setReqmsg("我已经连接到你了")
                .setReqtype(2).build();
        ctx.channel().writeAndFlush(imRequest);

    }

    /**
     * 与服务器端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("与服务器端已经断开连接");
        ctx.close();
    }


    /**
     * 连接出现异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error(cause.getMessage());
        ctx.close();
    }
}
