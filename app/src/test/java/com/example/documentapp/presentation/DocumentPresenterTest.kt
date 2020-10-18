package com.example.documentapp.presentation

import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentDisplayItem
import com.example.documentapp.data.DocumentInteractor
import com.example.documentapp.data.Result
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DocumentPresenterTest {

    private val interactor: DocumentInteractor = mock()
    private val view: DocumentView = mock()

    private val presenter = DocumentPresenter(interactor)

    @BeforeEach
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `call document interactor and show returned data when data arrived`() {
        given(interactor.getCvDocument(DOC_FILENAME)).willReturn(
            Single.just(Result.Success(SAMPLE_DOC_DATA))
        )

        presenter.attachView(view, CV_DOC_INFO)

        verify(view).showData(SAMPLE_DOC_DATA)
    }

    @Test
    fun `call document interactor and show error when error occurred`() {
        given(interactor.getCvDocument(DOC_FILENAME)).willReturn(
            Single.just(Result.Error)
        )

        presenter.attachView(view, CV_DOC_INFO)

        verify(view).showError()
    }

    @Test
    fun `show progress when loading data`() {
        given(interactor.getCvDocument(DOC_FILENAME)).willReturn(
            Single.just(Result.Success(SAMPLE_DOC_DATA))
        )

        presenter.attachView(view, CV_DOC_INFO)

        verify(view).showProgress()
    }

    private companion object {
        const val DOC_FILENAME = "Sir_Richard.json"
        val CV_DOC_INFO = CvDocumentInfo("Sir_Richard.json", "Sir Richard")
        val SAMPLE_DOC_DATA = listOf(DocumentDisplayItem.ExtraBigItem("Sir Richard"))
    }
}