package x2020.com.posrockettask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import x2020.com.posrockettask.api.Repository

val viewModelModule = module {
        viewModel {
        MainViewModel(get())
    }
}

class MainViewModel(private val repository: Repository) : ViewModel() {

    val discounts by lazy {

        liveData(Dispatchers.IO) {
            val retrievedData = repository.discounts()
            Log.d("retrievedData","retrievedData")
            emit(retrievedData)
        }
    }
    val extraCharges by lazy {

        liveData(Dispatchers.IO) {
            val retrievedData = repository.extraCharges()
            Log.d("retrievedData","retrievedData")
            emit(retrievedData)
        }
    }

    fun doAction() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrievedData = repository.discounts()
            Log.d("retrievedData","$retrievedData")
        }
    }
}