package com.nihongo.reviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var  newRecyclerview: RecyclerView
    private lateinit var  newArrayList: ArrayList<NihongoParticles>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var particles : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageId = arrayOf(
            R.drawable.ha,
            R.drawable.ga,
            R.drawable.he,
            R.drawable.de,
            R.drawable.ka,
            R.drawable.mo,
            R.drawable.ni,
            R.drawable.no,
            R.drawable.to,
            R.drawable.wo
        )
        heading = arrayOf(
            "ha",
            "ga",
            "he",
            "de",
            "ka",
            "mo",
            "ni",
            "no",
            "to",
            "wo"
        )

        newRecyclerview = findViewById(R.id.recyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)

        newArrayList = arrayListOf<NihongoParticles>()
        getUserdata()
    }

    private fun getUserdata() {

        for(i in imageId.indices){

            val particles = NihongoParticles(imageId[i], heading[i])
            newArrayList.add(particles)
        }
        var adapter = MyAdapter(newArrayList)
        newRecyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(this@MainActivity, "You Clicked item # $position", Toast.LENGTH_SHORT).show()
            }
        })

    }

}