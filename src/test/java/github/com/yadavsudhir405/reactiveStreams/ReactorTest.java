package github.com.yadavsudhir405.reactiveStreams;

import org.junit.After;
import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static reactor.core.publisher.Flux.fromIterable;

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
        fromIterable(words)
                .flatMap(word -> Flux.just(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, 100), (word, line) -> line + ". " + word)
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
        Flux<String> feed = Flux.interval(Duration.ofSeconds(1)).map(tick -> LocalTime.now().toString());

        // emits stream whenever clock emits stream and gets combined with latest from feed
        clock.withLatestFrom(feed, (tick, time) -> tick + " " + time)
                .subscribe(System.out::println);
    }

    @Test
    public void test4() throws InterruptedException {
        // Cold Flux, each subscription receives whole set of data, irrespective subscription time
       /* final Flux<String> citisFlux = fromIterable(asList("New Delhi", "Mumbai", "Pune", "Bangalore"));
        citisFlux.subscribe(city -> System.out.println("First " + city));
        citisFlux.subscribe(city -> System.out.println("Second " + city));
*/
        // Hot Flux, subscribers might miss few data
        /*final AtomicInteger atomicInteger = new AtomicInteger();
        final Flux<Object> generate = Flux.generate(synchronousSink -> synchronousSink.next(atomicInteger.incrementAndGet()));
        generate.take(4).subscribe(data -> System.out.println("First " + data));
        generate.take(4).subscribe(data -> System.out.println("Second " + data));*/

        // convert Cold flux to Hot flux
        final Flux<String> citisFlux = fromIterable(asList("New Delhi", "Mumbai", "Pune", "Bangalore"));

        final ConnectableFlux<String> connectableFlux = citisFlux.delayElements(Duration.ofMillis(500)).publish();

        connectableFlux.subscribe(data -> System.out.println("First "+ data));
        connectableFlux.connect();

        TimeUnit.SECONDS.sleep(1);

        connectableFlux.subscribe(data -> System.out.println("Second "+ data));

    }

    @After
    public void tearDown() throws Exception {
        TimeUnit.SECONDS.sleep(5);
    }

    public static boolean isFastTime() {
        if (count.get() > 0) {
            count.set(count.get() - 1);
            return true;
        }
        return false;
    }

    public static boolean isSlowTime() {
        return count.get() <= 0;
    }
}
