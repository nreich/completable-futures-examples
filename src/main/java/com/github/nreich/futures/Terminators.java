package com.github.nreich.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

public class Terminators {

  public static void main(String... args) throws ExecutionException, InterruptedException {
    // thenAcceptAsync
    Function<Integer, Integer> addOne = (i) -> i + 1;
    Consumer<Integer> integerConsumer = (i) -> System.out.println("Total is: " + i);

    Future<Void> done = CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync((i) -> addOne.apply(i)).thenApplyAsync((i) -> addOne.apply(i))
        .thenAcceptAsync(integerConsumer);

    done.get();
    System.out.println("Done!");

    // thenRunAsync
    CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync((i) -> addOne.apply(i)).thenApplyAsync((i) -> addOne.apply(i))
        .thenRunAsync(() -> System.out.println("Done running!"));
  }
}
