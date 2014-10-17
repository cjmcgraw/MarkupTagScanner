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
import com.mycompany.markupvalidator.errors.MarkupError;

public class TagErrorsFormatter extends TagLineFormatter {

    @Override
    public String format(FormatData<Tag> formatData) {
        setState(formatData);
        String result = formatErrors();
        clearState();
        return result;
    }

    protected String formatErrors() {
        writeErrors();
        return format();
    }


    protected void writeErrors() {
        String prev = this.indentation;
        this.indentation = "";
        writeErrorsWithIndentation();
        this.indentation = prev;
    }

    private void writeErrorsWithIndentation() {
        for (MarkupError err : this.tag.getErrorReporter().getErrors())
            writeLine(err);
    }

}
