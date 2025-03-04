# OrdersWorkflow
#### Temporal Demo Workflow and batch workload starter
* ca.pem and ca.keys will need to be added to your /usr/local/bin dir, either on localhost, or mounted into your k8s pod (see k8s/tls-secrets.yaml for an example)
  * To easily generate these files, use `tcld gen ca --org temporal -d 1y --ca-cert ca.pem --ca-key ca.key`
* Your temporal cloud endpoint and ns can be configured in application.yaml:
  * https://github.com/stwomack/ordersworkflow/blob/main/src/main/resources/application.yaml
* The backing service can be found here: https://github.com/stwomack/orderprocessingservice
* That orderprocessingservice will be started as follows:
* ```./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8080 -Dordersworkflow-url=http://localhost:8081/' ```
* Run this workflow as follows:
  * ```./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081 -Dorderprocessingservice-url=http://localhost:8080/'```
  * You can run as many instances of this as you like, to emulate a real scenario. Just change the server.port for each new instance
  * Start and stop workers to show durable execution, along with stickiness of workers to workload executions (until terminated)
* New workflow instances are kicked off via the web page of the service app
  * Just hit http://localhost:8080/ in a browser and push the red button to kick off individual workload executions

#### If running in kubernetes, just apply both deployments, and the service for the orderprocessingservice
* If running in kind, you'll need to use port-forwarding to access the web page
  * This whole demo gets tricky because of kind networking. YMMV