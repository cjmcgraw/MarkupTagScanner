/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

public class FormatData<T> {
    private String indentation;
    private T data;

    public FormatData(String indentation, T data) {
        this.indentation = indentation;
        this.data = data;
    }

    public FormatData(T data) {
        this("", data);
    }

    public T data() {
        return data;
    }

    public String indentation() {
        return indentation;
    }
}
