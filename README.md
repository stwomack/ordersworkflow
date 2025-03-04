# OrdersWorkflow
#### Temporal Demo Workflow and batch workload starter
* ca.pem and ca.keys will need to be added to your /usr/local/bin dir, either on localhost, or mounted into your k8s pod (see k8s/tls-secrets.yaml for an example)
  * To easily generate these files, use `tcld gen ca --org temporal -d 1y --ca-cert ca.pem --ca-key ca.key`
* If running both services on localhost, you can override the service url as follows:
  * ```./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081 -Dorderprocessingservice-url=http://localhost:8080/'```
* The orderprocessingservice will be started as follows:
  * ```./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8080 -Dordersworkflow-url=http://localhost:8081/' ```
* Backing service can be found here: https://github.com/stwomack/orderprocessingservice
* New workflow instances are kicked off via the web page of the service app now
* Your temporal cloud endpoint and ns can be configured in application.yaml:
https://github.com/stwomack/ordersworkflow/blob/main/src/main/resources/application.yaml