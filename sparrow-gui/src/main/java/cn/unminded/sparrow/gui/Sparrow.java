package cn.unminded.sparrow.gui;

import cn.unminded.sparrow.gui.component.MainJFrame;
import cn.unminded.sparrow.gui.util.UIUtils;
import cn.unminded.sparrow.util.LogUtil;
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
            LogUtil.getLogger().info("程序启动成功");
        });
    }

}
