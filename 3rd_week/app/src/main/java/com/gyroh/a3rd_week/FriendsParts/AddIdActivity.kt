package com.gyroh.a3rd_week.FriendsParts

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.gyroh.a3rd_week.Fragment.friendsFragment
import com.gyroh.a3rd_week.MainActivity
import com.gyroh.a3rd_week.R
import com.gyroh.a3rd_week.databinding.ActivityAddIdBinding
import java.io.ByteArrayOutputStream

class AddIdActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddIdBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            rlAddProfile.visibility = View.GONE
            rlAddNothing.visibility = View.GONE

            ibAddBack.setOnClickListener { finish() }
            btnAddFreinds.setOnClickListener { addUser() }
        }


    }

    override fun onStart() {
        super.onStart()
        showKeyboard(binding.etAddId)
    }

    override fun onResume() {
        super.onResume()
        addKakaoId()
    }


    private fun addKakaoId() {
        with(binding) {
            ibAddCheck.setOnClickListener {
                rlAddMyId.visibility = View.GONE
                hideKeyboard(etAddId)
                if (etAddId.text.toString() == "nogom369") {
                    rlAddProfile.visibility = View.VISIBLE
                    rlAddNothing.visibility = View.GONE
                } else {
                    rlAddNothing.visibility = View.VISIBLE
                    rlAddProfile.visibility = View.GONE
                    rlAddMyId.visibility = View.GONE
                }
            }

        }
    }

    fun addUser() {
        var bundle = Bundle()
        var friendsFragment = friendsFragment()
        var sendName = binding.tvProfileName.text.toString()
        var sendMessage = "안드로이드는 고통스러워"
        bundle.putString("name", sendName)
        bundle.putString("message", sendMessage)
        bundle.putInt("flag", 1)

        /*friendsFragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_friends,friendsFragment)
            .commit() */

        //setResult(1000, Intent(this, MainActivity::class.java).putExtra("Bundle", bundle))

        startActivity(Intent(this, MainActivity::class.java).putExtra("Bundle", bundle))
        finish()
    }


    private fun hideKeyboard(editText: EditText) {
        val manager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //키보드 숨김
        manager.hideSoftInputFromWindow(editText.applicationWindowToken, 0)
    }

    private fun showKeyboard(editText: EditText) {
        val manager: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //키보드 보여줌
        manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        //포커스를 지정하여 창이 켜지자마자 진행
        binding.etAddId.requestFocus()
    }

}