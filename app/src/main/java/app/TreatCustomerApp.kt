package app

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.treat.customer.di.dataSourceModule
import com.treat.customer.di.networkModule
import com.treat.customer.di.preferencesModule
import com.treat.customer.di.repositoryModule
import com.treat.customer.di.useCaseModule
import com.treat.customer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TreatCustomerApp : Application() {
    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin(this)
    }
    private fun startKoin(app: Application) {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(app)
            modules(
                viewModelModule,
                useCaseModule,
                dataSourceModule,
                repositoryModule,
                preferencesModule,
                networkModule,
            )
        }
    }
}


