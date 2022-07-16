package com.update.companiesmarketcap.stock.marketrate.navigations

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.update.companiesmarketcap.databinding.FragmentSecond2Binding
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Second2Fragment : Fragment() {

    var BASE_URL = "https://ticker.finology.in"

    var retrofit: Retrofit? = null

    private var _binding: FragmentSecond2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val empty= mutableListOf<TestModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecond2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val apiInterface: ApiInterface? = getClient()?.create(ApiInterface::class.java)

        val call = apiInterface?.Payment()

        call?.enqueue(object : Callback<String?> {

            override fun onResponse(call: Call<String?>, response: Response<String?>) {


                if (response.body() != null) {

                    var parse: Document? = null
                    parse = Jsoup.parse(response.body())

                    val element: Element? = parse.getElementsByClass("row").get(2)

                    val row: Elements? = element?.getElementsByClass("col-12 col-md-4")

                    for (i in row?.indices!!) {

                        val card: Elements = row[i].getElementsByClass("card cardscreen")

                        for (c in card.indices) {

                            val card1: Elements = card[c].getElementsByClass("row no-gutters")


                            val imageElement: Element? = card1.select("img").first()
                            val absoluteUrl = imageElement!!.absUrl("src")



                            val testModel = TestModel(absoluteUrl)
                            empty.add(testModel)

                            val adapter = TestAdapter(empty)
                            binding.rv.adapter = adapter
                            binding.rv.layoutManager = LinearLayoutManager(requireActivity())

                        }


                        val mn : Elements? = row[i].getElementsByClass("col-6 col-md-4 mt-4 compess").tagName("small")

                        Log.e("TAG", "onResponse: ${mn?.text()}" )

                    }



                }


            }

            override fun onFailure(call: Call<String?>, t: Throwable) {

            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getClient(): Retrofit? {

        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}