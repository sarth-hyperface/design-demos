package org.example.activities

import io.temporal.activity.ActivityInterface

@ActivityInterface
interface Activities {
    String randomStr()
    void childStuff()
}