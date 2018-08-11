package com.example.ahmadreza.testpop.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ahmadreza.testpop.DataFinders.CategoryDF
import com.example.ahmadreza.testpop.Storege.DataStorage

import com.example.ahmadreza.testpop.R
import kotlinx.android.synthetic.main.catego_first_lay.view.*
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

    override fun onPause() {
        super.onPause()
        DataStorage.instance.arr_categories.clear()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        Ui(view)
        view.second.animate().translationXBy(10000f).setDuration(10000)
        CategoryDF(view, context!!, activity).execute()
        scrolling(view)

        return view
    }

    fun Ui(view: View) {
        val layoutm = LinearLayoutManager(context)
        view.category_recyclerView_fst.layoutManager = layoutm
        view.category_recyclerView_fst.setHasFixedSize(true)


    }

    fun scrolling(view: View){
        var scrollup = true
        if (view.scrollView_catego != null) {

            view.scrollView_catego.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY) { // Up
                    if (scrollup){
                        activity!!.toolbar.x = 0f
                        activity!!.toolbar.y = 0f
                        activity!!.toolbar.animate().translationYBy(-activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = false }).setDuration(400)


                    }

                }
                else if (scrollY < oldScrollY) { // Down
                    if (!scrollup){

                        activity!!.toolbar.animate().translationYBy(activity!!.toolbar.height.toFloat()).withEndAction(Runnable { scrollup = true }).setDuration(200)
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
