/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.utilities;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;

public interface TagStack {

    public Tag pop();

    public void push(Tag tag);

    public Tag peek();

    public boolean empty();

    public String indentation();
}
