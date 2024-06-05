package com.kishan.wallpaperapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kishan.wallpaperapp.model.Photo
import com.kishan.wallpaperapp.network.RetrofitService

class SearchPagingSource(
    private val apiService: RetrofitService,
    private val search:String
) : PagingSource<Int, Photo>(){
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
       return try {
           val nextPage = params.key ?: FIRST_PAGE
           val responseSearch = apiService.searchWallPaper(page = nextPage, query = search)
           LoadResult.Page(
               data = responseSearch.photos,
               prevKey = null,
               nextKey = if(responseSearch.photos.isEmpty()) null else nextPage + 1
           )
       }catch (e:Exception){
           LoadResult.Error(e)
       }

    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}