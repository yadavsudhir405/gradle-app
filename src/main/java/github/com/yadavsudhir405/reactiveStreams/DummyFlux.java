package github.com.yadavsudhir405.reactiveStreams;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public class DummyFlux {
   public Flux<Integer> tenToZero() {
       return Flux.range(0,11)
               .map(i -> 10 - i);
   }

   public Flux<Integer> operateOnTenToZero(Function<Integer, Integer> operation) {
       return this.tenToZero()
               .map(operation);
   }

   public Flux<String> namesPerSecond() {
       final List<String> randomizedNames = Arrays.
               asList("foo", "bar", "Jazz", "bazz", "kazz", "kate");
       Collections.reverse(randomizedNames);

       return Flux.fromIterable(randomizedNames)
               .delayElements(Duration.ofSeconds(1));
   }
}
