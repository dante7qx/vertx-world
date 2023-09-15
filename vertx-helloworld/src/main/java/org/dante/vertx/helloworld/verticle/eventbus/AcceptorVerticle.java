package org.dante.vertx.helloworld.verticle.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;

public class AcceptorVerticle extends AbstractVerticle {

	@Override
	public void start() {
		final HttpServer server = this.vertx.createHttpServer();
		System.out.println(Thread.currentThread().getName() + ", Start Acceptor...");
		server.requestHandler(request -> {
			// 调用Event Bus
            final EventBus event = this.vertx.eventBus();
            System.out.println(Thread.currentThread().getName() + ", Accept Request...");
            event.<JsonObject>request("MSG://EVENT/BUS", 
    			new JsonObject().put("message", "事件通信。"),
    			reply -> {
    				if(reply.succeeded()) {
    					// 发送回客户端
                        System.out.println(Thread.currentThread().getName() + ", 收到客户端消息...");
                        System.out.println(" Message: " + reply.result().body());
                        request.response()
        					.putHeader("content-type", "text/plain;charset=utf-8")
                        	.end(reply.result().body().encode());
    				}
    			});
		});
		
		server.listen(8881);
	}
}
