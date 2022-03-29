package com.example.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    // 黏包/拆包 分隔字符

    @Override
    protected void initChannel(SocketChannel channel) {

        // 对象传输处理
        channel.pipeline().addLast(new MsgDecoder(MsgInfo.class));
        channel.pipeline().addLast(new MsgEncoder(MsgInfo.class));
        // json string传输处理
//		ByteBuf delimeterBuf = Unpooled.copiedBuffer(MSG_SUFFIX.getBytes());
//		channel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, delimeterBuf));
//		channel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//		channel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

        // 在管道中添加我们自己的接收数据实现方法

        channel.pipeline().addLast(new IdleStateHandler(180, 0, 180));
        channel.pipeline().addLast(new ServerHandler());
    }

}