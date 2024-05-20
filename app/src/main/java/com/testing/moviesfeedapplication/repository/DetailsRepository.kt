package com.testing.moviesfeedapplication.repository

import com.testing.moviesfeedapplication.utils.Constants.API_KEY
import com.testing.moviesfeedapplication.utils.Constants.BASE_URL
import com.testing.networklib.JSONListener
import com.testing.networklib.Network
import org.json.JSONObject

class DetailsRepository {

    fun getMovieDetails(id: String, callback: (Result<JSONObject>) -> Unit) {
        Network.Request(Network.GET)
            .url("$BASE_URL/movie/$id?language=en-US&api_key=$API_KEY")

            .makeRequest(object : JSONListener {
                override fun onResponse(res: JSONObject?) {
                    if (res != null) {
                        callback(Result.success(res))
                    } else {
                        callback(Result.failure(Exception("No data found")))
                    }
                }

                override fun onFailure(e: Exception?) {
                    callback(Result.failure(e ?: Exception("Something went wrong")))
                }
            })
    }
}
