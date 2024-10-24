package org.example.GUI;

import org.example.Managers.CollectionManager;
import org.example.Commands.Realize.HelpCommand;
import org.example.Commands.Realize.HelpOut;
import org.example.Commands.Realize.InfoCommand;
import org.example.Managers.LocalizationManager;
import org.example.models.Route;
import org.example.Commands.Realize.MaxByIdCommand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Locale;

public class MainFrame extends JFrame {
    private CollectionManager collectionManager;
    private JTable routeTable;
    private DefaultTableModel tableModel;
    private Connection connection;
    private int userId;

    public MainFrame(Connection connection, int userId) throws SQLException {
        this.connection = connection;
        this.userId = userId;
        collectionManager = new CollectionManager(connection);
        initializeUI();
    }

    private void initializeUI() {
        setTitle(LocalizationManager.getString("route_management"));
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[]{
                LocalizationManager.getString("id"),
                LocalizationManager.getString("name"),
                LocalizationManager.getString("coordinates"),
                LocalizationManager.getString("creation_date"),
                LocalizationManager.getString("from"),
                LocalizationManager.getString("to"),
                LocalizationManager.getString("distance"),
                LocalizationManager.getString("user_id")
        }, 0);
        routeTable = new JTable(tableModel);
        updateTableData();

        JScrollPane scrollPane = new JScrollPane(routeTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(200, 600));

        JButton addButton = new JButton(LocalizationManager.getString("add_route"));
        JButton saveButton = new JButton(LocalizationManager.getString("save_changes"));
        JButton logoutButton = new JButton(LocalizationManager.getString("logout"));
        JButton infoButton = new JButton(LocalizationManager.getString("info"));
        JButton helpButton = new JButton(LocalizationManager.getString("help"));
        JButton reorderButton = new JButton(LocalizationManager.getString("reorder"));
        JButton countButton = new JButton(LocalizationManager.getString("count_greater_than_distance"));
        JButton printButton = new JButton(LocalizationManager.getString("print_field_ascending_distance"));
        JButton clearButton = new JButton(LocalizationManager.getString("clear"));
        JButton maxByIdButton = new JButton(LocalizationManager.getString("max_by_id"));

        addButton.addActionListener(this::showAddRouteFrame);
        saveButton.addActionListener(this::saveChanges);
        logoutButton.addActionListener(e -> System.exit(0));
        infoButton.addActionListener(this::showInfo);
        helpButton.addActionListener(this::showHelp);
        reorderButton.addActionListener(this::reorderRoutes);
        countButton.addActionListener(this::showCountGreaterThanDistanceFrame);
        printButton.addActionListener(this::printFieldAscendingDistance);
        clearButton.addActionListener(this::clearRoutes);
        maxByIdButton.addActionListener(this::showMaxById);

        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(reorderButton);
        buttonPanel.add(countButton);
        buttonPanel.add(printButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(maxByIdButton);

        add(buttonPanel, BorderLayout.WEST);

        routeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = routeTable.rowAtPoint(evt.getPoint());
                int col = routeTable.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    showRouteOptions(row);
                }
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu languageMenu = new JMenu("Language");
        JMenuItem englishItem = new JMenuItem("English");
        JMenuItem russianItem = new JMenuItem("Русский");
        JMenuItem portugueseItem = new JMenuItem("Português");
        JMenuItem catalanItem = new JMenuItem("Català");
        JMenuItem spanishItem = new JMenuItem("Español (Panamá)");

        englishItem.addActionListener(e -> switchLanguage(new Locale("en", "US")));
        russianItem.addActionListener(e -> switchLanguage(new Locale("ru", "RU")));
        portugueseItem.addActionListener(e -> switchLanguage(new Locale("pt", "PT")));
        catalanItem.addActionListener(e -> switchLanguage(new Locale("ca", "ES")));
        spanishItem.addActionListener(e -> switchLanguage(new Locale("es", "PA")));

        languageMenu.add(englishItem);
        languageMenu.add(russianItem);
        languageMenu.add(portugueseItem);
        languageMenu.add(catalanItem);
        languageMenu.add(spanishItem);
        menuBar.add(languageMenu);

        setJMenuBar(menuBar);
    }

    private void switchLanguage(Locale locale) {
        LocalizationManager.setLocale(locale);
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame(connection, userId).setVisible(true);
                this.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void updateTableData() {
        tableModel.setRowCount(0);
        for (Route route : collectionManager.getRoutes()) {
            tableModel.addRow(new Object[]{
                    route.getId(),
                    route.getName(),
                    route.getCoordinates(),
                    route.getCreationDate(),
                    route.getFrom(),
                    route.getTo(),
                    route.getDistance(),
                    route.getUserId()
            });
        }
    }

    private void showAddRouteFrame(ActionEvent e) {
        new AddFrame(this, collectionManager, userId).setVisible(true);
    }

    private void saveChanges(ActionEvent e) {
        try {
            collectionManager.saveCollectionToDatabase();
            updateTableData();
            JOptionPane.showMessageDialog(this, LocalizationManager.getString("changes_saved"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, LocalizationManager.getString("saving_error") + ": " + ex.getMessage());
        }
    }

    private void showInfo(ActionEvent e) {
        InfoCommand infoCommand = new InfoCommand(collectionManager);
        infoCommand.execute(new String[]{});
        JOptionPane.showMessageDialog(this, LocalizationManager.getString("info") + ":\n" +
                LocalizationManager.getString("type") + ": " + collectionManager.getRoutes().getClass().getName() + "\n" +
                LocalizationManager.getString("initialization_date") + ": " + collectionManager.getInitializationDate() + "\n" +
                LocalizationManager.getString("number_of_elements") + ": " + collectionManager.getRoutes().size());
    }

    private void showHelp(ActionEvent e) {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute(new String[]{});
        StringBuilder helpText = new StringBuilder();
        for (HelpOut info : HelpOut.values()) {
            helpText.append(info.getCommandName()).append(": ").append(info.getDescription()).append("\n");
        }
        JOptionPane.showMessageDialog(this, helpText.toString());
    }

    private void reorderRoutes(ActionEvent e) {
        collectionManager.reorder();
        updateTableData();
        JOptionPane.showMessageDialog(this, LocalizationManager.getString("routes_reordered"));
    }

    private void showCountGreaterThanDistanceFrame(ActionEvent e) {
        new CountGreaterThanDistanceFrame(this, collectionManager).setVisible(true);
    }

    private void printFieldAscendingDistance(ActionEvent e) {
        String result = collectionManager.printFieldAscendingDistance();
        JOptionPane.showMessageDialog(this, LocalizationManager.getString("routes_in_ascending_distance") + ":\n" + result);
    }

    private void clearRoutes(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this, LocalizationManager.getString("confirm_clear"), LocalizationManager.getString("confirm_clear_title"), JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            collectionManager.clearRoutes(userId);
            updateTableData();
            JOptionPane.showMessageDialog(this, LocalizationManager.getString("routes_cleared"));
        }
    }

    private void showMaxById(ActionEvent e) {
        MaxByIdCommand maxByIdCommand = new MaxByIdCommand(collectionManager);
        maxByIdCommand.execute(new String[]{});
        Route maxByIdRoute = collectionManager.getRoutes().stream()
                .max(Comparator.comparing(Route::getId))
                .orElse(null);

        if (maxByIdRoute != null) {
            JOptionPane.showMessageDialog(this, LocalizationManager.getString("route_with_max_id") + ":\n" + maxByIdRoute.toString());
        } else {
            JOptionPane.showMessageDialog(this, LocalizationManager.getString("no_routes_found"));
        }
    }

    private void showRouteOptions(int row) {
        Object[] options = {LocalizationManager.getString("delete"), LocalizationManager.getString("update")};
        int choice = JOptionPane.showOptionDialog(this, LocalizationManager.getString("choose_action"), LocalizationManager.getString("route_options"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            collectionManager.removeRouteById((Long) tableModel.getValueAt(row, 0), userId);
            updateTableData();
        } else if (choice == JOptionPane.NO_OPTION) {
            // Implement update functionality if needed
        }
    }
}
