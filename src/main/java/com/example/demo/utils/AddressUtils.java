package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressUtils {
    public static String convertHexToLowerCase(String input) {
        // Regular expression to match hexadecimal numbers
        String regex = "0x[0-9a-fA-F]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        StringBuffer result = new StringBuffer();

        // Find matches and replace them with their lowercase versions
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group().toLowerCase());
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
