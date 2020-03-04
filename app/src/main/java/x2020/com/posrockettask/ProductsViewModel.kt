package x2020.com.posrockettask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import x2020.com.posrockettask.api.Discount
import x2020.com.posrockettask.api.ExtraCharge
import x2020.com.posrockettask.api.Repository

data class Product(
    val name: String,
    val price: Double,
    var discount: Discount? = null,
    var extraCharge: ExtraCharge? = null
)

val productsViewModel = module {
    viewModel {
        ProductsViewModel(get())
    }
}

class ProductsViewModel(private val repository: Repository) : ViewModel() {

    private val productsList =
        listOf(
            Product("product1", 500.0),
            Product("product2", 400.0),
            Product("product3", 300.0),
            Product("product4", 200.0),
            Product("product5", 100.0),
            Product("product6", 60.0)
        )

    val products by lazy {
        liveData(Dispatchers.IO) {
            val discounts = repository.discounts()
            val extraCharges = repository.extraCharges()
            productsList[0].discount = discounts?.firstOrNull()
            productsList[1].discount = discounts?.firstOrNull()
            productsList[2].discount = discounts?.firstOrNull()
            productsList[3].discount = discounts?.firstOrNull()
            productsList[4].discount = discounts?.firstOrNull()

            productsList[0].extraCharge = extraCharges?.firstOrNull()
            productsList[1].extraCharge = extraCharges?.firstOrNull()
            productsList[2].extraCharge = extraCharges?.firstOrNull()
            productsList[3].extraCharge = extraCharges?.firstOrNull()
            productsList[4].extraCharge = extraCharges?.firstOrNull()

            emit(productsList)
        }
    }

}
