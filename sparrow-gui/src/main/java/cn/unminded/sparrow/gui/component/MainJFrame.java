package cn.unminded.sparrow.gui.component;

import cn.unminded.sparrow.define.ConvertOutputFormat;
import cn.unminded.sparrow.config.SparrowConstants;
import cn.unminded.sparrow.metric.ConvertMetric;
import cn.unminded.sparrow.delegate.PDFSparrowConverterDelegate;
import cn.unminded.sparrow.util.LogUtil;
import cn.unminded.sparrow.util.OutputModeEnum;
import cn.unminded.sparrow.util.PageSizeEnum;
import cn.unminded.sparrow.gui.component.DragJPanel;
import cn.unminded.sparrow.gui.util.UIUtils;
import cn.unminded.sparrow.gui.util.JComponentUtils;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Objects;

public class MainJFrame extends JFrame {

    private JLabel sourceFolder = null;
    private JLabel targetFolder = null;

    private static final PDFSparrowConverterDelegate PDF_SPARROW_CONVERTER_DELEGATE;

    static {
        PDF_SPARROW_CONVERTER_DELEGATE = new PDFSparrowConverterDelegate();
    }


    public MainJFrame() {
        Dimension dimension = new Dimension(700, 500);
        setTitle("Sparrow PDF");
        setSize(dimension);
        setLocation(UIUtils.getPoint(dimension));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(UIUtils.getIconImage("img/sw.png"));
        setBackground(Color.WHITE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JComponentUtils.exitAppConfirm()) {
                    LogUtil.getLogger().info("程序退出成功");
                    System.exit(0);
                }
            }
        });

        initComponents();
    }

    private void initComponents() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu helpMenu = new JMenu();
        JMenuItem aboutMenuItem = new JMenuItem();
        JMenuItem feedbackMenuItem = new JMenuItem();
        JMenuItem donateMenuItem = new JMenuItem();
        sourceFolder = JComponentUtils.getJLabel("");
        targetFolder = JComponentUtils.getJLabel("");

        // 帮助按钮
        {
            helpMenu.setText("帮助");
            helpMenu.setMnemonic('H');
            //---- aboutMenuItem ----
            aboutMenuItem.setText("关于");
            aboutMenuItem.setMnemonic('A');
            aboutMenuItem.addActionListener(e -> aboutAction());
            helpMenu.add(aboutMenuItem);

            //---- feedbackMenuItem ----
            feedbackMenuItem.setText("快速反馈");
            feedbackMenuItem.setMnemonic('F');
            feedbackMenuItem.addActionListener(e -> feedbackAction());
            helpMenu.add(feedbackMenuItem);

            //---- donateMenuItem ----
            donateMenuItem.setText("支持作者");
            donateMenuItem.addActionListener(e -> donateAction());
            helpMenu.add(donateMenuItem);
        }
        jMenuBar.add(helpMenu);
        setJMenuBar(jMenuBar);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel clickChooseJPanel = JComponentUtils.getJPanel(new GridLayout());
        DragJPanel dragChooseJPanel = new DragJPanel(new FlowLayout());
        JPanel controlJPanel = JComponentUtils.getJPanel(new GridLayout(1, 3));
        contentPane.add(clickChooseJPanel, BorderLayout.NORTH);
        contentPane.add(dragChooseJPanel, BorderLayout.CENTER);
        contentPane.add(controlJPanel, BorderLayout.SOUTH);

        clickChooseFile(clickChooseJPanel);
        dragChooseFile(dragChooseJPanel);
        setControl(controlJPanel);
        // 用户按钮
        FlatButton usersButton = new FlatButton();
        usersButton.setIcon(new FlatSVGIcon("img/user.svg"));
        usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
        usersButton.setFocusable(false);
        usersButton.addActionListener(e -> JOptionPane.showMessageDialog( this, "Hello ! How are you?", "You", JOptionPane.INFORMATION_MESSAGE));
        jMenuBar.add(Box.createGlue());
        jMenuBar.add(usersButton);
    }

    private void dragChooseFile(DragJPanel dragChooseJPanel) {

    }

    private void clickChooseFile(JPanel choose) {
        JPanel jPanel = JComponentUtils.getJPanel(new GridLayout(2, 2));
        choose.add("chooseFile", jPanel);

        JButton sourceButton = JComponentUtils.getJButton("选择文件或目录");
        JButton targetButton = JComponentUtils.getJButton("输出目录");

        jPanel.add("sourceButton", sourceButton);
        jPanel.add("sourceFolder", sourceFolder);
        jPanel.add("targetButton", targetButton);
        jPanel.add("targetFolder", targetFolder);

        sourceFolder.setHorizontalAlignment(SwingConstants.LEFT);

        sourceButton.setHorizontalAlignment(SwingConstants.LEFT);
        sourceButton.setToolTipText("支持图片格式" + String.join(",", SparrowConstants.SUPPORT_IMAGE_TYPE));
        sourceButton.addActionListener(e -> {
            JFileChooser jFileChooser = JComponentUtils.getJFileChooser();
            int option = jFileChooser.showOpenDialog(choose);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                sourceFolder.setText(file.getAbsolutePath());
                if (file.isFile()) {
                    targetFolder.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.') + 1) +  "pdf");
                } else {
                    targetFolder.setText(file.getAbsolutePath());
                }
            }
        });

        targetFolder.setHorizontalAlignment(SwingConstants.LEFT);

        targetButton.setHorizontalAlignment(SwingConstants.LEFT);
        targetButton.setToolTipText("默认与源目录一致");
        targetButton.addActionListener(e -> {
            JFileChooser jFileChooser = JComponentUtils.getJFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = jFileChooser.showOpenDialog(choose);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                targetFolder.setText(file.getAbsolutePath());
            }
        });
    }

    private void setControl(JPanel bottom) {
        JPanel jPanel = JComponentUtils.getJPanel(new FlowLayout());
        bottom.add(jPanel);
        JComboBox<String> jComboBox = JComponentUtils.getJComboBox("独立输出", "合并输出");
        jPanel.add(jComboBox);

        JButton start = JComponentUtils.getJButton("开始");
        start.setName("转换按钮");
        start.setMnemonic(MouseEvent.MOUSE_PRESSED);
        start.addActionListener(l -> {
            if ("开始".equals(l.getActionCommand())) {
                start.setEnabled(false);
                if (sourceFolder.getText().isBlank()) {
                    JOptionPane.showMessageDialog(this, "待转换文件不能为空", "警告", JOptionPane.WARNING_MESSAGE);
                    start.setEnabled(true);
                    return;
                }

                ConvertOutputFormat format = ConvertOutputFormat.defaultOutputFormat();
                format.setSource(sourceFolder.getText());
                format.setTarget(targetFolder.getText());
                format.setSizeEnum(PageSizeEnum.ORIGINAL);
                if (Objects.equals(jComboBox.getSelectedItem(), "合并输出")) {
                    format.setOutputModeEnum(OutputModeEnum.MERGE);
                }
                try {
                    PDF_SPARROW_CONVERTER_DELEGATE.convert(format);
                    JOptionPane.showMessageDialog(this, "处理完成", "转换结果", JOptionPane.INFORMATION_MESSAGE);
                    SwingUtilities.invokeLater(() -> {
                        try {
                            Desktop.getDesktop().open(new File(format.getTarget()));
                        } catch (IOException e) {
                            LogUtil.getLogger().error("打开{}异常, 详细原因：", format.getTarget(), e);
                        }
                    });
                } catch (Exception e) {
                    LogUtil.getLogger().error("转换异常: ", e);
                    JOptionPane.showMessageDialog(this, "处理失败！", "转换结果", JOptionPane.ERROR_MESSAGE);
                } finally {
                    start.setEnabled(true);
                }
            }
        });
        jPanel.add(start);
    }

    private void aboutAction() {
        JLabel titleLabel = new JLabel( "Sparrow" );
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "h1" );

        String link = "https://www.unminded.cn/sparrow";
        JLabel linkLabel = new JLabel( "<html><a href=\"#\">" + link + "</a></html>" );
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch(IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(linkLabel,
                            "Failed to open '" + link + "' in browser.",
                            "About", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        JOptionPane.showMessageDialog( this,
                new Object[] {
                        titleLabel,
                        "简单易用的PDF格式转换工具",
                        " ",
                        "Copyright 2023-" + Year.now() + " unminded Software",
                        linkLabel,
                },
                "关于", JOptionPane.PLAIN_MESSAGE);
    }

    private void feedbackAction() {
        String link = "https://www.unminded.cn/sparrow/feedback?t=" + System.currentTimeMillis();
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch(IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to open '" + link + "' in browser.",
                    "About", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void donateAction() {
        ImageIcon imageIcon = UIUtils.getImageIcon("img/donate.png");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(230, 300, Image.SCALE_SMOOTH));
        JOptionPane.showMessageDialog(this, null, "支持作者", JOptionPane.INFORMATION_MESSAGE, imageIcon);
    }
}
