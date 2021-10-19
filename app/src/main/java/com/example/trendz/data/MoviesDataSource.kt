package com.example.trendz.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trendz.Api.RestService
import com.example.trendz.models.Result
import com.example.trendz.utils.Constants.API_KEY
import com.example.trendz.utils.Constants.LANG_US
import com.example.trendz.utils.Constants.STARTING_OFFSET_INDEX
import java.io.IOException
import javax.inject.Inject

class MoviesDataSource @Inject constructor(val apiService: RestService)
    : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
         return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val offset = params.key?:STARTING_OFFSET_INDEX
        val loadSize = params.loadSize

        return try {
            val data = apiService.fetchPopularMovies(
                    API_KEY, LANG_US,offset
            )

            val filteredData = data.body()?.results

            LoadResult.Page(data = filteredData!!,
                    prevKey = if (offset == STARTING_OFFSET_INDEX) null else offset - loadSize,
                    nextKey = if (data.body()!!.page == data.body()!!.total_pages) null else offset + loadSize
            )

        } catch (t: Throwable) {
            var exception = t

            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }
}