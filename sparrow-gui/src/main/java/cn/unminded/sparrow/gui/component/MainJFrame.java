package cn.unminded.sparrow.gui.component;

import cn.unminded.sparrow.config.SparrowConstants;
import cn.unminded.sparrow.config.SparrowThreadPool;
import cn.unminded.sparrow.define.ConvertOutputFormat;
import cn.unminded.sparrow.define.SparrowConverterException;
import cn.unminded.sparrow.delegate.PDFSparrowConverterDelegate;
import cn.unminded.sparrow.gui.util.JComponentUtils;
import cn.unminded.sparrow.gui.util.LogUtil;
import cn.unminded.sparrow.gui.util.MenuNameEnum;
import cn.unminded.sparrow.gui.util.UIUtils;
import cn.unminded.sparrow.info.ChangeInfo;
import cn.unminded.sparrow.util.ConvertFormatEnum;
import cn.unminded.sparrow.util.OutputModeEnum;
import cn.unminded.sparrow.util.PageSizeEnum;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatButton;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class MainJFrame extends JFrame {

    /** 当前的转换模式 */
    private String currentMode = MenuNameEnum.IMAGE_TO_PDF_MENU.getName();

    private JButton sourceButton = null;
    private JButton targetButton = null;
    private JTextField  sourceFolder = null;
    private JTextField  targetFolder = null;
    private JComboBox<String> jComboBox = null;
    private JButton start = null;
    JPanel pageSet = null;

    private static final PDFSparrowConverterDelegate PDF_SPARROW_CONVERTER_DELEGATE;

    static {
        PDF_SPARROW_CONVERTER_DELEGATE = new PDFSparrowConverterDelegate();
    }


    public MainJFrame() {
        Dimension dimension = new Dimension(700, 500);
        setResizable(false);
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
                    LogUtil.getLogger().info("Sparrow退出成功");
                    System.exit(0);
                }
            }
        });

        initComponents();
    }

    private void initComponents() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu transModeMenu = new JMenu();
        JMenuItem imageToPdfMenuItem = new JMenuItem();
        JMenuItem pdfToWordMenuItem = new JMenuItem();
        JMenuItem pdfSplitMenuItem = new JMenuItem();
        JMenuItem pdfMergeMenuItem = new JMenuItem();

        JMenu helpMenu = new JMenu();
        JMenuItem aboutMenuItem = new JMenuItem();
        JMenuItem feedbackMenuItem = new JMenuItem();
        JMenuItem donateMenuItem = new JMenuItem();

        sourceButton = JComponentUtils.getJButton("选择文件或目录");
        targetButton = JComponentUtils.getJButton("输出目录");
        sourceFolder = new JTextField();
        sourceFolder.setEditable(false);
        targetFolder = new JTextField();
        targetFolder.setEditable(false);
        jComboBox = JComponentUtils.getJComboBox("独立输出", "合并输出");
        start = JComponentUtils.getJButton(MenuNameEnum.START_MENU.getName());

        // 转换模式
        {
            transModeMenu.setText(MenuNameEnum.TRANS_MODE_MENU.getName());
            transModeMenu.setMnemonic(MenuNameEnum.TRANS_MODE_MENU.getMnemonic());

            //---- imageToPdfMenuItem ----
            imageToPdfMenuItem.setText(MenuNameEnum.IMAGE_TO_PDF_MENU.getName());
            imageToPdfMenuItem.setMnemonic(MenuNameEnum.IMAGE_TO_PDF_MENU.getMnemonic());
            imageToPdfMenuItem.addActionListener(this::imageToPdfAction);
            transModeMenu.add(imageToPdfMenuItem);

            //---- pdfToWordMenuItem ----
            pdfToWordMenuItem.setText(MenuNameEnum.PDF_TO_WORD_MENU.getName());
            pdfToWordMenuItem.setMnemonic(MenuNameEnum.PDF_TO_WORD_MENU.getMnemonic());
            pdfToWordMenuItem.addActionListener(this::pdfToWordAction);
            transModeMenu.add(pdfToWordMenuItem);

            //---- pdfSplitMenuItem ----
            pdfSplitMenuItem.setText(MenuNameEnum.PDF_SPLIT.getName());
            pdfSplitMenuItem.setMnemonic(MenuNameEnum.PDF_SPLIT.getMnemonic());
            pdfSplitMenuItem.addActionListener(this::pdfSplitAction);
            transModeMenu.add(pdfSplitMenuItem);

            //---- pdfMergeMenuItem ----
            pdfMergeMenuItem.setText(MenuNameEnum.PDF_MERGE.getName());
            pdfMergeMenuItem.setMnemonic(MenuNameEnum.PDF_MERGE.getMnemonic());
            pdfMergeMenuItem.addActionListener(this::pdfMergeAction);
            transModeMenu.add(pdfMergeMenuItem);
        }
        jMenuBar.add(transModeMenu);

        // 帮助按钮
        {
            helpMenu.setText(MenuNameEnum.HELP_MENU.getName());
            helpMenu.setMnemonic(MenuNameEnum.HELP_MENU.getMnemonic());
            //---- aboutMenuItem ----
            aboutMenuItem.setText(MenuNameEnum.ABOUT_MENU.getName());
            aboutMenuItem.setMnemonic(MenuNameEnum.ABOUT_MENU.getMnemonic());
            aboutMenuItem.addActionListener(e -> aboutAction());
            helpMenu.add(aboutMenuItem);

            //---- feedbackMenuItem ----
            feedbackMenuItem.setText(MenuNameEnum.FEEDBACK_MENU.getName());
            feedbackMenuItem.setMnemonic(MenuNameEnum.FEEDBACK_MENU.getMnemonic());
            feedbackMenuItem.addActionListener(e -> feedbackAction());
            helpMenu.add(feedbackMenuItem);

            //---- donateMenuItem ----
            donateMenuItem.setText(MenuNameEnum.DONATE_MENU.getName());
            donateMenuItem.addActionListener(e -> donateAction());
            helpMenu.add(donateMenuItem);
        }
        jMenuBar.add(helpMenu);
        setJMenuBar(jMenuBar);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel clickChooseJPanel = JComponentUtils.getJPanel(new GridLayout(2, 1));
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

    private void imageToPdfAction(ActionEvent e) {
        jComboBox.setEnabled(true);
        currentMode = MenuNameEnum.IMAGE_TO_PDF_MENU.getName();
        sourceButton.setText("选择图片或目录");
        sourceButton.setToolTipText("支持图片格式" + String.join(",", SparrowConstants.SUPPORT_IMAGE_TYPE));
        sourceFolder.setText("");
        sourceFolder.setToolTipText("");

        targetButton.setText("输出目录");
        targetButton.setToolTipText("输出目录默认与源文件一致");
        targetFolder.setText("");
        targetFolder.setToolTipText("");

        setPageSetJPanel();
    }

    private void pdfToWordAction(ActionEvent e) {
        jComboBox.setEnabled(false);
        currentMode = MenuNameEnum.PDF_TO_WORD_MENU.getName();
        sourceButton.setText("选择PDF文件");
        sourceButton.setToolTipText("点击选择目标PDF文件");
        sourceFolder.setText("");
        targetButton.setText("输出目录");
        targetButton.setToolTipText("输出目录默认与源文件一致");
        targetFolder.setText("");

        setPageSetJPanel();
    }

    private void pdfSplitAction(ActionEvent e) {
        currentMode = MenuNameEnum.PDF_SPLIT.getName();
        sourceButton.setText("选择PDF文件");
        sourceButton.setToolTipText("点击选择目标PDF文件");
        targetButton.setEnabled(false);

        setPageSetJPanel();
    }

    private void pdfMergeAction(ActionEvent e) {
        currentMode = MenuNameEnum.PDF_MERGE.getName();
        sourceButton.setText("选择PDF文件");
        sourceButton.setToolTipText("点击选择目标PDF文件");
        targetButton.setEnabled(false);

        setPageSetJPanel();
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

    private java.util.List<String> actionNameList = Arrays.asList(MenuNameEnum.PDF_TO_WORD_MENU.getName(), MenuNameEnum.PDF_MERGE.getName(), MenuNameEnum.PDF_SPLIT.getName());

    private void clickChooseFile(JPanel choose) {
        JPanel jPanel = JComponentUtils.getJPanel(new GridLayout(2, 2));
        pageSet = JComponentUtils.getJPanel(new FlowLayout(FlowLayout.LEFT));
        choose.add("chooseFile", jPanel);
        choose.add("pageSet", pageSet);
        this.outPageControl(pageSet);

        jPanel.add("sourceButton", sourceButton);
        jPanel.add("sourceFolder", sourceFolder);
        jPanel.add("targetButton", targetButton);
        jPanel.add("targetFolder", targetFolder);

        sourceFolder.setHorizontalAlignment(SwingConstants.LEFT);
        sourceButton.setHorizontalAlignment(SwingConstants.LEFT);
        sourceButton.setToolTipText("支持图片格式" + String.join(",", SparrowConstants.SUPPORT_IMAGE_TYPE));
        sourceButton.addActionListener(e -> {
            JFileChooser jFileChooser = JComponentUtils.getJFileChooser();
            if (actionNameList.contains(currentMode)) {
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            } else {
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            }
            int option = jFileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                if (Objects.isNull(file)) {
                    return;
                }
                this.setSourceButton(file, jFileChooser.getSelectedFiles());
            }
        });

        targetFolder.setHorizontalAlignment(SwingConstants.LEFT);
        targetButton.setHorizontalAlignment(SwingConstants.LEFT);
        targetButton.setToolTipText("默认与源目录一致");
        targetButton.addActionListener(e -> {
            JFileChooser jFileChooser = JComponentUtils.getJFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = jFileChooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                targetFolder.setText(file.getAbsolutePath());
                targetFolder.setToolTipText(file.getAbsolutePath());
            }
        });
    }

    private void outPageControl(JPanel pageSet) {
        pageSet.add(JComponentUtils.getJLabel("<html>&nbsp;&nbsp;&nbsp;开始页: </html>"));
        JTextField startPageTextField = new JTextField();
        pageSet.add(startPageTextField);
        JLabel splitLength = JComponentUtils.getJLabel("<html>&nbsp;&nbsp;分割大小: </html>");
        splitLength.setToolTipText("当分割大小是0时，意味着开始页和结束页之间的页面不会拆分");
        pageSet.add(splitLength);
        JTextField pageLengthTextField = new JTextField();
        pageSet.add(pageLengthTextField);
        pageSet.add(JComponentUtils.getJLabel("<html>&nbsp;&nbsp;结束页: </html>"));
        JTextField endPageTextField = new JTextField();
        pageSet.add(endPageTextField);

        pageSet.setEnabled(false);
    }

    private void setSourceButton(File file, File[] selectedFiles) {
        if (Objects.equals(currentMode, MenuNameEnum.IMAGE_TO_PDF_MENU.getName())) {
            boolean dirNoImg = false;
            if (file.isDirectory() && Objects.nonNull(file.list())) {
                String[] list = file.list(((dir, name) -> SparrowConstants.SUPPORT_IMAGE_TYPE.contains(name.substring(name.lastIndexOf(".") + 1))));
                dirNoImg = Objects.isNull(list) || list.length == 0;
            }
            if (dirNoImg || file.isFile() && !SparrowConstants.SUPPORT_IMAGE_TYPE.contains(file.getName().substring(file.getName().lastIndexOf('.') + 1))) {
                JOptionPane.showMessageDialog(null, "未发现有效的图片文件", "警告", JOptionPane.WARNING_MESSAGE);
                return;
            }

            sourceFolder.setText(file.getAbsolutePath());
            sourceFolder.setToolTipText(file.getAbsolutePath());
            if (file.isFile()) {
                targetFolder.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.') + 1) +  "pdf");
                targetFolder.setToolTipText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.') + 1) +  "pdf");
            } else {
                targetFolder.setText(file.getAbsolutePath());
                targetFolder.setToolTipText(file.getAbsolutePath());
            }
        }

        if (Objects.equals(currentMode, MenuNameEnum.PDF_TO_WORD_MENU.getName())
                || Objects.equals(currentMode, MenuNameEnum.PDF_SPLIT.getName())) {
            if (!file.isFile() || !file.getName().toLowerCase(Locale.ROOT).endsWith(".pdf")) {
                JOptionPane.showMessageDialog(null, "未发现有效的PDF文件", "警告", JOptionPane.WARNING_MESSAGE);
                return;
            }

            sourceFolder.setText(file.getAbsolutePath());
            sourceFolder.setToolTipText(file.getAbsolutePath());
            if (Objects.equals(currentMode, MenuNameEnum.PDF_TO_WORD_MENU.getName())) {
                targetFolder.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.') + 1) + "doc");
                targetFolder.setToolTipText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.') + 1) + "doc");
            } else {
                targetFolder.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));
                targetFolder.setToolTipText(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator)));
            }
        }
    }


    private void dragChooseFile(DragJPanel dragChooseJPanel) {
        // ignore
    }

    private void setControl(JPanel bottom) {
        JPanel jPanel = JComponentUtils.getJPanel(new FlowLayout());
        bottom.add(jPanel);

        jPanel.add(jComboBox);
        jPanel.add(start);

        start.setMnemonic(MenuNameEnum.START_MENU.getMnemonic());
        start.addActionListener(l -> {
            if (!MenuNameEnum.START_MENU.getName().equals(l.getActionCommand())) {
                return;
            }

            start.setEnabled(false);
            if (sourceFolder.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "待转换文件不能为空", "警告", JOptionPane.WARNING_MESSAGE);
                start.setEnabled(true);
                return;
            }

            final ConvertOutputFormat outputFormat;
            try {
                outputFormat = this.getOutputFormat();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "警告", JOptionPane.WARNING_MESSAGE);
                start.setEnabled(true);
                return;
            }

            try {
                SparrowThreadPool.execute(() -> PDF_SPARROW_CONVERTER_DELEGATE.convert(outputFormat)).join();
                JOptionPane.showMessageDialog(null, "处理完成", "转换结果", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> {
                    try {
                        Desktop.getDesktop().open(new File(outputFormat.getTarget()));
                    } catch (IOException e) {
                        LogUtil.getLogger().error("打开{}异常, 详细原因：", outputFormat.getTarget(), e);
                    }
                });
            } catch (Exception e) {
                LogUtil.getLogger().error("转换异常: ", e);
                JOptionPane.showMessageDialog(null, "处理失败！", "转换结果", JOptionPane.ERROR_MESSAGE);
            } finally {
                start.setEnabled(true);
            }
        });
    }

    private ConvertOutputFormat getOutputFormat() {
        ConvertOutputFormat format = ConvertOutputFormat.defaultOutputFormat();
        format.setSource(sourceFolder.getText());
        format.setTarget(targetFolder.getText());
        format.setSizeEnum(PageSizeEnum.ORIGINAL);

        if (Objects.equals(currentMode, MenuNameEnum.IMAGE_TO_PDF_MENU.getName())) {
            format.setConvertFormatEnum(ConvertFormatEnum.IMAGE_TO_PDF);
        } else if (Objects.equals(currentMode, MenuNameEnum.PDF_TO_WORD_MENU.getName())) {
            format.setConvertFormatEnum(ConvertFormatEnum.PDF_TO_WORD);
        } else if (Objects.equals(currentMode, MenuNameEnum.PDF_SPLIT.getName())) {
            format.setConvertFormatEnum(ConvertFormatEnum.PDF_SPLIT);
            format.setPdfChangeInfo(this.getPdfChangeInfo());
        } else if (Objects.equals(currentMode, MenuNameEnum.PDF_MERGE.getName())) {
            format.setConvertFormatEnum(ConvertFormatEnum.PDF_MERGE);
        } else {
            throw new SparrowConverterException("不支持的转换模式");
        }

        if (Objects.equals(currentMode, MenuNameEnum.PDF_TO_WORD_MENU.getName())
                || Objects.equals(currentMode, MenuNameEnum.PDF_MERGE.getName())
                || jComboBox.isEnabled() && Objects.equals(jComboBox.getSelectedItem(), "合并输出")) {
            format.setOutputModeEnum(OutputModeEnum.MERGE);
        }

        return format;
    }

    /**
     * 页面拆分或页面分割
     * @return changeInfo
     */
    private ChangeInfo getPdfChangeInfo() {
        JTextField startPage = (JTextField) pageSet.getComponent(1);
        JTextField pageSize = (JTextField) pageSet.getComponent(3);
        JTextField endPage = (JTextField) pageSet.getComponent(5);

        ChangeInfo changeInfo = new ChangeInfo();
        try {
            changeInfo.setStartPage(Integer.parseInt(startPage.getText()));
            changeInfo.setSplitLength(Integer.parseInt(pageSize.getText()));
            changeInfo.setEndPage(Integer.parseInt(endPage.getText()));
        } catch (NumberFormatException e) {
            throw new SparrowConverterException("开始页: " + startPage.getText() + "，分割大小: " + pageSize.getText() + "，结束页: " + endPage.getText() + "，\n三者中有非数字字符，请核对");
        }

        if (changeInfo.getStartPage() < 1) {
            throw new SparrowConverterException("开始页不能小于1");
        }

        if (changeInfo.getStartPage() > changeInfo.getEndPage()) {
            throw new SparrowConverterException("开始页不能大于结束页");
        }

        if (changeInfo.getEndPage() - changeInfo.getStartPage() < changeInfo.getSplitLength()) {
            throw new SparrowConverterException("页面分割大小不能大于结束页和结束页之间的页面数量");
        }

        try {
            PDDocument load = PDDocument.load(new File(sourceFolder.getText()));
            int count = load.getPages().getCount();
            if (changeInfo.getStartPage() > count || changeInfo.getSplitLength() > count || changeInfo.getEndPage() > count) {
                throw new SparrowConverterException("开始页: " + startPage.getText() + "分割大小: " + pageSize.getText() + "结束页: " + endPage.getText() + "，三者均不能大于文档的总页数:" + count);
            }
        } catch (IOException e) {
            LogUtil.getLogger().error("{}读取失败", sourceFolder.getText(), e);
            throw new SparrowConverterException(sourceFolder.getText() + "读取失败");
        }

        return changeInfo;
    }

    /**
     * 统一处理页面的功能按钮
     */
    private void setPageSetJPanel() {
        pageSet.setVisible(Objects.equals(currentMode, MenuNameEnum.PDF_SPLIT.getName()));
    }

}
