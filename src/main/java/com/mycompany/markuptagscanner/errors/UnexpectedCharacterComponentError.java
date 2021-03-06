/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.errors;

import java.awt.Point;

public class UnexpectedCharacterComponentError extends ComponentError {
    private static final long serialVersionUID = -5796386130383540454L;
    private static final String UNEXPECTED_CHAR_MSG = "Unexpected Character! At position %s didn't expect ---> %s";
    
    private Point position;
    private char unexpected;
    private Object data;
    
    public UnexpectedCharacterComponentError(char unexpected, Point position, Object data) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(UNEXPECTED_CHAR_MSG, position, unexpected)));
        this.position = position;
        this.unexpected = unexpected;
        this.data = data;
    }
    
    public String getData() {
        return this.data.toString();
    }

    @Override
    public String getErrorMessage() {
        String msg = String.format(UNEXPECTED_CHAR_MSG, this.position, this.unexpected);
        return String.format(DEFAULT_ERROR_MSG, msg);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + unexpected;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UnexpectedCharacterComponentError other = (UnexpectedCharacterComponentError) obj;
        if (data == null) {
            if (other.data != null) return false;
        } else if (!data.equals(other.data)) return false;
        if (position == null) {
            if (other.position != null) return false;
        } else if (!position.equals(other.position)) return false;
        if (unexpected != other.unexpected) return false;
        return true;
    }
    
    
}
