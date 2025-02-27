# OrdersWorkflow
#### Temporal Demo Workflow and batch workload starter
* ca.pem and ca.keys will need to be added to your /usr/local/bin dir, either on localhost, or mounted into your k8s pod
  * To easily generate these files, use `tcld gen ca --org temporal -d 1y --ca-cert ca.pem --ca-key ca.key`
* If running both services on localhost, you can override the service url as follows:
  * ```./mvnw spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=8081 -Dorderprocessingservice-url=http://localhost:8080/'```
* Backing service can be found here: https://github.com/stwomack/orderprocessingservice
* New workflows are kicked off every 20 seconds via @Scheduled(fixedRate = 20000) - I should extract that to a property but haven't yet
* Your temporal cloud endpoint and ns can be configured in application.yaml:
https://github.com/stwomack/ordersworkflow/blob/main/src/main/resources/application.yaml
