package org.dante.vertx.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.redis.client.Redis;

/**
 * Vertx-redis 示例
 * 
 * @author dante
 *
 */
public class MainVerticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {

		DeploymentOptions options = new DeploymentOptions().setInstances(4);

		vertx.deployVerticle("org.dante.vertx.redis.verticle.AcceptorVerticle", options);
	}

}
