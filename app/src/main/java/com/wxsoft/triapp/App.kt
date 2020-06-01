package com.wxsoft.triapp

import com.wxsoft.triapp.data.entity.Dictionary
import com.wxsoft.triapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {

    companion object {
        val dictionaries = mutableListOf(
            /**
             * 添加光反射
             */
            Dictionary(Id = -1, ParentId = -1, Key = "++", Name = "++"),
            Dictionary(Id = -1, ParentId = -1, Key = "+", Name = "+"),
            Dictionary(Id = -1, ParentId = -1, Key = "0", Name = "0"),
            Dictionary(Id = -1, ParentId = -1, Key = "C", Name = "C"),
            /**
             * 添加卧位
             */
            Dictionary(Id = -2, ParentId = -2, Key = "平卧位", Name = "平卧位"),
            Dictionary(Id = -2, ParentId = -2, Key = "左侧卧位", Name = "左侧卧位"),
            Dictionary(Id = -2, ParentId = -2, Key = "右侧卧位", Name = "右侧卧位"),
            Dictionary(Id = -2, ParentId = -2, Key = "半坐卧位", Name = "半坐卧位"),
            Dictionary(Id = -2, ParentId = -2, Key = "端坐位", Name = "端坐位"),
            Dictionary(Id = -2, ParentId = -2, Key = "俯卧位", Name = "俯卧位"),
            Dictionary(Id = -2, ParentId = -2, Key = "头低足高位", Name = "头低足高位"),
            Dictionary(Id = -2, ParentId = -2, Key = "头高足低位", Name = "头高足低位"),
            Dictionary(Id = -2, ParentId = -2, Key = "膝胸位", Name = "膝胸位"),
            Dictionary(Id = -2, ParentId = -2, Key = "截石位", Name = "截石位"),
            Dictionary(Id = -2, ParentId = -2, Key = "中凹卧位", Name = "中凹卧位"),
            /**
             * 神志
             */
            Dictionary(Id = 1, ParentId = -3, Key = "清醒状态", Name = "清醒状态"),
            Dictionary(Id = 2, ParentId = -3, Key = "嗜睡状态", Name = "嗜睡状态"),
            Dictionary(Id = 3, ParentId = -3, Key = "意识模糊", Name = "意识模糊"),
            Dictionary(Id = 4, ParentId = -3, Key = "昏睡状态", Name = "昏睡状态"),
            Dictionary(Id = 5, ParentId = -3, Key = "浅昏迷", Name = "浅昏迷"),
            Dictionary(Id = 6, ParentId = -3, Key = "深昏迷", Name = "深昏迷"),
            Dictionary(Id = 7, ParentId = -3, Key = "谵妄", Name = "谵妄"),

            /**
             * 瞳孔
             */
            Dictionary(Id = 1, ParentId = -4, Key = "1.0", Name = "1.0"),
            Dictionary(Id = 2, ParentId = -4, Key = "2.0", Name = "2.0"),
            Dictionary(Id = 3, ParentId = -4, Key = "3.0", Name = "3.0"),
            Dictionary(Id = 4, ParentId = -4, Key = "4.0", Name = "4.0"),
            Dictionary(Id = 5, ParentId = -4, Key = "5.0", Name = "5.0"),
            Dictionary(Id = 6, ParentId = -4, Key = "散大", Name = "散大"),

            /**
             * 瞳孔
             */
            Dictionary(Id = 1, ParentId = -5, Key = "1", Name = "脑卒中"),
            Dictionary(Id = 2, ParentId = -5, Key = "2", Name = "急性心梗"),
            Dictionary(Id = 3, ParentId = -5, Key = "3", Name = "孕产妇"),
            Dictionary(Id = 4, ParentId = -5, Key = "4", Name = "创伤"),
            Dictionary(Id = 5, ParentId = -5, Key = "5", Name = "中毒"),
            Dictionary(Id = 6, ParentId = -5, Key = "6", Name = "三无")
        )
    }



    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}