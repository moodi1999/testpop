package com.example.ahmadreza.testpop.Activities

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.example.ahmadreza.testpop.Adaptors.RecyclerViews.AlbumRecyAdp
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.Datas.AlbumData
import com.example.ahmadreza.testpop.Datas.CallType
import com.example.ahmadreza.testpop.Datas.MusicType
import com.example.ahmadreza.testpop.Datas.SongData
import com.example.ahmadreza.testpop.Fragments.*
import com.example.ahmadreza.testpop.Storege.DataStorage
import com.example.ahmadreza.testpop.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import de.hdodenhof.circleimageview.CircleImageView
import dmax.dialog.SpotsDialog
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.album_lay.*
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.fragment_recent.view.*
import kotlinx.android.synthetic.main.media_contorol.*
import kotlinx.android.synthetic.main.slide_toolbar.*
import kotlinx.android.synthetic.main.up_slide_lay.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var player: SimpleExoPlayer? = null
    var bandwidthMeter: BandwidthMeter? = null
    var extractorsFactory: ExtractorsFactory? = null
    var trackSelectionFactory: TrackSelection.Factory? = null
    var trackSelector: TrackSelector? = null
    var defaultBandwidthMeter: DefaultBandwidthMeter? = null
    var dataSourceFactory: DataSource.Factory? = null
    var mediaSource: MediaSource? = null
    var handler: Handler? = null
    var isPlaying = false
    var dialog: AlertDialog? = null
    val listener = object : Player.DefaultEventListener(){

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_ENDED -> {
                    //Stop playback and return to start position
                    csetPlayPause(false)
                    player?.seekTo(0)
                }
                ExoPlayer.STATE_READY -> {
                    setProgress()
                    loading_prog.visibility = View.INVISIBLE
                    small_loading_prog.visibility = View.INVISIBLE
                }
                ExoPlayer.STATE_BUFFERING ->{
                    loading_prog.visibility = View.VISIBLE
                    small_loading_prog.visibility = View.VISIBLE
                }

                ExoPlayer.STATE_IDLE ->{
                    println("IDLE")
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_slide_lay)

        uiInit()
        getData()
        initPlayer()
        play("")

    }

    fun uiInit(){
        toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Pop Music")
        loading_prog.visibility = View.INVISIBLE
        small_loading_prog.visibility = View.INVISIBLE
        dialog = SpotsDialog.Builder().setCancelable(false).setContext(this).setTheme(R.style.CustomPopup).build()
        val viewPad = ViewPageAdaptor(supportFragmentManager)

        viewPad.addFragment(Recent(), "Recent")
        viewPad.addFragment(Categories(), "Ctegories")
        viewPad.addFragment(Artists(), "Artists")
        viewPad.addFragment(Popular(), "Popular")

        viewpager.adapter = viewPad
        tab_View_pager.setViewPager(viewpager)

        tab_View_pager.bringToFront()
        toolbar.bringToFront()
        //sliding_layout.isClipPanel = true

        val layoutm = LinearLayoutManager(applicationContext)
        album_recyclerview.layoutManager = layoutm
        album_recyclerview.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(album_recyclerview, false)
    }


    fun getData(){
        dialog?.show()
        DownloadWebContent(applicationContext,CallType.RECENT).execute(DataStorage.instance.Main_URL)
    }

    fun initSeekBar() {
        song_seekbar!!.requestFocus()
        progresbar_small.requestFocus()
        song_seekbar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return
                }

                player!!.seekTo((progress * 1000).toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }


            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        song_seekbar!!.max = 0
        progresbar_small.max = 0
        song_seekbar!!.max = player!!.duration.toInt() / 1000
        progresbar_small.max = player!!.duration.toInt() / 1000

    }

    fun setProgress() {
        song_seekbar!!.max = 0
        progresbar_small.max = 0
        song_seekbar!!.max = player!!.duration.toInt() / 1000
        progresbar_small.max = player!!.duration.toInt() / 1000

        curr_time!!.text = stringForTime(player!!.currentPosition.toInt())
        song_lenth!!.text = stringForTime(player!!.duration.toInt())

        if (handler == null) handler = Handler()
        //Make sure you update Seekbar on UI thread
        handler!!.post(object : Runnable {
            override fun run() {
                if (player != null && isPlaying) {
                    song_seekbar!!.max = player!!.duration.toInt() / 1000
                    progresbar_small.max = player!!.duration.toInt() / 1000
                    val mCurrentPosition = player!!.currentPosition.toInt() / 1000
                    song_seekbar!!.progress = mCurrentPosition
                    progresbar_small.progress = mCurrentPosition
                    curr_time.text = stringForTime(player!!.currentPosition.toInt())
                    song_lenth.text = stringForTime(player!!.duration.toInt())

                    handler!!.postDelayed(this, 1000)

                }
            }
        })


    }

    fun csetPlayPause(play: Boolean) {
        isPlaying = play
        player!!.playWhenReady = play
        if (!isPlaying) {
            play_puase.setImageResource(R.drawable.ic_play)
            small_button.setImageResource(R.drawable.ic_play)
            loading_prog.visibility = View.INVISIBLE
            small_loading_prog.visibility = View.INVISIBLE
        } else {
            setProgress()
            play_puase.setImageResource(R.drawable.ic_pause)
            small_button.setImageResource(R.drawable.ic_pause)
            loading_prog.visibility = View.VISIBLE
            small_loading_prog.visibility = View.VISIBLE
        }
    }


    fun initPlayButton() {
        play_puase.requestFocus()
        play_puase.setOnClickListener { csetPlayPause(!isPlaying) }
        small_button.setOnClickListener { csetPlayPause(!isPlaying) }
    }

    fun download(url: String){
        val lun = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(lun)
    }

    fun initMediaControls() {
        initPlayButton()
        initSeekBar()
    }


    fun stringForTime(timeMs: Int): String {
        val mFormatBuilder: StringBuilder
        val mFormatter: Formatter
        mFormatBuilder = StringBuilder()
        mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
        val totalSeconds = timeMs / 1000

        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600

        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }


    fun initPlayer(){
        bandwidthMeter = DefaultBandwidthMeter()
        extractorsFactory = DefaultExtractorsFactory()
        trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(trackSelectionFactory)
        defaultBandwidthMeter = DefaultBandwidthMeter()

    }

    fun play(s: String, b: Boolean = false){

        dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter)
        mediaSource = ExtractorMediaSource(Uri.parse(s), dataSourceFactory, extractorsFactory, null, null)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player!!.prepare(mediaSource)
        player?.addListener(listener)
        initMediaControls()
        if (b){
            csetPlayPause(true)
        }
        else{
            csetPlayPause(false)
        }
    }

    // Menu Config
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var id = item?.itemId

        when(id){
            R.id.setting -> {
                println("Setting")
            }
            R.id.app_bar_search -> {
                println("Searching")
            }
        }

        return super.onOptionsItemSelected(item)
    }


    fun SetPlayer(songData: SongData? = null, musicType: MusicType, arr_album:ArrayList<AlbumData>? = ArrayList()){

        player!!.release()
        initPlayer()
        if (musicType == MusicType.Single){ // Songle Song
            album.visibility = View.INVISIBLE
            single.visibility = View.VISIBLE
            play(songData!!.mp3.get(3), true)
            if (songData.singer.equals("")){
                artist_text_small.text = ""
                song_text_small.text = songData.title
            }
            else{
                artist_text_small.text = songData.singer
                song_text_small.text = songData.title
            }
            DownloadIMG().execute(songData.Img_URL)
        }
        else{ // Album
            single.visibility = View.INVISIBLE
            album.visibility = View.VISIBLE
            println(songData!!.title)
            println(arr_album!!.size)
            if (songData.singer.equals("")){
                artist_text_small.text = ""
                song_text_small.text = songData.title
            }
            else{
                song_text_small.text = songData.title
                artist_text_small.text = songData.singer
            }
        }


    }

    inner class DownloadIMG : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg imgurl: String): Bitmap? {

            try {
                if (!imgurl.equals("Not Found")){
                    val url = URL(imgurl[0])

                    val connection = url.openConnection() as HttpURLConnection

                    connection.connect()

                    val inputStream = connection.inputStream

                    return BitmapFactory.decodeStream(inputStream)
                }
               else{
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            //background_image.setImageBitmap(result)
            var imageView = findViewById<ImageView>(R.id.background_image)
            var small_imageView = findViewById<ImageView>(R.id.artist_small_img)
            var circle = findViewById<CircleImageView>(R.id.single_circle)
            if (result != null){
                circle.setImageBitmap(result)
                small_imageView.setImageBitmap(result)
                Blurry.with(applicationContext).sampling(1).from(result).into(imageView)
            }
            dialog?.dismiss()
        }
    }

}

/*inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
      val fragmentTransaction = beginTransaction()
      fragmentTransaction.func()
      fragmentTransaction.commit()
  }
*/