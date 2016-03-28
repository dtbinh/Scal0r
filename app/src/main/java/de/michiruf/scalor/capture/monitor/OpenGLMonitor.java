//package de.michiruf.scalor.capture.monitor;
//
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GLCapabilities;
//import com.jogamp.opengl.awt.GLCanvas;
//import de.michiruf.scalor.config.Configuration;
//
//import javax.inject.Inject;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.nio.ByteBuffer;
//import java.util.Observable;
//
///**
// * @author lukasz1985
// * @see <a href="http://gamedev.stackexchange.com/questions/72911/how-to-save-am-image-of-a-screen-using-jogl/72915#72915">Stackexchange</a>
// */
//public class OpenGLMonitor implements Monitor {
//
//    private final Configuration configuration;
//
//    @Inject
//    public OpenGLMonitor(Configuration configuration, GLCapabilities glCapabilities) {
//        this.configuration = configuration;
//        canvas = new GLCanvas(glCapabilities);
//        configuration.addObserver(this);
//        updateDimens();
//    }
//
//    @Override
//    public BufferedImage captureScreen() {
//        int x = canvas.getX();
//        int y = canvas.getY();
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//
//        ByteBuffer buffer = ByteBuffer.allocate(width * height * 3);
//        canvas.getGL().glReadPixels(x, y, width, height, GL2.GL_RGB, GL2.GL_BYTE, buffer);
//
//        BufferedImage screenshot = new BufferedImage(width, height,
//                BufferedImage.TYPE_INT_RGB);
//        Graphics graphics = screenshot.getGraphics();
//        for (int h = 0; h < width; h++) {
//            for (int w = 0; w < height; w++) {
//                // The color are the three consecutive bytes, it's like referencing
//                // to the next consecutive array elements, so we got red, green, blue..
//                // red, green, blue, and so on..
//                graphics.setColor(new Color(buffer.get() * 2, buffer.get() * 2, buffer.get() * 2));
//                graphics.drawRect(w, h, 1, 1);
//            }
//        }
//        return screenshot;
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        updateDimens();
//    }
//
//    private void updateDimens() {
//        canvas.setBounds(configuration.getScanX(), configuration.getScanY(),
//                configuration.getScanWidth(), configuration.getScanHeight());
//    }
//}
