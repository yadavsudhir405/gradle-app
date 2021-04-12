package github.com.yadavsudhir405.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class DummyCompletableFuture {
    public static void testCompletableFuture() {
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        cf
                .exceptionally(throwable -> {throw new RuntimeException("Somehitng went wrong");})
                .thenApply(value -> 2*value) // kind of map function
                .exceptionally(throwable -> 100) // if any exception thrown in upstream
                .thenAccept(val -> System.out.println("Value is "+val+ Thread.currentThread().getName()));
        cf.completeOnTimeout(100, 3, TimeUnit.SECONDS);
        sleepFor(6);
        cf.complete(12);
    }

    private static CompletableFuture<Integer> createCompletableFuture() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Creating completeable future->"+Thread.currentThread().getName());
            sleepFor(2);
            return 4;
        });
    }

    private static void sleepFor(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {

        }
    }

}
