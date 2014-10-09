package com.mycompany.htmlvalidator.scanners.readers;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.*;

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
            InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
            ObjectInputStream ois = createObjectInputStreamForDeserializing(stream);
            return (List<HtmlData>) ois.readObject();
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to load stored HtmlData. IO Error!");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Class wasn't found to deserialize HtmlData!");
        }
    }
    
    private static ObjectInputStream createObjectInputStreamForDeserializing(InputStream in) throws IOException {
        return new ObjectInputStream(in) {
            @Override
            protected ObjectStreamClass readClassDescriptor() throws ClassNotFoundException, IOException {
                ObjectStreamClass desc = super.readClassDescriptor();
/*
                if (desc.getName().equals("com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData"))
                    return ObjectStreamClass.lookup(HtmlData.class);
                else if (desc.getName().equals("com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute"))
                    return ObjectStreamClass.lookup(HtmlAttribute.class);
                else if (desc.getName().equals("com.mycompany.htmlvalidator.exceptions.MarkupErrorReporter"))
                    return ObjectStreamClass.lookup(com.mycompany.htmlvalidator.errors.MarkupErrorReporter.class);
                else if (desc.getName().equals("com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.MissingEnclosureParsingException"))
                    return ObjectStreamClass.lookup(MissingEnclosureParsingError.class);
                else if (desc.getName().equals("com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.UnclosedTagParsingException"))
                    return ObjectStreamClass.lookup(UnclosedTagParsingError.class);
*/
                return desc;
            }
        };
    }

    public static void updateSerializedFiles() {
        updateSerializedFile(DEFAULT_VALID_DATA_FILE);
        updateSerializedFile(DEFAULT_INVALID_DATA_FILE);
        updateSerializedFile(DEFAULT_MIXED_DATA_FILE);
    }

    private static void updateSerializedFile(String dataFile) {
        try {
            List<HtmlData> deserialized = deserializeFile(dataFile);

            OutputStream stream = new FileOutputStream(dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(stream);
            oos.writeObject(deserialized);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to store HtmlData. IO ERROR!");
        }
    }
}
