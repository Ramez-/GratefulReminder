package com.example.ramezelbaroudy.gratefulreminder.gratefulness.gratefulPointsHome;


import com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome.GratefulPointsActivityPresenter;
import com.example.ramezelbaroudy.gratefulreminder.gratefulPointsHome.GratefulPointsView;
import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.utils.GratefulPoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GratefulPointsActivityPresenterTest {

    @Mock
    GratefulPointsRepository repository;

    @Mock
    GratefulPointsView view;
    private GratefulPointsActivityPresenter presenter;
    private final List<GratefulPoint> gratefulPointList = Arrays.asList(new GratefulPoint("first sentence"));

    @Before
    public void setUp() {
        // trampoline thread is the current thread
        presenter = new GratefulPointsActivityPresenter(view, repository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp(){
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldShowConfirmation(){
        when(repository.addGratefulPoint("test sentence")).thenReturn(Single.just("voala"));
        presenter.addGratefulPoint("test sentence");
        verify(view).displayConfirmation();
    }

    @Test
    public void shouldShowErrorForConfirmation(){
        presenter.addGratefulPoint("");
        verify(view).showMissingInput();
    }

    @Test
    public void shouldReminUserWithGratefulPoint(){
        when(repository.getGratefulPoints()).thenReturn(Single.just(gratefulPointList));
        presenter.remindUserWithGratefulPoint();
        verify(view).sendRandomGratefulPointNotification(gratefulPointList);
    }

    @Test
    public void shouldDoNothingWhenDatabaseEmpty(){
        when(repository.isEmpty()).thenReturn(true);
        presenter.remindUserWithGratefulPoint();
        // verfying that sendRandomGratfulPoint Was Never Called
        verify(view, never()).sendRandomGratefulPointNotification(null);
    }
}