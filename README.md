# java + mongoDB

file app.properties:

spring.application.name=watering

spring.data.mongodb.uri=

spring.data.mongodb.database=

# api document

# State of all

GET: /state

parram: page (default 0), size (default 10)

# light

endpoint: 

GET: /light/on

GET: /light/off

# pump

endpoint: 

GET: /pump/on

GET: /pump/off

# smart-controller

GET: /smart-controller/start

GET: /smart-controller/stop

# system crash - solve problem

still developer, 100% done soon
