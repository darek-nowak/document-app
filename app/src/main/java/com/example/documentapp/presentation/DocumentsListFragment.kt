package com.example.documentapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.documentapp.R
import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.di.DocumentScreenComponentHolder
import kotlinx.android.synthetic.main.fragment_document_list.*
import kotlinx.android.synthetic.main.fragment_document_list.errorView
import kotlinx.android.synthetic.main.fragment_document_list.progressBar
import javax.inject.Inject

class DocumentsListFragment: Fragment(), DocumentsListView {

    @Inject
    lateinit var presenter: DocumentsListPresenter
    @Inject
    lateinit var documentsListAdapter: DocumentsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_document_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        DocumentScreenComponentHolder.getComponent(requireActivity()).inject(this)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        initDocsInfoRecyclerView()
        setTitle()

        presenter.attachView(this)
    }

    private fun setTitle() {
        (activity as AppCompatActivity).supportActionBar?.title =  getString(R.string.cv_documents_list)
    }

    private fun initDocsInfoRecyclerView() {
        allDocumentsList.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = documentsListAdapter
        }
    }

    override fun showDocsListData(data: List<CvDocumentInfo>) {
        documentsListAdapter.setItems(data)
        documentsListAdapter.onItemClicked = { (activity as DocumentsContainerActivity).onItemClicked(it)}

        allDocumentsList.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "doc_list_fragment_tag"

        fun attachIfNeeded(
            @IdRes containerViewId: Int,
            fragmentManager: FragmentManager
        ) {
            if (fragmentManager.findFragmentByTag(TAG) == null) {
                fragmentManager
                    .beginTransaction()
                    .add(containerViewId, DocumentsListFragment(), TAG)
                    .commit()
            }
        }
    }
}