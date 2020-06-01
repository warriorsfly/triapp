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

package com.warriorsfly.core.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod


@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

object Converter{
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int): String {
        return if(value==0)"" else value.toString()
    }

    @JvmStatic fun stringToInt(value:String): Int {
        return if(value.isEmpty()) 0 else value.toInt()
    }
}

object NullableConverter{
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int?): String {
        return if(value==null)"" else value.toString()
    }

    @JvmStatic fun stringToInt(value:String): Int? {
        return if(value.isEmpty()) null else value.toInt()
    }
}

object QualityScoreConverter{
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int): String {
        return if(value==0)"-" else value.toString()
    }

    @JvmStatic fun stringToInt(value:String): Int {
        return if(value=="-") 0 else value.toInt()
    }
}

object LongConverter{
    @InverseMethod("stringToLong")
    @JvmStatic fun longToString(value: Long): String {
        return if(value==0L) ""  else value.toString()
    }

    @JvmStatic fun stringToLong(value:String): Long {
        return if(value.isEmpty()) 0L else value.toLong()
    }
}

object FloatConverter{
    @InverseMethod("stringToFloat")
    @JvmStatic fun floatToString(value: Float?):String  {
        return value?.toString() ?: "-"

    }

    @JvmStatic fun stringToFloat(value:String): Float? {
        return if(value.isEmpty()) null else value.toFloat()
    }
}
object DoubleConverter{
    @InverseMethod("stringToDouble")
    @JvmStatic fun doubleToString(value: Double?):String  {
        return value?.toString() ?: ""

    }
    @JvmStatic fun stringToDouble(value:String): Double? {
        return if(value.isEmpty()) null else value.toDouble()
    }
}