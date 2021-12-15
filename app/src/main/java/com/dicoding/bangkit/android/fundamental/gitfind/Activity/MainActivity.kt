package com.dicoding.bangkit.android.fundamental.gitfind.Activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bangkit.android.fundamental.gitfind.Adapter.GithubAdapter
import com.dicoding.bangkit.android.fundamental.gitfind.BuildConfig
import com.dicoding.bangkit.android.fundamental.gitfind.Pojo.Github
import com.dicoding.bangkit.android.fundamental.gitfind.R
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var  rvcar : RecyclerView
    private lateinit var githubAdapter: GithubAdapter
    private var list : ArrayList<Github> = arrayListOf()
    private lateinit var progressBarMain : ProgressBar

    companion object{
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBarMain = findViewById(R.id.progressBarMain)
        rvcar = findViewById(R.id.rv_github)

        showrecyclerViewConfig()
        getUser()
    }

    private fun getUser() {
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.API_GITHUB}")
        val url = "https://api.github.com/users"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                progressBarMain.visibility = View.INVISIBLE
                val hasil = String(responseBody)
                Log.d(tag as String?,hasil)
                try {
                    val jsonarray = JSONArray(hasil)
                    for (i in 0 until jsonarray.length()){
                        val jsonObject = jsonarray.getJSONObject(i)
                        val username : String = jsonObject.getString("login")
                        getUserDetail(username)
                    }
                }catch (e : Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                progressBarMain.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()
            }

        })

    }



    private fun getUserSearch(id: String) {
        progressBarMain.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.API_GITHUB}")
        val url = "https://api.github.com/search/users?q=$id"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                progressBarMain.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getUserDetail(username)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                progressBarMain.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()
            }

        })

    }

    private fun getUserDetail(id: String) {
        progressBarMain.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token ${BuildConfig.API_GITHUB}")
        val url = "https://api.github.com/users/$id"
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                progressBarMain.visibility = View.INVISIBLE
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val jsonObject = JSONObject(result)
                    val username: String? = jsonObject.getString("login").toString()
                    val name: String? = jsonObject.getString("name").toString()
                    val avatar: String? = jsonObject.getString("avatar_url").toString()
                    val company: String? = jsonObject.getString("company").toString()
                    val location: String? = jsonObject.getString("location").toString()
                    val repository: String? = jsonObject.getString("public_repos")
                    val followers: String? = jsonObject.getString("followers")
                    val following: String? = jsonObject.getString("following")
                    list.add(
                            Github(username,
                                    name,
                                    avatar,
                                    company,
                                    location,
                                    repository,
                                    followers,
                                    following,
                            )
                    )
                    showListRecyclerView()

                }catch (e : Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showrecyclerViewConfig() {
        rvcar.layoutManager = LinearLayoutManager(rvcar.context)
        rvcar.setHasFixedSize(true)
        rvcar.addItemDecoration(
                DividerItemDecoration(
                        rvcar.context,
                        DividerItemDecoration.VERTICAL
                )
        )
    }

    private fun showListRecyclerView(){
        rvcar.layoutManager = LinearLayoutManager(this)
        val githubAdapter = GithubAdapter(list)
        rvcar.adapter = githubAdapter
    }
    private fun showSelectedUser(dataUser: Github) {
        Github(
                dataUser.username,
                dataUser.name,
                dataUser.photoo,
                dataUser.company,
                dataUser.location,
                dataUser.repository,
                dataUser.followers,
                dataUser.following
        )
        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_DATA, dataUser)

        this@MainActivity.startActivity(intent)
        Toast.makeText(
                this,
                "${dataUser.name}",
                Toast.LENGTH_SHORT
        ).show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        val searchmanagerrr = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchmanagerrr.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    list.clear()
                    getUserSearch(query)
                }
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }



}