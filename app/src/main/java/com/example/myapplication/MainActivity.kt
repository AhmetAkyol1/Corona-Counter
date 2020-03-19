package com.example.myapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {


    var koronas = ArrayList<Korona>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRates()

    }

    fun search(view:View){

        var searching=searchText.text.toString()

        searching=searching.toLowerCase()
        searching=searching.capitalize()

        for(i in 0 until koronas.size){
            if(koronas.get(i).country.equals(searching)){

                country.text="Country: ${koronas.get(i).country}"
                cases.text="Cases: ${koronas.get(i).cases}"
                todayCases.text="Today Cases: ${koronas.get(i).todayCases}"
                todayDeaths.text="Today Deaths: ${koronas.get(i).todayDeaths}"
                deaths.text="Deaths: ${koronas.get(i).deaths}"
                recovered.text="Recovered: ${koronas.get(i).recovered}"
                active.text="Active: ${koronas.get(i).active}"
                critical.text="Critical: ${koronas.get(i).critical}"
                warning.text=""
                return
            }
        }
        warning.text="Not Found"
    }
    fun getRates(){

        val downloadData = Download()
        try {
            val url="https://coronavirus-19-api.herokuapp.com/countries"
            downloadData.execute(url)

        }catch (e: Exception){
        }
    }
    inner class Download : AsyncTask<String,Void,String>(){

        override fun doInBackground(vararg url: String?): String {

            var result : String=""
            var connection = URL(url[0]).openConnection() as HttpsURLConnection
            try{
                connection.connect()
                result=connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return result
        }
        override fun onPostExecute(result: String?){
            try{
                val jsonArray = JSONArray(result)
                for (i in 0 until jsonArray.length()){
                    var item = jsonArray.getJSONObject(i)

                    var korona=Korona()

                    korona.country = item["country"].toString()
                    korona.cases = Integer.parseInt(item["cases"].toString())
                    korona.todayCases = Integer.parseInt(item["todayCases"].toString())
                    korona.deaths = Integer.parseInt(item["deaths"].toString())
                    korona.todayDeaths = Integer.parseInt(item["todayDeaths"].toString())
                    korona.recovered = Integer.parseInt(item["recovered"].toString())
                    korona.active = Integer.parseInt(item["active"].toString())
                    korona.critical = Integer.parseInt(item["critical"].toString())

                    koronas.add(korona)

                }



            }catch (e: Exception){
                println(e.localizedMessage)
            }


            super.onPostExecute(result)

        }
    }


}
