package com.developer.cubemarket.utils

import com.developer.cubemarket.connection.MODEL.OOP.Mausac

interface CallBackColorProduct {
    fun onSuccess(ms: Mausac)
    fun onFail(rs: String)
    fun onError(rs: String)
}