package de.michiruf.scalor.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

/**
 * @author Michael Ruf
 * @since 2016-03-28
 */
public class ImageDataHelper {

    public static byte[] getRGBData(BufferedImage image) {
        DataBuffer buffer = image.getRaster().getDataBuffer();
        int[] dataInt = ((DataBufferInt) buffer).getData();

        ByteBuffer result = ByteBuffer.allocate(dataInt.length * 3);
        for (int pixel : dataInt) {
            // @see Color.getRed(), Color.getGreen(), Color.getBlue()
            result.put((byte) ((pixel >> 16) & 0xFF));
            result.put((byte) ((pixel >> 8) & 0xFF));
            result.put((byte) (pixel & 0xFF));
        }

        return result.array();
    }

    public static BufferedImage getBufferedImage(int width, int height, byte[] data) {
        return getBufferedImage(width, height, ByteBuffer.wrap(data));
    }

    public static BufferedImage getBufferedImage(int width, int height, ByteBuffer buffer) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        for (int h = 0; h < width; h++) {
            for (int w = 0; w < height; w++) {
                graphics.setColor(new Color(ub(buffer.get()), ub(buffer.get()), ub(buffer.get())));
                graphics.drawRect(w, height - h, 1, 1);
            }
        }
        return image;
    }

    private static int ub(byte b) {
        // Unsigned negation
        return b & 0xFF;
    }
}
