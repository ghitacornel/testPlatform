package product.common;

import com.github.javafaker.Faker;

import java.util.Random;

public abstract class AbstractActor {

    protected final Faker faker = Faker.instance();
    protected final Random random = new Random();

}
