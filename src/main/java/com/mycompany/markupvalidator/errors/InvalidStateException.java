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
package com.mycompany.markupvalidator.errors;

public class InvalidStateException extends MarkupError {
    private static final long serialVersionUID = 7342202150355963466L;
    private static final String MSG = "Error: State has not been set on <%s>. Cannot perform this operation without <%s> set! Invalid State!!";
    
    public InvalidStateException(String className, String fieldName) {
        super(String.format(MSG, className, fieldName));
    }
}
