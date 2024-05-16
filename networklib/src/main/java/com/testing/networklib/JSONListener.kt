package com.testing.networklib

import org.json.JSONObject

interface JSONListener {
    fun onResponse(res: JSONObject?)
    fun onFailure(e: Exception?)
}