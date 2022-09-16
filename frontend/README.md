# OrakelQueueSystem - Client Application

This is the React.js client application for the Orakel Queue System.

Due to restrictions applied by the corona pandemic at Oslomet, the Oracle service has figured we need a propper queue system so people don't line up in a big heard by our workstation while waiting for their turn to talk to one of our employees. During the pandemic we have also started doing Discord consultations in addition to the physical consulatations at P35, and a queue system to help us figure out who we should prioritize next will greatly improve our workflow on hectic days, making sure no students feel like they have been forgotten or treated unfairly.

Planned features and functionalities of the base application:
 - An index page with the queue displayed to all users.
 - The ability for a non-logged in user to add themselves to the queue with the following datapoints:
   - Their first name.
   - What subject they need help with.
   - Whether they want physical or digital consultation.
   - What year of studies they belong to.
 - Logged in Orakel employees shal be able to edit the queue (remove entities and change the order).
 - Logged in Orakel employees shal be able to pull all queue data and download it in CSV format.
 
The last feature involving pulling the data is for us to be able to better determine when we have the most traffic, something we have never been able to accurately measure before.


### The API being called from this client application is found [here](https://github.com/FredrikPedersen/Orakel_Queue_System_Server)
