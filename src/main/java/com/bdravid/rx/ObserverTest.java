package com.bdravid.rx;

import io.reactivex.Observable;

/**
 * Created by Bhushan on 7/5/2017.
 */
public class ObserverTest {
    public static void main(String[] args) {
        Observable<Integer> ints = Observable.<Integer>create(e -> {
            log("Create");
            e.onNext(42);
            e.onComplete();
        }).cache();
        ints.subscribe(i -> log("Sub A " + i));
        ints.subscribe(i -> log("Sub B " + i));
    }

    private static void log(String log) {
        System.out.println("[" + Thread.currentThread().getName() + "]" + log);
    }
}
