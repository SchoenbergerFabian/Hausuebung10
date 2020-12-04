package com.Infendro.SubarrayDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubarrayDivider {

    private final int amountInSub;
    private final List<Integer> numbers;

    public SubarrayDivider(int amountInSub, List<Integer> numbers) {
        this.amountInSub = amountInSub;
        this.numbers = numbers;
    }

    public void divide(int numberOfThreads){
        ExecutorService exec = Executors.newFixedThreadPool(numberOfThreads);

        int start = 0;
        int space = (numbers.size()/numberOfThreads)-1;
        List<SubarrayDividerCallable> dividers = new ArrayList<>();
        for(int counter = 1; counter<=numberOfThreads;counter++){
            if(counter==numberOfThreads){
                dividers.add(new SubarrayDividerCallable(numbers, amountInSub, start,numbers.size()-1));
            }else{
                dividers.add(new SubarrayDividerCallable(numbers, amountInSub, start,start+space));
                start += space+1;
            }
        }

        int maxUnique = 0;

        try {
            maxUnique = exec.invokeAll(dividers).stream()
                    .mapToInt(integerFuture -> {
                        try {
                            return integerFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    })
                    .max()
                    .orElse(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(maxUnique);

        exec.shutdown();
    }

}
