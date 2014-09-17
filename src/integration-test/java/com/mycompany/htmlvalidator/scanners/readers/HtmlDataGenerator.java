package com.mycompany.htmlvalidator.scanners.readers;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.readers.parsers.*;

public class HtmlDataGenerator {
    private static final String DEFAULT_VALID_STR_FILE = "valid-html-strs.txt";
    private static final String DEFAULT_VALID_DATA_FILE = "valid-html-data.ser";
    
    private static final String DEFAULT_INVALID_STR_FILE = "invalid-html-strs.txt";
    private static final String DEFAULT_INVALID_DATA_FILE = "invalid-html-data.ser";
    
    private static final String DEFAULT_MIXED_STR_FILE = "mixed-html-strs.txt";
    private static final String DEFAULT_MIXED_DATA_FILE = "mixed-html-data.ser";
    
    public static InputStream getValidHtmlStream() {
        return ClassLoader.getSystemResourceAsStream(DEFAULT_VALID_STR_FILE);
    }
    
    public static Iterator<HtmlData> getValidHtmlDataIterator() {
        return deserializeFile(DEFAULT_VALID_DATA_FILE).iterator();
    }
    
    public static InputStream getInvalidHtmlStream() {
        return ClassLoader.getSystemResourceAsStream(DEFAULT_INVALID_STR_FILE);
    }
    
    public static Iterator<HtmlData> getInvalidHtmlDataIterator() {
        return deserializeFile(DEFAULT_INVALID_DATA_FILE).iterator();
    }
    
    public static InputStream getMixedHtmlStream() {
        return ClassLoader.getSystemResourceAsStream(DEFAULT_MIXED_STR_FILE);
    }
    
    public static Iterator<HtmlData> getMixedHtmlDataIterator() {
        return deserializeFile(DEFAULT_MIXED_DATA_FILE).iterator();
    }
    
    @SuppressWarnings("unchecked")
    private static List<HtmlData> deserializeFile(String fileName) {
        try {
            InputStream stream = ClassLoader
                    .getSystemResourceAsStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(stream);
            return (List<HtmlData>) ois.readObject();
        } catch (IOException E) {
            throw new RuntimeException(
                    "Unable to load stored HtmlData. IO Error!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    "Class wasn't found to deserialize HtmlData!");
        }
    }
}
