package org.example.GUI;

import org.example.Managers.CollectionManager;
import org.example.Commands.Realize.CountGreaterThanDistanceCommand;

import javax.swing.*;
import java.awt.*;

public class CountGreaterThanDistanceFrame extends JFrame {
    private CollectionManager collectionManager;
    private MainFrame mainFrame;

    public CountGreaterThanDistanceFrame(MainFrame mainFrame, CollectionManager collectionManager) {
        this.mainFrame = mainFrame;
        this.collectionManager = collectionManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Count Greater Than Distance");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel distanceLabel = new JLabel("Enter distance:");
        JTextField distanceField = new JTextField(10);
        JButton countButton = new JButton("Count");

        countButton.addActionListener(e -> {
            String distanceText = distanceField.getText();
            try {
                long distance = Long.parseLong(distanceText);
                CountGreaterThanDistanceCommand command = new CountGreaterThanDistanceCommand(collectionManager);
                command.execute(new String[]{distanceText});
                JOptionPane.showMessageDialog(this, "Routes with distance greater than " + distance + ": " + command.getResult());
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid distance value.");
            }
        });

        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(distanceLabel, constraints);

        constraints.gridx = 1;
        panel.add(distanceField, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(countButton, constraints);

        add(panel);
    }
}
