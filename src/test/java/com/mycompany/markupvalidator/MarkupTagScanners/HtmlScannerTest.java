/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.MarkupTagScanners;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.VoidTag;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.*;
import com.mycompany.markupvalidator.errors.*;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class HtmlScannerTest {
    private HtmlReaderMock reader;
    private MarkupScanner scanner;
    private List<HtmlData> data;

    public void setUp(HtmlData... data) {
        this.data = generateData(data);
        this.reader = new HtmlReaderMock(this.data, false);
        this.scanner = new HtmlScanner(reader);
    }

    @After
    public void tearDown() {
        this.reader = null;
        this.scanner = null;
    }

    @Test
    public void testClose() throws Exception {
        // Arrange
        setUp();

        // Apply
        scanner.close();

        // Assert
        assertTrue(reader.getClosed());
    }

    @Test
    public void testHasNext_ReaderHasNextValue_ResultIsTrue() {
        // Arrange
        setUp(createTag());

        // Assert
        assertTrue(scanner.hasNext());
    }

    @Test
    public void testHasNext_ReaderEmpty_ResultIsFalse() {
        // Arrange
        setUp();

        // Assert
        assertFalse(scanner.hasNext());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testRemove_ThrowsExpectedException() {
        // Arrange
        setUp(createTag());

        // Apply + Assert
        scanner.remove();
    }

    @Test
    public void testNext_NonEmptyData_OneTag_ResultMatchesExpected() {
        // Set up
        setUp(createTag("tag1"));

        // Test
        testSameTags(data, getScannerData());
    }

    @Test
    public void testNext_NonEmptyData_TwoTags_ResultMatchesExpected() {
        // Set up
        setUp(createTag("tag1"), createTag("tag2"));

        // Test
        testSameTags(data, getScannerData());
    }

    @Test
    public void testNext_NonEmptyData_ThreeTags_ResultMatchesExpected() {
        // Set up
        setUp(createTag("tag1"), createTag("tag2"), createTag("tag3"));

        // Test
        testSameTags(data, getScannerData());
    }

    @Test
    public void testNext_NonEmptyData_MultiTags_IsSelfClosing_ValidVoidTag_ResultMatchesExpected() {
        // Set up
        HtmlData[] generatedData = new HtmlData[VoidTag.values().length];

        int i = 0;
        for (VoidTag tag : VoidTag.values()) {
            generatedData[i] = createTag(tag.getName(), "/");
            i++;
        }

        setUp(generatedData);

        // Test
        testSameTags(data, getScannerData());
    }

    @Test
    public void testNext_NonEmptyData_SingleTag_IsSelfClosing_NonValidVoidTag_ResultMatchesExpected() {
        // Set up
        HtmlData tag = createTag("tag1", "/");
        ErrorReporter reporter = tag.getErrorReporter();
        reporter.addError(generateException(tag));

        setUp(tag);

        // Test
        testSameTags(data, getScannerData());
    }

    private List<Tag> getScannerData() {
        List<Tag> result = new ArrayList<>();

        while (scanner.hasNext())
            result.add(scanner.next());
        return result;
    }

    private void testSameTags(List<? extends Tag> exp, List<? extends Tag> act) {
        assertEquals(exp.size(), act.size());

        for (int i = 0; i < exp.size(); i++)
            assertEquals(exp.get(i), act.get(i));
    }

    private HtmlData createTag() {
        return createTag("");
    }

    private MarkupError generateException(Tag tag) {
        return new InvalidMarkupError(String.format(HtmlScanner.INVALID_SELF_CLOSING_ERROR_MSG, tag));
    }

    private HtmlData createTag(String name, String... attrs) {
        HtmlData data = new HtmlData();
        data.setName(name);

        for (String attr : attrs) {
            HtmlAttribute attribute = new HtmlAttribute(attr);
            data.updateAttributes(attribute);
        }

        return data;
    }

    private List<HtmlData> generateData(HtmlData... data) {
        List<HtmlData> result = new ArrayList<>();

        for (HtmlData tag : data)
            result.add(tag);

        return result;
    }
}
