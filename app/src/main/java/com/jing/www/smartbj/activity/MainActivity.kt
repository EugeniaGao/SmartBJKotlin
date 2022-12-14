package com.jing.www.smartbj.activity


import android.os.Bundle
import com.jing.www.smartbj.R

import android.view.View
import android.widget.RadioButton

import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu
import com.jing.www.smartbj.adapter.MenuAdapter
import com.jing.www.smartbj.bean.NewsCenterBean
import com.jing.www.smartbj.fragment.HomeTabFragment
import com.jing.www.smartbj.fragment.NewsCenterFragment
import com.jing.www.smartbj.fragment.SmartServiceTabFragment
import com.jing.www.smartbj.fragment.GovaffairsFragment
import com.jing.www.smartbj.fragment.SettingTabFragment
import com.jing.www.smartbj.adapter.TabVpAdapter
import com.jing.www.smartbj.base.BaseFragment
import com.jing.www.smartbj.base.BaseLoadNetDataOperator
import com.jing.www.smartbj.beannew.SecondTabBean
import java.util.ArrayList

/**
 * Created by Administrator on 2017/2/5.
 */
class MainActivity : FragmentActivity(), RadioGroup.OnCheckedChangeListener {
    private var vp: ViewPager? = null
    private var rb_home: RadioButton? = null
    private var rb_setting: RadioButton? = null
    private var rg_tab: RadioGroup? = null
    private var fragments: MutableList<Fragment>? = null
    @JvmField
    var slidingMenu: SlidingMenu? = null
    var newsCenterMenuBeanList: List<NewsCenterBean.NewsCenterMenuBean>? = null

    private var adapter: MenuAdapter? = null
    @JvmName("setNewsCenterMenuBeanList1")
    fun setNewsCenterMenuBeanList(newsCenterMenuBeanList: List<NewsCenterBean.NewsCenterMenuBean>?) {
        this.newsCenterMenuBeanList = newsCenterMenuBeanList
//        initRecycleView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        initEvent()
        initViewPager()
    }

    private fun initRecycleView() {
        //1.?????????..????????????
        val rv_menu = slidingMenu!!.findViewById<View>(R.id.rv_menu) as RecyclerView
        //2.?????????????????????
        rv_menu.layoutManager = LinearLayoutManager(this)
        //3??????????????????????????????
        adapter = MenuAdapter(this, newsCenterMenuBeanList)
        rv_menu.adapter = adapter
    }

    private fun initView() {
        vp = findViewById<View>(R.id.vp) as ViewPager
        rg_tab = findViewById<View>(R.id.rg) as RadioGroup
        rb_home = findViewById<View>(R.id.rb_tab_home) as RadioButton
        rb_setting = findViewById<View>(R.id.rb_tab_setting) as RadioButton
    }

    private fun initEvent() {
        rg_tab!!.setOnCheckedChangeListener(this)
    }

    private fun initViewPager() {
        fragments = ArrayList()
        (fragments as ArrayList<Fragment>).add(NewsCenterFragment())
        (fragments as ArrayList<Fragment>).add(SettingTabFragment())
        vp!!.adapter = TabVpAdapter(supportFragmentManager, fragments as ArrayList<Fragment>)
    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        var item = 0
        when (checkedId) {
            R.id.rb_tab_home -> {
//                slidingMenu!!.touchModeAbove = SlidingMenu.TOUCHMODE_NONE
                item = 0
            }

            R.id.rb_tab_setting -> {
//                slidingMenu!!.touchModeAbove = SlidingMenu.TOUCHMODE_NONE
                item = 1
            }
        }
        vp!!.setCurrentItem(item, false)

        //?????????fragment
        val itemFragment = fragments!![item] as BaseFragment
        if (itemFragment is BaseLoadNetDataOperator) {
            //?????????fragment?????????????????????????????????????????????????????????????????????????????????
            (itemFragment as BaseLoadNetDataOperator).loadNetData()
        }
    }


    //2.????????????????????????fragment//1.????????????????????????fragment???id

    //????????????????????????
    val currentTabFragment: BaseFragment
        get() {
            val currentItem = vp!!.currentItem //1.????????????????????????fragment???id
            return fragments!![currentItem] as BaseFragment //2.????????????????????????fragment
        }
}