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

import static org.junit.Assert.*;

public class VerboseHtmlValidatorIT {
    public static final String[] INDENTATION_LEVELS = {"", "\t", "\t\t"};
    public static final String LINE_FRMT = "%s%n";
    public static final String UNCLOSED_FRMT = String.format(LINE_FRMT, HtmlValidator.UNCLOSED_TAG_MSG);

    private Random nameGenerator = new Random();
    private VerboseHtmlValidator validator;
    private PrinterMock printer;

    /* This class represents the "simple" integration tests of HtmlValidator.
     * Specifically this means combining the HtmlValidator and HtmlTagStack
     * classes for testing, but mocking out the printer to ensure that they
     * function as expected together.
     */

    @Before
    public void setUp() {
        this.printer = new PrinterMock();
        this.validator = new VerboseHtmlValidator(printer);
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

        for (int i = 0; i < tags.length; i++)
            exp.add(INDENTATION_LEVELS[i % 3] + String.format(LINE_FRMT, tags[i]));

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

            exp.add(INDENTATION_LEVELS[i % 3] + String.format(LINE_FRMT, tag));
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
        exp.add(String.format(LINE_FRMT, tag));

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

        exp.add(String.format(LINE_FRMT, openingTag));
        exp.add(String.format(LINE_FRMT, closingTag));

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
    public void testValidate_OpeningTagFollowedByNonMatchingClosingTag_PrinterContainsExpected() {
        // Arrange
        String exp = String.format(LINE_FRMT, HtmlValidator.NON_MATCHING_ERROR_MSG);

        List<Tag> data = new ArrayList<>();

        TagMock openingTag = generateTag("tag1");
        TagMock closingTag = generateTag("tag2");
        closingTag.isClosing = true;

        data.add(openingTag);
        data.add(closingTag);

        // Apply
        validator.validate(data.iterator());

        // Assert
        assertEquals(String.format(exp, openingTag),
                     printer.getPrintData().get(2));
    }

    @Test
    public void testValidate_SingleSelfClosingTag_IndentationLevelRemainsSame_PrinterContainsExpected() {
        // Test
        testValidate_SelfClosingTags_IndentationLevelRemainsSame(generateTag("tag1"));
    }

    @Test
    public void testValidate_DoubleSelfClosingTags_IndentationLevelRemainsSame_PrinterContainsExpected() {
        // Test
        testValidate_SelfClosingTags_IndentationLevelRemainsSame(generateTag("tag1"), generateTag("tag2"));
    }

    @Test
    public void testValidate_TripleSelfClosingTags_IndentationLevelRemainsSame_PrinterContainsExpected() {
        // Test
        testValidate_SelfClosingTags_IndentationLevelRemainsSame(generateTag("tag1"), generateTag("tag2"), generateTag("tag3"));
    }

    private void testValidate_SelfClosingTags_IndentationLevelRemainsSame(TagMock... tags) {
        // Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        data.add(generateTag("openingTag"));
        exp.add(String.format(LINE_FRMT, "openingTag"));


        for (TagMock tag : tags) {
            tag.isSelfClosing = true;

            data.add(tag);
            exp.add(String.format(LINE_FRMT, INDENTATION_LEVELS[1] + tag.toString()));
        }

        // Apply
        validator.validate(data.iterator());

        // Assert
        testPrinterDataBeginsWithExpected(exp);

    }

    @Test
    public void testValidate_NonSelfClosing_ContainsSelfClosingName_IndentationRemainsSame_PrinterContainsExpected() {
        //Arrange
        List<String> exp = new ArrayList<>();
        List<Tag> data = new ArrayList<>();

        data.add(generateTag("openingTag"));
        exp.add(String.format(LINE_FRMT, "openingTag"));

        for (VoidTag voidTag : VoidTag.values()){
            TagMock tag = generateTag(voidTag.getName());
            tag.name = voidTag.getName();

            exp.add(String.format(LINE_FRMT, INDENTATION_LEVELS[1] + voidTag.getName()));
            data.add(tag);
        }

        // Apply
        validator.validate(data.iterator());

        // Assert
        testPrinterDataBeginsWithExpected(exp);
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