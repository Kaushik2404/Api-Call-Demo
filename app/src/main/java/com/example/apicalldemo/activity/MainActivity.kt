package com.example.apicalldemo.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apicalldemo.ApiInterface
import com.example.apicalldemo.ApiViewModal
import com.example.apicalldemo.OnClickIteamMovie
import com.example.apicalldemo.R
import com.example.apicalldemo.ServiceGen
import com.example.apicalldemo.adapter.MovieAdapter
import com.example.apicalldemo.databinding.ActivityMainBinding
import com.example.apicalldemo.modal.DemoModal
import com.example.apicalldemo.modal.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var response: DemoModal
    private lateinit var oklist: List<Movie>
    private lateinit var list: List<Movie>
    private lateinit var adapter: MovieAdapter
    private var result=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = arrayListOf()
        oklist= arrayListOf()

        filterView()
        serchView()
        callApi()

    }

    private fun filterView() {

        binding.movieFilter.setOnClickListener {
            val popupMenu = PopupMenu(this,binding.movieFilter)
//         add the menu
            popupMenu.inflate(R.menu.filter_movie)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
            }
            // implement on menu item click Listener
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId){
                        R.id.asc -> {
                             oklist=list.sortedBy { it.title }
//                            list.sortedByDescending { it.title }
                            Log.d("TAG_LIST",oklist.toString())
                            adapter.filterList(oklist)
                            adapter.notifyDataSetChanged()

                            return true
                        }
                        // in the same way you can implement others
                        R.id.desc -> {
                             oklist=list.sortedByDescending { it.title }
//                            list.sortedByDescending { it.title }
                            Log.d("TAG_LIST",oklist.toString())
                            adapter.filterList(oklist)
                            adapter.notifyDataSetChanged()
                            return true
                        }

                    }
                    return false
                }
            })
            popupMenu.show()
        }
    }

    private fun serchView() {
        binding.serch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (result){
                    Toast.makeText(this@MainActivity, "No Movie Found", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(newText)
                }
                return false
            }

        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist = ArrayList<Movie>()

        // running a for loop to compare elements.
        for (item in list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.title?.toLowerCase()?.contains(text.lowercase(Locale.getDefault())) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            filteredlist.clear()
            oklist=filteredlist
            adapter.filterList(filteredlist)
            result= true
        } else {
            result=false
            oklist=filteredlist
            adapter.filterList(oklist)
        }
    }

    private fun callApi() {

        oklist= ApiViewModal.callingAPi()
        val apiService: ApiInterface = ServiceGen.buildService(ApiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            response = apiService.getPopularMovies()


            runOnUiThread {
                 response.results?.let {
                    oklist= it as List<Movie>
                    list= it
                    Log.d("TAG_SIZE",list.size.toString())
                     adapter =MovieAdapter(this@MainActivity,oklist, object : OnClickIteamMovie {
                        override fun getPost(pos: Int) {
                            val intent= Intent(this@MainActivity,MovieDetailActivity::class.java)
                            intent.putExtra("MOVIE_NAME",oklist[pos]?.title)
                            intent.putExtra("MOVIE_IMG", oklist[pos]?.posterPath)
                            intent.putExtra("MOVIE_RATE",oklist[pos]?.voteAverage.toString())
                            intent.putExtra("MOVIE_DETAIL",oklist[pos]?.overview)
                            startActivity(intent)

                        }
                    })
                }
                val layoutManager = GridLayoutManager(this@MainActivity, 2)
                binding.rvView.layoutManager = layoutManager
                binding.rvView.adapter = adapter
            }
        }


    }
}