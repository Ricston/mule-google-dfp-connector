Google-DFP Create Report Demo
===============================

INTRODUCTION
------------

This demo shows how to create a report in Google DFP.

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
Run the demo application and hit the following URL in your browser: http://localhost:8081. 

On 'createReport' form you will see five fields: 'Dimensions', 'Dimension Attributes', 'Columns', 'Start Date' and 'End Date'.
They have an initial value ('Dimension Attributes' is blank because it is optional) using these values the report should be created properly,
you can also fill in your own values in these fields but remember that all Dimensions/columns are not compatible.

'Dimensions', 'Dimension Attributes' and 'Columns' are lists of comma delimited items.

Once you created the Report you have the possibility of downloading it, on 'Download Report' form you will see  the 'ID Report' obtained from the previous step, click on 'Download' button and the file will be downloaded at  '/output/report.gz' (under the root directory of your mule project, this is configurable in the demo.properties file) , it is a compressed .CSV file
