package indi.ljf.im.server.handler;

import indi.ljf.im.protocol.request.GroupChatRequestPacket;
import indi.ljf.im.protocol.response.GroupChatResponsePacket;
import indi.ljf.im.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;

/**
 * @author: ljf
 * @date: 2021/10/26 14:16
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
public class GroupChatRequestHandler extends SimpleChannelInboundHandler<GroupChatRequestPacket> {
    /**
     * 根据组id获取对应的channelGroup
     * channelGroup进行转发数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestPacket requestPacket) throws Exception {
        Integer groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        String userName = SessionUtil.getSession(ctx.channel()).getUserName();

        //response packet
        System.out.printf("转发用户[%s]的数据\n", userName);
        GroupChatResponsePacket responsePacket = new GroupChatResponsePacket();
        responsePacket.setSuccess(false);
        if (!SessionUtil.hasLogin(ctx.channel())) {
            responsePacket.setMessage("该用户未登录，请先登录！");
        } else if (channelGroup == null) {
            responsePacket.setMessage("群聊组不存在");
        } else if (!channelGroup.contains(ctx.channel())) {
            responsePacket.setMessage("该用户不在群聊中");
        } else {
            responsePacket.setSuccess(true);
            responsePacket.setFromUserName(userName);
            responsePacket.setMessage(requestPacket.getMessage());
            responsePacket.setGroupId(groupId);
            channelGroup.writeAndFlush(responsePacket, ChannelMatchers.isNot(ctx.channel()));
        }

        if (!responsePacket.isSuccess()) {
            ctx.channel().writeAndFlush(responsePacket);
        }

    }
}
