package com.ganeeva.d.f.timemanagement.core

abstract class Mapper<From, To> {

    abstract fun map(from: From) : To

    fun map(list: List<From>): List<To> {
        return list.map { map(it) }
    }
}