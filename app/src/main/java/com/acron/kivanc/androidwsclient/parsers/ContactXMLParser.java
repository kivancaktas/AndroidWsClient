package com.acron.kivanc.androidwsclient.parsers;

import com.acron.kivanc.androidwsclient.model.Contacts;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KIVANC on 15.5.2015.
 */
public class ContactXMLParser {

    public static List<Contacts> parseFeed(String content) {
        try {
            boolean intDataItemTag = false;
            String currentTagName = "";
            Contacts contacts = null;
            List<Contacts> contactsList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch(eventType){
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("ZPERSONEL_S")) {
                            intDataItemTag = true;
                            contacts = new Contacts();
                            contactsList.add(contacts);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("ZPERSONEL_S")) {
                            intDataItemTag = false;
                        }
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (intDataItemTag && contacts != null) {
                            switch (currentTagName) {
                                case "BNAME":
                                    contacts.setBNAME(parser.getText());
                                    break;
                                case "KISAKOD":
                                    contacts.setKISAKOD(parser.getText());
                                    break;
                                case "TELEFON":
                                    contacts.setTELEFON(parser.getText());
                                    break;
                                case "SAHIS_TEL":
                                    contacts.setSAHIS_TEL(parser.getText());
                                    break;
                                case "OFIS_DAHILI":
                                    contacts.setOFIS_DAHILI(parser.getText());
                                    break;
                                case "EMAIL":
                                    contacts.setEMAIL(parser.getText());
                                    break;
                                case "RESIM_URL":
                                    contacts.setRESIM_URL(parser.getText());
                                    break;
                                case "NAME_FIRST":
                                    contacts.setNAME_FIRST(parser.getText());
                                    break;
                                case "NAME_LAST":
                                    contacts.setNAME_LAST(parser.getText());
                                    break;
                                case "ENAME":
                                    contacts.setENAME(parser.getText());
                                    break;
                                case "MODULLER":
                                    contacts.setMODULLER(parser.getText());
                                    break;
                                case "ISGIRIS":
                                    contacts.setISGIRIS(parser.getText());
                                    break;
                                case "DGRTRH":
                                    contacts.setDGTRH(parser.getText());
                                    break;
                                case "INDEX":
                                    contacts.setINDEX(parser.getText());
                                    break;
                                default:
                                    break;
                            }

                        }
                        break;
                }
                eventType = parser.next();
            }
            return contactsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
