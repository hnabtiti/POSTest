package x2020.com.posrockettask.api


import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import x2020.com.posrockettask.database.room.entities.DiscountEntity
import x2020.com.posrockettask.database.room.entities.ExtraChargeEntity
import x2020.com.posrockettask.database.room.entities.OrdersEntity
import x2020.com.posrockettask.database.room.entities.ProductEntity

data class Product(
    val name: String,
    val price: Double,
    var discount: Discount? = null,
    var extraCharge: ExtraCharge? = null,
    val productId: String = "$name$price"

)

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        name,
        price,
        discount?.toDiscountEntity(),
        extraCharge?.toExtraChargeEntity()
    )
}

data class Orders(
    val orderId: Int = 0,
    val customerId: Int,
    val productId: String
)

val Orders.toOrderEntity: OrdersEntity
    get() {
        return OrdersEntity(orderId, customerId, productId)
    }

data class Discount(
    @SerializedName("name") val name: String,
    @SerializedName("rate") val rate: Double
)

fun Discount.toDiscountEntity(): DiscountEntity {
    return DiscountEntity(name, rate)
}

data class ExtraCharge(
    @SerializedName("name") val name: String,
    @SerializedName("rate") val rate: Double
)

fun ExtraCharge.toExtraChargeEntity(): ExtraChargeEntity {
    return ExtraChargeEntity(name, rate)
}


interface POSRocketServiceInterface {
    @GET("/discounts")
    suspend fun discounts(): List<Discount>?

    @GET("/extra_charges")
    suspend fun extraCharges(): List<ExtraCharge>?
}