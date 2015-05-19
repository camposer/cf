CF Test
=======

## Requirements

- JDK 1.8
- Maven 3.X
- Node JS 0.10

## Backend (trade-processor)

### Assumptions

- CF_HOME is an environment variable pointing to the directory where the git repo was cloned

### Launching

- $ cd $CF_HOME/trade-processor
- $ mvn clean && mvn tomcat7:run &

### For sending trade messages

- Getting OAuth Access Token 
  - $ CLIENT_ID=0cc175b9c0f1b6a831c399e269772661
  - $ SECRET=92eb5ffee6ae2fec3ad71c777531578f
  - $ curl -X POST "http://$CLIENT_ID:$SECRET@localhost:8080/trade-processor/oauth/token?grant_type=client_credentials" && echo
  - $ TOKEN="previously generated access token"

- Sending messages
  - $ curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{ "userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24­JAN­15 10:27:44", "originatingCountry" : "FR" }' http://localhost:8080/trade-processor/trades && echo

## Launching trade-processor (frontend)

$ cd $CF_HOME/trade-processor-front
$ nodejs app

## Known improvements

The following improvements were no implemented because this is just a demo:
- Replace InMemory OAuth DB for RDBMS
- Replace InMemory Mongo for a Sharded Cluster or Replica Set
- Replace InMemory Publish Subscribe Channel for a Backed MQ (e.g. ActiveMQ)
- POM Hardening. Prevent memory leaks due to duplicated (different versions) dependencies
