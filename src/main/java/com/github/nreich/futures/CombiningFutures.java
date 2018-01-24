package com.github.nreich.futures;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CombiningFutures {

  private static final Random RANDOM = new Random();

  public static void main(String... args) throws ExecutionException, InterruptedException {
    // allOf
    CompletableFuture<?>[] allFutures = new CompletableFuture[3];
    for (int i = 0; i < 3; i++) {
      allFutures[i] = CompletableFuture.runAsync(CombiningFutures::slowJob);
    }
    Future<Void> allOf = CompletableFuture.allOf(allFutures);
    allOf.get();
    System.out.println("Done waiting for allOf");

    // anyOf
    CompletableFuture<?>[] anyOfFutures = new CompletableFuture[3];
    for (int i = 0; i < 3; i++) {
      anyOfFutures[i] = CompletableFuture.supplyAsync(CombiningFutures::slowJob);
    }
    Future<Object> anyOf = CompletableFuture.anyOf(anyOfFutures);
    System.out.println("Got task that took " + anyOf.get() + " seconds");
    System.out.println("Done waiting for anyOf");
  }

  private static int slowJob() {
    int seconds = RANDOM.nextInt(10);
    try {
      Thread.sleep(1000 * seconds);
      System.out.println("Ran for " + seconds + " seconds");
    } catch (InterruptedException ignore) {

    }
    return seconds;
  }
}
