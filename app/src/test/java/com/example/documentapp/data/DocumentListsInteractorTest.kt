package com.example.documentapp.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.jupiter.api.Test

internal class DocumentListsInteractorTest {
    private val docRepository: GithubDocRepository = mock()

    private val interactor = DocumentListsInteractor(docRepository)

    @Test
    fun `return result with list of CvDocumentInfo`() {
        given(docRepository.fetchDocumentsList()).willReturn(
            Single.just(listOf(FileInfo("john_travolta.json")))
        )

        interactor.getCvDocumentsList().test()
            .assertValue(
                Result.Success(
                    listOf(
                        CvDocumentInfo(
                            filename = "john_travolta.json",
                            name = "John Travolta"
                        )
                    )
                )
            )
    }

    @Test
    fun `cache result with list of CvDocumentInfo without resubsription to repository source`() {
        var subscriptions = 0
        val singleSource: Single<List<FileInfo>> = Single.create { subscriber ->
            subscriptions++
            subscriber.onSuccess(listOf(FileInfo("john_travolta.json")))
        }
        given(docRepository.fetchDocumentsList()).willReturn(
            singleSource
        )

        interactor.getCvDocumentsList().test()
        interactor.getCvDocumentsList().test()

        assertThat(subscriptions).isEqualTo(1)
    }

    @Test
    fun `return result with error when repository error`() {
        given(docRepository.fetchDocumentsList()).willReturn(
            Single.error(Throwable("error"))
        )

        interactor.getCvDocumentsList().test()
            .assertValue(
                Result.Error
            )
    }
}