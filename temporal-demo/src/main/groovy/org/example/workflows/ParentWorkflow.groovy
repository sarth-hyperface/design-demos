package org.example.workflows


import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
interface ParentWorkflow {
    @WorkflowMethod
    String doParentStuff()
}