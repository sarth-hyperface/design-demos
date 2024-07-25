package org.example.workers

import io.temporal.worker.Worker
import io.temporal.worker.WorkerFactory
import io.temporal.worker.WorkerFactoryOptions
import io.temporal.worker.WorkerOptions
import jakarta.annotation.PostConstruct
import org.example.activities.Activities
import org.example.activities.ActivitiesImpl
import org.example.service.WorkflowService
import org.example.workflows.ChildWorkflowImpl
import org.example.workflows.ParentWorkflowImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ParentWorker {
    @Autowired
    WorkflowService workflowService

    @Autowired
    ActivitiesImpl activitiesImpl

    @Autowired
    @Qualifier("taskQueue")
    String taskQueue

    @PostConstruct
    void init() {
        WorkerFactory factory = WorkerFactory.newInstance(workflowService.workflowClient(), WorkerFactoryOptions.newBuilder()
                .setMaxWorkflowThreadCount(8)
                .setWorkflowCacheSize(6)
                .build())
        registerParentWorker(factory)
        factory.start()
    }

    void registerParentWorker(WorkerFactory factory) {
        println("Registering parent worker")
        Worker worker = factory.newWorker(taskQueue, WorkerOptions.newBuilder()
                .setMaxConcurrentWorkflowTaskPollers(1)
                .setMaxConcurrentActivityTaskPollers(1)
                .setMaxConcurrentWorkflowTaskExecutionSize(2)
                .setMaxConcurrentActivityExecutionSize(2)
                .build())
        worker.registerWorkflowImplementationTypes(ParentWorkflowImpl.class)
        worker.registerActivitiesImplementations(activitiesImpl)
        worker.registerWorkflowImplementationTypes(ChildWorkflowImpl.class)
    }
}
