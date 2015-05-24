CF Test
=======

### Intro

- The backend has a REST endpoint /trades able to consume trade messages. This endpoint is secured with OAuth.
- Messages are forwarded to a channel listened by a Processor Service.
- The Processor Service stores data into a Mongo, re-calculate Trade Summary and publish new values to a Stomp WebSocket endpoint /topic/trade-summaries
- Frontend consume messages using sockjs and stompjs.

### Technologies

- Backend: Spring IoC, MVC Security, OAuth2, WebSocket and Data/Mongo
- Frontend: AngularJS, Bootstrap and Google-Chart

## Requirements

- JDK 1.8.X
- Maven 3.X
- Node JS 0.10.X
- Compass 0.1.X
- NPM 1.4.X

## Backend (trade-processor)

### Assumptions

- CF_HOME is an environment variable pointing to the directory where the git repo was cloned

### Launching

```
$ cd $CF_HOME/trade-processor
$ mvn clean && mvn install
$ mvn tomcat7:run
```

NOTES: 
- mvn install will execute all app tests
- proxy.properties can be used for configuring a corporate proxy settings

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
$ curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{ "userId": "134256", "currencyFrom": "USD", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 500.00, "rate": 0.5, "timePlaced" : "24­JAN­15 10:27:44", "originatingCountry" : "US" }' http://localhost:8080/trade-processor/trades && echo
```

## Launching trade-processor (frontend)

```
$ sudo npm install bower -g
$ cd $CF_HOME/trade-processor-front
$ bower install
$ grunt serve
```

NOTE: 
- Maybe you run into a "Waiting...Fatal error: watch ENOSPC" problem when starting grunt. You may solve this increasing the number of files that can be watched by a user

```
$ echo fs.inotify.max_user_watches=524288 | sudo tee -a /etc/sysctl.conf && sudo sysctl -p
```

## Known improvements

The following improvements were not implemented because this is a demo:
- Replace InMemory OAuth DB (e.g. for RDBMS)
- Replace InMemory Mongo (e.g. for a Sharded Cluster or Replica Set)
- Replace InMemory Publish Subscribe Channel (e.g. for a Backed MQ)
- Replace InMemory TradeSummaryCache (e.g. for a Memcached implementation)
- POM Hardening. Prevent memory leaks due to duplicated (different versions) dependencies
- Add tests for OAuth Access (e.g. using HttpClient)
- Add integration test for WebSocket part
- Add stress tests for Consumer-Producer (e.g. using JMeter)
- Add tests for frontend (ideally Selenium tests)
- Improve GUI design and UX
