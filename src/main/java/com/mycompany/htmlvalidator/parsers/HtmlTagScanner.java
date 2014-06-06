package com.mycompany.htmlvalidator.parsers;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.interfaces.HtmlTag;
import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlBufferedReader;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;
import com.mycompany.htmlvalidator.parsers.HtmlScanner;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlReader;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParser;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParserImpl;
import com.mycompany.htmlvalidator.parsers.utilities.MutableHtmlTagFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

public class HtmlTagScanner implements HtmlScanner {
    private final String defaultTag = "standard";
    
    private MutableHtmlTagFactory factory = new MutableHtmlTagFactory();
    private HtmlTagDataParser parser = new HtmlTagDataParserImpl();
    private HtmlReader reader;
    
    public HtmlTagScanner(HtmlReader in, HtmlTagDataParser parser) {
        this.reader = in;
        this.parser = parser;
    }
    
    public HtmlTagScanner(String data) {
        this.reader = new HtmlBufferedReader(data);
    }
    
    public HtmlTagScanner(File data) throws FileNotFoundException {
        this.reader = new HtmlBufferedReader(data);
    }
    
    @Override
    public boolean hasNext() {
        return this.reader.hasNext();
    }
    
    @Override
    public HtmlTag next() throws IllegalHtmlTagException {
        if (this.hasNext())
            return this.readAndParseNextTag();
        else
            throw new NoSuchElementException();
    }
    
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
    
    private HtmlTag readAndParseNextTag() throws IllegalHtmlTagException {
        String htmlData = this.reader.next();
        HtmlData data = parser.parse(htmlData);
        MutableHtmlTag tag = factory.makeTag(this.defaultTag);
        tag.setData(data);
        return tag;
    }
}//
