package com.softsquared.template.kotlin.src.Login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentLoginBinding
import com.softsquared.template.kotlin.src.Login.Join.LoginJoinFragment
import com.softsquared.template.kotlin.src.Login.Login.LoginSignFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLoginJoinEmail.setOnClickListener {
            setFragment(LoginJoinFragment())
        }

        binding.tvLoginByEmail.setOnClickListener {
            setFragment(LoginSignFragment())
        }
    }

    private fun setFragment(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.login_frm,fragment).commit()
    }
}