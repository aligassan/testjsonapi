package com.example.ali.test

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://api.flightradar24.com/common/v1/airport.json?code=BGW"
        Download().execute(url)

    }
//    full class for json api
    inner class Download : AsyncTask<String,String,String>(){

        override fun onPreExecute() {
        }
//        for build connection
        override fun doInBackground(vararg p0: String?): String{

            try {

                val url = URL(p0[0])
                val urlConnect = url.openConnection() as HttpURLConnection
                urlConnect.connectTimeout = 700
                val inputStream = urlConnect.inputStream
                val dataJsonAsStr = covertStreamToString(urlConnect.inputStream)
                publishProgress(dataJsonAsStr)

                }   catch (e: Exception){

            }
            return ""
        }

//        for get items from json api
        override fun onProgressUpdate(vararg values: String?) {

            val json= JSONObject(values[0])
            val result = json.getJSONObject("result")
            val request =result.getJSONObject("request")
            val code = request.getString("code")
            codeText.setText(code)

        }

        override fun onPostExecute(result: String?) {

        }

    }

//    for connection api
    fun covertStreamToString (inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line:String
        var  allString:String=""
        try {
            do{
                line=bufferReader.readLine()
                if (line!=null)
                    allString+=line
            }while (line!=null)

            bufferReader.close()
        }catch (ex:java.lang.Exception){}

        return allString;
    }
}


