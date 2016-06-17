Google-DFP Company Service Demo
===============================

INTRODUCTION
------------

This demo shows how you can use some operations to create/update a Company and a Contact in Google DFP.

HOW TO RUN DEMO
---------------

### Prerequisites
In order to build run this demo project you'll need:

* Anypoint Studio with Mule ESB 3.6 Runtime
* Google-DFP Connector v2.0.0 installed on Studio
* Google-DFP Credentials

### Importing the Demo

With Anypoint Studio up and running, open the Import wizard from the File menu. A pop-up wizard will offer you the chance to pick Anypoint Studio Project from External Location. On the next wizard window point Project Root to the location of the demo project directory and select the Server Runtime as Mule Server 3.6.1. Once successfully imported the studio will automatically present the Mule Flows.

From the Package Explorer view, navigate to src/main/resources and open demo.properties. Fill in your Google-DFP credentials.

Note that the Google-DFP connector library may not have been added automatically to the project build path. To add the library, open the Mule Flow, right-click on a Google-DFP connector on the flow and click 'Add GoogleDFP libraries to the project'.

### Running the Demo
-------------------
Run the demo application and hit the following URL in your browser: http://localhost:8081. An accordian style form will show up which allows to run the different aspects of this demo.
First you will have to create a Company, the next step will be to update this Company (changing its name), once this has been done you will be able to create a Contact that will be associated
to the previously created Company and finally you will be able to update this Contact (adding a Title).