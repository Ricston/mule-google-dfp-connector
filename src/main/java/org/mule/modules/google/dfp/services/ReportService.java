/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.mule.modules.google.dfp.exceptions.CreateReportException;
import org.mule.modules.google.dfp.exceptions.ReportDownloadException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.ReportDownloader;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Column;
import com.google.api.ads.dfp.axis.v201605.Date;
import com.google.api.ads.dfp.axis.v201605.DateRangeType;
import com.google.api.ads.dfp.axis.v201605.Dimension;
import com.google.api.ads.dfp.axis.v201605.DimensionAttribute;
import com.google.api.ads.dfp.axis.v201605.ExportFormat;
import com.google.api.ads.dfp.axis.v201605.ReportDownloadOptions;
import com.google.api.ads.dfp.axis.v201605.ReportJob;
import com.google.api.ads.dfp.axis.v201605.ReportQuery;
import com.google.api.ads.dfp.axis.v201605.ReportQueryAdUnitView;
import com.google.api.ads.dfp.axis.v201605.ReportServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.io.Resources;

public class ReportService {

    private static final Logger logger = Logger.getLogger(ReportService.class);

    protected ReportServiceInterface createReportService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the ReportService.
        ReportServiceInterface reportService = dfpServices.get(session,
                ReportServiceInterface.class);

        return reportService;
    }

    public ReportJob createReport(DfpSession session,
            Date startDateWithTimezone, Date endDateWithTimezone, String dimensions,
            String columns, String dimensionAttributes)
            throws CreateReportException {

        logger.info("Creating a report");

        try {
            // Get the ReportService.
            ReportServiceInterface reportService = createReportService(session);

            // Create report query.
            ReportQuery reportQuery = new ReportQuery();
            reportQuery.setDimensions(transformStringToDimensions(dimensions));
            reportQuery.setAdUnitView(ReportQueryAdUnitView.TOP_LEVEL);
            reportQuery.setColumns(transformStringToColumns(columns));
            reportQuery.setDimensionAttributes(transformStringToDimensionAttributes(dimensionAttributes));

            // Create report date range
            reportQuery.setDateRangeType(DateRangeType.CUSTOM_DATE);

            reportQuery.setStartDate(startDateWithTimezone);
            reportQuery.setEndDate(endDateWithTimezone);

            // Create report job.
            ReportJob reportJob = new ReportJob();
            reportJob.setReportQuery(reportQuery);

            // Run report job.
            return reportService.runReportJob(reportJob);

        } catch (ApiException e) {
            throw new CreateReportException(e);
        } catch (RemoteException e) {
            throw new CreateReportException(e);
        } catch (IllegalArgumentException e) {
            throw new CreateReportException(e);
        } catch (Exception e) {
            throw new CreateReportException(e);
        }

    }

    private Dimension[] transformStringToDimensions(String items) throws IllegalArgumentException {
        Dimension[] dimArray;
        if (items == null) {
            dimArray = null;
        } else {
            String[] dimensions = items.split(",");
            dimArray = new Dimension[dimensions.length];
            int i = 0;
            try {
                for (String dim : dimensions) {
                    dimArray[i] = Dimension.fromValue(dim.toUpperCase()
                            .trim());
                    i++;
                }

            } catch (IllegalArgumentException e) {
                logger.info("Re-throwing IllegalArgumentException...", e);
                String failString = dimensions[i];
                throw new IllegalArgumentException("Unknown Dimension: " + failString);
            }
        }
        return dimArray;
    }

    private DimensionAttribute[] transformStringToDimensionAttributes(String items) throws IllegalArgumentException {
        DimensionAttribute[] dimArray;
        if (items == null) {
            dimArray = null;
        } else {
            String[] dimensionsAtt = items.split(",");
            dimArray = new DimensionAttribute[dimensionsAtt.length];
            int i = 0;
            try {
                for (String dim : dimensionsAtt) {
                    dimArray[i] = DimensionAttribute.fromValue(dim.toUpperCase()
                            .trim());
                    i++;
                }

            } catch (IllegalArgumentException e) {
                logger.info("Re-throwing IllegalArgumentException...", e);
                String failString = dimensionsAtt[i];
                throw new IllegalArgumentException("Unknown DimensionAttribute: " + failString);
            }
        }

        return dimArray;
    }

    private Column[] transformStringToColumns(String items) throws IllegalArgumentException {
        Column[] dimArray;
        if (items == null) {
            dimArray = null;
        } else {
            String[] dimensionsAtt = items.split(",");
            dimArray = new Column[dimensionsAtt.length];
            int i = 0;
            try {
                for (String dim : dimensionsAtt) {
                    dimArray[i] = Column.fromValue(dim.toUpperCase()
                            .trim());
                    i++;
                }

            } catch (IllegalArgumentException e) {
                logger.info("Re-throwing IllegalArgumentException...", e);
                String failString = dimensionsAtt[i];
                throw new IllegalArgumentException("Unknown Column: " + failString);
            }
        }
        return dimArray;
    }

    public InputStream downloadReport(DfpSession session, ReportJob reportJob)
            throws ReportDownloadException {
        try {
            // Get the ReportService.
            ReportServiceInterface reportService = createReportService(session);

            // Create report downloader.
            final ReportDownloader reportDownloader = new ReportDownloader(
                    reportService, reportJob.getId());

            // Wait for the report to be ready.
            boolean success = reportDownloader.waitForReportReady();

            if (!success) {
                throw new ReportDownloadException(
                        new Throwable(
                                "Cannot download report. Google DFP failed to return the report."));
            }

            ReportDownloadOptions options = new ReportDownloadOptions();
            options.setExportFormat(ExportFormat.CSV_DUMP);
            options.setUseGzipCompression(true);
            URL url = reportDownloader.getDownloadUrl(options);

            return Resources.asByteSource(url)
                    .openStream();

        } catch (ApiException e) {
            logger.error("API Exception", e);
            throw new ReportDownloadException(e);
        } catch (RemoteException e) {
            logger.error("Remote Exception", e);
            throw new ReportDownloadException(e);
        } catch (IOException e) {
            logger.error("IO Exception", e);
            throw new ReportDownloadException(e);
        } catch (Exception e) {
            logger.error("Exception", e);
            throw new ReportDownloadException(e);
        }
    }

}
