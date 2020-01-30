package de.mvitz.dynamic_proxy;

public class ProxyWorker implements Worker {

    private final Worker subject;

    public ProxyWorker(Worker subject) {
        this.subject = subject;
    }

    @Override
    public int doWork(String input) {
        final long start = System.nanoTime();
        try {
            return subject.doWork(input);
        } finally {
            final long duration = System.nanoTime() - start;
            System.out.println("doWork took: " + duration + "ns");
        }
    }
}
