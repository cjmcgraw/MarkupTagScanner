package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.ParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlAttributeParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlClosingParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlElementParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.HtmlUtilityParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParser extends HtmlParser{
    private HtmlUtilityParser closingParser;
    private HtmlUtilityParser elementParser;
    private HtmlUtilityParser attributeParser;
    
    public HtmlDataParser() {
        this(new HtmlClosingParser(), 
             new HtmlElementParser(), 
             new HtmlAttributeParser());
        }
    
    public HtmlDataParser(HtmlUtilityParser closingParser,  HtmlUtilityParser elementParser, HtmlUtilityParser attributeParser) {
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
        
        if (!HtmlParser.isOpenTagEnclosure(c)) {
            this.unread(c);
            throw new MissingEnclosureParsingException(input.getPosition(), 
                                                       HtmlParser.OPEN_TAG_ENCLOSURE,
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
        
        if (!isCloseTagEnclosure(c)) {
            this.openTagRead(c);
        }
        this.result.confirmClosingTag();
    }
    
    private void setState(PushbackAndPositionReader input) {
        this.setState(input, new MutableHtmlData());
    }
}