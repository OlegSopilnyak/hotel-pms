# hotel-pms
PoC for hotel rooms booking process.
Micro-service demonstrate a facility to book room
for guest to particular period of time. All data
will be stored to external source for future processing.
Business Model contains hotels, rooms, services provides by hotel,
guests, hotel staff etc. For this PoC used H2 in-memory RDBMS.<br/>
To build project execute command:<br/>
**$gradle build**<br/><br/>
After it whole project will be built and all tests will be passed.<br/>
In folder named **service-runner/build/libs** will be placed result of build **test-runner-0.3.0.jar**<br/>
archive which consists all necessary classes to run micro-service by command:<br/><br/>
**$java -jar service-runner/build/libs/test-runner-0.3.0.jar** <br/> 
All log information will output to console which maybe changed in file **service-runner/src/main/resources/logback.xml**
and<br/>after rebuild take power for micro-service.There is feature to deploy service to **Docker** system.
<br/>For this purpose just execute command:<br/><br/>
**$gradle service-runner:buildDocker**<br/>
After it Docker image will be created and moved to Docker as image **sopilnyak/test-1:test**<br/>
to run it just execute:<br/><br/>
$ **docker run -i -t -p 80:8080 sopilnyak/test-1:test**<br/>
Built image will start in the Docker container and expose port 80 as port for communication.<br/>
To get access to database used by service point your browser to **docker-host/console** and adjust connection 
with in-memory database, entering: **jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false** to **JDBC URL** field.<br/>
<br/>**Endpoints served by micro-service:**<br>
`POST /api/rooms-reservation/create` - creates unconfirmed reservation request;<br>
`POST /api/rooms-reservation/confirm` - confirm created reservation;<br/>
`POST /api/rooms-reservation/update` - update confirmed reservation;<br/>
`GET /api/rooms-reservation/agreement/{id}` - get hotel agreement by id <br/>
`DELETE /api/rooms-reservation/agreement/{id}` - delete reservation by id<br/>
`GET /api/rooms-reservation/reserved/from/{from}/till/{till}` - where parameters look<br/> like **`yyyy-MM-dd`**,
to get reservations by period of dates.<br/>
 
<br/>Micro-service has 5 modules:<br/>
**common:model** - the business and transport model for business-logic<br/>
**common:service** - definition and realization of core services<br/>
**persistence** - persistence layer of service<br/>
**rest** - presentation layer of service (REST controllers)<br/>
**service-runner** - module to run service and Docker's stuff.
<br/><br/><br/>
If you have any question don't hesitate to ask my directly via e-mail<br/>**oleg.sopilnyak@gmail.com** or<br>Skype **oleg.sopilnyak**