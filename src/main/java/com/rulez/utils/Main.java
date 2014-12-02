package com.rulez.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rulezzz on 02.12.2014.
 */
public class Main {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^(19|20)\\d{2}[.](((0[469]|11)[.](0[1-9]|[12][0-9]|30))|((0[13578]|10|12])[.](0[1-9]|[12][0-9]|3[01]))|(02[.](0[1-9]|[12][0-9])))$");
        Matcher matcher = pattern.matcher("2014.03.31");
        System.out.print(matcher.matches());
    }
}
