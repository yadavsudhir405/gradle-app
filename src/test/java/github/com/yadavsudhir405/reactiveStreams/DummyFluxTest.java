package github.com.yadavsudhir405.reactiveStreams;


import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.Duration;

class DummyFluxTest {
    private final DummyFlux dummyFlux = new DummyFlux();

    @Test
    public void tenToZero() {
        StepVerifier.create(dummyFlux.tenToZero())
                    .expectNext(10)
                    .expectNext(9,8,7,6,5)
                    .expectNextCount(4)
                    .expectNext(0)
                    .expectComplete()
                    .verify();
    }

    @Test
    void applyFunctionOnTenToZero() {
        StepVerifier.create(dummyFlux.operateOnTenToZero(i -> i * 10))
                .expectSubscription()
                .expectNextCount(5)
                .expectNext(50)
                .expectNext(40, 30, 20, 10, 0)
                .verifyComplete();
    }

    @Test
    void namesPerSecond() {
        StepVerifier.withVirtualTime(() -> dummyFlux.namesPerSecond()
                                            .doOnNext(System.out::println))
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .expectNext("kate")
                .thenAwait(Duration.ofSeconds(2))
                .expectNextCount(2)
                .thenAwait(Duration.ofSeconds(2))
                .expectNextCount(2)
                .expectNext("foo")
                .verifyComplete();
    }
}