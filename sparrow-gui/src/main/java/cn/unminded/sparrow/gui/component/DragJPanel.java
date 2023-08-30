package cn.unminded.sparrow.gui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;

public class DragJPanel extends JPanel implements DropTargetListener {

    public DragJPanel(LayoutManager layout) {
        super(layout);
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
        setVisible(true);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragExit(DropTargetEvent dte) {

    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try{
            Transferable tr = dtde.getTransferable();
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(dtde.getDropAction());
                DragJPanel dragJPanel = (DragJPanel) dtde.getDropTargetContext().getComponent();
                @SuppressWarnings("unchecked") java.util.List<File> fileList = (java.util.List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
                fileList.forEach(p -> dragJPanel.add(new ImageJPanel(p)));
                dtde.dropComplete(true);
            } else {
                dtde.rejectDrop();
            }
        } catch(Exception err) {
            err.printStackTrace();
        }
    }
}
