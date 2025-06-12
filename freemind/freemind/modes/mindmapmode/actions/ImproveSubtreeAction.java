package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import freemind.modes.NodeAdapter;
import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;
import freemind.view.mindmapview.MapView;
import freemind.view.mindmapview.NodeView;

public class ImproveSubtreeAction extends AbstractAction {

    private final MindMapController controller;

    public ImproveSubtreeAction(MindMapController controller) {
        super(controller.getText("improve_subtree"));
        System.out.println("21. adan improve_subtree");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("22. adan improve_subtree");

        MapView mv = controller.getView();
        NodeView node = mv.getSelected();

        NodeAdapter mn = (NodeAdapter) node.getModel();
        String nodeId = mn.getID();
        System.out.println("ID=" + nodeId);

        File mapFile = controller.getMap().getFile();
        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");

        // Step 1: Show "loading" message
        AIPanel panel = controller.getController().getAIPanel();
        if (panel != null) {
        panel.appendMessage("[System] Improving subtree rooted at \"" + nodeId + "\"...", "system");
        }

        // Step 2: Run HTTP request in background
        new Thread(() -> {
            try {
                String url = "/improve_subtree";
                String response = SendRequest.sendPostWithFileAndNode(url, filePath, nodeId);

                // Step 3: Update panel with response
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
