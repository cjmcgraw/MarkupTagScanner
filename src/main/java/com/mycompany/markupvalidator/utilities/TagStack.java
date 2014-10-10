/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.utilities;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

public interface TagStack {

    public Tag pop();

    public void push(Tag tag);

    public Tag peek();

    public boolean empty();

    public String indentation();
}
