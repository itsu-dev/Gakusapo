package itsu.mcbe.webbrowser.core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class JavaMain extends JPanel {

    private static final double SIZE = 400;

    private static BufferedImage picture;

    private static double newWidth;
    private static double newHeight;

    public static BufferedImage init(String path) {
        try {
        picture = reduce(ImageIO.read(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picture;
    }

    private static BufferedImage reduce(BufferedImage image) {
        double width = image.getWidth();
        double height = image.getHeight();

        newWidth = SIZE * (width / height);
        newHeight = SIZE * (height / width);

        BufferedImage thumb = new BufferedImage((int) newWidth, (int) newHeight, image.getType());
        thumb.getGraphics().drawImage(image.getScaledInstance((int) newWidth, (int) newHeight, Image.SCALE_AREA_AVERAGING), 0, 0, (int) newWidth, (int) newHeight, null);
        return thumb;
    }

    public BufferedImage convolve(BufferedImage img) {
        Kernel kernel = new Kernel(3, 3,
                new float[] {
                        0f, -1f, 0f,
                        -1f, 5f, -1f,
                        0f, -1f, 0f
                });

        BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        op.filter(img, image);

        return image;
    }

    public BufferedImage grayScale(BufferedImage img) {
        ColorSpace space = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        BufferedImageOp op = new ColorConvertOp(space, null);
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        op.filter(img, image);

        return image;
    }

    public static BufferedImage transform(BufferedImage img, double radian) {
        AffineTransform at = AffineTransform.getRotateInstance(radian, 64, 64);
        AffineTransformOp op = new AffineTransformOp(at, null);
        BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        op.filter(img, image);

        return image;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);

        BufferedImage image = convolve(picture);

        int black = Color.BLACK.getRGB();
        int red = Color.RED.getRGB();
        for (int x = 0; x < picture.getWidth(); x++) {
            for (int y = 0; y < picture.getHeight(); y++) {
                if (image.getRGB(x, y) != black) {
                    image.setRGB(x, y, red);
                }
            }
        }

        g2.drawImage(picture, 0, 10, null);
        g2.drawImage(image, picture.getWidth() + 10, 10, null);
        g2.drawString("鮮鋭化", picture.getWidth() + 15, picture.getHeight() + 30);
    }

}
