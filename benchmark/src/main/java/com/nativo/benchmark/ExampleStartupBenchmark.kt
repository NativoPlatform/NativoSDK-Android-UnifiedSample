package com.nativo.benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable shell=true>` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()
    private val packageName = "com.nativo.sampleapp"

//    @Test
//    fun startup() = benchmarkRule.measureRepeated(
//        packageName = packageName,
//        metrics = listOf(StartupTimingMetric()),
//        compilationMode = CompilationMode.Full(),
//        iterations = 3,
//        startupMode = StartupMode.COLD,
//        setupBlock = {
//            // Press home button before each run to ensure the starting activity isn't visible.
//            pressHome()
//        }
//    ) {
//        startActivityAndWait()
//    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun scrollWarm() = benchmarkRule.measureRepeated(
        packageName,
        listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.Full(),
        startupMode = StartupMode.WARM,
        iterations = 3,
        setupBlock = {
            startActivityAndWait()
        }
    ) {
        val scrollable = UiScrollable(UiSelector().scrollable(true)).setAsVerticalList()
        // Scroll all the way down and back up
        scrollable.scrollToEnd(8)
        scrollable.scrollToBeginning(8)
    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun scrollCold() = benchmarkRule.measureRepeated(
        packageName,
        listOf(FrameTimingMetric(), TraceSectionMetric("scrollBenchmark")),
        compilationMode = CompilationMode.Full(),
        startupMode = StartupMode.COLD,
        iterations = 3,
        setupBlock = {
            startActivityAndWait()
        }
    ) {
        val scrollable = UiScrollable(UiSelector().scrollable(true)).setAsVerticalList()
        // Scroll all the way down and back up
        scrollable.scrollToEnd(20)
        //scrollable.scrollToBeginning(8)
    }
}