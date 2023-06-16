package com.capstone.tastematch.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tastematch.R
import com.capstone.tastematch.response.ResponseItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textViewNamaMakanan: TextView
    private lateinit var textViewKalori: TextView
    private lateinit var textViewBahan: TextView
    private lateinit var textViewLangkahPembuatan: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.imageView)
        textViewNamaMakanan = findViewById(R.id.textViewNamaMakanan)
        textViewKalori = findViewById(R.id.textViewKalori)
        textViewBahan = findViewById(R.id.textViewBahan)
        textViewLangkahPembuatan = findViewById(R.id.textViewLangkahPembuatan)

        firestore = FirebaseFirestore.getInstance()

        val responseItem = intent.getParcelableExtra<ResponseItem>(EXTRA_RESPONSE_ITEM)
        responseItem?.let {
            displayData(it)
        } ?: run {
            Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
            finish()
        }

        val fabBack: FloatingActionButton = findViewById(R.id.fabBack)
        fabBack.setOnClickListener {
            onBackPressed()
        }

        val fabShare: FloatingActionButton = findViewById(R.id.fabShare)
        fabShare.setOnClickListener {
            responseItem?.let {
                shareData(it)
            }
        }
    }

    private fun displayData(responseItem: ResponseItem) {
        Picasso.get().load(responseItem.imageURL).into(imageView)
        textViewNamaMakanan.text = responseItem.menu
        textViewKalori.text = "Kalori: ${responseItem.kalori} Kkal // Serving"

        // Mengambil data dari Firestore berdasarkan menu
        val menu = responseItem.menu
        firestore.collection("menu")
            .whereEqualTo("menu", menu)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val bahanList = document.get("bahan") as List<String>
                    val langkahPembuatanList = document.get("langkahPembuatan") as List<String>

                    val bahanText = bahanList.joinToString("\n")
                    val langkahPembuatanText = langkahPembuatanList.joinToString("\n")

                    textViewBahan.text = bahanText
                    textViewLangkahPembuatan.text = langkahPembuatanText
                }
            }
    }

    private fun shareData(responseItem: ResponseItem) {
        val websiteURL = "https://bit.ly/TasteMatchApps"
        val shareText = "Coba resep makanan ini: ${responseItem.menu}\nLihat resep lengkap di: $websiteURL"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
    }

    companion object {
        const val EXTRA_RESPONSE_ITEM = "extra_response_item"
    }
}
