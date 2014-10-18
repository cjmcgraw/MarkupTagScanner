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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.markupvalidator.MarkupTagScanners.Attribute;
import com.mycompany.markupvalidator.MarkupTagScanners.errors.ParsingError;

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
