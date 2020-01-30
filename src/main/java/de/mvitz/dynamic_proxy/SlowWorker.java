package de.mvitz.dynamic_proxy;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SlowWorker implements Worker {

    private final Random random = new Random();

    @Override
    public int doWork(String input) {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {}
        return 42 * input.length();
    }
}
