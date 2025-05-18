package com.example.tipcalculator

import org.junit.Test
import org.junit.Assert.*

class TipCalculatorLogicUnitTest {

    @Test
    fun calculateTipLogic_validAmount_returnsCorrectString() {
        val billAmount = "100.0"
        val percentage = 0.15
        val expectedTip = 100.0 * 0.15
        val expectedTotal = 100.0 + expectedTip
        val expectedResult = "Tip: $%.2f\nTotal Bill: $%.2f".format(expectedTip, expectedTotal)

        val actualResult = calculateTipLogic(billAmount, percentage)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun calculateTipLogic_anotherValidAmount_returnsCorrectString() {
        val billAmount = "55.50"
        val percentage = 0.18
        val expectedTip = 55.50 * 0.18
        val expectedTotal = 55.50 + expectedTip
        val expectedResult = "Tip: $%.2f\nTotal Bill: $%.2f".format(expectedTip, expectedTotal)

        val actualResult = calculateTipLogic(billAmount, percentage)
        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun calculateTipLogic_zeroAmount_returnsEmptyString() {
        val result = calculateTipLogic("0.0", 0.20)
        assertEquals("", result)
    }

    @Test
    fun calculateTipLogic_negativeAmount_returnsEmptyString() {
        val result = calculateTipLogic("-10.0", 0.15)
        assertEquals("", result)
    }

    @Test
    fun calculateTipLogic_invalidAmountString_returnsEmptyString() {
        val result = calculateTipLogic("abc", 0.20)
        assertEquals("", result)
    }

    @Test
    fun calculateTipLogic_emptyAmountString_returnsEmptyString() {
        val result = calculateTipLogic("", 0.15)
        assertEquals("", result)
    }
}