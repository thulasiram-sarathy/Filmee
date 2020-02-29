package com.thul.filmee.view.activity.video

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.thul.filmee.AppConstants.Companion.INTENT_VIDEO_KEY
import com.thul.filmee.AppConstants.Companion.YT_KEY
import com.thul.filmee.R
import com.thul.filmee.databinding.VideoActivityBinding

class VideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private var videoKey: String? = null
    private var binding: VideoActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseView()
    }

    private fun initialiseView() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_video)
        binding?.youtubeView?.initialize(YT_KEY, this)
        videoKey = intent.getStringExtra(INTENT_VIDEO_KEY)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider,
        youTubePlayer: YouTubePlayer,
        b: Boolean
    ) {
        youTubePlayer.setFullscreen(true)
        youTubePlayer.loadVideo(videoKey) // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider,
        youTubeInitializationResult: YouTubeInitializationResult
    ) {
        if (youTubeInitializationResult.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(
                this,
                RECOVERY_REQUEST
            ).show()
        } else {
            val error =
                String.format("video error", youTubeInitializationResult.toString())
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val RECOVERY_REQUEST = 1
    }
}