package indi.ljf.im.server.handler;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.jmx.JmxReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ljf
 * @date: 2021/11/3 14:03
 * @description: 统计指标
 * @modified By:
 * @version: $ 1.0
 */
@ChannelHandler.Sharable
public class MetricHandler extends ChannelDuplexHandler {
    private final AtomicLong totalConnectionNum = new AtomicLong();

    {
        MetricRegistry metricRegistry = new MetricRegistry();
        metricRegistry.register("im-netty-totalConnectionNum", (Gauge<Long>) totalConnectionNum::longValue);
        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry).build();
        consoleReporter.start(10, TimeUnit.SECONDS);

        //jmx查看指标
        JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
        jmxReporter.start();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNum.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        totalConnectionNum.incrementAndGet();
        super.channelInactive(ctx);
    }
}
