package com.github.nreich.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class ThreadControl {
    public static void main(String... args) throws ExecutionException, InterruptedException {
        Function<Integer, Integer> addOne = (i) -> {
            System.out.println(Thread.currentThread().getName());
            return i + 1;
        };

        // apply async
        System.out.println("All async: ");
        Future<Integer> async = CompletableFuture.supplyAsync(() -> addOne.apply(0))
                .thenApplyAsync(addOne).thenApplyAsync(addOne).thenApplyAsync(addOne);
        async.get();

        // apply
        System.out.println("Synchronous: ");
        CompletableFuture.supplyAsync(() -> addOne.apply(0))
                .thenApply(addOne).thenApply(addOne).thenApply(addOne);
    }
}
