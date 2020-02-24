package x2020.com.posrockettask.api

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class Discount(

    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "rate") @SerializedName("rate") val rate: Double
)

data class ExtraCharge(
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @ColumnInfo(name = "rate") @SerializedName("rate") val rate: Double
)

interface POSRocketServiceInterface {
    @GET("/discounts")
    suspend fun discounts(): List<Discount?>?

    @GET("/extra_charges")
    suspend fun extraCharges(): List<ExtraCharge?>?
}