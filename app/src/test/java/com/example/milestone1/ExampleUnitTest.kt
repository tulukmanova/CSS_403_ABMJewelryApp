package com.example.milestone1

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun appName_isCorrect() {
        val appName = "ABM Jewelry Shop"
        assertEquals("ABM Jewelry Shop", appName)
    }

    @Test
    fun routes_areCorrect() {
        assertEquals("home", Routes.HOME)
        assertEquals("collections", Routes.COLLECTIONS)
        assertEquals("jewelry", Routes.JEWELRY)
    }
}