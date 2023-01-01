package com.ubproject.theateradmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var movieList: ArrayList<MovieModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        movieRecyclerView = findViewById(R.id.rvMovie)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        movieList = arrayListOf<MovieModel>()

        getMovieData()

    }

    private fun getMovieData() {

        movieRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Movie")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                movieList.clear()
                if (snapshot.exists()){
                    for (movieSnap in snapshot.children){
                        val movieData = movieSnap.getValue(MovieModel::class.java)
                        movieList.add(movieData!!)
                    }
                    val mAdapter = MovieAdapter(movieList)
                    movieRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MovieAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, MovieDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("movieId", movieList[position].movieId)
                            intent.putExtra("about_movie", movieList[position].about_movie)
                            intent.putExtra("banner_image_url", movieList[position].banner_image_url)
                            intent.putExtra("cover_image_url", movieList[position].cover_image_url)
                            intent.putExtra("languages", movieList[position].languages)
                            intent.putExtra("movie_duration", movieList[position].movie_duration)
                            intent.putExtra("movie_name", movieList[position].movie_name)
                            intent.putExtra("release_date", movieList[position].release_date)
                            startActivity(intent)
                        }

                    })

                    movieRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}