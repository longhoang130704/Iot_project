# java + mongoDB

file app.properties:

spring.application.name=watering

spring.data.mongodb.uri=

spring.data.mongodb.database=

# api document

# Action

endpoint

GET: /action

GET: /action/{gardenName}

# State of all

GET: /last-state

GET: /state

GET: /state/last-7-days

parram: page (default 0), size (default 10)

# light

endpoint: 

GET: /light/on param: userId, gardenName

GET: /light/off param: userId, gardenName

# pump

endpoint: 

GET: /pump/on param: userId, gardenName

GET: /pump/off param: userId, gardenName

# smart-controller

GET: /smart-controller/start param: userId, gardenName

GET: /smart-controller/stop param: userId, gardenName

GET: /smart-controller/pump/start param: userId, gardenName

GET: /smart-controller/pump/stop param: userId, gardenName

GET: /smart-controller/light/start param: userId, gardenName, startTime ("06:04"), endTime ("08:45") (start và endtime là String)

GET: /smart-controller/light/stop param: userId, gardenName

# system crash - solve problem

still developer, 100% done soon
