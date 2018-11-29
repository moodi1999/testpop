package com.example.ahmadreza.testpop.Fragments


import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.ahmadreza.testpop.DataFinders.PopularDF

import com.example.ahmadreza.testpop.R
import com.example.ahmadreza.testpop.Storege.DataStorage
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.view.*
import kotlinx.android.synthetic.main.fragment_recent.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [Popular.newInstance] factory method to
 * create an instance of this fragment.
 */
class Popular : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular, container, false)
        println("Popular.onCreateView")
        activity!!.toolbar.x = 0f
        activity!!.toolbar.y = 0f
        PopularDF(view, context!!, activity).execute()

        Ui(view)

        return view
    }

    fun Ui(view: View?){
        /*val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.background)
        val imageView = view?.findViewById<ImageView>(R.id.background_main)
        Blurry.with(context).sampling(1).from(largeIcon).into(imageView)*/

        ViewCompat.setNestedScrollingEnabled(view!!.popular_lay, false)
        ViewCompat.setNestedScrollingEnabled(view.week_recyclerview, false)
        ViewCompat.setNestedScrollingEnabled(view.month_recyclerview, false)
        ViewCompat.setNestedScrollingEnabled(view.year_recyclerview, false)

        val layoutmw = LinearLayoutManager(context)
        view?.week_recyclerview?.layoutManager = layoutmw
        view?.week_recyclerview?.setHasFixedSize(true)

        val layoutmm = LinearLayoutManager(context)
        view?.month_recyclerview?.layoutManager = layoutmm
        view?.month_recyclerview?.setHasFixedSize(true)

        val layoutmy = LinearLayoutManager(context)
        view?.year_recyclerview?.layoutManager = layoutmy
        view?.year_recyclerview?.setHasFixedSize(true)

        scrolling(view!!)

    }

    fun scrolling(view: View){
        var scrollup = true
        if (view.popular_scroll != null) {

            view.popular_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY) { // Up
                    if (scrollup){
                        activity!!.toolbar.x = 0f
                        activity!!.toolbar.y = 0f
                        activity!!.toolbar.animate().translationYBy(-activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = false }).setDuration(400)

                        activity!!.swip.animate().translationYBy(-activity!!.toolbar.height.toFloat()).withEndAction(Runnable {
                            activity!!.swip.y = activity!!.tab_View_pager.height.toFloat()
                        }).setDuration(400)

                    }

                }
                else if (scrollY < oldScrollY) { // Down
                    if (!scrollup){

                        activity!!.toolbar.animate().translationYBy(activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = true
                            activity!!.toolbar.x = 0f
                            activity!!.toolbar.y = 0f
                        }).setDuration(200)


                        activity!!.swip.animate().translationYBy(activity!!.toolbar.height.toFloat()).withEndAction(Runnable {

                            activity!!.swip.y = activity!!.toolbar.height.toFloat() + activity!!.tab_View_pager.height.toFloat()
                        }).setDuration(200)
                    }

                }

                else if (scrollY == 0) {
                    activity!!.toolbar.x = 0f
                    activity!!.toolbar.y = 0f

                }

                if(scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
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
         * @return A new instance of fragment Popular.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Popular {
            val fragment = Popular()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
