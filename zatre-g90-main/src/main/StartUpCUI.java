package main;

import cui.ConsoleApp;
import domein.DomeinController;

public class StartUpCUI {

	public static void main(String[] args) {
		new ConsoleApp(new DomeinController()).start();
	}

}
