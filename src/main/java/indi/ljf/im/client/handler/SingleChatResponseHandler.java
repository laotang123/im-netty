package indi.ljf.im.client.handler;

import indi.ljf.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ljf
 * @date: 2021/10/24 15:49
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class SingleChatResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) throws Exception {
        Integer fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + responsePacket.getMessage());
    }
}
