// 231RDB331 Petr Gabuniia 6

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

//================================================

class Student implements Serializable {
	public String name;
	public String surname;
	public int mark1, mark2, mark3;
	
	public Student(String name, String surname, int mark1, int mark2, int mark3) {
		this.name = name;
		this.surname = surname;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.mark3 = mark3;
	}
	
	public void print(int numurs) {
		System.out.printf("\n%-4d%-15s%-15s%-12d%-12d%-12d", numurs, name, surname, mark1, mark2, mark3);
	}
}

//================================================

public class Main {

	static Scanner sc = new Scanner(System.in);
	
	static String filename = "db.csv";

	public static void main(String[] args) {
		String choiseStr;

		loop: while (true) {
            System.out.print("\nPossible comands: ");
			System.out.println("\n1) Add; format: add id city date days price vehicle; adds new trip into the file ");
			System.out.println("2) Delete; format: del id; deletes trip from the file");
			System.out.println("3) Edit; format: edit id city date days price vehicle; edits trip in the file");
			System.out.println("4) Print; format: print; prints all trips from the file");
            System.out.println("5) Sort; format: sort; sort all trips in the file");
            System.out.println("6) Find; format: find price; finds trips with price less than given");
            System.out.println("7) Calculate average; format: avg; calculates and prints average price of all trips");
            System.out.println("8) Exit; format: exit; exits the program");
			System.out.print("\nEnter one of the allowed commads (add, del, print, sort, find, avg or exit): ");
			
			choiseStr = sc.nextLine();
            String comand[] = choiseStr.split(" ");
            System.out.println(comand[0]);
			
			try {
				if (!comand[0].equals("find") && !comand[0].equals("avg") && !comand[0].equals("exit") && !comand[0].equals("print") && !comand[0].equals("sort") && !comand[0].equals("add") && !comand[0].equals("del")){
					throw new Exception();
				}
			}
			catch (Exception ex) {
				System.out.println("Error, please, input number from 1 till 10");
				continue;
			}

            File f = new File(filename);
            if (!f.exists()) {
                System.out.println("The file does not exist.");
                return;
            }
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(filename));
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                return;
            }
			

			switch (comand[0]) {
                case "add":
                    //TODO
                    add();
                    break;

                case "del":
                    //TODO
                    delete();
                    break;

                case "edit":
                    //Todo
                    edit();
                    break;

                case "print":
                    print(reader);
                    break;

                case "sort":
                    //TODO
                    sort();
                    break;

                case "find":
                    try {
                        double price = Double.parseDouble(comand[1]);
                        find(reader, price);
                        } catch (Exception ex) {
                            System.out.println("wrong price");
                            continue;
                        }
                    break;

                case "avg":
                    calculateAverage(reader);
                    break;

                case "exit":
                    return;
		    }
	    }

    }
    public static void add(){
        // TODO insert code here
    }

    public static void delete(){
        // TODO insert code here
    }

    public static void edit(){
        // TODO insert code here
    }

    public static void print(BufferedReader reader){
        System.out.println("\n----------------------------------------------------------------"); // 64
		System.out.printf("#   %-4s%-21s%-11s%-6s%-10s%-8s", "ID", "City", "Date", "Days", "Price", "Vehicle");
		System.out.println("\n----------------------------------------------------------------");
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] el = line.split(";");
                System.out.printf("#   %-4s%-21s%-11s%-6s%-10s%-8s%n", el[0], el[1], el[2], el[3], el[4], el[5]);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void calculateAverage(BufferedReader reader){
        try {
            String line;
            double avg = 0;
            int count = 0;
            while ((line = reader.readLine()) != null) {

                String[] el = line.split(";");
                avg = avg + Double.parseDouble(el[4]);
                count++;
            }
            avg = avg / count;
            System.out.println("average = " + String.format("%.2f", avg));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void find(BufferedReader reader, double price){
        try {
            String line;
            boolean first_tinme = true;
            while ((line = reader.readLine()) != null) {

                String[] el = line.split(";");
                if(Double.parseDouble(el[4]) < price)
                {
                    if(first_tinme)
                    {
                        System.out.println("\n----------------------------------------------------------------"); // 64
                        System.out.printf("#   %-4s%-21s%-11s%-6s%-10s%-8s", "ID", "City", "Date", "Days", "Price", "Vehicle");
                        System.out.println("\n----------------------------------------------------------------");
                        first_tinme = false;
                    }
                    System.out.printf("#   %-4s%-21s%-11s%-6s%-10s%-8s%n", el[0], el[1], el[2], el[3], el[4], el[5]);
                }

            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void sort(){
        // TODO insert code here
    }

}
