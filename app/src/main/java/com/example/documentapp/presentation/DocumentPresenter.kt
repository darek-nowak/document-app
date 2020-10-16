package com.example.documentapp.presentation

import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentDisplayItem
import com.example.documentapp.data.DocumentInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class DocumentPresenter @Inject constructor(
    private val docInteractor: DocumentInteractor
) {
    private var view: DocumentView? = null
    private var disposable = Disposables.disposed()

    fun attachView(view: DocumentView, docInfo: CvDocumentInfo) {
        this.view = view

        view.setTitle(docInfo.name)
        fetchDocumentData(docInfo.filename)
    }

    private fun fetchDocumentData(filename: String) {
        disposable = docInteractor.getCvDocument(filename)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showProgress() }
            .subscribe(
                { data -> view?.showData(data) },
                { view?.showError() }
            )
    }

    fun detachView() {
        view = null
        disposable.dispose()
    }

}

interface DocumentView {
    fun showData(data: List<DocumentDisplayItem>)
    fun showError()
    fun showProgress()
    fun setTitle(title: String)
}