package nic.vahan.common.jsf.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import nic.java.util.FormatUtils;

public class DateUtil {
	 private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DateUtil.class);
	    /**
	     * Milliseconds in one day
	     */
	    public static final long MILLISECONDS_IN_ONE_DAY = 24 * 60 * 60 * 1000;
	    /**
	     * Default Date format
	     */
	    public static final String DATE_FORMAT = "dd-MM-yyyy"; // 'MM' means months, 'mm' means Minutes
	    /**
	     * DAY
	     */
	    public static final int DAY = 1;
	    /**
	     * MONTH
	     */
	    public static final int MONTH = 2;
	    /**
	     * YEAR
	     */
	    public static final int YEAR = 3;

	    /**
	     * Get Current local Date.
	     *
	     * @return local date object.
	     */
	    public static java.util.Date getCurrentLocalDate() {
	        return new java.util.Date();
	    }
	    public static Date parseDateFromYYYYMMDD(String dateStr) {
	        // Check input

	        if (dateStr == null) {
	            return null;
	        }
	        //69. SAR_MAN -24/02/2007 --- inserted next line ----start-----part1--

	        dateStr = dateStr.replace('/', '-');
	        //69. SAR_MAN -24/02/2007 ---end-----part1--
	        // Create the formatter object
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	        // Do not parse the string if it do not adhere to the format given
	        formatter.setLenient(false);

	        // Parse
	        ParsePosition pos = new ParsePosition(0);
	        Date date = formatter.parse(dateStr, pos);

	        // Return
	        return date;
	    }

	    public static String parseDateYYYYMMDDToString(Date dt) {
	        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
	        sdf.applyPattern("YYYY-MM-dd");
	        String nDate = sdf.format(dt);
	        return nDate;
	    }
	    public static String parseDateToString(Date dt) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	        sdf.applyPattern("dd-MMM-yyyy");
	        String nDate = sdf.format(dt);
	        return nDate;
	    }
	    public static String convertStringYYYYMMDDToDDMMYYYY(String strDt) {
	        String ddMMyyyy = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date date = null;
	        try {
	            date = sdf.parse(strDt);
	            ddMMyyyy = getDateInDDMMYYYY(date);
	        } catch (ParseException ex) {
//	            System.out.println("test1:exception raised here");
//	            ddMMyyyy=convertStringDDMMMYYYYToDDMMYYYY(strDt);
	            ex.printStackTrace();
	        }
	        return ddMMyyyy;
	    }
	    public static String getDateInDDMMYYYY(java.util.Date date) {
	        // Check input
	        if (date == null) {
	            return "";
	        }
	        // Set the date
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);

	        // Get the month, day and year
	        int month = calendar.get(Calendar.MONTH) + 1;
	        int day = calendar.get(Calendar.DAY_OF_MONTH);
	        int year = calendar.get(Calendar.YEAR);

	        // Get the 'DD/MM/YYYY' format
	        String strDate = FormatUtils.getInNDigitFormat(2, day) + "-"
	                + FormatUtils.getInNDigitFormat(2, month) + "-"
	                + FormatUtils.getInNDigitFormat(4, year);

	        // Return
	        return strDate;
	    }
	    public static Date parseDate(String dateStr) {
	        // Check input
	        if (dateStr == null) {
	            return null;
	        }

	        // Create the formatter object
	        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

	        // Do not parse the string if it do not adhere to the format given
	        formatter.setLenient(false);

	        // Parse
	        ParsePosition pos = new ParsePosition(0);
	        Date date = formatter.parse(dateStr, pos);

	        // Return
	        return date;
	    }
	    public static String getCurrentDate_YYYY_MM_DD() {
	        //    get current date in Format YYYY-MM-DD
	        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
	        final String dateFormat = "yyyy-MM-dd";
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
	        sdf.setTimeZone(TimeZone.getDefault());
	        return sdf.format(cal.getTime());
	    }

}
