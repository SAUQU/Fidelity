package com.example.fidelity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.network.AnimeApiManager
import com.example.fidelity.network.network_response.CoreNetworkResponse
import com.example.fidelity.viewmodel.animelist.AnimeViewModel
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.*

@RunWith(JUnit4::class)
class ViewModelTestCases {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    var context: AppApplication? = null

    @Mock
    var apiClient: AnimeApiManager? = null

    @Mock
    var observer: Observer<Boolean>? = null

    @Mock
    var lifecycleOwner: LifecycleOwner? = null
    private var lifecycle: Lifecycle? = null
    private var viewModel: AnimeViewModel? = null
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(context!!.getBaseURL()).thenReturn("https://api.jikan.moe/v3/")
        viewModel = AnimeViewModel(apiClient!!, context!!)
        lifecycle = LifecycleRegistry(lifecycleOwner!!)
        viewModel!!.dataLoading.observeForever(observer!!)
    }

    @Test
    fun testNull() = coroutinesTestRule.dispatcher.runBlockingTest {
        Mockito.`when`(apiClient!!.getAnimeList(ArgumentMatchers.anyString())).thenReturn(null)
        Assert.assertNotNull(viewModel!!.data)
        Assert.assertTrue(viewModel!!.dataLoading.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() = coroutinesTestRule.dispatcher.runBlockingTest {
        // Mock API response
        val animeModel = AnimeModel()
        val list: MutableList<AnimeModel> = ArrayList()
        list.add(animeModel)
        val coreNetworkResponse = CoreNetworkResponse()
        coreNetworkResponse.results = list
        Mockito.`when`(apiClient!!.getAnimeList(ArgumentMatchers.anyString())).thenReturn(Response.success(coreNetworkResponse))
        viewModel!!.loadData("")
        Mockito.verify(observer)!!.onChanged(true)
        Mockito.verify(observer)!!.onChanged(false)
        Assert.assertFalse(viewModel!!.data.isEmpty())
    }

    @Test
    fun testApiFetchDataError() = coroutinesTestRule.dispatcher.runBlockingTest {
        val errorResponse =
                "{\n" +
                        "  \"type\": \"error\",\n" +
                        "  \"message\": \"Error Response.\"\n}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        Mockito.`when`(apiClient!!.getAnimeList(ArgumentMatchers.anyString())).thenReturn(Response.error(401,errorResponseBody))
        viewModel!!.loadData("")
        Assert.assertTrue(viewModel!!.data.isEmpty())
        Assert.assertFalse(viewModel!!.errorMessage.isEmpty())
    }

    @After
    fun tearDown() {
        apiClient = null
        viewModel = null
    }
}