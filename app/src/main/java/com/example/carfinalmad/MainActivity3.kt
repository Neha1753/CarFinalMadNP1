package com.example.carfinalmad


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.text.DecimalFormat

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val intent = intent
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val days= DataHolder.myData
        val img = findViewById<ImageView>(R.id.image)
        if(name.equals("Maruthi Wagon-R")) {
            img.setImageResource(R.drawable.ca1)
        }
        if(name.equals("Maruthi Swift(LXI)")) {
            img.setImageResource(R.drawable.ca2)
        }
        if(name.equals("Maruthi Swift(VXI)")) {
            img.setImageResource(R.drawable.ca3)
        }
        if(name.equals("Maruthi Brezza")) {
            img.setImageResource(R.drawable.ca4)
        }
        if(name.equals("Kia Carnival(SUV)")) {
            img.setImageResource(R.drawable.ca5)
        }
        if(name.equals("Toyota Fortuner(SUV)")) {
            img.setImageResource(R.drawable.ca6)
        }
        if(name.equals("Kia Seltos")) {
            img.setImageResource(R.drawable.ca7)
        }
        val button1=findViewById<ImageButton>(R.id.back)
        button1.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val text2=findViewById<TextView>(R.id.text2)
        val text3=findViewById<TextView>(R.id.text3)
        val text4=findViewById<TextView>(R.id.text4)
        val text5=findViewById<TextView>(R.id.text5)

        text2.text=text2.text.toString()+price
        text3.text=text3.text.toString()+days
        if (price != null && days != null) {

            val result = days.toInt() * price.toInt() * 0.28
            val decimalFormat = DecimalFormat("#.##")
            val formattedResult = decimalFormat.format(result)

            text4.text = text4.text.toString() + formattedResult
        }
        if (days != null) {
            if (price != null) {
                text5.text=text5.text.toString()+((days.toInt()*price.toInt()*0.28)+(days.toInt()*price.toInt()))
            }
        }


        }
        }

