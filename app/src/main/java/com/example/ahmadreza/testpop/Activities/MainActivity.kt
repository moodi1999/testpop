package com.example.ahmadreza.testpop.Activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
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
import com.google.android.exoplayer2.ExoPlayerFactory
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
import kotlinx.android.synthetic.main.album_lay.*
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.fragment_recent.view.*
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

    var seekPlayerProgress: SeekBar? = null
    var handler: Handler? = null
    var btnPlay: ImageButton? = null
    var txtCurrentTime: TextView? = null
    var txtEndTime: TextView? = null
    var isPlaying = false

    var song = ""


    fun initSeekBar() {
        seekPlayerProgress = findViewById<SeekBar>(R.id.mediacontroller_progress)
        seekPlayerProgress!!.requestFocus()

        seekPlayerProgress!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

        seekPlayerProgress!!.max = 0
        seekPlayerProgress!!.max = player!!.duration.toInt() / 1000

    }

    fun setProgress() {
        seekPlayerProgress!!.progress = 0
        seekPlayerProgress!!.max = player!!.duration.toInt() / 1000
        txtCurrentTime!!.text = stringForTime(player!!.currentPosition.toInt())
        txtEndTime!!.text = stringForTime(player!!.duration.toInt())

        if (handler == null) handler = Handler()
        //Make sure you update Seekbar on UI thread
        handler!!.post(object : Runnable {
            override fun run() {
                if (player != null && isPlaying) {
                    seekPlayerProgress!!.max = player!!.duration.toInt() / 1000
                    val mCurrentPosition = player!!.currentPosition.toInt() / 1000
                    seekPlayerProgress!!.progress = mCurrentPosition
                    txtCurrentTime!!.text = stringForTime(player!!.currentPosition.toInt())
                    txtEndTime!!.text = stringForTime(player!!.duration.toInt())

                    handler!!.postDelayed(this, 1000)
                    /*        when(player?.playbackState){

                                Player.STATE_BUFFERING -> {
                                   println("Bufringggggggggggggggggggggggggg")
                                }

                                Player.STATE_READY -> {
                                   println("readyyyyyyyyyyyyyyyyyyy")
                                }

                                Player.STATE_ENDED -> {
                                    println("readyyyyyyyyyyyyyyyyyyy")
                                }
                           }*/
                }
            }
        })


    }

    fun initTxtTime() {
        txtCurrentTime = findViewById<TextView>(R.id.time_current)
        txtEndTime = findViewById<TextView>(R.id.player_end_time)
    }

    fun csetPlayPause(play: Boolean) {
        isPlaying = play
        player!!.playWhenReady = play
        if (!isPlaying) {
            btnPlay!!.setImageResource(android.R.drawable.ic_media_play)
        } else {
            setProgress()
            btnPlay!!.setImageResource(android.R.drawable.ic_media_pause)
        }
    }


    fun initPlayButton() {
        btnPlay = findViewById<ImageButton>(R.id.btnPlay)
        btnPlay!!.requestFocus()
        btnPlay!!.setOnClickListener { csetPlayPause(!isPlaying)}
    }


    fun initMediaControls() {
        initPlayButton()
        initSeekBar()
        initTxtTime()
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_slide_lay)
        uiInit()
        getData()
        initPlayer()


    }

    fun initPlayer(){
        bandwidthMeter = DefaultBandwidthMeter()
        extractorsFactory = DefaultExtractorsFactory()

        trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

        trackSelector = DefaultTrackSelector(trackSelectionFactory)

        /*        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);*/

        defaultBandwidthMeter = DefaultBandwidthMeter()
    }
    fun play(s: String, b: Boolean = false){

        dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter)


        mediaSource = ExtractorMediaSource(Uri.parse(s), dataSourceFactory, extractorsFactory, null, null)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player!!.prepare(mediaSource)
        initMediaControls()
        if (b){
            csetPlayPause(true)
        }
        else{
            csetPlayPause(false)
        }
    }

    fun getData(){
        DownloadWebContent(CallType.RECENT).execute(DataStorage.instance.Main_URL)
    }



    fun uiInit(){
        toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Pop Music")


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
                println("hi")
            }
        }

        return super.onOptionsItemSelected(item)
    }


    fun SetPlayer(songData: SongData? = null, musicType: MusicType, arr_album:ArrayList<AlbumData>? = ArrayList()){

        if (musicType == MusicType.Single){ // Songle Song
            album.visibility = View.INVISIBLE
            single.visibility = View.VISIBLE
            DownloadIMG().execute(songData!!.Img_URL)
            play(songData.mp3.get(3))
        }
        else{ // Album
            single.visibility = View.INVISIBLE
            album.visibility = View.VISIBLE
           // DownloadIMG().execute(songData!!.Img_URL)
            println(songData!!.title)
            println(arr_album!!.size)
            val adaptor = AlbumRecyAdp(songData!!, arr_album!!)
            album_recyclerview.adapter = adaptor

        }

    }

    inner class DownloadIMG : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg imgurl: String): Bitmap? {

            try {

                val url = URL(imgurl[0])

                val connection = url.openConnection() as HttpURLConnection

                connection.connect()

                val inputStream = connection.inputStream

                return BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            background_image.setImageBitmap(result)
        }
    }

}

/*inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
      val fragmentTransaction = beginTransaction()
      fragmentTransaction.func()
      fragmentTransaction.commit()
  }
*/