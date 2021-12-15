package com.dicoding.bangkit.android.fundamental.gitfind.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bangkit.android.fundamental.gitfind.Pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.dicoding.bangkit.android.fundamental.gitfind.Adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    private lateinit var tvusername : TextView
    private lateinit var tvname : TextView
    private lateinit var tvrepo : TextView
    private lateinit var tvcompany : TextView
    private lateinit var tvlocation : TextView
    private lateinit var cvfoto : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        setData()
        setTabDetail()
    }

    private fun setTabDetail(){
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }




    private fun setData() {
        tvusername = findViewById(R.id.tvusernamedetail)
        tvname = findViewById(R.id.tvnamedetail)
        tvrepo = findViewById(R.id.tvrepodetail)
        tvcompany = findViewById(R.id.tvcompanydetail)
        tvlocation = findViewById(R.id.tvlocationdetail)
        cvfoto = findViewById(R.id.cv_user_detail)

        val receivepaket = intent.getParcelableExtra<Github>(EXTRA_DATA) as Github

        tvusername.text = receivepaket.username
        tvname.text = receivepaket.name
        tvcompany.text = receivepaket.company
        tvlocation.text = receivepaket.location
        tvrepo.text = receivepaket.repository
        Glide.with(this)
                .load(receivepaket.photoo)
                .apply(RequestOptions().override(75,75))
                .into(cvfoto)

    }


}