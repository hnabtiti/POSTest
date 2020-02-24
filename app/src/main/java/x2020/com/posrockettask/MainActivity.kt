package x2020.com.posrockettask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import x2020.com.posrockettask.database.AppDatabase

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val appDatabase: AppDatabase by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.discounts.observe(this, Observer { data ->
            Log.d("retrievedData", "$data")
            data?.forEach {
                it?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.discountDao().insertAll(it)

                    }

                }

            }
        })

        mainViewModel.doAction()
        mainViewModel.doAction()
        mainViewModel.doAction()

    }
}
