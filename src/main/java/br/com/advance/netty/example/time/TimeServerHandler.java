/**
 * @(#)TimeServerHandler.java
 */
package br.com.advance.netty.example.time;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * TimeServer ChannelHandler implementation.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class TimeServerHandler extends SimpleChannelHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#channelOpen(org.jboss.netty
	 * .channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		TimeServer.allChannels.add(e.getChannel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#channelConnected(org.jboss
	 * .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		final UnixTime time = new UnixTime(
				(int) (System.currentTimeMillis() / 1000));
		final ChannelFuture channelFuture = e.getChannel().write(time);
		channelFuture.addListener(ChannelFutureListener.CLOSE);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss
	 * .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		// Debug
		e.getCause().printStackTrace();

		// Closes the channel
		final Channel channel = e.getChannel();
		channel.close();
	}
}
