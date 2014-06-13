package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlParser {
    public static final char OPEN_TAG_ENCLOSURE = '<';
    public static final char CLOSE_TAG_ENCLOSURE = '>';
    private static final Set<Character> TAG_ENCLOSURES = 
            new HashSet<Character>(Arrays.asList(OPEN_TAG_ENCLOSURE, CLOSE_TAG_ENCLOSURE));
    
    public static boolean isOpenTagEnclosure(char c) {
        return c == OPEN_TAG_ENCLOSURE;
    }
    
    public static boolean isCloseTagEnclosure(char c) {
        return c == CLOSE_TAG_ENCLOSURE;
    }
    
    public static boolean isTagEnclosure(char c) {
        return TAG_ENCLOSURES.contains(c);
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException;
}
