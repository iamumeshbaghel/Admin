package com.ubproject.theateradmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmpId: TextView
    private lateinit var tvAboutMovie: TextView
    private lateinit var tvBannerImg: TextView
    private lateinit var tvCoverImg: TextView
    private lateinit var tvLanguage: TextView
//    private lateinit var tvDuration: TextView
//    private lateinit var tvMovieName: TextView
//    private lateinit var tvRating: TextView
//    private lateinit var tvReleaseDate: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("about_movie").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvAboutMovie = findViewById(R.id.tvAboutMovie)
        tvBannerImg = findViewById(R.id.tvBannerImgUrl)
        tvCoverImg = findViewById(R.id.tvCoverImgUrl)
        tvLanguage = findViewById(R.id.tvLang)
//        tvDuration = findViewById(R.id.tvDuration)
//        tvMovieName = findViewById(R.id.tvMovieName)
//        tvRating = findViewById(R.id.tvRating)
//        tvReleaseDate = findViewById(R.id.tvReleaseDate)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvAboutMovie.text = intent.getStringExtra("about_movie")
        tvBannerImg.text = intent.getStringExtra("banner_image_url")
        tvCoverImg.text = intent.getStringExtra("cover_image_url")
        tvLanguage.text = intent.getStringExtra("languages")
//        tvDuration.text = intent.getStringExtra("movie_duration")
//        tvMovieName.text = intent.getStringExtra("movie_name")
//        tvRating.text = intent.getStringExtra("no_of_ratings")
//        tvReleaseDate.text = intent.getStringExtra("release_date")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Movie").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        empId: String,
        about_movie: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etAboutMovie = mDialogView.findViewById<EditText>(R.id.etAboutMovie)
        val etBannerImg = mDialogView.findViewById<EditText>(R.id.etBannerImgUrl)
        val etCoverImg = mDialogView.findViewById<EditText>(R.id.etCoverImgUrl)
        val etLanguage = mDialogView.findViewById<EditText>(R.id.etLanguages)
//        val etMovieDuration = mDialogView.findViewById<EditText>(R.id.etDuration)
//        val etMovieName = mDialogView.findViewById<EditText>(R.id.etMovieName)
//        val etRating = mDialogView.findViewById<EditText>(R.id.etRating)
//        val etReleaseDate = mDialogView.findViewById<EditText>(R.id.etReleaseDate)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etAboutMovie.setText(intent.getStringExtra("about_name").toString())
        etBannerImg.setText(intent.getStringExtra("banner_image_url").toString())
        etCoverImg.setText(intent.getStringExtra("cover_image_url").toString())
        etLanguage.setText(intent.getStringExtra("languages").toString())
//        etMovieDuration.setText(intent.getStringExtra("movie_duration").toString())
//        etMovieName.setText(intent.getStringExtra("movie_name").toString())
//        etRating.setText(intent.getStringExtra("no_of_ratings").toString())
//        etReleaseDate.setText(intent.getStringExtra("release_date").toString())

        mDialog.setTitle("Updating $about_movie Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etAboutMovie.text.toString(),
                etBannerImg.text.toString(),
                etCoverImg.text.toString(),
                etLanguage.text.toString(),

                )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvAboutMovie.text = etAboutMovie.text.toString()
            tvBannerImg.text = etBannerImg.text.toString()
            tvCoverImg.text = etCoverImg.text.toString()
            tvLanguage.text = etLanguage.text.toString()


            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        about_movie: String,
        banner_image_url: String,
        cover_image_url: String,
        languages: String

    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Movie").child(id)
        val empInfo = MovieModel(
            id,
            about_movie,
            banner_image_url,
            cover_image_url,
            languages
        )
        dbRef.setValue(empInfo)
    }

}