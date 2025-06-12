package freemind.view.mindmapview;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import freemind.controller.Controller;
import freemind.view.MapModule;
import java.io.File;
import freemind.modes.mindmapmode.actions.SendRequest;



public class AIPanel extends JPanel {

    private final Controller controller;
    private final JTextPane messagePane;
    private final JTextField inputField;
    private final JButton sendButton;
    private final JButton applyPromptButton;
    private final JButton sendFeedbackButton;
    private final StyledDocument doc;

    public AIPanel(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        // === Message Display Pane ===
        messagePane = new JTextPane();
        messagePane.setEditable(false);
        doc = messagePane.getStyledDocument();
        JScrollPane scrollPane = new JScrollPane(messagePane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // === Input Area ===
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        applyPromptButton = new JButton("Apply to Map");
        sendFeedbackButton = new JButton("Send Feedback");
        


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        buttonPanel.add(applyPromptButton);
        buttonPanel.add(sendFeedbackButton);
        
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // === Send Actions ===
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        
        applyPromptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String prompt = inputField.getText().trim();
                if (!prompt.isEmpty()) {
                    appendMessage("[User] " + prompt, "user");
                    inputField.setText("");
                    applyCustomPromptToMap(prompt);
                }
            }
        });

        sendFeedbackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String feedback = inputField.getText().trim();
                if (!feedback.isEmpty()) {
                    appendMessage("[User Feedback] " + feedback, "user");

                    inputField.setText("");
                    sendFeedbackToServer(feedback);
                }
            }
        });
        
        
        // Disable the buttons
        sendButton.setEnabled(false);
        applyPromptButton.setEnabled(false);
        
        // Show initial map status
        refresh();
    }

    public void refresh() {
        try {
            MapModule activeMap = controller.getMapModuleManager().getMapModule();
            if (activeMap != null) {
                appendMessage("[System] Current Map: " + activeMap.toString(), "system");
            } else {
                appendMessage("[System] No map loaded.", "system");
            }
        } catch (Exception e) {
            appendMessage("[System] Error loading map.", "system");
        }
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            appendMessage("[User] " + text, "user");
            inputField.setText("");
        }
    }

    public void appendMessage(String msg, String type) {
        try {
            Style style = messagePane.addStyle(type, null);

            if ("user".equals(type)) {
                StyleConstants.setForeground(style, new Color(33, 150, 243)); // Blue
                StyleConstants.setBold(style, true);
            } else {
                StyleConstants.setForeground(style, Color.DARK_GRAY); // System message
                StyleConstants.setItalic(style, true);
            }

            doc.insertString(doc.getLength(), msg + "\n", style);
            messagePane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private void applyCustomPromptToMap(String prompt) {
        appendMessage("[System] Sending custom prompt to modify mind map...", "system");

        new Thread(() -> {
            try {
                File file = controller.getMapModuleManager().getMapModule().getModel().getFile();
                if (file == null || !file.exists()) {
                    SwingUtilities.invokeLater(() -> {
                        appendMessage("[System] Error: Map file not found.", "system");
                    });
                    return;
                }

                String filePath = file.getAbsolutePath().replace("\\", "\\\\");
                String url = "/apply_custom_prompt";

                String response = SendRequest.sendCustomPrompt(url, filePath, prompt);

                SwingUtilities.invokeLater(() -> {
                    appendMessage("[System] Response: " + response, "system");
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    appendMessage("[System] Error: " + ex.getMessage(), "system");
                });
            }
        }).start();
    }
    
    private void sendFeedbackToServer(String feedback) {
        new Thread(() -> {
            try {
                String username = System.getProperty("user.name");

                String url = "/submit_feedback";
                String json = String.format(
                    "{\"message\": \"%s\", \"user\": \"%s\"}",
                    feedback.replace("\"", "\\\""),
                    username.replace("\"", "\\\"")
                );

                String response = SendRequest.sendPost(url, json);

                SwingUtilities.invokeLater(() -> {
                    appendMessage("[System] Feedback submitted: " + response, "system");
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    appendMessage("[System] Error sending feedback: " + ex.getMessage(), "system");
                });
            }
        }).start();
    }



}
