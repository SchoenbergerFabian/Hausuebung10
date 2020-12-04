package com.Infendro.PasswordGuesser.password2;

import com.Infendro.PasswordGuesser.Sha256;

import java.util.concurrent.Callable;

public class GuesserCallable implements Callable<String> {

    private final PasswordGuesser guesser;
    private final String start;
    private final String end;

    public static boolean running;

    public GuesserCallable(PasswordGuesser guesser, String start, String end) {
        this.guesser = guesser;
        this.start = start;
        this.end = end;

        running = true;
    }

    @Override
    public String call() throws Exception {
        String password = start;
        while(!password.equals(end)&&running){
            if(Sha256.applySha256(password).equals(guesser.getPasswordHash())){
                return password;
            }
            password = guesser.stringPlusPlus(password);
        }
        while(running){}
        return null;
    }
}
