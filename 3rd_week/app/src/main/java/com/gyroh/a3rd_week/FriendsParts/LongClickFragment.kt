package com.gyroh.a3rd_week.FriendsParts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.gyroh.a3rd_week.Fragment.friendsFragment
import com.gyroh.a3rd_week.MainActivity
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.FragmentFriendsBinding
import com.gyroh.a3rd_week.databinding.FragmentLongClickBinding


class LongClickFragment : Fragment() {

    val binding by lazy {FragmentLongClickBinding.inflate(layoutInflater)}
    //val bundle by lazy {Bundle()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding.flLongClick.setOnClickListener{
            finishLongClick()
        }

        binding.btnChangeName.setOnClickListener {
            reviseFriends()
            finishLongClick()
        }

        binding.btnRemove.setOnClickListener {
            removeFriends()
            finishLongClick()
        }

        return binding.root
    }
    private fun finishLongClick(){
        parentFragmentManager.beginTransaction().remove(this@LongClickFragment).commit()
    }


    private fun removeFriends(){
        val name = arguments?.getString("username")
        val message = arguments?.getString("usermessage")
        Log.d("bundle","$name $message")



    }

    private fun reviseFriends(){
    }

}