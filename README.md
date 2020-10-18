document-app
=========

Application demonstrating Android tools of the trade for creating simple two fragment application.
It that fetches list of document CV filenames from github and presents it on list fragment and details screen
fragment.

What was done:
- Dagger 2 used for dependencies injections
- Retrofit with adapter to RxJava used for network calls
- MVP with View, Presenter, Interactor used for separating responsibillities
- Used scoped Interactors for not repeating network calls

Libraries used:
```groovy
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation 'com.squareup.retrofit2:converter-jackson:2.5.0'
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
implementation 'io.reactivex.rxjava2:rxjava:2.2.19'
implementation 'com.google.dagger:dagger:2.28.3'
kapt 'com.google.dagger:dagger-compiler:2.28.3'
```

Test libraries used:
```groovy
testImplementation 'org.mockito:mockito-inline:2.18.3'
testImplementation 'com.nhaarman:mockito-kotlin-kt1.1:1.5.0'
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.3.1'
testImplementation 'com.google.truth:truth:1.0.1'
```
