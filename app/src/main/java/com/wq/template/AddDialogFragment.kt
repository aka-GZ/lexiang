package com.wq.template


import android.os.Bundle
import android.view.View
import com.wq.base.UIBindDialogFragment
import com.wq.project01.R
import kotlinx.android.synthetic.main.dialog_add_people.*

/**
 * 添加团队成员
 * Created by WQ on 2017/6/7.
 */

class AddDialogFragment : UIBindDialogFragment() {
    override fun getLayoutId(): Int {
        return R.layout.dialog_add_people
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        submit.setOnClickListener { dismiss() }
        cancel.setOnClickListener { dismiss() }
    }

}
