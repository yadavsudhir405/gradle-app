**similarity and differences between java streams and reactive streams.**

`Similarity`

| Java-8 streams          |          Java reactive streams |
|-----------------------|------------------------------|
| pipeline of functions   | pipeline of functions |
| lazy                    | lazy |
| push                    | push |
| data only               | data also |

`Differences`

| Java-8 streams           |       Java reactive streams |
|-------------------------|----------------------------|
|Not good at error handling | deal with it easily downstream |
|only one channel(data) present | data, error and complete channels are present |
| no forking i.e data can flowen only in on directions | forking is allowed multiple subscribers can consume the same data |
| push at will | backpressure |
| sequential and parallel | sync and async|

**Reactive streams API**

* Publisher: emits data.
* Subscriber: handles emitted data, error and completion.
* Subscription: kind of different session to same publisher.
* Processors: acts as both publisher and subscriber. This acts as subscriber
to upstream channel and publisher to downstream channel.
  
```
Flowable.interval(2, TimeUnit.SECONDS)
  .filter()
  .map(treansform())
  .subscribe(data -> sout(data), error -> handle Error, () -> sout(complete))
```
