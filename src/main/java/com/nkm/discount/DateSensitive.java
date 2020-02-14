package com.nkm.discount;

import java.time.LocalDate;

public interface DateSensitive {
    boolean isValid(LocalDate date);
}
