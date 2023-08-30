package cn.unminded.sparrow.gui.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import static cn.unminded.sparrow.gui.util.UIUtils.getIconImage;
import static cn.unminded.sparrow.gui.util.UIUtils.getPoint;

public class JComponentUtils {

    private JComponentUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取通用JFrame
     * @param title
     * @param dimension
     * @return
     */
    public static JFrame getJFrame(String title, Dimension dimension) {
        JFrame jFrame = new JFrame();
        jFrame.setTitle(title);
        jFrame.setSize(dimension);
        jFrame.setLocation(getPoint(dimension));
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.setIconImage(getIconImage());
        jFrame.setBackground(Color.WHITE);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (exitAppConfirm()) {
                    System.exit(0);
                }
            }
        });

        return jFrame;
    }

    /**
     * 根据指定布局获取可见jPanel
     * @param layout 布局
     * @return
     */
    public static JPanel getJPanel(LayoutManager layout) {
        JPanel jPanel = new JPanel(layout);
        jPanel.setVisible(true);
        return jPanel;
    }

    /**
     * 获取width=60,height=20的可见JButton
     * @param text button上显示的文字
     * @return
     */
    public static JButton getJButton(String text) {
        JButton jButton = new JButton(text);
        jButton.setSize(30, 20);
        return jButton;
    }

    /**
     * 根据指定布局获取可见jPanel
     * @param text label显示的字体
     * @return
     */
    public static JLabel getJLabel(String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setVisible(true);
        return jLabel;
    }

    /**
     * 返回一个下拉列表
     * @param items 下拉选项列表
     * @param <T> 选项列表类型
     * @return
     */
    @SafeVarargs
    public static <T> JComboBox<T> getJComboBox(T ... items) {
        Objects.requireNonNull(items, "不能为空");

        JComboBox<T> jComboBox = new JComboBox<>();
        for (T item : items) {
            jComboBox.addItem(item);
        }

        return jComboBox;
    }

    /**
     * 获取一个文件选择器
     * @return
     */
    public static JFileChooser getJFileChooser() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return jFileChooser;
    }

    public static boolean exitAppConfirm() {
        return JOptionPane.showConfirmDialog(null, "确认关闭应用吗？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

}
