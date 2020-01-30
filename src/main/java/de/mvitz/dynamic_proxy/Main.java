package de.mvitz.dynamic_proxy;

public class Main {

    public static void main(String[] args) {
        // without proxy
        Worker worker = new SlowWorker();
        int result = worker.doWork(args[0]);
        System.out.println(result);

        // with specific proxy
        worker = new ProxyWorker(new SlowWorker());
        result = worker.doWork(args[0]);
        System.out.println("result=" + result);

        // with dynamic proxy
        worker = TimingProxy.time(new SlowWorker());
        result = worker.doWork("Michael");
        System.out.println("result=" + result);
    }
}
