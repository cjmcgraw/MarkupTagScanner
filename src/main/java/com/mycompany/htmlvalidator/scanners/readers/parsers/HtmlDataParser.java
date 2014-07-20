package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParser extends DataParser{
    private static final String CLASS_NAME = "HtmlDataParser";
    private static final String FIRST_FIELD_NAME = "closingParser";
    private static final String SECOND_FIELD_NAME = "elementParser";
    private static final String THIRD_FIELD_NAME = "attributeParser";
    
    private HtmlUtilityParser closingParser;
    private HtmlUtilityParser elementParser;
    private HtmlUtilityParser attributeParser;
    
    public HtmlDataParser() {
        this(new HtmlClosingParser(), 
             new HtmlElementParser(), 
             new HtmlAttributesParser());
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
            e.setHtmlData(this.getResult());
            return e;
        }
        return this.getResult();
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
        char c = this.read();
        
        if (!isOpeningTag(c)) {
            this.unread(c);
            MarkupTag tag = MarkupTag.getTag(c);
            throw new MissingEnclosureParsingException(this.currentPosition(), 
                                                       (tag != null ? MarkupTag.CLOSING_TAG.toChar() : '?'),
                                                       this.getResult());
        }
        this.getResult().confirmOpeningTag();
    }
    
    private HtmlData parseClosingData() throws IOException {
        this.validateState();
        return this.closingParser.parse(this.getInput(), this.getResult());
    }
    
    private HtmlData parseElementName() throws IOException {
        this.validateState();
        return this.elementParser.parse(this.getInput(), this.getResult());
    }
    
    private HtmlData parseAttributeData() throws IOException {
        this.validateState();
        return this.attributeParser.parse(this.getInput(), this.getResult());
    }
    
    private void readCloseTag() throws IOException {
        char c = this.read();
        try {
            this.validateChar(c);
        } catch(UnexpectedCloseTagParsingException e) {
            this.read();
            this.getResult().confirmClosingTag();
        }
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private boolean isMissingState() {
        return this.closingParser == null ||
               this.elementParser == null ||
               this.attributeParser == null;
    }
    
    private String getMissingFieldName() {
        if (this.closingParser == null)
            return FIRST_FIELD_NAME;
        if (this.elementParser == null)
            return SECOND_FIELD_NAME;
        if (this.attributeParser == null)
            return THIRD_FIELD_NAME;
        return "Unknown field is missing!";
    }
}