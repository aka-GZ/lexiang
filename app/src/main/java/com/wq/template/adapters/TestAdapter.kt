package com.wq.template.adapters

import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ListView
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.wq.project01.R

/**
 * Created by weiquan on 2017/6/8.
 */
class TestAdapter <out T>(val list:ListView, val data:List<T>){
    init {
        list.Adapter(data, R.layout.item_my_template){ holder: ViewHodler, item: T, position: Int ->

        }
    }
}