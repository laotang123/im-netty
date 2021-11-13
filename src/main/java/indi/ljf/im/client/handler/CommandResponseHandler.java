package indi.ljf.im.client.handler;

import indi.ljf.im.protocol.Session;
import indi.ljf.im.protocol.response.*;
import indi.ljf.im.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: ljf
 * @date: 2021/11/5 11:02
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class CommandResponseHandler extends SimpleChannelInboundHandler<ResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponsePacket responsePacket) throws Exception {
        if (responsePacket instanceof LoginResponsePacket) {
            processLoginResponse(ctx, (LoginResponsePacket) responsePacket);
        } else if (responsePacket instanceof LogoutResponsePacket) {
            processLogoutResponse((LogoutResponsePacket) responsePacket);
        } else if (responsePacket instanceof CreateGroupResponsePacket) {
            processCreateGroupResponse((CreateGroupResponsePacket) responsePacket);
        } else if (responsePacket instanceof JoinGroupResponsePacket) {
            processJoinGroupResponse(ctx, (JoinGroupResponsePacket) responsePacket);
        } else if (responsePacket instanceof QuitGroupResponsePacket) {
            processQuitGroupResponse(ctx, (QuitGroupResponsePacket) responsePacket);
        } else {
            ctx.fireChannelRead(responsePacket);
        }

    }

    private void processQuitGroupResponse(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) {
        Integer fromUserId = responsePacket.getFromUserId();
        Integer groupId = responsePacket.getGroupId();
        String fromUserName = responsePacket.getFromUserName();
        if (responsePacket.isSuccess()) {
            Session session = SessionUtil.getSession(ctx.channel());
            if (session.getUserId() == fromUserId) {
                log.info("成功退出群聊：{}", groupId);
            } else {
                log.info("群聊{}，用户{}退出", groupId, fromUserName);
            }
        } else {
            log.info("退出群聊失败，原因：{}", responsePacket.getMessage());
        }
    }

    private void processJoinGroupResponse(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) {
        Integer fromUserId = responsePacket.getFromUserId();
        Integer groupId = responsePacket.getGroupId();
        String fromUserName = responsePacket.getFromUserName();
        if (responsePacket.isSuccess()) {
            Session session = SessionUtil.getSession(ctx.channel());
            if (session.getUserId() == fromUserId) {
                log.info("成功加入群聊：{}", groupId);
            } else {
                log.info("群聊{}，加入新用户：{}", groupId, fromUserName);
            }
        } else {
            log.info("加入群聊失败，原因：{}", responsePacket.getMessage());
        }
    }

    private void processLogoutResponse(LogoutResponsePacket responsePacket) {
        log.info("退出登录");
    }

    private void processLoginResponse(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {
        Integer userId = responsePacket.getUserId();
        String userName = responsePacket.getUserName();
        if (responsePacket.isSuccess()) {
            log.info("用户{}登录成功，用户id为：{}", userName, userId);
            SessionUtil.markAsLogin(ctx.channel());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            log.info(": 客户端登录失败，原因：" + responsePacket.getMessage());
        }
    }

    private void processCreateGroupResponse(CreateGroupResponsePacket responsePacket) {
        Integer groupId = responsePacket.getGroupId();
        List<String> userNameList = responsePacket.getUserNameList();
        log.info("群创建成功，id：{}，成员有：{}", groupId, userNameList);
    }
}
