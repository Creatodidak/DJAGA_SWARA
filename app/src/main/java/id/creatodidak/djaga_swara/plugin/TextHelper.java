package id.creatodidak.djaga_swara.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHelper {
    public static String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static String numberingData(String data){
        String[] strings = data.split("\n\n");
        String newString = "";
        int i = 0;
        for(String datas : strings){
            i = i+1;
            newString = newString + i +". "+datas+"\n";
        }

        return newString;
    }
}