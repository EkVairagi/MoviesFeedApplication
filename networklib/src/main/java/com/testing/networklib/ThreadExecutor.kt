package com.testing.networklib

import android.os.Process
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/*
class ThreadExecutor private constructor() {
    companion object {
        private val executorService: ExecutorService = Executors.newSingleThreadExecutor { thread ->
            Thread(thread, "PriorityHandlerThread").apply {
                priority = Process.THREAD_PRIORITY_DEFAULT
            }
        }

        fun execute(runnable: Runnable?) {
            runnable?.let { executorService.execute(it) }
        }
    }
}
*/


class ThreadExecutor private constructor() {

    companion object {
        private val executorService: ExecutorService = Executors.newSingleThreadExecutor { thread ->
            Thread(thread, "PriorityHandlerThread")
        }

        fun execute(runnable: Runnable?) {
            runnable?.let { executorService.execute(it) }
        }
    }
}
