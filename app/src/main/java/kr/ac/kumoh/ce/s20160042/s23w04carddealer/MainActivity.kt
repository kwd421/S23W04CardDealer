package kr.ac.kumoh.ce.s20160042.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20160042.s23w04carddealer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //R.drawable.c_10_of_clubs

        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)   // 여기까지 하면 뒷면 나옴

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]
        model.cards.observe(this, Observer {
            val res = IntArray(5)
            for(i in res.indices) {
                res[i] = resources.getIdentifier(getCardName(it[i]), "drawable", packageName)
            }
            main.imgCard1.setImageResource(res[0])
        })
        main.btnShuffle.setOnClickListener {
            model.shuffle() // observer가 알아서 바꿔주기 때문에 shuffle만 해주면 set자동
        }
        //main.imgCard1.setImageResource(R.drawable.c_king_of_diamonds)   // 카드 덮어쓰기(뒤집기)

    }

    private fun getCardName(c: Int): String {
        val shape = when(c / 13) {  // switch문 대신
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> "jack"
            11 -> "queen"
            12 -> "king"
            else -> "error"
        }
        return if (number in arrayOf("jack", "queen", "king"))
            "c_${number}_of_${shape}2"
        else
            "c_${number}_of_${shape}"

    }
}