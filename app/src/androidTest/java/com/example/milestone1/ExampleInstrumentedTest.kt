package com.example.milestone1

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry
            .getInstrumentation().targetContext
        assertEquals("com.example.milestone1",
            appContext.packageName)
    }

    @Test
    fun appPackageName_isCorrect() {
        val appContext = InstrumentationRegistry
            .getInstrumentation().targetContext
        assertTrue(appContext.packageName
            .startsWith("com.example"))
    }

    @Test
    fun appContext_isNotNull() {
        val appContext = InstrumentationRegistry
            .getInstrumentation().targetContext
        assertNotNull(appContext)
    }
}