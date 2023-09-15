package org.dante.vertx.redis.verticle.eventbus;

import cn.hutool.core.lang.Console;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class WorkerVerticle extends AbstractVerticle {

	@Override
	public void start() {
		Console.log(Thread.currentThread().getName() + ", Start Worker...");
		final EventBus event = this.vertx.eventBus();
		// 接收消息
		event.<JsonObject>consumer("MSG://EVENT/BUS", reply -> {
			Console.log(Thread.currentThread().getName() + ", 消费服务端消息...");
			// 提取接收消息
            final JsonObject message = reply.body();
			Console.log(" Message: " + message.encode());
            // 回复消息
            reply.reply(new JsonObject().put("worker", "Worker 发出的消息"));
		});
	}
	
}
