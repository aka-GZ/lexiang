package com.wq.common.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Created by WQ on 2017/6/8.
 */
/**
 *
 */
fun  TextView.onTextChanged(listener:( CharSequence,  Int,  Int, Int)->Unit):TextView{
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            listener.invoke(s,start,before,count)
        }

        override fun afterTextChanged(s: Editable) {

        }
    })
    return this
}

/**
 * 使用指定分隔符连接集合中所有元素
 */
 inline fun <T> Iterable<T>.toString(limitStr : String=",",callback: (T) -> String):String {
    var tmpStr=""
    forEach {
        tmpStr=tmpStr+callback.invoke(it)+limitStr
    }
    if(!tmpStr.isEmpty()){
        tmpStr.substring(0,tmpStr.length-1)
    }
    return tmpStr
}