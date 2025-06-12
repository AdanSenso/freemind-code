package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import freemind.modes.NodeAdapter;
import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;
import freemind.view.mindmapview.MapView;
import freemind.view.mindmapview.NodeView;

//Yet to be correctly implemented!
public class SearchFreelancersInDBActionV2 extends AbstractAction {

    private final MindMapController controller;

    public SearchFreelancersInDBActionV2(MindMapController controller) {
        super(controller.getText("search_freelancer_db_v2"));
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
//        MapView mv = controller.getView();
//        NodeView node = mv.getSelected();
//        NodeAdapter mn = (NodeAdapter) node.getModel();
//        String nodeId = mn.getID();
//        File mapFile = controller.getMap().getFile();
//        String filePath = mapFile.getAbsolutePath().replace("\\", "\\\\");
//
//        AIPanel panel = controller.getController().getAIPanel();
//   	if (panel != null) {
//        panel.appendMessage("[System] Searching for freelancers for node \"" + nodeId + "\"...", "system");}
//
//        Executors.newSingleThreadExecutor().submit(() -> {
//            try {
//                String url = "/find_freelancers_from_mindmap_v2_mock";
//                String requestJson = "{\"file_path\": \"" + filePath + "\"}";
//
//                String response = SendRequest.sendPostWithFile(url, requestJson);
//
//                //List<String[]> topMatches = parseTopMatches(response);
//                List<String> relevantNames = parseRelevantNames(response);
//
//                SwingUtilities.invokeLater(() -> showFreelancerPopup(topMatches, relevantNames, nodeId, filePath));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                SwingUtilities.invokeLater(() -> {
 //   	if (panel != null) {
//                    panel.appendMessage("[System] Error: " + ex.getMessage(), "system");
 //   }
//                });
//            }
//        });
    }
//
//    private List<String[]> parseTopMatches(String json) {
//        List<String[]> matches = new ArrayList<>();
//        Pattern pattern = Pattern.compile("\\{\\s*\"name\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"reason\"\\s*:\\s*\"(.*?)\"\\s*\\}");
//        Matcher matcher = pattern.matcher(json);
//        while (matcher.find()) {
//            matches.add(new String[]{matcher.group(1), matcher.group(2)});
//        }
//        return matches;
//    }
//
    private List<String> parseRelevantNames(String json) {
//        List<String> names = new ArrayList<>();
//        Pattern pattern = Pattern.compile("\"relevant_freelancer_names\"\\s*:\\s*\\[(.*?)\\]");
//        Matcher matcher = pattern.matcher(json);
//        if (matcher.find()) {
//            String[] rawNames = matcher.group(1).split(",");
//            for (String name : rawNames) {
//                name = name.trim().replaceAll("^\"|\"$", "");
//                if (!name.isEmpty()) {
//                    names.add(name);
//                }
//            }
//        }
    	return null;
        //return names;
    }
//
    private void showFreelancerPopup(List<String[]> topMatches, List<String> relevantNames, String nodeId, String filePath) {
//        StringBuilder topText = new StringBuilder();
//        for (int i = 0; i < topMatches.size(); i++) {
//            String[] entry = topMatches.get(i);
//            topText.append((i + 1)).append(". ").append(entry[0]).append(": ").append(entry[1]).append("\n");
//        }
//
//        JTextArea topArea = new JTextArea(topText.toString());
//        topArea.setEditable(false);
//        topArea.setWrapStyleWord(true);
//        topArea.setLineWrap(true);
//        topArea.setBackground(UIManager.getColor("Panel.background"));
//        topArea.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        DefaultListModel<String> listModel = new DefaultListModel<>();
//        for (String name : relevantNames) {
//            listModel.addElement(name);
//        }
//
//        JList<String> list = new JList<>(listModel);
//        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        JScrollPane scrollPane = new JScrollPane(list);
//        scrollPane.setPreferredSize(new java.awt.Dimension(250, 120));
//
//        Object[] components = {
//                "Top 3 Matches:", topArea,
//                "Select freelancer(s) to insert:", scrollPane
//        };
//
//        int result = JOptionPane.showConfirmDialog(null, components, "Freelancer Matches", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            List<String> selectedNames = list.getSelectedValuesList();
//            if (!selectedNames.isEmpty()) {
//                placeFreelancersInMindMap(filePath, nodeId, selectedNames);
//            }
//        }
    }
//
    private void placeFreelancersInMindMap(String filePath, String nodeId, List<String> freelancerNames) {
//        Executors.newSingleThreadExecutor().submit(() -> {
//            try {
//                StringBuilder json = new StringBuilder();
//                json.append("{");
//                json.append("\"file_path\":\"").append(filePath).append("\",");
//                json.append("\"freelancer_names\":[");
//                for (int i = 0; i < freelancerNames.size(); i++) {
//                    if (i > 0) json.append(",");
//                    json.append("\"").append(freelancerNames.get(i).replace("\"", "\\\"")).append("\"");
//                }
//                json.append("]}");
//
//                SendRequest.sendPost("/insert_freelancer_nodes", json.toString());
//
//                SwingUtilities.invokeLater(() -> {
//                    controller.getController().getAIPanel().appendMessage(
//                            "[System] Inserted " + freelancerNames.size() + " freelancer(s) into the mind map.", "system");
//                });
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                SwingUtilities.invokeLater(() -> {
//                    controller.getController().getAIPanel().appendMessage(
//                            "[System] Error inserting freelancers: " + ex.getMessage(), "system");
//                });
//            }
//        });
    }
}
