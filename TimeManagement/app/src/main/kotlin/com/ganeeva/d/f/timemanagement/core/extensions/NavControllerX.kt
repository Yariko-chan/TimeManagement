package com.ganeeva.d.f.timemanagement.core.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateWithCheck(action: NavDirections) {
    if (currentDestination?.id != action.actionId) {
        navigate(action)
    }
}