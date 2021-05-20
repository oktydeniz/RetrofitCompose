package com.example.retrofit_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofit_compose.model.CryptoModel
import com.example.retrofit_compose.ui.theme.RetrofitComposeTheme
import com.example.retrofit_compose.ui.theme.backGroundGrey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val cryptoModel = remember { mutableStateListOf<CryptoModel>() }
    val baseUrl = "https://api.nomics.com/v1/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    val call = retrofit.getData()
    call.enqueue(object : Callback<List<CryptoModel>> {
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    cryptoModel.addAll(it)
                }
            }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }

    })

    Scaffold(
        topBar = { AppBar() }
    ) {
        CryptoList(model = cryptoModel)
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        contentPadding = PaddingValues(5.dp, 2.dp, 0.dp, 2.dp),
        backgroundColor = Color.DarkGray,
        elevation = 5.dp,
    ) {
        Text(
            text = "Retrofit Compose",
            fontSize = 18.sp,
            color = Color.White,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun CryptoList(model: List<CryptoModel>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(model) { c ->
            CryptoRow(model = c)
        }

    }
}

@Composable
fun CryptoRow(model: CryptoModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backGroundGrey)
            .border(2.dp, color = Color.DarkGray)

    ) {
        Text(
            text = model.currency,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = model.price,
            color = Color.DarkGray,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(2.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitComposeTheme {

    }
}