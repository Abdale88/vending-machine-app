package com.techelevator;

import com.techelevator.view.ProductList;
import com.techelevator.view.Menu;
import com.techelevator.view.VendingMachine;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String EXIT_PROGRAM = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, EXIT_PROGRAM };

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				ProductList products = new ProductList();
				products.printFileContent();
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				VendingMachine vm = new VendingMachine();
				vm.printChoice();
			}else if(choice.equals(EXIT_PROGRAM)){
				break;
			}
		}
	}

	public static void main(String[] args) {
//		VendingMachine vm = new VendingMachine();
//		vm.restock();

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
		System.out.println();
	}
}
