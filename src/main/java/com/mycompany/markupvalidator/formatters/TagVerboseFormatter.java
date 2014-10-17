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

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

public class TagVerboseFormatter extends TagErrorsFormatter {

    @Override
    public String format(FormatData<Tag> formatData) {
        setState(formatData);
        String result = generateTagLineFormat();
        clearState();
        return result;
    }

    protected String generateTagLineFormat() {
        writeTag();
        writeErrors();
        return format();
    }

    protected void writeTag() {
        writeLine(tag);
    }
}
