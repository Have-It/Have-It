package com.habit.domain.model.statistics

data class Date(
    val year: Int,
    val month: Int,
    val day: Int,


    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Date

        if (year != other.year) return false
        if (month != other.month) return false
        if (day != other.day) return false

        return true
    }
}

