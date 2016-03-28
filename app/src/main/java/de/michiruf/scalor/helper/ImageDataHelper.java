package de.michiruf.scalor.helper;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public class ImageDataHelper {

    public static byte[] getBufferedImageByteData(BufferedImage image) {
        DataBuffer buffer = image.getRaster().getDataBuffer();
        if (buffer instanceof DataBufferByte) {
            return ((DataBufferByte) buffer).getData();
        }

        throw new RuntimeException("Not containing byte data.");
    }

    public static int[] getBufferedImageIntData(BufferedImage image) {
        DataBuffer buffer = image.getRaster().getDataBuffer();
        if (buffer instanceof DataBufferInt) {
            return ((DataBufferInt) buffer).getData();
        }

        throw new RuntimeException("Not containing int data.");
    }
}
