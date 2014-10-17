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

package com.mycompany.markupvalidator.utilities;

import com.mycompany.markupvalidator.MarkupTagScanners.*;
import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTagNames;
import org.junit.*;

import java.util.EmptyStackException;

import static junit.framework.Assert.*;

public class HtmlTagStackTest {
    private HtmlTagStack tagStack;

    @Before
    public void setUp() {
        this.tagStack = new HtmlTagStack();
    }

    @After
    public void tearDown() {
        this.tagStack = null;
    }

    @Test(expected= EmptyStackException.class)
    public void testPop_EmptyStack_ThrowsExpectedException() {
        tagStack.pop();
    }

    @Test
    public void testPopAndPush_SingleTag_ResultMatchesExpected() {
        testPopAndPush(generateTag());
    }

    @Test
    public void testPopAndPush_TwoTags_ResultMatchesExpected() {
        testPopAndPush(generateTag(), generateTag());
    }

    @Test
    public void testPopAndPush_ThreeTags_ResultMatchesExpected() {
        testPopAndPush(generateTag(), generateTag(), generateTag());
    }

    private void testPopAndPush(Tag... expected) {
        // Arrange
        for (int i = expected.length - 1; i >= 0; i--)
            tagStack.push(expected[i]);

        for (Tag exp : expected)
            // Assert
            assertEquals(exp, tagStack.pop());
    }

    @Test(expected=EmptyStackException.class)
    public void testPeek_EmptyStack_ThrowsExpectedException() {
        tagStack.peek();
    }

    @Test
    public void testPeek_SingleValue_ResultMatchesExpected() {
        testPeek_CallsPush(generateTag());
    }

    @Test
    public void testPeek_TwoValues_ResultMatchesExpected() {
        testPeek_CallsPush(generateTag(), generateTag());
    }

    @Test
    public void testPeek_ThreeValues_ResultMatchesExpected() {
        testPeek_CallsPush(generateTag(), generateTag(), generateTag());
    }

    private void testPeek_CallsPush(Tag... expected) {
        for (Tag exp : expected) {
            tagStack.push(exp);
            assertEquals(exp, tagStack.peek());
        }
    }

    @Test
    public void testEmpty_EmptyStack_ResultIsTrue() {
        // Assert
        assertTrue(tagStack.empty());
    }

    @Test
    public void testEmpty_NonEmptyStack_ResultIsFalse() {
        // Arrange
        tagStack.push(generateTag());

        // Assert
        assertFalse(tagStack.empty());
    }

    @Test
    public void testIndentation_PushData_IncreaseIndentationEmptyStack_ResultIsEmptyString() {
        testIndentation_PushData_IncreaseIndentationResultMatchesExpected(0);
    }

    @Test
    public void testIndentation_PushData_IncreaseIndentationSingleTag_ResultIsSingleIndentation() {
        testIndentation_PushData_IncreaseIndentationResultMatchesExpected(1);
    }

    @Test
    public void testIndentation_PushData_IncreaseIndentationDoubleTag_ResultIsDoubleIndentation() {
        testIndentation_PushData_IncreaseIndentationResultMatchesExpected(2);
    }

    @Test
    public void testIndentation_PushData_IncreaseIndentationTripleTag_ResultIsTripleIndentation() {
        testIndentation_PushData_IncreaseIndentationResultMatchesExpected(3);
    }

    private void testIndentation_PushData_IncreaseIndentationResultMatchesExpected(int n) {
        // Arrange
        StringBuilder exp = new StringBuilder();

        for (int i = n; i > 0; i--) {
            tagStack.push(generateTag());
            exp.append(HtmlTagStack.INDENTATION);
        }

        // Assert
        assertEquals(exp.toString(), tagStack.indentation());
    }

    @Test
    public void testIndentation_PushData_SelfClosingTag_NoIndentationLevelIncrease() {
        // Arrange
        String exp = tagStack.indentation();

        TagMock tag = generateTag();
        tag.isSelfClosing = true;

        // Apply
        tagStack.push(tag);

        // Assert
        assertEquals(exp, tagStack.indentation());
    }

    @Test
    public void testIndentation_PushData_CommentTag_NoIndentationLevelincrease() {
        // Arrange
        String exp = tagStack.indentation();

        Tag tag = generateTag(MarkupTagNames.COMMENT_TAG.getBeginName());

        // Apply
        tagStack.push(tag);

        // Assert
        assertEquals(exp, tagStack.indentation());
    }

    @Test
    public void testIndentation_PopData_DecreasesIndentationSingleTag_ResultIsDoubleIndentation() {
        testIndentation_PopData_DecreaseIndentationResultMatchesExpected(1);
    }

    @Test
    public void testIndentation_PopData_DecreaseIndentationDoubleTag_ResultIsSingleIndentation() {
        testIndentation_PopData_DecreaseIndentationResultMatchesExpected(2);
    }

    @Test
    public void testIndentation_PopData_DecreaseIndentationTripleTag_ResultIsEmptyString() {
        testIndentation_PopData_DecreaseIndentationResultMatchesExpected(3);
    }

    private void testIndentation_PopData_DecreaseIndentationResultMatchesExpected(int n) {
        // Arrange
        StringBuilder exp;

        for (int i = n; i > 0; i--)
            tagStack.push(generateTag());

        exp = new StringBuilder(tagStack.indentation());

        // assert
        for (int i = n; i > 0; i--) {
            exp.deleteCharAt(0);
            tagStack.pop();
            assertEquals(exp.toString(), tagStack.indentation());
        }
    }

    private TagMock generateTag() {
        return new TagMock();
    }

    private TagMock generateTag(String name) {
        return new TagMock(name);
    }
}
