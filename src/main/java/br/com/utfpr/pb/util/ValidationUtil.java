package br.com.utfpr.pb.util;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

/**
 * Created by Joao on 12/11/2016.
 */
public class ValidationUtil {
    private static ValidationUtil ourInstance = new ValidationUtil();

    public static ValidationUtil getInstance() {
        return ourInstance;
    }

    private ValidationUtil() {
    }

    public String getMessage(ConstraintViolationException e) {
        StringBuilder builder = new StringBuilder();
        builder.append("Verifique os campos obrigatórios.\n\n");

        Iterator<ConstraintViolation<?>> iterator = e.getConstraintViolations().iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<?> next = iterator.next();
            builder.append("- ");
            builder.append(next.getMessage());
            builder.append("\n");
        }

        return builder.toString();
    }
}
