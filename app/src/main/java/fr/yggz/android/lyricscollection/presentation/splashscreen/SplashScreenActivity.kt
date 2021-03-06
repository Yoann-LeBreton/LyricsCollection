package fr.yggz.android.lyricscollection.presentation.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import fr.yggz.android.lyricscollection.BuildConfig
import fr.yggz.android.lyricscollection.R
import fr.yggz.android.lyricscollection.domain.common.Constants
import fr.yggz.android.lyricscollection.domain.common.Constants.Companion.MINUTE_BETWEEN_SYNC
import fr.yggz.android.lyricscollection.domain.common.SharedPrefConstants
import fr.yggz.android.lyricscollection.domain.common.StateData
import fr.yggz.android.lyricscollection.presentation.main.MainActivity
import fr.yggz.android.lyricscollection.presentation.tablet.TabletActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels<SplashViewModel>()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sharedPref = getSharedPreferences(SharedPrefConstants.APP_SHARED_PREF, MODE_PRIVATE)
        progressBar = findViewById<ContentLoadingProgressBar>(R.id.splash_progress_bar)
        splashViewModel.syncResultState.observe(this) { state ->
            when (state.status) {
                StateData.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                StateData.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        this,
                        resources.getString(R.string.error_retrieve_data),
                        Toast.LENGTH_LONG
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        redirect()
                    }, 4000)
                }
                StateData.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    state.data?.let {
                        saveSyncDate(it)
                    }
                    redirect()
                }
                else -> {}
            }
        }
        if (needNewSync()) {
            splashViewModel.syncSongs()
        } else {
            redirect()
        }
    }

    private fun saveSyncDate(date: String) {
        with(sharedPref.edit()) {
            putString(SharedPrefConstants.LAST_SYNC, date)
            apply()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun needNewSync(): Boolean {
        val lastSync = sharedPref.getString(SharedPrefConstants.LAST_SYNC, null) ?: return true
        try {
            val dateLastSync = SimpleDateFormat(Constants.DATE_FORMAT).parse(lastSync)
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, -MINUTE_BETWEEN_SYNC)
            val expirationDate: Date = calendar.time
            dateLastSync?.let {
                return dateLastSync.before(expirationDate)
            } ?: run {
                return true
            }
        } catch (parseEx: ParseException) {
            return true
        }
    }

    private fun redirect() {
        if (BuildConfig.IS_TABLET) {
            val redirectIntent = Intent(this, TabletActivity::class.java)
            startActivity(redirectIntent)
        } else {
            val redirectIntent = Intent(this, MainActivity::class.java)
            startActivity(redirectIntent)
        }
    }
}