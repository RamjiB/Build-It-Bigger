package com.example;

import java.util.Random;

public class JavaJokeLibrary {

    public String JokeTellingLibrary() {

        String[] jokes = new String[2];

        jokes[0] = "There are so many scams on the Internet these days, but for Rs.200 I can show you how to avoid them";
        jokes[1] = "If Snapchat has taught me anything its that a lot of you look better as farm animals";

        Random random = new Random();

        return jokes[random.nextInt(jokes.length)];

    }

}
