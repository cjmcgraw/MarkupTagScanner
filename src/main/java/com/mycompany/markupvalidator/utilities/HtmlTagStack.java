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

import java.util.Stack;
import java.util.EmptyStackException;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTagNames;
import com.mycompany.markupvalidator.formatters.Formatter;

public class HtmlTagStack implements TagStack{
    public static final String INDENTATION = Formatter.SIMPLE_INDENTATION;

    private StringBuilder indentationTracker;
    private Stack<Tag> tagStack;


    public HtmlTagStack() {
        super();
        this.indentationTracker = new StringBuilder();
        this.tagStack = new Stack<>();
    }

    @Override
    public Tag pop() {
        validateStack();
        removeIndentationLevel();
        return tagStack.pop();
    }

    @Override
    public void push(Tag tag) {
        addIndentationLevel(tag);
        tagStack.push(tag);
    }

    @Override
    public Tag peek() {
        validateStack();
        return tagStack.peek();
    }

    @Override
    public boolean empty() {
        return tagStack.empty();
    }

    @Override
    public String indentation() {
        return indentationTracker.toString();
    }

    private void addIndentationLevel(Tag tag) {
        if (validTagToIncreaseIndentation(tag))
            indentationTracker.append(INDENTATION);
    }

    private boolean validTagToIncreaseIndentation(Tag tag) {
        return !tag.isSelfClosing() &&
               !MarkupTagNames.COMMENT_TAG.isOpening(tag.getName());
    }

    private void removeIndentationLevel() {
        indentationTracker.deleteCharAt(indentationTracker.length() - 1);
    }

    private void validateStack() {
        if (empty())
            throw new EmptyStackException();
    }
}
