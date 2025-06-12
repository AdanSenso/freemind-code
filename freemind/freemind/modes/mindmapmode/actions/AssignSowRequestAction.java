package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;
import freemind.view.mindmapview.MapView;
import freemind.view.mindmapview.NodeView;

public class AssignSowRequestAction extends AbstractAction {

    private final MindMapController controller;

    public AssignSowRequestAction(MindMapController controller) {
        super(controller.getText("assign_sow_request"));
        System.out.println("adan assign_sow_request");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("adan assign_sow_request");

        MapView mv = controller.getView();
        NodeView node = mv.getSelected();

        File mapFile = controller.getMap().getFile();
        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");

        AIPanel panel = controller.getController().getAIPanel();
        if (panel != null) {
        panel.appendMessage("[System] Assign SOW request...", "system");
        }

        new Thread(() -> {
            try {
                String url = "/assign_sow_request";
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
