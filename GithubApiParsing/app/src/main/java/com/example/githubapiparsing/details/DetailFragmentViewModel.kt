package com.example.githubapiparsing.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapiparsing.network.Users

class DetailFragmentViewModel(users : Users) : ViewModel() {
   private val _SelectedUser = MutableLiveData<Users>()
    val SelectedUser : LiveData<Users>
    get() = _SelectedUser
   init {
       _SelectedUser.value = users
   }
}