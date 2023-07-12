package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class ProductList {
    private String name;
    private String type;
    private BigDecimal price = new BigDecimal("0");
    private String ID;
    List<String> products  = new ArrayList<>();
    private String quantity = "5";

    public ProductList(String ID, String name, BigDecimal price, String type ){
        this.name = name;
        this.type = type;
        this.price = price;
        this.ID = ID;
    }
    public ProductList(){}

    public void printFileContent(){
        File fileData = new File("vendingmachine.csv");
        try(Scanner sn = new Scanner(fileData)){
            while(sn.hasNextLine()){
                String line = sn.nextLine();
                System.out.println(line);
            }
        }catch (FileNotFoundException e ){
            System.out.println(e.getMessage());
        }
    }


    public List<String> printProductsList(){
        File fileData = new File("vendingmachine.csv");
        try(Scanner scanner = new Scanner(fileData)) {
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                products.add(line + "|" + quantity);
            }
        }catch (FileNotFoundException e ){
            e.getMessage();
        }
        return products;
    }



    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public BigDecimal getPrice(){return price;}
    public void setPrice(BigDecimal price){this.price = price;}

    public String getID(){return ID;}
    public void setID(String ID){this.ID = ID;}

    public List<String> getProducts(){return products;}
    public void setProducts(List<String> products){this.products = products;}

    public String getQuantity(){return quantity;}
    public void setQuantity(String quantity){this.quantity = quantity;}
}
