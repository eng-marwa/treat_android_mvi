package com.treat.customer.domain.usecases.home

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow

class GetBranchesByServiceType(private val iHomeRepository: IHomeRepository) {
    suspend operator fun invoke(
        serviceCategoryIds: ArrayList<String>?,
        serviceTypeId: String?,
        gender: String?,
        lat: String?,
        lng: String?
    ): Flow<NetworkResource<BranchesResponse>> =
        iHomeRepository.getBranchesByServiceType(serviceCategoryIds, serviceTypeId, gender, lat, lng)
}