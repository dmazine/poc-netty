/**
 * @(#)TimeClientHandler.java
 */
package br.com.advance.netty.example.time;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * TimeClient ChannelHandler implementation.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class TimeClientHandler extends SimpleChannelHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss
	 * .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		final UnixTime m = (UnixTime) e.getMessage();
		System.out.println(m);
		e.getChannel().close();
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
