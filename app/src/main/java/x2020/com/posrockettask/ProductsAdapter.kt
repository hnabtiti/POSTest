package x2020.com.posrockettask

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product.view.*


class ProductsAdapter(private val list: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.product, parent, false)) {
    private val productName: TextView? = itemView.productName
    private val productPrice: TextView? = itemView.productPrice

    private val priceAfterDiscount: TextView? = itemView.priceAfterDiscount
    private val priceAfterExtraCharge: TextView? = itemView.priceAfterExtraCharge

    private val discountName: TextView? = itemView.discountName
    private val extraChargeName: TextView? = itemView.extraChargeName

    private val totalPrice: TextView? = itemView.totalPrice


    fun bind(product: Product) {
        productName?.text = product.getNameToShow()
        productPrice?.text = product.getPriceToShow()

        priceAfterDiscount?.text = product.getPriceAfterDiscount()
        priceAfterExtraCharge?.text = product.getPriceAfterExtraCharge()

        discountName?.text = product.getDiscountNameToShow()
        extraChargeName?.text = product.getExtraChargeNameToShow()
        totalPrice?.text = product.getTotalPriceToShow()

    }

}

private fun Product.getTotalPriceToShow(): CharSequence? {
    return "total price : ${price - discountValue + extraChargeValue}"

}

private fun Product.getDiscountNameToShow(): CharSequence? =
    "Discount Name : ${this.discount?.name} rate : ${this.discount?.rate}"

private fun Product.getExtraChargeNameToShow(): CharSequence? =
    "Extra Charge Name : ${this.extraCharge?.name} rate : ${this.extraCharge?.rate}"

private fun Product.getPriceAfterExtraCharge(): CharSequence? {
    return "price after Extra Charge : ${this.price + this.extraChargeValue} $"
}

private fun Product.getPriceAfterDiscount(): CharSequence? {
    return "price after discount : ${this.price - this.discountValue} $"
}

private fun Product.getPriceToShow(): CharSequence? = "price : ${this.price} $"

private fun Product.getNameToShow(): CharSequence? = "name : ${this.name}"

private val Product.discountValue: Double
    get() {
        val rate = discount?.rate ?: 1.0
        return this.price * rate
    }
private val Product.extraChargeValue: Double
    get() {
        val rate = extraCharge?.rate ?: 1.0
        return price * rate
    }