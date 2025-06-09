package com.example.springwebmvc.utils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Utility class for converting strings into URL-friendly slugs.
 */
public class SlugUtil {

    // Matches all non-word characters except dashes
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");

    // Matches any whitespace character (space, tab, etc.)
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    /**
     * Converts the given input string into a slug.
     *
     * @param input The original string (e.g. product name or title)
     * @return A slugified version of the input (e.g. "Wireless Headphones" → "wireless-headphones")
     */
    public static String toSlug(String input) {
        if (input == null) return null;

        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");

        return slug.toLowerCase(Locale.ENGLISH);
    }
}
