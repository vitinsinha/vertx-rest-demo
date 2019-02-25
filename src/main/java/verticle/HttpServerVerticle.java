package verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import model.Product;

/**
 * Created by f4le0wn on 2/25/19.
 */
public class HttpServerVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Override
    public void start(Future<Void> future) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/products/*").handler(this::handleRequest);

        vertx.createHttpServer().requestHandler(router).listen(8081);
    }

    private void handleRequest(RoutingContext routingContext) {
        vertx.eventBus().send("DatabaseService", routingContext.request().path(), response -> {
            if (response.succeeded()) {
                routingContext.response()
                        .putHeader("content-type", "application/json")
                        .end(Json.encodePrettily(response.result().body()));
            } else {
                logger.info("Can't send message to Database service");
                routingContext.response().setStatusCode(500).end(response.cause().getMessage());
            }
        });
    }

}
