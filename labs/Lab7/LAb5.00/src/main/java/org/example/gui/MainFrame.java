package org.example.gui;

import org.example.dao.RouteDao;
import org.example.models.Route;
import org.example.DatabaseManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable routeTable;
    private DefaultTableModel tableModel;
    private JPanel visualizationPanel;

    public MainFrame(List<Route> routes) {
        setTitle("Главное окно");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Настройка таблицы
        String[] columnNames = {"ID", "Name", "Coordinates", "From", "To", "Distance", "User ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        routeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(routeTable);

        // Заполнение таблицы данными
        for (Route route : routes) {
            Object[] rowData = {
                    route.getId(),
                    route.getName(),
                    route.getCoordinates().toString(),
                    route.getFrom().toString(),
                    route.getTo().toString(),
                    route.getDistance(),
                    route.getUserId()
            };
            tableModel.addRow(rowData);
        }

        // Панель для визуализации
        visualizationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Визуализация объектов коллекции
                for (Route route : routes) {
                    // Пример: нарисовать круг для каждого маршрута
                    g.setColor(Color.BLUE);
                    g.fillOval((int) route.getCoordinates().getX(), (int) route.getCoordinates().getY().longValue(), 10, 10);
                }
            }
        };
        visualizationPanel.setBackground(Color.WHITE);

        // Размещение компонентов в окне
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScrollPane, visualizationPanel);
        splitPane.setDividerLocation(300);

        add(splitPane);
    }

    private static List<Route> loadRoutesFromDatabase() {
        try (Connection connection = DatabaseManager.getConnection()) {
            RouteDao routeDao = new RouteDao(connection);
            return routeDao.getAllRoutes();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Возвращаем пустой список в случае ошибки
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Route> routes = loadRoutesFromDatabase();
            MainFrame frame = new MainFrame(routes);
            frame.setVisible(true);
        });
    }
}
