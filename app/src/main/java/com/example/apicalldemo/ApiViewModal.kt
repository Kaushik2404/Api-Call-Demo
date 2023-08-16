package com.example.apicalldemo

import androidx.lifecycle.ViewModel
import com.example.apicalldemo.modal.DemoModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApiViewModal: ViewModel() {

    lateinit var response:DemoModal

    fun callingAPi():DemoModal{

        val apiService: ApiInterface = ServiceGen.buildService(ApiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
          response = apiService.getPopularMovies()
        }
        return response
    }

}