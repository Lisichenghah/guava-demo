package top.lisicheng.thread;

import com.google.common.util.concurrent.FutureCallback;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * 手写的工具类
 */
public class FutureCallbacks {

    public static  <V> FutureCallback<V> create(Consumer<V> successConsumer, Consumer<Throwable> failureConsumer) {
        return new FutureCallback<V>() {
            @Override
            public void onSuccess(@Nullable V result) {
                successConsumer.accept(result);
            }

            @Override
            public void onFailure(Throwable t) {
                failureConsumer.accept(t);
            }
        };
    }


}
