package com.example.documentapp.data

import com.example.documentapp.DataObjects.CV_DATA_MODEL
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.junit.jupiter.api.Test

internal class DocumentInteractorTest {

    private val docRepository: GithubRepository = mock()
    private val interactor = DocumentInteractor(docRepository)

    @Test
    fun `provide list of document items mapped from CvData`() {
        given(docRepository.fetchDocument("filename")).willReturn(
            Single.just(CV_DATA_MODEL)
        )

        val tested = interactor.getCvDocument("filename").test()

        tested.assertValue(
            listOf(
                DocumentDisplayItem.ExtraBigItem("Rafal Kowalski"),
                DocumentDisplayItem.ExtraBigItem("Senior Android Developer"),
                DocumentDisplayItem.Item("Android developer experienced in RxJava programming in fintech sector"),
                DocumentDisplayItem.Header("Professional Experience"),
                DocumentDisplayItem.ParagraphItem("08/2016 - till now"),
                DocumentDisplayItem.BigItem("Facebook, US"),
                DocumentDisplayItem.BigItem("Sr Android Developer"),
                DocumentDisplayItem.Item("Designing Custom Views"),
                DocumentDisplayItem.Item("Grooming Planning tasks"),
                DocumentDisplayItem.Item("Application development")
            )
        )


    }
}