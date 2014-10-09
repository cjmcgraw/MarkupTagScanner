/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.utilities;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;

import java.util.*;

public class TagStackMock implements TagStack{
    public List<Tag> pushedData;
    public List<Tag> dataToPop;
    public String indentation;
    public int currIndex;

    public TagStackMock() {
        this(new ArrayList<>());
    }

    public TagStackMock(List<Tag> dataToPop) {
        this.pushedData = new ArrayList<>();
        this.dataToPop = dataToPop;
        this.currIndex = 0;
        this.indentation = "";
    }

    @Override
    public Tag pop() {
        Tag tag = peek();
        currIndex++;
        return tag;
    }

    @Override
    public void push(Tag tag) {
        pushedData.add(tag);
    }

    @Override
    public Tag peek() {
        if (empty())
            throw new EmptyStackException();
        return dataToPop.get(currIndex);
    }

    @Override
    public boolean empty() {
        return dataToPop.size() <= currIndex;
    }

    @Override
    public String indentation() {
        return indentation;
    }
}
