package com.ganeeva.d.f.timemanagement.filter.domain

class Filter(
    var sortType: SortType,
    var periodStart: Long, // the earliest border
    var periodEnd: Long // the latest border
)