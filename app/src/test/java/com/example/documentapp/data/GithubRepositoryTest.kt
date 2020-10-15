package com.example.documentapp.data

import com.example.documentapp.DataObjects.CV_DATA_MODEL
import com.example.documentapp.DataObjects.CV_APPLICANT_JSON
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.jupiter.api.Test

internal class GithubRepositoryTest {
    private val api: GitHubApi = mock()
    private val base64Decoder: Base64Decoder = mock()
    private val jsonObjectMapper = ObjectMapper()

    private val repository = GithubRepository(
        api,
        base64Decoder,
        jsonObjectMapper
    )

    @Test
    fun `map encoded content string to document data model`() {
        given(api.getFileContent("dariusz_nowak.json")).willReturn(
            Single.just(
                Content(
                    encoding = "base64",
                    content = ENCODED_CONTENT
                )
            ))
        given(base64Decoder.decode(ENCODED_CONTENT)).willReturn(CV_APPLICANT_JSON)

        val tested = repository.fetchDocument()
            .test()

        tested.assertValue(CV_DATA_MODEL)

    }

    companion object {
        const val ENCODED_CONTENT = "abrakadabra"
    }
}