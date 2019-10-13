# Fuber
Fuber code

Assumptions made :
1. since the UI / DB was not a mandatory portion, i have skipped the same. I have dealt the data within the Java side itself using static block of code for data initialization and collections to store / update them.

Flow of the code:
Once the project is executed, the services starts with a GET method call by the customer to find cars that are near him/her(3 kms radius)
Customer can also give the preferance for pinkCab as true or false. The preference along with the GPS coordinates of the customer is sent as inputs for service. The service will return the details of the nearest cab. If pinkCab is set as true, then, only cars with pink is checked for their availability of the trip. If no such prefernace is given, all types of cabs are checked in the vicinity.The scan might stumble upon a pink car and allot the same if the same is the nearest. If no cabs are found, then a msg is displated in the response as No cabs available
sample URI :
http://localhost:8080/cabs-available/false?latitude=12.885105&longitude=77.645504

The customer can book this cab by calling a POST method service which will have customer details in request body and cabID of nearest cab in the requestHeader.This will result in updating the customer location to the cab and also block the availability of this particular cab untill the trip is over. This will also set the Start point and Endpoint of the trip which are given as part of JSON in request body. After successful setting of the attributes, the response will have the distance to the customer from the current location of the cab.
Before initating all these, the availability of teh cab is again checked as to avoid the chance of re booking a cab that is already booked by another customer. if the cab is still available, availability is set as false(to avoid poping up in the scan by another customer) and  the bookingstatus is set as true. 
Sample URI : http://localhost:8080/book-cab

Once the cab arrives at the customer location, the driver will have to start the trip to initialize the trip data. This is done via a POST method service that will have the cabID as the request header parameter. This service will enable cab driver to set the start time for the trip. This will also update current location of the cab as the location of the customer.
Sample URI : http://localhost:8080/start-trip

The cab arrives at the destination.To initiate the billing procedures, driver has to call the service end-trip.
Sampel URI : http://localhost:8080/end-trip
 This will  initiate the calculation of total distance travelled(by Pythagoreas theorem) and the total fare for the trip. The end time is noted  when the service is called and the total time taken for trip is calculated. Once fare, distane and total time is calculated, then the cab is released from booking. Here the availability attribute is set to true and the booking status attribute is set to false.This will also update the destination location as the current location for the cab.

