package com.bdravid.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Bhushan on 7/3/2017.
 */
public class ReactiveCalcV3 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> subject = PublishSubject.create(e -> {
            log("Create...");
            try {
                for(int i = 1; i <20; i++) {
                    e.onNext(i);
                }
                e.onComplete();
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
        subject.subscribe(new Logger());
        subject.subscribe(new Adder());
    }

    private static class Logger implements Observer<Integer> {

        @Override
        public void onSubscribe(Disposable d) {
            log("Hooked in logger...");
        }

        @Override
        public void onNext(Integer value) {
            log("Processing " + value);
        }

        @Override
        public void onError(Throwable e) {
            log("Erred while reading from system.in" + e);
        }

        @Override
        public void onComplete() {
            log("Logger onComplete");
        }
    }

    private static class Adder implements Observer<Integer>{
        private LongAdder adder = new LongAdder();

        @Override
        public void onSubscribe(Disposable d) {
            log("Hooked in adder...");
        }

        @Override
        public void onNext(Integer value) {
            adder.add(value);
            log("Cumulative sum = " + adder.longValue());
        }


        @Override
        public void onError(Throwable e) {
            log("Ignoring error while processing " + e);
        }

        @Override
        public void onComplete() {
        }
    }

    private static class Average implements Observer<Long> {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public void onSubscribe(Disposable d) {
            log("Hooked in average calc...");
        }

        @Override
        public void onNext(Long sum) {
            log("Value = " + sum);
            log("Avg = " + (sum / count.incrementAndGet()));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private static void log(String toLog) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + toLog);
    }
}
