/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers;

import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.*;

import java.io.*;
import java.util.*;

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
                if (desc.getName().equals("com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData"))
                    return ObjectStreamClass.lookup(HtmlData.class);
                else if (desc.getName().equals("com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute"))
                    return ObjectStreamClass.lookup(HtmlAttribute.class);
                else if (desc.getName().equals("com.mycompany.markuptagscanner.errors.MarkupErrorReporter"))
                    return ObjectStreamClass.lookup(MarkupErrorReporter.class);
                if (desc.getName().equals("com.mycompany.markuptagscanner.errors.MissingEnclosureParsingError"))
                    return ObjectStreamClass.lookup(MissingEnclosureParsingError.class);
                else if (desc.getName().equals("com.mycompany.markuptagscanner.errors.UnclosedTagParsingError"))
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
