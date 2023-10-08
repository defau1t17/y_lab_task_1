package org.example.In;

import org.example.DAO.TransactionDAO;
import org.example.Entity.AccountManager;
import org.example.Entity.Operation;
import org.example.Entity.SessionClient;
import org.example.Entity.Transaction;
import org.example.Repository.OperationData;
import org.example.Service.BeginTransaction;
import org.example.Service.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputReader {

    private static final Scanner scanner = new Scanner(System.in);

    public static boolean beginDialog() {
        System.out.println("Для продолжения зарегистрируйтесь/авторизируйтесь");
        System.out.println("Регистрация (1)\nАвторизация (2)\nВыход (3)");
        int input_value = scanner.nextInt();
        switch (input_value) {
            case 1:
                while (true) {
                    System.out.println("input nick ");
                    String nick = scanner.next();
                    System.out.println("input username ");
                    String username = scanner.next();
                    System.out.println("input password  ");
                    String password = scanner.next();
                    if (AccountManager.createClient(nick, username, password)) {
                        break;
                    } else {
                        System.out.println("Клиент с таким username уже существует");
                        break;
                    }
                }
                return true;

            case 2:
                while (true) {
                    System.out.println("Введите username : ");
                    String username = scanner.next();

                    System.out.println("Введите password : ");
                    String password = scanner.next();

                    if (AccountManager.login(username, password)) {
                        break;
                    } else {
                        System.out.println("Не удалось найти пользователя с таким username или password. Попробуйте снова");
                        break;
                    }
                }
                return true;
            case 3:
                System.exit(1);
        }
        return false;
    }

    public static boolean chooseMove() {
        System.out.println("Выбирите операцию : \n(1) Баланс счета\n(2) Пополнить счет\n(3) Снять деньги со счета\n(4) Взять кредит\n(5) История операций\n(6) Выход");
        int input_value = scanner.nextInt();
        double quantity;
        switch (input_value) {
            case 1:
                System.out.println("Баланс счета :\n" + Request.getBalance() + "\n--------------------------------------");
                return true;
            case 2:
                System.out.println("Введите значние : ");
                quantity = scanner.nextDouble();
                if (new BeginTransaction().replenishment(Operation.REPLENISHMENT, quantity)) {
                    System.out.println("Оперция выполнена успешно!\n--------------------------------------");
                } else {
                    System.out.println("Произошла ошибка во время выполнения\n--------------------------------------");
                }
                break;
            case 3:
                System.out.println("Введите значние : ");
                quantity = scanner.nextDouble();
                if (new BeginTransaction().withdrawal(Operation.WITHDRAWAL, quantity)) {
                    System.out.println("Оперция выполнена успешно!\n--------------------------------------");
                } else {
                    System.out.println("Произошла ошибка во время выполнения! Недостаточно средств\n--------------------------------------");
                }
                break;

            case 4:
                System.out.println("Введите значние : ");
                quantity = scanner.nextDouble();
                if (new BeginTransaction().creditAdd(Operation.CREDIT, quantity)) {
                    System.out.println("Оперция выполнена успешно!\n--------------------------------------");
                } else {
                    System.out.println("Произошла ошибка во время выполнения\n--------------------------------------");
                }
                break;
            case 5:
                ArrayList<Transaction> transactions = new OperationData().displayAccountTransaction(SessionClient.session_client);
                if (transactions == null) {
                    System.out.println("Операций пока нет :)");
                } else {
                    System.out.println("Операции :");
                    for (Transaction transaction : transactions) {
                        System.out.println("Дата : '" + transaction.getLocalDate() + "' Значение : '" + transaction.getMagnitude() + "' Тип : '" + transaction.getOperation_type().toString() + "' \n----------------");
                    }
                }
                break;
            case 6:
                SessionClient.UpdateSessionClient(null);
                return true;

        }

        return false;
    }


}
