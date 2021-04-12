package github.com.yadavsudhir405.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinRunner {
    public static void testForkJoinTask() {
        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        final String inputString = "abcdefghijklmnopqrstuvwxyz | abcdefghijklmnopqrstuvwxyz | abcdefghijklmnopqrstuvwxyz" +
                " | abcdefghijklmnopqrstuvwxyz | abcdefghijklmnopqrstuvwxyz";
        long startTime = System.currentTimeMillis();
        final ForkJoinTask<String> submit = forkJoinPool.submit(new UppercaseRecursionTask(inputString));
        final String result = submit.join();
        final long endTime = System.currentTimeMillis();
        System.out.println("Total time taken to process "+ (endTime-startTime));
    }
}
