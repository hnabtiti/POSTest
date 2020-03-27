package x2020.com.posrockettask.database.room.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import x2020.com.posrockettask.api.Product

@Entity(tableName = "product")
data class ProductEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @Embedded(prefix = "discount_") var discount: DiscountEntity? = null,
    @Embedded(prefix = "extra_charge_") var extraCharge: ExtraChargeEntity? = null,
    @PrimaryKey @ColumnInfo(name = "productId") val productId: String = "$name$price"

)

@Entity(
    tableName = "Orders",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class OrdersEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    @ColumnInfo(name = "customerId") val customerId: Int,
    @ColumnInfo(name = "productId") val productId: String
)

@Entity(tableName = "discount", primaryKeys = ["name", "rate"])
data class DiscountEntity(
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "rate") @SerializedName("rate") val rate: Double
)

@Entity(tableName = "extra_charge", primaryKeys = ["name", "rate"])
data class ExtraChargeEntity(
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "rate") @SerializedName("rate") val rate: Double
)
