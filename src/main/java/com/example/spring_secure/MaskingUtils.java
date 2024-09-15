package com.example.spring_secure;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MaskingUtils {


    public String maskEmail(String eMail){
        String[] splitEmail = eMail.split("@");
        String maskedEmail;

        if(splitEmail[0].length() > 2){
            maskedEmail = splitEmail[0].charAt(0) + "*****" + splitEmail[0].charAt(splitEmail[0].length() - 1);
            return maskedEmail + "@" + splitEmail[1];
        } else {
            return "**@" + splitEmail[1];
        }





    }
}
