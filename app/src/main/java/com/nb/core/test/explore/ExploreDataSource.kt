package com.nb.core.test.explore

import com.nb.core.RecyclerDataSource
import com.nb.core.test.model.WorksModel
import com.nb.core.test.model.WorksModelGenerator
import retrofit2.HttpException
import java.io.IOException

class ExploreDataSource : RecyclerDataSource<WorksModel, String>(null) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WorksModel> {
        return try {
            LoadResult.Page(
                data = WorksModelGenerator.createWorksModels(params.loadSize),
                prevKey = null,
                nextKey = WorksModelGenerator.getID()
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}