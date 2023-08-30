package cn.unminded.sparrow.gui.component;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class DragAndDrop extends JFrame implements DropTargetListener {
    private static final long serialVersionUID = 1L;
    JLabel cmdLabel = new JLabel("你拖入的文件地址是：");

    public DragAndDrop() {
        Container CP = getContentPane();
        CP.add(cmdLabel, BorderLayout.WEST);
        setSize(300, 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 50);
        setTitle("读取命令行输入");
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
        setVisible(true);
    }

    public void dragEnter(DropTargetDragEvent dtde) {

    }

    public void dragExit(DropTargetEvent dte) {

    }

    public void dragOver(DropTargetDragEvent dtde) {

    }

    public void drop(DropTargetDropEvent dtde) {
        try {
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                List<?> list = (List<?>) (dtde.getTransferable()
                        .getTransferData(DataFlavor.javaFileListFlavor));
                Iterator<?> iterator = list.iterator();
                while (iterator.hasNext()) {
                    File f = (File) iterator.next();
                    this.cmdLabel.setText("你拖入的文件是：" + f.getAbsolutePath());
                }
                dtde.dropComplete(true);
                // this.updateUI();
            } else {
                dtde.rejectDrop();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public static void main(String[] args) {
        DragAndDrop c = new DragAndDrop();
        if (args.length != 0)
            c.cmdLabel.setText(args[0]);
    }
}