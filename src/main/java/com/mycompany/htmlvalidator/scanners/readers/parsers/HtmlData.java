package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HtmlData {
    private List<String> data;
    private boolean isClosing;
    private String name;
    
    public HtmlData(String name, Collection<String> data, boolean isClosing) {
        this.name = name;
        this.isClosing = isClosing;
        this.data = new ArrayList<String>();
        this.data.addAll(data);
    }
    
    public String getName() {
        return this.name;
    }
    
    public List<String> getData() {
        return this.data;
    }
    
    public boolean isClosing() {
        return this.isClosing;
    }
}
