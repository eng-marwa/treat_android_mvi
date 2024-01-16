package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class AddFavoriteResponse(
    @SerializedName("data") var data: FavoriteData? = FavoriteData()
):TreatResponse()

data class FavoriteData(
    @SerializedName("branch_id") var branchId: String? = null,
    @SerializedName("client_id") var clientId: Int? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("id") var id: Int? = null

)