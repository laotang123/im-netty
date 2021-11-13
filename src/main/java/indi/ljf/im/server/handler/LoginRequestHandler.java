package indi.ljf.im.server.handler;

import indi.ljf.im.common.codec.PacketCodeC;
import indi.ljf.im.protocol.Session;
import indi.ljf.im.protocol.request.LoginRequestPacket;
import indi.ljf.im.protocol.response.LoginResponsePacket;
import indi.ljf.im.utils.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ljf
 * @date: 2021/10/24 14:51
 * @description:
 * @modified By:
 * @version: $ 1.0
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    private static final Map<String, String> userMap = new HashMap<>();

    static {
        userMap.put("ljf", "0000");
        userMap.put("system", "0000");
        userMap.put("ld", "0000");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket responsePacket = login(loginRequestPacket);
        if (responsePacket.isSuccess()) {
            //服务端到客户端和客户端到服务端是两个channel
            SessionUtil.markAsLogin(ctx.channel());
            SessionUtil.bindSession(new Session(responsePacket.getUserId(), loginRequestPacket.getUserName()), ctx.channel());
        }
        ByteBuf byteBuf = PacketCodeC.getInstance().encode(ctx.alloc(), responsePacket);
        ctx.channel().writeAndFlush(byteBuf);
    }

    private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserId(SessionUtil.randomId());
            loginResponsePacket.setUserName(loginRequestPacket.getUserName());
            log.info("{}登录成功", loginRequestPacket.getUserName());
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setMessage("账号密码校验失败，请重新输入：");
            log.info("{}登录失败", loginRequestPacket.getUserName());
        }
        return loginResponsePacket;
    }

    private boolean valid(LoginRequestPacket packet) {
        //TODO 访问数据库，校验账号和密码
        return userMap.getOrDefault(packet.getUserName(), "").equals(packet.getPassword());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
