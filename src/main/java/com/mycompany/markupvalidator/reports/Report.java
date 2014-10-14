/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.reports;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

import java.util.Collection;

public interface Report {

    public Tag presumedCause();

    public Collection<Tag> parsingErrors();

    public Collection<Tag> validationErrors();

    public boolean isValid();

    @Override
    public String toString();
}
