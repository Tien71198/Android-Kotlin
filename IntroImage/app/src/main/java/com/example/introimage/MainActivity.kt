package com.example.introimage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragment1 = SlideFragment()
    val fragment2 = SlideFragment()
    val fragment3 = SlideFragment()
    lateinit var adapter : mypagerAdapter
    lateinit var activity : Activity

    lateinit var preference : SharedPreferences
    val pref_show_intro = "Intro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this
        preference = getSharedPreferences("IntroImage", Context.MODE_PRIVATE)

        if (!preference.getBoolean(pref_show_intro, true))
        {
            startActivity(Intent (activity, DashboardActivity::class.java))
            finish()
        }
        fragment1.setTitle("Welcome to Dac San Mon Ngon Viet Nam")
        fragment2.setTitle("Perfect")
        fragment3.setTitle("Let Go !!!")

        adapter = mypagerAdapter(supportFragmentManager)
        adapter.list.add(fragment1)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        view_pager.adapter = adapter
        btn_next.setOnClickListener {
            view_pager.currentItem++
        }

        btn_skip.setOnClickListener { GoToDashBoard() }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                 if (position == adapter.list.size-1)
                 {
                     // last page
                     btn_next.text = "DONE"
                     btn_next.setOnClickListener {
                         GoToDashBoard()
                     }
                 } else {
                     // has next
                     btn_next.text = "NEXT"
                     btn_next.setOnClickListener {
                         view_pager.currentItem++
                     }
                 }
                when(view_pager.currentItem){
                    0->{
                        indicator1.setTextColor(Color.BLACK)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.GRAY)                    }
                    1->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.BLACK)
                        indicator3.setTextColor(Color.GRAY)
                    }
                    2->{
                        indicator1.setTextColor(Color.GRAY)
                        indicator2.setTextColor(Color.GRAY)
                        indicator3.setTextColor(Color.BLACK)
                    }
                }
            }

        })
    }

    fun GoToDashBoard(){
        startActivity(Intent (activity, DashboardActivity::class.java))
        finish()
        val editor = preference.edit()
        editor.putBoolean(pref_show_intro,false)
        editor.apply()
    }

    class mypagerAdapter(manager: FragmentManager) : FragmentPagerAdapter (manager) {

        val list : MutableList<Fragment> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }
}