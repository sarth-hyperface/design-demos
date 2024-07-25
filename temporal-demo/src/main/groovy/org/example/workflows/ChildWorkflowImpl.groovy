package org.example.workflows

import io.temporal.activity.ActivityOptions
import io.temporal.common.RetryOptions
import io.temporal.workflow.Workflow
import org.example.activities.Activities

import java.time.Duration

class ChildWorkflowImpl implements org.example.workflows.ChildWorkflow {
    private final Activities activities = Workflow.newActivityStub(Activities.class, ActivityOptions
            .newBuilder()
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumAttempts(1)
                    .build())
            .setStartToCloseTimeout(Duration.ofSeconds(15))
            .build())

    @Override
    void doChildStuff(Integer i) {
        println("Doing child stuff ${i}")
        activities.childStuff()
        Workflow.sleep(50)
        // done
    }
}
