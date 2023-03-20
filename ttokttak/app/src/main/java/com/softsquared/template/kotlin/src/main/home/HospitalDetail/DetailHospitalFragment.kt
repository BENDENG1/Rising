package com.softsquared.template.kotlin.src.main.home.HospitalDetail

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentDetailHospitalBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.Search.SearchFragment
import com.softsquared.template.kotlin.src.main.home.models.DetailHospitalPicture
import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse

class DetailHospitalFragment : BaseFragment<FragmentDetailHospitalBinding>(FragmentDetailHospitalBinding::bind
    , R.layout.fragment_detail_hospital),HomeFragmentInterface{

    private val hospitalNum by lazy {requireArguments().getInt("hospitalNum")}
    private var pictureList = arrayListOf<DetailHospitalPicture>() //병원 사진들 리스트
    private lateinit var callback: OnBackPressedCallback //백버튼 클릭시

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailHospitalPictureViewPager() //병원 정보 사진 viewpager

        HomeService(this@DetailHospitalFragment).tryGetDetailHospital(hospitalNum,hospitalNum)

        binding.mainScrollView.run {
            header = binding.headerView
        }

        binding.ibtnDetailHospitalBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@DetailHospitalFragment).commit()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
//                parentFragmentManager.beginTransaction().remove(this@DetailHospitalFragment).commit()
                parentFragmentManager.beginTransaction().replace(R.id.main_frm,SearchFragment()).commit()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)
    }

    private fun detailHospitalPictureViewPager(){
        val detailHospitalPictureArray = resources.obtainTypedArray(R.array.detail_hospital_pic_list)

        for(i in 0 until detailHospitalPictureArray.length()){
            pictureList.add(DetailHospitalPicture(detailHospitalPictureArray.getResourceId(i,-1)))
        }
        binding.vp2DetailHospitalImage.apply {
            adapter = DetailHospitalAdapter(detailHospitalList = pictureList)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun googleMapMarker(address : String){
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses.isNotEmpty()) {
            val lat = addresses[0].latitude
            val lng = addresses[0].longitude

            val location = LatLng(lat, lng)
            val marker = MarkerOptions().position(location)
            binding.mapViewDetailHospital.getMapAsync{
                it.addMarker(marker)
                it.moveCamera(CameraUpdateFactory.newLatLng(location))
            }
        }
    }


    override fun onGetHospitalSuccess(response: HospitalDataResponse) {}

    override fun onGetHospitalFailure(message: String) {}

    override fun onGetDetailHospitalSuccess(response: HospitalDataResponse) {
        val address = response.LOCALDATA_010101_NW.row[0].hospitalAddress
        googleMapMarker(address)

        binding.tvDetailHospitalHospitalSector.text = response.LOCALDATA_010101_NW.row[0].hospitalSector
        binding.tvDetailHospitalTitle.text = response.LOCALDATA_010101_NW.row[0].hospitalName
        binding.tvDetailHospitalName.text = response.LOCALDATA_010101_NW.row[0].hospitalName
        binding.tvDetailHospitalLocation.text = response.LOCALDATA_010101_NW.row[0].hospitalAddress
        binding.tvDetailHospitalLocationInformation.text = response.LOCALDATA_010101_NW.row[0].hospitalAddress


    }

    override fun onGetDetailHospitalFailure(message: String) {
    }
}