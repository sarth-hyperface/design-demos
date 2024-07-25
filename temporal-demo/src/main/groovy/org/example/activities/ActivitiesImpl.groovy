package org.example.activities

import org.springframework.stereotype.Component

@Component
class ActivitiesImpl implements org.example.activities.Activities {
    @Override
    String randomStr() {
        return UUID.randomUUID().toString()
    }

    @Override
    void childStuff() {
        def gg = UUID.randomUUID().toString()
        println("start ${gg}")
        Thread.sleep(5000)
        println("end ${gg}")
        return
    }
}
