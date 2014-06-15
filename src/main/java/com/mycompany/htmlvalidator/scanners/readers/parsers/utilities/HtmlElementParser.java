package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.AbstractHtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlElementParser extends AbstractHtmlParser {
    public static final char elementTerminator = ' ';
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseData();
        this.clearState();
        return result;
    }
    
    private void parseData() throws IOException {
        this.parseElementName();
    }
    
    private void parseElementName() throws IOException {
        char c;
        while(this.checkChar(c = readNext()))
            this.result.updateName(c);
    }
    
    private boolean checkChar(char c) throws IOException {
        if(AbstractHtmlParser.isCloseTagEnclosure(c)) {
            this.unread(c);
            throw new UnexpectedCloseTagParsingException(this.input.getPosition(), this.result);
        }else if (AbstractHtmlParser.isOpenTagEnclosure(c)) {
            this.unread(c);
            throw new UnclosedTagParsingException(this.input.getPosition(), this.result);
        }
        return c != elementTerminator;
    }
}