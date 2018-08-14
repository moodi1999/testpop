package com.example.ahmadreza.testpop.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ahmadreza.testpop.Activities.MainActivity
import com.example.ahmadreza.testpop.DataFinders.SongDataFinder
import com.example.ahmadreza.testpop.DataGeters.DownloadWebContent
import com.example.ahmadreza.testpop.Datas.CallType

import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.fragment_recent.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [Recent.newInstance] factory method to
 * create an instance of this fragment.
 */
class Recent : Fragment() {


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

   /* override fun onPause() {
        super.onPause()
        DataStorage.instance.arr_recentData.clear()

    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recent, container, false)

       if (!DataStorage.instance.isCreated){
           SongDataFinder(view!!, context, activity, CallType.RECENT).execute("")
       }
        Ui(view)

        scrolling(view)


        return view
    }


    fun Ui(view: View): Unit {
        val layoutm = GridLayoutManager(context, 2)
        view.recent_recyclerView.layoutManager = layoutm
        view.recent_recyclerView.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(view.recent_recyclerView, false)

        view.more_recent.setOnClickListener{
            DataStorage.instance.page_num ++
            (activity as MainActivity).dialog?.show()
            DataStorage.instance.recentWebContent_Done = null
            DownloadWebContent(context!!,CallType.RECENT).execute(DataStorage.instance.Main_URL + DataStorage.instance.page_txt + DataStorage.instance.page_num.toString())
            SongDataFinder(view!!, context, activity, CallType.RECENT).execute("")
        }
    }

    fun scrolling(view: View){
        var scrollup = true
        if (view.scrollView_recent != null) {

            view.scrollView_recent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY) { // Up
                    if (scrollup){
                        activity!!.toolbar.x = 0f
                        activity!!.toolbar.y = 0f
                        activity!!.toolbar.animate().translationYBy(-activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = false }).setDuration(400)


                    }

                }
                else if (scrollY < oldScrollY) { // Down
                    if (!scrollup){

                        activity!!.toolbar.animate().translationYBy(activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = true
             activity!!.toolbar.x = 0f
             activity!!.toolbar.y = 0f
             }).setDuration(200)
                        view.more_recent.animate().translationY(150f).setDuration(100)
                    }

                }

                else if (scrollY == 0) {
                    activity!!.toolbar.x = 0f
                    activity!!.toolbar.y = 0f

                }

                if(scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    view.more_recent.bringToFront()
                    view.more_recent.animate().translationY(-150f).setDuration(100)
                }
            })
        }
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
         * @return A new instance of fragment Recent.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Recent {
            print("newinstance")
            val fragment = Recent()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
