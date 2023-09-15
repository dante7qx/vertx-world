package org.dante.vertx.helloworld.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;

public class DeployVerticle extends AbstractVerticle {
	
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		
		DeploymentOptions options = new DeploymentOptions().setInstances(4);
		
		vertx.deployVerticle("org.dante.vertx.helloworld.verticle.AcceptorVerticle", options);
		vertx.deployVerticle("org.dante.vertx.helloworld.verticle.WorkerVerticle", options);
	}
}
