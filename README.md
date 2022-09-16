# Orakel Queue System

Due to restrictions applied by the corona pandemic at Oslomet, the Oracle service has figured we need a propper queue system so people don't line up in a big heard by our workstation while waiting for their turn to talk to one of our employees. During the pandemic we have also started doing Discord consultations in addition to the physical consulatations at P35, and a queue system to help us figure out who we should prioritize next will greatly improve our workflow on hectic days, making sure no students feel like they have been forgotten or treated unfairly.

The application also let's us have concrete data on how much work we do, which was nigh impossible to measure before.

## Frontend

The frontend has a couple of outdated dependencies which has proven to be hard to update without breaking the entire application and warranting huge re-writes.
For the moment, the application has to be installed using the lagacy-peer-deps argument to prevent problems with an older Typescript-dependency from react-scripts.

````Bash
npm install --legacy-peer-deps
````

## Backend
