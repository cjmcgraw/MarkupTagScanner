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

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

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
