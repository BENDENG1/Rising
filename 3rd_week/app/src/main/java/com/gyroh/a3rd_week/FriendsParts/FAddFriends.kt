package com.gyroh.a3rd_week.FriendsParts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyroh.a3rd_week.Fragment.friendsFragment
import com.gyroh.a3rd_week.MainActivity
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentFaddfriendsBinding

class FAddFriends : Fragment() {

    val binding by lazy {FragmentFaddfriendsBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibBack.setOnClickListener{
            onBackPressed()
        }
        binding.ibID.setOnClickListener{

            val intent = Intent(context, AddIdActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            //parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun onBackPressed(){
        parentFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }


}