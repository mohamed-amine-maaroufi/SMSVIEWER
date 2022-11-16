package com.amine.smsviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static int ID_START_FROM = 1;
    public static String substringFee(String messageContent){
        try {
            return messageContent.substring(messageContent.indexOf("Fee-ADE") + 7, messageContent.lastIndexOf("\n"));
        } catch (Exception e) {
            System.out.println("Cannot subst fee");
            return "0.0";
        }

    }

    public static String formatMessageContent(String messageContent){

        boolean isFound = messageContent.indexOf("Fee-ADE") !=-1? true: false;
        if(isFound){
            return messageContent.replace(" ", "\n");
        }

        return messageContent;
    }

    public static float strToFloat(String str)
    {
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to float.");
            return 0.0F;
        }

    }

    public static float countTotalPrice (HashMap<Integer, List<Message>> listOfMessages){

        float totalPrice = 0.0f;

        for (Map.Entry<Integer, List<Message>> set :
                listOfMessages.entrySet()) {
            totalPrice+= listOfMessages.get(set.getKey()).get(0).getFee();
        }

        return totalPrice;
    }
}
