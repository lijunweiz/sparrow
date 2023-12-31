package cn.unminded.sparrow.gui;

import cn.unminded.sparrow.gui.component.MainJFrame;
import cn.unminded.sparrow.gui.util.LogUtil;
import cn.unminded.sparrow.gui.util.UIUtils;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Sparrow {

    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIUtils.setFullFont(12f);
        SwingUtilities.invokeLater(() -> {
            MainJFrame mainJFrame = new MainJFrame();
            mainJFrame.setLocationRelativeTo(null);
            mainJFrame.setVisible(true);
            LogUtil.getLogger().info("Sparrow启动成功");
        });
    }

}
