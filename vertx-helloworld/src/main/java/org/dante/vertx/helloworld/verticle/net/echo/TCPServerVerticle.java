package org.dante.vertx.helloworld.verticle.net.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TCPServerVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);
		server.connectHandler(socket -> {
			socket.handler(buffer -> {
				System.out.println("I received some bytes: " + buffer.length());
			});
		}).listen(res -> {
			if (res.succeeded()) {
				System.out.println("Server is now listening on actual port: " + server.actualPort());
			} else {
				System.out.println("Failed to bind!");
			}
		});
	}

}
