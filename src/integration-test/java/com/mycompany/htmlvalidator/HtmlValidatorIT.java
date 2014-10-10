package com.mycompany.htmlvalidator;/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

import com.mycompany.htmlvalidator.MarkupTagScanners.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.enums.VoidTag;
import com.mycompany.htmlvalidator.errors.*;
import com.mycompany.htmlvalidator.printers.PrinterMock;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class HtmlValidatorIT {
    public static final String LINE_FRMT = "%s%n";
    public static final String UNCLOSED_FRMT = String.format(LINE_FRMT, HtmlValidator.UNCLOSED_TAG_MSG);

    private Random nameGenerator = new Random();
    private HtmlValidator validator;
    private PrinterMock printer;

    /* This class represents the "simple" integration tests of HtmlValidator.
     * Specifically this means combining the HtmlValidator and HtmlTagStack
     * classes for testing, but mocking out the printer to ensure that they
     * function as expected together.
     */

    @Before
    public void setUp() {
        this.printer = new PrinterMock();
        this.validator = new HtmlValidator(printer);
    }

    @After
    public void tearDown() {
        this.validator = null;
        this.printer = null;
    }

    @Test
    public void testValidate_OpeningTag_SingleTag_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTags(generateTag("tag1"));
    }

    @Test
    public void testValidate_OpeningTag_DoubleTag_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTags(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_OpeningTag_TripleTag_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTags(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_OpeningTags(Tag... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = Arrays.asList(tags);

        // Apply
        validator.validate(data.iterator());

        // Assert
        testPrinterDataBeginsWithExpected(exp);
    }

    @Test
    public void testValidate_ClosingTag_SingleTag_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTags(generateTag("tag1"));
    }

    @Test
    public void testValidate_ClosingTag_DoubleTag_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTags(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_ClosingTag_TripleTag_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTags(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_ClosingTags(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = generateInitialOpeningTags(tags);

        int endOfOpeningTags = data.size();

        for (int i = tags.length - 1; i >= 0; i--) {
            TagMock tag = tags[i].copy();
            tag.isClosing = true;
            data.add(tag);
        }

        // Apply
        validator.validate(data.iterator());

        // Assert
        testPrinterDataContainsExpectedAt(exp, endOfOpeningTags);
    }

    @Test
    public void testValidate_OpeningTag_WithSingleError_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTagErrors(generateError("err1"));

    }

    @Test
    public void testValidate_OpeningTag_WithDoubleError_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTagErrors(generateError("err1"), generateError("err2"));
    }

    @Test
    public void testValidate_OpeningTag_WithTripleError_PrinterContainsExpected() {
        // Test
        testValidate_OpeningTagErrors(generateError("err1"), generateError("err2"), generateError("err3"));
    }

    private void testValidate_OpeningTagErrors(MarkupError... errs) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        Tag tag = generateTag("errorTag");
        data.add(tag);

        for (MarkupError err : errs) {
            exp.add(String.format(LINE_FRMT, err.toString()));
            tag.getErrorReporter().addError(err);
        }
        exp.add(String.format(LINE_FRMT, ""));

        // Apply
        validator.validate(data.iterator());

        // Assert
        testPrinterDataBeginsWithExpected(exp);
    }

    @Test
    public void testValidate_ClosingTag_WithSingleError_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTagErrors(generateError("err1"));
    }

    @Test
    public void testValidate_ClosingTag_WithDoubleError_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTagErrors(generateError("err1"), generateError("err2"));
    }

    @Test
    public void testValidate_ClosingTag_WithTripleError_PrinterContainsExpected() {
        // Test
        testValidate_ClosingTagErrors(generateError("err1"), generateError("err2"), generateError("err3"));
    }

    private void testValidate_ClosingTagErrors(MarkupError... errs) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        TagMock openingTag = generateTag("errorTag");
        TagMock closingTag = openingTag.copy();
        closingTag.isClosing = true;

        for (MarkupError err : errs) {
            exp.add(String.format(LINE_FRMT, err.toString()));
            closingTag.getErrorReporter().addError(err);
        }
        exp.add(String.format(LINE_FRMT, ""));

        data.add(openingTag);
        data.add(closingTag);

        // Apply
        validator.validate(data.iterator());

        // Assert
        assertEquals(exp, printer.getPrintData());

    }

   @Test
    public void testValidate_HasUnclosedTags_SingleTag_PrinterContainsExpected() {
        // Test
       testValidate_HasUnclosedTags_PrinterContainsExpected(generateTag("tag1"));
    }

    @Test
    public void testValidate_HasUnclosedTags_TwoTags_PrinterContainsExpected() {
        // Test
        testValidate_HasUnclosedTags_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_HasUnclosedTags_ThreeTags_PrinterContainsExpected() {
        // Test
        testValidate_HasUnclosedTags_PrinterContainsExpected(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_HasUnclosedTags_PrinterContainsExpected(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        for (int i = tags.length - 1; i >= 0; i--) {
            data.add(tags[tags.length - 1 - i]);
            exp.add(String.format(UNCLOSED_FRMT, tags[i]));
        }

        // Apply
        validator.validate(data);

        // Assert
        assertEquals(exp, printer.getPrintData());
    }

    private void testPrinterDataBeginsWithExpected(List<String> exp) {
        testPrinterDataContainsExpectedAt(exp, 0);
    }

    private void testPrinterDataContainsExpectedAt(List<String> exp, int start) {
        int i = start;

        for (String expected : exp) {
            assertEquals(expected, printer.getPrintData().get(i));
            i++;
        }
    }

    private TagMock generateTag(String data) {
        TagMock tag = new TagMock(nameGenerator.nextInt(10000) + "");
        tag.toString = data;
        return tag;
    }

    private List<Tag> generateInitialOpeningTags(TagMock... tags) {
        List<Tag> result = new ArrayList<>();

        for (TagMock tag : tags) {
            TagMock openingTag = tag.copy();
            openingTag.isClosing = false;
            result.add(openingTag);
        }

        return result;
    }

    private MarkupErrorMock generateError(String data) {
        return new MarkupErrorMock(data);
    }
}