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

package com.mycompany.markupvalidator.MarkupTagScanners;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.*;
import com.mycompany.markupvalidator.MarkupTagScanners.errors.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.*;

import java.io.*;

public class HtmlScanner implements MarkupScanner {
    public static final String INVALID_SELF_CLOSING_ERROR_MSG = "[ %s ] contains a self closing, but isn't an expected void tag";

    private MarkupReader reader;

    public HtmlScanner(File file) throws IOException {
        this(new HtmlReader(file));
    }

    public HtmlScanner(InputStream input) throws IOException {
        this(new HtmlReader(input));
    }

    public HtmlScanner(MarkupReader reader) {
        this.reader = reader;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        return reader.hasNext();
    }

    @Override
    public Tag next() {
        Tag tag = reader.next();
        try {
            validateSelfClosing(tag);
        } catch (MarkupError err) {
            tag.getErrorReporter().addError(err);
        }
        return tag;
    }

    private void validateSelfClosing(Tag tag) {
        if(invalidSelfClosingTag(tag))
            throw new InvalidMarkupError(String.format(INVALID_SELF_CLOSING_ERROR_MSG, tag));
    }

    private boolean invalidSelfClosingTag(Tag tag) {
        return tag.isSelfClosing() &&
               !VoidTag.contains(tag.getName());
    }
}
