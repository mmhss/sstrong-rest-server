package com.gates.standstrong.utils;

public class StringUtils {

    public static double StringCompare(String a, String b)
    {
        if ((a.length() == 0) || (b.length() == 0)) //One is empty, second is not
        {
            return 0;
        }

        int minLen = a.length() < b.length() ? a.length(): b.length();
        int validCount = 0;
        int sameCharAtIndex = 0;

        for (int i = 0; i < minLen; i++) //Compare char by char
        {
            if(a.charAt(i)=='x')
                continue;

            if(b.charAt(i)=='x')
                continue;

            validCount++;

            if (a.charAt(i) == b.charAt(i))
            {
                sameCharAtIndex++;
            }
        }
        return sameCharAtIndex /  validCount * 100;
    }

    public static String fill(String str, int chartHour, String appendStr) {

        if(str.length()>=24) {
            return str;
        }

        int fillX = chartHour - str.length() -1;

        for(int i = 0; i<fillX; i++){
            str = str + "x";
        }

        return str + appendStr;
    }

    public static String shiftLeft(String str) {

        str = str + "x";

        return str.substring(1,25);

    }

    public static String shiftRight(String str) {

        str =  "x" + str;

        return str.substring(0,24);
    }

    public static boolean isMatch(String str1, String str2, int threshold){

        if(!hasSufficientOnes(str1)){
            return false;
        }

        if(!hasSufficientOnes(str2)){
            return false;
        }

        if(hasTooManyMissingValue(str2)){
            return false;
        }


        if (StringUtils.StringCompare(str1, str2) > threshold ){
            return true;
        }

        if (StringUtils.StringCompare(str1, StringUtils.shiftLeft(str2)) > threshold ){
            return true;
        }

        if (StringUtils.StringCompare(str1, StringUtils.shiftRight(str2)) > threshold ){
            return true;
        }

        return false;
    }

    private static boolean hasTooManyMissingValue(String str) {

        int count=0;
        for (int i = 0; i < str.length(); i++) //Compare char by char
        {
            if(str.charAt(i)=='x'){
                count++;
            }

            if(count>12){
                System.out.println("Has too many missing values");
                return true;
            }

        }

        return false;
    }


    public static boolean hasSufficientOnes(String str) {

        int count=0;
        for (int i = 0; i < str.length(); i++) //Compare char by char
        {
            if(str.charAt(i)=='1'){
                count++;
            }

            if(count>6){
                return true;
            }

        }

        return false;
    }
}
