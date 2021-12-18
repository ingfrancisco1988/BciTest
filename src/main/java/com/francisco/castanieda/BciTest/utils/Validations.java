package com.francisco.castanieda.BciTest.utils;

import static com.francisco.castanieda.BciTest.model.constants.CustomConstants.REG_EXP_FOR_EMAIL;
import static com.francisco.castanieda.BciTest.model.constants.CustomConstants.REG_EXP_FOR_PASSWORD;

public class Validations {

    public static boolean paswordValidation (String pass) {
        return pass.matches(REG_EXP_FOR_PASSWORD);
    }

    public static boolean emailValidation (String email){
        return  email.matches(REG_EXP_FOR_EMAIL);
    }
}
