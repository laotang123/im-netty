package indi.ljf.im.utils;

import indi.ljf.im.constants.Attributes;
import indi.ljf.im.protocol.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ljf
 * @date: 2021/10/23 23:19
 * @description: 服务器只是做到消息转发的作用
 * channel -> userId 使用channel.attr获取
 * userId -> channel 使用map映射来获取
 * @modified By:
 * @version: $ 1.0
 */
public class SessionUtil {
    private static final Map<Integer, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<Integer, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

    public static Integer randomId() {
        Random random = new Random();
        int id = random.nextInt();
        return id < 0 ? -id : id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(randomId());
        }
    }

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(Integer userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(Integer groupId, DefaultChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(Integer groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }
}
