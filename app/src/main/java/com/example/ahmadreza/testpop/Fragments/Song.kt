package com.example.ahmadreza.testpop.Fragments

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ahmadreza.testpop.R
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.EventListener

import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

import java.util.Formatter
import java.util.Locale


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Song : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private val startButton: Button? = null
    private val stopButton: Button? = null

    var player: SimpleExoPlayer? = null
    private var bandwidthMeter: BandwidthMeter? = null
    private var extractorsFactory: ExtractorsFactory? = null
    private var trackSelectionFactory: TrackSelection.Factory? = null
    private var trackSelector: TrackSelector? = null
    private var defaultBandwidthMeter: DefaultBandwidthMeter? = null
    private var dataSourceFactory: DataSource.Factory? = null
    private var mediaSource: MediaSource? = null

    private var seekPlayerProgress: SeekBar? = null
    private var handler: Handler? = null
    private var btnPlay: ImageButton? = null
    private var txtCurrentTime: TextView? = null
    private var txtEndTime: TextView? = null
    private var isPlaying = false


    internal var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }


    private fun initSeekBar() {
        seekPlayerProgress = view?.findViewById<SeekBar>(R.id.mediacontroller_progress)
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

    private fun setProgress() {
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

    private fun initTxtTime() {
        txtCurrentTime = view?.findViewById<TextView>(R.id.time_current)
        txtEndTime = view?.findViewById<TextView>(R.id.player_end_time)
    }

    private fun csetPlayPause(play: Boolean) {
        isPlaying = play
        player!!.playWhenReady = play
        if (!isPlaying) {
            btnPlay!!.setImageResource(android.R.drawable.ic_media_play)
        } else {
            setProgress()
            btnPlay!!.setImageResource(android.R.drawable.ic_media_pause)
        }
    }


    private fun initPlayButton() {
        btnPlay = view?.findViewById<ImageButton>(R.id.btnPlay)
        btnPlay!!.requestFocus()
        btnPlay!!.setOnClickListener { csetPlayPause(!isPlaying)}
    }


    private fun initMediaControls() {
        initPlayButton()
        initSeekBar()
        initTxtTime()
    }


    private fun stringForTime(timeMs: Int): String {
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

    var eventListener: Player.EventListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_song, container, false)


        /* startButton = (Button) view.findViewById(R.id.startButton);
        stopButton = (Button) view.findViewById(R.id.stopButton);
*/
        bandwidthMeter = DefaultBandwidthMeter()
        extractorsFactory = DefaultExtractorsFactory()

        trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

        trackSelector = DefaultTrackSelector(trackSelectionFactory)

        /*        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"),
                (TransferListener<? super DataSource>) bandwidthMeter);*/

        defaultBandwidthMeter = DefaultBandwidthMeter()
        dataSourceFactory = DefaultDataSourceFactory(context!!,
                Util.getUserAgent(context, "mediaPlayerSample"), defaultBandwidthMeter)


        mediaSource = ExtractorMediaSource(Uri.parse(ARG_PARAM1), dataSourceFactory, extractorsFactory, null, null)
        try {
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
            player!!.prepare(mediaSource)
            initMediaControls()
            println("${player?.playbackState}  player back")
            println("im gone playyyyyyyyyyyyyyyyyyyyyyyyyyyy ${ARG_PARAM1}")
        }
        catch (e: Exception){
            e.printStackTrace()
        }


        // player!!.addListener(eventListener)
        /*    startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(true);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);
            }
        });
*/

        return view
    }

    fun play(a: String){
        player!!.playWhenReady = true
    }


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters

        fun newInstance(param1: String, param2: String): Song {
            val fragment = Song()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

        private val TAG = "MainActivity"
    }

}// Required empty public constructor
