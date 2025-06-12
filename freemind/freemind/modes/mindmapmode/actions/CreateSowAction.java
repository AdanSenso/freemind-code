package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import java.awt.Desktop;
import java.io.IOException;

import freemind.modes.NodeAdapter;
import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;
import freemind.view.mindmapview.MapView;
import freemind.view.mindmapview.NodeView;

public class CreateSowAction extends AbstractAction {
    private final MindMapController controller;

    public CreateSowAction(MindMapController controller) {
        super(controller.getText("create_sow_mm"));
        System.out.println("23. adan create_sow");
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("24. adan create_sow");

        MapView mv = controller.getView();
        NodeView node = mv.getSelected();

        NodeAdapter mn = (NodeAdapter) node.getModel();
        String nodeId = mn.getID();
        System.out.println("ID=" + nodeId);

        File mapFile = controller.getMap().getFile();
        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");
        System.out.println("file path=" + filePath);

        AIPanel panel = controller.getController().getAIPanel();
        if (panel != null) {
            panel.appendMessage("[System] Creating SOW mind map...", "system");
        }

        new Thread(() -> {
            try {
                String url = "/create_sow_mindmap";
                String response = SendRequest.sendPostWithFile(url, filePath);
                handleSowResponse(response, panel);

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

    /**
     * Handles the response from the SOW creation endpoint.
     * Assumes the response is currently just the raw path string.
     * TODO: Update this method when the backend returns proper JSON.
     */
    private void handleSowResponse(String response, AIPanel panel) {
        SwingUtilities.invokeLater(() -> {
            try {
                // === TEMP: Clean up plain response path ===
                String cleanedPath = response.replaceAll("^\"|\"$", "").replace("\\\\", "\\");

                if (panel != null) {
                    panel.appendMessage("[System] SOW created at: " + cleanedPath, "system");
                }

                Desktop.getDesktop().open(new File(cleanedPath));

            } catch (IOException ex) {
                ex.printStackTrace();
                if (panel != null) {
                    panel.appendMessage("[System] Failed to open file: " + ex.getMessage(), "system");
                }
            }
        });
    }
}
