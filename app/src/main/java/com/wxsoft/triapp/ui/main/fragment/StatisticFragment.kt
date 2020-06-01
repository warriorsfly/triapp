package com.wxsoft.triapp.ui.main.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.warriorsfly.core.di.ViewModelFactory
import com.warriorsfly.core.utils.activityViewModelProvider
import com.wxsoft.triapp.R
import com.wxsoft.triapp.databinding.ActivityChartBinding
import com.wxsoft.triapp.ui.common.BaseFragment
import com.wxsoft.triapp.utils.DateTimeUtils
import java.util.*
import javax.inject.Inject


class StatisticFragment() : BaseFragment() {


    private val weekdays= listOf("星期日","星期一","星期二","星期三","星期四","星期五","星期六")
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var binding: ActivityChartBinding

    private lateinit var viewModel: PatientWithDetailModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = activityViewModelProvider(factory)
        binding = ActivityChartBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.greenStatistics.observe(viewLifecycleOwner, Observer {

            val pies = it.GreenPassageWays.map { item -> PieEntry(item.Count.toFloat(), item.Name) }
            val items = mutableListOf<PieEntry>().apply {
                addAll(pies)

            }

            binding.chart1.run {
                centerText=generateCenterSpannableText("绿色通道人员统计",it.Total.toString())
                data = PieData().apply {
                    setDrawValues(true)
                    description.text=""
                    dataSet = PieDataSet(items, "").apply {
                        setColors(
                            resources.getColor(R.color.list1),
                            resources.getColor(R.color.list2),
                            resources. getColor(R.color.list3),
                            resources.getColor(R.color.list4),
                            resources.getColor(R.color.list5),
                            resources.getColor(R.color.list6)
                        )
                    }
                }

                invalidate()
            }
        })

        viewModel.levelDistributes.observe(viewLifecycleOwner, Observer {
            it.DeptDistributes.let { levels->
                levels.firstOrNull { item->item.Name=="一级" }?.let { level1->
                    binding.level1.text=level1.Count.toString()
                }
                levels.firstOrNull { item->item.Name=="二级" }?.let { level1->
                    binding.level2.text=level1.Count.toString()
                }
                levels.firstOrNull { item->item.Name=="三级" }?.let { level1->
                    binding.level3.text=level1.Count.toString()
                }
                levels.firstOrNull { item->item.Name=="四级" }?.let { level1->
                    binding.level4.text=level1.Count.toString()
                }
            }
        })

        viewModel.deptDistributes.observe(viewLifecycleOwner, Observer {
            val pies = it.DeptDistributes.map { item -> PieEntry(item.Count.toFloat(), item.Name) }
            val items = mutableListOf<PieEntry>().apply {
                addAll(pies)

            }

            binding.chart2.run {
                centerText=generateCenterSpannableText("分诊科室统计",it.Total.toString())
                data = PieData().apply {
                    setDrawValues(true)
                    description.text=""
                    dataSet = PieDataSet(items, "").apply {
                        setColors(
                            resources.getColor(R.color.list1),
                            resources.getColor(R.color.list2),
                            resources. getColor(R.color.list3),
                            resources.getColor(R.color.list4),
                            resources.getColor(R.color.list5),
                            resources.getColor(R.color.list6)
                        )
                    }
                }

                invalidate()
            }
        })
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Calendar.getInstance()
            .run {
                val index=get(Calendar.DAY_OF_WEEK)
                binding.week.text=weekdays[index-1]
                binding.date.text=DateTimeUtils.chinaFormat.format(time)
            }
    }


    private fun generateCenterSpannableText(title:String ,total:String): SpannableString? {
        val s = SpannableString("${title}\n共计${total}人").apply {
//            setSpan(RelativeSizeSpan(1.2f), 0, title.length, 0)
            setSpan(StyleSpan(Typeface.NORMAL), 0, title.length, 0)
            setSpan(ForegroundColorSpan(Color.GRAY), 0, title.length, 0)
//            setSpan(RelativeSizeSpan(3.8f), title.length, length, 0)
            setSpan(StyleSpan(Typeface.NORMAL), title.length, length, 0)
            setSpan(ForegroundColorSpan(Color.BLACK), title.length, length, 0)
        }

        return s

//        return "${title}\n共计${total}人"
    }

}
