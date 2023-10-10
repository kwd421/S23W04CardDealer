package kr.ac.kumoh.ce.s20160042.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kumoh.ce.s20160042.s23w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //R.drawable.c_10_of_clubs

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)   // 여기까지 하면 joker 나옴

        main.imgCard1.setImageResource(R.drawable.c_king_of_diamonds)   // 카드 덮어쓰기(뒤집기)
    }
}