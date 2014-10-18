/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.*;
import java.util.*;

import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import org.junit.*;

import com.mycompany.markuptagscanner.Tag;

public class HtmlReaderIT {
    private static final String W = String.format("%n\t %n%n%n%n \t\t\t\t    %n%n\t ");
    private static final MarkupError UNCLOSED_TAG_ERROR = new UnclosedTagParsingError(new Point(0,0), new HtmlData());
    private static final MarkupError MISSING_ENCLOSURE_ERROR = new MissingEnclosureParsingError(new Point(0,0), ' ', ' ', new HtmlData());
    private static final HtmlAttribute CLOSING_ATTR = new HtmlAttribute("/");
    
    private static final HtmlAttribute[] DEFAULT_FLAGS = {new HtmlAttribute("flag1"), 
                                                          new HtmlAttribute("flag2"), 
                                                          new HtmlAttribute("flag3")};
    private static final HtmlAttribute[] DEFAULT_ATTRS = {new HtmlAttribute("attr1", "val1"),
                                                          new HtmlAttribute("attr2", "val2"),
                                                          new HtmlAttribute("attr3", "val3")};
    private static final HtmlAttribute[] SINGLE_QUOTE_ATTRS = {new HtmlAttribute("attr1", "'val1'"),
                                                               new HtmlAttribute("attr2", "'val2'"),
                                                               new HtmlAttribute("attr3", "'val3'")};
    private static final HtmlAttribute[] DOUBLE_QUOTE_ATTRS = {new HtmlAttribute("attr1", "\"val1\""),
                                                               new HtmlAttribute("attr2", "\"val2\""),
                                                               new HtmlAttribute("attr3", "\"val3\"")};
    private MarkupReader reader;
    
    public void setState(String s) throws IOException {
        this.reader = new HtmlReader(s);
    }
    
    @Test
    public void testNext_ValidTag_TagOnlyHasName_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tag");
        
        // Test
        testReadTagAndDataMatches("<tag>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAttrFlag", DEFAULT_FLAGS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAttrFlag flag1>", exp);
        
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTwoAttrFlags", DEFAULT_FLAGS[0], DEFAULT_FLAGS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTwoAttrFlags flag1 flag2>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithMultiAttrFlags", DEFAULT_FLAGS);
        
        testReadTagAndDataMatches("<tagWithMultiAttrFlags flag1 flag2 flag3>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAttrAndVal", DEFAULT_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAttrAndVal attr1=val1>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTwoAttrsAndVals", DEFAULT_ATTRS[0], DEFAULT_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTwoAttrsAndVals attr1=val1 attr2=val2>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithMultiAttrsAndVals", DEFAULT_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAttrAndValueInSingleQuotes", SINGLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAttrAndValueInSingleQuotes attr1='val1'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTwoAttrsAndValuesInSingleQuotes", SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithMultiAttrsAndValuesInSingleQuotes", SINGLE_QUOTE_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAttrAndValueInDoubleQuotes", DOUBLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAttrAndValueInDoubleQuotes attr1=\"val1\">", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTwoAttrsAndValuesInDoubleQuotes", DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\">", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_ResultMatchesExpected() throws IOException {
        // Test
        HtmlData exp = generateData("tagWithMultiAttrsAndValuesInDoubleQuotes", DOUBLE_QUOTE_ATTRS);
        // Set up
        testReadTagAndDataMatches("<tagWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\">", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagOnlyHasName_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespace");
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespace >", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithAttrFlag", DEFAULT_FLAGS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithAttrFlag flag1 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithTwoAttrFlags", DEFAULT_FLAGS[0], DEFAULT_FLAGS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithTwoAttrFlags flag1 flag2 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithMultiAttrFlags", DEFAULT_FLAGS);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithMultiAttrFlags flag1 flag2 flag3 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithAttrAndVal", DEFAULT_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithAttrAndVal attr1=val1 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithTwoAttrsAndVals", DEFAULT_ATTRS[0], DEFAULT_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithTwoAttrsAndVals attr1=val1 attr2=val2 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithMultiAttrsAndVals", DEFAULT_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3 >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithAttrAndValueInSingleQuotes", SINGLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithAttrAndValueInSingleQuotes attr1='val1' >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithTwoAttrsAndValuesInSingleQuotes",
                                    SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithMultiAttrsAndValuesInSingleQuotes",
                                    SINGLE_QUOTE_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3' >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithAttrAndValueInDoubleQuotes", DOUBLE_QUOTE_ATTRS[0]);
        
        // Test
        
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithAttrAndValueInDoubleQuotes attr1=\"val1\" >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithTwoAttrsAndValuesInDoubleQuotes",
                                    DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_HasTrailingWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithTrailingWhitespaceWithMultiAttrsAndValuesInDoubleQuotes",
                                    DOUBLE_QUOTE_ATTRS);
        
        
        // Test
        testReadTagAndDataMatches("<tagWithTrailingWhitespaceWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\" >", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagOnlyHasName_HasClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTag");
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTag>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_HasClosingTag__ResultMatchesExpected() throws IOException {
        // Setup
        HtmlData exp = generateData("tagWithClosingTagWithAttrFlag", DEFAULT_FLAGS[0]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithAttrFlag flag1>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_HasClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithTwoAttrFlags", DEFAULT_FLAGS[0], DEFAULT_FLAGS[1]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithTwoAttrFlags flag1 flag2>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_HasClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithMultiAttrFlags", DEFAULT_FLAGS);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithMultiAttrFlags flag1 flag2 flag3>", 
                exp);
    }

    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithAttrAndVal", DEFAULT_ATTRS[0]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithAttrAndVal attr1=val1>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithTwoAttrsAndVals",
                                           DEFAULT_ATTRS[0], DEFAULT_ATTRS[1]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithTwoAttrsAndVals attr1=val1 attr2=val2>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithMultiAttrsAndVals", DEFAULT_ATTRS);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithAttrAndValueInSingleQuotes", SINGLE_QUOTE_ATTRS[0]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithAttrAndValueInSingleQuotes attr1='val1'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithTwoAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithMultiAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithAttrAndValueInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0]);
        exp.setIsClosing(true);
        
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithAttrAndValueInDoubleQuotes attr1=\"val1\">", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithTwoAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1]);
        exp.setIsClosing(true);
        // Test
        testReadTagAndDataMatches("</tagWithClosingTagWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\">", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_HasClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithClosingTagWithMultiAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS);
        exp.setIsClosing(true);
        
        //Test
        testReadTagAndDataMatches("</tagWithClosingTagWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\">", 
                exp);
    }

    @Test
    public void testNext_ValidTag_TagOnlyHasName_HasSelfClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttribute", CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttribute />", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_HasSelfClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithAttrFlag", 
                                           DEFAULT_FLAGS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithAttrFlag flag1 />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_HasSelfClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithTwoAttrFlags",
                                            DEFAULT_FLAGS[0], DEFAULT_FLAGS[1], CLOSING_ATTR);
        
        // TEst
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithTwoAttrFlags flag1 flag2 />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_HasSelfClosingTag__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithMultiAttrFlags",
                                           DEFAULT_FLAGS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithMultiAttrFlags flag1 flag2 flag3 />", 
                exp);
    }

    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithAttrAndVal",
                                           DEFAULT_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithAttrAndVal attr1=val1 />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithTwoAttrsAndVals",
                                           DEFAULT_ATTRS[0], DEFAULT_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithTwoAttrsAndVals attr1=val1 attr2=val2 />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithMultiAttrsAndVals", DEFAULT_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3 />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithAttrAndValueInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithAttrAndValueInSingleQuotes attr1='val1' />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithTwoAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithMultiAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3' />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithAttrAndValueInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithAttrAndValueInDoubleQuotes attr1=\"val1\" />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithTwoAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_HasSelfClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeWithMultiAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // TEst
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\" />", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagOnlyHasName_HasSelfClosingTagNoSpace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfter", CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfter/>", exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_HasSelfClosingTagNoSpace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithAttrFlag", 
                                            DEFAULT_FLAGS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithAttrFlag flag1/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_HasSelfClosingTagNoSpace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrFlags", 
                                           DEFAULT_FLAGS[0], DEFAULT_FLAGS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrFlags flag1 flag2/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_HasSelfClosingTagNoSpace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrFlags", DEFAULT_FLAGS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrFlags flag1 flag2 flag3/>", 
                exp);
    }

    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndVal",
                                           DEFAULT_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndVal attr1=val1/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndVals",
                                           DEFAULT_ATTRS[0], DEFAULT_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndVals attr1=val1 attr2=val2/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndVals",
                                           DEFAULT_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndValueInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndValueInSingleQuotes attr1='val1'/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndValueInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithAttrAndValueInDoubleQuotes attr1=\"val1\"/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1], CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\"/>", 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_HasSelfClosingTagNoSpace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS);
        exp.updateAttributes(CLOSING_ATTR);
        
        // Test
        testReadTagAndDataMatches("<tagWithSelfClosingAttributeNoSpaceAfterWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\"/>", 
                exp);
    }

    @Test
    public void testNext_ValidTag_TagOnlyHasName_HasAdditionalWhitespace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespace");
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespace >".replace(" ", W), exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrFlag_HasAdditionalWhitespace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithAttrFlag", DEFAULT_FLAGS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithAttrFlag flag1 >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsFlags_HasAdditionalWhitespace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithTwoAttrFlags",
                                           DEFAULT_FLAGS[0], DEFAULT_FLAGS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithTwoAttrFlags flag1 flag2 >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrsFlags_HasAdditionalWhitespace__ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithMultiAttrFlags", DEFAULT_FLAGS);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithMultiAttrFlags flag1 flag2 flag3 >".replace(" ", W),
                exp);
    }

    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValue_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithAttrAndVal", DEFAULT_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithAttrAndVal attr1 = val1 >".replace(" ", W), 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrsWithValues_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithTwoAttrsAndVals", DEFAULT_ATTRS[0], DEFAULT_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithTwoAttrsAndVals attr1 = val1 attr2 = val2 >".replace(" ",  W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMutliAttrsWithValues_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithMultiAttrsAndVals", DEFAULT_ATTRS);
        
        // TEst
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithMultiAttrsAndVals attr1 = val1 attr2 = val2 attr3 = val3 >".replace(" ", W), 
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInSingleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithAttrAndValueInSingleQuotes", SINGLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithAttrAndValueInSingleQuotes attr1 = 'val1' >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInSingleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithTwoAttrsAndValuesInSingleQuotes",
                                           SINGLE_QUOTE_ATTRS[0], SINGLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithTwoAttrsAndValuesInSingleQuotes attr1 = 'val1' attr2 = 'val2' >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInSingleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithMultiAttrsAndValuesInSingleQuotes", SINGLE_QUOTE_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithMultiAttrsAndValuesInSingleQuotes attr1 = 'val1' attr2 = 'val2' attr3 = 'val3' >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndAttrAndValueInDoubleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithAttrAndValueInDoubleQuotes", DOUBLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithAttrAndValueInDoubleQuotes attr1 = \"val1\" >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndTwoAttrAndValueInDoubleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithTwoAttrsAndValuesInDoubleQuotes",
                                           DOUBLE_QUOTE_ATTRS[0], DOUBLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithTwoAttrsAndValuesInDoubleQuotes attr1 = \"val1\" attr2 = \"val2\" >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_TagHasNameAndMultiAttrAndValueInDoubleQuotes_HasAdditionalWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("tagWithAdditionalWhitespaceWithMultiAttrsAndValuesInDoubleQuotes", DOUBLE_QUOTE_ATTRS);
        
        // Test
        testReadTagAndDataMatches("<tagWithAdditionalWhitespaceWithMultiAttrsAndValuesInDoubleQuotes attr1 = \"val1\" attr2 = \"val2\" attr3 = \"val3\" >".replace(" ", W),
                exp);
    }
    
    @Test
    public void testNext_ValidTag_CommentTag_IsEmptyCommentTag_ResultMatchesExpceted() throws IOException {
        // Set up
        HtmlData exp = generateData("!--");
        
        // Test
        testReadTagAndDataMatches("<!---->", exp);
    }
    
    @Test
    public void testNext_ValidTag_CommentTag_HasCommentData_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("!--", new HtmlAttribute(" some comment data "));
        
        // Test
        testReadTagAndDataMatches("<!-- some comment data -->", exp);
    }
    
    @Test
    public void testNext_ValidTag_CommentTag_HasAlternateCommentData_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData("!--", new HtmlAttribute("some other comment data"));
        
        // Test
        testReadTagAndDataMatches("<!--some other comment data-->", exp);
    }
    
    @Test
    public void testNext_ValidTag_CommentTag_HasAlotOfWhitespace_ResultMatchesExpected() throws IOException {
        // Set up
        String sub = W.replace(String.format("%n"), "");
        HtmlData exp = generateData("!--", new HtmlAttribute(" comment with lots of whitespace ".replace(" ", sub)));
        
        // Test
        testReadTagAndDataMatches("<!-- comment with lots of whitespace -->".replace(" ", W), exp);
        
    }
    
    @Test
    public void testNext_ValidTag_EntireFileWithMultipleTags_ResultMatchesExpected() throws IOException {
        Iterator<HtmlData> exp = HtmlDataGenerator.getValidHtmlDataIterator();
        HtmlReader data = new HtmlReader(HtmlDataGenerator.getValidHtmlStream());
        
        int i = 0;
        while(data.hasNext()) {
            i++;
            assertEquals(exp.next(), data.next());
        }
        data.close();
        assertTrue(i > 0);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTag");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTag", false);
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTag", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagHasAttrIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithAttrFlag flag");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagHasAttrIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithAttrFlag", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithAttrFlag flag1", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagHasTwoAttrsIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithTwoAttrFlags flag1 flag2");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagHasTwoAttrsIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithTwoAttrFlags", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        exp.updateAttributes(DEFAULT_FLAGS[1]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithTwoAttrFlags flag1 flag2",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagMultiAttrsIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithMultiAttrFlags flag1 flag2 flag3");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagMultiAttrsIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithMultiAttrFlags", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        exp.updateAttributes(DEFAULT_FLAGS[1]);
        exp.updateAttributes(DEFAULT_FLAGS[2]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithMultiAttrFlags flag1 flag2 flag3", 
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagAttrAndValIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithAttrAndVal attr1=val1");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagAttrAndValIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithAttrAndVal", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithAttrAndVal attr1=val1",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithTwoAttrAndValsIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithTwoAttrsAndVals attr1=val1 attr2=val2");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithTwoAttrAndValsIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithTwoAttrsAndVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(DEFAULT_ATTRS[1]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithTwoAttrsAndVals attr1=val1 attr2=val2",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithMultiAttrsWithValsIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithMultiAttrsWithValsIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithMultiAttrsAndVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(DEFAULT_ATTRS[1]);
        exp.updateAttributes(DEFAULT_ATTRS[2]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3", 
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithAttrAndValsInSingleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithAttrAndValueInSingleQuotes attr='val'");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithAttrAndValsInSingleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithAttrAndValueInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithAttrAndValueInSingleQuotes attr1='val1'",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithTwoAttrsAndValsInSingleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithTwoAttrsAndValsInSingleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithTwoAttrsAndValuesInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithMultiAttrsAndValsInSingleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithMultiAttrsAndValsInSingleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithMultiAttrsAndValuesInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[1]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[2]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithAttrAndValsInDoubleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithAttrAndValueInDoubleQuotes attr=\"val\"");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithAttrAndValsInDoubleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithAttrAndValueInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithAttrAndValueInDoubleQuotes attr1=\"val1\"",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithTwoAttrsAndValsInDoubleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\"");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithTwoAttrsAndValsInDoubleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithTwoAttrsAndValuesInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[1]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\"",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithMultiAttrsAndValsInDoubleQuotesIsMissingClosingBracket_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithMissingClosingTagWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\"");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithMultiAttrsAndValsInDoubleQuotesIsMissingClosingBracket_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithMissingClosingTagWithMultiAttrsAndValuesInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[1]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[2]);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithMissingClosingTagWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\"",
                exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFAfterClosingTag_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("</");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterClosingTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "", false);
        exp.setIsClosing(true);
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("</", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFInCommentTag_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<!--some comment data");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFInCommentTag_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "!--", false);
        exp.updateAttributes(new HtmlAttribute("some comment data"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<!--some comment data", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFInSingleQuoteAttr_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFInSingleQuoteAttr 'someAttr");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFInSingleQuoteAttr_ResultMatchesExpected() throws IOException {
        // Arrange
        HtmlData exp = generateData(true, "tagWithEOFInSingleQuoteAttr", false);
        exp.updateAttributes(new HtmlAttribute("'someAttr"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFInSingleQuoteAttr 'someAttr", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFInDoubleQuoteAttr_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFInDoubleQuoteAttr \"someAttr");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFInDoubleQuoteAttr_ResultMatchesExpected() throws IOException {
        // Arrange
        HtmlData exp = generateData(true, "tagWithEOFInDoubleQuoteAttr", false);
        exp.updateAttributes(new HtmlAttribute("\"someAttr"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFInDoubleQuoteAttr \"someAttr", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWitEOFAfterSingleAttrAtEquals_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFWithAttrWithVal attr1=");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterSingleAttrAtEquals_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithEOFWithAttrWithVal", false);
        exp.updateAttributes(new HtmlAttribute("attr1"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFWithAttrWithVal attr1=", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFAfterTwoAttrAtEquals_ThrowsExpectedException() throws IOException {
        //Arrange
        this.setState("<tagWithEOFWithTwoAttrsWithVals attr1=val1 attr2=");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterTwoAttrAtEquals_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithEOFWithTwoAttrsWithVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(new HtmlAttribute("attr2"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFWithTwoAttrsWithVals attr1=val1 attr2=", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWithEOFAfterThreeAttrAtEquals_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFWithThreeAttrsWithVals attr1=val1 attr2=val2 attr3=");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterThreeAttrAtEquals_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithEOFWithThreeAttrsWithVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(DEFAULT_ATTRS[1]);
        exp.updateAttributes(new HtmlAttribute("attr3"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFWithThreeAttrsWithVals attr1=val1 attr2=val2 attr3=", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWitEOFAfterSingleAttrWithValInSingleQuotes_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFWithAttrWithVal attr='someVal");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterSingleAttrWithValInSingleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithEOFWithAttrWithVal", false);
        exp.updateAttributes(new HtmlAttribute("attr", "'someVal"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFWithAttrWithVal attr='someVal", exp);
    }
    
    @Test(expected=EndOfInputParsingError.class)
    public void testNext_InvalidTag_TagWitEOFAfterSingleAttrWithValInDoubleQuotes_ThrowsExpectedException() throws IOException {
        // Arrange
        this.setState("<tagWithEOFWithAttrWithVal attr=\"someVal");
        
        // Apply + Assert
        this.reader.next();
    }
    
    @Test
    public void testNext_InvalidTag_TagWithEOFAfterSingleAttrWithValInDoubleQuotes_ResultMatchesExpected() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithEOFWithAttrWithVal", false);
        exp.updateAttributes(new HtmlAttribute("attr", "\"someVal"));
        
        // Test
        testReadInvalidTagAndDataMatches_ThrowsException("<tagWithEOFWithAttrWithVal attr=\"someVal", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        // Test
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTag>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        // Test
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithAttrFlag flag>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithTwoAttrFlags flag1 flag2>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithMultiAttrFlags flag1 flag2 flag3>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrWithValIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithAttrAndVal attr=val>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsAndValsIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithTwoAttrsAndVals attr1=val1 attr2=val2>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsAndValsIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithMultiAttrsAndVals attr1=val1 attr2=val2 attr3=val3>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrAndValInSingleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithAttrAndValueInSingleQuotes attr='val'>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsAndValsInSingleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithTwoAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2'>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsAndValsInSingleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithMultiAttrsAndValuesInSingleQuotes attr1='val1' attr2='val2' attr3='val3'>");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrAndValInDoubleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithAttrAndValueInDoubleQuotes attr=\"val\">");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsAndValsInDoubleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithTwoAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\">");
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMutliAttrsAndValsInDoubleQuoteIsMissingOpeningTag_ResultIsEmptyHtmlData() throws IOException {
        testReadTagAndDataMatches_MissingOpening("tagWithMissingOpeningTagWithMultiAttrsAndValuesInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\">");
    }
    
    private void testReadTagAndDataMatches_MissingOpening(String s) throws IOException {
        // Set up
        HtmlData exp = generateEmptyData(false, true);
        exp.getErrorReporter().addError(MISSING_ENCLOSURE_ERROR);

        exp.setLocation(new Point(s.length(), 1));
        
        // Test
        testReadTagAndDataMatches(s, exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosing", false);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        //Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosing<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrFlagWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithAttrFlag", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithAttrFlag flag1<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrFlagsWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithTwoAttrFlags", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        exp.updateAttributes(DEFAULT_FLAGS[1]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithTwoAttrFlags flag1 flag2<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrFlagsWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithMultiAttrFlags", false);
        exp.updateAttributes(DEFAULT_FLAGS[0]);
        exp.updateAttributes(DEFAULT_FLAGS[1]);
        exp.updateAttributes(DEFAULT_FLAGS[2]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithMultiAttrFlags flag1 flag2 flag3<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrWithValWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithAttrWithVal", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithAttrWithVal attr1=val1<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsWithValsWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithTwoAttrsWithVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(DEFAULT_ATTRS[1]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithTwoAttrsWithVals attr1=val1 attr2=val2<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsWithValsWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithMultiAttrsWithVals", false);
        exp.updateAttributes(DEFAULT_ATTRS[0]);
        exp.updateAttributes(DEFAULT_ATTRS[1]);
        exp.updateAttributes(DEFAULT_ATTRS[2]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithMultiAttrsWithVals attr1=val1 attr2=val2 attr3=val3<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrWithValInSingleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithAttrWithValInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithAttrWithValInSingleQuotes attr1='val1'<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsWithValsInSingleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithTwoAttrsWithValsInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[1]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithTwoAttrsWithValsInSingleQuotes attr1='val1' attr2='val2'<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsWithValsInSingleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithMultiAttrsWithValsInSingleQuotes", false);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[1]);
        exp.updateAttributes(SINGLE_QUOTE_ATTRS[2]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithMultiAttrsWithValsInSingleQuotes attr1='val1' attr2='val2' attr3='val3'<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithAttrWithValInDoubleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithAttrWithValInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithAttrWithValInDoubleQuotes attr1=\"val1\"<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithTwoAttrsWithValsInDoubleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithTwoAttrsWithValsInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[1]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithTwoAttrsWithValsInDoubleQuotes attr1=\"val1\" attr2=\"val2\"<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_tagWithMultiAttrsWithValsInDoubleQuotesWithOpeningInsteadOfClosing_ResultIsExpectedData() throws IOException {
        // Set up
        HtmlData exp = generateData(true, "tagWithOpeningInsteadOfClosingWithMultiAttrsWithValsInDoubleQuotes", false);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[0]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[1]);
        exp.updateAttributes(DOUBLE_QUOTE_ATTRS[2]);
        exp.getErrorReporter().addError(UNCLOSED_TAG_ERROR);
        
        // Test
        testReadTagAndDataMatches("<tagWithOpeningInsteadOfClosingWithMultiAttrsWithValsInDoubleQuotes attr1=\"val1\" attr2=\"val2\" attr3=\"val3\"<", exp);
    }
    
    @Test
    public void testNext_InvalidTag_EntireFileWithTags_ResultsMatchExpected() throws IOException {
        Iterator<HtmlData> exp = HtmlDataGenerator.getInvalidHtmlDataIterator();
        MarkupReader data = new HtmlReader(HtmlDataGenerator.getInvalidHtmlStream());
        
        int i = 0;
        while(data.hasNext()) {
            i++;
            assertEquals(exp.next(), data.next());
        }
        data.close();
        assertTrue(i > 0);
    }
    
    @Test
    public void testNext_ValidAndInvalidTags_EntireFileWithMixedTags_ResultsMatchExpected() throws IOException {
        Iterator<HtmlData> exp = HtmlDataGenerator.getMixedHtmlDataIterator();
        MarkupReader data = new HtmlReader(HtmlDataGenerator.getMixedHtmlStream());
        
        int i = 0;
        while(data.hasNext()) {
            i++;
            HtmlData e = exp.next();
            Tag q = data.next();
            assertEquals(e, q);
        }
        data.close();
        assertTrue(i > 0);
    }

/*
    @Test
    public void SANITY_CHECK() throws IOException {
        // Sanity Check can be performed here
        HtmlDataGenerator.updateSerializedFiles();
    }
*/

    private HtmlData generateEmptyData(boolean hasOpening, boolean hasClosing) {
        HtmlData result = new HtmlData();
        if(hasOpening) result.confirmOpeningTag();
        if(hasClosing) result.confirmClosingTag();
        result.setLocation(new Point(1, 1));
        return result;
    }
    
    private HtmlData generateData(boolean hasOpening, String name, boolean hasClosing) {
        HtmlData data = generateEmptyData(hasOpening, hasClosing);
        data.setName(name);
        return data;
    }
    
    private HtmlData generateData(String name, HtmlAttribute...attrs) {
        HtmlData data = generateData(true, name, true);
        
        for (HtmlAttribute attr : attrs)
            data.updateAttributes(attr);

        return data;
    }
    
    private void testReadTagAndDataMatches(String s, HtmlData exp) throws IOException {
        // Arrange
        Tag data;
        this.setState(s);
        
        // Apply
        data = this.reader.next();
        
        // Assert
        assertEquals(exp, data);
    }
    
    private void testReadInvalidTagAndDataMatches_ThrowsException(String s, HtmlData exp) throws IOException {
        // Arrange
        HtmlData data = null;
        this.setState(s);
        
        // Apply
        try {
            this.reader.next();
        } catch (ParsingError e) {
            data = e.getHtmlData();
        }
        // Assert
        assertEquals(exp, data);
    }
    
    @After
    public void tearDown() throws IOException {
        if (this.reader != null) {reader.close();}
        this.reader = null;
    }
}
