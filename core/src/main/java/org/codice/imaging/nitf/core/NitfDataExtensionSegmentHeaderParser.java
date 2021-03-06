/**
 * Copyright (c) Codice Foundation
 *
 * This is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details. A copy of the GNU Lesser General Public License
 * is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 *
 **/
package org.codice.imaging.nitf.core;

import java.text.ParseException;

/**
    Parser for a data extension segment (DES) subheader in a NITF file.
*/
class NitfDataExtensionSegmentHeaderParser extends AbstractNitfSegmentParser {
    private int userDefinedSubheaderLength = 0;

    private NitfDataExtensionSegmentHeader segment = null;


    NitfDataExtensionSegmentHeaderParser() {
    }

    final NitfDataExtensionSegmentHeader parse(final NitfReader nitfReader) throws ParseException {
        reader = nitfReader;
        segment = new NitfDataExtensionSegmentHeader();

        readDE();
        readDESID();
        readDESVER();
        segment.setSecurityMetadata(new NitfSecurityMetadata(reader));

        if (segment.isTreOverflow(reader.getFileType())) {
            readDESOFLW();
            readDESITEM();
        }
        readDSSHL();
        readDSSHF();
        return segment;
    }

    private void readDE() throws ParseException {
        reader.verifyHeaderMagic(NitfConstants.DE);
    }

    private void readDESID() throws ParseException {
        segment.setIdentifier(reader.readBytes(NitfConstants.DESID_LENGTH));
    }

    private void readDESVER() throws ParseException {
        segment.setDESVersion(reader.readBytesAsInteger(NitfConstants.DESVER_LENGTH));
    }

    private void readDESOFLW() throws ParseException {
        segment.setOverflowedHeaderType(reader.readTrimmedBytes(NitfConstants.DESOFLW_LENGTH));
    }

    private void readDESITEM() throws ParseException {
        segment.setItemOverflowed(reader.readBytesAsInteger(NitfConstants.DESITEM_LENGTH));
    }

    private void readDSSHL() throws ParseException {
        userDefinedSubheaderLength = reader.readBytesAsInteger(NitfConstants.DESSHL_LENGTH);
    }

    private void readDSSHF() throws ParseException {
        segment.setUserDefinedSubheaderField(reader.readBytes(userDefinedSubheaderLength));
    }
}
