package com.wxsoft.triapp.data.entity.rating

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.SerializedName
import com.wxsoft.triapp.BR

/**
 * 评分
 */
data class Rating(val Id:Int=0,
                  val name:String="",
                  val memo:String="",
                  val resultGrades:List<Grade> = emptyList()

                  ):BaseObservable(){


    var subjects:List<Subject> = emptyList()

    @get:Bindable
    var score:Int=0
        set(value) {
            field=value
            notifyPropertyChanged(BR.score)
        }

    @Transient
    @get:Bindable
    var max:Int=0
        set(value) {
            field=value
            notifyPropertyChanged(BR.max)
        }

    @Transient
    @get:Bindable
    var process:Int=0
        set(value) {
            field=value
            notifyPropertyChanged(BR.process)
        }

    @get:Bindable
    @Transient
    var grade: Grade?=null
    set(value) {
        field=value
        notifyPropertyChanged(BR.grade)
    }
    fun refreshScore(subject: Subject,option: Option){
//        autoCheck(option.autoChecks)
        subject.check(option)
        refreshScore()
    }

    fun refreshScore(){
        score = subjects.sumBy{ it.options.filter { option ->  option.checked }.sumBy { op->op.score } }
        process=subjects.count{it.options.size==1 || it.options.any{option->option.checked}}
        grade=resultGrades.firstOrNull { it.scoreStart<score && it.scoreEnd>= score }
    }

}