package platform.utils;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import java.util.List;
import java.util.Random;

@UtilityClass
public class GenerateUtils {

    public Object random(Exchange exchange, Random random, Cache<Integer, ?> cache) {
        List<?> data = exchange.getMessage().getBody(List.class);

        int index;
        do {
            index = random.nextInt(data.size());
        } while (cache.getIfPresent(index) != null);

        return data.get(index);
    }

}
