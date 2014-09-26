package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParser extends DataParser{
    private static final String CLASS_NAME = "HtmlDataParser";
    private static final String FIRST_FIELD_NAME = "closingParser";
    private static final String SECOND_FIELD_NAME = "elementParser";
    private static final String THIRD_FIELD_NAME = "attributeParser";
    
    private HtmlSubParser closingParser;
    private HtmlSubParser elementParser;
    private HtmlSubParser attributeParser;
    
    public HtmlDataParser() {
        this(new HtmlClosingSubParser(),
             new HtmlElementSubParser(),
             new HtmlAttributesSubParser());
        }
    
    public HtmlDataParser(HtmlSubParser closingParser,
                          HtmlSubParser elementParser,
                          HtmlSubParser attributeParser) {
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
            return this.getResult();
        } catch (NonFatalParsingException e) {
            HtmlData data = e.getHtmlData();
            data.getErrorReporter().addError(e);
            return data;
        }
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
           throw this.invalidOpeningTagRead();
        }
        this.getResult().confirmOpeningTag();
    }
    
    private ParsingException invalidOpeningTagRead() throws IOException {
        if (!isClosingTag())
            this.unread();
        else
            this.getResult().confirmClosingTag();
        char exp = MarkupTag.OPENING_TAG.toChar();
        return this.getMissingEnclosureParsingException(exp, getCurrChar());
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
        
        if (!isClosingTag(c)) {
            this.unread(c);
            char exp = MarkupTag.CLOSING_TAG.toChar();
            throw this.getMissingEnclosureParsingException(exp, c);
        }
        this.getResult().confirmClosingTag();
    }
    
    private ParsingException getMissingEnclosureParsingException(char missing, char found) {
        return new MissingEnclosureParsingException(this.currentPosition(), missing, found, this.getResult());
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