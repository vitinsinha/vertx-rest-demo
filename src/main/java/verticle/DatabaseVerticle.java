package verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import model.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by f4le0wn on 2/25/19.
 */
public class DatabaseVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(DatabaseVerticle.class);

    @Override
    public void start() {
        vertx.eventBus().consumer("DatabaseService", this::dispatchMessage);
    }

    private void dispatchMessage(final Message<Object> message) {
        String requestUri = message.body().toString();
        if (requestUri.equals("/products")) {
            message.reply(getAllProducts());
        } else if (requestUri.matches("/products/\\d+")) {
            message.reply(getProductById(requestUri.replaceAll("\\D+","")));
        } else {
            logger.error("Unable to handle operation: " + requestUri);
            message.reply("Unsupported operation");
        }
    }

    public JsonArray getAllProducts() {
        JsonArray array = new JsonArray();
        products.forEach((k,v) -> array.add(new JsonObject(Json.encode(new Product(k,v)))));
        return array;
    }

    public Object getProductById(String id) {
        if (products.containsKey(id)) {
            return new JsonObject(Json.encode(new Product(id, products.get(id))));
        } else {
            return "Product not found";
        }
    }

    private static final Map<String,String> products = new HashMap<String, String>() {{
        put("1", "Product1");
        put("2", "Product2");
    }};
}
