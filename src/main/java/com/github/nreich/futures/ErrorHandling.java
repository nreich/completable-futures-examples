package com.github.nreich.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ErrorHandling {
  public static void main(String... args) throws ExecutionException, InterruptedException {

    // handleAsync
    Function<Integer, Integer> addOne = (i) -> i + 1;

    BiFunction<Integer, Throwable, String> errorHandler = (i, e) -> {
      if (e != null) {
        return "Went Boom!";
      } else {
        return "Returned: " + i;
      }
    };

    Future<String> kaboom = CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync((i) -> Integer.parseInt("not a number")).handleAsync(errorHandler);

    System.out.println(kaboom.get());

    Future<String> success = CompletableFuture.supplyAsync(() -> addOne.apply(0))
        .thenApplyAsync(addOne).handleAsync(errorHandler);

    System.out.println(success.get());
  }

}
