package com.example.ramezelbaroudy.gratefulreminder.gratefulness.showGratefulPoints;



import com.example.ramezelbaroudy.gratefulreminder.repositories.GratefulPointsRepository;
import com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints.GratefulPointShowPresenter;
import com.example.ramezelbaroudy.gratefulreminder.showGratefulPoints.GratefulPointShowView;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GratefulPointShowPresenterTest {

    @Mock
    GratefulPointsRepository repository;

    @Mock
    GratefulPointShowView view;

    GratefulPointShowPresenter presenter;
    private final List<GratefulPoint> gratefulPointList = Arrays.asList(new GratefulPoint("first sentence"));

    @Before
    public void setUp() {
        presenter = new GratefulPointShowPresenter(view, repository, Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassGratefulPointsToView() {
        // given
        // presenter care only about GratefulPointsView and doesn't care about who implments the view
        // only when calling getGratefulPoints method then create a mock with one grratefulpoint in a list
        // just is for emitting the grateful point as we are mocking the getGratefulPoints when called
        when(repository.getGratefulPoints()).thenReturn(Single.just(gratefulPointList));

        // part of the test cannot be part of the before
        // when
        presenter.loadGratefulPoints();

        //then
        verify(view).displayGratefulPoints(gratefulPointList);
    }

    @Test
    public void shouldHandleNoGratefulPointsFound() {

        List<GratefulPoint> gratefulPointList = Collections.<GratefulPoint>emptyList();
        when(repository.getGratefulPoints()).thenReturn(Single.just(gratefulPointList));

        presenter.loadGratefulPoints();

        // verfiy that displayNobooks was called
        verify(view).displayNoBooks();
    }

    @Test
    public void shouldHandleErorr() {
        when(repository.getGratefulPoints()).thenReturn(Single.error(new Throwable("error returned")));
        presenter.loadGratefulPoints();
        verify(view).displayError();
    }

    @Test
    public void shouldDeleteGratefulPoint() {
        when(repository.deleteGratefulPoint("first sentence")).thenReturn(Single.just("Succed"));
        presenter.deleteGratefulPoint("first sentence");
        verify(view).refreshView();
    }

}