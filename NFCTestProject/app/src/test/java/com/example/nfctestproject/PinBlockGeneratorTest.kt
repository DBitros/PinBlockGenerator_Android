package com.example.nfctestproject
import org.junit.Test
import org.junit.Assert.*

class PinBlockGeneratorTest {
    @Test
    fun testGenerateIso3PinBlock() {
        val pin = "1234"
        val pan = "1111222233334444"
        val expectedPinBlock = "341226DDDCCCCBBB"
        val actualPinBlock = PinBlockGenerator.generateIso3PinBlock(pin, pan)
        assertEquals(expectedPinBlock, actualPinBlock)
    }

    @Test
    fun testXorHex() {
        val a = "1234"
        val b = "5678"
        val expected = "444C"
        val actual = PinBlockGenerator.xorHex(a, b)
        assertEquals(expected, actual)
    }
}