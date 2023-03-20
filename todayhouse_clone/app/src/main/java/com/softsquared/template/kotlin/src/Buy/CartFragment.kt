package com.softsquared.template.kotlin.src.Buy

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.BuyCartListBinding
import com.softsquared.template.kotlin.databinding.FragmentCartBinding
import com.softsquared.template.kotlin.src.Buy.models.*
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.shopping.Detail.DetailFragment
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.shopping.ShoppingFragment
import com.softsquared.template.kotlin.src.main.shopping.ShoppingProductsAdapter
import com.softsquared.template.kotlin.src.main.shopping.models.ProductResult
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat

class CartFragment:BaseFragment<com.softsquared.template.kotlin.databinding.FragmentCartBinding>(FragmentCartBinding::bind, R.layout.fragment_cart)
    ,BuyFragmentInterface {

    val userNum = ApplicationClass.sSharedPreferences.getInt("userNum",0)
    private var totalcount = 0
    private var totalSum = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)


        BuyService(this).tryGetCarts(userNum = userNum)
        BuyService(this).tryGetProducts()

        binding.btnCartOrder.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.main_frm, OrderFragment()).addToBackStack(null).commitAllowingStateLoss()
        }

        binding.btnCartGoShopping.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, ShoppingFragment()).commit()

        }

        binding.ivCartClose.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onGetCartsSuccess(response: CartResponse) {
        val dec = DecimalFormat("###,###,###")
        if(response.result.cartList.isEmpty()){
            with(binding){
                cbCartSelectAll.visibility = View.GONE
                tvCartSelectPart.visibility = View.GONE
                nsvCart.visibility = View.GONE
                clCartBottombar.visibility = View.GONE
                clCartIsEmpty.visibility = View.VISIBLE
            }
        }
        binding.rvCartList.apply {
            adapter = CartListAdapter(response.result.cartList, object : BuyClickListener<Cart> {
                override fun onItemClick(pos: Int, item: Cart) {
                    val productNum : Int = item.productNum
                    parentFragmentManager.beginTransaction().add(R.id.main_frm, DetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt("productNum", productNum)
                        }
                    }).addToBackStack(null).commitAllowingStateLoss()
                }

                override fun onViewClick(view: BuyCartListBinding, pos: Int) {
                    view.btnCartListCount.setOnClickListener {
                        binding.clCartCountNum.visibility = View.VISIBLE
                        binding.clCartCountNum.bringToFront()
                        Log.d("----","$pos , $userNum , ${response.result.cartList[pos].ordersNum} , ${response.result.cartList[pos].orderCnt} ")
                        Log.d("----","$pos, $userNum ,${response.result.cartList[0].ordersNum}, ${response.result.cartList[pos].orderCnt}")
//                        BuyService(this@CartFragment).tryPatchCarts(userNum,CartPatchRequest(response.result.cartList[pos].ordersNum,5))
                        selectNum(view.btnCartListCount,
                            view.tvCartListPrice,
                            view.tvCartListPrice2,
                            response.result.cartList[pos].price,
                            response.result.cartList[pos].orderCnt,
                            response.result.cartList[pos].ordersNum)
                    }
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }


        totalSum = response.result.totalPrice
        binding.tvCartPriceSum.text = dec.format(response.result.totalPrice) + "원"
        binding.tvCartPaymentSum.text = dec.format(response.result.totalPrice) + "원"
        binding.tvCartProductSum.text = dec.format(response.result.totalPrice) + "원"
        for(i in 0 until response.result.cartList.size){
            totalcount += response.result.cartList[i].orderCnt
        }
        binding.tvCartCountSum.text = totalcount.toString() + "개 "
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(true)

        BuyService(this).tryGetCarts(userNum = userNum)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        val mainAct = activity as MainActivity
        mainAct.bottomNavigationHide(false)
    }

    //현재 가격 , 갯수, 주문번호
    private fun selectNum(btnNum:Button,price1: TextView,price2 : TextView,price :Int, cnt : Int, ordersNum:Int){
        binding.btnCartCountPoint1.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum, CartPatchRequest(ordersNum =ordersNum ,1))
            cartUpload(btnNum,price1,price2,1,cnt,price)
        }
        binding.btnCartCountPoint2.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,2))
            cartUpload(btnNum,price1,price2,2,cnt,price)
        }
        binding.btnCartCountPoint3.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,3))
            cartUpload(btnNum,price1,price2,3,cnt,price)
        }
        binding.btnCartCountPoint4.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,4))
            cartUpload(btnNum,price1,price2,4,cnt,price)
        }
        binding.btnCartCountPoint5.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,5))
            cartUpload(btnNum,price1,price2,5,cnt,price)
        }
        binding.btnCartCountPoint6.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,6))
            cartUpload(btnNum,price1,price2,6,cnt,price)
        }
        binding.btnCartCountPoint7.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,7))
            cartUpload(btnNum,price1,price2,7,cnt,price)

        }
        binding.btnCartCountPoint8.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,8))
            cartUpload(btnNum,price1,price2,8,cnt,price)
        }
        binding.btnCartCountPoint9.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,9))
            cartUpload(btnNum,price1,price2,9,cnt,price)
        }
        binding.btnCartCountPoint10plus.setOnClickListener {
            binding.clCartCountNum.visibility = View.GONE
            BuyService(this).tryPatchCarts(userNum,CartPatchRequest(ordersNum,10))
            cartUpload(btnNum,price1,price2,10,cnt,price)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun cartUpload(btnNum:Button,price1: TextView,price2 : TextView,orderCnt : Int, beforeCnt : Int, orderPrice :Int){
        val dec = DecimalFormat("###,###,###")
        totalSum += orderPrice * (orderCnt - beforeCnt)
        btnNum.text = orderCnt.toString()
        price1.text = dec.format((orderPrice/beforeCnt) * orderCnt) + "원"
        price2.text = dec.format((orderPrice/beforeCnt) * orderCnt) + "원"
        binding.tvCartPriceSum.text = dec.format(totalSum) + "원"
        binding.tvCartPaymentSum.text = dec.format(totalSum) + "원"
        binding.tvCartProductSum.text = dec.format(totalSum) + "원"
    }




    override fun onGetCartsFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onOrderSuccess(response: OrderResponse) {}

    override fun onOrderFailure(message: String) {}

    override fun onGetProductSuccess(response: ProductsResponse) {
        binding.rvCartAntotherProduct.apply {
            adapter = ShoppingProductsAdapter(response.productResult,object :
                ItemClickListener<ProductResult> {
                override fun onItemClick(pos: Int, item: ProductResult) {
                    val productNum : Int? = item.productNum
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,DetailFragment().apply {
                        arguments = Bundle().apply {
                            productNum?.let { putInt("productNum", productNum) } //key,value
                        }
                    }).addToBackStack(null).commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onGetProductFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchCartSuccess(response: CartPatchResponse) {
        Log.d("----카트테스트","${response.result} , ${response.message} , ${response.isSuccess}")

    }

    override fun onPatchCartFailure(message: String) {
        Log.d("----","오류 : $message")
    }

    override fun onDeleteCartSuccess(response: CartDeleteResponse) {}

    override fun onDeleteCartFailure(message: String) {}

}