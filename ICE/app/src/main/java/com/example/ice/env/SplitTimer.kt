package com.example.ice.env

import android.os.SystemClock

class SplitTimer(
    name: String?
){

    private var lastWallTime: Long = 0
    private var lastCpuTime: Long = 0

    init { newSplit() }

    private fun newSplit() {
        lastWallTime = SystemClock.uptimeMillis()
        lastCpuTime = SystemClock.currentThreadTimeMillis()
    }

    fun endSplit(splitName: String?) {
        val currWallTime = SystemClock.uptimeMillis()
        val currCpuTime = SystemClock.currentThreadTimeMillis()
        lastWallTime = currWallTime
        lastCpuTime = currCpuTime
    }
}