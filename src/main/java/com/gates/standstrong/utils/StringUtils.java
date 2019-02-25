package com.gates.standstrong.utils;

public class StringUtils {

    public static double StringCompare(String a, String b)
    {
        if (a == b) //Same string, no iteration needed.
            return 100;
        if ((a.length() == 0) || (b.length() == 0)) //One is empty, second is not
        {
            return 0;
        }
        double maxLen = a.length() > b.length() ? a.length(): b.length();
        int minLen = a.length() < b.length() ? a.length(): b.length();
        int sameCharAtIndex = 0;
        for (int i = 0; i < minLen; i++) //Compare char by char
        {
            if (a.charAt(i) == b.charAt(i))
            {
                sameCharAtIndex++;
            }
        }
        return sameCharAtIndex / maxLen * 100;
    }

    public static String fill(String str, int chartHour, String appendStr) {

        if(str.length()>=24) {
            return str;
        }

        int fillX = chartHour - str.length() -1;

        for(int i = 0; i<fillX; i++){
            str = str + "0";
        }

        return str + appendStr;
    }

    public static String shiftLeft(String str) {

        str = str + "0";

        return str.substring(1,25);

    }

    public static String shiftRight(String str) {

        str =  "0" + str;

        return str.substring(0,24);
    }

    public static boolean isMatch(String str1, String str2, int threshold){

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
}
