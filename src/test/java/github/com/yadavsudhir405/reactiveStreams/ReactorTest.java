package github.com.yadavsudhir405.reactiveStreams;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class ReactorTest {
    @Test
    public void test1() {
        Flux.just("Hello", "World")
                .subscribe(System.out::println);
    }
}
