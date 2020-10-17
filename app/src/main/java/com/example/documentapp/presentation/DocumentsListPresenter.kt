package com.example.documentapp.presentation

import android.util.Log
import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentDisplayItem
import com.example.documentapp.data.DocumentListsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class DocumentsListPresenter @Inject constructor(
    private val docsListInteractor: DocumentListsInteractor
) {
    private var view: DocumentsListView? = null
    private var disposable = Disposables.disposed()

    fun attachView(view: DocumentsListView) {
        this.view = view

        fetchDocumentsListData()
    }

    private fun fetchDocumentsListData() {
        disposable = docsListInteractor.getCvDocumentsList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showProgress() }
            .subscribe(::onData, ::onError)
    }

    private fun onData(data: List<CvDocumentInfo>) {
        view?.showDocsListData(data)
    }

    private fun onError(error: Throwable) {
        view?.showError()
    }

    fun detachView() {
        view = null
        disposable.dispose()
    }
}

interface DocumentsListView {
    fun showDocsListData(data: List<CvDocumentInfo>)
    fun showError()
    fun showProgress()
}
