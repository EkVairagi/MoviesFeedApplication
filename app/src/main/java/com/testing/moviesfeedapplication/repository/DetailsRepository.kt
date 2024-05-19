package com.testing.moviesfeedapplication.repository

import com.testing.networklib.JSONListener
import com.testing.networklib.Network
import org.json.JSONObject

class DetailsRepository {

    fun getMovieDetails(id: String, callback: (Result<JSONObject>) -> Unit) {
        Network.Request(Network.GET)
            .url("https://api.themoviedb.org/3/movie/$id?language=en-US&api_key=909594533c98883408adef5d56143539")
            .makeRequest(object : JSONListener {
                override fun onResponse(res: JSONObject?) {
                    if (res != null) {
                        callback(Result.success(res))
                    } else {
                        callback(Result.failure(Exception("No response from server")))
                    }
                }

                override fun onFailure(e: Exception?) {
                    callback(Result.failure(e ?: Exception("Unknown error")))
                }
            })
    }
}
