package com.treat.customer.di

import android.content.Context
import com.treat.customer.data.datasource.interfaces.IBranchRemoteDS
import com.treat.customer.data.datasource.interfaces.IHomeRemoteDS
import com.treat.customer.data.datasource.interfaces.INotificationRemoteDS
import com.treat.customer.data.datasource.interfaces.IUserAuthRemoteDS
import com.treat.customer.data.datasource.interfaces.IUserMoreRemoteDS
import com.treat.customer.data.datasource.interfaces.NotificationRemoteDSImpl
import com.treat.customer.data.datasource.local.prefs.PreferenceHelper
import com.treat.customer.data.datasource.remote.BranchRemoteDSImpl
import com.treat.customer.data.datasource.remote.HomeRemoteDSImpl
import com.treat.customer.data.datasource.remote.UserAuthRemoteDSImpl
import com.treat.customer.data.datasource.remote.UserMoreRemoteDSImpl
import com.treat.customer.data.datasource.remote.api.ApiService
import com.treat.customer.data.datasource.remote.api.AuthInterceptor
import com.treat.customer.data.datasource.remote.api.RetrofitClient
import com.treat.customer.domain.repository.BranchRepositoryImpl
import com.treat.customer.domain.repository.HomeRepositoryImpl
import com.treat.customer.domain.repository.IBranchRepository
import com.treat.customer.domain.repository.IHomeRepository
import com.treat.customer.domain.repository.INotificationRepository
import com.treat.customer.domain.repository.IUserAuthRepository
import com.treat.customer.domain.repository.IUserMoreRepository
import com.treat.customer.domain.repository.NotificationRepositoryImpl
import com.treat.customer.domain.repository.UserAuthRepositoryImpl
import com.treat.customer.domain.repository.UserMoreRepositoryImpl
import com.treat.customer.domain.usecases.auth.DisableAccountUseCase
import com.treat.customer.domain.usecases.auth.GetGenderUseCase
import com.treat.customer.domain.usecases.auth.GetLoginStatusUseCase
import com.treat.customer.domain.usecases.auth.GetProfileUseCase
import com.treat.customer.domain.usecases.auth.GetSavedGenderUseCase
import com.treat.customer.domain.usecases.auth.GetSavedUserDataUseCase
import com.treat.customer.domain.usecases.auth.LogoutRemoteUseCase
import com.treat.customer.domain.usecases.auth.LogoutUseCase
import com.treat.customer.domain.usecases.auth.ResendOTPUseCase
import com.treat.customer.domain.usecases.auth.SaveGenderUseCase
import com.treat.customer.domain.usecases.auth.SaveUserDataUseCase
import com.treat.customer.domain.usecases.auth.SetLoginStatusUseCase
import com.treat.customer.domain.usecases.auth.UpdateLocationUseCase
import com.treat.customer.domain.usecases.auth.UpdateUserProfileUseCase
import com.treat.customer.domain.usecases.auth.UserLoginUseCase
import com.treat.customer.domain.usecases.auth.VerifyAccountUseCase
import com.treat.customer.domain.usecases.branches.GetBranchDetailsUseCase
import com.treat.customer.domain.usecases.favorites.AddBranchToFavoriteUseCase
import com.treat.customer.domain.usecases.favorites.ViewFavoriteBranchesUseCase
import com.treat.customer.domain.usecases.home.GetAllServiceCategoriesUseCase
import com.treat.customer.domain.usecases.home.GetBranchesByServiceType
import com.treat.customer.domain.usecases.home.GetGenderByServiceTypeUseCase
import com.treat.customer.domain.usecases.home.GetHomeSliderUseCase
import com.treat.customer.domain.usecases.home.GetServiceCategoriesUseCase
import com.treat.customer.domain.usecases.home.GetServiceTypesUseCase
import com.treat.customer.domain.usecases.home.GetSpecificServicesUseCase
import com.treat.customer.domain.usecases.more.GetAppSettingsUseCase
import com.treat.customer.domain.usecases.more.GetFQAUseCase
import com.treat.customer.domain.usecases.more.GetMyPointsUseCase
import com.treat.customer.domain.usecases.more.GetMyWalletUseCase
import com.treat.customer.domain.usecases.more.TransferPointsToWalletUseCase
import com.treat.customer.domain.usecases.notifications.GetNotificationUseCase
import com.treat.customer.presentation.auth.login.LoginViewModel
import com.treat.customer.presentation.auth.otp.OTViewModel
import com.treat.customer.presentation.auth.profile.ProfileViewModel
import com.treat.customer.presentation.location.LocationViewModel
import com.treat.customer.presentation.main.notification.NotificationViewModel
import com.treat.customer.presentation.main.search.SearchViewModel
import com.treat.customer.presentation.main.ui.branches.BranchViewModel
import com.treat.customer.presentation.main.ui.favorites.FavoritesViewModel
import com.treat.customer.presentation.main.ui.home.HomeViewModel
import com.treat.customer.presentation.main.ui.more.MoreViewModel
import com.treat.customer.presentation.main.ui.more.fqa.FQAViewModel
import com.treat.customer.presentation.main.ui.more.my_points.MyPointsViewModel
import com.treat.customer.presentation.main.ui.more.my_wallet.MyWalletViewModel
import com.treat.customer.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { OTViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get(), get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get(), get()) }
//    viewModel { BookingViewModel(get()) }
    viewModel { MoreViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { FQAViewModel(get()) }
//    viewModel { ChangeLanguageViewModel(get()) }
    viewModel { MyWalletViewModel(get()) }
    viewModel { MyPointsViewModel(get(), get()) }
    viewModel { BranchViewModel(get()) }
    viewModel { SearchViewModel() }
    viewModel { NotificationViewModel(get()) }
}

val useCaseModule = module {
    factory { UserLoginUseCase(get()) }
    factory { VerifyAccountUseCase(get()) }
    factory { ResendOTPUseCase(get()) }
    factory { DisableAccountUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { SaveUserDataUseCase(get()) }
    factory { GetSavedUserDataUseCase(get()) }
    factory { GetLoginStatusUseCase(get()) }
    factory { SetLoginStatusUseCase(get()) }
    factory { GetGenderUseCase(get()) }
    factory { UpdateLocationUseCase(get()) }
    factory { UpdateUserProfileUseCase(get()) }
    factory { GetFQAUseCase(get()) }
    factory { GetMyWalletUseCase(get()) }
    factory { GetMyPointsUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { LogoutRemoteUseCase(get()) }
    factory { TransferPointsToWalletUseCase(get()) }
    factory { GetAppSettingsUseCase(get()) }
    factory { GetHomeSliderUseCase(get()) }
    factory { GetServiceCategoriesUseCase(get()) }
    factory { GetServiceTypesUseCase(get()) }
    factory { GetSavedGenderUseCase(get()) }
    factory { SaveGenderUseCase(get()) }
    factory { GetSpecificServicesUseCase(get()) }
    factory { GetAllServiceCategoriesUseCase(get()) }
    factory { AddBranchToFavoriteUseCase(get()) }
    factory { ViewFavoriteBranchesUseCase(get()) }
    factory { GetBranchesByServiceType(get()) }
    factory { GetGenderByServiceTypeUseCase(get()) }
    factory { GetBranchDetailsUseCase(get()) }
    factory { GetNotificationUseCase(get()) }
}
val repositoryModule = module {

    single<IUserAuthRepository> {
        UserAuthRepositoryImpl(get(), get())
    }
    single<IUserMoreRepository> {
        UserMoreRepositoryImpl(get())
    }
    single<IHomeRepository> {
        HomeRepositoryImpl(get())
    }
    single<IBranchRepository> {
        BranchRepositoryImpl(get())
    }
    single<INotificationRepository> {
        NotificationRepositoryImpl(get())
    }
}

val dataSourceModule = module {
    single {
        UserAuthRemoteDSImpl(get()) as IUserAuthRemoteDS
    }

    single {
        UserMoreRemoteDSImpl(get()) as IUserMoreRemoteDS
    }
    single {
        HomeRemoteDSImpl(get()) as IHomeRemoteDS
    }
    single {
        BranchRemoteDSImpl(get()) as IBranchRemoteDS
    }
    single {
        NotificationRemoteDSImpl(get()) as INotificationRemoteDS
    }
}

val networkModule = module {
    single { AuthInterceptor(pref = get()) }

    single { RetrofitClient.provideOkHttpClient(authInterceptor = get()) }
    single { RetrofitClient.provideRetrofit(okHttpClient = get()) }
    single { ApiService.createAuthService(retrofit = get()) }
}


val preferencesModule = module {
    single { androidContext().getSharedPreferences("treat", Context.MODE_PRIVATE) }
    single {
        PreferenceHelper(get())
    }
}

