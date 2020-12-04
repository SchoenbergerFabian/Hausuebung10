package com.Infendro.PasswordGuesser.password0;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//97-122 ... a-z
public class PasswordGuesser {

    private final String passwordHash;

    public PasswordGuesser(){
        String lines = "";
        try {
            lines = Files.lines(new File("passwords/password0").toPath())
                    .reduce((string1,string2)->string1+"\n"+string2)
                    .orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        passwordHash = lines;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String guess(){

        List<GuesserCallable> callables = new ArrayList<>();
        String end;
        for(String start = "aaaa"; start !=null; start = end){
            end=generateNextStart(start);
            callables.add(new GuesserCallable(this,start,end));
        }

        ExecutorService exec = Executors.newFixedThreadPool(callables.size());
        String result = "";
        try {
            result = exec.invokeAny(callables);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        exec.shutdown();
        GuesserCallable.running = false;

        return result;
    }

    private String generateNextStart(String previous){
        char[] chars = previous.toCharArray();

        int value = chars[0];
        value++;

        if(value==123){
            return null;
        }
        chars[0] = (char) value;

        return String.valueOf(chars);
    }

    public String stringPlusPlus(String string){
        char[] chars = string.toCharArray();

        char[] charsResult = charPlusPlus(chars,chars.length-1);

        return charsResult!=null ? String.valueOf(charsResult) : null;
    }

    private char[] charPlusPlus(char[] chars, int index){
        int value = chars[index];
        value++;

        if(value==123){
            if(index == 0){
                return null;
            }else{
                chars[index] = 97;
                return charPlusPlus(chars, index - 1);
            }
        }else{
            chars[index] = (char) value;
        }

        return chars;
    }
}
