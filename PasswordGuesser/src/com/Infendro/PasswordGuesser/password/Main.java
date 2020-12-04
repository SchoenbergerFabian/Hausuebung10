package com.Infendro.PasswordGuesser.password;

import com.Infendro.PasswordGuesser.Sha256;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in,"Windows-1252");

        String pw = password(scanner);
        String passwordHash = Sha256.applySha256(pw);

        int pwLength = numberOfCharacters(scanner);
        boolean[] values = values(scanner);

        PasswordGuesser guesser = new PasswordGuesser(passwordHash,pwLength,values[0],values[1],values[2]);
        System.out.println("password: "+guesser.guess());
    }

    private static String password(Scanner scanner){
        System.out.print("password > ");
        return scanner.nextLine();
    }

    private static int numberOfCharacters(Scanner scanner){
        System.out.print("length > ");
        return Integer.parseInt(scanner.nextLine());
    }

    private static boolean[] values(Scanner scanner){
        boolean[] values = new boolean[3];
        System.out.print("answer with true/false"
                + "\ncontains numbers > ");
        values[0] = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("contains uppercase letters > ");
        values[1] = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("contains lowercase letters > ");
        values[2] = Boolean.parseBoolean(scanner.nextLine());

        return values;
    }

}
