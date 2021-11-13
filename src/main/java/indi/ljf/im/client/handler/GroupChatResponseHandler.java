package indi.ljf.im.client.handler;

import indi.ljf.im.protocol.response.GroupChatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: ljf
 * @date: 2021/10/26 14:16
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class GroupChatResponseHandler extends SimpleChannelInboundHandler<GroupChatResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.printf("收到来自组[%d] 用户[%s]的消息: %s\n", responsePacket.getGroupId(), responsePacket.getFromUserName(), responsePacket.getMessage());
        } else {
            System.out.printf("发送失败，原因为：%s\n", responsePacket.getMessage());
        }
    }
}
