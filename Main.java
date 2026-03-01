import java.util.*;

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void pay(double amount) {
        System.out.println("Оплата " + amount + " тенге банковской картой: " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    public void pay(double amount) {
        System.out.println("Оплата " + amount + " тенге через PayPal: " + email);
    }
}

class CryptoPayment implements PaymentStrategy {
    private String wallet;

    public CryptoPayment(String wallet) {
        this.wallet = wallet;
    }

    public void pay(double amount) {
        System.out.println("Оплата " + amount + " тенге криптовалютой: " + wallet);
    }
}

class PaymentContext {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(double amount) {
        if (strategy != null) {
            strategy.pay(amount);
        } else {
            System.out.println("Способ оплаты не выбран");
        }
    }
}

interface Observer {
    void update(double rate);
}

interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

class CurrencyExchange implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private double rate;

    public void setRate(double rate) {
        this.rate = rate;
        notifyObservers();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(rate);
        }
    }
}

class Bank implements Observer {
    public void update(double rate) {
        System.out.println("Банк получил новый курс: " + rate);
    }
}

class Investor implements Observer {
    public void update(double rate) {
        System.out.println("Инвестор анализирует курс: " + rate);
    }
}

class MobileApp implements Observer {
    public void update(double rate) {
        System.out.println("Мобильное приложение обновило курс: " + rate);
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        PaymentContext context = new PaymentContext();

        System.out.println("Введите сумму:");
        double amount = scanner.nextDouble();

        System.out.println("1 - Карта");
        System.out.println("2 - PayPal");
        System.out.println("3 - Крипто");

        int choice = scanner.nextInt();

        if (choice == 1) {
            context.setStrategy(new CreditCardPayment("1111-2222-3333"));
        } else if (choice == 2) {
            context.setStrategy(new PayPalPayment("user@mail.com"));
        } else if (choice == 3) {
            context.setStrategy(new CryptoPayment("0xABC123"));
        }

        context.execute(amount);

        CurrencyExchange exchange = new CurrencyExchange();

        Observer bank = new Bank();
        Observer investor = new Investor();
        Observer app = new MobileApp();

        exchange.addObserver(bank);
        exchange.addObserver(investor);
        exchange.addObserver(app);

        System.out.println("Введите новый курс валют:");
        double newRate = scanner.nextDouble();

        exchange.setRate(newRate);
    }
}