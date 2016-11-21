package br.com.utfpr.pb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joao on 18/11/2016.
 */
public class DateUtil {
    private static DateUtil ourInstance = new DateUtil();

    public static DateUtil getInstance() {
        return ourInstance;
    }

    private DateUtil() {
    }

    public String format(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
