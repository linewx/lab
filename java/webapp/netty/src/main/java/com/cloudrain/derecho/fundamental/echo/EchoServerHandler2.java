package com.cloudrain.derecho.fundamental.echo;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * Created by lugan on 12/29/2016.
 */
public class EchoServerHandler2 extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.name());
        //ctx.read();
    }
}
