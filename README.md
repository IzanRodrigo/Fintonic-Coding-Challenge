# Fintonic Test Challenge

## Technical decisions

The architecture used is based on MVP (Model View Presenter).
- _Model_: The repository uses one or more controllers (Retrofit client, DB Dao, etc) to manage the application data and business logic.
- _View_: Doesn't manage any business logic, just the UI widgets and the navigation between screens.
- _Presenter_: It acts as a bridge between the _View_ and the _Model_. Could listen to _Model_ changes to update the _View_.

Since the app is written entirely in Kotlin, I decided to use the following libraries:
- [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) to handle the background work. 
- [Koin](https://insert-koin.io/) to handle the Dependency Injection.

Besides, the following libraries were used:
- [Retrofit](https://square.github.io/retrofit/) as the HTTP client using [Moshi](https://github.com/square/moshi) as JSON converter.
- [Glide](https://bumptech.github.io/glide/) to load the images from network.
- [Room](https://developer.android.com/topic/libraries/architecture/room) to persist the information in a database.
