package com.mycompany.htmlvalidator.parsers;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;

public class HtmlTagDataParserImpl implements HtmlTagDataParser {
    private final Pattern expectedPattern = Pattern.compile("^<.*>$");
    private final String closingToken = "/";
    
    private String element;
    private boolean isClosing;
    
    @Override
    public HtmlData parse(String data) throws IllegalHtmlTagException {
        this.parseAndGenerateState(data.trim());
        return this.consumeStateAndGenerateHtmlData();
    }
    
    private void parseAndGenerateState(String data) throws IllegalHtmlTagException {
        this.validateInputTag(data);
        this.parseTag(data);
        this.validateState();
    }
    
    private HtmlData consumeStateAndGenerateHtmlData() {
        HtmlData result = new HtmlData(element, !isClosing);
        this.clearState();
        return result;
    }
    
    private void parseTag(String tag) throws IllegalHtmlTagException {
        this.parseTagData(tag.substring(1, tag.length() -1));
    }
    
    private void parseTagData(String tagData) throws IllegalHtmlTagException {
        this.isClosing = tagData.startsWith(this.closingToken);
        this.parseElementData(tagData);
    }
    
    private void parseElementData(String elementData) throws IllegalHtmlTagException {
        Scanner input = new Scanner(elementData);
        try {
            String element = input.next();
            this.element = trimClosingTags(element);
        } catch (Exception e) {
            throw new IllegalHtmlTagException("Missing tag element name!");
        } finally {
            input.close();
        }
    }
    
    private String trimClosingTags(String element) {
        int startIndex = 0;
        int endIndex = element.length();
        
        if(element.startsWith(this.closingToken))
            startIndex++;
        if(element.endsWith(this.closingToken))
            endIndex--;
        return element.substring(startIndex, endIndex);
    }
    
    private void validateInputTag(String htmlTag) throws IllegalHtmlTagException {
        Matcher matcher = this.expectedPattern.matcher(htmlTag);
        if (!matcher.matches())
            throw new IllegalHtmlTagException("Improper html tag format!");
    }
    
    private void validateState() throws IllegalHtmlTagException {
        if (this.element == null || this.element == "")
            throw new IllegalHtmlTagException("Missing tag element name!");
    }
    
    private void clearState() {
        this.element = null;
        this.isClosing = false;
    }
}