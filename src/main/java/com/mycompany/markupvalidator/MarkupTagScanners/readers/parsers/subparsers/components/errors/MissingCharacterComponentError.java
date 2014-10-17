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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors;

import java.awt.Point;

public class MissingCharacterComponentError extends ComponentError {
    private static final long serialVersionUID = -4066078864219639731L;
    private static final String MISSING_CHAR_MSG = "Missing Character! At position %s, expected ---> %s";
    
    private Point position;
    private char missing;
    private Object data;
    
    public MissingCharacterComponentError(char missing, Point position, Object data) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(MISSING_CHAR_MSG, position, missing)));
        this.missing = missing;
        this.position = position;
        this.data = data;
    }
    
    public String getData() {
        return this.data.toString();
    }
    
    @Override
    public String getErrorMessage() {
        String msg = String.format(MISSING_CHAR_MSG, this.position, this.missing);
        return String.format(DEFAULT_ERROR_MSG, msg);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + missing;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MissingCharacterComponentError other = (MissingCharacterComponentError) obj;
        if (data == null) {
            if (other.data != null) return false;
        } else if (!data.equals(other.data)) return false;
        if (missing != other.missing) return false;
        if (position == null) {
            if (other.position != null) return false;
        } else if (!position.equals(other.position)) return false;
        return true;
    }
}
