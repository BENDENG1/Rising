package com.gyroh.a3rd_week.Fragment

import android.app.Activity
import android.content.ClipData.Item
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyroh.a3rd_week.FriendsParts.FAddFriends
import com.gyroh.a3rd_week.FriendsParts.LongClickFragment
import com.gyroh.a3rd_week.FriendsParts.Profile
import com.gyroh.a3rd_week.FriendsParts.ProfileAdapter
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentFriendsBinding

class friendsFragment : Fragment() {

    val binding by lazy { FragmentFriendsBinding.inflate(layoutInflater) }
    var userList = arrayListOf<Profile>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.rlReviseFriends.visibility = View.GONE
        binding.clRevise.visibility =View.GONE

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //테스트용
        userList.add(Profile(R.drawable.ic_profile1, "노균욱", "안녕하세요 노균욱입니다~~~~"))
        userList.add(Profile(R.drawable.ic_profile2, "근육", "안드로이드는 어려워"))
        userList.add(Profile(R.drawable.ic_profile_3, "균욱", "오늘도 귀찮은 하루.."))
        for (i in 1..20) {
            userList.add(Profile(R.drawable.ic_profile1, "노균욱${i}", "노균욱 클론 ${i}입니다."))
        }

        binding.ibAddFriend.setOnClickListener {
            val fAddFriends = FAddFriends()
            parentFragmentManager.beginTransaction().replace(R.id.fl_friends, fAddFriends).commit()
        }

        // recyclerViewProfile.layoutManager를 연결 recyclerView필수
        // LinearLayoutManager로 import 이지만 xml로도 컨트롤이 가능하다는점
        binding.recyclerViewProfile.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProfile.setHasFixedSize(true) // 고정된 크기의 아이템ui사용 -> recyclerview에 대한 성능개선방안

        val adapter = ProfileAdapter(userList)
        //어댑터 연결
        binding.recyclerViewProfile.adapter = adapter

        adapter.setOnItemClickListener(object :ProfileAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Profile, pos: Int) {
                binding.rlReviseFriends.visibility = View.VISIBLE
                binding.tvFriendsName.text = userList[pos].name

                binding.btnChangeName.setOnClickListener {
                    //userList[pos].name = binding.etReviseId.text.toString()
                    binding.rlReviseFriends.visibility = View.GONE
                    binding.clRevise.visibility = View.VISIBLE
                    binding.tvBeforeName.text = "친구가 설정한 이름 : ${userList[pos].name}"

                    binding.ibReviseCheck.setOnClickListener {
                        userList[pos].name = binding.etReviseId.text.toString()
                        adapter.notifyDataSetChanged()
                        binding.clRevise.visibility = View.GONE
                    }
                }

                binding.btnRemove.setOnClickListener {
                    adapter.deleteItem(pos)
                    binding.rlReviseFriends.visibility = View.GONE
                }
            }
        })

        //이렇게도 다음에 꼭 도전
//        binding.button5.setOnClickListener {
//            //longClickFragment로 이동
//            val longClickFragment = LongClickFragment()
//            val bundle = Bundle()
//            bundle.putString("username", userList[1].name)
//            bundle.putString("usermessage", userList[1].message)
//            longClickFragment.arguments = bundle
//            parentFragmentManager.beginTransaction().add(R.id.frameLayout, longClickFragment)
//                .commit()
//        }

    }



    //음... 다시 생각해보자
    override fun onResume() {
        super.onResume()
        updateData(ProfileAdapter(userList))
    }

    private fun updateData(profileAdapter: ProfileAdapter) {
        //여기서 bundle로 받아온 arguement가 자꾸 null인데 왜 그럴까?
        var flagCheck = arguments?.getInt("flag")
        var receivename: String = arguments?.getString("name").toString()
        var receivemessage: String = arguments?.getString("message").toString()

        Log.d("----", "$flagCheck $receivename $receivemessage")
        if (flagCheck == 1) {
            profileAdapter.addItem(Profile(R.drawable.ic_add_profile,receivename,receivemessage))
        }

    }
}