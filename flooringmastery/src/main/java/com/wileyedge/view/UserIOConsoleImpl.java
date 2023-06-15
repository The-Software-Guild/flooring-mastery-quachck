package com.wileyedge.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    private Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.println(message);
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer value.");
            }
        }
    }
        
	public int readInt(String msgPrompt, int min, int max) {
		int result;
		do {
			result = readInt(msgPrompt);
		} while (result < min || result > max);

		return result;
	}

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a decimal value.");
            }
        }
    }

    public LocalDate readLocalDate(String prompt) {
        String dateStr;
        LocalDate parsedDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        boolean isValid = false;
        do {
            dateStr = readString(prompt);
            try {
                parsedDate = LocalDate.parse(dateStr, formatter);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Please enter the date in the format MMDDYYYY");
                isValid = false;
            }
        } while (!isValid);
        return parsedDate;
    }


    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
