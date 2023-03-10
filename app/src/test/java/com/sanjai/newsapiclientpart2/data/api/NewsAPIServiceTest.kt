package com.sanjai.newsapiclientpart2.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName: String) {
        runBlocking {
            val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(StandardCharsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getTopHeadLines_sentRequest_showExceptedResult() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val response = service.getNewsTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=ba959ca60c03470f8ea1c9521147894a")
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_showPages() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getNewsTopHeadlines("us",1).body()
            val article = responseBody!!.articles.size
            assertThat(article).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_sentRequest_showMatchedNames() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getNewsTopHeadlines("us",1).body()
            val article = responseBody!!.articles[0]
            assertThat(article.author).isEqualTo("Adam Bankhurst")
        }
    }

}