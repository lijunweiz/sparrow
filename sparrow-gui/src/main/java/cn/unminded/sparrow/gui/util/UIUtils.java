package cn.unminded.sparrow.gui.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class UIUtils {

    private UIUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 全局设置字体
     */
    public static void setFullFont(float size) {
        if (size > 0) {
            UIManager.getLookAndFeelDefaults().forEach((k, v) -> {
                if (v instanceof FontUIResource) {
                    FontUIResource fontUIResource = (FontUIResource) v;
                    UIManager.put(k, new FontUIResource(fontUIResource.deriveFont(size)));
                }
            });
        }
    }

    /**
     * 获取窗口的位置居中
     * @param dimension
     * @return
     */
    public static Point getPoint(Dimension dimension) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point((int) ((screenSize.getWidth() - dimension.getWidth()) / 2), (int) ((screenSize.getHeight() - dimension.getHeight()) / 2));
    }

    /**
     * 获取窗口图标
     * @return
     */
    public static Image getIconImage(String ...path) {
        String iconPath = "img/icon.png";
        URL resource = UIUtils.class.getClassLoader().getResource(Objects.isNull(path) || path.length == 0 ? iconPath : path[0]);
        if (Objects.isNull(resource)) {
            return null;
        }
        ImageIcon imageIcon = new ImageIcon(resource);
        return imageIcon.getImage();//.getScaledInstance(15, 15, Image.SCALE_DEFAULT)
    }

    /**
     * 获取窗口图标
     * @return
     */
    public static ImageIcon getImageIcon(String path) {
        URL resource = UIUtils.class.getClassLoader().getResource(path);
        if (Objects.isNull(resource)) {
            return null;
        }
        return new ImageIcon(resource);
    }

}
