package com.example.documentapp.presentation
import com.example.documentapp.data.DocumentDisplayItem
import com.example.documentapp.data.DocumentInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import javax.inject.Inject

class DocumentPresenter @Inject constructor(
    private val docInteractor: DocumentInteractor
) {
    private var view: DocumentView? = null
    private var disposable = Disposables.disposed()

    fun attachView(view: DocumentView) {
        this.view = view

        fetchDocumentData()
    }

    private fun fetchDocumentData() {
        disposable = docInteractor.getCvDocument()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showProgress() }
            .subscribe(
                { data -> view?.showData(data) },
                { view?.showError() }
            )
    }

    fun dettachView() {
        view = null
        disposable.dispose()
    }

    fun reloadDocument() {
        disposable.dispose()
        fetchDocumentData()
    }
}

interface DocumentView {
    fun showData(data: List<DocumentDisplayItem>)
    fun showError()
    fun showProgress()
}
