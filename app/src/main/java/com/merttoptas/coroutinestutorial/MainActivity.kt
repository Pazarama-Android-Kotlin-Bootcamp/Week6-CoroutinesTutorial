@file:OptIn(DelicateCoroutinesApi::class)

package com.merttoptas.coroutinestutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.start()
        main()
        mainGlobal()
        mainDispatchers()
        exampleJob()
        exampleWithContext()
        // With lifecycleScope
        lifecycleScope.launchWhenStarted {
            Log.d("deneme1", "launchWhenStarted")
        }

        lifecycleScope.launchWhenCreated {
            Log.d("deneme1", "launchWhenCreated")
        }

        lifecycleScope.launchWhenResumed {
            Log.d("deneme1", "launchWhenResumed")
        }
    }

    private fun main() = runBlocking {
        val time = measureTimeMillis {
            val oneExample = async { exampleOne() }
            val twoExample = async { exampleTwo() }

            Log.d("Deneme1", "Main Scope: ${oneExample.await() + twoExample.await()}")
        }
        Log.d("Deneme1", "Main Scope: Completed ${time}")
    }

    private fun mainGlobal() = runBlocking {
        val time = measureTimeMillis {
            val oneExample = GlobalScope.async { exampleOne() }
            val twoExample = GlobalScope.async { exampleTwo() }

            Log.d("Deneme1", "${oneExample.await() + twoExample.await()}")
        }
        Log.d("Deneme1", "Completed ${time}")
    }


    private fun mainDispatchers() = runBlocking {
        val time = measureTimeMillis {
            val oneExample = async(Dispatchers.Default) { exampleOne() }
            val twoExample = async(Dispatchers.Default) { exampleTwo() }

            Log.d("Deneme1", "Main Dispatchers Scope: ${oneExample.await() + twoExample.await()}")
        }
        Log.d("Deneme1", "Main Dispatchers Scope: Completed ${time}")
    }

    private suspend fun exampleOne(): Int {
        delay(1000L)
        return 15
    }

    private suspend fun exampleTwo(): Int {
        delay(1000L)
        return 25
    }

    private fun exampleJob() = runBlocking {
        val firstJob = launch {
            delay(1000)
            Log.d("Deneme2", "First job")
            val secondJob = launch {
                Log.d("Deneme2", "Second job")
            }
        }
        firstJob.invokeOnCompletion {
            Log.d("Deneme2", "Job finished")
        }
    }

    private fun exampleWithContext() = runBlocking {
        launch(Dispatchers.Main) {
            Log.d("Deneme2", "This is a main thread")
            withContext(Dispatchers.IO) {
                Log.d("Deneme2", "This is a io networking thread")

            }

        }

        launch(Dispatchers.IO) {
            Log.d("Deneme2", "This is a io (networking) thread")
            withContext(Dispatchers.Default) {
                Log.d("Deneme2", "This is a default networking thread")

            }
        }
    }
}