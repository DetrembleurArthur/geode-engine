package com.geode.graphics.ui.text;

public class FontCharsets {
    public static final String NUMERIC = "0123456789";
    public static final String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA_NUMERIC = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!$:/;.,?*-+()[]{}%";

    public static String ascii() {
        StringBuilder charset = new StringBuilder();
        for (int i = 0; i < 254; i++) {
            charset.append((char) i);
        }
        return charset.toString();
    }
}
