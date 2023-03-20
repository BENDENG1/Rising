package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.Search.SearchFragment
import com.softsquared.template.kotlin.src.main.home.models.HomeFindHospital
import com.softsquared.template.kotlin.src.main.home.models.HomePicture
import com.softsquared.template.kotlin.src.main.home.models.HomeSymptomHospital
import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
        HomeFragmentInterface {

    var eventList = arrayListOf<HomePicture>() //홈 이벤트 사진 리스트
    var findHospitalList = arrayListOf<HomeFindHospital>()
    var symptomList = arrayListOf<HomeSymptomHospital>()
    private var homePictureAdapter : HomePictureAdapter? = null
    private var homeFindHospitalAdapter : HomeFindHospitalAdapter?= null
    private var homeSymptomHospitalAdapter : HomeSymptomHospitalAdapter?=null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventPictureRecyclerView() //홈화면 이벤트 사진 리사이클러뷰 컨트롤
        findHospitalRecyclerView() //홈화면 병원 찾기 리사이클러뷰 컨트롤
        symptomHospitalRecyclerView()

        searchCare() // 병원,진료,병원을 검색할때 사용하는 함수

    }

    override fun onResume() {
        super.onResume()
        val mainact = activity as MainActivity
        mainact.hideNavigationBar(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun searchCare(){
        binding.btnHomeSearch.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SearchFragment()).addToBackStack(null).commit()
            val mainAct = activity as MainActivity
            mainAct.hideNavigationBar(false)
        }
    }

    @SuppressLint("Recycle")
    private fun eventPictureRecyclerView(){
        val eventArray = resources.obtainTypedArray(R.array.home_event_pic_list)
        val eventColorArray = resources.getIntArray(R.array.home_event_color_list)

        for(i in 0 until eventArray.length()){
            eventList.add(HomePicture(eventArray.getResourceId(i,-1),eventColorArray[i]))
        }
        binding.vpHomeEventPic.apply {
            adapter = HomePictureAdapter(eventList)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }

    private fun findHospitalRecyclerView(){
        val findHospitalPictureArray = resources.obtainTypedArray(R.array.home_find_hospital_pic_list)
        val findHospitalColorArray = resources.getIntArray(R.array.home_find_hospital_color_list)
        val findHospitalNameArray = resources.getStringArray(R.array.home_find_hospital_name_list)

        for(i in 0 until findHospitalPictureArray.length()){
            findHospitalList.add(HomeFindHospital(findHospitalPictureArray.getResourceId(i,-1),findHospitalColorArray[i],findHospitalNameArray[i]))
        }
        binding.rvHomeFindHospital.apply {
            adapter = HomeFindHospitalAdapter(findHospitalList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    private fun symptomHospitalRecyclerView(){
        val symptomHospitalArray = resources.getStringArray(R.array.home_symptom_hospital_name_list)
        for(i in symptomHospitalArray.indices){
            symptomList.add(HomeSymptomHospital(symptomHospitalArray[i]))
        }
        binding.rvHomeSymptomHospital.apply {
            adapter = HomeSymptomHospitalAdapter(symptomList)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onGetHospitalSuccess(response: HospitalDataResponse) {}

    override fun onGetHospitalFailure(message: String) {}

    override fun onGetDetailHospitalSuccess(response: HospitalDataResponse) {

    }

    override fun onGetDetailHospitalFailure(message: String) {}
}