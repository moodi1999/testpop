package com.example.ahmadreza.testpop.Activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.SeekBar
import com.example.ahmadreza.testpop.Adaptors.ViewPageAdaptor
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.Datas.*
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
import kotlinx.android.synthetic.main.media_contorol.*
import kotlinx.android.synthetic.main.single_music.*
import kotlinx.android.synthetic.main.slide_toolbar.*
import kotlinx.android.synthetic.main.up_slide_lay.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var Ds: DataStorage? = null

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
                    setPlayPause(false)
                    player?.seekTo(0)
                    if (Ds!!.repeat){
                        setPlayPause(true)
                    }
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
                    println("IDLEEEEEEE")
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_slide_lay)

        Ds = DataStorage.instance
        uiInit()
        getData()
        initPlayer() // init ExoPlayer and make it ready for play
    }

    fun uiInit(){
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.background)
        val imageView = findViewById<ImageView>(R.id.background_main)
        Blurry.with(applicationContext).sampling(1).from(largeIcon).into(imageView)

        // toolbar
        toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("Pop Music")
        toolbar.bringToFront()

        // loading progres
        loading_prog.visibility = View.INVISIBLE
        small_loading_prog.visibility = View.INVISIBLE

        // loading Dialog
        dialog = SpotsDialog.Builder().setCancelable(false).setContext(this).setTheme(R.style.CustomPopup).build()


        // album recycler view in the slideup panel
        val layoutm = LinearLayoutManager(applicationContext)
        album_recyclerview.layoutManager = layoutm
        album_recyclerview.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(album_recyclerview, false)

        //view pager Init func
        viewPagerInit()

        //search layout traslation
        searchlay.animate().translationYBy(2000f).setDuration(300)
    }

    fun viewPagerInit(){  // View pager and Tab Layout

        val viewPad = ViewPageAdaptor(supportFragmentManager)
        viewPad.addFragment(Recent(), "Recent")
        viewPad.addFragment(Categories(), "Ctegories")
        //viewPad.addFragment(Artists(), "Artists")
        viewPad.addFragment(Popular(), "Popular")
        viewpager.adapter = viewPad
        tab_View_pager.setViewPager(viewpager)
        tab_View_pager.bringToFront()
    }

    fun getData(){ // TODO: Get the Main web page content
        dialog?.show()
        DownloadWebContent(applicationContext,CallType.RECENT).execute(Ds!!.Main_URL)

    }


    fun setProgress() {
        song_seekbar!!.requestFocus()
        progresbar_small.requestFocus()
        ring_progress.requestFocus()
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
                    ring_progress.progress = mCurrentPosition
                    curr_time.text = stringForTime(player!!.currentPosition.toInt())
                    song_lenth.text = stringForTime(player!!.duration.toInt())

                    handler!!.postDelayed(this, 1000)

                }
            }
        })

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


    }

    fun setPlayPause(play: Boolean) {
        isPlaying = play
        if (!isPlaying) { // not playing
            play_puase.setImageResource(R.drawable.ic_play)
            small_button.setImageResource(R.drawable.ic_play)
            loading_prog.visibility = View.INVISIBLE
            small_loading_prog.visibility = View.INVISIBLE

        } else { // is playing
            setProgress()
            play_puase.setImageResource(R.drawable.ic_pause)
            small_button.setImageResource(R.drawable.ic_pause)
        }
        if (player != null){
            player!!.playWhenReady = play
        }

    }


    fun stringForTime(timeMs: Int): String {
        val mFormatBuilder = StringBuilder()
        val mFormatter = Formatter(mFormatBuilder, Locale.getDefault())
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
        // Views
        play_puase.requestFocus()
        play_puase.setOnClickListener { setPlayPause(!isPlaying) }
        small_button.setOnClickListener { setPlayPause(!isPlaying) }

        // Code
        bandwidthMeter = DefaultBandwidthMeter()
        extractorsFactory = DefaultExtractorsFactory()
        trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(trackSelectionFactory)
        defaultBandwidthMeter = DefaultBandwidthMeter()
        dataSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter)
    }

    fun play(uri: String, play: Boolean = false){ //TODO: play the (uri) with (play) permisson


        mediaSource = ExtractorMediaSource(Uri.parse(uri), dataSourceFactory, extractorsFactory, null, null)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player!!.prepare(mediaSource)
        player?.addListener(listener)
        setPlayPause(play)
    }

    // Menu Config
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId

        when(id){
            R.id.setting -> {

            }
            R.id.app_bar_search -> {
                println("searchhhhhhh")
                searchlay.animate().translationYBy(-2000f).setDuration(300).withEndAction {
                    searchlay.x = 0f
                    searchlay.y = 0f
                }

                val search = findViewById<SearchView>(R.id.app_bar_search)
                search.setOnCloseListener { println("search closeeeeeeeeee")
                true
                }

            }
            R.id.refresh -> {
                refresh()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    fun SetPlayer(songData: SongData? = null, musicType: MusicType, arr_album:ArrayList<AlbumData>? = ArrayList(), context: Context = applicationContext){

        try {
            if (isPlaying){ // when the user choose new song player release to decrease the memory usage
                player!!.release()
            }
            initPlayer()

            if (songData!!.singer.equals("")){ // page finders couldent find the singer name
                artist_text_small.text = ""
                song_text_small.text = songData.title
            }
            else{
                artist_text_small.text = songData.singer
                song_text_small.text = songData.title
            }

            if (musicType == MusicType.Single){ // Single Song
                album.visibility = View.INVISIBLE
                single.visibility = View.VISIBLE
                try { // first try to play 128 qu
                    play(songData!!.mp3.get(3), true)
                }catch (e: Exception){ // if that qu wasnt there play 320q
                    play(songData!!.mp3.get(1), true)
                }
                DownloadIMG().execute(songData!!.Img_URL)
            }
            else{ // Album
                single.visibility = View.INVISIBLE
                album.visibility = View.VISIBLE
                println(songData!!.title)
                println(arr_album!!.size)

            }
        }catch (e: Exception){ // if anything happend among the playing a dialog showed
            dialog?.dismiss()

            errorDialog(context, songData!!.url)
        }

    }

    inner class DownloadIMG : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg imgurl: String): Bitmap? {

            try {
                if (!imgurl.equals("Not Found")) {
                    println(imgurl[0])
                    val url = URL(imgurl[0])

                    val connection = url.openConnection() as HttpURLConnection

                    connection.connect()

                    val inputStream = connection.inputStream

                    return BitmapFactory.decodeStream(inputStream)
                } else {
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }
        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
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

    fun refresh(){
        Ds!!.arr_recentData.clear()
        Ds!!.rec_page_num = 1
        Ds!!.item_categoWebContent_Done = null
        Ds!!.Cat_isCreated = false
        Ds!!.week_con = ""
        Ds!!.month_con = ""
        Ds!!.year_con = ""
        Ds!!.arr_popu_week.clear()
        Ds!!.arr_popu_month.clear()
        Ds!!.arr_popu_year.clear()
        Ds!!.item_categoWebContent = ""
        Ds!!.item_categoWebContent_Done = null
        Ds!!.arr_categories.clear()
        Ds!!.arr_catego_item_Data.clear()
        getData()
        viewPagerInit()
    }

    fun errorDialog(context: Context, url: String) {
        var builder = AlertDialog.Builder(context)
        var alertdialog: AlertDialog? = null
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setTitle("\nSorry!! :(")
        builder.setMessage("We can not play tis song for some resean  \nwant to check the song page on your browser?")
        builder.setPositiveButton("yes",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                openBrowser(url)
            }
        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                alertdialog?.cancel()
            }

        })
        alertdialog = builder.create()
        alertdialog!!.show()
    }

    fun openBrowser(url: String) {
        val lun = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(lun)
    }

    fun downloadMp3(arrayList: ArrayList<String>, context: Context){
        val lun = Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(1)))
        startActivity(lun)
    }


}

/*inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
      val fragmentTransaction = beginTransaction()
      fragmentTransaction.func()
      fragmentTransaction.commit()
  }
*/