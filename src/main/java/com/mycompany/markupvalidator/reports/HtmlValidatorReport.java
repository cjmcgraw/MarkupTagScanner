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

package com.mycompany.markupvalidator.reports;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.formatters.*;
import com.mycompany.markupvalidator.formatters.Formatter;

import java.util.*;

public class HtmlValidatorReport implements ReportGenerator {
    private Formatter<Report> reportFormatter = new ReportFormatter();

    private Queue<Tag> validationErrors;
    private Queue<Tag> parsingErrors;
    private Tag cause;

    public HtmlValidatorReport() {
        this.validationErrors = new LinkedList<>();
        this.parsingErrors = new LinkedList<>();
        this.cause = null;
    }

    @Override
    public Tag presumedCause() {
        return cause;
    }

    @Override
    public void addParsingError(Tag tag) {
        addTagTo(tag, parsingErrors);
    }

    @Override
    public Collection<Tag> parsingErrors() {
        return parsingErrors;
    }

    @Override
    public void addValidationError(Tag tag) {
        addTagTo(tag, validationErrors);
    }

    private void addTagTo(Tag tag, Queue<Tag> data) {
        if (!hasCause())
            setCause(tag);
        data.add(tag);
    }

    private void setCause(Tag tag) {
        this.cause = tag;
    }

    @Override
    public Collection<Tag> validationErrors() {
        return validationErrors;
    }

    @Override
    public boolean isValid() {
        return !hasCause() &&
               validationErrors.isEmpty() &&
               parsingErrors.isEmpty();
    }

    private boolean hasCause() {
        return cause != null;
    }

    @Override
    public String toString() {
        FormatData<Report> formatData = new FormatData<>(this);
        return reportFormatter.format(formatData);
    }
}
