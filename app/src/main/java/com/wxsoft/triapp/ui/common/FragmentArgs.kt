package com.wxsoft.triapp.ui.common

import android.os.Bundle
import android.text.TextUtils
import java.io.Serializable
import java.util.*

class FragmentArgs : Serializable {
    private val values: MutableMap<String, Serializable> =
        HashMap()

    fun add(key: String, value: Serializable?): FragmentArgs {
        if (!TextUtils.isEmpty(key) && value != null) values[key] = value
        return this
    }

    operator fun get(key: String): Serializable? {
        return values[key]
    }

    companion object {
        private const val serialVersionUID = 5526514482404853100L
        fun toBundle(bundle: Bundle, args: FragmentArgs) {
            for (key in args.values.keys) {
                val value = args[key] ?: continue
                bundle.putSerializable(key, value)
            }
        }

        fun transToArgs(bundle: Bundle): FragmentArgs {
            val args = FragmentArgs()
            for (s in bundle.keySet()) {
                val o = bundle[s] ?: continue
                args.add(s, o as Serializable)
            }
            return args
        }

        fun transToBundle(args: FragmentArgs): Bundle {
            val bundle = Bundle()
            toBundle(bundle, args)
            return bundle
        }
    }
}