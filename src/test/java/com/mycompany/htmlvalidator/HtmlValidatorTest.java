/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator;

import com.mycompany.htmlvalidator.MarkupTagScanners.*;
import com.mycompany.htmlvalidator.errors.*;
import com.mycompany.htmlvalidator.printers.PrinterMock;
import com.mycompany.htmlvalidator.utilities.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class HtmlValidatorTest {
    public static final String LINE_FRMT = "%s%n";
    public static final String FILLER = "the quick brown fox jumped over the lazy dog";

    private HtmlValidator validator;
    private TagStackMock tagStack;
    private PrinterMock printer;


    @Before
    public void setUp() {
        this.printer = new PrinterMock();
        this.tagStack = new TagStackMock();

        TagStackFactory factory = new TagStackFactoryMock(tagStack);
        this.validator = new HtmlValidator(printer, factory);
    }

    @After
    public void tearDown() {
        this.printer = null;
        this.tagStack = null;
        this.validator = null;
    }

    @Test
    public void testValidate_SingleTag_OpeningTag_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_StandardTag_PrinterContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_OpeningTag_NoIndentation_PrinterContainsExpected() {
        // Test
        testValidate_StandardTag_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_OpeningTag_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_StandardTag_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_NoIndentation_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"));

        // Test
        testValidate_StandardTag_PrinterContainsExpected(closingTags);
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_NoIndentation_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"), generateTag("tag2"));

        // Test
        testValidate_StandardTag_PrinterContainsExpected(closingTags);
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_NoIndentation_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));

        // Test
        testValidate_StandardTag_PrinterContainsExpected(closingTags);
    }

    private void testValidate_StandardTag_PrinterContainsExpected(Tag... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = Arrays.asList(tags);

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_SingleTag_OpeningTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_ErrorTag_PrinterContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_OpeningTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_ErrorTag_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_OpeningTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_ErrorTag_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"));

        // Test
        testValidate_ErrorTag_PrinterContainsExpected(closingTags);
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"), generateTag("tag2"));

        // Test
        testValidate_ErrorTag_PrinterContainsExpected(closingTags);
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_NoIndentation_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] closingTags = createClosingTagsAndSetUpTagStack(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));

        // Test
        testValidate_ErrorTag_PrinterContainsExpected(closingTags);
    }

    private void testValidate_ErrorTag_PrinterContainsExpected(Tag... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        for (int i = 0; i < tags.length; i++) {
            Tag tag = tags[i];
            MarkupError err = generateError("err" + i);
            tag.getErrorReporter().addError(err);
            data.add(tag);
            exp.add(formatLine(err));
            exp.add(formatLine());
        }

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_ClosingTag_EmptyStack_PrinterDataContainsExpected(TagMock... tags) {
        // Test
        testValidate_ClosingTag_NoOpeningTag_PrinterDataContainsExpected(HtmlValidator.UNEXPECTED_CLOSE_TAG, tags);
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_ClosingTag_NonMatchingOpeningTag_printerDataContainsExpected(TagMock... tags) {
        // Set up
        loadDataToPopWith(generateTag("openingTag"));

        // Test
        testValidate_ClosingTag_NoOpeningTag_PrinterDataContainsExpected(String.format(HtmlValidator.NON_MATCHING_ERROR_MSG, tagStack.peek()), tags);
    }

    private void testValidate_ClosingTag_NoOpeningTag_PrinterDataContainsExpected(String errMsg,  TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<String> result;

        List<Tag> data = new ArrayList<>();

        for (TagMock tag : tags) {
            tag.isClosing = true;
            data.add(tag);
            exp.add(formatLine(errMsg));
        }

        // Apply
        validator.validate(data);

        // This code here is required to trim off the end sequence
        // which contains the unexpected close tag errors which will
        // be tested for at a different location
        result = getPrintedData(0, printer.getPrintData().size() - tagStack.dataToPop.size());

        // Assert
        assertEquals(exp, result);
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_WithErrors_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_WithErrors_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_WithErrors_EmptyStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_EmptyStack_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_ClosingTag_WithErrors_EmptyStack_PrinterDataContainsExpected(TagMock... tags) {
        // Test
        testValidate_ClosingTag_WithErrors_NoOpeningTag_PrinterDataContainsExpected(HtmlValidator.UNEXPECTED_CLOSE_TAG, tags);
    }

    @Test
    public void testValidate_SingleTag_ClosingTag_WithErrors_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_ClosingTag_WithErrors_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_ClosingTag_WithErrors_NonMatchingOpeningTagOnStack_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_ClosingTag_WithErrors_NonMatchingOpeningTag_printerDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_ClosingTag_WithErrors_NonMatchingOpeningTag_printerDataContainsExpected(TagMock... tags) {
        // Set up
        loadDataToPopWith(generateTag("openingTag"));

        // Test
        testValidate_ClosingTag_WithErrors_NoOpeningTag_PrinterDataContainsExpected(String.format(HtmlValidator.NON_MATCHING_ERROR_MSG, tagStack.peek()), tags);
    }

    private void testValidate_ClosingTag_WithErrors_NoOpeningTag_PrinterDataContainsExpected(String errMsg, TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<String> result;

        List<Tag> data = new ArrayList<>();

        int i = 0;
        for (TagMock tag : tags) {
            tag.isClosing = true;
            MarkupError err = generateError("err" + i);

            tag.getErrorReporter().addError(err);

            data.add(tag);

            exp.add(formatLine(err));
            exp.add(formatLine());
            exp.add(formatLine(errMsg));
        }

        // Apply
        validator.validate(data);

        // This code here is required to trim off the end sequence
        // which contains the unexpected close tag errors which will
        // be tested for at a different location
        result = getPrintedData(0, printer.getPrintData().size() - tagStack.dataToPop.size());

        // Assert
        assertEquals(exp, result);
    }

    @Test
    public void testValidate_SingleTag_SelfClosing_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_SelfClosingTag_PrinterDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_SelfClosing_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_SelfClosingTag_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_SelfClosing_NoIndentation_PrinterDataContainsExpected() {

        // Test
        testValidate_SelfClosingTag_PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_SelfClosingTag_PrinterDataContainsExpected(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        for (TagMock tag : tags) {
            tag.isSelfClosing = true;
            data.add(tag);
        }

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_SingleTag_SelfClosing_WithErrors_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_SelfClosingTag_WithErrors__PrinterDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTags_SelfClosing_WithErrors_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_SelfClosingTag_WithErrors__PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTags_SelfClosing_WithErrors_NoIndentation_PrinterDataContainsExpected() {
        // Test
        testValidate_SelfClosingTag_WithErrors__PrinterDataContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_SelfClosingTag_WithErrors__PrinterDataContainsExpected(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        for (TagMock tag : tags) {
            tag.isSelfClosing = true;
            MarkupError err = generateError("err");
            tag.getErrorReporter().addError(err);

            data.add(tag);
            exp.add(formatLine(err));
            exp.add(formatLine());
        }

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_SingleTagInTagStack_NoClosingTags_NoErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_TwoTagsInTagStack_NoClosingTags_NoErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(generateTag("tag1"),
                                                                                 generateTag("tag2"));
    }

    @Test
    public void testValidate_ThreeTagsInTagStack_NoClosingTags_NoErrors_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(generateTag("tag1"),
                                                                                 generateTag("tag2"),
                                                                                 generateTag("tag3"));
    }

    @Test
    public void testValidate_SingleTagInStack_NoClosingTags_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock tag = generateTag("tag1", generateError("err1"));

        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(tag);
    }

    @Test
    public void testValidate_TwoTagsInStack_NoClosingTags_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] tags = {generateTag("tag1", generateError("err1")),
                          generateTag("tag2", generateError("err1"), generateError("err2"))};

        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(tags);
    }

    @Test
    public void testValidate_ThreeTagsInStack_NoClosingTags_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] tags = {generateTag("tag1", generateError("err1")),
                          generateTag("tag2", generateError("err1"), generateError("err2")),
                          generateTag("tag3", generateError("err1"), generateError("err2"), generateError("err3"))};

        // Test
        testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(tags);
    }

    private void testValidate_TagStackRemaining_NoClosingTags_PrinterDataContainsExpected(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();
        List<Tag> remaining = new ArrayList<>();

        for (TagMock tag : tags) {
            remaining.add(tag);
            exp.add(formatLine(String.format(HtmlValidator.UNCLOSED_TAG_MSG, tag)));

            for (MarkupError err : tag.getErrorReporter().getErrors())
                exp.add(formatLine(err));

            if (tag.getErrorReporter().hasErrors())
                exp.add(formatLine());
        }

        tagStack.dataToPop = remaining;

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_SingleClosingTag_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(1);
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_TwoClosingTags_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(2);
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_ThreeClosingTags_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(3);
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_FiveClosingTags_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(5);
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_NineClosingTags_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(9);
    }

    @Test
    public void testValidate_TenTagsInStackRemaining_TenClosingTags_PrinterDataContainsExpected() {
        // Test
        testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(10);
    }

    private void testValidate_TagStackRemaining_WithClosingTags_PrinterDataContainsExpected(int numOfClosingTags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();
        List<Tag> remaining = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TagMock tag = generateTag("tag" + i);

            remaining.add(tag);

            if (i < numOfClosingTags) {
                TagMock closingTag = tag.copy();
                closingTag.isClosing = true;

                data.add(closingTag);
            } else {
                exp.add(formatLine(String.format(HtmlValidator.UNCLOSED_TAG_MSG, tag)));
            }
        }

        tagStack.dataToPop = remaining;

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    @Test
    public void testValidate_SingleTagInStack_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] tags = {generateTag("tag", generateError("err1"))};

        // Test
        testValidate_TagStackRemaining_WithErrors_PrinterDataContainsExpected(tags);
    }

    @Test
    public void testValidate_TwoTagsInStack_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] tags = {generateTag("tag1", generateError("err1")),
                          generateTag("tag2", generateError("err1"), generateError("err2"))};

        // Test
        testValidate_TagStackRemaining_WithErrors_PrinterDataContainsExpected(tags);
    }

    @Test
    public void testValidate_ThreeTagsInStack_WithErrors_PrinterDataContainsExpected() {
        // Set up
        TagMock[] tags = {generateTag("tag1", generateError("err1")),
                          generateTag("tag2", generateError("err1"), generateError("err2")),
                          generateTag("tag3", generateError("err1"), generateError("err2"), generateError("err3"))};

        // Test
        testValidate_TagStackRemaining_WithErrors_PrinterDataContainsExpected(tags);
    }

    private void testValidate_TagStackRemaining_WithErrors_PrinterDataContainsExpected(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();
        List<Tag> remaining = new ArrayList<>();

        for (Tag tag : tags) {
            exp.add(formatLine(String.format(HtmlValidator.UNCLOSED_TAG_MSG, tag)));

            for (MarkupError err : tag.getErrorReporter().getErrors())
                exp.add(formatLine(err));

            if (tag.getErrorReporter().hasErrors()) exp.add(formatLine());

            remaining.add(tag);
        }

        tagStack.dataToPop = remaining;

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    private void loadDataToPopWith(Tag... tags) {
        List<Tag> dataToPop = tagStack.dataToPop;

        for (Tag tag : tags)
            dataToPop.add(tag);
    }

    private String formatLine() {
        return formatLine("");
    }

    private String formatLine(Object obj) {
        return formatLine(obj.toString());
    }

    private String formatLine(String data) {
        return String.format(LINE_FRMT, data);
    }

    private TagMock generateTag(String name) {
        TagMock tag = new TagMock();
        tag.name = name;
        tag.toString = name;
        return tag;
    }

    private TagMock generateTag(String name, MarkupError... errs) {
        TagMock tag = generateTag(name);

        for (MarkupError err : errs)
            tag.getErrorReporter().addError(err);

        return tag;
    }

    private TagMock[] createClosingTagsAndSetUpTagStack(TagMock... openingTags) {
        TagMock[] closingTags = new TagMock[openingTags.length];

        int i = 0;
        for(TagMock openingTag : openingTags) {
            closingTags[i] = createClosingCopyOf(openingTag);
            i++;
        }
        loadDataToPopWith(openingTags);
        return closingTags;
    }

    private TagMock createClosingCopyOf(TagMock tag) {
        TagMock result = tag.copy();
        result.isClosing = true;
        return result;
    }

    private List<String> getPrintedData(int start, int end) {
        List<String> result = printer.getPrintData();
        return result.subList(start, end);
    }

    private MarkupError generateError(String data) {
        return new MarkupErrorMock(data);
    }
}