package github.com.yadavsudhir405.reactiveStreams;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReactorTest {
    @Test
    public void test1() {
        Flux.just("Hello", "World")
                .subscribe(System.out::println);
    }

    @Test
    public void test2() {
        final List<String> words = List.of("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
        Flux.fromIterable(words)
                .zipWith(Flux.range(1, 100), (word, line) -> line +". "+ word)
                .subscribe(System.out::println);
    }
}
