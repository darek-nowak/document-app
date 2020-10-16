package com.example.documentapp.data

import android.os.Parcelable
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class DocumentListsInteractor @Inject constructor(
    private val docRepository: GithubRepository
) {
    fun getCvDocumentsList(): Single<List<CvDocumentInfo>> =
        docRepository.fetchDocumentsList()
            .map { files ->
                files.map {
                    CvDocumentInfo(
                        name = it.filename.toApplicantName(),
                        filename = it.filename
                    )
                }
            }

    private fun String.toApplicantName() =
        removeSuffix(".json")
            .split("_")
            .joinToString(" ") { it.capitalize() }
}

@Parcelize
data class CvDocumentInfo(val filename: String, val name: String): Parcelable