package com.treat.customer.data.datasource.remote

import com.treat.customer.base.BaseApiProvider
import com.treat.customer.data.datasource.interfaces.IHomeRemoteDS
import com.treat.customer.data.datasource.remote.api.ApiService
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

class HomeRemoteDSImpl(private val apiService: ApiService) : IHomeRemoteDS, BaseApiProvider() {
    override suspend fun serviceTypes(): Flow<NetworkResource<ServiceTypesResponse>> {
        return apiRequest { apiService.serviceTypes() }
    }

    override suspend fun serviceCategories(id: String): Flow<NetworkResource<ServiceCategoriesResponse>> {
        return apiRequest { apiService.serviceCategories(id) }
    }

    override suspend fun getHomeSlider(): Flow<NetworkResource<HomeSliderResponse>> {
        return apiRequest { apiService.getHomeSlider() }
    }

    override suspend fun getSpecificServices(
        categoryId: String,
        gender: String
    ): Flow<NetworkResource<SpecificServicesResponse>> {
        return apiRequest { apiService.specificServices(categoryId, gender) }
    }

    override suspend fun allServiceCategories(): Flow<NetworkResource<ServiceCategoriesResponse>> {
        return apiRequest { apiService.allCategories() }
    }

    override suspend fun getAvailableTimes(): Flow<NetworkResource<AvailableTimeResponse>> {
        return apiRequest { apiService.getAvailableTimes() }
    }

    override suspend fun addBranchToFavorite(branchId: String): Flow<NetworkResource<AddFavoriteResponse>> {
        return apiRequest { apiService.addFavoriteBranch(branchId) }
    }

    override suspend fun viewFavoriteBranches(): Flow<NetworkResource<BranchesResponse>> {
        return apiRequest { apiService.getFavoriteBranches() }
    }

    override suspend fun getBranchesByServiceType(
        serviceCategoryIds: ArrayList<String>?,
        serviceTypeId: String?,
        gender: String?,
        lat: String?,
        lng: String?,
        date: String?
    ): Flow<NetworkResource<BranchesResponse>> {
        return apiRequest { apiService.getBranches(serviceCategoryIds, serviceTypeId, gender, lat, lng ,date) }
    }

    override suspend fun getGenderByServiceType(serviceTypeId: String): Flow<NetworkResource<GenderResponse>> {
        return apiRequest { apiService.genderByServiceType(serviceTypeId) }

    }
}