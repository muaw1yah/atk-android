@file:JvmName("TimeUtils")

package com.atakaice.commons.extensions

import java.util.*

fun Long.getFriendlyTime(): String {
    val dateTime = Date(this * 1000)
    val sb = StringBuffer()
    val current = Calendar.getInstance().time
    var diffInSeconds = ((current.time - dateTime.time) / 1000).toInt()

    val sec = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val min = if (diffInSeconds >= 60) (diffInSeconds % 60) else diffInSeconds
    diffInSeconds /= 60
    val hrs = if (diffInSeconds >= 24) (diffInSeconds % 24) else diffInSeconds
    diffInSeconds /= 24
    val days = if (diffInSeconds >= 30) (diffInSeconds % 30) else diffInSeconds
    diffInSeconds /= 30
    val months = if (diffInSeconds >= 12) (diffInSeconds % 12) else diffInSeconds
    diffInSeconds /= 12
    val years = diffInSeconds

    if (years > 0) {
        if (years == 1) {
            sb.append("1 year")
        } else {
            sb.append("$years years")
        }
        if (years <= 6 && months > 0) {
            if (months == 1) {
                sb.append(" and 1 month")
            } else {
                sb.append(" and $months months")
            }
        }
    } else if (months > 0) {
        if (months == 1) {
            sb.append("1 month")
        } else {
            sb.append("$months months")
        }
        if (months <= 6 && days > 0) {
            if (days == 1) {
                sb.append(" and 1 day")
            } else {
                sb.append(" and $days days")
            }
        }
    } else if (days > 0) {
        if (days == 1) {
            sb.append("1 day")
        } else {
            sb.append("$days days")
        }
        if (days <= 3 && hrs > 0) {
            if (hrs == 1) {
                sb.append(" and 1 hr")
            } else {
                sb.append(" and $hrs hrs")
            }
        }
    } else if (hrs > 0) {
        if (hrs == 1) {
            sb.append("1 hr")
        } else {
            sb.append("$hrs hrs")
        }
        if (min > 1) {
            sb.append(" and $min mins")
        }
    } else if (min > 0) {
        if (min == 1) {
            sb.append("1 min")
        } else {
            sb.append("$min mins")
        }
        if (sec > 1) {
            sb.append(" and $sec secs")
        }
    } else {
        if (sec <= 1) {
            sb.append("about a secs")
        } else {
            sb.append("about $sec secs")
        }
    }

    sb.append(" ago")

    return sb.toString()
}