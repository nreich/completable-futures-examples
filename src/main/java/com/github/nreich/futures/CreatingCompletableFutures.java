package com.github.nreich.futures;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class CreatingCompletableFutures {

  public static void main(String... args) throws ExecutionException, InterruptedException {
    // runAsync
    CompletableFuture.runAsync(() -> System.out.println("Printing this async"));
    CompletableFuture.runAsync(CreatingCompletableFutures::runnableMethod);

    // Using a supplier
    Supplier<String> nowSupplier = () -> Instant.now().toString();
    Future<String> nowFuture = CompletableFuture.supplyAsync(nowSupplier);
    System.out.println(nowFuture.get());

    CreatingCompletableFutures.NowSupplier mySupplier = new NowSupplier();
    System.out.println(CompletableFuture.supplyAsync(mySupplier).get());
  }

  private static void runnableMethod() {
    System.out.println("String from a method");
  }

  private static class NowSupplier implements Supplier<String> {
    public String get() {
      return Instant.now().toString();
    }
  }
}
