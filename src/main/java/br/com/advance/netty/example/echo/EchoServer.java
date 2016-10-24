/**
 * @(#)EchoServer.java
 */
package br.com.advance.netty.example.echo;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * Echo server.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class EchoServer {

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
					return Channels.pipeline(new EchoServerHandler());
				}
			});

			// Sets accepted Channels options
			serverBootstrap.setOption("child.tcpNoDelay", true);
			serverBootstrap.setOption("child.keepAlive", true);

			// Binds to the port 8080 of all NICs (network interface cards) in
			// the machine
			serverBootstrap.bind(new InetSocketAddress(8080));
		} catch (Exception e) {
			// Debug
			e.printStackTrace();
		}
	}

}
