/*
 * Ryan Flageolle
 * Computer Science
 * November 17, 2017
 * HW2 - Redone
 */

/** The border class allows us to check if two countries border each other **/

public class Border{

    boolean doesBorder;
    Country one;
    Country two;

    Border(Country a, Country b){

        this.one = a;
        this.two = b;

    }

    public void doesBorder(){
        doesBorder = a.doesBorder(b);
    }

}
