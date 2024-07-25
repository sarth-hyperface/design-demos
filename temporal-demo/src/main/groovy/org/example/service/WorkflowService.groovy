package org.example.service

import io.temporal.client.WorkflowClient
import io.temporal.client.WorkflowOptions
import io.temporal.common.RetryOptions
import io.temporal.serviceclient.WorkflowServiceStubs
import io.temporal.workflow.Functions
import org.example.workflows.ParentWorkflow
import org.springframework.stereotype.Service

import java.time.Duration

@Service
class WorkflowService {
    def taskQueue = "TQ-1286"

    WorkflowClient workflowClient() {
        WorkflowServiceStubs stubs = WorkflowServiceStubs.newLocalServiceStubs()
        WorkflowClient client = WorkflowClient.newInstance(stubs)
    }

    void triggerParent() {
        def workflowId = "parent-${UUID.randomUUID().toString()}"
        def client = workflowClient()
        def parentWorkflow = client
                .newWorkflowStub(ParentWorkflow.class, WorkflowOptions.newBuilder()
                        .setRetryOptions(RetryOptions.newBuilder()
                                .setMaximumAttempts(1)
                                .build())
                        .setWorkflowExecutionTimeout(Duration.ofSeconds(20))
                        .setWorkflowId(workflowId)
                        .setTaskQueue(taskQueue)
                        .build())

        WorkflowClient.start((Functions.Func) parentWorkflow::doParentStuff)
    }
}
