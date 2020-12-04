package com.Infendro.PasswordGuesser.password3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PasswordGuesser {

    private final String passwordHash;

    public PasswordGuesser(){
        String lines = "";
        try {
            lines = Files.lines(new File("passwords/password3").toPath())
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
        List<String> list = readWikiList();

        if(list!=null) {
            List<GuesserCallable> callables = new ArrayList<>();
            int start = 0;
            int space = list.size() / 10;
            for (int counter = 0; counter < 9; counter++) {
                callables.add(new GuesserCallable(this, list, start, start + space));
                start += space + 1;
            }
            callables.add(new GuesserCallable(this, list, start, list.size() - 1));

            ExecutorService exec = Executors.newFixedThreadPool(10);
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
        return null;
    }

    private List<String> readWikiList(){
        try {
            Document document = Jsoup.connect("https://de.wikipedia.org/wiki/Liste_von_Fabelwesen").get();
            return document.select("ul li a").stream()
                    .map(creature -> creature.attr("title"))
                    .filter(creature -> !creature.equals(""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
