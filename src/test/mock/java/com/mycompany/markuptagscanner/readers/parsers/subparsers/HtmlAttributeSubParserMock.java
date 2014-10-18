/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers.parsers.subparsers;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.markuptagscanner.Attribute;
import com.mycompany.markuptagscanner.errors.ParsingError;

public class HtmlAttributeSubParserMock extends HtmlTypeSubParserMock {
    private List<Attribute> data;
    
    public HtmlAttributeSubParserMock(ParsingError exception) {
        super(exception);
        this.data = new ArrayList<>();
    }
    
    public List<Attribute> getData() {
        return this.data;
    }
    
    public void setData(List<Attribute> data) {
        this.data = data;
    }

    @Override
    protected void updateResult() {
        this.result.setAttributes(this.data);
    }
    
}
