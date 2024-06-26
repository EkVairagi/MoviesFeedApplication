package com.testing.moviesfeedapplication.repository

import com.testing.moviesfeedapplication.model.Result
import com.testing.moviesfeedapplication.utils.Constants.API_KEY
import com.testing.moviesfeedapplication.utils.Constants.BASE_URL
import com.testing.networklib.JSONListener
import com.testing.networklib.Network
import org.json.JSONObject

class MovieRepository {
    fun getTrendingMovies(callback: (List<Result>?, String?) -> Unit) {
        Network.Request(Network.GET)
            .url("$BASE_URL/trending/movie/day?language=en-US&api_key=$API_KEY")
            .makeRequest(object : JSONListener {
                override fun onResponse(res: JSONObject?) {
                    val results = mutableListOf<Result>()
                    val arr = res?.optJSONArray("results")
                    if (arr != null) {
                        for (i in 0 until arr.length()) {
                            val jsonObj = arr.getJSONObject(i)
                            val genreIds = jsonObj.getJSONArray("genre_ids")
                            val genreList = (0 until genreIds.length()).map { genreIds.getInt(it) }
                            val result = Result(
                                jsonObj.getBoolean("adult"),
                                jsonObj.getString("backdrop_path"),
                                genreList,
                                jsonObj.getInt("id"),
                                jsonObj.getString("original_language"),
                                jsonObj.getString("original_title"),
                                jsonObj.getString("overview"),
                                jsonObj.getDouble("popularity"),
                                jsonObj.getString("poster_path"),
                                jsonObj.getString("release_date"),
                                jsonObj.getString("title"),
                                jsonObj.getBoolean("video"),
                                jsonObj.getDouble("vote_average"),
                                jsonObj.getInt("vote_count")
                            )
                            results.add(result)
                        }
                        callback(results, null)
                    } else {
                        callback(null, "No data found")
                    }
                }
                override fun onFailure(e: Exception?) {
                    callback(null, e?.message)
                }
            })
    }
}

