package platform.utils;

import com.github.benmanes.caffeine.cache.AsyncCache;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class GenerateUtils {

    public Object random(Exchange exchange, Random random, AsyncCache<Integer, Object> cache) {
        List<?> data = exchange.getMessage().getBody(List.class);

        int index;
        do {
            index = random.nextInt(data.size());
        } while (cache.getIfPresent(index) != null);
        cache.put(index, CompletableFuture.completedFuture(index));

        return data.get(index);
    }

    public <T> T random(List<T> data, Random random) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        int index = random.nextInt(data.size());
        return data.get(index);
    }

}
