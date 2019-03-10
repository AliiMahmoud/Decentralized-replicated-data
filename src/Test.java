package control;

import java.util.Scanner;

import dataManager.FakeDataGenerator;

public class Test {
	public static void main(String[] args) throws Exception
	{
		Node node = new Node();
		node.login();
		node.start();
		
		while(true)
		{
			Scanner in = new Scanner(System.in);
			if(node.sendData(in.nextLine()))
			{
				node.sleep(500);
			}
		}
	}

}
