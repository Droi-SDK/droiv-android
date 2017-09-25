package com.alibaba.weex.commons.util;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Locale;

public class AppConfigXmlParser {
    private static String TAG = "AppConfigXmlParser";

    private AppPreferences prefs = new AppPreferences();

    public AppPreferences getPreferences() {
        return prefs;
    }

    public synchronized void parse(Context action) {
        // First checking the class namespace for config.xml
        int id = action.getResources().getIdentifier("app_config", "xml", action.getClass().getPackage()
                .getName());
        if (id == 0) {
            // If we couldn't find config.xml there, we'll look in the namespace from AndroidManifest.xml
            id = action.getResources().getIdentifier("app_config", "xml", action.getPackageName());
            if (id == 0) {
                Log.e(TAG, "res/xml/app_config.xml is missing!");
                return;
            }
        }
        parse(action.getResources().getXml(id));
    }

    public void parse(XmlPullParser xml) {
        int eventType = -1;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                handleStartTag(xml);
            } else if (eventType == XmlPullParser.END_TAG) {
                handleEndTag(xml);
            }
            try {
                eventType = xml.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleStartTag(XmlPullParser xml) {
        String strNode = xml.getName();
        if (strNode.equals("preference")) {
            String name = xml.getAttributeValue(null, "name").toLowerCase(Locale.ENGLISH);
            String value = xml.getAttributeValue(null, "value");
            prefs.set(name, value);
        }
    }

    private void handleEndTag(XmlPullParser xml) {

    }
}
