package com.treat.customer.domain.repository

import com.treat.customer.data.datasource.interfaces.IHomeRemoteDS
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

class HomeRepositoryImpl(private val iHomeRemoteDS: IHomeRemoteDS) : IHomeRepository {

    override suspend fun getServiceTypes(): Flow<NetworkResource<ServiceTypesResponse>> {
        return iHomeRemoteDS.serviceTypes()
    }

    override suspend fun getServiceCategories(id: String): Flow<NetworkResource<ServiceCategoriesResponse>> {
        return iHomeRemoteDS.serviceCategories(id)
    }

    override suspend fun getHomeSlider(): Flow<NetworkResource<HomeSliderResponse>> {
        return iHomeRemoteDS.getHomeSlider()
    }

    override suspend fun getSpecificServices(
        categoryId: String,
        gender: String
    ): Flow<NetworkResource<SpecificServicesResponse>> {
        return iHomeRemoteDS.getSpecificServices(categoryId, gender)
    }

    override suspend fun getAllServiceCategories(): Flow<NetworkResource<ServiceCategoriesResponse>> {
        return iHomeRemoteDS.allServiceCategories()
    }

    override suspend fun getAvailableTimes(): Flow<NetworkResource<AvailableTimeResponse>> {
        return iHomeRemoteDS.getAvailableTimes()
    }

    override suspend fun addBranchToFavorite(branchId: String): Flow<NetworkResource<AddFavoriteResponse>> {
        return iHomeRemoteDS.addBranchToFavorite(branchId)
    }

    override suspend fun viewFavoriteBranches(): Flow<NetworkResource<BranchesResponse>> {
        return iHomeRemoteDS.viewFavoriteBranches()
    }

    override suspend fun getBranchesByServiceType(
        serviceCategoryIds: ArrayList<String>?, serviceTypeId: String?, gender: String?, lat: String?, lng: String?,date: String?
    ): Flow<NetworkResource<BranchesResponse>> {
        return iHomeRemoteDS.getBranchesByServiceType(serviceCategoryIds, serviceTypeId, gender, lat, lng,date)
    }

    override suspend fun getGenderByServiceType(
        serviceType: String,
    ): Flow<NetworkResource<GenderResponse>> {
        return iHomeRemoteDS.getGenderByServiceType(serviceType)
    }
}