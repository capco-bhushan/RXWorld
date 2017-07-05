package com.bdravid.rx;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
 * The sum of these multiples is 23. Find the sum of all the multiples of 3 or 5 below 1000
 *
 * Created by Bhushan on 6/23/2017.
 */
public class Euler1 {
    public static void main(String[] args) {
        int maxCount = 10;
        Observable<Integer> integerSequence = Observable.range(1, maxCount).cache();
        Observable<Integer> multiplesOf3 = integerSequence.filter(i -> i%3 == 0);
        Observable<Integer> multiplesOf5 = integerSequence.filter(i -> i%5 == 0);
        Single<Integer> result = Observable.merge(multiplesOf3, multiplesOf5).distinct().reduce(0, (a, b) -> a + b);
        System.out.println(result.blockingGet());
    }
}
