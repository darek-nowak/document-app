package com.example.documentapp.presentation

import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentListsInteractor
import com.example.documentapp.data.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
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
            .subscribe { result ->
                when (result) {
                    is Result.Success -> onData(result.data)
                    Result.Error -> onError()
                }
            }
    }

    private fun onData(data: List<CvDocumentInfo>) {
        view?.showDocsListData(data)
    }

    private fun onError() {
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
