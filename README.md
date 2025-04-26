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

GET: /light/on param: userId, gardenName, minute: so phut den hoat dong

GET: /light/off param: userId, gardenName

# pump

endpoint: 

GET: /pump/on param: userId, gardenName

GET: /pump/off param: userId, gardenName

# smart-controller

GET: /smart-controller/start param: userId, gardenName

GET: /smart-controller/stop param: userId, gardenName

# system crash - solve problem

still developer, 100% done soon
