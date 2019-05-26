package com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints;

import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GratefulPointShowPresenter {
    private final GratefulPointsRepository repository;
    private final GratefulPointShowView view;
    private final Scheduler mainScheduler;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public GratefulPointShowPresenter(GratefulPointShowView gratefulPointShowView, GratefulPointsRepository gratefulPointsRepository, Scheduler mainScheduler) {
        this.repository = gratefulPointsRepository;
        this.view = gratefulPointShowView;
        this.mainScheduler = mainScheduler;
    }

        public void loadGratefulPoints() {

        compositeDisposable.add(repository.getGratefulPoints()
                .subscribeOn(Schedulers.io())
                //dependancy injection for the tests to work
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<GratefulPoint>>() {
                    @Override
                    public void onSuccess(List<GratefulPoint> gratefulPointsList) {
                        if (gratefulPointsList.size() == 0) {
                            view.displayNoBooks();
                        } else {
                            view.displayGratefulPoints(gratefulPointsList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayError();
                    }
                }));

    }

    public void deleteGratefulPoint(String gratefulPointString){
        compositeDisposable.add((Disposable) repository.deleteGratefulPoint(gratefulPointString)
        .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver() {
                    @Override
                    public void onSuccess(Object o) {
                        view.refreshView();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
