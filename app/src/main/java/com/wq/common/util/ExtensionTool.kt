package com.wq.common.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.sunrun.sunrunframwork.http.utils.UIUpdateHandler
import com.wq.main.RequestDelegate

/**
 * Kotlin 扩展方法汇总
 * Created by WQ on 2017/6/8.
 */

fun RequestDelegate.SESSION(key:String,value:Any){
    this.that.session.put(key,value)
}

inline  fun <reified  T> UIUpdateHandler.SESSION(key:String):T?{
        return when(T::class){
            Double::class->session.getDouble(key)as T
            Int::class->session.getInt(key)as T
            Float::class->session.getFloat(key)as T
            Long::class->session.getLong(key)as T
            String::class->session.getString(key)as T
            else -> session.getObject(key,T::class.java)
        }
}
 fun UIUpdateHandler.SESSION(key:String,value:Any?=null):UIUpdateHandler{
        this.session.put(key,value)
     return this
}
/**
 *  简化文本框内容改变回掉
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

fun <T,R> Iterable<T>.list2list(callback:(T)->R):List<R>{
    var aimList= arrayListOf<R>()
    forEach { aimList.add(callback.invoke(it)) }
    return aimList;
}
