package x2020.com.posrockettask.database.sqldelight


import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.dsl.module.module
import x2020.com.posrockettask.Database
import x2020.com.posrockettask.api.Discount
import x2020.com.posrockettask.api.ExtraCharge
import x2020.com.posrockettask.api.Product

val sqlDeliDatabase = module {
    single { getSqlDeliDatabase(get()) }
}

fun getSqlDeliDatabase(context: Context): SqlDeliDatabase {
    return SqlDeliDatabase(context)
}

class SqlDeliDatabase(private val applicationContext: Context) {
    private val androidSqlDriver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = applicationContext,
        name = "testSqlDeLite.db"
    )
    private val productQueries = Database(androidSqlDriver).productQueries

    fun insertProduct(product: Product) {
        productQueries.insertOrReplace(
            product.productId,
            product.name,
            product.price,
            product.discount?.name,
            product.discount?.rate,
            product.extraCharge?.name,
            product.extraCharge?.rate
        )
    }

    fun insertAll(products: List<Product>) {
        productQueries.transaction {
            products.forEach { product ->
                productQueries.insertOrReplace(
                    product.productId,
                    product.name,
                    product.price,
                    product.discount?.name,
                    product.discount?.rate,
                    product.extraCharge?.name,
                    product.extraCharge?.rate
                )
            }
        }
    }

    fun deleteAllProducts() = productQueries.empty()

    fun selectAllProduct(): List<Product> {
        return productQueries.selectAll().executeAsList().map {
            it.toProduct()
        }
    }

    fun selectProductByName(name: String): List<Product> {
        return return productQueries.selectByName(name).executeAsList().map {
            it.toProduct()
        }
    }
}

private fun x2020.com.posrockettask.Product.toProduct(): Product {
    val discount = Discount(discount_name ?: "", discount_rate ?: 0.0)
    val extraCharge = ExtraCharge(extra_charge_name ?: "", extra_charge_rate ?: 0.0)
    return Product(name, price, discount, extraCharge)
}
