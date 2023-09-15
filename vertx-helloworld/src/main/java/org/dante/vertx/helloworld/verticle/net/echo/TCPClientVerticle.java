package org.dante.vertx.helloworld.verticle.net.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetSocket;

public class TCPClientVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		vertx.createNetClient().connect(4321, "localhost", res -> {

			if (res.succeeded()) {
				NetSocket socket = res.result();
				socket.handler(buffer -> {
					System.out.println("Net client receiving: " + buffer.toString("UTF-8"));
				});

				// Now send some data
				for (int i = 0; i < 10; i++) {
					String str = "hello " + i + "\n";
					System.out.println("Net client sending: " + str);
					socket.write(str);
				}
			} else {
				System.out.println("Failed to connect " + res.cause());
			}
		});
	}

}
