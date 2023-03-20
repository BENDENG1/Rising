package com.softsquared.template.kotlin.src.Buy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentOrderBinding
import com.softsquared.template.kotlin.src.Buy.models.*
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat


class OrderFragment:BaseFragment<FragmentOrderBinding>(FragmentOrderBinding::bind, R.layout.fragment_order)
    ,BuyFragmentInterface{

    private val purchaseIconList = arrayListOf<PurchaseIconData>()
    private var beforeClick = 0 //결제 수단 전 클릭
    val userNum = ApplicationClass.sSharedPreferences.getInt("userNum",0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)


        BuyService(this).tryGetCarts(userNum)

        binding.btnPurchasePaymentFinish.setOnClickListener {
            if(binding.cbPurchaseAgree.isChecked){
                BuyService(this).tryPostPurchase(userNum = userNum)
            }
            else{
                binding.cbPurchaseAgree.setButtonDrawable(R.drawable.order_red_border)
                binding.tvPurchaseEssentialAgree.visibility = View.VISIBLE
            }
        }
        binding.cbPurchaseAgree.setOnClickListener {
            if(binding.cbPurchaseAgree.isChecked){
                binding.tvPurchaseEssentialAgree.visibility = View.GONE
            }else{
                binding.tvPurchaseEssentialAgree.visibility = View.VISIBLE
                binding.cbPurchaseAgree.setButtonDrawable(R.drawable.order_red_border)
            }
        }
        purchaseIconRecyclerview()
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)
    }

    @SuppressLint("Recycle")
    private fun purchaseIconRecyclerview(){

        val purchaseIconArray  = resources.obtainTypedArray(R.array.purchase_icon_list)
        val purchaseTitleArray = resources.getStringArray(R.array.purchase_title_list)
        val purchaseSubArray = resources.getStringArray(R.array.purchase_subTitle_list)

        for(i in purchaseTitleArray.indices){
            purchaseIconList.add(PurchaseIconData(purchaseIconArray.getResourceId(i,-1),purchaseTitleArray[i],purchaseSubArray[i]))
        }

        binding.rvPurchaseIcon.apply {
            adapter = PurchaseIconAdapter(purchaseIconList, object : ItemClickListener<PurchaseIconData>{
                override fun onItemClick(pos: Int, item: PurchaseIconData) {
                    showpurchaseWay(pos)
                }
            })
            layoutManager = GridLayoutManager(context,4)
        }
    }

    //결제수단 색빠뀌게 하는것 그리고 업애기.
    private fun showpurchaseWay(pos : Int){
        val purchaseWayArray : Array<String> = resources.getStringArray(R.array.purchase_way)

        binding.rvPurchaseIcon[beforeClick].setBackgroundResource(R.drawable.purchase_gray_border)
        binding.rvPurchaseIcon[pos].setBackgroundResource(R.drawable.purchase_bgskyblue_borderskyblue)
        beforeClick = pos

        if(purchaseWayArray[pos] != "x"){
            binding.tvPurchaseWay.visibility = View.VISIBLE
            binding.tvPurchaseWay.text = purchaseWayArray[pos]
        }else{
            binding.tvPurchaseWay.visibility = View.GONE
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onGetCartsSuccess(response: CartResponse) {
        val dec = DecimalFormat("###,###,###")
        binding.rvPurchaseOrderProduct.apply {
            adapter = OrderListAdapter(response.result.cartList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
        binding.tvPurchasePaymentSum.text = dec.format(response.result.totalPrice) + " 원"
        binding.tvPurchasePaymentSum2.text = dec.format(response.result.totalPrice)
        binding.btnPurchasePaymentFinish.text = dec.format(response.result.totalPrice) + " 원 결제하기"
        binding.tvPurchaseEarnPoint.text = dec.format(response.result.totalPrice / 100)

    }

    override fun onGetCartsFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onOrderSuccess(response: OrderResponse) {
        parentFragmentManager.beginTransaction().replace(R.id.main_frm, OrderFinishFragment()).commit()
    }

    override fun onOrderFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetProductSuccess(response: ProductsResponse) {}

    override fun onGetProductFailure(message: String) {}

    override fun onPatchCartSuccess(response: CartPatchResponse) {}

    override fun onPatchCartFailure(message: String) {}
    override fun onDeleteCartSuccess(response: CartDeleteResponse) {
    }

    override fun onDeleteCartFailure(message: String) {
    }
}