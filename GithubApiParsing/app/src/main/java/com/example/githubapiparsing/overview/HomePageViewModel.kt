package com.example.githubapiparsing.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiparsing.network.GitHubApiService
import com.example.githubapiparsing.network.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class UserApiStatus {
    LOADING, DONE, ERROR
}
class HomePageViewModel : ViewModel() {
    private val _ApiResponse = MutableLiveData<String>()
    val ApiResponse : LiveData<String>
    get() = _ApiResponse

    private val _MoveUser = MutableLiveData<Users>()
    val MoveUser : LiveData<Users>
    get() = _MoveUser

    //live data that will be observed in order to display error icon or loading icon
    private val _UserStatus = MutableLiveData<UserApiStatus>()
    val UserStatus : LiveData<UserApiStatus>
    get() = _UserStatus

    //liveData to hold the list of users returned from the Api
    private val _Users = MutableLiveData<List<Users>>()
    val Users : LiveData<List<Users>>
    get() =  _Users

    //coroutine job for asychronous processing
   private var viewModelJob = Job()
   private val coroutineScope2 = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        gitHubApiService()
    }

   private fun gitHubApiService() {

     /*    GitHubApiService.createService().getUsers().enqueue(
          object : Callback<List<Users>>{
              override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                  _ApiResponse.value = "Failure : " + t.message
              }

              override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                  _ApiResponse.value = response.body()?.size.toString()
              }

          }
  )*/
        coroutineScope2.launch {
           var gitHubServ = GitHubApiService.createService().getUsers()
           try {
               _UserStatus.value = UserApiStatus.LOADING
                var TempHold = gitHubServ.await()
              // _ApiResponse.value = TempHold.size.toString()
               _Users.value = TempHold
               _UserStatus.value = UserApiStatus.DONE
           }catch (e : Exception) {
               _ApiResponse.value = "Error ${e.message}"
               _Users.value = ArrayList()
               _UserStatus.value = UserApiStatus.ERROR
           }
       }
    }

    fun diplayUserDetails(user : Users) {
        _MoveUser.value = user
    }

    fun doneDisplayingUser() {
        _MoveUser.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
/* coroutineScope.launch {
           var gitHubService = GitHubApiService.createService().getUsers()
           try {
              var TempHold = gitHubService.await()
               _ApiResponse.value = TempHold.size.toString()
           }catch (e : Exception) {
               _ApiResponse.value = "Error"
           }
       }*/

/*  GitHubApiService.createService().getUsers().enqueue(
      object : Callback<String>{
          override fun onFailure(call: Call<String>, t: Throwable) {
              _ApiResponse.value = "Failure : " + t.message
          }

          override fun onResponse(call: Call<String>, response: Response<String>) {
              _ApiResponse.value = response.body()
          }

      }
  )*/