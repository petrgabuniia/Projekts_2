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
		int choise;
		String choiseStr;

		loop: while (true) {

			System.out.println("\n1) About");
			System.out.println("2) Add");
			System.out.println("3) Delete");
			System.out.println("4) Edit");
			System.out.println("5) Print");
            System.out.println("6) Sort");
            System.out.println("7) Find");
            System.out.println("8) Calculate average");
            System.out.println("9) Exit");
			System.out.print("\nInput number from 1 till 9: ");
			
			choiseStr = sc.nextLine();
			
			try {
				choise = Integer.parseInt(choiseStr);
				if (choise < 1 || choise > 9) {
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
			

			switch (choise) {
                case 1:
                    about();
                    break;
                case 2:
                    add();
                    break;
                case 3:
                    delete();
                    break;
                case 4:
                    edit();
                    break;
                case 5:
                    print(reader);
                    break;
                case 6:
                    sort();
                    break;
                case 7:
                    find(reader);
                    break;
                case 8:
                    calculateAverage(reader);
                    break;
                case 9:
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
            System.out.println("average= " + String.format("%.2f", avg));
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

    public static void find(BufferedReader reader){
        try {
            String line;
            boolean first_tinme = true;
            while ((line = reader.readLine()) != null) {

                String[] el = line.split(";");
                if(Double.parseDouble(el[4]) < 1000)
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





	public static void about() {
		// TODO insert information about authors
		System.out.println("231RDB331 Petr Gabuniia");
	}

}
