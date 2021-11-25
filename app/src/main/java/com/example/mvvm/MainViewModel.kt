package com.example.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.network.ApiClient
import com.example.mvvm.network.Character
import com.example.mvvm.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainViewModel(private val repository: Repository = Repository(ApiClient.apiService)) : ViewModel() {


    private var charactersLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    val characterLiveData : LiveData<ScreenState<List<Character>?>>
    get() = charactersLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter(){
        val client = repository.getCharacters("1")
        charactersLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : Callback<CharacterResponse>{


            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful){
                    charactersLiveData.postValue(ScreenState.Success(response.body()?.result))

                } else {
                    charactersLiveData.postValue(ScreenState.Error(response.code().toString(), null))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                //Log.d("Failure", t.message.toString())
                charactersLiveData.postValue(ScreenState.Error(t.message, null))
            }
        })
    }

}