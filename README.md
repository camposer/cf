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

```
$ cd $CF_HOME/trade-processor
$ mvn clean && mvn install
$ mvn tomcat7:run
```

NOTE: mvn install will execute all app tests

### For sending trade messages

- Getting OAuth Access Token 
```
$ CLIENT_ID=0cc175b9c0f1b6a831c399e269772661
$ SECRET=92eb5ffee6ae2fec3ad71c777531578f
$ curl -X POST "http://$CLIENT_ID:$SECRET@localhost:8080/trade-processor/oauth/token?grant_type=client_credentials" && echo
$ TOKEN="previously generated access token"
```

- Sending messages
```
$ curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{ "userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24­JAN­15 10:27:44", "originatingCountry" : "FR" }' http://localhost:8080/trade-processor/trades && echo
```

## Launching trade-processor (frontend)

```
$ cd $CF_HOME/trade-processor-front
$ nodejs app
```

## Known improvements

The following improvements were no implemented because this is just a demo:
- Replace InMemory OAuth DB (e.g. for RDBMS)
- Replace InMemory Mongo (e.g. for a Sharded Cluster or Replica Set)
- Replace InMemory Publish Subscribe Channel (e.g. for a Backed MQ)
- Replace InMemory TradeSummaryCache (e.g. for a Memcached implementation)
- POM Hardening. Prevent memory leaks due to duplicated (different versions) dependencies
- Add tests for OAuth Access (e.g. using HttpClient)
- Add stress tests for Consumer-Producer (e.g. using JMeter)
