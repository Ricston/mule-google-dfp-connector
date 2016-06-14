/**
 * (c) 2003-2016 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.google.dfp.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mule.modules.google.dfp.exceptions.CreateFailedException;
import org.mule.modules.google.dfp.exceptions.GetAllContactsException;
import org.mule.modules.google.dfp.exceptions.GetContactByIdException;
import org.mule.modules.google.dfp.exceptions.GetContactByNameException;
import org.mule.modules.google.dfp.exceptions.UpdateFailedException;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201605.StatementBuilder;
import com.google.api.ads.dfp.axis.v201605.ApiException;
import com.google.api.ads.dfp.axis.v201605.Contact;
import com.google.api.ads.dfp.axis.v201605.ContactPage;
import com.google.api.ads.dfp.axis.v201605.ContactServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;

public class ContactService {

    private static final Logger logger = Logger.getLogger(ContactService.class);

    protected ContactServiceInterface createContactService(DfpSession session) {
        DfpServices dfpServices = new DfpServices();

        // Get the CustomField service.
        ContactServiceInterface contactService = dfpServices.get(session,
                ContactServiceInterface.class);

        return contactService;
    }

    public List<Contact> getContactsByStatement(DfpSession session)
            throws GetAllContactsException {
        try {

            // Get the ContactService.
            ContactServiceInterface contactService = createContactService(session);

            // Create a statement to get all contacts.
            StatementBuilder statementBuilder = new StatementBuilder().orderBy(
                    "id ASC")
                    .limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

            // Default for total result set size.
            int totalResultSetSize = 0;
            List<Contact> results = new ArrayList<Contact>();

            ContactPage initialPage = contactService.getContactsByStatement(statementBuilder
                    .toStatement());
            totalResultSetSize = initialPage.getTotalResultSetSize();

            logger.info("Getting all contacts.");
            do {
                // Get contacts by statement.
                ContactPage page = contactService.getContactsByStatement(statementBuilder
                        .toStatement());

                if (page.getResults() != null) {
                    for (Contact contact : page.getResults()) {
                        results.add(contact);
                    }
                }

                statementBuilder
                        .increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
            } while (statementBuilder.getOffset() < totalResultSetSize);

            logger.info("Retrieved " + totalResultSetSize + " contacts.");

            return results;
        } catch (RemoteException e) {
            throw new GetAllContactsException(e);
        }
    }

    public Contact createContact(DfpSession session, Contact contact) throws CreateFailedException {
        try {
            // Get the ContactService.
            ContactServiceInterface contactService = createContactService(session);

            Contact[] createdContacts = contactService.createContacts(new Contact[] { contact
            });

            Contact createdContact = createdContacts[0];
            logger.info(String
                    .format("A Contact with ID \"%d\", name \"%s\", company \"%d\", and email \"%s\" was created.\"%n\"",
                            createdContact.getId(), createdContact.getName(),
                            createdContact.getCompanyId(), createdContact.getEmail()));

            return createdContact;

        } catch (ApiException e) {
            throw new CreateFailedException(e);
        } catch (RemoteException e) {
            throw new CreateFailedException(e);
        } catch (Exception e) {
            throw new CreateFailedException(e);
        }

    }

    public Contact updateContact(DfpSession session, Contact contact) throws UpdateFailedException {
        try {
            // Get the ContactService.
            ContactServiceInterface contactService = createContactService(session);

            Contact[] updatedContacts = contactService.updateContacts(new Contact[] { contact
            });

            Contact updatedContact = updatedContacts[0];
            logger.info(String
                    .format("A Contact with ID \"%d\", name \"%s\", company \"%d\", and email \"%s\" was updated.\"%n\"",
                            updatedContact.getId(), updatedContact.getName(),
                            updatedContact.getCompanyId(), updatedContact.getEmail()));

            return updatedContact;

        } catch (ApiException e) {
            throw new UpdateFailedException(e);
        } catch (RemoteException e) {
            throw new UpdateFailedException(e);
        } catch (Exception e) {
            throw new UpdateFailedException(e);
        }

    }

    public Contact getContactById(DfpSession session, Long contactId)
            throws GetContactByIdException {
        try {

            // Get the ContactService.
            ContactServiceInterface contactService = createContactService(session);

            // Create a statement to get all companies.
            StatementBuilder statementBuilder = new StatementBuilder().where(
                    "id = :id")
                    .withBindVariableValue("id", contactId);

            Contact contact = null;

            // Get companies by statement.
            ContactPage page = contactService
                    .getContactsByStatement(statementBuilder.toStatement());

            if (page.getResults() != null) {
                contact = page.getResults(0);
                logger.info("Contact with ID " + contact.getId() + " , name "
                        + contact.getName() + " email " + contact.getEmail()
                        + " was found.");
            }

            return contact;
        } catch (ApiException e) {
            throw new GetContactByIdException(e);
        } catch (RemoteException e) {
            throw new GetContactByIdException(e);
        } catch (Exception e) {
            throw new GetContactByIdException(e);
        }
    }

    public Contact getContactByName(DfpSession session, String name)
            throws GetContactByNameException {
        try {

            // Get the ContactService.
            ContactServiceInterface contactService = createContactService(session);

            // Create a statement to get all companies.
            StatementBuilder statementBuilder = new StatementBuilder().where(
                    "name = :name")
                    .withBindVariableValue("name", name);

            Contact contact = null;

            // Get companies by statement.
            ContactPage page = contactService
                    .getContactsByStatement(statementBuilder.toStatement());

            if (page.getResults() != null) {
                contact = page.getResults(0);
                logger.info("Contact with ID " + contact.getId() + " , name "
                        + contact.getName() + " email " + contact.getEmail()
                        + " was found.");
            }

            return contact;
        } catch (ApiException e) {
            throw new GetContactByNameException(e);
        } catch (RemoteException e) {
            throw new GetContactByNameException(e);
        } catch (Exception e) {
            throw new GetContactByNameException(e);
        }
    }

}
