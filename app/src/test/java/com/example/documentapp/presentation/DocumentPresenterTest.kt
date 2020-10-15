package com.example.documentapp.presentation

import com.example.documentapp.data.DocumentInteractor
import com.example.documentapp.data.DocumentDisplayItem
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
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
        given(interactor.getCvDocument()).willReturn(
            Single.just(SAMPLE_DOC_DATA)
        )

        presenter.attachView(view)

        verify(view).showData(SAMPLE_DOC_DATA)
    }

    @Test
    fun `call document interactor and show error when error occurred`() {
        given(interactor.getCvDocument()).willReturn(
            Single.error(Throwable("Error"))
        )

        presenter.attachView(view)

        verify(view).showError()
    }

    @Test
    fun `show progress when loading data`() {
        given(interactor.getCvDocument()).willReturn(
            Single.just(SAMPLE_DOC_DATA)
        )

        presenter.attachView(view)

        verify(view).showProgress()
    }

    @Test
    fun `fetch data again when reload is called`() {
        given(interactor.getCvDocument()).willReturn(
            Single.just(SAMPLE_DOC_DATA)
        )
        presenter.attachView(view)
        reset(view)

        presenter.reloadDocument()

        verify(view).showData(SAMPLE_DOC_DATA)
    }

    private companion object {
        val SAMPLE_DOC_DATA = listOf(DocumentDisplayItem.ExtraBigItem("Sir Richard"))
    }
}