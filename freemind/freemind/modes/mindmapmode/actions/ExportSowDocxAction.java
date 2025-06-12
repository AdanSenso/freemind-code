package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;
import freemind.view.mindmapview.MapView;
import freemind.view.mindmapview.NodeView;

public class ExportSowDocxAction extends AbstractAction {

    private final MindMapController controller;

    public ExportSowDocxAction(MindMapController controller) {
        super(controller.getText("export_sow_docx"));
        System.out.println("adan export_sow_docx");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("adan export_sow_docx");

        MapView mv = controller.getView();
        NodeView node = mv.getSelected();

        File mapFile = controller.getMap().getFile();
        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");

        AIPanel panel = controller.getController().getAIPanel();
        if (panel != null) {
        panel.appendMessage("[System] Exporting SOW to DOCX...", "system");
        }

        new Thread(() -> {
            try {
                String url = "/export_sow_docx";
                String response = SendRequest.sendPostWithFile(url, filePath);

                SwingUtilities.invokeLater(() -> {
                	if (panel != null) {
                    panel.appendMessage("[System] Response: " + response, "system");
                	}
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                	if (panel != null) {
                    panel.appendMessage("[System] Error: " + ex.getMessage(), "system");
                	}
                });
            }
        }).start();
    }
}
