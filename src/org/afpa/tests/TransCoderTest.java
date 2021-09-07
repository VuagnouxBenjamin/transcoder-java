package org.afpa.tests;

import org.afpa.tool.TransCoder;
import org.junit.Assert;
import org.junit.Test;

public class TransCoderTest {

    private String testCryptKey = "6lUjKOzUj4e/Gelw9c6sDLqHniwulClN6XSayZ+HRF/kbZx+CMf95jxrhm4YFSY26OnxVlsrzGkO00IMeAFs3g==";

    @Test
    public void createMapTest() {
        TransCoder transcodeTest = new TransCoder(testCryptKey);
        Assert.assertNotNull(transcodeTest.getEncodeMap());
        System.out.println(transcodeTest.getEncodeMap().toString());
        Assert.assertNotNull(transcodeTest.getDecodeMap());
        System.out.println(transcodeTest.getDecodeMap().toString());
    }

    @Test
    public void encodeMsgTest() {
        TransCoder transcodeTest = new TransCoder(testCryptKey);
        Assert.assertEquals("AEBOBV", transcodeTest.encodeMsg("Ben"));
        System.out.println(transcodeTest.encodeMsg("Ben"));
    }

    @Test
    public void encodeMsgWithAccentsTest() {
        TransCoder transcodeTest = new TransCoder(testCryptKey);
        Assert.assertEquals("AEBOBV", transcodeTest.encodeMsg("Bén"));
        System.out.println(transcodeTest.encodeMsg("Ben"));
    }

    @Test
    public void decodeMsgTest() {
        TransCoder transcodeTest = new TransCoder(testCryptKey);
        Assert.assertEquals("Ben", transcodeTest.decodeMsg("AEBOBV"));
        System.out.println(transcodeTest.encodeMsg("Ben"));
    }
}
