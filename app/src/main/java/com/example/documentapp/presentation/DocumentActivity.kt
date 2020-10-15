package com.example.documentapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.documentapp.DocumentApplication
import com.example.documentapp.R
import com.example.documentapp.data.DocumentDisplayItem
import kotlinx.android.synthetic.main.activity_document.*
import kotlinx.android.synthetic.main.error_view_layout.*
import javax.inject.Inject

class DocumentActivity : AppCompatActivity(), DocumentView {

    @Inject
    lateinit var presenter: DocumentPresenter
    @Inject
    lateinit var documentAdapter: DocumentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)
        setSupportActionBar(toolbar)

        DocumentApplication.applicationComponent
            .documentScreenComponent().create()
            .inject(this)

        presenter.attachView(this)
        reloadDocument.setOnClickListener { presenter.reloadDocument() }
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView() {
        documentList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = documentAdapter
        }
    }

    override fun showData(result: List<DocumentDisplayItem>) {
        documentAdapter.setItems(result)
        documentList.visibility = VISIBLE
        progressBar.visibility = GONE
        errorView.visibility = GONE
    }

    override fun showError() {
        errorView.visibility = VISIBLE
        documentList.visibility = GONE
        progressBar.visibility = GONE
    }

    override fun showProgress() {
        progressBar.visibility = VISIBLE
        documentList.visibility = GONE
        errorView.visibility = GONE
    }

    override fun onDestroy() {
        presenter.dettachView()
        super.onDestroy()
    }
}