package github.com.yadavsudhir405.forkjoin;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class UppercaseRecursionTask extends RecursiveTask<String> {
    private static final int MAX_LENGTH = 4;

    private final String inputString;

    public UppercaseRecursionTask(String inputString) {
        this.inputString = inputString;
    }

    @Override
    protected String compute() {
        if(this.inputString.length() <= MAX_LENGTH) {
            return performTask();
        }
//        System.out.println("Divided task to smaller task by "+ Thread.currentThread().getName());
        int mid = this.inputString.length()/2;
        int length = this.inputString.length();
        final UppercaseRecursionTask uppercaseRecursionTask1 =
                new UppercaseRecursionTask(this.inputString.substring(0, mid));
        final UppercaseRecursionTask uppercaseRecursionTask2 =
                new UppercaseRecursionTask(this.inputString.substring(mid, length));
//        System.out.println(Thread.currentThread().getName()+" *************************************");

        /*
        uppercaseRecursionTask1.fork();
        uppercaseRecursionTask2.fork();
        return uppercaseRecursionTask1.join() + uppercaseRecursionTask2.join();
        */
        // Below varient calling compute and forking other piece seems to be more performant
        uppercaseRecursionTask2.fork();
        return uppercaseRecursionTask1.compute() + uppercaseRecursionTask2.join();
    }

    private String performTask() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("uppercase done by "+Thread.currentThread().getName());
        return this.inputString.toUpperCase();
    }
}
