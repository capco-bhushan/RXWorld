package com.bdravid.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Bhushan on 7/3/2017.
 */
public class ReactiveCalcV2 {
    private final static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.generate(stringEmitter -> stringEmitter.onNext(scanner.next()));
        observable.subscribe(new Logger());
        observable.share().subscribe(new Adder());
    }

    private static class Logger implements Observer<String> {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Hooked in logger...");
        }

        @Override
        public void onNext(String value) {
            System.out.println("Processing " + value);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private static class Adder extends Observable<Long> implements Observer<String>{
        private LongAdder adder = new LongAdder();

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Hooked in adder...");
        }

        @Override
        public void onNext(String value) {
            adder.add(Long.parseLong(value));
            System.out.println("Sum = "+adder.longValue());
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("Ignoring error while processing " + e);
        }

        @Override
        public void onComplete() {
        }

        @Override
        protected void subscribeActual(Observer<? super Long> observer) {
            observer.onNext(this.adder.longValue());
        }
    }

    private static class Average implements Observer<Long> {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Hooked in average calc...");
        }

        @Override
        public void onNext(Long sum) {
            System.out.println("Value = " + sum);
            System.out.println("Avg = " + (sum / count.incrementAndGet()));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
