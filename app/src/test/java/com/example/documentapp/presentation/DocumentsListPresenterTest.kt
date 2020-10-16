package com.example.documentapp.presentation

import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentListsInteractor
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DocumentsListPresenterTest {
    private var view: DocumentsListView = mock()
    private val interactor: DocumentListsInteractor = mock()

    private val presenter = DocumentsListPresenter(interactor)

    @BeforeEach
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `fetch documents list on attach and show returned data`() {
        given(interactor.getCvDocumentsList()).willReturn(
            Single.just(listOf(CvDocumentInfo("sir_richard.json", "Sir Richard")))
        )

        presenter.attachView(view)

        verify(view).showDocsListData(listOf(CvDocumentInfo("sir_richard.json", "Sir Richard")))
    }

    @Test
    fun `show error on interactor returning error`() {
        given(interactor.getCvDocumentsList()).willReturn(
            Single.error(Throwable(""))
        )

        presenter.attachView(view)

        verify(view).showError()
    }

    @Test
    fun `show progress when loading data`() {
        given(interactor.getCvDocumentsList()).willReturn(
            Single.just(listOf(CvDocumentInfo("sir_richard.json", "Sir Richard")))
        )

        presenter.attachView(view)

        verify(view).showProgress()
    }
}