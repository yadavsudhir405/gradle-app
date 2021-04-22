package github.com.yadavsudhir405.reactiveStreams;

import reactor.core.publisher.Flux;

import java.util.concurrent.Flow;

public class ReactiveStreamRunner {
    public static void testReactiveStream() {
        Flux.just(10,11).subscribe(System.out::println);
    }
}
