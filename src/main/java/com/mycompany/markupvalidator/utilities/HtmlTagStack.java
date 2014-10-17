/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
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
