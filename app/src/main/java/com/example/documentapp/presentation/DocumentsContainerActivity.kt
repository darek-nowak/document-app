package com.example.documentapp.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.documentapp.DocumentApplication
import com.example.documentapp.R
import com.example.documentapp.data.CvDocumentInfo
import com.example.documentapp.di.DocumentScreenComponent
import kotlinx.android.synthetic.main.activity_document_container.*

class DocumentsContainerActivity : AppCompatActivity() {

    lateinit var component: DocumentScreenComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document_container)
        setSupportActionBar(toolbar)

        component = DocumentApplication.applicationComponent
            .documentScreenComponent().create()
        component.inject(this)

        DocumentsListFragment.attachIfNeeded(
            R.id.documentContainer,
            supportFragmentManager
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onItemClicked(docInfo: CvDocumentInfo) {
        DocumentFragment.attachFragment(
            R.id.documentContainer,
            supportFragmentManager,
            docInfo
        )
    }
}