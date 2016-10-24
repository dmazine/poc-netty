/**
 * @(#)TimeServer.java
 */
package br.com.advance.netty.example.time;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * Time Server.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class TimeServer {

	/** */
	static final ChannelGroup allChannels = new DefaultChannelGroup(
			"time-server");

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		try {
			// Creates the channel factory
			final ChannelFactory channelFactory = new NioServerSocketChannelFactory(
					Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());

			// Sets up a server
			final ServerBootstrap serverBootstrap = new ServerBootstrap(
					channelFactory);

			// Sets the pipeline
			serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				public ChannelPipeline getPipeline() {
					return Channels.pipeline(new TimeServerHandler(),
							new TimeEncoder());
				}
			});

			// Sets accepted Channels options
			serverBootstrap.setOption("child.tcpNoDelay", true);
			serverBootstrap.setOption("child.keepAlive", true);

			// Binds to the port 8080 of all NICs (network interface cards) in
			// the machine
			final Channel channel = serverBootstrap.bind(new InetSocketAddress(8080));
			allChannels.add(channel);
			
			// Waits for shutdown command
			Thread.sleep(5000);

			//
			ChannelGroupFuture channelGroupFuture = allChannels.close();
			channelGroupFuture.awaitUninterruptibly();
			
			//
			channelFactory.releaseExternalResources();
		} catch (Exception e) {
			// Debug
			e.printStackTrace();
		}
	}

}
