package org.example.Managers;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    private static Locale currentLocale = new Locale("en", "US");
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", currentLocale);

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }
}
