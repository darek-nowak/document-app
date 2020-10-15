package com.example.documentapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val api: GitHubApi,
    private val base64Decoder: Base64Decoder,
    private val jsonObjectMapper: ObjectMapper
) {
    fun fetchDocument(): Single<CvData> {
        return api.getFileContent(file = "dariusz_nowak.json")
            .map { response ->
                base64Decoder.decode(response.content)
            }
            .map {
                jsonObjectMapper.readValue(it, CvData::class.java)
            }
    }
}

public interface GitHubApi {
    @GET("users/$USER/repos")
    fun listRepos(): Single<List<Repo>>

    @GET("repos/$USER/$DOC_REPO/contents/{file}")
    fun getFileContent(@Path("file") file: String): Single<Content>

    companion object {
        const val USER = "darek-nowak"
        const val DOC_REPO = "json"
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Repo(
    @JsonProperty("name") val name: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Content(
    @JsonProperty("encoding") val encoding: String,
    @JsonProperty("content") val content: String
)

data class CvData(
    @JsonProperty("applicant") val applicant: String,
    @JsonProperty("currentRole") val currentRole: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("experience") val experience: List<ExperienceItem>
)

data class ExperienceItem(
    @JsonProperty("startDate") val startDate: String,
    @JsonProperty("endDate") val endDate: String,
    @JsonProperty("company") val company: String,
    @JsonProperty("role") val role: String,
    @JsonProperty("responsibilities") val responsibilities: List<String>
)
