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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

import org.junit.rules.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

public class RasterProductFormatUtilitiesTest {

    @Test
    public void checkA1() throws IOException, ParseException {
        RasterProductFormatUtilities rpfUtils = new RasterProductFormatUtilities();
        assertEquals("CM", rpfUtils.getAbbreviationForFileName("foo.A11"));
        assertEquals("Combat Charts, 1:10,000 scale", rpfUtils.getNameForFileName("foo.A11"));
    }

    @Test
    public void checkZZ() throws IOException, ParseException {
        RasterProductFormatUtilities rpfUtils = new RasterProductFormatUtilities();
        assertEquals("", rpfUtils.getAbbreviationForFileName("foo.ZZA"));
        assertEquals("IFR Enroute High/Low", rpfUtils.getNameForFileName("foo.ZZA"));
    }

    @Test
    public void checkLF() throws IOException, ParseException {
        RasterProductFormatUtilities rpfUtils = new RasterProductFormatUtilities();
        assertEquals("LFC-FR (Day)", rpfUtils.getAbbreviationForFileName("foo.LF2"));
        assertEquals("Low Flying Chart (Day) - Host Nation", rpfUtils.getNameForFileName("foo.LF2"));
    }

    @Test
    public void checkBadExtension() throws IOException, ParseException {
        RasterProductFormatUtilities rpfUtils = new RasterProductFormatUtilities();
        assertNull(rpfUtils.getAbbreviationForFileName("foo"));
        assertNull(rpfUtils.getNameForFileName("foo.A1"));
    }

}
