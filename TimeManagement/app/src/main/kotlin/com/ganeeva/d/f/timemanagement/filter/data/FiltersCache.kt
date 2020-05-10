package com.ganeeva.d.f.timemanagement.filter.data

import com.ganeeva.d.f.timemanagement.filter.domain.SortType
import java.util.*

class FiltersCache(
    var sortType: SortType = SortType.CREATION_DATE_NEW,
    var periodStart: Long = -1L, // the earliest border
    var periodEnd: Long = System.currentTimeMillis() // the latest border
) {
    init {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, -1)
        periodStart = c.timeInMillis
    }
}