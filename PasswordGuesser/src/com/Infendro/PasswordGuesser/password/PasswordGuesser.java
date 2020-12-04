package com.Infendro.PasswordGuesser.password;

import com.Infendro.PasswordGuesser.Sha256;

public class PasswordGuesser {

    private String passwordHash;

    private final int length;
    private final boolean upperCaseLetters;
    private final boolean lowerCaseLetters;
    private final boolean numbers;

    public PasswordGuesser(String passwordHash, int length, boolean numbers, boolean upperCaseLetters, boolean lowerCaseLetters) {
        this.passwordHash = passwordHash;
        this.length = length;
        this.numbers = numbers;
        this.upperCaseLetters = upperCaseLetters;
        this.lowerCaseLetters = lowerCaseLetters;
    }

    public String guess(){
        String password = generateStartString();

        while(!Sha256.applySha256(password).equals(passwordHash)){
            System.out.println(password);
            password=stringPlusPlus(password);
        }

        return password;
    }

    private String generateStartString(){
        StringBuilder start = new StringBuilder();
        if(numbers){
            start.append("0");
            for(int counter = 1; counter<length; counter++){
                start.append("0");
            }
        }else if(upperCaseLetters){
            start.append("A");
            for(int counter = 1; counter<length; counter++){
                start.append("A");
            }
        }else if(lowerCaseLetters){
            start.append("a");
            for(int counter = 1; counter<length; counter++){
                start.append("a");
            }
        }
        return start.toString();
    }

    //48-57 ... 0-9
    //65-90 ... A-Z
    //97-122 ... a-z
    private String stringPlusPlus(String string){
        char[] chars = string.toCharArray();
        char[] charsResult = charPlusPlus(chars,chars.length-1);
        return charsResult!=null ? String.valueOf(charsResult) : null;
    }

    private char[] charPlusPlus(char[] chars, int index){
        boolean extended = false;

        int value = chars[index];
        value++;

        if(value==58){
            if(upperCaseLetters){
                chars[index] = 65;
            }else if(lowerCaseLetters){
                chars[index] = 98;
            }else{
                if(index == 0&&length!=0){
                    return null;
                }else{
                    chars[index] = 48;
                    if(index == 0) {
                        return extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }
        }else if(value==91){
            if(lowerCaseLetters){
                chars[index] = 97;
            }else if(numbers){
                if(index == 0&&length!=0){
                    return null;
                }else{
                    chars[index] = 48;
                    if(index == 0) {
                        return  extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }else{
                if(index == 0&&length!=0){
                    return null;
                }else{
                    chars[index] = 65;
                    if(index == 0) {
                        return  extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }
        }else if(value==123){
            if(numbers){
                if(index == 0&&length!=0){
                    return null;
                }else{
                    chars[index] = 48;
                    if(index == 0) {
                        return  extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }else if(upperCaseLetters){
                if(index == 0&&length !=0){
                    return null;
                }else{
                    chars[index] = 65;
                    if(index == 0) {
                        return extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }else{
                if(index == 0&&length!=0){
                    return null;
                }else{
                    chars[index] = 97;
                    if(index == 0) {
                        return extendCharArray(chars);
                    }
                    return charPlusPlus(chars, index - 1);
                }
            }
        }else{
            chars[index] = (char) value;
        }
        return chars;
    }

    private char[] extendCharArray(char[] chars){
        char[] charsExtended = new char[chars.length+1];
        for(int i = chars.length-1; i>=0; i--){
            charsExtended[i+1]=chars[i];
        }
        if(numbers){
            charsExtended[0] = 48;
        }else if(upperCaseLetters){
            charsExtended[0] = 65;
        }else if(lowerCaseLetters){
            charsExtended[0] = 97;
        }
        return charsExtended;
    }

}
