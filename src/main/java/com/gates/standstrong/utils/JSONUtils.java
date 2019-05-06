package com.gates.standstrong.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {

    public static String parse(String json, String key) throws ParseException {

        Object obj = new JSONParser();
        JSONObject jo = (JSONObject) ((JSONParser) obj).parse(json);


         return ("" + jo.get(key));
    }

}
