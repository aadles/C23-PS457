package com.capstone.tastematch.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
	val response: List<ResponseItem?>? = null
) : Parcelable

@Parcelize
@Entity(tableName = "menudb")
data class ResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id: String? = null,
	val menu: String? = null,
	val langkahPembuatan: List<String?>? = null,
	val kalori: Int? = null,
	val imageURL: String? = null,
	val bahan: List<String?>? = null
) : Parcelable
