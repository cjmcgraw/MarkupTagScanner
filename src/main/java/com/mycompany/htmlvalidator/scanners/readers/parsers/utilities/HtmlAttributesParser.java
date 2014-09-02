package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.ComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlAttributesParser extends HtmlUtilityParser {
    private static final String CLASS_NAME = "HtmlAttributeParser";
    private static final String FIRST_FIELD_NAME = "enclosureParser";
    private static final String SECOND_FIELD_NAME = "parser";
    private static final String THIRD_FIELD_NAME = "commentParser";
    private static final String FOURTH_FIELD_NAME = "consumer";
    
    private static final String COMMENT_NAME = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    private HtmlComponentEnclosureParser enclosureParser;
    private HtmlComponentAttributeParser attributeParser;
    private HtmlComponentAttributeParser commentParser;
    private Consumer whitespaceConsumer;
    
    public HtmlAttributesParser() {
        this(new HtmlQuoteEnclosureParser(),
             new HtmlSingleAttributeParser(),
             new HtmlCommentAttributeParser(),
             new WhitespaceConsumer());
    }
    
    public HtmlAttributesParser(HtmlComponentEnclosureParser enclosureParser,
                                HtmlComponentAttributeParser attributeParser,
                                HtmlComponentAttributeParser commentParser,
                                Consumer whitespaceConsumer) {
        super();
        this.enclosureParser = enclosureParser;
        this.attributeParser = attributeParser;
        this.commentParser = commentParser;
        this.whitespaceConsumer = whitespaceConsumer;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseAttributes();
        return result;
    }
    
    private void parseAttributes() throws IOException {
        if (this.resultIsComment())
            this.parseCommentData();
        else 
            this.parseStandardAttributes();
    }
    
    private boolean resultIsComment() {
        return this.getResult().getName().startsWith(COMMENT_NAME);
    }
    
    private void parseCommentData() throws IOException{
        HtmlAttribute attribute = this.parseCommentAttribute();
        this.getResult().updateAttributes(attribute);
    }
    
    private void parseStandardAttributes() throws IOException {
        this.updateCurrentCharacter();
        
        while(this.isValidCharacter()) {
            this.updateResultAttributes();
            this.updateCurrentCharacter();
        }
    }
    
    private void updateCurrentCharacter() throws IOException {
        this.consumeWhitespace();
        this.read();
    }
    
    private boolean isValidCharacter() throws IOException {
        try {
            return this.validateChar(this.getCurrChar());
        } catch (UnexpectedCloseTagParsingException e) {
            return false;
        }
    }
    
    private void consumeWhitespace() throws IOException {
        this.validateState();
        this.whitespaceConsumer.parse(this.getInput());
    }
    
    private void updateResultAttributes() throws IOException {
        HtmlAttribute attribute = this.getAttribute();
        this.getResult().updateAttributes(attribute);
    }
    
    private HtmlAttribute getAttribute() throws IOException {
        if (this.isQuoteEnclosure()) 
            return this.parseQuoteEnclosure();
        return this.parseGeneralAttribute();
    }
    
    private HtmlAttribute parseCommentAttribute() throws IOException {
        this.validateState();
        return this.commentParser.parse(this.getInput());
    }
    
    private HtmlAttribute parseQuoteEnclosure() throws IOException {
        this.validateState();
        this.unread();
        return this.getEnclosureAttributeData();
    }
    
    private HtmlAttribute getEnclosureAttributeData() throws IOException {
        String name;
        HtmlAttribute result = new HtmlAttribute();
        
        try {
            name = this.getEnclosureDataName();
        } catch (ComponentException err) {
            name = err.getData();
            result.addError(err);
        }
        result.setName(name);
        return result;
    }
    
    private String getEnclosureDataName() throws IOException {
        return this.enclosureParser.parse(this.getInput());
    }
    
    private HtmlAttribute parseGeneralAttribute() throws IOException {
        this.validateState();
        this.unread();
        return this.attributeParser.parse(this.getInput());
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private boolean isMissingState() {
        return this.enclosureParser == null ||
               this.attributeParser == null ||
               this.commentParser == null ||
               this.whitespaceConsumer == null;
    }
    
    private String getMissingFieldName() {
        if (this.enclosureParser == null)
            return FIRST_FIELD_NAME;
        if (this.attributeParser == null)
            return SECOND_FIELD_NAME;
        if (this.commentParser == null)
            return THIRD_FIELD_NAME;
        if (this.whitespaceConsumer == null)
            return FOURTH_FIELD_NAME;
        return "Unknown Field Caused Error";
    }
}