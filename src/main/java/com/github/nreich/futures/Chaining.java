package com.github.nreich.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Chaining {
  public static void main(String... args) throws ExecutionException, InterruptedException {
    Function<Integer, Integer> addOne = (i) -> i + 1;

    // apply async
    CompletableFuture<Integer> total = CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync(addOne).thenApplyAsync(addOne);

    System.out.println("Total is: " + total.get());

    // compose async
    CompletableFuture<Integer> composeTotal = CompletableFuture.supplyAsync(
        () -> addOne.apply(0)).thenComposeAsync(Chaining::addOneAsyncMethod);
    System.out.println("Compose total is: " + composeTotal.get());

    // thenCombineAsync
    Future<Integer> combinedTotal = composeTotal.thenCombineAsync(total, (t1, t2) -> t1 + t2);
    System.out.println("CombinedTotal = " + combinedTotal.get());
  }

  private static CompletableFuture<Integer> addOneAsyncMethod(Integer i) {
    return CompletableFuture.supplyAsync(() -> i + 1);
  }
}
