package com.casino.rank.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {
    public static boolean isValidAmount(double amount) {
        return amount > 0.0;
    }
}
