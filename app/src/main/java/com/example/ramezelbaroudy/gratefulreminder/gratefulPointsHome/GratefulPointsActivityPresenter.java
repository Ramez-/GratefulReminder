package com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome;


import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;


import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GratefulPointsActivityPresenter {

    private GratefulPointsView view;
    private GratefulPointsRepository repository;
    private Scheduler mainScheduler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GratefulPointsActivityPresenter(GratefulPointsView view, GratefulPointsRepository repository, Scheduler mainScheduler) {
        this.view = view;
        this.repository = repository;
        this.mainScheduler = mainScheduler;
    }

    public void addGratefulPoint(String gratefulSentence) {
        if (gratefulSentence.length() == 0) {
            view.showMissingInput();
        } else {
            compositeDisposable.add((Disposable) repository.addGratefulPoint(gratefulSentence)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainScheduler)
                    .subscribeWith(new DisposableSingleObserver() {
                        @Override
                        public void onSuccess(Object o) {
                            view.displayConfirmation();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }));

        }
    }

    public void remindUserWithGratefulPoint() {
        if (!repository.isEmpty()) {
            compositeDisposable.add((Disposable) repository.getGratefulPoints()
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainScheduler)
                    .subscribeWith(new DisposableSingleObserver<List<GratefulPoint>>() {
                        @Override
                        public void onSuccess(List<GratefulPoint> gratefulPoints) {
                            view.sendRandomGratefulPointNotification(gratefulPoints);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    }));
        }
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }

}
