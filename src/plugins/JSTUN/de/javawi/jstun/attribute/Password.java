/*
 * This file is part of JSTUN.
 *
 * Copyright (c) 2005 Thomas King <king@t-king.de>
 *
 * JSTUN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * JSTUN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JSTUN; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package plugins.JSTUN.de.javawi.jstun.attribute;

import plugins.JSTUN.de.javawi.jstun.util.Utility;
import plugins.JSTUN.de.javawi.jstun.util.UtilityException;

public class Password extends MessageAttribute {
    String password;

    public Password() {
        super(MessageAttribute.MessageAttributeType.Password);
    }

    public Password(String password) {
        super(MessageAttribute.MessageAttributeType.Password);
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getBytes() throws UtilityException {
        int length = password.length();

        // password header
        if ((length % 4) != 0) {
            length += 4 - (length % 4);
        }

        // message attribute header
        length += 4;

        byte[] result = new byte[length];

        // message attribute header
        // type
        System.arraycopy(Utility.IntegerToTwoBytes(typeToInteger(type)), 0, result, 0, 2);

        // length
        System.arraycopy(Utility.IntegerToTwoBytes(length - 4), 0, result, 2, 2);

        // password header
        byte[] temp = password.getBytes();

        System.arraycopy(temp, 0, result, 4, temp.length);

        return result;
    }

    public static Password parse(byte[] data) {
        Password result = new Password();
        String password = new String(data);

        result.setPassword(password);

        return result;
    }
}
