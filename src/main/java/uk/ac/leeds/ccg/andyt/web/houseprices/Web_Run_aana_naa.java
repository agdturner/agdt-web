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
package uk.ac.leeds.ccg.andyt.web.houseprices;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import uk.ac.leeds.ccg.andyt.data.postcode.Generic_UKPostcode_Handler;

/**
 * Class for formatting postcodes of the aana_naa format.
 */
public class Web_Run_aana_naa extends Web_AbstractRun {

    /**
     * A reference to ZooplaHousepriceScraper.getABEHMNPRVWXY() for convenience.
     */
    private TreeSet<String> _ABEHMNPRVWXY;
            
    /**
     * @param tZooplaHousepriceScraper The Web_ZooplaHousepriceScraper
     * @param restart This is expected to be true if restarting a partially
     * completed run and false otherwise.
     */
    public Web_Run_aana_naa(
            Web_ZooplaHousepriceScraper tZooplaHousepriceScraper,
            boolean restart) {
        init(tZooplaHousepriceScraper, restart);
        _ABEHMNPRVWXY = Generic_UKPostcode_Handler.get_ABEHMNPRVWXY();
    }

    @Override
    public void run() {
        System.out.println("running");
        if (restart == false) {
            formatNew();
        } else {
            if (firstpartPostcode.length() != 2) {
                throw new NotImplementedException();
            }
            // Initialise output files
            String filenamepart = "_Houseprices_" + firstpartPostcode + "na";
            String[] postcodeForRestart = getPostcodeForRestart(
                    getType(), filenamepart);
            if (postcodeForRestart == null) {
                formatNew();
            } else {
                if (postcodeForRestart[0] != null) {
                    int n0Restart = Integer.valueOf(postcodeForRestart[0].substring(3, 4));
                    String a2Restart = postcodeForRestart[0].substring(4, 5);
                    // Initialise output files
                    initialiseOutputs(getType(), filenamepart);
                    // Process
                    Iterator<String> _ABEHMNPRVWXY_Iterator;
                    Iterator<String> _NAA_Iterator;
                    int n0;
                    int _int0;
                    String _NAAString;
                    int counter = 0;
                    int numberOfHousepriceRecords = 0;
                    int numberOfPostcodesWithHousepriceRecords = 0;
                    String a0 = firstpartPostcode.substring(0, 1);
                    String a1 = firstpartPostcode.substring(1, 2);
                    String a2;
                    boolean n0Restarter = false;
                    boolean a2Restarter = false;
                    boolean secondPartPostcodeRestarter = false;
                    for (n0 = 0; n0 < 10; n0++) {
                        if (!n0Restarter) {
                            if (n0 == n0Restart) {
                                n0Restarter = true;
                            }
                        } else {
                            _ABEHMNPRVWXY_Iterator = _ABEHMNPRVWXY.iterator();
                            while (_ABEHMNPRVWXY_Iterator.hasNext()) {
                                a2 = (String) _ABEHMNPRVWXY_Iterator.next();
                                if (!a2Restarter) {
                                    if (a2.equalsIgnoreCase(a2Restart)) {
                                        a2Restarter = true;
                                    }
                                } else {
                                    String completeFirstPartPostcode = a0 + a1
                                            + Integer.toString(n0) + a2;
                                    String aURLString0 = url + completeFirstPartPostcode;
                                    checkRequestRate();
                                    if (ZooplaHousepriceScraper.isReturningOutcode(completeFirstPartPostcode, aURLString0)) {
                                        _NAA_Iterator = _NAA.iterator();
                                        while (_NAA_Iterator.hasNext()) {
                                            _NAAString = (String) _NAA_Iterator.next();
                                            if (!secondPartPostcodeRestarter) {
                                                if (_NAAString.equalsIgnoreCase(postcodeForRestart[1])) {
                                                    secondPartPostcodeRestarter = true;
                                                }
                                            } else {
                                                String aURLString = aURLString0 + "-" + _NAAString;
                                                _int0 = ZooplaHousepriceScraper.writeHouseprices(
                                                        outPR,
                                                        logPR,
                                                        sharedLogPR,
                                                        aURLString,
                                                        firstpartPostcode + Integer.toString(n0) + a2,
                                                        _NAAString,
                                                        addressAdditionalPropertyDetails);
                                                counter++;
                                                numberOfHousepriceRecords += _int0;
                                                if (_int0 > 0) {
                                                    numberOfPostcodesWithHousepriceRecords++;
                                                }
                                            }
                                        }
                                    } else {
                                        Web_ZooplaHousepriceScraper.updateLog(
                                                logPR,
                                                sharedLogPR,
                                                completeFirstPartPostcode);
                                    }
                                }
                            }
                        }
//                        System.out.println(getReportString(
//                                counter,
//                                numberOfHousepriceRecords,
//                                numberOfPostcodesWithHousepriceRecords));
                    }
                    finalise(counter, numberOfHousepriceRecords, numberOfPostcodesWithHousepriceRecords);
                }
            }
        }
    }

    private void formatNew() {
        // Initialise output files
        String filenamepart = "_Houseprices_" + firstpartPostcode + "na";
        initialiseOutputs("aana", filenamepart);
        // Process
        Iterator<String> _ABEHMNPRVWXY_Iterator;
        Iterator<String> _NAA_Iterator;
        int n0;
        int _int0;
        String _NAAString;
        int counter = 0;
        int numberOfHousepriceRecords = 0;
        int numberOfPostcodesWithHousepriceRecords = 0;
        String a0 = firstpartPostcode.substring(0, 1);
        String a1 = firstpartPostcode.substring(1, 2);
        String a2;
        for (n0 = 0; n0 < 10; n0++) {
            _ABEHMNPRVWXY_Iterator = _ABEHMNPRVWXY.iterator();
            while (_ABEHMNPRVWXY_Iterator.hasNext()) {
                a2 = (String) _ABEHMNPRVWXY_Iterator.next();
                String completeFirstPartPostcode = a0 + a1
                        + Integer.toString(n0) + a2;
                String aURLString0 = url + completeFirstPartPostcode;
                checkRequestRate();
                if (ZooplaHousepriceScraper.isReturningOutcode(completeFirstPartPostcode, aURLString0)) {
                    _NAA_Iterator = _NAA.iterator();
                    while (_NAA_Iterator.hasNext()) {
                        _NAAString = (String) _NAA_Iterator.next();
                        String aURLString = aURLString0 + "-" + _NAAString;
                        _int0 = ZooplaHousepriceScraper.writeHouseprices(
                                outPR,
                                logPR,
                                sharedLogPR,
                                aURLString,
                                firstpartPostcode + Integer.toString(n0) + a2,
                                _NAAString,
                                addressAdditionalPropertyDetails);
                        counter++;
                        numberOfHousepriceRecords += _int0;
                        if (_int0 > 0) {
                            numberOfPostcodesWithHousepriceRecords++;
                        }
                    }
                } else {
                    Web_ZooplaHousepriceScraper.updateLog(
                            logPR,
                            sharedLogPR,
                            completeFirstPartPostcode);
                }
            }
//            System.out.println(getReportString(
//                    counter,
//                    numberOfHousepriceRecords,
//                    numberOfPostcodesWithHousepriceRecords));
        }
        finalise(counter, numberOfHousepriceRecords, numberOfPostcodesWithHousepriceRecords);
    }
}
