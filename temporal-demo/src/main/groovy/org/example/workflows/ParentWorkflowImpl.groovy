package org.example.workflows

import io.temporal.activity.ActivityOptions
import io.temporal.api.enums.v1.ParentClosePolicy
import io.temporal.common.RetryOptions
import io.temporal.workflow.Async
import io.temporal.workflow.ChildWorkflowOptions
import io.temporal.workflow.Promise
import io.temporal.workflow.Workflow
import org.example.activities.Activities

import java.time.Duration

class ParentWorkflowImpl implements org.example.workflows.ParentWorkflow {
    static final String taskQueue = "TQ-1286"

    private final Activities activities = Workflow.newActivityStub(Activities.class, ActivityOptions
            .newBuilder()
            .setRetryOptions(RetryOptions.newBuilder()
                    .setMaximumInterval(Duration.ofSeconds(2))
                    .setMaximumAttempts(1)
                    .build())
            .setStartToCloseTimeout(Duration.ofSeconds(15))
            .setTaskQueue(taskQueue)
            .build())

    @Override
    String doParentStuff() {
        def gg = activities.randomStr()
        println("Parent ${gg}")
        Thread.sleep(500)
        ArrayList<Promise<Void>> promises = []

        5.times {
            promises.add(Async.function(activities::childStuff))
        }

        Promise.allOf(promises).get()
        println("Endparent ${gg}")
        return "Test"
    }
}
