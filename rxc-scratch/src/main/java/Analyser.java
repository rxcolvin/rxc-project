import java.util.*;
import java.util.function.*;

/**
 * Created by richard on 12/02/2016.
 */
public class Analyser {

  public static void main(String[] args) {

    Calculation<Record, Long> countAll = new Calculation<>(
        new Key("CountAll"),
        it -> true,
        (Long d, Record r) -> d = d + 1,
        () -> 0L,
        Long.class
    );


    TimeBucketData timeBucketData = new TimeBucketData();

    List<Calculation<Record, ?>> calcs = new ArrayList<>();
    calcs.add(countAll);

    RecordConsumer recordConsumer = new RecordConsumer(timeBucketData, calcs);
  }


  public static class Key {

    private final Object[] comps;

    public Key(Object... comps) {

      this.comps = comps;
    }

    @Override public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Key key = (Key) o;

      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      return Arrays.equals(comps, key.comps);

    }

    @Override public int hashCode() {
      return Arrays.hashCode(comps);
    }
  }

  public static class TimeBucketDataStore {
    private final long bucketSize;
    private final Map<Long, TimeBucketData> buckets = new HashMap<>();

    public TimeBucketDataStore(long bucketSize) {
      this.bucketSize = bucketSize;
    }

    public TimeBucketData getTimeBucketDatFor(long time) {
      return buckets.getOrDefault(time % bucketSize, new TimeBucketData()); //Obviously shit HACK for now

    }
  }

  public static class TimeBucketData {
    private final Map<Key, Object> elements = new HashMap<>();

    public TimeBucketData() {
    }


    public <D> D getElement(Key key, Supplier<D> create, Class<D> type) {
      return (D) elements.computeIfAbsent(key, key1 -> create.get());
    }

    public <D> void setElement(Key key, D element) {
      elements.put(key, element);
    }

  }

  public static class Record {
    public final long timestamp;

    public Record(long timestamp) {
      this.timestamp = timestamp;
    }
  }


  public static class RecordConsumer
      implements Consumer<Record> {
    private final TimeBucketDataStore          timeBucketDataStore;
    private final List<Calculation<Record, ?>> calculations;


    public RecordConsumer(
        final TimeBucketDataStore timeBucketDataStore,
        final List<Calculation<Record, ?>> calculations
    ) {
      this.timeBucketDataStore = timeBucketDataStore;
      this.calculations = calculations;
    }

    @Override public void accept(Record record) {
      TimeBucketData timeBucketData = timeBucketDataStore.getTimeBucketDatFor(record.timestamp);
      calculations.forEach(it -> {

        if (it.test.test(record)) {
          Object element = timeBucketData.getElement(it.key, it.create, it.dataType);
          timeBucketData.setElement(it.key, it.calc.apply(element, record));
        }

      });

    }
  }

  public static class Calculation<R, D> {
    private final Key                 key;
    private final Predicate<R>        test;
    private final BiFunction<D, R, D> calc;
    public final  Supplier<D>         create;
    private final Class<D>            dataType;

    public Calculation(
        Key key, Predicate<R> test,
        BiFunction<D, R, D> calc,
        Supplier<D> create,
        Class<D> dataType) {
      this.key = key;
      this.test = test;
      this.calc = calc;
      this.create = create;
      this.dataType = dataType;
    }
  }

  public static class CaseFunction<T, R> implements Function<T, R> {
    private final List<Function<T, Optional<R>>> functions;

    public CaseFunction(List<Function<T, Optional<R>>> functions) {
      this.functions = functions;
    }

    @Override public R apply(T t) {
      return functions.stream()
                      .map(it -> it.apply(t))
                      .filter(Optional::isPresent)
                      .findFirst()
                      .orElseGet(this::exception)
                      .get();
    }

    private Optional<R> exception() {
      throw new RuntimeException();
    }
  }


  public static interface PartialFunction<T, R> extends Function<T, R> {
    boolean isDefinedAt(T t);
  }

}
