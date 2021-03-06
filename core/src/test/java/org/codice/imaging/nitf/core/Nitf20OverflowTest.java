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

import java.io.BufferedInputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.Test;

public class Nitf20OverflowTest {

    private SimpleDateFormat formatter = null;

    @Before
    public void beforeTest() {
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testU1130F() throws IOException, ParseException {
        final String nitf20File = "/JitcNitf20Samples/U_1130F.NTF";

        assertNotNull("Test file missing", getClass().getResource(nitf20File));

        InputStream is = getClass().getResourceAsStream(nitf20File);
        AllDataExtractionParseStrategy parseStrategy = new AllDataExtractionParseStrategy();
        NitfReader reader = new NitfInputStreamReader(new BufferedInputStream(is));
        NitfFileParser.parse(reader, parseStrategy);
        Nitf nitf = parseStrategy.getNitfHeader();
        assertEquals(FileType.NITF_TWO_ZERO, nitf.getFileType());
        assertEquals(1, nitf.getComplexityLevel());
        assertEquals("", nitf.getStandardType());
        assertEquals("ALLOVERFLO", nitf.getOriginatingStationId());
        assertEquals("1997-09-15 09:00:00", formatter.format(nitf.getFileDateTime().toDate()));
        assertEquals("Checks overflow from all possible areas. Created by George Levy.", nitf.getFileTitle());
        NitfFileSecurityMetadata securityMetadata = nitf.getFileSecurityMetadata();
        assertUnclasAndEmpty(securityMetadata);
        assertEquals("      ", securityMetadata.getDowngradeDateOrSpecialCase());
        assertNull(securityMetadata.getDowngradeEvent());

        assertEquals("00000", nitf.getFileSecurityMetadata().getFileCopyNumber());
        assertEquals("00000", nitf.getFileSecurityMetadata().getFileNumberOfCopies());
        assertEquals("JITC FT HUACHUCA", nitf.getOriginatorsName());
        assertEquals("(520) 538-5494", nitf.getOriginatorsPhoneNumber());
        assertEquals(1, parseStrategy.getImageSegmentHeaders().size());
        assertEquals(0, parseStrategy.getGraphicSegmentHeaders().size());
        assertEquals(1, parseStrategy.getSymbolSegmentHeaders().size());
        assertEquals(1, parseStrategy.getLabelSegmentHeaders().size());
        assertEquals(1, parseStrategy.getTextSegmentHeaders().size());
        assertEquals(7, parseStrategy.getDataExtensionSegmentHeaders().size());
        assertEquals(1, nitf.getUserDefinedHeaderOverflow());
        assertEquals(2, nitf.getExtendedHeaderDataOverflow());

        NitfImageSegmentHeader imageSegment1 = parseStrategy.getImageSegmentHeaders().get(0);
        assertNotNull(imageSegment1);
        assertEquals("512 Lenna", imageSegment1.getIdentifier());
        assertEquals("1993-03-25 15:25:59",  formatter.format(imageSegment1.getImageDateTime().toDate()));
        assertEquals("- BASE IMAGE -", imageSegment1.getImageIdentifier2());
        assertEquals("          ", imageSegment1.getImageTargetId().getBasicEncyclopediaNumber());
        assertEquals("     ", imageSegment1.getImageTargetId().getOSuffix());
        assertEquals("  ", imageSegment1.getImageTargetId().getCountryCode());
        assertUnclasAndEmpty(imageSegment1.getSecurityMetadata());
        assertEquals("Unknown", imageSegment1.getImageSource());
        assertEquals(512L, imageSegment1.getNumberOfRows());
        assertEquals(512L, imageSegment1.getNumberOfColumns());
        assertEquals(PixelValueType.INTEGER, imageSegment1.getPixelValueType());
        assertEquals(ImageRepresentation.MONOCHROME, imageSegment1.getImageRepresentation());
        assertEquals(ImageCategory.VISUAL, imageSegment1.getImageCategory());
        assertEquals(8, imageSegment1.getActualBitsPerPixelPerBand());
        assertEquals(PixelJustification.RIGHT, imageSegment1.getPixelJustification());
        assertEquals(ImageCoordinatesRepresentation.NONE, imageSegment1.getImageCoordinatesRepresentation());
        assertEquals(0, imageSegment1.getImageComments().size());
        assertEquals(ImageCompression.NOTCOMPRESSED, imageSegment1.getImageCompression());
        assertEquals(1, imageSegment1.getNumBands());
        NitfImageBand band1 = imageSegment1.getImageBand(1);
        assertNotNull(band1);
        assertEquals("", band1.getImageRepresentation());
        assertEquals("", band1.getSubCategory());
        assertEquals(0, band1.getNumLUTs());
        assertEquals(0, band1.getNumLUTEntries());
        assertEquals(ImageMode.BLOCKINTERLEVE, imageSegment1.getImageMode());
        assertEquals(1, imageSegment1.getNumberOfBlocksPerRow());
        assertEquals(1, imageSegment1.getNumberOfBlocksPerColumn());
        assertEquals(512, imageSegment1.getNumberOfPixelsPerBlockHorizontal());
        assertEquals(512, imageSegment1.getNumberOfPixelsPerBlockVertical());
        assertEquals(8, imageSegment1.getNumberOfBitsPerPixelPerBand());
        assertEquals(1, imageSegment1.getImageDisplayLevel());
        assertEquals(0, imageSegment1.getAttachmentLevel());
        assertEquals(0, imageSegment1.getImageLocationRow());
        assertEquals(0, imageSegment1.getImageLocationColumn());
        assertEquals("1.0 ", imageSegment1.getImageMagnification());
        assertEquals(1.0, imageSegment1.getImageMagnificationAsDouble(), 0.00000001);
        assertEquals(3, imageSegment1.getUserDefinedHeaderOverflow());
        assertEquals(4, imageSegment1.getExtendedHeaderDataOverflow());

        NitfSymbolSegmentHeader symbolSegment1 = parseStrategy.getSymbolSegmentHeaders().get(0);
        assertNotNull(symbolSegment1);
        assertEquals("Text", symbolSegment1.getIdentifier());
        assertEquals("", symbolSegment1.getSymbolName());
        assertUnclasAndEmpty(symbolSegment1.getSecurityMetadata());
        assertEquals("      ", symbolSegment1.getSecurityMetadata().getDowngradeDateOrSpecialCase());
        assertEquals(SymbolType.CGM, symbolSegment1.getSymbolType());
        assertEquals(SymbolColour.UNKNOWN, symbolSegment1.getSymbolColour());
        assertEquals(0, symbolSegment1.getNumberOfLinesPerSymbol());
        assertEquals(0, symbolSegment1.getNumberOfPixelsPerLine());
        assertEquals(0, symbolSegment1.getLineWidth());
        assertEquals(0, symbolSegment1.getNumberOfBitsPerPixel());
        assertEquals(2, symbolSegment1.getSymbolDisplayLevel());
        assertEquals(0, symbolSegment1.getAttachmentLevel());
        assertEquals(20, symbolSegment1.getSymbolLocationRow());
        assertEquals(20, symbolSegment1.getSymbolLocationColumn());
        assertEquals(0, symbolSegment1.getSymbolLocation2Row());
        assertEquals(0, symbolSegment1.getSymbolLocation2Column());
        assertEquals("000000", symbolSegment1.getSymbolNumber());
        assertEquals(0, symbolSegment1.getSymbolRotation());
        assertEquals(210, parseStrategy.getSymbolSegmentData().get(0).length);
        assertEquals(5, symbolSegment1.getExtendedHeaderDataOverflow());

        NitfLabelSegmentHeader labelSegment1 = parseStrategy.getLabelSegmentHeaders().get(0);
        assertNotNull(labelSegment1);
        assertEquals("label", labelSegment1.getIdentifier());
        assertNotNull(labelSegment1.getSecurityMetadata());
        assertEquals(NitfSecurityClassification.UNCLASSIFIED, labelSegment1.getSecurityMetadata().getSecurityClassification());
        assertNull(labelSegment1.getSecurityMetadata().getSecurityClassificationSystem());
        assertEquals("", labelSegment1.getSecurityMetadata().getCodewords());
        assertEquals("Control and Handling", labelSegment1.getSecurityMetadata().getControlAndHandling());
        assertEquals("Releasing Instructions", labelSegment1.getSecurityMetadata().getReleaseInstructions());
        assertNull(labelSegment1.getSecurityMetadata().getDeclassificationType());
        assertNull(labelSegment1.getSecurityMetadata().getDeclassificationDate());
        assertNull(labelSegment1.getSecurityMetadata().getDeclassificationExemption());
        assertNull(labelSegment1.getSecurityMetadata().getDowngrade());
        assertNull(labelSegment1.getSecurityMetadata().getDowngradeDate());
        assertNull(labelSegment1.getSecurityMetadata().getClassificationText());
        assertNull(labelSegment1.getSecurityMetadata().getClassificationAuthorityType());
        assertEquals("Classification", labelSegment1.getSecurityMetadata().getClassificationAuthority());
        assertNull(labelSegment1.getSecurityMetadata().getClassificationReason());
        assertEquals("0", labelSegment1.getSecurityMetadata().getSecurityControlNumber());
        assertEquals("999999", labelSegment1.getSecurityMetadata().getDowngradeDateOrSpecialCase());
        assertEquals(40, labelSegment1.getLabelLocationRow());
        assertEquals(20, labelSegment1.getLabelLocationColumn());
        assertEquals(12, labelSegment1.getLabelCellWidth());
        assertEquals(25, labelSegment1.getLabelCellHeight());
        assertEquals(8, labelSegment1.getLabelDisplayLevel());
        assertEquals(0, labelSegment1.getAttachmentLevel());
        assertEquals("This is a label on Lenna in an \"OverflowTestFile\"!", parseStrategy.getLabelSegmentData().get(0));
        assertEquals(6, labelSegment1.getExtendedHeaderDataOverflow());

        NitfTextSegmentHeader textSegment = parseStrategy.getTextSegmentHeaders().get(0);
        assertNotNull(textSegment);
        assertEquals("Text ID   ", textSegment.getIdentifier());
        assertEquals(0, textSegment.getAttachmentLevel());
        assertEquals("Text Title", textSegment.getTextTitle());
        assertEquals(TextFormat.BASICCHARACTERSET, textSegment.getTextFormat());
        assertEquals("Example of a SideArm text file.  Marc Smelser\r\nCreated this NITFText file 07/07/95", parseStrategy.getTextSegmentData().get(0));
        assertEquals(7, textSegment.getExtendedHeaderDataOverflow());

        NitfDataExtensionSegmentHeader des1 = parseStrategy.getDataExtensionSegmentHeaders().get(0);
        assertNotNull(des1);
        assertEquals("Registered Extensions    ", des1.getIdentifier());
        assertEquals(99, des1.getDESVersion());
        assertEquals("UDHD", des1.getOverflowedHeaderType());
        assertEquals(0, des1.getItemOverflowed());

        NitfDataExtensionSegmentHeader des2 = parseStrategy.getDataExtensionSegmentHeaders().get(1);
        assertNotNull(des2);
        assertEquals("Controlled Extensions    ", des2.getIdentifier());
        assertEquals(99, des2.getDESVersion());
        assertEquals("UDHD", des1.getOverflowedHeaderType());
        assertEquals(0, des1.getItemOverflowed());

        NitfDataExtensionSegmentHeader des3 = parseStrategy.getDataExtensionSegmentHeaders().get(2);
        assertNotNull(des3);
        assertEquals("Registered Extensions    ", des3.getIdentifier());
        assertEquals(99, des3.getDESVersion());
        assertEquals("UDID", des3.getOverflowedHeaderType());
        assertEquals(1, des3.getItemOverflowed());

        NitfDataExtensionSegmentHeader des4 = parseStrategy.getDataExtensionSegmentHeaders().get(3);
        assertNotNull(des4);
        assertEquals("Controlled Extensions    ", des4.getIdentifier());
        assertEquals(99, des4.getDESVersion());
        assertEquals("IXSHD", des4.getOverflowedHeaderType());
        assertEquals(1, des4.getItemOverflowed());

        NitfDataExtensionSegmentHeader des5 = parseStrategy.getDataExtensionSegmentHeaders().get(4);
        assertNotNull(des5);
        assertEquals("Controlled Extensions    ", des5.getIdentifier());
        assertEquals(99, des5.getDESVersion());
        assertEquals("SXSHD", des5.getOverflowedHeaderType());
        assertEquals(1, des5.getItemOverflowed());

        NitfDataExtensionSegmentHeader des6 = parseStrategy.getDataExtensionSegmentHeaders().get(5);
        assertNotNull(des6);
        assertEquals("Controlled Extensions    ", des6.getIdentifier());
        assertEquals(99, des6.getDESVersion());
        assertEquals("LXSHD", des6.getOverflowedHeaderType());
        assertEquals(1, des6.getItemOverflowed());

        NitfDataExtensionSegmentHeader des7 = parseStrategy.getDataExtensionSegmentHeaders().get(6);
        assertNotNull(des7);
        assertEquals("Controlled Extensions    ", des7.getIdentifier());
        assertEquals(99, des7.getDESVersion());
        assertEquals("TXSHD", des7.getOverflowedHeaderType());
        assertEquals(1, des7.getItemOverflowed());

        is.close();
    }

    void assertUnclasAndEmpty(NitfSecurityMetadata securityMetadata) {
        assertNotNull(securityMetadata);
        assertEquals(NitfSecurityClassification.UNCLASSIFIED, securityMetadata.getSecurityClassification());
        assertNull(securityMetadata.getSecurityClassificationSystem());
        assertEquals("", securityMetadata.getCodewords());
        assertEquals("", securityMetadata.getControlAndHandling());
        assertEquals("", securityMetadata.getReleaseInstructions());
        assertNull(securityMetadata.getDeclassificationType());
        assertNull(securityMetadata.getDeclassificationDate());
        assertNull(securityMetadata.getDeclassificationExemption());
        assertNull(securityMetadata.getDowngrade());
        assertNull(securityMetadata.getDowngradeDate());
        assertNull(securityMetadata.getClassificationText());
        assertNull(securityMetadata.getClassificationAuthorityType());
        assertEquals("", securityMetadata.getClassificationAuthority());
        assertNull(securityMetadata.getClassificationReason());
        assertEquals("", securityMetadata.getSecurityControlNumber());
    }
}