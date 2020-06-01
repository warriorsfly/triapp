package com.wxsoft.triapp.data.entity.rating

/**
 * 评分题
 */
data class Subject(val Id:Int=0,
                   val ratingId:Int=0,
                   val name:String="",
                   val index:Int=0,
                   val singleAnswer:Boolean=true){
    var options:MutableList<Option> =  mutableListOf()

    fun check(option: Option){

        //单选，先更新旧的选中的选项为未选中
        if(singleAnswer ) {
            if(options.size==1){
                option.checked = !option.checked
            }else {
                if (!option.checked) {
                    options.firstOrNull { it.checked }?.checked=false
                    options.firstOrNull { it.Id==option.Id }?.checked=true
//                    option.checked = !option.checked
                }
            }

        }else {
            options.firstOrNull { it.Id==option.Id }?.checked = !option.checked
        }
    }
}