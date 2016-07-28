Google-DFP Perform Line-Items Demo
===================================

INTRODUCTION
------------

This demo shows how you can use the perform-line-items operation, this operation has several possible actions, this demo uses Archive-line-items and Unarchive-line-items actions.

HOW TO RUN DEMO
---------------

### Prerequisites
In order to build run this demo project you'll need:

* Anypoint Studio with Mule ESB 3.6 Runtime (or higher)
* Google-DFP Connector v2.0.0 installed on Studio
* Google-DFP Credentials

### Importing the Demo

With Anypoint Studio up and running, open the Import wizard from the File menu. A pop-up wizard will offer you the chance to pick Anypoint Studio Project from External Location. On the next wizard window point Project Root to the location of the demo project directory and select the Server Runtime as Mule Server 3.6.1. (or higher). Once successfully imported the studio will automatically present the Mule Flows.

From the Package Explorer view, navigate to src/main/resources and open demo.properties. Fill in your Google-DFP credentials.

Note that the Google-DFP connector library may not have been added automatically to the project build path. To add the library, open the Mule Flow, right-click on a Google-DFP connector on the flow and click 'Add GoogleDFP libraries to the project'.

### Running the Demo
-------------------
Run the demo application and hit the following URL in your browser: http://localhost:8081. An accordian style form will show up which allows to run the different aspects of this demo.
Four steps will be shown to you in order to archive, read, unarchive and read a list of line items. You will have to introduce an order-id and all the lineitems belonging to this order will be updated/read.
