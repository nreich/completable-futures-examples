package com.github.nreich.futures;

import java.util.concurrent.*;
import java.util.function.Function;

public class ExecutorControl {
  public static void main(String... args) throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    Function<Integer, Integer> addOne = (i) -> {
      System.out.println(Thread.currentThread().getName());
      return i + 1;
    };

    System.out.println("With executor");
    Future<Integer> withExecutor = CompletableFuture.supplyAsync(() -> addOne.apply(0), executor)
        .thenApplyAsync(addOne, executor).thenApplyAsync(addOne, executor)
        .thenApplyAsync(addOne, executor);
    withExecutor.get();

    System.out.println("With default fork join pool");
    Future<Integer> withDefaultPool = CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync(addOne).thenApplyAsync(addOne)
        .thenApplyAsync(addOne);
    withDefaultPool.get();
  }
}
