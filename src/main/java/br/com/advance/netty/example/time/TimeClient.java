/**
 * @(#)TimeClient.java
 */
package br.com.advance.netty.example.time;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * Time Client.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class TimeClient {
	
	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		try {
			// Creates the channel factory
			final ChannelFactory channelFactory = new NioClientSocketChannelFactory(
					Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool());

			// Sets up a client
			final ClientBootstrap clientBootstrap = new ClientBootstrap(
					channelFactory);

			// Sets the pipeline
			clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
				public ChannelPipeline getPipeline() {
					return Channels.pipeline(new TimeDecoder(),
							new TimeClientHandler());
				}
			});

			// Sets the Channel options
			clientBootstrap.setOption("tcpNoDelay", true);
			clientBootstrap.setOption("keepAlive", true);

			// Opens the connection
			final ChannelFuture future = clientBootstrap.connect(
					new InetSocketAddress("localhost", 8080));
			future.awaitUninterruptibly();
			if (!future.isSuccess()) {
				future.getCause().printStackTrace();
			}

			future.getChannel().getCloseFuture().awaitUninterruptibly();
			channelFactory.releaseExternalResources();
		} catch (Exception e) {
			// Debug
			e.printStackTrace();
		}
	}

}
