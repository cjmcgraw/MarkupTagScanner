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

package com.mycompany.markupvalidator.reports;

import com.mycompany.markupvalidator.MarkupTagScanners.*;
import org.junit.*;

import java.util.*;
import java.util.function.*;

import static junit.framework.Assert.*;

public class HtmlValidatorReportTest {
    private ReportGenerator report;

    @Before
    public void setUp() {
        this.report = new HtmlValidatorReport();
    }

    @After
    public void tearDown() {
        this.report = null;
    }

    @Test
    public void testPresumedCause_NoErrorsAdded_ResultIsNull() {
        // Assert
        assertNull(report.presumedCause());
    }

    @Test
    public void testPresumedCause_OneValidationErrorAdded_ResultMatchesTagAdded() {
        // Arrange
        Tag exp = createTag("tag1");
        addValidationErrorToReport(exp);

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_TwoValidationErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("tag1");
        addValidationErrorToReport(exp, createTag("tag2"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_ThreeValidationErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("tag1");
        addValidationErrorToReport(exp, createTag("Tag2"), createTag("tag3"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_OneParsingErrorAdded_ResultMatchesTagAdded() {
        // Arrange
        Tag exp = createTag("tag1");
        addParsingErrorToReport(exp);

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_TwoParsingErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("tag1");
        addParsingErrorToReport(exp, createTag("tag2"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_ThreeParsingErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("Tag1");
        addParsingErrorToReport(exp, createTag("tag2"), createTag("tag3"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_OneParsingOneValidationErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("tag1");
        addParsingErrorToReport(exp);
        addValidationErrorToReport(createTag("tag2"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testPresumedCause_OneValidationOneParsingErrorsAdded_ResultMatchesFirstTag() {
        // Arrange
        Tag exp = createTag("tag1");
        addValidationErrorToReport(exp);
        addParsingErrorToReport(createTag("tag2"));

        // Assert
        assertEquals(exp, report.presumedCause());
    }

    @Test
    public void testParsingErrors_NoParsingErrorsAdded_ResultIsEmpty() {
        // Assert
        assertTrue(report.parsingErrors().isEmpty());
    }

    @Test
    public void testParsingErrors_SingleParsingErrorAdded_ResultMatches() {
        // Test
        testAddParsingError_ResultMatches(createTag("tag1"));
    }

    @Test
    public void testParsingErrors_TwoParsingErrorsAdded_ResultMatches() {
        // Test
        testAddParsingError_ResultMatches(createTag("tag1"), createTag("tag2"));
    }

    @Test
    public void testParsingErrors_ThreeParsingErrorsAdded_ResultMatches() {
        // Test
        testAddParsingError_ResultMatches(createTag("Tag1"), createTag("Tag2"), createTag("Tag3"));
    }

    private void testAddParsingError_ResultMatches(Tag... tags) {
        // Test
        testAddErrors_ResultMatches(report::addParsingError, report::parsingErrors, tags);
    }

    @Test
    public void testValidationErrors_SingleValidationErrorAdded_ResultMatches() {
        // Test
        testAddValidationError_ResultMatches(createTag("tag1"));
    }

    @Test
    public void testValidationErrors_TwoValidationErrorsAdded_ResultMatches() {
        // Test
        testAddValidationError_ResultMatches(createTag("tag1"), createTag("tag2"));
    }

    @Test
    public void testValidationErrors_ThreeValidationErrorsAdded_ResultMatches() {
        // Test
        testAddValidationError_ResultMatches(createTag("Tag1"), createTag("Tag2"), createTag("Tag3"));
    }

    private void testAddValidationError_ResultMatches(Tag... tags) {
        // Test
        testAddErrors_ResultMatches(report::addValidationError, report::validationErrors, tags);
    }

    private void testAddErrors_ResultMatches(Consumer<Tag> f, Supplier<Iterable<Tag>> supplier,Tag... tags) {
        // Arrange
        Collection<Tag> exp = new ArrayList<>();

        for (Tag tag : tags) {
            exp.add(tag);
            f.accept(tag);
        }

        // Assert
        assertEquals(exp, supplier.get());
    }

    @Test
    public void testIsValid_NoErrorsAdded_ResultIsTrue() {
        // Assert
        assertTrue(report.isValid());
    }

    @Test
    public void testIsValid_ParsingErrorAdded_ResultIsFalse() {
        // Arrange
        addValidationErrorToReport(createTag("tag1"));

        // Assert
        assertFalse(report.isValid());
    }

    @Test
    public void testIsValid_ValidationErrorAdded_ResultIsFalse() {
        // Arrange
        addParsingErrorToReport(createTag("tag1"));

        // Assert
        assertFalse(report.isValid());
    }

    private void addValidationErrorToReport(Tag... tags) {
        for (Tag tag : tags)
            report.addValidationError(tag);
    }

    private void addParsingErrorToReport(Tag... tags) {
        for (Tag tag : tags)
            report.addParsingError(tag);
    }

    private TagMock createTag(String s) {
        return new TagMock(s);
    }
}
