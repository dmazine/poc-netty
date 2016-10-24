/**
 * @(#)TimeEncoder.java
 */
package br.com.advance.netty.example.time;

import static org.jboss.netty.buffer.ChannelBuffers.buffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * TimeEncoder ChannelHandler implementation.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class TimeEncoder extends SimpleChannelHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelHandler#writeRequested(org.jboss
	 * .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		final UnixTime time = (UnixTime) e.getMessage();

		final ChannelBuffer channelBuffer = buffer(4);
		channelBuffer.writeInt(time.getValue());

		Channels.write(ctx, e.getFuture(), channelBuffer);
	}

}
