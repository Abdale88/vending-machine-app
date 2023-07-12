package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class VendingMachine implements Purchasable{
    private Map<String, List<ProductList>> products = new HashMap<>();
    private BigDecimal customerBalance = new BigDecimal("0");
    private ProductList listOfProducts;
    private BigDecimal vmBalance = new BigDecimal("0");


    public VendingMachine(){
        restock();
    }

    public void restock(){
        listOfProducts = new ProductList();
        for(String el : listOfProducts.printProductsList()){
            String[] arr = el.split("\\|");
            List<ProductList> list = new ArrayList<>();
            list.add(new ProductList(arr[0], arr[1], new BigDecimal(arr[2]), arr[3]));
            products.put(arr[0], list);
        }
    }


    public void printChoice(){
        System.out.println("Current Money Provided: $" + customerBalance);
        System.out.println("(1) Feed Money");
        System.out.println("(2) Select Product");
        System.out.println("(3) Finish Transaction\n");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select an option: ");
        String userInput = scanner.nextLine();
        if(userInput.equals("3")) finishTransaction();

        while(userInput.equals("1") || userInput.equals("2")){
            System.out.println("Current Money Provided: $" + customerBalance);

            if(userInput.equals("1"))feedMoney();
            if(userInput.equals("2"))selectProduct();

            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction\n");


            System.out.print("Select an option: ");
            userInput = scanner.nextLine();
        }
        if(userInput.equals("3")) finishTransaction();
    }

    public void feedMoney(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please feed money using whole dollars Only ");
        String userMoney = sc.nextLine();
        try{
//            double userTotalMoney = 0;
//            userTotalMoney += Double.parseDouble(userMoney);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
            customerBalance = customerBalance.add(BigDecimal.valueOf(Integer.parseInt(userMoney)));
            String log = sdf.format(date) + " FEED MONEY: $" + BigDecimal.valueOf(Integer.parseInt(userMoney)).setScale(2) + " $" + customerBalance.setScale(2);
            writeLogs(log);
        }catch (NumberFormatException e){
            System.out.print("please enter amount in numbers ");
        }
    }

    public void selectProduct() {
        ProductList productList = new ProductList();
        productList.printFileContent();
        System.out.println();

        Scanner sc = new Scanner(System.in);
        System.out.print("Choose the item you want buy: ");
        String choice = sc.nextLine();

        for (Map.Entry<String, List<ProductList>> entry : products.entrySet()) {
            List<ProductList> values = entry.getValue();
            if (!products.containsKey(choice)) {
                break;
            } else if (entry.getKey().equals(choice)) {
                for (ProductList value : values) {
                    int currentQuantity = Integer.parseInt(value.getQuantity());
                    if (currentQuantity > 0) {
                        if(customerBalance.compareTo(value.getPrice()) < 0){
                            System.out.println("You don't have enough money to buy this item.");
                            break;
                        }
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
                        String price = String.valueOf(value.getPrice());
                        vmBalance = vmBalance.add((value.getPrice())).setScale(2);

                        BigDecimal remainingMoney = customerBalance.subtract(BigDecimal.valueOf(Double.parseDouble(price))).setScale(2);
                        System.out.println(sdf.format(date) + " " + value.getName() + " $" + value.getPrice() + " " + remainingMoney);
                        if (value.getType().equals("Chip")) System.out.println("Crunch Crunch, Yum!");
                        if (value.getType().equals("Candy")) System.out.println("Munch Munch, Yum!");
                        if (value.getType().equals("Drink")) System.out.println("Glug Glug, Yum!");
                        if (value.getType().equals("Gum")) System.out.println("Chew Chew, Yum!");
                        customerBalance = customerBalance.subtract(BigDecimal.valueOf(Double.parseDouble(price))).setScale(2);
                        currentQuantity--;
                        System.out.println("current Quantity " + currentQuantity + " Your balance is: $" + customerBalance + " vm balance is: $" + vmBalance);

                        value.setQuantity(String.valueOf(currentQuantity));
                        String log = sdf.format(date) + " " + value.getName() + " " + value.getID() + " $" +value.getPrice() + " $" + customerBalance;
                        writeLogs(log);
                    }else {
                        System.out.println("SOLD OUT");
                        break;
                    }
                }
            }
        }
    }

    public void finishTransaction(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss aa");
        int total = (int) (Double.parseDouble(String.valueOf(customerBalance)) * 100);
        int numOfQuarters = total / 25;
        int remainder = total % 25;

        int numOfDimes = remainder / 10;
        remainder %= 10;

        int numOfNickles  = remainder / 5;
        remainder %= 5;

        int numOfPennies = remainder;
        System.out.print("Your change is: ");
        if(numOfQuarters > 0) System.out.print(numOfQuarters + " Quarters, " );
        if(numOfDimes > 0) System.out.print(numOfDimes + " Dimes, " );
        if(numOfNickles > 0) System.out.print(numOfNickles + " Nickles, " );
        if(numOfPennies > 0) System.out.print( "and " +numOfPennies + " Pennies" );

        BigDecimal change = customerBalance;
        customerBalance = new BigDecimal("0").setScale(2);
        vmBalance = new BigDecimal("0");
        String log = sdf.format(date) + " GIVE CHANGE: $" + change.setScale(2) + " $" + customerBalance;
        writeLogs(log);
    }


    private void writeLogs(String logs){
        File file = new File("logs.txt");

        try(PrintWriter pw = new PrintWriter( new FileOutputStream(file, true))){
            pw.println(logs);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }


    public BigDecimal getBalance(){
        return new BigDecimal("0");
    }
}

