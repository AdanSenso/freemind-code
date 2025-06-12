package freemind.modes.mindmapmode.actions;

import java.awt.event.ActionEvent;
import javax.swing.*;

import freemind.modes.mindmapmode.MindMapController;
import freemind.view.mindmapview.AIPanel;

public class ReopenAIPanelAction extends AbstractAction {

    private final MindMapController controller;
    private JFrame aiFrame;
    private AIPanel aiPanel;

    public ReopenAIPanelAction(MindMapController controller) {
        super(controller.getText("reopen_ai_panel"));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (aiFrame == null || !aiFrame.isDisplayable()) {
            aiPanel = new AIPanel(controller.getController());
            controller.getController().setAIPanel(aiPanel);

            aiFrame = new JFrame("AI Assistant");
            aiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            aiFrame.setContentPane(aiPanel);
            aiFrame.pack();
            aiFrame.setLocationRelativeTo(null);
        }

        aiFrame.setVisible(true);
        aiFrame.toFront();
    }
}
