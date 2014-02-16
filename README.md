About the project

This is java implementation of unix tail, using apapche commons-io.
The logs are stored in Mongo db or can be displayed on the console.

How to configure:
Spring is used for configuration.
The configuration includes the interface for the Processing of Line and Mongo DB.

The default configuration stores the lines in the MongDB. 

To display the log lines on the console, configure the spring with processLogLineDisp:
replace ProcessLogLineMongo with ProcessLogLineDisp in spring configuration.

How to Run:

1. Configure spring-config.xml 
2. run startLogWriter: generates log lines randomly, fields are separated with ;
3. run startLogTail: processes each line of the logs as the logs change.