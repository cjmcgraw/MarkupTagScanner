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

import java.io.IOException;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlSubParser extends DataParser{

    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        HtmlData result = new HtmlData();
        return this.parse(input, result);
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException;
    
}
