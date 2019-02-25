import io.vertx.core.Vertx;
import verticle.DatabaseVerticle;
import verticle.HttpServerVerticle;

/**
 * Created by f4le0wn on 2/25/19.
 */
public class Application {

    public static void main(String args[]) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpServerVerticle());
        vertx.deployVerticle(new DatabaseVerticle());
    }
}
