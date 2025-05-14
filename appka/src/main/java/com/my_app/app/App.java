package com.my_app.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.my_app.entities.customer.Customer;
import com.my_app.entities.customer.transaction.Transaction;
import com.my_app.entities.employee.Employee;
import com.my_app.entities.employee.WorkPlace;
import com.my_app.entities.order.Order;
import com.my_app.entities.order.StatusOrder;
import com.my_app.entities.product.Product;
import com.my_app.entities.sale_point.SalePoint;
import com.my_app.entities.warehouse.Warehouse;
import com.my_app.entities.warehouse_cell.Cell;
import com.my_app.entities.warehouse_cell.Volume;
import com.my_app.service.customer_service.CustomerServiceR;
import com.my_app.service.employ_service.EmployeeServiceR;
import com.my_app.service.order_service.OrderServiceR;
import com.my_app.service.product_service.ProductServiceR;
import com.my_app.service.sale_pointer_service.SalesPointServiceR;
import com.my_app.service.warehouse_service.WarehouseServiceR;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerServiceR customerService = new CustomerServiceR();
    private static final OrderServiceR orderService = new OrderServiceR();
    private static final ProductServiceR productService = new ProductServiceR();
    private static final WarehouseServiceR warehouseService = new WarehouseServiceR();
    private static final SalesPointServiceR salesPointService = new SalesPointServiceR();
    private static final EmployeeServiceR employeeService = new EmployeeServiceR();

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = getIntInput("Выберите действие: ");
            processMainMenuChoice(choice);
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Управление товарами");
        System.out.println("2. Управление заказами");
        System.out.println("3. Управление клиентами");
        System.out.println("4. Управление складами");
        System.out.println("5. Управление пунктами продаж");
        System.out.println("6. Управление сотрудниками");
        System.out.println("0. Выход");
    }

    private static void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                showProductMenu();
                break;
            case 2:
                showOrderMenu();
                break;
            case 3:
                showCustomerMenu();
                break;
            case 4:
                showWarehouseMenu();
                break;
            case 5:
                showSalesPointMenu();
                break;
            case 6:
                showEmployeeMenu();
                break;
            case 0:
                System.out.println("Game over");
                System.exit(0);
            default:
                System.out.println("Неверный выбор");
        }
    }

    private static void showProductMenu() {
        while (true) {
            System.out.println("\n=== Управление товарами ===");
            System.out.println("1. Добавить товар");
            System.out.println("2. Просмотреть все товары");
            System.out.println("3. Найти товар по ID");
            System.out.println("4. Обновить товар");
            System.out.println("5. Удалить товар");
            System.out.println("6. Обновить количество товара");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    showAllProducts();
                    break;
                case 3:
                    findProductById();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    updateProductQuantity();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addProduct() {
        System.out.println("\n=== Добавление товара ===");
        String name = getStringInput("Введите название товара: ");
        int price = getIntInput("Введите цену товара: ");
        int quantity = getIntInput("Введите количество товара: ");
        String description = getStringInput("Введите описание товара: ");
        String category = getStringInput("Введите категорию товара: ");

        Product product = new Product(name, price, quantity, description, null, category);
        try {
            Product createdProduct = productService.createProduct(product);
            System.out.println("Товар успешно добавлен с ID: " + createdProduct.getId());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private static void showAllProducts() {
        System.out.println("\n=== Список всех товаров ===");
        try {
            productService.getAllProducts().forEach(product -> {
                System.out.println("ID: " + product.getId());
                System.out.println("Название: " + product.getDescription());
                System.out.println("Цена: " + product.getPrice());
                System.out.println("Количество: " + product.getQuantity());
                System.out.println("Категория: " + product.getCategory());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    private static void findProductById() {
        Integer id = getIntInput("Введите ID товара: ");
        try {
            Product product = productService.getProductById(id);
            if (product != null) {
                System.out.println("\n=== Информация о товаре ===");
                System.out.println("ID: " + product.getId());
                System.out.println("Название: " + product.getDescription());
                System.out.println("Цена: " + product.getPrice());
                System.out.println("Количество: " + product.getQuantity());
                System.out.println("Категория: " + product.getCategory());
            } else {
                System.out.println("Товар не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске товара: " + e.getMessage());
        }
    }

    private static void updateProduct() {
        Integer id = getIntInput("Введите ID товара для обновления: ");
        String name = getStringInput("Введите новое название товара: ");
        int price = getIntInput("Введите новую цену товара: ");
        int quantity = getIntInput("Введите новое количество товара: ");
        String description = getStringInput("Введите новое описание товара: ");
        String category = getStringInput("Введите новую категорию товара: ");

        Product product = new Product(name, price, quantity, description, null, category);
        try {
            productService.updateProduct(id, product);
            System.out.println("Товар успешно обновлен");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении товара: " + e.getMessage());
        }
    }

    private static void deleteProduct() {
        Integer id = getIntInput("Введите ID товара для удаления: ");
        try {
            productService.deleteProduct(id);
            System.out.println("Товар успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    private static void updateProductQuantity() {
        Integer id = getIntInput("Введите ID товара: ");
        int quantity = getIntInput("Введите новое количество: ");
        try {
            productService.updateProductQuantity(id, quantity);
            System.out.println("Количество товара успешно обновлено");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении количества: " + e.getMessage());
        }
    }

    private static void showOrderMenu() {
        while (true) {
            System.out.println("\n=== Управление заказами ===");
            System.out.println("1. Создать заказ");
            System.out.println("2. Просмотреть все заказы");
            System.out.println("3. Найти заказ по ID");
            System.out.println("4. Добавить товар в заказ");
            System.out.println("5. Удалить товар из заказа");
            System.out.println("6. Изменить статус заказа");
            System.out.println("7. Рассчитать стоимость заказа");
            System.out.println("8. Удалить заказ");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    showAllOrders();
                    break;
                case 3:
                    findOrderById();
                    break;
                case 4:
                    addProductToOrder();
                    break;
                case 5:
                    removeProductFromOrder();
                    break;
                case 6:
                    changeOrderStatus();
                    break;
                case 7:
                    calculateOrderTotal();
                    break;
                case 8:
                    deleteOrder();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void createOrder() {
        System.out.println("\n=== Создание заказа ===");
        Integer customerId = getIntInput("Введите ID клиента: ");
        List<Product> products = new ArrayList<>();
        
        Order order = new Order(customerId, StatusOrder.IN_WAREHOUSE, products);
        try {
            orderService.createOrder(order, customerId);
            System.out.println("Заказ успешно создан");
        } catch (Exception e) {
            System.out.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    private static void showAllOrders() {
        System.out.println("\n=== Список всех заказов ===");
        try {
            orderService.getAllOrders().forEach(order -> {
                System.out.println("ID заказа: " + order.getId());
                System.out.println("ID клиента: " + order.getCustomerId());
                System.out.println("Статус: " + order.getStatus());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка заказов: " + e.getMessage());
        }
    }

    private static void findOrderById() {
        Integer id = getIntInput("Введите ID заказа: ");
        try {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                System.out.println("\n=== Информация о заказе ===");
                System.out.println("ID заказа: " + order.getId());
                System.out.println("ID клиента: " + order.getCustomerId());
                System.out.println("Статус: " + order.getStatus());
            } else {
                System.out.println("Заказ не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске заказа: " + e.getMessage());
        }
    }

    private static void addProductToOrder() {
        Integer orderId = getIntInput("Введите ID заказа: ");
        Integer productId = getIntInput("Введите ID товара: ");
        int quantity = getIntInput("Введите количество: ");
        try {
            orderService.addProductToOrder(orderId, productId, quantity);
            System.out.println("Товар успешно добавлен в заказ");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара в заказ: " + e.getMessage());
        }
    }

    private static void removeProductFromOrder() {
        Integer orderId = getIntInput("Введите ID заказа: ");
        Integer productId = getIntInput("Введите ID товара: ");
        try {
            orderService.removeProductFromOrder(orderId, productId);
            System.out.println("Товар успешно удален из заказа");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении товара из заказа: " + e.getMessage());
        }
    }

    private static void changeOrderStatus() {
        Integer orderId = getIntInput("Введите ID заказа: ");
        System.out.println("Выберите новый статус:");
        System.out.println("1. IN_WAREHOUSE");
        System.out.println("2. IN_PATH");
        System.out.println("3. IN_SALE_POINT");
        System.out.println("4. IN_CUSTOMER");
        int statusChoice = getIntInput("Выберите статус: ");
        
        StatusOrder status;
        switch (statusChoice) {
            case 1: status = StatusOrder.IN_WAREHOUSE; break;
            case 2: status = StatusOrder.IN_PATH; break;
            case 3: status = StatusOrder.IN_SALE_POINT; break;
            case 4: status = StatusOrder.IN_CUSTOMER; break;
            default:
                System.out.println("Неверный выбор статуса");
                return;
        }

        try {
            orderService.changeOrderStatus(orderId, status);
            System.out.println("Статус заказа успешно изменен");
        } catch (Exception e) {
            System.out.println("Ошибка при изменении статуса заказа: " + e.getMessage());
        }
    }

    private static void calculateOrderTotal() {
        Integer orderId = getIntInput("Введите ID заказа: ");
        try {
            Integer total = orderService.calculateOrderTotalPrice(orderId);
            System.out.println("Общая стоимость заказа: " + total);
        } catch (Exception e) {
            System.out.println("Ошибка при расчете стоимости заказа: " + e.getMessage());
        }
    }

    private static void deleteOrder() {
        Integer id = getIntInput("Введите ID заказа для удаления: ");
        try {
            orderService.deleteOrder(id);
            System.out.println("Заказ успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении заказа: " + e.getMessage());
        }
    }

    private static void showCustomerMenu() {
        while (true) {
            System.out.println("\n=== Управление клиентами ===");
            System.out.println("1. Добавить клиента");
            System.out.println("2. Просмотреть всех клиентов");
            System.out.println("3. Найти клиента по ID");
            System.out.println("4. Обновить данные клиента");
            System.out.println("5. Удалить клиента");
            System.out.println("6. Просмотреть заказы клиента");
            System.out.println("7. Просмотреть баланс клиента");
            System.out.println("8. Пополнить баланс");
            System.out.println("9. Снять средства");
            System.out.println("10. Просмотреть транзакции клиента");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    showAllCustomers();
                    break;
                case 3:
                    findCustomerById();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    showCustomerOrders();
                    break;
                case 7:
                    showCustomerBalance();
                    break;
                case 8:
                    replenishCustomerBalance();
                    break;
                case 9:
                    withdrawCustomerBalance();
                    break;
                case 10:
                    showCustomerTransactions();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addCustomer() {
        System.out.println("\n=== Добавление клиента ===");
        String name = getStringInput("Введите имя клиента: ");
        int balance = getIntInput("Введите начальный баланс: ");
        String address = getStringInput("Введите адрес клиента: ");

        Customer customer = new Customer(name, balance, address);
        try {
            customerService.createCustomer(customer);
            System.out.println("Клиент успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении клиента: " + e.getMessage());
        }
    }

    private static void showAllCustomers() {
        System.out.println("\n=== Список всех клиентов ===");
        try {
            customerService.getAllCustomers().forEach(customer -> {
                System.out.println("ID: " + customer.getId());
                System.out.println("Имя: " + customer.getName());
                System.out.println("Баланс: " + customer.getBalance());
                System.out.println("Адрес: " + customer.getAdress());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка клиентов: " + e.getMessage());
        }
    }

    private static void findCustomerById() {
        Integer id = getIntInput("Введите ID клиента: ");
        try {
            Customer customer = customerService.getCustomerById(id);
            if (customer != null) {
                System.out.println("\n=== Информация о клиенте ===");
                System.out.println("ID: " + customer.getId());
                System.out.println("Имя: " + customer.getName());
                System.out.println("Баланс: " + customer.getBalance());
                System.out.println("Адрес: " + customer.getAdress());
            } else {
                System.out.println("Клиент не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске клиента: " + e.getMessage());
        }
    }

    private static void updateCustomer() {
        Integer id = getIntInput("Введите ID клиента для обновления: ");
        String name = getStringInput("Введите новое имя клиента: ");
        int balance = getIntInput("Введите новый баланс: ");
        String address = getStringInput("Введите новый адрес: ");

        Customer customer = new Customer(name, balance, address);
        try {
            customerService.updateCustomer(id, customer);
            System.out.println("Данные клиента успешно обновлены");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных клиента: " + e.getMessage());
        }
    }

    private static void deleteCustomer() {
        Integer id = getIntInput("Введите ID клиента для удаления: ");
        try {
            customerService.deleteCustomer(id);
            System.out.println("Клиент успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении клиента: " + e.getMessage());
        }
    }

    private static void showCustomerOrders() {
        Integer id = getIntInput("Введите ID клиента: ");
        try {
            List<Order> orders = customerService.getCustomerOrders(id);
            System.out.println("\n=== Заказы клиента ===");
            orders.forEach(order -> {
                System.out.println("ID заказа: " + order.getId());
                System.out.println("Статус: " + order.getStatus());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении заказов клиента: " + e.getMessage());
        }
    }

    private static void showCustomerBalance() {
        Integer id = getIntInput("Введите ID клиента: ");
        try {
            int balance = customerService.getCustomerBalance(id);
            System.out.println("Текущий баланс клиента: " + balance);
        } catch (Exception e) {
            System.out.println("Ошибка при получении баланса клиента: " + e.getMessage());
        }
    }

    private static void replenishCustomerBalance() {
        Integer id = getIntInput("Введите ID клиента: ");
        int amount = getIntInput("Введите сумму для пополнения: ");
        try {
            int newBalance = customerService.replenishBalance(id, amount);
            System.out.println("Баланс успешно пополнен. Новый баланс: " + newBalance);
        } catch (Exception e) {
            System.out.println("Ошибка при пополнении баланса: " + e.getMessage());
        }
    }

    private static void withdrawCustomerBalance() {
        Integer id = getIntInput("Введите ID клиента: ");
        int amount = getIntInput("Введите сумму для снятия: ");
        try {
            int newBalance = customerService.withdrawBalance(id, amount);
            System.out.println("Средства успешно сняты. Новый баланс: " + newBalance);
        } catch (Exception e) {
            System.out.println("Ошибка при снятии средств: " + e.getMessage());
        }
    }

    private static void showCustomerTransactions() {
        Integer id = getIntInput("Введите ID клиента: ");
        try {
            List<Transaction> transactions = customerService.getCustomerTransactions(id);
            System.out.println("\n=== Транзакции клиента ===");
            transactions.forEach(transaction -> {
                System.out.println("ID транзакции: " + transaction.getId());
                System.out.println("Сумма: " + transaction.getAmount());
                System.out.println("Описание: " + transaction.getDescription());
                System.out.println("Тип: " + transaction.getType());
                System.out.println("Дата: " + transaction.getDate());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении транзакций клиента: " + e.getMessage());
        }
    }

    private static void showWarehouseMenu() {
        while (true) {
            System.out.println("\n=== Управление складами ===");
            System.out.println("1. Добавить склад");
            System.out.println("2. Просмотреть все склады");
            System.out.println("3. Найти склад по ID");
            System.out.println("4. Обновить данные склада");
            System.out.println("5. Удалить склад");
            System.out.println("6. Добавить ячейку на склад");
            System.out.println("7. Удалить ячейку со склада");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    addWarehouse();
                    break;
                case 2:
                    showAllWarehouses();
                    break;
                case 3:
                    findWarehouseById();
                    break;
                case 4:
                    updateWarehouse();
                    break;
                case 5:
                    deleteWarehouse();
                    break;
                case 6:
                    addCellToWarehouse();
                    break;
                case 7:
                    removeCellFromWarehouse();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addWarehouse() {
        System.out.println("\n=== Добавление склада ===");
        String address = getStringInput("Введите адрес склада: ");
        List<Cell> cells = new ArrayList<>();

        Warehouse warehouse = new Warehouse(address, cells,true);
        try {
            warehouseService.createWarehouse(warehouse);
            System.out.println("Склад успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении склада: " + e.getMessage());
        }
    }

    private static void showAllWarehouses() {
        System.out.println("\n=== Список всех складов ===");
        try {
            warehouseService.getAllWarehouses().forEach(warehouse -> {
                System.out.println("ID: " + warehouse.getId());
                System.out.println("Адрес: " + warehouse.getAdress());
                System.out.println("Количество ячеек: " + warehouse.getCells().size());
                System.out.println("Активен: " + warehouse.isActive());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка складов: " + e.getMessage());
        }
    }

    private static void findWarehouseById() {
        Integer id = getIntInput("Введите ID склада: ");
        try {
            Warehouse warehouse = warehouseService.getWarehouseById(id);
            if (warehouse != null) {
                System.out.println("\n=== Информация о складе ===");
                System.out.println("ID: " + warehouse.getId());
                System.out.println("Адрес: " + warehouse.getAdress());
                System.out.println("Количество ячеек: " + warehouse.getCells().size());
            } else {
                System.out.println("Склад не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске склада: " + e.getMessage());
        }
    }

    private static void updateWarehouse() {
        Integer id = getIntInput("Введите ID склада для обновления: ");
        String address = getStringInput("Введите новый адрес склада: ");
        List<Cell> cells = new ArrayList<>();

        Warehouse warehouse = new Warehouse(address, cells, true);
        try {
            warehouseService.updateWarehouse(id, warehouse);
            System.out.println("Данные склада успешно обновлены");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных склада: " + e.getMessage());
        }
    }

    private static void deleteWarehouse() {
        Integer id = getIntInput("Введите ID склада для удаления: ");
        try {
            warehouseService.deleteWarehouse(id);
            System.out.println("Склад успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении склада: " + e.getMessage());
        }
    }

    private static void addCellToWarehouse() {
        Integer warehouseId = getIntInput("Введите ID склада: ");
        Integer cellId = getIntInput("Введите ID ячейки: ");
        System.out.println("Выберите размер ячейки:");
        System.out.println("1. Маленькая (2)");
        System.out.println("2. Большая (4)");
        int volumeChoice = getIntInput("Выберите размер: ");
        
        Volume volume;
        switch (volumeChoice) {
            case 1: volume = Volume.LITTLE; break;
            case 2: volume = Volume.BIG; break;
            default:
                System.out.println("Неверный выбор размера");
                return;
        }

        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        if (warehouse == null) {
            System.out.println("Склад не найден");
            return;
        }
        Cell cell = new Cell(cellId, volume, warehouseId, 0);
        try {
            warehouse.addCell(cell);
            warehouseService.updateWarehouse(warehouseId, warehouse);
            System.out.println("Ячейка успешно добавлена на склад");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении ячейки: " + e.getMessage());
        }
    }

    private static void removeCellFromWarehouse() {
        Integer warehouseId = getIntInput("Введите ID склада: ");
        Integer cellId = getIntInput("Введите ID ячейки для удаления: ");

        try {
            Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
            if (warehouse == null) {
                System.out.println("Склад не найден");
                return;
            }

            Cell cellToRemove = warehouse.getCells().stream()
                .filter(cell -> cell.getId() == cellId)
                .findFirst()
                .orElse(null);

            if (cellToRemove == null) {
                System.out.println("Ячейка не найдена");
                return;
            }

            warehouse.removeCell(cellToRemove);
            warehouseService.updateWarehouse(warehouseId, warehouse);
            System.out.println("Ячейка успешно удалена со склада");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении ячейки: " + e.getMessage());
        }
    }

    private static void showSalesPointMenu() {
        while (true) {
            System.out.println("\n=== Управление пунктами продаж ===");
            System.out.println("1. Добавить пункт продаж");
            System.out.println("2. Просмотреть все пункты продаж");
            System.out.println("3. Найти пункт продаж по ID");
            System.out.println("4. Обновить данные пункта продаж");
            System.out.println("5. Удалить пункт продаж");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    addSalesPoint();
                    break;
                case 2:
                    showAllSalesPoints();
                    break;
                case 3:
                    findSalesPointById();
                    break;
                case 4:
                    updateSalesPoint();
                    break;
                case 5:
                    deleteSalesPoint();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addSalesPoint() {
        System.out.println("\n=== Добавление пункта продаж ===");
        String address = getStringInput("Введите адрес пункта продаж: ");
        List<Employee> employees = new ArrayList<>();
        Map<Product, Integer> products = new HashMap<>();

        List<Product> productList = new ArrayList<>(products.keySet());
        SalePoint salePoint = new SalePoint(address, productList, false);
        try {
            salesPointService.createSalePoint(salePoint);
            System.out.println("Пункт продаж успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении пункта продаж: " + e.getMessage());
        }
    }

    private static void showAllSalesPoints() {
        System.out.println("\n=== Список всех пунктов продаж ===");
        try {
            salesPointService.getAllSalePoints().forEach(salePoint -> {
                System.out.println("ID: " + salePoint.getId());
                System.out.println("Адрес: " + salePoint.getAdress());
                System.out.println("Открыт: " + salePoint.isActive());
                System.out.println("Количество сотрудников: " + salePoint.getEmployees().size());
                System.out.println("Количество товаров: " + salePoint.getProducts().size());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка пунктов продаж: " + e.getMessage());
        }
    }

    private static void findSalesPointById() {
        Integer id = getIntInput("Введите ID пункта продаж: ");
        try {
            SalePoint salePoint = salesPointService.getSalePointById(id);
            if (salePoint != null) {
                System.out.println("\n=== Информация о пункте продаж ===");
                System.out.println("ID: " + salePoint.getId());
                System.out.println("Адрес: " + salePoint.getAdress());
            } else {
                System.out.println("Пункт продаж не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске пункта продаж: " + e.getMessage());
        }
    }

    private static void updateSalesPoint() {
        Integer id = getIntInput("Введите ID пункта продаж для обновления: ");
        String address = getStringInput("Введите новый адрес пункта продаж: ");
        Map<Product, Integer> products = new HashMap<>();

        List<Product> productList = new ArrayList<>(products.keySet());
        SalePoint salePoint = new SalePoint(address, productList, false);
        try {
            salesPointService.updateSalePoint(id, salePoint);
            System.out.println("Данные пункта продаж успешно обновлены");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных пункта продаж: " + e.getMessage());
        }
    }

    private static void deleteSalesPoint() {
        Integer id = getIntInput("Введите ID пункта продаж для удаления: ");
        try {
            salesPointService.deleteSalePoint(id);
            System.out.println("Пункт продаж успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пункта продаж: " + e.getMessage());
        }
    }

    private static void showEmployeeMenu() {
        while (true) {
            System.out.println("\n=== Управление сотрудниками ===");
            System.out.println("1. Добавить сотрудника");
            System.out.println("2. Просмотреть всех сотрудников");
            System.out.println("3. Найти сотрудника по ID");
            System.out.println("4. Обновить данные сотрудника");
            System.out.println("5. Удалить сотрудника");
            System.out.println("6. Изменить место работы сотрудника");
            System.out.println("0. Вернуться в главное меню");

            int choice = getIntInput("Выберите действие: ");
            if (choice == 0) break;

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    showAllEmployees();
                    break;
                case 3:
                    findEmployeeById();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    changeEmployeeWorkPlace();
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }

    private static void addEmployee() {
        System.out.println("\n=== Добавление сотрудника ===");
        String name = getStringInput("Введите имя сотрудника: ");
        System.out.println("Выберите место работы:");
        System.out.println("1. Склад");
        System.out.println("2. Пункт продаж");
        int workPlaceChoice = getIntInput("Выберите место работы: ");
        
        WorkPlace workPlace;
        switch (workPlaceChoice) {
            case 1: workPlace = WorkPlace.WAREHOUSE; break;
            case 2: workPlace = WorkPlace.SELL_PLACE; break;
            default:
                System.out.println("Неверный выбор места работы");
                return;
        }

        Employee employee = new Employee(name, "active", true, workPlace);
        try {
            employeeService.createEmployee(employee);
            System.out.println("Сотрудник успешно добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении сотрудника: " + e.getMessage());
        }
    }

    private static void showAllEmployees() {
        System.out.println("\n=== Список всех сотрудников ===");
        try {
            employeeService.getAllEmployees().forEach(employee -> {
                System.out.println("ID: " + employee.getId());
                System.out.println("Имя: " + employee.getName());
                System.out.println("Место работы: " + employee.getWorkPlace());
                System.out.println("------------------------");
            });
        } catch (Exception e) {
            System.out.println("Ошибка при получении списка сотрудников: " + e.getMessage());
        }
    }

    private static void findEmployeeById() {
        Integer id = getIntInput("Введите ID сотрудника: ");
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                System.out.println("\n=== Информация о сотруднике ===");
                System.out.println("ID: " + employee.getId());
                System.out.println("Имя: " + employee.getName());
                System.out.println("Место работы: " + employee.getWorkPlace());
            } else {
                System.out.println("Сотрудник не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при поиске сотрудника: " + e.getMessage());
        }
    }

    private static void updateEmployee() {
        Integer id = getIntInput("Введите ID сотрудника для обновления: ");
        String name = getStringInput("Введите новое имя сотрудника: ");
        System.out.println("Выберите новое место работы:");
        System.out.println("1. Склад");
        System.out.println("2. Пункт продаж");
        int workPlaceChoice = getIntInput("Выберите место работы: ");
        
        WorkPlace workPlace;
        switch (workPlaceChoice) {
            case 1: workPlace = WorkPlace.WAREHOUSE; break;
            case 2: workPlace = WorkPlace.SELL_PLACE; break;
            default:
                System.out.println("Неверный выбор места работы");
                return;
        }

        Employee employee = new Employee(name, "active", true, workPlace);
        try {
            employeeService.updateEmployee(id, employee);
            System.out.println("Данные сотрудника успешно обновлены");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении данных сотрудника: " + e.getMessage());
        }
    }

    private static void deleteEmployee() {
        Integer id = getIntInput("Введите ID сотрудника для удаления: ");
        try {
            employeeService.deleteEmployee(id);
            System.out.println("Сотрудник успешно удален");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении сотрудника: " + e.getMessage());
        }
    }

    private static void changeEmployeeWorkPlace() {
        Integer id = getIntInput("Введите ID сотрудника: ");
        System.out.println("Выберите новое место работы:");
        System.out.println("1. Склад");
        System.out.println("2. Пункт продаж");
        int workPlaceChoice = getIntInput("Выберите место работы: ");
        
        WorkPlace workPlace;
        switch (workPlaceChoice) {
            case 1: workPlace = WorkPlace.WAREHOUSE; break;
            case 2: workPlace = WorkPlace.SELL_PLACE; break;
            default:
                System.out.println("Неверный выбор места работы");
                return;
        }

        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                employee.setWorkPlace(workPlace);
                employeeService.updateEmployee(id, employee);
                System.out.println("Место работы сотрудника успешно изменено");
            } else {
                System.out.println("Сотрудник не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при изменении места работы: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}