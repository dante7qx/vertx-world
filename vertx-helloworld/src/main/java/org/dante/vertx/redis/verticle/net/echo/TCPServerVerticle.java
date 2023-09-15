package org.dante.vertx.redis.verticle.net.echo;

import cn.hutool.core.lang.Console;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class TCPServerVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		Console.log("================================= TCP Server ====================================");
		NetServerOptions options = new NetServerOptions().setPort(4321);
		NetServer server = vertx.createNetServer(options);
		server.connectHandler(socket -> {
			socket.handler(buffer -> {
				Console.log("TCP Server received some bytes: {}, length: {}", buffer.toString() ,buffer.length());
			});
		}).listen(res -> {
			if (res.succeeded()) {
				Console.log("TCP Server is now listening on actual port: " + server.actualPort());
			} else {
				Console.log("Failed to bind!");
			}
		});
	}

}
