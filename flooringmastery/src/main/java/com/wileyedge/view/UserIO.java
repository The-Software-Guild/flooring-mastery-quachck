package com.wileyedge.view;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserIO {

    void print(String message);

    int readInt(String prompt);
    
    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt);

    LocalDate readLocalDate(String prompt);

    String readString(String prompt);
}
