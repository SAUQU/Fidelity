package com.example.fidelity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.fidelity.network.AnimeApiManager
import com.example.fidelity.viewmodel.animelist.AnimeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class VMTestMockServerResponse{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private var server: MockWebServer? = null

    private var viewModel : AnimeViewModel? =null

    @Mock
    var context: AppApplication? = null

    @Mock
    var observer: Observer<Boolean>? = null



    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        server = MockWebServer()
        startServer(200)
        val url = server?.url("/").toString()
        `when`(context!!.getBaseURL()).thenReturn(url)

        val apiClient = AnimeApiManager(context!!)

        viewModel = AnimeViewModel(apiClient, context!!)
        viewModel!!.dataLoading.observeForever(observer!!)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNetworkCall()  =  coroutinesTestRule.dispatcher.runBlockingTest  {
        viewModel?.loadData("naruto")
        val lock = CountDownLatch(1);
        lock.await(2000, TimeUnit.MILLISECONDS);
        Mockito.verify(observer)!!.onChanged(true)
        Mockito.verify(observer)!!.onChanged(false)
        Assert.assertFalse(viewModel!!.data.isEmpty())
    }

    private fun startServer(responseCode: Int){

        val mockedResponse = MockResponse()

        mockedResponse.setResponseCode(responseCode)
        val contentBody= MockResponseFileReader("animelist.json")
        contentBody.content?.let { mockedResponse.setBody(it) }
        server?.enqueue(mockedResponse)
        server?.start()
    }
}
