package org.dante.vertx.helloworld.basicobj;

import org.junit.jupiter.api.Test;

import io.vertx.rxjava.core.Vertx;

public class VertxTestts {
	
	/**
	 * 每隔一秒发送一个事件的计时器
	 * @throws InterruptedException 
	 * 
	 */
	@Test
	public void testPeriodic() throws InterruptedException {
		Vertx vertx = Vertx.vertx();
		long timerId = vertx.setPeriodic(1000L, id -> {
			// This handler will get called every second
			  // 这个处理器将会每隔一秒被调用一次
			  System.out.println("timer fired!");
		});
		System.out.println(timerId);
		Thread.sleep(5000L);
	}
	
}
