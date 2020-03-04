package x2020.com.posrockettask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.discound_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() {
    private val productsViewModel: ProductsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.discound_fragment, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        productsViewModel.products.observe(this, observer)
    }

    private val observer = Observer<List<Product>> { products ->
        productRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            productRecyclerView.adapter = ProductsAdapter(products)
        }
    }

}
