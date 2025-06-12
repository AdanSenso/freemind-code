package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import freemind.modes.NodeAdapter;
import freemind.view.mindmapview.NodeView;
import freemind.view.mindmapview.MapView;
import java.lang.Exception;
import javax.swing.SwingUtilities;
import freemind.view.mindmapview.AIPanel;


import javax.swing.AbstractAction;

import freemind.modes.mindmapmode.MindMapController;

public class ImproveNodeAction extends AbstractAction{

	private final MindMapController controller;

	public ImproveNodeAction(MindMapController controller) {
		
		super(controller.getText("improve_node"));
		System.out.println(" adan improve_node" );
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
	    System.out.println("adan improve_node");

	    MapView mv = controller.getView();
	    NodeView node = mv.getSelected();
	    NodeAdapter mn = (NodeAdapter) node.getModel();
	    String nodeId = mn.getID();

	    File mapFile = controller.getMap().getFile();
	    String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");

	    // Step 1: Show "loading" message
	    AIPanel panel = controller.getController().getAIPanel();
	    if (panel != null) {
	    panel.appendMessage("[System] Improving node \"" + nodeId + "\"...", "system");
	    }

	    // Step 2: Run HTTP request in background
	    new Thread(() -> {
	        try {
	            String url = "/improve_node";
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
