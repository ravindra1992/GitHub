# GitHub
This android application depicts the recommended architectural pattern(MVVM) for developing android apps. 

The app data is fetched from https://api.github.com/users then the JSON objects is parsed into Kotlin Object through Retrofit and MOSHI library. The users' avatars are loaded into an image view using Glide library, and the images are shown on the app through a recylerview adapter.  An onClick listener is implemented on the recyclerview to navigate to a detail fragment where the user avatar and login name is then displayed.
