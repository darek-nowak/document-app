package com.example.documentapp.data

import com.example.documentapp.DataObjects.CV_DATA_MODEL
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.jupiter.api.Test

internal class DocumentInteractorTest {

    private val docRepository: GithubDocRepository = mock()
    private val interactor = DocumentInteractor(docRepository)

    @Test
    fun `provide result with list of document content mapped from CvData`() {
        given(docRepository.fetchDocument("filename")).willReturn(
            Single.just(CV_DATA_MODEL)
        )

        val tested = interactor.getCvDocument("filename").test()

        tested.assertValue(
            Result.Success(
                listOf(
                    DocumentDisplayItem.ExtraBigItem("Rafal Kowalski"),
                    DocumentDisplayItem.ExtraBigItem("Senior Android Developer"),
                    DocumentDisplayItem.Item("Android developer experienced in RxJava programming in fintech sector"),
                    DocumentDisplayItem.Header("Professional Experience"),
                    DocumentDisplayItem.ParagraphItem("08/2016 - till now"),
                    DocumentDisplayItem.BigItem("Facebook, US"),
                    DocumentDisplayItem.BigItem("Sr Android Developer"),
                    DocumentDisplayItem.Item("• Designing Custom Views"),
                    DocumentDisplayItem.Item("• Grooming Planning tasks"),
                    DocumentDisplayItem.Item("• Application development")
                )
            )
        )
    }


    @Test
    fun `provide result with error when repository returns error`() {
        given(docRepository.fetchDocument("filename")).willReturn(
            Single.error(Throwable("error"))
        )

        val tested = interactor.getCvDocument("filename").test()

        tested.assertValue(
            Result.Error
        )
    }


    @Test
    fun `cache result without resubsription to repository source for the same document name`() {
        var subscriptions = 0
        val singleSource: Single<CvData> = Single.create { subscriber ->
            subscriptions++
            subscriber.onSuccess(CV_DATA_MODEL)
        }
        given(docRepository.fetchDocument("filename")).willReturn(
            singleSource
        )

        interactor.getCvDocument("filename").test()
        interactor.getCvDocument("filename").test()

        Truth.assertThat(subscriptions).isEqualTo(1)
    }

    @Test
    fun `resubscribe to repository source for the different document name`() {
        var subscriptions = 0
        val singleSource: Single<CvData> = Single.create { subscriber ->
            subscriptions++
            subscriber.onSuccess(CV_DATA_MODEL)
        }
        given(docRepository.fetchDocument(any())).willReturn(
            singleSource
        )

        interactor.getCvDocument("filename1").test()
        interactor.getCvDocument("filename2").test()

        Truth.assertThat(subscriptions).isEqualTo(2)
    }
}