package dataManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import javafx.scene.transform.Scale;

public class DataStorage 
{
	public static boolean writeFile(Vector<String> queue, String filePath) 
	{
		try
		{
			File file = new File(filePath);
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter);
			int size = queue.size();
			for(int i = 0; i < size; ++i)
			{
				String element = queue.elementAt(0);
				printWriter.println(element);
				queue.removeElementAt(0);
			}
			printWriter.close();
			bufferedWriter.close();
			fileWriter.close();		
		}
		catch(IOException e)
		{
			System.out.println("An error occured while storing the data");
			return false;
		}
		return true;
	}
	public static Vector<String> readFile(String filePath) throws Exception
	{
		Scanner file = new Scanner(new File(filePath));
		Vector<String> data = new Vector<>();
		while(file.hasNextLine())
		{
			data.add(file.nextLine());
		}
		file.close();
		return data;
	}
	
	
	
}