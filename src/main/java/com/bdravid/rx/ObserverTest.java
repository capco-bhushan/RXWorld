package com.bdravid.rx;

import io.reactivex.Observable;

/**
 * Created by Bhushan on 7/5/2017.
 */
public class ObserverTest {
    public static void main(String[] args) {
        //List of ints without any caching. Everytime a lambda is subscribed a new instance of Observable is created.
        //SO the result will show "Create" being as many times as subscribe method is called.
        Observable<Integer> ints = Observable.create(e -> {
            log("Create");
            e.onNext(42);
            e.onComplete();
        });
        ints.subscribe(i -> log("Sub A " + i));
        ints.subscribe(i -> log("Sub B " + i));


        System.out.println("\n\n");


        //List of ints with caching. This will create the observable only once and its events will be shared amongst all
        //the subscribers. As a result the "create" will be logged only once.
        Observable<Integer> intsCached = Observable.<Integer>create(e -> {
            log("Create");
            e.onNext(44);
            e.onComplete();
        }).cache();
        intsCached.subscribe(i -> log("Sub A " + i));
        intsCached.subscribe(i -> log("Sub B " + i));
    }

    private static void log(String log) {
        System.out.println("[" + Thread.currentThread().getName() + "]" + log);
    }
}
