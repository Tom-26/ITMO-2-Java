package org.example.GUI;

import org.example.Managers.CollectionManager;
import org.example.models.Route;
import org.example.models.Coordinates;
import org.example.models.Location;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AddFrame extends JFrame {
    private CollectionManager collectionManager;
    private MainFrame mainFrame;
    private int userId;

    public AddFrame(MainFrame mainFrame, CollectionManager collectionManager, int userId) {
        this.mainFrame = mainFrame;
        this.collectionManager = collectionManager;
        this.userId = userId;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Add Route");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 2));

        JTextField nameField = new JTextField();
        JTextField coordinatesXField = new JTextField();
        JTextField coordinatesYField = new JTextField();
        JTextField fromXField = new JTextField();
        JTextField fromYField = new JTextField();
        JTextField fromNameField = new JTextField();
        JTextField toXField = new JTextField();
        JTextField toYField = new JTextField();
        JTextField toNameField = new JTextField();
        JTextField distanceField = new JTextField();
        JButton addButton = new JButton("Add");

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                float coordinatesX = Float.parseFloat(coordinatesXField.getText());
                long coordinatesY = Long.parseLong(coordinatesYField.getText());
                Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);

                float fromX = Float.parseFloat(fromXField.getText());
                float fromY = Float.parseFloat(fromYField.getText());
                String fromName = fromNameField.getText();
                Location from = new Location(fromX, fromY, fromName);

                float toX = Float.parseFloat(toXField.getText());
                float toY = Float.parseFloat(toYField.getText());
                String toName = toNameField.getText();
                Location to = new Location(toX, toY, toName);

                long distance = Long.parseLong(distanceField.getText());
                String creationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now());

                Route newRoute = new Route(name, coordinates, creationDate, from, to, distance);
                newRoute.setUserId(userId); // Устанавливаем user_id текущего пользователя
                collectionManager.addRoute(newRoute);
                mainFrame.updateTableData();
                JOptionPane.showMessageDialog(this, "Route added successfully.");
                this.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Coordinates X:"));
        panel.add(coordinatesXField);
        panel.add(new JLabel("Coordinates Y:"));
        panel.add(coordinatesYField);
        panel.add(new JLabel("From X:"));
        panel.add(fromXField);
        panel.add(new JLabel("From Y:"));
        panel.add(fromYField);
        panel.add(new JLabel("From Name:"));
        panel.add(fromNameField);
        panel.add(new JLabel("To X:"));
        panel.add(toXField);
        panel.add(new JLabel("To Y:"));
        panel.add(toYField);
        panel.add(new JLabel("To Name:"));
        panel.add(toNameField);
        panel.add(new JLabel("Distance:"));
        panel.add(distanceField);
        panel.add(addButton);

        add(panel);
    }
}
