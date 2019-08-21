package com.uv.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaDates {
    Pattern RFC3339_STR_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}[Tt]\\d{2}:\\d{2}:\\d{2})(\\.?\\d*)(Z|[\\+\\-]\\d{2}:\\d{2})");
    DateFormat INTERNAL_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS XXX");

    public String reformRFCDateString(String dateStr) {
        String result = null;
        Matcher matcher = RFC3339_STR_PATTERN.matcher(dateStr);
        if (matcher.matches()) {
            MatchResult matchResult = matcher.toMatchResult();
            if (matchResult.groupCount() != 4) {
                 result = matchResult.group(1).replaceAll("t|T", " ") + " "
                         + getMilliSecs(matchResult.group(2)) + " "
                         + ("Z".equalsIgnoreCase(matchResult.group(3)) ? "+00:00" : matchResult.group(3));
                 System.out.println(dateStr + " >> " + result);
            }
        }
        return result;
    }

    public Date getDateFromRFC3339Str(String dateStr) {
        Date resultDate = null;
        try {
            String result = reformRFCDateString(dateStr);
            if (result != null) resultDate = INTERNAL_DATE.parse(result);
        } catch (ParseException e) {
             e.printStackTrace();
        }
        return resultDate;
    }

    public String getInternalDateStrFromRFC3339Str(String dateStr) {
        return INTERNAL_DATE.format(getDateFromRFC3339Str(dateStr));
    }

    protected String getMilliSecs(String secFraction) {
        String millis = (secFraction == null || secFraction.isEmpty())? "000" : secFraction.substring(1) + "000";
        return millis.substring(0,3);
    }

    public void printTimes() {
        System.out.println((""+System.currentTimeMillis()).length());
        System.out.println((""+System.nanoTime()).length());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
    }

}
