package at.fhj.msd.stopwatch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.fhj.msd.stopwatch.viewmodels.StopWatchViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StopWatchViewModelTest {
    //subject under test
    private val viewModel = StopWatchViewModel()

    //needed for async live data
    @Rule @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testStartStopButton(){
        assertEquals(R.string.stopwatch_start, viewModel.stopStartText.getOrAwaitValue())
        viewModel.toggleStartStop()
        assertEquals(R.string.stopwatch_stop, viewModel.stopStartText.getOrAwaitValue())
        viewModel.toggleStartStop()
        assertEquals(R.string.stopwatch_start, viewModel.stopStartText.getOrAwaitValue())
    }

    @Test
    fun testLapTimeLists(){
        viewModel.toggleStartStop()
        viewModel.takeLapTime()
        viewModel.takeLapTime()
        viewModel.toggleStartStop()
        assertEquals(viewModel.lapTimes.getOrAwaitValue().size, 2)
        viewModel.clearLaps()
        assertEquals(viewModel.lapTimes.getOrAwaitValue().size, 0)
    }

    @Test
    fun testTakingTimeReset(){
        viewModel.toggleStartStop()
        Thread.sleep(400L)
        viewModel.toggleStartStop()
        assertNotEquals(viewModel.elapsedTimeString.getOrAwaitValue(), "00:00.00")
        viewModel.reset()
        assertEquals(viewModel.elapsedTimeString.getOrAwaitValue(), "00:00.00")
    }

}