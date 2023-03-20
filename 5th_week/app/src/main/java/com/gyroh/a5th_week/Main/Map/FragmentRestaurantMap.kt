package com.gyroh.a5th_week.Main.Map

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.gyroh.a5th_week.Activity.MainActivity
import com.gyroh.a5th_week.Main.*
import com.gyroh.a5th_week.Main.Models.Data
import com.gyroh.a5th_week.Main.Models.RestaurantResponse
import com.gyroh.a5th_week.R
import com.gyroh.a5th_week.databinding.FragmentRestaurantMapBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class FragmentRestaurantMap : Fragment(), OnMapReadyCallback, MainActivity.onBackPressedListener {

    private val binding by lazy{FragmentRestaurantMapBinding.inflate(layoutInflater)}
    private lateinit var rgoogleMap : GoogleMap
    private val API_KEY : String = "UPYKo30hO6I84E2Ds136lHQCPjNAyLx5ozwZH/SXQJSKj4f9/mzeveiVtPNhSabyqfP1Y0uZ7rQTpbu4lmXJZw=="
    val latLngList = ArrayList<LatLng>()
    val nameList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavigation(true)

        binding.rvRestaurantMap.bringToFront() //뷰 최상단
        getMapRestaurant(API_KEY,1,10)

        binding.restaurantGoogleMap.onCreate(savedInstanceState)
        binding.restaurantGoogleMap.getMapAsync(this)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnMapBack.setOnClickListener {
            onBackPressed()
        }
        binding.rvRestaurantMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val mainAct = activity as MainActivity
        mainAct.hideBottomNavigation(false) //navigationview 다시 생김
    }

    override fun onBackPressed() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onMapReady(googleMap : GoogleMap) {
        val point = LatLng(37.493732629949, 127.01431918107)

        //rgoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL //default값이라서 딱히 상관없기함
        rgoogleMap = googleMap
        rgoogleMap.apply{
            val latLngBounds = LatLngBounds.Builder()
            moveCamera(CameraUpdateFactory.newLatLngZoom(point,14F))
            addMarker(MarkerOptions().position(point).title("여기다!"))
            for(i in 0 until latLngList.size){
                addMarker(MarkerOptions().position(latLngList[i]).title(nameList[i]))
                latLngBounds.include(latLngList[i])
            }
        }
    }


    //ㅠㅠㅠㅠㅠㅠㅠ하는 방법이 떠올랐지만 여기까지..
//    private fun showRestaurant(result: List<Data>?, latLngList : ArrayList<LatLng>){
//        result?.let {
//            val latLngBounds = LatLngBounds.Builder()
//            for(i in it.indices){
//                val position = latLngList[i]
//                val marker = MarkerOptions().position(position).title(it[i].name)
//                rgoogleMap.addMarker(marker)
//                latLngBounds.include(position)
//            }
//        }
//    }



    private fun getMapRestaurant(serviceKey : String, page :Int, perPage : Int){

        RetrofitClient.restaurant.getRestaurant(serviceKey,page,perPage).enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                if(response.isSuccessful){
                    val result = response.body() as RestaurantResponse

                    val restaurantMapAdapter = RestaurantMapAdapter(result.data)
                    binding.rvRestaurantMap.adapter = restaurantMapAdapter
                    for(i in 0 until result.data.size)
                    {
                        latLngList.add(getLatLng(result.data[i].address))
                        nameList.add(result.data[i].name)
                    }
                    //showRestaurant(result.data, latLngList)
                }else{
                    Log.d("----","response X${response.toString()}")
                }
            }
            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                Log.d("----","통신 x")
            }
        })
    }


    private fun getLatLng(address:String) : LatLng{
        val geoCoder = Geocoder(context, Locale.KOREA)   // Geocoder 로 자기 나라에 맞게 설정
        val list = geoCoder.getFromLocationName(address, 3)

        var location = LatLng(37.493732629949, 127.01431918107)     //임시 교대역으로

        if(list != null){
            if (list.size ==0){
                Log.d("GeoCoding", "해당 주소로 찾는 위도 경도가 없습니다. 올바른 주소를 입력해주세요.")
            }else{
                val addressLatLng = list[0]
                location = LatLng(addressLatLng.latitude, addressLatLng.longitude)
                return location
            }
        }
        return location
    }

}