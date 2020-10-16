/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hauschildt.ui;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author marchauschildt
 */
public class UIUtility {

    /**
     * The desired width of the page in characters.
     */
    public static final int PAGE_WIDTH = 80;


    /**
     * Displays the supplied title text in a consistently formatted manner.
     *
     * @param title The text to display
     */
    public static void showSectionTitle(String title){
        System.out.print(getPageBars('*'));
        int barsWidth = 4;
        int offset = (PAGE_WIDTH / 2) - (title.length() / 2) - barsWidth;
        char[] spaces = new char[offset];
        Arrays.fill(spaces, ' ');
        System.out.println( "\n" + new String(spaces) + "*** " + title + " ***\n");
    }

    /**
     * Displays a wait prompt and waits for the user to hit the enter key.
     */
    public static void pressEnterToContinue(){
        System.out.print("\nPress Enter to continue... ");
        Scanner in = new Scanner(System.in);
        in.nextLine();
    }

    /**
     * Displays a properly formatted menu title.
     *
     * @param menuTitle the text of the title
     */
    public static void showMenuTitle(String menuTitle){
        int barsWidth = 4;
        int offset = (PAGE_WIDTH / 2) - (menuTitle.length() / 2) - barsWidth;
        char[] spaces = new char[offset];
        Arrays.fill(spaces, ' ');
        System.out.println( "\n"
                + new String(spaces)
                + "xxx "
                + menuTitle
                + " xxx\n"
        );
    }

    /**
     * Displays a menu and the prompt, returning the String entered by the user.
     *
     * @param menuTitle the title of the menu
     * @param prompt the prompt for input
     * @param menuOptions the list of menu options to display
     * @return the user's response
     */
    public static String showMenuOptions(String menuTitle
            , String prompt, String[] menuOptions) {
        showMenuTitle(menuTitle);
        for (String menuOption : menuOptions) {
            System.out.println("\t" + menuOption);
        }
        System.out.print(prompt + " ");
        Scanner in = new Scanner(System.in);
        return in.nextLine().trim();
    }

    /**
     * Provides a consistent manner for showing messages.
     * @param message
     */
    public static void showMessage(String message){
        System.out.println(message);
    }

    /**
     * Displays the supplied message.  If waitForAcknowledement is true, will
     * also call pressEnterToContinue().
     *
     * @param message The error message
     * @param waitForAcknowledgement true if must press enter to acknowledge
     */
    public static void showErrorMessage(String message
            , boolean waitForAcknowledgement){
        System.out.println("ERROR: " + message);
        if(waitForAcknowledgement){
            pressEnterToContinue();
        }
    }

    /**
     * Build a line of stars the width of the page.
     * @param barCharacter the character from which to make the bar
     * @return
     */
    public static String getPageBars(char barCharacter){
        char[] chars = new char[PAGE_WIDTH];
        Arrays.fill(chars, barCharacter);
        return new String(chars) + "\n";
    }

    /**
     * Takes a String and shows it, performing line breaks at PAGE_WIDTH
     * intervals.  This makes it easier to print really long Strings in a
     * friendly manner.
     *
     * @param longText the text to print.
     */
    public static void showLongText(String longText){
        System.out.print(getPageBars('-'));
        String toPrint;
        String tempLine = null;
        String remainder = longText;
        int lastSpaceLocation;
        while(remainder.length() > 0){
            if(remainder.length() >= PAGE_WIDTH){
                tempLine = remainder.substring(0, PAGE_WIDTH);
            } else {
                tempLine = remainder.substring(0);
            }
            if(tempLine.length() < 80){
                toPrint = tempLine;
            } else {
                lastSpaceLocation = tempLine.lastIndexOf(' ');
                toPrint = tempLine.substring(0, lastSpaceLocation);
            }
            if(remainder.length() > toPrint.length()){
                remainder = remainder.substring(toPrint.length() + 1);
            } else {
                remainder = remainder.substring(toPrint.length());
            }
            System.out.println(toPrint);
        }
        System.out.print(getPageBars('-'));
    }

    /**
     * Prompts the user for a String and returns whatever was entered.
     *
     * @param prompt the prompt text for the user
     * @return the String entered by the user
     */
    public static String getUserString(String prompt){
        Scanner in = new Scanner(System.in);
        System.out.print(prompt + " ");
        return in.nextLine().trim();
    }

    /**
     * Prompts the user to enter a whole number.  If the value is not a whole
     * number, prints the notIntMessage and tries again.  Otherwise, returns the
     * int that was entered.
     *
     * @param prompt the prompt text for the user
     * @param notIntMessage the error message for not an int
     * @return the int entered
     */
    public static int getUserInt(String prompt, String notIntMessage){
        Scanner in = new Scanner(System.in);
        int value = 0;
        String input;
        boolean needed = true;
        while(needed){
            System.out.print(prompt + " ");
            input = in.nextLine();
            try{
                value = Integer.parseInt(input);
                needed = false;
            }catch(NumberFormatException nfe){
                showErrorMessage(notIntMessage, true);
            }
        }
        return value;
    }

    /**
     * Prompts the user for a whole number between the low bound (inclusive) and
     * the high bound (inclusive).
     *
     * @param prompt the text of the user prompt
     * @param notIntMessage the error message for not an int
     * @param lowBound the lower boundary of the range, inclusive
     * @param highBound the upper boundary of the range, inclusive
     * @param outOfBoundsMessage the error message for not in range
     * @return the entered whole number
     */
    public static int getUserIntInRange(String prompt, String notIntMessage
            , int lowBound, int highBound, String outOfBoundsMessage ){
        int value = 0;
        boolean needed = true;
        while(needed){
            value = getUserInt(prompt, notIntMessage);
            if(value < lowBound || value > highBound){
                showErrorMessage(outOfBoundsMessage, true);
            } else {
                needed = false;
            }
        }
        return value;
    }


}
