package br.com.utfpr.pb.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Joao on 12/11/2016.
 */
public class DoubleUtil {
    private static DoubleUtil ourInstance = new DoubleUtil();

    public static DoubleUtil getInstance() {
        return ourInstance;
    }

    private DoubleUtil() {
    }

    public Double getDouble(String valor) {
        if (valor.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(valor.replace(".", "").replace(",", "."));
    }

    public String format(Double valor) {
        BigDecimal valorFormatado = new BigDecimal(valor);
        DecimalFormat format = new DecimalFormat("#,###,##0.00");
        return format.format(valorFormatado);
    }
}
