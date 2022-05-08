package ch5.item31;

import java.util.concurrent.Future;

public interface ScheduledFuture <V> extends Delayed, Future<V> {
}
