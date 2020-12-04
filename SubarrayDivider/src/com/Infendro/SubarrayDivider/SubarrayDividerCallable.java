package com.Infendro.SubarrayDivider;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;

public class SubarrayDividerCallable implements Callable<Integer> {

    private final List<Integer> numbers;
    private final int amountInSub;
    private final int start;
    private final int end;

    public SubarrayDividerCallable(List<Integer> numbers, int amountInSub, int start, int end) {
        this.numbers = numbers;
        this.amountInSub = amountInSub;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int maxUnique = 0;
        for(int index = start; index<=end; index++){
            if(index+amountInSub>numbers.size()){
                break;
            }else {
                HashSet<Integer> uniqueNumbers = new HashSet<>();
                for (int innerIndex = index; innerIndex < index + amountInSub; innerIndex++) {
                    uniqueNumbers.add(numbers.get(innerIndex));
                }
                if(uniqueNumbers.size()>maxUnique){
                    maxUnique= uniqueNumbers.size();
                }
            }
        }
        return maxUnique;
    }
}
