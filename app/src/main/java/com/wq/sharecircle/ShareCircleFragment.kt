package com.wq.main

import android.os.Bundle
import android.view.View
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uiutils.PictureShow
import com.sunrun.sunrunframwork.utils.log.Logger
import com.wq.base.LBaseFragment
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.ClassTemplateBean
import com.wq.common.model.HomeTemplateBean
import com.wq.common.model.LoginInfo
import com.wq.common.quest.BaseQuestConfig.*
import com.wq.common.quest.BaseQuestStart
import com.wq.common.quest.Config
import com.wq.main.adapter.HotTemplateAdapter
import com.wq.main.adapter.SearchHistoryAdapter
import com.wq.project01.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 * 乐享圈
 * Created by WQ on 2017/6/1.
 */

class ShareCircleFragment : LBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }



    override fun getLayoutRes(): Int {
        return R.layout.fragment_share_circle
    }

}
