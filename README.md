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
<img src="https://user-images.githubusercontent.com/13196689/115075675-e2e8c500-9f14-11eb-9371-9c9a70e76cc7.png" width="32%"/>
 <img src="https://user-images.githubusercontent.com/13196689/115075717-f3993b00-9f14-11eb-9789-ee45cd816fc4.png" width="32%"/>
 <img src="https://user-images.githubusercontent.com/13196689/115076302-d0bb5680-9f15-11eb-8b37-1b0ed65690fc.png" width="32%"/>
</p>
<p align="left">
<img src="https://user-images.githubusercontent.com/13196689/115076279-c9944880-9f15-11eb-88b2-4b66ecf589af.png" width="32%"/>
 <img src="https://user-images.githubusercontent.com/13196689/115075728-f85def00-9f14-11eb-889c-94211fc070c2.png" width="32%"/>
 <img src="https://user-images.githubusercontent.com/13196689/115075738-fc8a0c80-9f14-11eb-941f-e88a1434de1e.png" width="32%"/>
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
  - Firebase cloud Function code can be found at https://github.com/shakil807g/ScopicTest/blob/master/FirebaseCloud/functions/index.js  But since Cloud Function are only supported  on paid Firebase plan it's not deploy on Firebase.  

- Data Sources
  - Firebase is used to save text list
  - Jetpack DataStore is used to store local text list 

