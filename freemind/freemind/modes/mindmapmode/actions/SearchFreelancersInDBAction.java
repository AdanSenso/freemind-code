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

public class SearchFreelancersInDBAction extends AbstractAction {
    private final MindMapController controller;

    public SearchFreelancersInDBAction(MindMapController controller) {
        super(controller.getText("search_freelancer_db"));
        System.out.println("19. adan search_fl");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("18. adan search_fl");

        MapView mv = controller.getView();
        NodeView node = mv.getSelected();

        NodeAdapter mn = (NodeAdapter) node.getModel();
        String nodeId = mn.getID();
        System.out.println("ID=" + nodeId);

        File mapFile = controller.getMap().getFile();
        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");

        AIPanel panel = controller.getController().getAIPanel();
        if (panel != null) {
        panel.appendMessage("[System] Searching for suitable freelancers for node \"" + nodeId + "\"...", "system");
        }

        new Thread(() -> {
            try {
                String url = "/suggest_freelancers_to_mindmap";
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
