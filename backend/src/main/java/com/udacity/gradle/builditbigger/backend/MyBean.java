package com.udacity.gradle.builditbigger.backend;

import com.example.JavaJokeLibrary;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private final JavaJokeLibrary javaJokeLibrary;

    public MyBean(){
        javaJokeLibrary = new JavaJokeLibrary();
    }
    public String getJoke(){
        return javaJokeLibrary.JokeTellingLibrary();
    }
}