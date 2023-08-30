package cn.unminded.sparrow.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

/**
 * 进度条
 * @since 3.2
 */
public class SplashScreen extends JWindow {

    private static final long serialVersionUID = 1L;
    private final JProgressBar jProgressBar = new JProgressBar(0, 100);

    public SplashScreen() {
        setLayout(new BorderLayout());
        add(loadLogo(), BorderLayout.CENTER);
        add(jProgressBar, BorderLayout.SOUTH);
        jProgressBar.setBackground(Color.CYAN);
        setAutoRequestFocus(true);
        setBackground(Color.GREEN);
        pack();
        setLocationRelativeTo(null);
    }

    public static JComponent loadLogo() {
        JLabel logo = new JLabel();
        logo.setBorder(new EmptyBorder(10, 10, 10, 10));
        URL url = SplashScreen.class.getClassLoader().getResource("img/donate.png");
        if (Objects.nonNull(url)) {
            logo.setIcon(new ImageIcon(url));
        } else {
            logo.setText("<html><h1>Sparrow</h1></html>");
        }
        return logo;
    }

    public void showScreen() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            setAlwaysOnTop(true);
        });
    }

    public void close() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            dispose();
        });
    }

    public void setProgress(final int progress) {
        SwingUtilities.invokeLater(() -> jProgressBar.setValue(progress));
    }
}
