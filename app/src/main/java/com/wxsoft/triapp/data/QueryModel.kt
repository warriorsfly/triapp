package com.wxsoft.triapp.data

/**
 * 查询模型
 */
data class QueryModel(
    val PageCondition: PageCon = PageCon(),
    val FilterGroup: FilterGro = FilterGro()
) {
    /**
     * Rule条件，对应SQL的 and or = <> < <= > >= '%aa' 'aa%' '%aa%'
     */
    enum class Filter(val value: Int) {
        And(1),
        Or(2),
        Equal(3),
        NotEqual(4),
        Less(5),
        LessOrEqual(6),
        Greater(7),
        GreaterOrEqual(8),
        StartsWith(9),
        EndsWith(10),
        Contains(11),
        NotContains(12)
    }


    enum class SortDirection(val value: Int) {
        Ascending(0),
        Descending(1)
    }

    /**
     * 页码
     */
    data class PageCon(
        var PageIndex: Int = 1,
        var PageSize: Int = 9999,
        val SortConditions: MutableList<SortCondition> = mutableListOf()
    )

    /**
     * 排序组
     */
    data class SortCondition(
        val SortField: String = "",
        val ListSortDirection: Int = SortDirection.Descending.value
    )

    /**
     * 过滤组
     */
    data class FilterGro(
        val Rules: MutableList<Rule> = mutableListOf(),
        val Groups: MutableList<FilterGro> = mutableListOf(),
        var Operate: Int = Filter.And.value,
        var Level:Int = 1
    )

    /**
     * 查询规则
     */
    data class Rule(val field: String = "", val value: Any, val operate: Int = Filter.Equal.value)
}