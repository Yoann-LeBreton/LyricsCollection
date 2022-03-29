package fr.yggz.android.lyricscollection.presentation.tablet

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import fr.yggz.android.lyricscollection.R
import fr.yggz.android.lyricscollection.presentation.tablet.album.TabletAlbumFragment
import fr.yggz.android.lyricscollection.presentation.tablet.song.TabletSongFragment

class TabletActivity : AppCompatActivity() {

    private val _tabletViewModel: TabletViewModel by viewModels()
    private var _showListSongs = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablet)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<TabletAlbumFragment>(R.id.frgt_album_container)
            }
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<TabletSongFragment>(R.id.frgt_song_container)
            }
        }

        _tabletViewModel.songs.observe(this) {
            if (it.isNotEmpty() && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                showSongs()
            }
        }
    }

    override fun onBackPressed() {
        if (_showListSongs) {
            hideSongs()
        } else {
            super.onBackPressed()
        }
    }

    private fun showSongs() {
        _showListSongs = true
        findViewById<FrameLayout>(R.id.frgt_song_container).visibility = View.VISIBLE
        findViewById<FrameLayout>(R.id.frgt_album_container).visibility = View.GONE
    }

    private fun hideSongs() {
        _showListSongs = false
        findViewById<FrameLayout>(R.id.frgt_song_container).visibility = View.GONE
        findViewById<FrameLayout>(R.id.frgt_album_container).visibility = View.VISIBLE
    }
}