package org.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {
	private String convertDateFormat(String date, String expectedFormat) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyy hh-mm-ss");
		SimpleDateFormat outputFormat = new SimpleDateFormat(expectedFormat);
		String outputDateString = "";

		try {
			Date inputDate = inputFormat.parse(date);
			outputDateString = outputFormat.format(inputDate);
			System.out.println("Converted Date: " + outputDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return outputDateString;
	}

	public Date convertStringtoDate(String date, String format) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat(format);
		return inputFormat.parse(date);

	}

	public String getCurrentDate(String format) {
		Date currentDate = new Date();
		// Create a SimpleDateFormat object to format the date and time
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		// Format the date and time as a string
		return dateFormat.format(currentDate);
	}
	
	public boolean isFileOlder(Date specificDate, int days) {
		long differenceInDays = TimeUnit.DAYS.convert(new Date().getTime() - specificDate.getTime(), TimeUnit.MILLISECONDS);
        // Check if the difference is greater than the days
        if (differenceInDays > days) {
            return true;
        } else {
            return false;
        }
	}
}
