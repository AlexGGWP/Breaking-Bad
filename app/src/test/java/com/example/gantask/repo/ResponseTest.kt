package com.example.gantask.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.gantask.base.BaseUtilityTests
import com.example.gantask.di.MockTestAPIService
import com.example.gantask.di.configureTestAppComponent
import com.example.gantask.rest.NetworkResponseHandler
import com.example.gantask.rest.api.APIs
import com.example.gantask.rest.data.BBCharactersData
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection


@RunWith(JUnit4::class)
class ResponseTest : BaseUtilityTests() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val mAPIService: APIs by inject()
    val mockWebServer: MockWebServer by inject()

    lateinit var mockTestAPIService: MockTestAPIService
    lateinit var listOfCharacters: List<BBCharactersData>

    @Before
    fun start() {
        super.setUp()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @Test
    fun testReceivedDataResponse() = runBlocking<Unit> {

        var successfulResponse = false
        var errorResponse = false

        mockNetworkResponseWithFileContent("mock_response.json", HttpURLConnection.HTTP_OK)

        mockTestAPIService = MockTestAPIService()

        when (val dataReceived = mockTestAPIService.getAllCharacters()) {
            is NetworkResponseHandler.Success -> {
                successfulResponse = true
                listOfCharacters = dataReceived.successData
            }
            is NetworkResponseHandler.Error -> {
                errorResponse = true
                dataReceived.exception.message
            }
        }

        //Check if response was success or not
        assertTrue(successfulResponse)
        assertFalse(errorResponse)

        //Check result
        assertNotNull(listOfCharacters)
        assertEquals(listOfCharacters.size, 57)
    }
}