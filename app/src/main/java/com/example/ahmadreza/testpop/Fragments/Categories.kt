package com.example.ahmadreza.testpop.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ahmadreza.testpop.DataFinders.CategoryDF
import com.example.ahmadreza.testpop.Storege.DataStorage

import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.catego_first_lay.view.*
import kotlinx.android.synthetic.main.catego_second_lay.*
import kotlinx.android.synthetic.main.catego_second_lay.view.*
import kotlinx.android.synthetic.main.cor_activity_main.*
import kotlinx.android.synthetic.main.fragment_categories.view.*


class Categories : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    /*override fun onPause() {
        super.onPause()
        DataStorage.instance.arr_categories.clear()

    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        println("Categories.onCreateView")
        Ui(view)

        CategoryDF(view, context!!, activity).execute()

        return view
    }

    fun Ui(view: View) {
        /*val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.background)
        val imageView = view.findViewById<ImageView>(R.id.background_main)
        Blurry.with(context).sampling(1).from(largeIcon).into(imageView)*/

        view.first.x = 0f
        view.first.y = 0f
        view.second.animate().translationXBy(-2000f).setDuration(600).withEndAction {
            view.second.x = -2000f
            view.second.y = -2000f
        }

        val layoutm = GridLayoutManager(context, 3)
        view.category_recyclerView_fst.layoutManager = layoutm
        view.category_recyclerView_fst.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(view.category_recyclerView_fst, false)


        val layoutm_song = GridLayoutManager(context, 2)
        view.category_recyclerView_sec.layoutManager = layoutm_song
        view.category_recyclerView_sec.setHasFixedSize(true)
        ViewCompat.setNestedScrollingEnabled(view.category_recyclerView_sec, false)

        view.catego_chane_page.setOnClickListener {
            DataStorage.instance.arr_catego_item_Data.clear()

            activity!!.toolbar.x = 0f
            activity!!.toolbar.y = 0f
            view.second.x = 0f
            view.second.y = 0f
            view.second.animate().translationXBy(-2000f).setDuration(600).withEndAction {
                view.second.x = -2000f
                view.second.y = -2000f
            }
            view.first.animate().translationXBy(2000f).setDuration(600).withEndAction {
                view.first.x = 0f
                view.first.y = 0f
            }
        }

        scrolling_item(view)
        scrolling_song(view)


    }

    fun scrolling_item(view: View){
        var scrollup = true
        if (view.catego_item_scroll != null) {

            view.catego_item_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
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
                            activity!!.toolbar.y = 0f}).setDuration(200)

                        activity!!.swip.animate().translationYBy(activity!!.toolbar.height.toFloat()).withEndAction(Runnable {

                            activity!!.swip.y = activity!!.toolbar.height.toFloat() + activity!!.tab_View_pager.height.toFloat()
                        }).setDuration(200)

                    }

                }

                if (scrollY == 0) {
                    activity!!.toolbar.x = 0f
                    activity!!.toolbar.y = 0f

                }

                if(scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                }
            })
        }
    }


    fun scrolling_song(view: View){
        var scrollup = true
        if (view.catego_song_scroll != null) {

            view.catego_song_scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
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

                        view.more_catego.animate().translationY(150f).setDuration(100)
                    }

                }

                else if (scrollY == 0) {
                    activity!!.toolbar.x = 0f
                    activity!!.toolbar.y = 0f

                }

                if(scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    view.more_catego.bringToFront()
                    view.more_catego.animate().translationY(-150f).setDuration(100)
                }
            })
        }
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): Categories {
            val fragment = Categories()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
