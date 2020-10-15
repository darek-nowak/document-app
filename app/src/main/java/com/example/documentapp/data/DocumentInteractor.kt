package com.example.documentapp.data

import io.reactivex.Single
import javax.inject.Inject

class DocumentInteractor @Inject constructor(
    private val docRepository: GithubRepository
) {
    fun getCvDocument(): Single<List<DocumentDisplayItem>> = docRepository.fetchDocument()
        .map { cvData -> cvData.toDocumentDisplayItems() }

    private fun CvData.toDocumentDisplayItems() = mutableListOf(
        DocumentDisplayItem.ExtraBigItem(applicant),
        DocumentDisplayItem.ExtraBigItem(currentRole),
        DocumentDisplayItem.Item(description),
        DocumentDisplayItem.Header("Professional Experience")
    ).apply {
        experience.forEach { item ->
            add(DocumentDisplayItem.ParagraphItem(item.startDate + " - " + item.endDate.addIfEmpty("till now")))
            add(DocumentDisplayItem.BigItem(item.company))
            add(DocumentDisplayItem.BigItem(item.role))
            item.responsibilities.forEach {
                add(DocumentDisplayItem.Item(it))
            }
        }
    }

    private fun String.addIfEmpty(name: String) = if (this.isBlank()) name else this
}

sealed class DocumentDisplayItem {
    abstract val name: String
    data class ExtraBigItem(override val name: String): DocumentDisplayItem()
    data class BigItem(override val name: String): DocumentDisplayItem()
    data class ParagraphItem(override val name: String): DocumentDisplayItem()
    data class Header(override val name: String): DocumentDisplayItem()
    data class Item(override val name: String): DocumentDisplayItem()
}