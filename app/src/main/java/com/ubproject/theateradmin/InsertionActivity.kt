package com.ubproject.theateradmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    private lateinit var etAboutMovie: EditText
    private lateinit var etBannerImage: EditText
    private lateinit var etCoverImage: EditText
    private lateinit var etLanguages: EditText
    private lateinit var etMovieDuration: EditText
    private lateinit var etMovieName: EditText
    private lateinit var etReleaseDate: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etAboutMovie = findViewById(R.id.etAboutMovie)
        etBannerImage = findViewById(R.id.etBannerImgUrl)
        etCoverImage = findViewById(R.id.etCoverImgUrl)
        etLanguages = findViewById(R.id.etLanguages)
        etMovieDuration = findViewById(R.id.etDuration)
        etMovieName = findViewById(R.id.etMovieName)
        etReleaseDate = findViewById(R.id.etReleaseDate)

        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Movie")

        btnSaveData.setOnClickListener {
            saveMovieData()
        }
    }

    private fun saveMovieData() {

        //getting values
        val about_movie = etAboutMovie.text.toString()
        val banner_image_url = etBannerImage.text.toString()
        val cover_image_url = etCoverImage.text.toString()
        val languages = etLanguages.text.toString()
        val movie_name = etMovieName.text.toString()
        val movie_duration = etMovieDuration.text.toString()
        val release_date = etReleaseDate.text.toString()

        if (about_movie.isEmpty()) {
            etAboutMovie.error = "About the movie required!"
        }
        if (banner_image_url.isEmpty()) {
            etBannerImage.error = "Banner Image Url Required!"
        }
        if (cover_image_url.isEmpty()) {
            etCoverImage.error = "Cover Image Url Required!"
        }
        if (languages.isEmpty()) {
            etLanguages.error = "Movie Languages is Required!"
        }
        if (movie_duration.isEmpty()) {
            etMovieDuration.error = "Movie Duration is Required!"
        }
        if (movie_name.isEmpty()) {
            etMovieName.error = "Movie Name is Required!"
        }
        if (release_date.isEmpty()) {
            etReleaseDate.error = "Add Release Date"
        }
        val movieId = dbRef.push().key!!

        val movie = MovieModel(movieId, about_movie, banner_image_url, cover_image_url, languages, movie_duration, movie_name, release_date)

        dbRef.child(movieId).setValue(movie)
            .addOnCompleteListener {
                Toast.makeText(this, "Movie Added successfully", Toast.LENGTH_LONG).show()

                etAboutMovie.text.clear()
                etBannerImage.text.clear()
                etCoverImage.text.clear()
                etLanguages.text.clear()
                etMovieDuration.text.clear()
                etMovieName.text.clear()
                etReleaseDate.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}