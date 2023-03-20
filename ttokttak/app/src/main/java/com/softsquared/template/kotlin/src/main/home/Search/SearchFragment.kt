package com.softsquared.template.kotlin.src.main.home.Search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeSearchBinding
import com.softsquared.template.kotlin.src.ItemClickListener
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragment
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.HospitalDetail.DetailHospitalFragment
import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse
import com.softsquared.template.kotlin.src.main.home.models.Row

class SearchFragment: BaseFragment<FragmentHomeSearchBinding>(FragmentHomeSearchBinding::bind , R.layout.fragment_home_search), HomeFragmentInterface{

    private val apiKey : String = "666e556d666e6f673130395542784968"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ibtnSearchBack.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.main_frm,HomeFragment()).commit()
        }

        binding.ibtnSearchSearch.setOnClickListener {
            binding.clSearchBefore.visibility = View.GONE
            binding.nsvSearchAfter.visibility = View.VISIBLE
            binding.floatingActionButton.bringToFront()

            hideKeyBoard()
            HomeService(this@SearchFragment).tryGetHospital(apiKey = apiKey)
        }


    }

    private fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken,0)
    }


    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)
    }


    @SuppressLint("SetTextI18n")
    override fun onGetHospitalSuccess(response: HospitalDataResponse) {
        binding.tvSearchHospitalCount.text = "총 ${response.LOCALDATA_010101_NW.list_total_count}개" // 총 몇개 보여줌

        binding.rvSearchHospitalList.apply {
            adapter = SearchHospitalAdapter(response.LOCALDATA_010101_NW.row,object :ItemClickListener<Row>{
                override fun onItemClick(pos: Int, item: Row) {
                    /*
                    START_INDEX	INTEGER(필수)	요청시작위치	정수 입력 (페이징 시작번호 입니다 : 데이터 행 시작번호)
                    END_INDEX	INTEGER(필수)	요청종료위치	정수 입력 (페이징 끝번호 입니다 : 데이터 행 끝번호)
                    이렇게 정보를 주어주니깐 START_INDEX + 1 과 END_INDEX + 1 로 넘겨주고 GET을 그냥 따로 설정하자 -> ㅇㅇ
                     */
                    showDetailHospital(pos + 1)
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

    }

    override fun onGetHospitalFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    private fun showDetailHospital(pos : Int){ //행 번호를 넘겨주면 될듯 ㅇㅇ
        val args = Bundle()
        val detailHospitalFragment = DetailHospitalFragment()
        args.putInt("hospitalNum",pos)
        detailHospitalFragment.arguments = args
        parentFragmentManager.beginTransaction().add(R.id.main_frm,detailHospitalFragment).commit()

    }

    override fun onGetDetailHospitalSuccess(response: HospitalDataResponse) {}

    override fun onGetDetailHospitalFailure(message: String) {}
}