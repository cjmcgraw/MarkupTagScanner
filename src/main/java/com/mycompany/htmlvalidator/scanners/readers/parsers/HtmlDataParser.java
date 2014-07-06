package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParser extends DataParser{
    private HtmlUtilityParser closingParser;
    private HtmlUtilityParser elementParser;
    private HtmlUtilityParser attributeParser;
    
    public HtmlDataParser() {
        this(new HtmlClosingParser(), 
             new HtmlElementParser(), 
             new HtmlAttributeParser());
        }
    
    public HtmlDataParser(HtmlUtilityParser closingParser, 
                          HtmlUtilityParser elementParser, 
                          HtmlUtilityParser attributeParser) {
        this.closingParser = closingParser;
        this.elementParser = elementParser;
        this.attributeParser = attributeParser;
    }
    
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        HtmlData result = this.parseTag();
        this.clearState();
        return result;
    }
    
    private HtmlData parseTag() throws IOException {
        try {
            this.readOpenTag();
            this.parseTagData();
            this.readCloseTag();
        } catch (ParsingException e) {
            e.setHtmlData(this.result);
            return e;
        }
        return this.result;
    }
    
    private void parseTagData() throws IOException {
        try {
            this.parseTagDataInSegments();
        } catch (UnexpectedCloseTagParsingException e) {}
    }
    
    private void parseTagDataInSegments() throws IOException {
        this.parseClosingData();
        this.parseElementName();
        this.parseAttributeData();
    }
    
    private void readOpenTag() throws IOException {
        char c = this.readNext();
        
        if (!isOpeningTag(c)) {
            this.unread(c);
            MarkupTag tag = MarkupTag.getTag(c);
            throw new MissingEnclosureParsingException(input.getPosition(), 
                                                       (tag != null ? MarkupTag.CLOSING_TAG.toChar() : '?'),
                                                       this.result);
        }
        this.result.confirmOpeningTag();
    }
    
    private HtmlData parseClosingData() throws IOException {
        return this.closingParser.parse(this.input, this.result);
    }
    
    private HtmlData parseElementName() throws IOException {
        return this.elementParser.parse(this.input, this.result);
    }
    
    private HtmlData parseAttributeData() throws IOException {
        return this.attributeParser.parse(this.input, this.result);
    }
    
    private void readCloseTag() throws IOException {
        char c = this.readNext();
        
        if (!isClosingTag(c)) {
            this.openTagRead(c);
        }
        this.result.confirmClosingTag();
    }
}