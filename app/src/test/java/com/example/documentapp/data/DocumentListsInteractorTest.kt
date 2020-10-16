package com.example.documentapp.data

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.jupiter.api.Test

internal class DocumentListsInteractorTest {
    private val docRepository: GithubRepository = mock()

    private val interactor = DocumentListsInteractor(docRepository)

    @Test
    fun `return list of CvDocumentInfo`() {
        given(docRepository.fetchDocumentsList()).willReturn(
            Single.just(listOf(FileInfo("john_travolta.json")))
        )

        interactor.getCvDocumentsList().test()
            .assertValue(
                listOf(
                    CvDocumentInfo(
                        filename = "john_travolta.json",
                        name = "John Travolta"
                    )
                )
            )
    }
}