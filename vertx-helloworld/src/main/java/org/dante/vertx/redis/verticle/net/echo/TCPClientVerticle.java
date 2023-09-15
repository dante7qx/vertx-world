package org.dante.vertx.redis.verticle.net.echo;

import cn.hutool.core.lang.Console;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetSocket;

public class TCPClientVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		Console.log("================================= TCP Client ====================================");
		vertx.createNetClient().connect(4321, "localhost", res -> {

			if (res.succeeded()) {
				NetSocket socket = res.result();
				socket.handler(buffer -> {
					Console.log("Net client receiving: " + buffer.toString("UTF-8"));
				});

				// Now send some data
				for (int i = 0; i < 10; i++) {
					String str = "hello " + i + "\n";
					Console.log("Net client sending: " + str);
					socket.write(str);
				}
			} else {
				Console.log("Failed to connect " + res.cause());
			}
		});
	}

}
