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
        final List<String> words = List.of("the", "quick", "brown", "fox", "jumpes", "over", "the", "lazy", "dog");
        Flux.fromIterable(words)
                .flatMap(word -> Flux.just(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, 100), (word, line) -> line +". "+ word)
                .subscribe(System.out::println);
    }
}
