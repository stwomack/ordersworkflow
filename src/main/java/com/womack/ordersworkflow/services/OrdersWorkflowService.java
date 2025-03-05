package com.womack.ordersworkflow.services;

import com.womack.ordersworkflow.activities.OrderActivitiesImpl;
import com.womack.ordersworkflow.domain.SubmittedOrder;
import com.womack.ordersworkflow.helpers.SubmittedOrderHelper;
import com.womack.ordersworkflow.workflows.OrdersWorkflow;
import com.womack.ordersworkflow.workflows.OrdersWorkflowImpl;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.SimpleSslContextBuilder;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.Workflow;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@EnableScheduling
public class OrdersWorkflowService {
    public static final Logger LOG = Workflow.getLogger(OrdersWorkflowService.class);

    WorkflowClient client;
    WorkerFactory factory;

    @Value("${orderprocessingservice-url}")
    private String serviceUrl;

    @Value("${temporal.ns}")
    private String namespace;

    @Value("${temporal.target}")
    private String target;

    @Value("${temporal.taskQueue}")
    private String temporalTaskQueue;

    @Value("${temporal.cert}")
    private String temporalCert;

    @Value("${temporal.key}")
    private String temporalKey;

    @PostConstruct
    public void startWorker() throws IOException {
        LOG.info("Starting Worker");
        createWorkerFactory();
        Worker worker = getFactory().newWorker(temporalTaskQueue);
        worker.registerWorkflowImplementationTypes(OrdersWorkflowImpl.class);
        OrderActivitiesImpl orderActivities = new OrderActivitiesImpl();
        orderActivities.setServiceUrl(serviceUrl);
        worker.registerActivitiesImplementations(orderActivities);
        factory.start();
        LOG.info("Worker started");
    }

    @PostMapping("/submitOrder")
    // @Scheduled(fixedRate = 20000)
    public void generateOrderWorkflow() {
        SubmittedOrder submittedOrder = SubmittedOrderHelper.createSubmittedOrder();
        StringBuilder stringBuilder = new StringBuilder()
                .append("SubmittedOrder::")
                .append(submittedOrder.getOrderNumber());
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(temporalTaskQueue)
                .setWorkflowId(stringBuilder.toString())
                .build();

        OrdersWorkflow workflow = getClient().newWorkflowStub(OrdersWorkflow.class, options);
        WorkflowExecution we = WorkflowClient.start(workflow::processOrder, submittedOrder);
        LOG.info("Order Workflow Submitted: {}::{}", we.getWorkflowId(), we.getRunId());
    }

    private void createWorkerFactory() throws FileNotFoundException, SSLException {
        client = createWorkflowClient();
        factory = WorkerFactory.newInstance(client);
    }

    private WorkflowClient createWorkflowClient() throws FileNotFoundException, SSLException {
        InputStream clientCertInputStream = new FileInputStream(temporalCert);
        InputStream clientKeyInputStream = new FileInputStream(temporalKey);

        SslContext sslContext = SimpleSslContextBuilder.forPKCS8(clientCertInputStream, clientKeyInputStream).build();
        WorkflowServiceStubsOptions stubsOptions = WorkflowServiceStubsOptions.newBuilder()
                .setSslContext(sslContext)
                .setTarget(target)
                .build();
        WorkflowServiceStubs service = WorkflowServiceStubs.newServiceStubs(stubsOptions);
        WorkflowClientOptions options = WorkflowClientOptions.newBuilder()
                .setNamespace(namespace)
                .build();

        return WorkflowClient.newInstance(service, options);
    }

    public WorkerFactory getFactory() {
        return factory;
    }

    public WorkflowClient getClient() {
        return client;
    }
}