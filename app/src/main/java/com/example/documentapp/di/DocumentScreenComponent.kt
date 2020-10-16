package com.example.documentapp.di

import com.example.documentapp.presentation.DocumentsContainerActivity
import com.example.documentapp.data.GitHubApi
import com.example.documentapp.presentation.DocumentFragment
import com.example.documentapp.presentation.DocumentsListFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit

@Subcomponent
interface DocumentScreenComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DocumentScreenComponent
    }

    fun inject(activity: DocumentsContainerActivity)
    fun inject(documentFragment: DocumentFragment)
    fun inject(documentsListFragment: DocumentsListFragment)

}

@Module(subcomponents = [ DocumentScreenComponent::class ])
class DocumentScreenModule {
    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)
}