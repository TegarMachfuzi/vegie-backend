package com.vegiecrud.vegie.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.zip.Adler32;

@Component
public class CommonUtils {
    public static String generateTimestemp(Timestamp time) {
        Timestamp timestamp = new Timestamp(time.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedTimestamp = dateFormat.format(timestamp);
        return formattedTimestamp;
    }

    public static String generateVegetableId(String name){
        Adler32 adler32 = new Adler32();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String time = dateFormat.format(timestamp);

        adler32.update(name.getBytes());
        String adlerPart = Long.toHexString(adler32.getValue()), limitId = "";

        int len = adlerPart.length();
        if (len < 8) {
            int padLength = 8 -len;
            String random = getRandomNumberString().substring(0, padLength);
            limitId = time + adlerPart.concat(random);
        } else if (len >= 8) {
            limitId = time + adlerPart.substring(0, 8);
        }
        return limitId;
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        Number number = rnd.nextInt(999999) + 1;
        String numberString = String.format("%06d", number);
        if (numberString.equals("999999"))
            return "000001";
        else
            return numberString;
    }
}
