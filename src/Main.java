import java.security.PrivateKey;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Car{
    private String carId;
    private String carModel;
    private String carBrand;
    private double basePrice;
    private boolean isAvailable;

    public Car(String carId, String carModel, String carBrand, double basePrice){
        this.carId = carId;
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }
    public String getCarModel(){
        return carModel;
    }
    public String getCarBrand(){
        return carBrand;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public double calculateprice (int rentalDays){
        return basePrice * rentalDays;
    }
    public void rented(){
        isAvailable = false;
    }
    public void returned(){
        isAvailable = true;

    }
}
class Customer{
    private String customerId;
    private String customerName;
    public Customer(String customerId, String customerName){
        this.customerId = customerId;
        this.customerName = customerName;
    }
    public String getCustomerId(){
        return customerId;
    }
    public String getCustomerName(){
        return customerName;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int rentedDays;

    Rental(Car car, Customer customer, int rentedDays){
        this.car = car;
        this.customer = customer;
        this.rentedDays = rentedDays;
    }
    // getters
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getRentedDays(){
        return rentedDays;
    }
}
class RentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    RentalSystem(){
        cars = new ArrayList<>();
        customers= new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentCar(Car car, Customer customer, int rentedDays){
        if(car.isAvailable()){
            car.rented();
            rentals.add(new Rental(car, customer , rentedDays));
            System.out.println("Car rented successfully");
        }
        else {
            System.out.println("Car is not avaliable");
        }
    }
    // reture car
    public void returnCar(Car car){
        car.returned();
        Rental rentalToRemove = null;
        for (Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null){
            rentals.remove(rentalToRemove);
        }else {
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1){
                System.out.println("\n ==== Rent a Car ====\n");
                System.out.print("Enter your name");
                String customerName = scanner.nextLine();

                System.out.println("\n Avaliable Cars");
                for (Car car : cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+ car.getCarBrand()+" "+car.getCarModel());
                    }
                }

                System.out.print("Enter car Id you want to rent");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newcustomer = new Customer("C00"+(customers.size()+1), customerName);
                addCustomer(newcustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable())  {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculateprice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newcustomer.getCustomerId());
                    System.out.println("Customer Name: " + newcustomer.getCustomerName());
                    System.out.println("Car: " + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newcustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }

            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getCustomerName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }

            } else if (choice == 3) {
                break;
            }else {
                System.out.println("Enter a valid Input");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}



public class Main {
    public static void main(String[] args) {
        RentalSystem rentalSystem = new RentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}