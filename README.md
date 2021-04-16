<h1 align="center">Scopic Task</h1></br>
<p align="center">  
 Scopic Test Using Firebase 
</p>
</br>

<p align="center">
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

## Download
Go to the [Releases](https://github.com/shakil807g/ScopicTest/releases) to download the latest APK.

## Screenshots
<p align="left">
<img src="/demo/demo.gif" width="32%"/>
</p>


## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt (alpha) for dependency injection.
- JetPack
  - Kotlin Coroutines (https://kotlinlang.org/docs/reference/coroutines-overview.html) (Asynchronous programming)
  - Compose - A modern toolkit for building native Android UI.
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Moshi - (https://github.com/square/moshi) (Kotlin JSON library for Android from Square)
  - Accompanist - (https://github.com/chrisbanes/accompanist/tree/master/coil) (Image Loading with coil)

- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern

- Firebase
  - Firebase Auth
  - Firebase FireStore

- Firebase Clould Functions
  - Firebase cloud Function code can be found at https://github.com/shakil807g/ScopicTest/blob/master/FirebaseCloud/functions/index.js  But since Cloud Function are only supported   on paid Firebase plan it's not deploy on Firebase.  


