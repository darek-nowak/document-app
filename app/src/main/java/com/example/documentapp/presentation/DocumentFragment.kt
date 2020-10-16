package com.example.documentapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.documentapp.R
import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.data.DocumentDisplayItem
import kotlinx.android.synthetic.main.fragment_cv_document.*
import javax.inject.Inject

class DocumentFragment : Fragment(), DocumentView {

    @Inject
    lateinit var presenter: DocumentPresenter

    @Inject
    lateinit var documentAdapter: DocumentAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as DocumentsContainerActivity).component.inject(this)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
        val docInfo = arguments!!.getParcelable<CvDocumentInfo>(DOC_INFO_ARG)!!
        presenter.attachView(this, docInfo)
    }

    override fun setTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_cv_document, container, false)

    private fun initRecyclerView() {
        cvDocumentList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = documentAdapter
        }
    }

    override fun showData(result: List<DocumentDisplayItem>) {
        documentAdapter.setItems(result)
        cvDocumentList.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    companion object {
        private const val DOC_INFO_ARG = "doc_info_arg"

        fun attachFragment(
            @IdRes containerViewId: Int,
            fragmentManager: FragmentManager,
            docInfo: CvDocumentInfo
        ) {
            fragmentManager
                .beginTransaction()
                .replace(containerViewId, newInstance(docInfo))
                .addToBackStack(null)
                .commit()
        }

        private fun newInstance(
            docInfo: CvDocumentInfo
        ) = DocumentFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DOC_INFO_ARG, docInfo)
            }
        }
    }
}