package cn.tzqwz.im.init;

import cn.tzqwz.common.im.protocolbuf.IMResponseProto;
import cn.tzqwz.im.handler.IMClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 关闭客户端拦截器
 */
public class IMClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //解码器,并设置解码消息类型
        pipeline.addLast(new ProtobufDecoder(IMResponseProto.IMResponse.getDefaultInstance()));
        //编码器
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new IMClientHandler());
    }
}
