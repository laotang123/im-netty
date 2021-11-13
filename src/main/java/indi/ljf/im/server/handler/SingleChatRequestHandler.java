package indi.ljf.im.server.handler;

import indi.ljf.im.protocol.Session;
import indi.ljf.im.protocol.request.MessageRequestPacket;
import indi.ljf.im.protocol.response.MessageResponsePacket;
import indi.ljf.im.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: ljf
 * @date: 2021/10/24 15:24
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class SingleChatRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    /**
     * 服务端只起到转发的作用
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket requestPacket) throws Exception {
        //消息发送发会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        //转发
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());
        responsePacket.setMessage(requestPacket.getMessage());

        //接收方channel
        Channel toUserChannel = SessionUtil.getChannel(requestPacket.getToUserId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(responsePacket);
        } else {
            log.error("[{}]用户不在线，发送失败", requestPacket.getToUserId());
        }
    }
}
