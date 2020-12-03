Project Description:
===================

The component 'Alert' subscribes via JMS for the events:
--> "info",
--> "warning", and
--> "exception".

These events are read from a database and are made ready for "consumption" from the Topic-component Messanger, which is triggered by the Timer component. It is the Timer component, which generates the events above on a regular basis (interval), whose value is also read from the database.

Upon occuring of one of the events, the 'Alert' component reads from the database the (types of) actions, which are associated with that event,
and which are to be performed by dynamically calling the corresponding implementation.

Depending on the configuration, the types of actions (one ore more) could be one of the:
--> Sending an E-Mail
--> Writting a log entry in the database
--> Writting a log entry into a file.


Te project described  here implements the above requirements

 ____________
|            |   publish event	   |-------------|  subscribe for events    |-------------|
|  Timer     | ------------------> | Messenger   |<-------------------------| Alert       |
|____________|					   |_____________|                          |_____________|
    -.																			   -.
	  -.																	     -.
		---------------------------> ( Database ) <-------------------------------