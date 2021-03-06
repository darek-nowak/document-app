package com.example.documentapp.di

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.documentapp.DocumentApplication
import com.example.documentapp.data.GitHubApi
import com.example.documentapp.presentation.DocumentFragment
import com.example.documentapp.presentation.DocumentsContainerActivity
import com.example.documentapp.presentation.DocumentsListFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit
import javax.inject.Scope

@DocumentActivityScope
@Subcomponent
interface DocumentScreenComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DocumentScreenComponent
    }

    fun inject(documentFragment: DocumentFragment)
    fun inject(documentsListFragment: DocumentsListFragment)
}

class DocumentScreenComponentHolder : ViewModel() {
    val component = DocumentApplication.applicationComponent
        .documentScreenComponent().create()

    companion object {
        fun getComponent(activity: ComponentActivity): DocumentScreenComponent {
            val holder: DocumentScreenComponentHolder by activity.viewModels()
            return holder.component
        }
    }
}

@Module(subcomponents = [ DocumentScreenComponent::class ])
class DocumentScreenModule {
    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi = retrofit.create(GitHubApi::class.java)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DocumentActivityScope
