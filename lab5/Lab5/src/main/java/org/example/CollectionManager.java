package org.example;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class CollectionManager {
    private LinkedList<Route> routes;
    private final String initializationDate; // Дата инициализации коллекции
    private long nextIndex = 1;
    private User currentUser;
    private AuthenticationService authService;
    private RouteDAO routeDAO;

    public CollectionManager(AuthenticationService authService) {
        this.initializationDate = DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()); // Установка времени инициализации коллекции
        this.routes = new LinkedList<>();
        this.authService = authService;
        this.routeDAO = new RouteDAO();
        loadCollectionFromDatabase();
        updateIndices();
    }

    private void loadCollectionFromDatabase() {
        try {
            List<Route> routesFromDB = routeDAO.getAllRoutes();
            this.routes.addAll(routesFromDB);
            System.out.println("Коллекция успешно загружена из базы данных. Количество элементов: " + routes.size());
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке коллекции из базы данных: " + e.getMessage());
            this.routes = new LinkedList<>(); // Инициализация пустой коллекции на случай ошибки
        }
        updateIndices();
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public String getInitializationDate() {
        return initializationDate;
    }

    public void addRoute(Route newRoute) {
        if (currentUser != null) {
            newRoute.setUserId(currentUser.getId());
            if (routeDAO.addRoute(newRoute)) {
                this.routes.add(newRoute);
                updateIndices();
                System.out.println("Маршрут добавлен. Текущее количество маршрутов: " + routes.size());
            } else {
                System.out.println("Не удалось добавить маршрут в базу данных.");
            }
        } else {
            System.out.println("Пользователь не авторизован. Добавление маршрута невозможно.");
        }
    }

    public boolean removeRouteById(long id) {
        if (currentUser != null) {
            Route route = routes.stream().filter(r -> r.getId() == id && r.getUserId() == currentUser.getId()).findFirst().orElse(null);
            if (route != null && routeDAO.deleteRoute(id)) {
                routes.remove(route);
                updateIndices();
                System.out.println("Маршрут с ID " + id + " удален.");
                return true;
            } else {
                System.out.println("Маршрут с ID " + id + " не найден или принадлежит другому пользователю.");
                return false;
            }
        } else {
            System.out.println("Пользователь не авторизован. Удаление маршрута невозможно.");
            return false;
        }
    }

    public void clearRoutes() {
        if (currentUser != null) {
            List<Route> userRoutes = new LinkedList<>(routes);
            for (Route route : userRoutes) {
                if (route.getUserId() == currentUser.getId()) {
                    if (routeDAO.deleteRoute(route.getId())) {
                        routes.remove(route);
                    }
                }
            }
            updateIndices();
            System.out.println("Все маршруты текущего пользователя удалены.");
        } else {
            System.out.println("Пользователь не авторизован. Очистка маршрутов невозможна.");
        }
    }

    public void removeAt(int index) {
        if (currentUser != null) {
            if (index >= 0 && index < routes.size()) {
                Route route = routes.get(index);
                if (route.getUserId() == currentUser.getId()) {
                    if (routeDAO.deleteRoute(route.getId())) {
                        routes.remove(index);
                        updateIndices();
                        System.out.println("Маршрут на позиции " + index + " удален.");
                    } else {
                        System.out.println("Не удалось удалить маршрут из базы данных.");
                    }
                } else {
                    System.out.println("Маршрут принадлежит другому пользователю. Удаление невозможно.");
                }
            } else {
                System.out.println("Индекс вне диапазона: " + index);
            }
        } else {
            System.out.println("Пользователь не авторизован. Удаление маршрута невозможно.");
        }
    }

    public void removeLast() {
        if (currentUser != null) {
            if (!routes.isEmpty()) {
                Route route = routes.getLast();
                if (route.getUserId() == currentUser.getId()) {
                    if (routeDAO.deleteRoute(route.getId())) {
                        routes.removeLast();
                        updateIndices();
                        System.out.println("Последний маршрут удален.");
                    } else {
                        System.out.println("Не удалось удалить маршрут из базы данных.");
                    }
                } else {
                    System.out.println("Последний маршрут принадлежит другому пользователю. Удаление невозможно.");
                }
            }
        } else {
            System.out.println("Пользователь не авторизован. Удаление маршрута невозможно.");
        }
    }

    public void reorder() {
        routes.sort((r1, r2) -> Long.compare(r2.getId(), r1.getId()));
        updateIndices();
        System.out.println("Коллекция переупорядочена.");
    }

    private void updateIndices() {
        IntStream.range(0, routes.size()).forEach(i -> routes.get(i).setIndex(i));
    }

    public boolean updateRoute(long id, Route updatedRoute) {
        if (currentUser != null) {
            Route route = routes.stream().filter(r -> r.getId() == id && r.getUserId() == currentUser.getId()).findFirst().orElse(null);
            if (route != null && routeDAO.updateRoute(id, updatedRoute)) {
                updatedRoute.setId(id);
                updatedRoute.setUserId(currentUser.getId());
                int index = routes.indexOf(route);
                routes.set(index, updatedRoute);
                updateIndices();
                System.out.println("Маршрут с ID " + id + " обновлен.");
                return true;
            } else {
                System.out.println("Маршрут с ID " + id + " не найден или принадлежит другому пользователю.");
                return false;
            }
        } else {
            System.out.println("Пользователь не авторизован. Обновление маршрута невозможно.");
            return false;
        }
    }

    public Route getRouteById(long id) {
        return routes.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    // Методы для управления сессией пользователя
    public boolean register(String username, String password) {
        return authService.register(username, password);
    }

    public boolean login(String username, String password) {
        User user = authService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Пользователь " + username + " успешно авторизован.");
            return true;
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
            return false;
        }
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("Пользователь " + currentUser.getUsername() + " вышел из системы.");
            currentUser = null;
        } else {
            System.out.println("Ни один пользователь не авторизован.");
        }
    }
}
