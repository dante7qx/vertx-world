package org.dante.vertx.redis.verticle.basic;

import cn.hutool.core.lang.Console;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;

/**
 * HelloWorld - 第一个 Vert.x 示例
 * 
 * @author dante
 *
 */
public class HelloVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startFuture) throws Exception {
		Console.log("================================= HTTP 服务端 ====================================");

		Context context = parseContext();
		Console.log(Thread.currentThread().getName() +
                ": Start : " +
                Thread.currentThread().getId()
                + ", DeployId: " + this.deploymentID());
		
		vertx.createHttpServer().requestHandler(req -> {
			req.response()
				.putHeader("content-type", "text/plain; charset=utf-8")
				.end("来自 Vert.x 4 的问候!");
		}).listen(8888, http -> {
			if (http.succeeded()) {
				Console.log(context.get("data").toString());
				startFuture.complete();
				Console.log("HTTP server started on port 8888");
			} else {
				startFuture.fail(http.cause());
			}
		});
	}

	@Override
	public void stop(Promise<Void> stopFuture) throws Exception {
		System.out.println(Thread.currentThread().getName() +
                ": Stop : " +
                Thread.currentThread().getId()
                + ", DeployId: " + this.deploymentID());
		System.out.println("HTTP server stopped");
		super.stop(stopFuture);
	}
	
	/**
	 * 通常一个上下文实例就是一个Event Loop的Context实例，它会指定一个Event Loop线程与Verticle实例关联，
	 * 所以和这个Context实例相关的所有事件的执行通常都是在相同的Event Loop线程中——但是：Vert.x中的Context是有类型概念的，
	 * 当您使用的Verticle实例是一个Worker或Multi-Thread Worker时，这个时候的Context就不应该和Event Loop关联了，
	 * 而是直接和Worker Pool中的线程关联
	 * 
	 */
	private Context parseContext() {
		Context context = vertx.getOrCreateContext();
		if (context.isEventLoopContext()) {
		    System.out.println("Context attached to Event Loop");
		} else if (context.isWorkerContext()) {
			System.out.println("Context attached to Worker Thread");
		} else if (! Context.isOnVertxThread()) {
		    System.out.println("Context not attached to a thread managed by vert.x");
		}
		context.put("data", "Hello Vert.x 上下文");
		return context;
	}

}
