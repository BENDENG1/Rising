package com.softsquared.template.kotlin.src.Buy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderFinishBinding
import com.softsquared.template.kotlin.src.Buy.models.CartDeleteResponse
import com.softsquared.template.kotlin.src.Buy.models.CartPatchResponse
import com.softsquared.template.kotlin.src.Buy.models.CartResponse
import com.softsquared.template.kotlin.src.Buy.models.OrderResponse
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragment
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat

class OrderFinishFragment : BaseFragment<FragmentOrderFinishBinding> (FragmentOrderFinishBinding::bind , R.layout.fragment_order_finish)
    ,BuyFragmentInterface{


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)




        binding.tvOrderFinishAddress.text = "서울시 서울구 서울동\n서울아파트 101동 101호"

        binding.btnOrderFinishClose.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, ShoppingFragment()).commit()
            mainAct.bottomNavigationHide(false)
        }
        binding.btnOrderFinishShopping.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, ShoppingFragment()).commit()
            mainAct.bottomNavigationHide(false)
        }
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)
    }

    override fun onGetCartsSuccess(response: CartResponse) {
        val dec = DecimalFormat("###,###,###")

        binding.tvOrderFinishPrice.text = dec.format(response.result.totalPrice)
        binding.tvOrderFinishPrice2.text = dec.format(response.result.totalPrice)
        binding.tvOrderFinishProductName.text = response.result.cartList[0].productName
    }

    override fun onGetCartsFailure(message: String) {
    }

    override fun onOrderSuccess(response: OrderResponse) {
    }

    override fun onOrderFailure(message: String) {
    }

    override fun onGetProductSuccess(response: ProductsResponse) {
    }

    override fun onGetProductFailure(message: String) {
    }

    override fun onPatchCartSuccess(response: CartPatchResponse) {
    }

    override fun onPatchCartFailure(message: String) {
    }

    override fun onDeleteCartSuccess(response: CartDeleteResponse) {
    }

    override fun onDeleteCartFailure(message: String) {
    }
}