package com.sb.services.utils;

//import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public class CommonUtils {

    private static final AtomicLong LAST_TIME_MS = new AtomicLong();

    private static final char[] symbols;
//    private static final Gson gson = new Gson();

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }

    private static final Random random = new Random();

    private CommonUtils() {
    }

    /**
     * Returns Data Object from String
     *
     * @param dateStr dd/MM/yyyy
     * @return
     */
    public static LocalDate getLocateDate(String dateStr, DateTimeFormatter dateTimeFormatter) {

        if (dateStr == null) {

            return LocalDate.now();
        }

        return LocalDate.parse(dateStr, dateTimeFormatter);

    }

    public static Date getDate(String dateStr, DateTimeFormatter dateTimeFormatter) {

        if (dateStr == null) {

            return new Date();
        }

        ZonedDateTime zonedDateTime = LocalDate.parse(dateStr, dateTimeFormatter)
                                               .atTime(0, 0, 0)
                                               .atZone(ZoneId.of("UTC"));

        return new Date(zonedDateTime.toInstant()
                                     .toEpochMilli());
    }

    public static String getDateStr(Date date, DateTimeFormatter dateTimeFormatter) {

        Instant instant = Instant.ofEpochMilli(date.getTime());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        return localDateTime.format(dateTimeFormatter);
    }

    public static DateTimeFormatter getDateTimeFormatter(HttpSession session) {

        String dateFormatJava = String.valueOf(session.getAttribute("dateFormatJava"));

        if (StringUtils.isBlank(dateFormatJava)) {

            dateFormatJava = "dd/MM/yyyy";
        }

        DateTimeFormatter inputDateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatJava);

        return inputDateTimeFormatter;
    }

    public static String concatenateStrings(String... strings) {

        StringBuilder stringBuffer = new StringBuilder();

        Arrays.asList(strings).forEach(stringBuffer::append);

        return stringBuffer.toString();
    }

    public static long uniqueCurrentTimeMS() {

        long now = System.currentTimeMillis();

        while (true) {

            long lastTime = LAST_TIME_MS.get();

            if (lastTime >= now)
                now = lastTime + 1;

            if (LAST_TIME_MS.compareAndSet(lastTime, now))
                return now;
        }
    }

    public static String prepareRandomString(int len) {

        char[] buf = new char[len];

        for (int idx = 0; idx < buf.length; ++idx) {

            buf[idx] = symbols[random.nextInt(symbols.length)];
        }

        return new String(buf);
    }

    public static Date getCurrentDate() {

        ZonedDateTime localDateTime = LocalDateTime.now().atZone(ZoneId.of("UTC"));

        return new Date(localDateTime.toInstant()
                                     .toEpochMilli());
    }

    public static LocalDate asLocalDate(java.util.Date date) {

        Instant instant = date.toInstant();

        LocalDate localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate();

        return localDate;
    }


}
