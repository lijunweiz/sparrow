package cn.unminded.sparrow.gui.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageJPanel extends JPanel {

    public ImageJPanel(File file) {
        this.setImagePath(file);
    }

    private Image image;
    private int imgWidth;
    private int imgHeight;

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public void setImagePath(File file) {
        try {
            // 该方法会将图像加载到内存，从而拿到图像的详细信息。
            image = ImageIO.read(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setImgWidth(200);
        setImgHeight(350);
        repaint();
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

}
