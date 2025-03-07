package com.womack.ordersworkflow.services;

import com.womack.ordersworkflow.workflows.OrdersWorkflow;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.schedules.*;

import javax.annotation.Nullable;
import java.util.stream.Stream;

//This is just here as a code example. It does nothing and has no impl
public class ScheduledWorkflow {

    public static void main(String[] args) {

        Schedule schedule =
                Schedule.newBuilder()
                        .setAction(
                                ScheduleActionStartWorkflow.newBuilder()
                                        .setWorkflowType(OrdersWorkflow.class)
                                        .setArguments("World")
                                        .setOptions(
                                                WorkflowOptions.newBuilder()
                                                        .setWorkflowId("WorkflowId")
                                                        .setTaskQueue("scheduleClient")
                                                        .build())
                                        .build())
                        .setSpec(ScheduleSpec.newBuilder().build())
                        .build();
        ScheduleHandle handle = scheduleClient.createSchedule("ScheduleId", schedule, ScheduleOptions.newBuilder().build());
    }

    static ScheduleClient scheduleClient = new ScheduleClient() {
        @Override
        public ScheduleHandle createSchedule(String scheduleID, Schedule schedule, ScheduleOptions options) {
            return null;
        }

        @Override
        public ScheduleHandle getHandle(String scheduleID) {
            return null;
        }

        @Override
        public Stream<ScheduleListDescription> listSchedules() {
            return Stream.empty();
        }

        @Override
        public Stream<ScheduleListDescription> listSchedules(@Nullable Integer pageSize) {
            return Stream.empty();
        }

        @Override
        public Stream<ScheduleListDescription> listSchedules(@Nullable String query, @Nullable Integer pageSize) {
            return Stream.empty();
        }
    };
}
