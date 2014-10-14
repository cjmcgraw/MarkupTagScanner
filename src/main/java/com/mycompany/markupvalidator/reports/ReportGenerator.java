/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.reports;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

public interface ReportGenerator extends Report {

    public void addParsingError(Tag tag);

    public void addValidationError(Tag tag);
}
