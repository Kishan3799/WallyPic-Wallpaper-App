package com.kishan.wallypic.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kishan.wallypic.model.Photo
import com.kishan.wallypic.network.RetrofitService

class CategoryPagingSource(
    private val apiService:RetrofitService,
    private val category:String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
      return state.anchorPosition?.let { anchorPosition->
          val anchorPage = state.closestPageToPosition(anchorPosition)
          anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
      }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE
            val responseCategory = apiService.wallPaperCategory(page = nextPage, category = category)
            LoadResult.Page(
                data = responseCategory.photos,
                prevKey = null,
                nextKey = if (responseCategory.photos.isEmpty()) null else nextPage + 1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}