package org.dante.vertx.redis.verticle;

import cn.hutool.core.lang.Console;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;

import java.util.Arrays;

public class RedisClientVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // If a config file is set, read the host and port.
        String host = Vertx.currentContext().config().getString("host");
        if (host == null) {
            host = RedisOptions.DEFAULT_ENDPOINT;
        }

        // Create the redis client
        Redis client = Redis.createClient(vertx, new RedisOptions().addConnectionString(host));
        RedisAPI redis = RedisAPI.api(client);

        client.connect()
                .compose(conn ->
                        redis.set(Arrays.asList("key", "value"))
                                .compose(v -> {
                                    Console.log("key stored");
                                    return redis.get("key");
                                }))
                .onSuccess(result -> {
                    Console.log("Retrieved value: " + result);
                })
                .onFailure(err -> {
                    Console.log("Connection or Operation Failed " + err);
                });
    }

    public static void main(String[] args) {
        Launcher.executeCommand("run", RedisClientVerticle.class.getName());
    }
}
