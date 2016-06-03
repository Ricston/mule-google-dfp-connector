/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.automation.functional;

import com.google.api.ads.dfp.axis.utils.v201605.DateTimes;
import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.DateTime;

public class TestDataBuilder {

    public static Date getCreateContractedReportStartDate() {
        return new Date(2015, 1, 1);
    }

    public static Date getCreateContractedReportEndDate() {
        return new Date(2015, 1, 31);
    }

    public static DateTime getGetAllCompaniesLastModifiedDate() {
        return DateTimes.toDateTime("2015-01-01T00:00:00", "Europe/Madrid");
    }

    public static DateTime getGetAllCompaniesSnapshotDateTime() {
        return DateTimes.toDateTime("2017-01-31T00:00:00", "Europe/Madrid");
    }
}