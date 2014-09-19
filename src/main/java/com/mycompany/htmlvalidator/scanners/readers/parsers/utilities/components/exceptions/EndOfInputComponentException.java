package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

public class EndOfInputComponentException extends ComponentException {
    private static final long serialVersionUID = 5802533142904660152L;
    private static final String MSG = "End of input detected after after [%s]";
    
    private String data;
    
    public EndOfInputComponentException(String data) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(MSG, data)));
        this.data = data;
    }
    
    @Override
    public String getErrorMessage() {
        return String.format(MSG, data);
    }
    
    @Override
    public String getData() {
        return data;
    }
    
}
