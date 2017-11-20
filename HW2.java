/*
 * Ryan Flageolle
 * Computer Science
 * November 17, 2017
 * HW2 - Redone
 */

/** This class pulls it all together and allows the user to interface with the program **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HW2{

    String[] knownCountries;
    int numCountries;
    Country[] countries;
    static Scanner in = new Scanner(System.in);

    public HW2(String file){

        int size = countCountries(file, 0);
        countries = new Country[size];

        loadCountries(file);

    }

    public static void main(String[] args){

        System.out.println("Please enter the name of the File containing the List of Countries: ");
        String file = in.nextLine();

        HW2 current = new HW2(file);
        current.promptUser();
    }


    public int countCountries(String file, int attempt){

      int num = 0;

      try {

          File inn = new File(file);
          BufferedReader reader = new BufferedReader(new FileReader(inn));

          while (reader.readLine() != null) {
              num++;
          }

          reader.close();

      } catch (FileNotFoundException fnfe) {

          attempt++;
          String newFile = "";
          System.out.println("\nSeriously? Can you enter an Actual file name.");
          newFile = in.nextLine();
          countCountries(newFile, attempt);

      } catch (IOException i) {

          System.out.println("Something bad happened.");

      }

      if (attempt == 3){
          System.exit(0);
      }

      //System.out.println(num);
      return num;
    }

    public void loadCountries(String file){

        int currentCountry = 0;

        try{
            File text = new File(file);
            Scanner lines = new Scanner(text);
            while(lines.hasNextLine()){
                String full = lines.nextLine();
                int size = 1;
                for (int i = 0; i < full.length(); i++){
                    if (full.charAt(i) == ','){
                        size++;
                    }
                }

                String[] country = new String[size];
                Scanner breaker = new Scanner(full).useDelimiter(", ");
                int currentString = 0;

                while (breaker.hasNext()){
                      String toAdd = breaker.next();
                      country[currentString] = toAdd;
                      //System.out.println(toAdd);
                      currentString++;
                }

                //System.out.println("Size: " + size);
                //System.out.println("borderSize: " + (size - 8));
                int[] borders = new int[size - 8];

                for (int i = 0; i < (size - 8); i++){
                     borders[i] = Integer.parseInt(country[i + 8]);
                     //System.out.println(country[i + 7]);
                }

                Country add = new Country(country[0], country[1], country[2], Integer.parseInt(country[3]),
                    Integer.parseInt(country[4]), Double.parseDouble(country[5]), Integer.parseInt(country[6]),
                    Integer.parseInt(country[7]), borders);

                countries[currentCountry] = add;
                currentCountry++;

            }

        } catch(FileNotFoundException fnfe){
            String newFile = "";
            System.out.println("\nSeriously? Can you enter an Actual file name.");
            newFile = in.nextLine();
            loadCountries(newFile);
        }

    }

    public void promptUser(){
        boolean cont = false;

        System.out.println("This program allows you to choose one of 4 operations: \n"
                      + "1 - Display the countries that border another Country. \n"
                      + "2 - Display all countries that have a population greater "
                      + "than a given population. \n"
                      + "3 - Display the countries that border another Country "
                      + "with a population greater than a given population. \n"
                      + "4 - Quit the program. \n\n"
                      + "To select the program simply type the number corresponding to "
                      + "the operation you want to be completed and then follow the instructions \n"
                      + "on screen. (When entering Countries use upper-case first letter followed by lower-case form) \n"
                      + "------------------------------------------------------------------------------------------------- \n\n");

      do{
          System.out.print("Which Operation would you like to perform: ");
          int operation = in.nextInt();
          boolean moveOn = false;

          switch (operation) {
              case 1:
                  String country = "";

                  while (!moveOn){
                      System.out.println("Which country's borders would you like to see? ");
                      country = in.next();
                      moveOn = isCountry(country);
                  }
                  displayBorders(country);
                  cont = doContinue();
                  break;
              case 2:
                  int pop = 0;

                  String countries = "";
                  do {
                      System.out.println("What population would you like to test? (whole number): ");
                      moveOn = in.hasNextInt();
                      pop = in.nextInt();
                  } while (!moveOn);

                  greaterPopulation(pop);
                  cont = doContinue();
                  break;
              case 3:
                  String count = "";
                  while (!moveOn){
                      System.out.println("Which Country would you like to see?: ");
                      count = in.nextLine();
                      moveOn = isCountry(count);
                  }

                  do {
                      System.out.println("What population would you like to test? (whole number): ");
                      moveOn = in.hasNextInt();
                      pop = in.nextInt();
                  } while (!moveOn);

                  bordersAndPopulation(count, pop);
                  cont = doContinue();
                  break;

              case 4:
                  cont = false;
                  break;
              default:
                  cont = false;
          }
      } while (cont);
    }

    public void displayBorders(String str){
        Country toDisplay = null;
        boolean valid = false;

        for (Country current : countries){
            if (current.isCountry(str)){
                toDisplay = current;
                System.out.println();
                valid = true;
                break;
            }
        }

        String rtn = "Countries that border " + toDisplay.name + "(" + toDisplay.borderNumber + ")" + ":";

        for (int i : toDisplay.borders) {
            rtn += "\n - " + countries[i].name;
        }

        if (valid){
            System.out.println(rtn);
        }
        else {
            System.out.println("Country not valid.");
        }
    }

    public void greaterPopulation(int pop){

        String rtn = "\nCountries with population greater than " + pop + ":";

        for (Country c : countries){
            if (c.population > pop){
                rtn += "\n - " + c.name;
            }
        }

        System.out.println(rtn);
    }

    public void bordersAndPopulation(String str, int pop){
        Country current = fromString(str);

        String rtn;
        if (current != null){
            rtn = "\nCountries that border " + current.name + " and population greater than: " + pop;
            for (int i : current.borders){
                if (countries[i].population > pop){
                    rtn += "\n - " + countries[i].name;
                }
            }
        }
        else {
            rtn = "Country not found.";
        }

        System.out.println(rtn);
    }

    public Country fromString(String str){
        Country rtn = null;
        for (Country c : countries){
            if(str.toLowerCase().equals(c.name.toLowerCase())){
                rtn = c;
                break;
            }
        }
        return rtn;
    }

    public boolean isCountry(String str){
      boolean check = false;
        for(Country c : countries){
            if (c.isCountry(str)){
                check = true;
                break;
            }
        }
        return check;
    }

    public static Boolean doContinue(){
        Boolean b = true;
        Boolean rtn = false;
        do {
            System.out.print("\n" + "Would you like to Continue? (Y or N): ");
            switch (in.next()) {
                case "Y":
                case "y":
                    rtn = true;
                    break;
                case "N":
                case "n":
                    rtn = false;
                    break;
                default:
                    b = false;
                    break;
            }
        } while (!b);
        return rtn;
    }

    @Override
    public String toString(){

        String str = "Countries contained: \n";

        for (Country c : countries){
            str += "\n" + c.toString() + "\n";
        }

        return str;

    }
}
