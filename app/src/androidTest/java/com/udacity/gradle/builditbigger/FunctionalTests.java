package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import java.util.concurrent.ExecutionException;

public class FunctionalTests extends AndroidTestCase {


    public void NonEmptyJokeTest(){

        String joke = null;
        EndPointsAsyncTask endPointsAsyncTask = new EndPointsAsyncTask(getContext(),null);
        endPointsAsyncTask.execute();

        try {
            joke = endPointsAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertNotNull(joke);
    }
}
