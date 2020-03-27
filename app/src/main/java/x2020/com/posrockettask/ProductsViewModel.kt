package x2020.com.posrockettask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import x2020.com.posrockettask.api.*
import x2020.com.posrockettask.api.Orders
import x2020.com.posrockettask.api.Product
import x2020.com.posrockettask.database.room.RoomAppDatabase
import x2020.com.posrockettask.database.sqldelight.SqlDeliDatabase


val productsViewModel = module {
    viewModel {
        ProductsViewModel(get(), get(), get())
    }
}

class ProductsViewModel(
    private val repository: Repository,
    private val roomDatabaseRepository: RoomAppDatabase,
    private val sqlDeliDatabase: SqlDeliDatabase
) :
    ViewModel() {

    private val productsList = mutableListOf<Product>()

    val products by lazy {
        liveData(Dispatchers.IO) {

            val discounts = repository.discounts()
            val extraCharges = repository.extraCharges()
            discounts?.let {
                roomDatabaseRepository.discountDao()
                    .insertAll(it.map { discount -> discount.toDiscountEntity() })

            }
            extraCharges?.let {
                roomDatabaseRepository.extraChargeDao()
                    .insertAll(it.map { extraCharge -> extraCharge.toExtraChargeEntity() })
            }

            logWithTime("---------------start----------------------")
            roomDatabaseRepository.productDao().nukeTable()
            logWithTime("room delete all data in table")
            sqlDeliDatabase.deleteAllProducts()
            logWithTime("sql delight delete all data in table")
            (0..100000).forEach {
                val product = Product(
                    "product$it",
                    it * 10.0,
                    discounts?.firstOrNull(),
                    extraCharges?.firstOrNull()
                )
                productsList.add(product)
            }
            logWithTime("creation of list with 1000 00 item")

            roomDatabaseRepository.productDao()
                .insertAll(productsList.map { product -> product.toProductEntity() })
            logWithTime(" room insert all time")


            (100000..100100).forEach {
                roomDatabaseRepository.productDao().insert(
                    Product(
                        "product$it",
                        it * 10.0,
                        discounts?.firstOrNull(),
                        extraCharges?.firstOrNull()
                    ).toProductEntity()
                )
            }
            logWithTime(" room insert one  100 times time ")


            roomDatabaseRepository.productDao().getAll()?.forEach { product ->
                //    Log.d("productFromRoom :", "$product")
            }
            logWithTime("room select all time :")


            logWithTime(
                "sqlDeli  select product1  ${roomDatabaseRepository.productDao().loadAllByName(
                    listOf("product1")
                )}"
            )

            sqlDeliDatabase.insertAll(productsList)
            logWithTime(" sqlDeli insert all time")
            (100000..100100).forEach {
                sqlDeliDatabase.insertProduct(
                    Product(
                        "product$it",
                        it * 10.0,
                        discounts?.firstOrNull(),
                        extraCharges?.firstOrNull()
                    )
                )
            }
            logWithTime(" sqlDeli insert one  100 times time ")


            sqlDeliDatabase.selectAllProduct()
            logWithTime("sqlDeli  select all ")

            val product1 = sqlDeliDatabase.selectProductByName("product1")
            logWithTime("sqlDeli  select product1  $product1")
            product1.forEach {
                val order = Orders(customerId = 123, productId = it.productId)
                roomDatabaseRepository.orderDao().insert(order.toOrderEntity)
            }
            emit(productsList)
        }

    }
    var currentTime = System.currentTimeMillis()
    private fun logWithTime(message: String) {
        Log.d(
            "testDatabase :",
            "$message  ${System.currentTimeMillis() - currentTime}"
        )
        currentTime = System.currentTimeMillis()
    }

}



