package indi.ljf.im.server.handler;

import indi.ljf.im.common.codec.PacketCodeC;
import indi.ljf.im.protocol.Packet;
import indi.ljf.im.protocol.Session;
import indi.ljf.im.protocol.request.*;
import indi.ljf.im.protocol.response.*;
import indi.ljf.im.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ljf
 * @date: 2021/11/5 13:26
 * @description: 指令请求处理器
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class CommandRequestHandler extends SimpleChannelInboundHandler<Packet> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet requestPacket) throws Exception {
        if (requestPacket instanceof CreateGroupRequestPacket) {
            processCreateGroupCommand(ctx, (CreateGroupRequestPacket) requestPacket);
        } else if (requestPacket instanceof JoinGroupRequestPacket) {
            processJoinGroupCommand(ctx, (JoinGroupRequestPacket) requestPacket);
        } else if (requestPacket instanceof LogoutRequestPacket) {
            processLogoutCommand(ctx);
        } else if (requestPacket instanceof QuitGroupRequestPacket) {
            processQuitGroupCommand(ctx, (QuitGroupRequestPacket) requestPacket);
        } else {
            ctx.fireChannelRead(requestPacket);
        }
    }

    private void processQuitGroupCommand(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) {
        Integer groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(false);
        if (channelGroup == null) {
            responsePacket.setMessage("群聊不存在");
        } else if (!channelGroup.contains(ctx.channel())) {
            responsePacket.setMessage("该用户不在该群聊");
        } else {
            channelGroup.remove(ctx.channel());
            responsePacket.setSuccess(true);
            responsePacket.setMessage("退出群聊成功");
            Session session = SessionUtil.getSession(ctx.channel());
            responsePacket.setFromUserName(session.getUserName());
            responsePacket.setFromUserId(session.getUserId());
            channelGroup.writeAndFlush(responsePacket);
        }
        if (!responsePacket.isSuccess()) {
            ctx.channel().writeAndFlush(responsePacket);
        }
    }

    private void processLogoutCommand(ChannelHandlerContext ctx) {
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        if (SessionUtil.hasLogin(ctx.channel())) {
            SessionUtil.unBindSession(ctx.channel());
            responsePacket.setSuccess(true);
            responsePacket.setMessage("退出成功");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setMessage("该用户未登录");
        }
    }

    private void processJoinGroupCommand(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) {
        Integer groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(false);
        if (channelGroup == null) {
            responsePacket.setMessage("群聊不存在");
        } else if (channelGroup.contains(ctx.channel())) {
            responsePacket.setMessage("该用户已加入群聊");
        } else {
            channelGroup.add(ctx.channel());
            responsePacket.setSuccess(true);
            responsePacket.setMessage("加入群聊成功");
            Session session = SessionUtil.getSession(ctx.channel());
            responsePacket.setFromUserName(session.getUserName());
            responsePacket.setFromUserId(session.getUserId());
            channelGroup.writeAndFlush(responsePacket);
        }
        if (!responsePacket.isSuccess()) {
            ctx.channel().writeAndFlush(responsePacket);
        }
    }

    private void processCreateGroupCommand(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) {
        List<Integer> userIdList = requestPacket.getUserIdList();
        List<String> userNameList = new ArrayList<>(userIdList.size());

        //create channel group
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        userIdList.forEach(userId -> {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });

        //创建群聊创建的响应
        Integer groupId = SessionUtil.randomId();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);
        responsePacket.setUserNameList(userNameList);

        //群发消息
        channelGroup.writeAndFlush(responsePacket);
        log.info("群创建成功，id为{}，群成员有：{}", groupId, userNameList);

        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }


}
