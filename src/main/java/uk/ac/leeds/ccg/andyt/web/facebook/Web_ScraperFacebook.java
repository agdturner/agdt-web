/*
 * Copyright 2017 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.web.facebook;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;

public class Web_ScraperFacebook {

    public Web_ScraperFacebook() {
    }

    /** Main method
     * @param args
     * @throws java.lang.Exception */
    public static void main(String[] args) {
        new Web_ScraperFacebook().run(args);
    }

    public void run(String[] args) {
        String url = "http://www.facebook.com/search.php?init=dir&q=BNP&type=groups#!/group.php?gid=280968101201";
        getFacebookContacts(url);
    }

    public void getFacebookContacts(String url) {
        File dir;
        dir = new File(System.getProperty("user.dir"), "data");
        dir.mkdirs();
        File outputFile = new File(dir, "Test.html");
        outputFile.getParentFile().mkdirs();
        try {
            outputFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Web_ScraperFacebook.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter outputPW = Generic_IO.getPrintWriter(outputFile, false);
        File logFile = new File(dir, "Test.log");
        try {
            logFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Web_ScraperFacebook.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter logPrintWriter = Generic_IO.getPrintWriter(logFile, false);
        getHTML(url, outputPW);
        outputPW.close();
        logPrintWriter.close();
    }

    public void getHTML(
            String a_URL_String,
            PrintWriter a_PrintWriter) {
        HashSet result = new HashSet();
        URL a_URL = null;
        HttpURLConnection a_HttpURLConnection = null;
        BufferedReader a_BufferedReader = null;
        String line = null;
        try {
            a_URL = new URL(a_URL_String);
        } catch (MalformedURLException a_MalformedURLException) {
            System.exit(1);
        }
        try {
            a_HttpURLConnection = (HttpURLConnection) a_URL.openConnection();
        } catch (IOException a_IOException) {
            System.exit(1);
        }
        try {
            a_HttpURLConnection.setRequestMethod("GET");
        } catch (ProtocolException a_ProtocolException) {
            System.exit(1);
        }
        try {
            a_BufferedReader = new BufferedReader(
                    new InputStreamReader(a_HttpURLConnection.getInputStream()));
            while ((line = a_BufferedReader.readLine()) != null) {
                a_PrintWriter.write(line);
            }
        } catch (IOException a_IOException) {
            System.exit(1);
        }

    }
}
