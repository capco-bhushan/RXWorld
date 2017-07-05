package com.bdravid.rx;

import io.reactivex.Observable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Bhushan on 7/3/2017.
 */
public class Fibonacchi {
    public static void main(String[] args) {

        final AtomicInteger previous = new AtomicInteger(0);
        Observable.range(0, 5).map(integer -> {
            Integer retval = integer + previous.get();
            previous.set(retval);
            return retval;
        }).forEach(System.out::println);
    }

}
