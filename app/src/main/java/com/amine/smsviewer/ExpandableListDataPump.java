package com.amine.smsviewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<Integer, List<Message>> getData() {
        HashMap<Integer, List<Message>> expandableListDetail = new HashMap<Integer, List<Message>>();

        List<Message> message1 = new ArrayList<Message>();
        message1.add(new Message("4444", Utils.formatMessageContent("extension PlateNo-76996 PlateSource-Dubai TicketNo-92440451 Fee-ADE3.38 VAT-AED0"), "02/06/2019", 3.38f));

        List<Message> message2 = new ArrayList<Message>();
        message2.add(new Message("5545", Utils.formatMessageContent("extension PlateNo-76997 PlateSource-Dubai TicketNo-92440451 Fee-ADE14 VAT-AED0"), "02/11/2022", 14.0f));

        List<Message> message3 = new ArrayList<Message>();
        message3.add(new Message("7895", Utils.formatMessageContent("extension PlateNo-76996 PlateSource-Dubai TicketNo-92440451 Fee-ADE4.5 VAT-AED0"), "10/06/2022", 4.5f));

        List<Message> message4 = new ArrayList<Message>();
        message4.add(new Message("4444", Utils.formatMessageContent("extension PlateNo-76995 PlateSource-Dubai TicketNo-92440451 Fee-ADE4.5 VAT-AED0"), "10/06/2022", 4.5f));

        int id = Utils.ID_START_FROM; //1
        expandableListDetail.put(id, message1);
        expandableListDetail.put(++id, message2);
        expandableListDetail.put(++id, message3);
        expandableListDetail.put(++id, message4);
        return expandableListDetail;
    }
}
