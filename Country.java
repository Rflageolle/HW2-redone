/*
 * Ryan Flageolle
 * Computer Science
 * November 17, 2017
 * HW2 - Redone
 */

/** The Country class is the basic building block of the program which lays out the
    aspects of the programs representation of the country and several methods to aid in the
    implementation of the HW2 class **/

public class Country {

    String name;
    String latitude;
    String longitude;
    int area;
    int population;
    double gdp;
    int year;
    int borderNumber;
    int[] borders;

    Country(String name, String lat, String lon, int area, int pop, double gdp, int year, int borderNumber, int[] borders){

          this.name = name;
          this.latitude = lat;
          this.longitude = lon;
          this.area = area;
          this.population = pop;
          this.gdp = gdp;
          this.year = year;
          this.borderNumber = borderNumber;
          this.borders = borders;

    }

    public boolean doesBorder(Country b){

          int check = b.borderNumber;
          boolean does = false;

          for (int i : borders){
              if (i == check){
                  does = true;
              }
          }

          return does;

    }

    public boolean populationCheck(int i){
        return (population > i);
    }

    public boolean isCountry(String str){
        return (str.toLowerCase().equals(name.toLowerCase()));
    }

    public String getName(int i){
        String str = "";
        if (i == borderNumber) {
            str = name;
        }
        return str;
    }

    @Override
    public String toString(){
        return (this.name + ": " + "\n - Latitude: " + this.latitude + "\n - Longitude: " + this.longitude +
        "\n - Area: " + this.area + "  Population: " + this.population + "\n - GDP: " + this.gdp + "\n - Year: " + this.year);
    }
}
