/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wxsoft.triapp.utils

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.wxsoft.triapp.App
import com.wxsoft.triapp.R

@BindingAdapter("levelAt")
fun levelAt(view: TextView, levelss: String?) {
    if (levelss == null) {

        view.setBackgroundResource(0)
        view.text = "未分诊"
    } else {
        val level = levelss.toInt()
        val states = arrayOf(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled))
        when (level) {
            1 -> {
                val colors = intArrayOf(-0xe9533a)
                view.backgroundTintList = ColorStateList(states, colors)
                view.setTextColor(-0xffffff)
                view.text = "一级"
            }
            2 -> {
                val colors = intArrayOf(-0xFFA500)
                view.backgroundTintList = ColorStateList(states, colors)
                view.setTextColor(-0xffffff)
                view.text = "二级"
            }
            3 -> {
                val colors = intArrayOf(-0xFFFF00)
                view.backgroundTintList = ColorStateList(states, colors)
                view.setTextColor(-0x000000)
                view.text = "三级"
            }
            else -> {
                val colors = intArrayOf(-0x70AD47)
                view.backgroundTintList = ColorStateList(states, colors)
                view.setTextColor(-0xffffff)
                view.text = "四级"
            }
        }
    }

}


@BindingAdapter("dictionaryAt")
fun dictionaryAt(view: TextView, levelss: String?) {
    levelss?.let {
        App.dictionaries.find { d -> d.Id.toString() == it }?.let { dic ->
            view.text = dic.Name
        }

    }
}


@BindingAdapter("dictKeyAt")
fun dictKeyAt(view: TextView, levelss: String?) {
    levelss?.let {
        App.dictionaries.find { d -> d.Key == it }?.let { dic ->
            view.text = dic.Name
        }

    }
}
@BindingAdapter("dictionaryAt")
fun dictionaryAt(view: TextView, levelss: Int) {
    levelss?.let {
        App.dictionaries.find { d -> d.Id == it }?.let { dic ->
            view.text = dic.Name
        }

    }
}

@BindingAdapter("dictionaryGreenAt")
fun dictionaryGreenAt(view: TextView, levelss: String?) {
    levelss?.let {
        App.dictionaries.find { d -> d.Key == it && d.ParentId==-5 }?.let { dic ->
            view.text = dic.Name
        }

    }
}


@BindingAdapter("lisItemsRow")
fun lisItemsRow(textView: TextView, position:String?) {
    when(position){
        "L"->textView.setBackgroundResource(R.drawable.ic_row_down)
        "H"->textView.setBackgroundResource(R.drawable.ic_row_up)
        //不在上述代码中则清空textView的背景
        else->textView.setBackgroundResource(0)
    }
}

@BindingAdapter("lisItemsColor")
fun lisItemsColor(textView: TextView, position:String?) {
    when(position){
        "L"->textView.setTextColor(textView.context.resources.getColor(R.color.row_down))
        "H"->textView.setTextColor(textView.context.resources.getColor(R.color.row_up))
        //不在上述代码中则清空textView的背景
        else->textView.setTextColor(textView.context.resources.getColor(R.color.black))
    }
}
