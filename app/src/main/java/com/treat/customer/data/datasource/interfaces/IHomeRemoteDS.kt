package com.treat.customer.data.datasource.interfaces

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.AddFavoriteResponse
import com.treat.customer.data.model.AvailableTimeResponse
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.HomeSliderResponse
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.ServiceTypesResponse
import com.treat.customer.data.model.SpecificServicesResponse
import kotlinx.coroutines.flow.Flow

interface IHomeRemoteDS {
    suspend fun serviceTypes(): Flow<NetworkResource<ServiceTypesResponse>>
    suspend fun serviceCategories(id: String): Flow<NetworkResource<ServiceCategoriesResponse>>
    suspend fun getHomeSlider(): Flow<NetworkResource<HomeSliderResponse>>
    suspend fun getSpecificServices(
        categoryId: String,
        gender: String
    ): Flow<NetworkResource<SpecificServicesResponse>>

    suspend fun allServiceCategories(): Flow<NetworkResource<ServiceCategoriesResponse>>
    suspend fun getAvailableTimes(): Flow<NetworkResource<AvailableTimeResponse>>
    suspend fun addBranchToFavorite(branchId: String): Flow<NetworkResource<AddFavoriteResponse>>
    suspend fun viewFavoriteBranches(): Flow<NetworkResource<BranchesResponse>>
    suspend fun getBranchesByServiceType(serviceCategoryIds: ArrayList<String>?, serviceTypeId: String?, gender: String?, lat: String?, lng: String?, date:String?): Flow<NetworkResource<BranchesResponse>>
    suspend fun getGenderByServiceType(serviceTypeId: String): Flow<NetworkResource<GenderResponse>>
}