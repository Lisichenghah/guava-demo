## Guava学习笔记

### 并发

1. ListenableFuture

   > 什么是`ListenableFuture`？`ListenableFuture`是`guava`对==jdkFuture==的补充或者扩展

   ```java
   public interface ListenableFuture<V> extends Future<V>
   ```

   我们知道`Future`是异步结算任务结果，通过*get()*方法等待计算完成的结果。

   想要使用`ListtenableFuture`首先要构造一个能够返回ListtenableFuture的线程池

   ```java
   public interface ListeningExecutorService extends ExecutorService
   ```

   > 构建**ListeningExecutorService**

   ```java
   // 构建jdk线程池
   ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
   // 通过jdk线程构建guava线程池
   ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(fixedThreadPool);
   ```

   > 使用方式

   ```java
   // 提交任务，返回异步任务结果对象
   ListenableFuture<String> stringListenableFuture = listeningExecutorService.submit(() -> {
       System.out.println(Thread.currentThread().getName() + "执行任务");
       TimeUnit.SECONDS.sleep(2);
       return "result1";
   });
   ```

   添加回调方式1

   ```java
   
   // 对异步任务结果对象添加任务回调，并且需要设置回调任务在哪个线程池执行（第二参数）
   stringListenableFuture.addListener(() -> {
       System.out.println(Thread.currentThread().getName() + "任务回调");
   }, listeningExecutorService);
   ```

   添加回调方式2

   ```java
   // 通过Futures工具类添加回调
   Futures.addCallback(stringListenableFuture, 
                       // 回调任务实现类，onSuccess表示执行成功的回调，onFailure表示执行失败的回调
                       new FutureCallback<String>() {
       @Override
       public void onSuccess(@Nullable String result) {
           System.out.println(Thread.currentThread().getName() + "成功回调");
       }
   
       @Override
       public void onFailure(Throwable throwable) {
           System.out.println(Thread.currentThread().getName() + "失败回调." + throwable.getMessage());
       }
   },listeningExecutorService);
   ```

   两个设置回调方式的区别：

   1. 方式1为`ListenableFuture`的实例方法，而方式2是使用的工具类

   2. 从两者回调任务的参数也能看出来，方式1获取不到任务结果值，而方式2可以

      

2. Futures

   > 什么是`Futures`？`Futures`是`guava`对象异步任务对象的工具类

   常用方法

   - ​	addCallback

     > 为`ListenableFuture`添加回调

     ```java
     <V> void addCallback(
         final ListenableFuture<V> future,
         final FutureCallback<? super V> callback,
         Executor executor)
     ```

     

   - ​	allAsList

     > 对多个`ListenableFuture`的合并，返回一个当所有`Future`成功时返回多个`Future`返回值的List对象。当其中任意一个Future失败或者取消的时候，将会立即进入失败或取消。

     ```java
     ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures)
     // 重载方法
     ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures)
     ```

      

   - ​	successfulAsList

     > 和allAsList相似，区别在于当`Future`失败或取消的时候是返回null值，不会立即进入失败或取消状态。另外当提交的任务没有返回值的时候也是返回null

     ```java
     ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures)
     // 重载方法
     ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures)
     ```

     

   - ​	transform

     > 根据`ListenableFuture`返回的结果计算。与*allCallback*一样，也是在`ListenableFuture`计算完成之后才会执行，不同的是该方法还会继续返回`ListenableFuture`对象，而*addCallback*是纯粹回调

     ```java
     <I, O> ListenableFuture<O> transform(
           ListenableFuture<I> input, Function<? super I, ? extends O> function, Executor executor)
     ```

     

   -    submit

     > 提交一个任务，返回`ListenableFuture`对象。这样实现快速创建`ListenableFuture`，而不需要构建`ListeningExecutorService`来创建`ListenableFuture`

     ```java
     <O> ListenableFuture<O> submit(Callable<O> callable, Executor executor)
     // 重载方法
     ListenableFuture<Void> submit(Runnable runnable, Executor executor)
     ```

     

   - immediateFuture、immediateFailedFuture 、immediateCancelledFuture、immediateVoidFuture

     > 快速返回ListenableFuture（非异步）

     ```java
     // 通过值构建
     <V> ListenableFuture<V> immediateFuture(@Nullable V value)
     // 通过异常构建
     <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable)
     // 构建取消状态ListenableFuture
     <V> ListenableFuture<V> immediateCancelledFuture()
     // 构建空值ListenableFuture
     ListenableFuture<Void> immediateVoidFuture()
     ```

     
     
   - JdkFutureAdapters.listenInPoolThread(future)
   
     > `guava`同时提供了将==JDK Future==转换为`ListenableFuture`的接口函数
   
     ```java
     Future<Integer> future = Executors.newFixedThreadPool(1).submit(() -> {
                 return 1;
             });
     
     // 将future转换为ListenableFuture
     ListenableFuture<Integer> integerListenableFuture = JdkFutureAdapters.listenInPoolThread(future);
     ```





### 集合

#### 	容器

##### 			1.List

##### 			2.Map

##### 			3.Set

#### 	工具类

### IO

### 缓存

### 事件

### 函数式

### 基础/常用