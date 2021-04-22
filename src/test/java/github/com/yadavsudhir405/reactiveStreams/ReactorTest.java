package github.com.yadavsudhir405.reactiveStreams;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ReactorTest {
    private static AtomicInteger count = new AtomicInteger(3);

    @Test
    public void test1() {
        Flux.just("Hello", "World")
                .subscribe(System.out::println);
    }

    @Test
    public void test2() {
        final List<String> words = List.of("the", "quick", "brown", "fox", "jumpes", "over", "the", "lazy", "dog");
        Flux.fromIterable(words)
                .flatMap(word -> Flux.just(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, 100), (word, line) -> line +". "+ word)
                .subscribe(System.out::println);
    }

    @Test
    public void test3() {
        Flux<String> fastClock = Flux.interval(Duration.ofSeconds(1)).map(tick -> "fast" + tick);
        Flux<String> slowClock = Flux.interval(Duration.ofSeconds(2)).map(tick -> "slow" + tick);

        Flux<String> clock = Flux.merge(
                fastClock.filter(tick -> isFastTime()),
                slowClock.filter(tick -> isSlowTime())
        );

        // ticks every second
        Flux<String > feed = Flux.interval(Duration.ofSeconds(1)).map(tick -> LocalTime.now().toString());

        // emits stream whenever clock emits stream and gets combined with latest from feed
        clock.withLatestFrom(feed, (tick, time) -> tick + " "+ time)
                .subscribe(System.out::println);
    }

    @After
    public void tearDown() throws Exception {
        TimeUnit.SECONDS.sleep(30);
    }

    public static boolean isFastTime() {
        if(count.get() > 0){
            count.set(count.get() - 1);
            return true;
        }
        return false;
    }

    public static boolean isSlowTime() {
        return count.get() <= 0;
    }
}
