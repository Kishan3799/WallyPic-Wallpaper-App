package com.kishan.wallpaperapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.network.RetrofitService

class CuratedPagingSource(
    private val apiService: RetrofitService
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE
            val responseCurated = apiService.getCuratedWallPaper(nextPage)
            LoadResult.Page(
                data = responseCurated.photos,
                prevKey = null,
                nextKey = if (responseCurated.photos.isEmpty()) null else nextPage + 1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}