community-inventory
===================

This app is a prototype for implementing an API to get data from an existing web application that does basic crud operations.
It is an application where members of a community share their pantry items.
A user can add or remove item's to his Family's pantry, and also view the items in his Family's pantry.
The head of the household can see item's in other Family's pantry in their community.

Bootstrap.groovy has seed data including username/password/role information for users.

If you try to access the web application through a browser you will be required to provide credentials and login in order to do anything. 
If you try to access via the api you will be required to provide credentials also as all features require authentication and authorization.
Credentials can be provided as request parameters. It is assumed that in a production environment all access would be done over ssl.
This first implementation of using username/password for authentication was done as first step of api access of the secured application.
For future work this could be implemented using an access token issued to the user, and passed via the header.

Other future work includes caching queries that are called often and will bring back the same result each time such as the query to get the user's family.
Additionally augmenting the show action to accept json and xml as done with the list action, and additional testing of service methods, are areas I had hoped to have done. 


