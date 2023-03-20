package com.softsquared.template.kotlin.src.main.home.Notification

import android.os.Bundle
import android.view.View
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentNotificationBinding
import com.softsquared.template.kotlin.src.main.Commnet.models.GetBigCommentResponse
import com.softsquared.template.kotlin.src.main.Commnet.models.GetCommentUserResponse
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.models.GetHomePheedResponse
import com.softsquared.template.kotlin.src.main.home.models.GetHomeStoryResponse

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::bind, R.layout.fragment_notification){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibtnNotificaitonBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


    }

    //이제 알림 rv나오게 되면 이중 리사이클러뷰로 만들어야함

    //api 만드시는것을 포기하셔서 이부분은 그냥 지워버림

}