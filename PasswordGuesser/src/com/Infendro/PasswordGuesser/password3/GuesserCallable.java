package com.Infendro.PasswordGuesser.password3;

import com.Infendro.PasswordGuesser.Sha256;

import java.util.List;
import java.util.concurrent.Callable;

public class GuesserCallable implements Callable<String> {

    private final PasswordGuesser guesser;
    private final List<String> list;
    private final int start;
    private final int end;

    public static boolean running;

    public GuesserCallable(PasswordGuesser guesser, List<String> list, int start, int end) {
        this.guesser = guesser;
        this.list = list;
        this.start = start;
        this.end = end;

        running = true;
    }

    @Override
    public String call() {
        for(int index = start;index<=end&&running;index++){
            if(Sha256.applySha256(list.get(index)).equals(guesser.getPasswordHash())){
                return list.get(index);
            }
        }
        while(running){}
        return null;
    }
}
