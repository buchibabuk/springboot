/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nic.vahan.common.jsf.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import org.w3c.dom.html.HTMLButtonElement;

public class JSFUtils {

    private static final Logger LOGGER = Logger.getLogger(JSFUtils.class);
    public static final int VM_MODELS_INDEX_OF_MAKER_ID = 2;
    public static final String ERROR = "ERROR";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String FATAL = "FATAL";

    public static boolean validateDate(String date) {

        Pattern pattern;
        Matcher matcher;

        String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);
        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31")
                        && (month.equals("4") || month.equals("6") || month.equals("9")
                        || month.equals("11") || month.equals("04") || month.equals("06")
                        || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

 

   
    /**
     * Dump the given data in the console
     *
     * @param data Given two dimensional table (array)
     */
    public static void dump(String[][] data) {
        // Check input
        if (data == null) {
            return;
        }

        // Print the data
        for (int i = 0; i < data.length; i++) {
            System.out.println(data[i][0] + "\t: " + data[i][1]);
        }
    }

    

   

    /**
     * Get the Select One Menu component by ID and then set the value of the
     * component
     *
     * @param evt
     * @param uiSelectOneMenu
  
    public static HashMap arrayToHash(String[][] data) {
        HashMap tempMap = new HashMap();
        for (int i = 0; i < data.length; i++) {
            tempMap.put(data[i][0], data[i][1]);
        }
        return tempMap;
    }

    /**
     * Method to check if the given string is a number or not.
     *
     * @param numToCheck number to check
     *
     * @return "true" if the string is a number else "false".
     */
    public static boolean isNumeric(String numToCheck) {
        boolean retValue = false;

        // Check if has some value
        if (numToCheck != null && numToCheck.matches("[0-9]+")) {
            try {
                retValue = true;
            } catch (NumberFormatException ne) {
                // Do nothing in the catch as retValue is already false.
            }
        }

        return retValue;
    }

    /**
     * Returns boolean value, showing whether Input string is AlphaNumeric or
     * not.
     *
     * @param str String
     *
     * @return true if alphanumeric else false
     */
    public static boolean isAlphaNumeric(String str) {
        boolean result = true;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isLetterOrDigit(str.charAt(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isAlphaNumericWithSpace(String str) {
        boolean result = true;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isLetterOrDigit(str.charAt(i)) || Character.isSpaceChar(str.charAt(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Returns boolean value, showing whether Input string is Alphabetic or not.
     *
     * @param str String
     *
     * @return true if Alphabetic, else false
     */
    public static boolean isAlphabet(String str) {
        boolean result = true;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isLetter(str.charAt(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean isAlphabetWithSpace(String str) {
        boolean result = true;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isLetter(str.charAt(i)) || Character.isSpaceChar(str.charAt(i)))) {
                result = false;
                break;
            }
        }
        return result;
    }

    /*
     * address validator
     */
    public static boolean isAddressValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9/.,&\\- ]*");
        }
        return false;
    }
    /*
     * Financer Name validator
     */

    public static boolean isFncrNameValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9/.,&\\- ]*");
        }
        return false;
    }

    /*
     * Encdata validator
     */
    public static boolean isEncdataValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9_&=?\\s]*");
        }
        return false;
    }

    /*
     * Double Varification data validator
     */
    public static boolean isDblVarificationEncdataValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9_&=?\\s]*");
        }
        return false;
    }

    /*
     * URL validator
     */
    public static boolean isUrlValid(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[\\w:./\\\\ ]*");
        }
        return false;
    }

    /*
     * URL validator
     */
    public static boolean isUrlValidAsPerVahanService(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[\\w:./\\\\ -?=]*");
        }
        return false;
    }

    /*
     * Email Validator
     */
    public static boolean isEmailValid(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[\\w\\._@]*");
        }
        return false;
    }
    /*
     * Date Validator
     */

    public static boolean isDateValid(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[A-Za-z0-9-:./ ]*");
        }
        return false;
    }
    /*
     * DATETIME validator 
     */

    public static boolean isDateTimeValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[A-Za-z0-9-:./ ]*");
        }
        return false;
    }

    /*
     * IP address validator
     */
    public static boolean isIPValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[0-9:.]*");
        }
        return false;
    }

    /**
     * Get a HashMap Object from String, Here it splits the String with special
     * symbols '|' and '='
     *
     * @param str
     * @return
     */
    public static HashMap getHashMapFromString(String str) {
        HashMap map = new HashMap();
        String tokens[] = str.split("\\|");
        for (int i = 0; i < tokens.length; i++) {
            String subTokens[] = tokens[i].split("=");
            if (subTokens.length > 1) {
                map.put(subTokens[0], subTokens[1]);
            }
        }
        return map;
    }

    public static String md5_hex(String passwd) {
        String rtnMd5 = "";
        if (passwd != null && !passwd.equalsIgnoreCase("")) {
            try {
                rtnMd5 = MD5(passwd);
            } catch (Exception ex) {
            }
        }
        return rtnMd5;
    }
// add spl

    public static int generateHashCode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        int hashCode = 0;
        if (str != null && str.length() > 0) {
            String md5String = MD5(str);
            long asciiSum = getAsciiSum(md5String);
            hashCode = (int) (asciiSum % 10);
        }
        return hashCode;
    }
// end spl

    public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = null;
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
// add spl

    public static long getAsciiSum(String str) {
        long asciiSum = 0;
        if (str != null && str.length() > 0) {
            for (int i = 0; i < str.length(); i++) {
                asciiSum = asciiSum + str.charAt(i);

            }
        }
        return asciiSum;
    }
// end spl

    public static String convertSanitizedStr(String str) {
        return (str.replaceAll("[\'\"([%])<>?:=&#$*]", ""));
    }

    public static String convertAddressSanitizedStr(String str) {
        return (str.replaceAll("[\'\"([%])<>?:=&#$*]", ""));
    }

    public static String convertSanitizedStr(Object obj) {
        String str = "";
        if (obj == null) {
            str = "";
        } else {
            str = obj.toString();
        }
        return (str.replaceAll("[\'\"([%])<>?:=&#$*]", ""));
    }

    public static String trim(String str) {
        String trim = "";
        if (str == null || str.equalsIgnoreCase("")) {
            trim = "";
        } else {
            trim = str.trim();
        }
        return trim;
    }

    public static Date getLastDateOfTheMonth(Date dt) {
        Date rtnDate = null;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setLenient(false);
        if (dt != null) {
            cal.setTime(dt);
            int lastDate = cal.getActualMaximum(Calendar.DATE);
            cal.set(Calendar.DATE, lastDate);
            rtnDate = cal.getTime();
        }
        return rtnDate;
    }

    public static java.sql.Date convertStringToSQLDate(String date) {
        java.sql.Date dt = null;

        if (date != null && date.length() > 0) {
            java.util.Date utilDate = getStringToDate(date);
            if (utilDate != null) {
                long t = utilDate.getTime();
                dt = new java.sql.Date(t);
            }
        }
        return dt;
    }

    public static Date getStringToDate(String strDt) {
        //return variable
        Date dt = null;
        //Constructs a SimpleDateFormat using the given pattern
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            //Parses the given string to a date
            dt = sdf.parse(strDt);
        } catch (ParseException pex) {
        }
        return dt;
    }

    public static Date getStringToDateyyyyMMdd(String strDt) {
        //return variable
        Date dt = null;
        //Constructs a SimpleDateFormat using the given pattern
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Parses the given string to a date
            dt = sdf.parse(strDt);
        } catch (ParseException pex) {
        }
        return dt;
    }

    public static Date getStringToDateddMMMyyyy(String strDt) {
        //return variable
        Date dt = null;
        //Constructs a SimpleDateFormat using the given pattern
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            //Parses the given string to a date
            dt = sdf.parse(strDt);
        } catch (ParseException pex) {
        }
        return dt;
    }

    /**
     * Method to convert date into 'dd-MMM-yyyy' format
     *
     * @param dt
     * @return
     */
    public static String convertToStandardDateFormat(Date dt) {
        String rtnDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

        if (dt != null) {
            rtnDate = sdf.format(dt);
        }
        return rtnDate;
    }
    /*
     * Methods Copied from the National Permit Software
     *
     * Ambrish 27 May 2011
     */
//ambrish start

    public static String makeCapital(String str) {
        String rtnStr = "";
        if (str != null && str.length() > 0) {
            rtnStr = str.toUpperCase();
        }
        return rtnStr;
    }

    public static boolean validateRegNo(String regNo) {
        return regNo.matches("[a-yA-Y][a-zA-Z0-9 ]*");
    }

    public static boolean validateTransId(String regNo) {
        return regNo.matches("[0-9]+");
    }

  
    public static boolean checkFullName(String strVal) {
        return strVal.matches("[a-zA-Z ]*");

    }

    public static boolean validateAddress(String address) {
        return address.matches("`");
    }

    public static boolean checkPinCode(String strVal) {
        return strVal.matches("[0-9]{6,7}");

    }

    public static boolean validatePhone(String phone) {
        return phone.matches("[0-9]*");
    }

    public static boolean checkMobileNumber(String strVal) {
        return strVal.matches("[0-9]{10}");

    }

    public static boolean checkStringDigit(String strVal) {
        return strVal.matches("[a-zA-Z0-9 ]*");

    }

    public static boolean checkUserName(String strVal) {
        return strVal.matches("[a-zA-Z0-9.]*");

    }

    public static String convertMessage(String strVal) {

        return ((strVal.substring(strVal.indexOf("Detail:"))).replace("Detail: Key ", ""));

    }

    public static boolean validateUsrRole(String strVal) {
        boolean flag = false;
        if (strVal.equalsIgnoreCase("RTO")) {
            flag = true;
        } else if (strVal.equalsIgnoreCase("POLICE")) {
            flag = true;
        } else if (strVal.equalsIgnoreCase("ADMINISTRATOR")) {
            flag = true;
        }
        return flag;
    }

    
   

 

    public static boolean isRegnNoEven(String oldRegnNo) {
        boolean even = false;
        try {
            int numericPart = Integer.parseInt(oldRegnNo.substring(oldRegnNo.length() - 1));
            if (numericPart % 2 == 0) {
                even = true;
            } else {
                even = false;
            }
        } catch (Exception e) {
            LOGGER.error(e.toString() + " " + e.getStackTrace()[0]);
        }
        return even;
    }

    

    

    public static String getDateInDD_MMM_YYYY(String strDate) throws ParseException {
        Date dt = nic.java.util.DateUtils.parseDate(strDate);
        SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
        return sd.format(dt);
    }

    public static void main(String[] args) {
        try {
            Date dt = nic.java.util.DateUtils.parseDate("22-12-2015");
            SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
            System.out.println(" : " + sd.format(dt));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static boolean isFloat(String numToCheck) {
        boolean retValue = false;

        // Check if has some value
        if (numToCheck != null && numToCheck.matches("[0-9.]+")) {
            try {
                retValue = true;
            } catch (NumberFormatException ne) {
                // Do nothing in the catch as retValue is already false.
            }
        }

        return retValue;
    }

    /*
     * Added by Aftab @ 12-07-2016
     * Financer Name validator
     */
    public static boolean isUserNameValid(String compValue) {

        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9/.,&\\- ]*");
        }
        return false;
    }

    /*
     *  chasis no, engine no and username validator
     */
    public static boolean isAlphaNumWithSpecialChar(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9/.,+&@#\\-_| ()]*");
        }
        return false;
    }

    public static boolean isDespStr(String compValue) {
        if (compValue != null && !compValue.equalsIgnoreCase("")) {
            return compValue.matches("[a-zA-Z0-9/:.,;+&?@#<>=\\-_| ()]*");
        }
        return false;
    }
}
