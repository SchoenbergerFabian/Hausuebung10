package com.Infendro.SubarrayDivider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String FILE_NAME = "numbers/numbers5";
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) {
        int amountInSub = readFirstLine();
        List<Integer> numbers = readNumbers();

        SubarrayDivider divider = new SubarrayDivider(amountInSub,numbers);

        divider.divide(NUMBER_OF_THREADS);
    }

    private static int readFirstLine(){
        try {
            return Integer.parseInt(Files.lines(new File(FILE_NAME).toPath())
                    .findFirst()
                    .orElse("0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static List<Integer> readNumbers(){
        try {
            final List<Integer> numbers = new ArrayList<>();
            Files.lines(new File(FILE_NAME).toPath())
                    .skip(1)
                    .forEach(line -> Arrays.stream(line.split(" "))
                            .forEach(numberString -> numbers.add(Integer.parseInt(numberString))));
            return numbers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
