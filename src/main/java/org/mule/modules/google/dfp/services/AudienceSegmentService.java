/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mule.modules.google.dfp.exceptions.AudienceSegmentException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.AudienceSegment;
import com.google.api.ads.dfp.axis.v201605.AudienceSegmentPage;
import com.google.api.ads.dfp.axis.v201605.AudienceSegmentServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class AudienceSegmentService {

    private static final Logger logger = Logger
            .getLogger(AudienceSegmentService.class);

    protected AudienceSegmentServiceInterface createAudienceSegmentService(
            DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        AudienceSegmentServiceInterface audienceSegmentService = dfpServices
                .get(session, AudienceSegmentServiceInterface.class);

        return audienceSegmentService;
    }

    public List<AudienceSegment> getAudienceSegmentsByStatement(
            DfpSession session) throws AudienceSegmentException {
        try {
            AudienceSegmentServiceInterface audienceSegmentService = createAudienceSegmentService(session);

            // .where("lastModifiedDateTime > :lastModifiedDateTime AND lastModifiedDateTime <= :snapshotDateTime")
            StatementBuilder statementBuilder = new StatementBuilder()
                    .orderBy("id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            // .withBindVariableValue("lastModifiedDateTime",
            // lastModifiedDateTime)
            // .withBindVariableValue("snapshotDateTime", snapshotDateTime);

            logger.info("Retrieving the all audience segments.");

            int totalResultSetSize = 0;
            List<AudienceSegment> results = new ArrayList<AudienceSegment>();

            AudienceSegmentPage initialPage = audienceSegmentService
                    .getAudienceSegmentsByStatement(statementBuilder
                            .toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            do {
                AudienceSegmentPage page = audienceSegmentService
                        .getAudienceSegmentsByStatement(statementBuilder
                                .toStatement());

                if (page.getResults() != null) {
                    for (AudienceSegment audience : page.getResults()) {
                        results.add(audience);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Number of results found: " + totalResultSetSize);

            logger.info("Number of results retrieved: " + results.size());
            return results;

        } catch (ApiException e) {
            throw new AudienceSegmentException(e);
        } catch (RemoteException e) {
            throw new AudienceSegmentException(e);
        } catch (Exception e) {
            throw new AudienceSegmentException(e);
        }
    }

}
