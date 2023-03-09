package de.muehlbauer.assignment.barista.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @Author : Hanieh Moafi
 * @Date : 10/14/2022
 */
public class MessageBundler {

    private static final ResourceBundle expBundler;

    static {
        Locale.setDefault(new Locale("en", "EN"));
        expBundler = ResourceBundle.getBundle("exception_messages");

    }

    public MessageBundler() {
        ResourceBundle expBundler = ResourceBundle.getBundle("exception_messages");
    }

    public static String getExpMessage(String key) {
        if (expBundler.containsKey(key)) return expBundler.getString(key);
        else return key;

    }

}
