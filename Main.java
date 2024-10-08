// 231RDB331 Petr Gabuniia 6

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

//================================================

public class Main {

	static Scanner sc = new Scanner(System.in);
	
	static String filename = "db.csv";

	public static void main(String[] args) {
		String choiseStr;
        BufferedReader reader = null;
        BufferedWriter writer = null;

		loop: while (true) {
            System.out.print("\nPossible comands: ");
			System.out.println("\n1) Add; format: add id;city;date;days;price;vehicle; adds new trip into the file ");
			System.out.println("2) Delete; format: del id; deletes trip from the file");
			System.out.println("3) Edit; format: edit id;city;date;days;price;vehicle; edits trip in the file");
			System.out.println("4) Print; format: print; prints all trips from the file");
            System.out.println("5) Sort; format: sort; sort all trips in the file");
            System.out.println("6) Find; format: find price; finds trips with price less than given");
            System.out.println("7) Calculate average; format: avg; calculates and prints average price of all trips");
            System.out.println("8) Exit; format: exit; exits the program");
			System.out.print("\nEnter one of the allowed commads (add, del, print, sort, find, avg or exit): ");
			
			choiseStr = sc.nextLine();
            String comand[] = choiseStr.split(" "); 
            String input[] = null;
            if (comand.length != 1)
            {
                input = comand[1].split(";");
            }
			
			try {
				if (!comand[0].equals("find") && !comand[0].equals("avg") && !comand[0].equals("exit") && !comand[0].equals("print") && !comand[0].equals("sort") && !comand[0].equals("add") && !comand[0].equals("del") && !comand[0].equals("edit")){
					throw new Exception();
				}
			}
			catch (Exception ex) {
				System.out.println("Error, no such comand.");
				continue;
			}

            File f = new File(filename);
            if (!f.exists()) {
                System.out.println("The file does not exist.");
                return;
            }
            

            try {
                reader = new BufferedReader(new FileReader(filename));
                writer = new BufferedWriter(new FileWriter("temp"));
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
                return;
            } catch (IOException ex) {
                System.out.println("Error creating BufferedWriter: " + ex.getMessage());
                return;
            }
			

			switch (comand[0]) {
                case "add":
                    if (input == null) {
                        System.out.println("wrong field count");
                        continue;
                    }
                    if (input.length != 6) {
                        System.out.println("wrong field count");
                        continue;
                    }
                    add(reader, writer, input);
                    break;

                case "del":
                    if (comand.length != 2) {
                        System.out.println("wrong field count");
                        continue;
                    }
                    try {
                        int id = Integer.parseInt(comand[1]);
                        if (id < 0 || id > 999) {
                            throw new Exception();
                        }
                    } catch (Exception ex) {
                        System.out.println("wrong id");
                        continue;
                    }
                    delete(reader, writer, comand);
                    break;

                case "edit":
                    if (input == null) {
                        System.out.println("wrong field count");
                        continue;
                    }
                    if (input.length != 6) {
                        System.out.println("wrong field count");
                        continue;
                    }
                    edit(reader, writer, input);
                    break;

                case "print":
                    print(reader);
                    break;

                case "sort":
                    //TODO
                    sort(reader, writer);
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
                    try 
                    {
                        File temp = new File("temp");
                        temp.delete();
                        return;
                    }
                    catch (Exception ex) {

                    }
                    return;
		    }
	    }

    }


    public static void add(BufferedReader reader, BufferedWriter writer, String[] input){
        try {
            String line;
            try {
                int id = Integer.parseInt(input[0]);
                if (id < 0 || id > 999) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println("wrong id");
                return;
            }
            try {
                int days = Integer.parseInt(input[3]);
                if (days < 1 || days > 365) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println("wrong day count");
                return;
            }
            try {
                double price = Double.parseDouble(input[4]);
                input[4] = String.format("%.2f", price);
            } catch (Exception ex) {
                System.out.println("wrong price");
                return;
            }
            String date = input[2];
            String dates[] = date.split("/");
            try{
                int day = Integer.parseInt(dates[0]);
                int month = Integer.parseInt(dates[1]);
                if (day < 1 || day > 31 || month < 1 || month > 12) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println("wrong date");
                return;
            }
            String city = input[1];
            city = city.toLowerCase();
            city = city.substring(0, 1).toUpperCase() + city.substring(1);
            input[1] = city;
            String vehicle = input[5];
            vehicle = vehicle.toUpperCase();
            if(!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
                System.out.println("wrong vehicle");
                return;
            }
            
            while ((line = reader.readLine()) != null) {
                String els[] = line.split(";");
                if (els[0].equals(input[0])) {
                    System.out.println("wrong id");
                    return;
                }
                writer.write(line);
                writer.newLine();
            }
            
            writer.write(input[0] + ";" + input[1] + ";" + input[2] + ";" + input[3] + ";" + input[4] + ";" + input[5]);
            writer.newLine();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
                writer.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        File originalFile = new File(filename);
        File tempFile = new File("temp");
        originalFile.delete();
        tempFile.renameTo(originalFile);
        System.out.println("added");
}

    public static void delete(BufferedReader reader, BufferedWriter writer, String[] comand){
       try{
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String els[] = line.split(";");
                if (els[0].equals(comand[1])) {
                    found = true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
            if (!found) {
                System.out.println("wrong id");
                return;
            }
        }

        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
                writer.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        File originalFile = new File(filename);
        File tempFile = new File("temp");
        originalFile.delete();
        tempFile.renameTo(originalFile);
        System.out.println("deleted");
    }
    
    

    public static void edit(BufferedReader reader, BufferedWriter writer, String[] input){
        try {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String els[] = line.split(";");
                if (els[0].equals(input[0])) {
                    found = true;
                    try {
                        if (input[3]!= ""){
                            int days = Integer.parseInt(input[3]);
                            if (days < 1 || days > 365) {
                                throw new Exception();
                            }
                            els[3] = input[3];
                        }
                    } catch (Exception ex) {
                        System.out.println("wrong day count");
                        return;
                    }
                    try {
                        if (input[4] != ""){
                            double price = Double.parseDouble(input[4]);
                            els[4] = String.format("%.2f", price);
                        }
                    } catch (Exception ex) {
                        System.out.println("wrong price");
                        return;
                    }
                    String date = input[2];
                    if (date != ""){
                        String dates[] = date.split("/");
                        try{
                            int day = Integer.parseInt(dates[0]);
                            int month = Integer.parseInt(dates[1]);
                            if (day < 1 || day > 31 || month < 1 || month > 12) {
                                throw new Exception();
                            }
                            els[2] = input[2];
                        } catch (Exception ex) {
                            System.out.println("wrong date");
                            return;
                        }
                    }
                    if (input[1] != ""){
                        String city = input[1];
                        city = city.toLowerCase();
                        city = city.substring(0, 1).toUpperCase() + city.substring(1);
                        els[1] = city;
                    }
                    if (input[5] != ""){
                        String vehicle = input[5];
                        vehicle = vehicle.toUpperCase();
                        if(!vehicle.equals("TRAIN") && !vehicle.equals("PLANE") && !vehicle.equals("BUS") && !vehicle.equals("BOAT")) {
                            System.out.println("wrong vehicle");
                            return;
                        }
                        els[5] = vehicle;
                    }
                    writer.write(els[0] + ";" + els[1] + ";" + els[2] + ";" + els[3] + ";" + els[4] + ";" + els[5]);
                    writer.newLine();
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
            if (!found) {
                System.out.println("wrong id");
                return;
            }

        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
                writer.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        File originalFile = new File(filename);
        File tempFile = new File("temp");
        originalFile.delete();
        tempFile.renameTo(originalFile);
        System.out.println("changed");
        
    }





    public static void print(BufferedReader reader){
        System.out.println("\n------------------------------------------------------------"); 
		System.out.printf("%-4s%-21s%-11s%-6s%-10s%-8s", "ID", "City", "Date", "Days", "Price", "Vehicle");
        System.out.println("\n------------------------------------------------------------"); 
        try {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] el = line.split(";");
                System.out.printf("%-4s%-21s%-11s%-6s%-10s%-8s%n", el[0], el[1], el[2], el[3], el[4], el[5]);
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

    public static void sort(BufferedReader reader, BufferedWriter writer){
        try {
            String line;
            String[] lines = new String[1000];
            int count = 0;
            while ((line = reader.readLine()) != null) {
                lines[count] = line;
                count++;
            }
            for (int i = 0; i < count; i++) {
                for (int j = i + 1; j < count; j++) {
                    String[] el1 = lines[i].split(";");
                    String[] el2 = lines[j].split(";");
                    String date1[] = el1[2].split("/");
                    String date2[] = el2[2].split("/");
                    int year1 = Integer.parseInt(date1[2]);
                    int month1 = Integer.parseInt(date1[1]);
                    int day1 = Integer.parseInt(date1[0]);

                    int year2 = Integer.parseInt(date2[2]);
                    int month2 = Integer.parseInt(date2[1]);
                    int day2 = Integer.parseInt(date2[0]);
                    if (year1 > year2 || (year1 == year2 && month1 > month2) || (year1 == year2 && month1 == month2 && day1 > day2)){
                        String temp = lines[i];
                        lines[i] = lines[j];
                        lines[j] = temp;
                    }
                }
            }
            for (int i = 0; i < count; i++) {
                writer.write(lines[i]);
                writer.newLine();
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            try {
                reader.close();
                writer.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        File originalFile = new File(filename);
        File tempFile = new File("temp");
        originalFile.delete();
        tempFile.renameTo(originalFile);
        System.out.println("sorted");
    }
    

}
