/*
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
 */
package org.codice.imaging.nitf.core;

import java.text.ParseException;

/**
    Parser for the file security metadata.
    <p>
    The security metadata at the file level is the same as the subheaders, except for
    two extra fields (copy number, and number of copies).
*/
class NitfFileSecurityMetadataParser extends NitfSecurityMetadataParser {

    public NitfFileSecurityMetadataParser() {
    }

    public final void parse(final NitfReader nitfReader, final NitfFileSecurityMetadata metadata) throws ParseException {
        doParse(nitfReader, metadata);
        readFileSecurityMetadataExtras(metadata);
    }

    private void readFileSecurityMetadataExtras(final NitfFileSecurityMetadata metadata) throws ParseException {
        readFSCOP(metadata);
        readFSCPYS(metadata);
    }

    private void readFSCOP(final NitfFileSecurityMetadata metadata) throws ParseException {
        metadata.setFileCopyNumber(reader.readTrimmedBytes(NitfConstants.FSCOP_LENGTH));
    }

    private void readFSCPYS(final NitfFileSecurityMetadata metadata) throws ParseException {
        metadata.setFileNumberOfCopies(reader.readTrimmedBytes(NitfConstants.FSCPYS_LENGTH));
    }
};

