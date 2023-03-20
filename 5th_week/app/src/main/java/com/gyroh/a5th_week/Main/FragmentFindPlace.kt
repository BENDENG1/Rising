package com.gyroh.a5th_week.Main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyroh.a5th_week.Main.Map.FragmentRestaurantMap
import com.gyroh.a5th_week.Main.Models.RestaurantResponse
import com.gyroh.a5th_week.R
import com.gyroh.a5th_week.databinding.FragmentFindPlaceBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentFindPlace : Fragment() {

    companion object{
        val API_KEY : String = "UPYKo30hO6I84E2Ds136lHQCPjNAyLx5ozwZH/SXQJSKj4f9/mzeveiVtPNhSabyqfP1Y0uZ7rQTpbu4lmXJZw=="
    }
    val binding by lazy{FragmentFindPlaceBinding.inflate(layoutInflater)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOrderBy.setOnClickListener {
            val bottomSheet = main_bottomSheetFragment()
            bottomSheet.show(parentFragmentManager,bottomSheet.tag)
        }
        binding.ibtnHomeMap.setOnClickListener {
            binding.ibtnHomeMap.visibility = View.GONE
            val restaurantMapFragment = FragmentRestaurantMap()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_Main,restaurantMapFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerviewControl()
    }

    private fun recyclerviewControl(){
        binding.rvRestaurant.apply {
            layoutManager = GridLayoutManager(context,2)
            getRestaurantData(API_KEY,1,50)
            setHasFixedSize(true)
        }

        binding.rvRestaurantReview.apply{
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }


    private fun getRestaurantData(serviceKey : String, page :Int, perPage : Int){

        //retrofitclient에서 정의했음
        //val restaurant = RetrofitClient.retrofit.create(Restaurant::class.java)

        RetrofitClient.restaurant.getRestaurant(serviceKey,page,perPage).enqueue(object : Callback<RestaurantResponse>{
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as RestaurantResponse
                    Log.d("----","결과 ${result.data[0]}\n $response")
                    val restaurantAdapter = RestaurantAdapter(result.data)
                    binding.rvRestaurant.adapter = restaurantAdapter
                    val reviewAdapter = MainReviewAdapter(result.data)
                    binding.rvRestaurantReview.adapter = reviewAdapter
                }else{
                    Log.d("----","response X${response.toString()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                Log.d("----","통신 x")
            }
        })
    }

}
