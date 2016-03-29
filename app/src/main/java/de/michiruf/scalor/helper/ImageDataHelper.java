package de.michiruf.scalor.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
// TODO image data and buffered image functions are not inversed functions but should be
public class ImageDataHelper {

    public static byte[] getImageByteData(BufferedImage image) {
        DataBuffer buffer = image.getRaster().getDataBuffer();
        return ((DataBufferByte) buffer).getData();
    }

    public static int[] getImageIntData(BufferedImage image) {
        DataBuffer buffer = image.getRaster().getDataBuffer();
        return ((DataBufferInt) buffer).getData();
    }

    public static BufferedImage getBufferedImage(int width, int height, byte[] data) {
        return getBufferedImage(width, height, ByteBuffer.wrap(data));
    }

    public static BufferedImage getBufferedImage(int width, int height, ByteBuffer buffer) {
        BufferedImage screenshot = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = screenshot.getGraphics();
        for (int h = 0; h < width; h++) {
            for (int w = 0; w < height; w++) {
                // The color are the three consecutive bytes, it's like referencing
                // to the next consecutive array elements, so we got red, green, blue..
                // red, green, blue, and so on..
                graphics.setColor(new Color(buffer.get() * 2, buffer.get() * 2, buffer.get() * 2));
                graphics.drawRect(w, h, 1, 1);
            }
        }
        return screenshot;
    }
}
