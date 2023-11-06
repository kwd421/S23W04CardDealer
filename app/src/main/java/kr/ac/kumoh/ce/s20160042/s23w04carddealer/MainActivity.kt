package kr.ac.kumoh.ce.s20160042.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20160042.s23w04carddealer.databinding.ActivityMainBinding
import java.util.Arrays
import java.util.Collections
import kotlin.math.log

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
            main.imgCard2.setImageResource(res[1])
            main.imgCard3.setImageResource(res[2])
            main.imgCard4.setImageResource(res[3])
            main.imgCard5.setImageResource(res[4])

            main.rank.text = "${getCardRank(it[0], it[1], it[2], it[3], it[4])}"
        })
        main.btnShuffle.setOnClickListener {
            model.shuffle() // observer가 알아서 바꿔주기 때문에 shuffle만 해주면 set자동
        }
        //main.imgCard1.setImageResource(R.drawable.c_king_of_diamonds)   // 카드 덮어쓰기(뒤집기)

    }

    private fun isSpades(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "spades" && b == "spades" && c == "spades" &&
                d == "spades" && e == "spades")
    }
    private fun isClubs(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "clubs" && b == "clubs" && c == "clubs" &&
                d == "clubs" && e == "clubs")
    }
    private fun isDiamonds(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "diamonds" && b == "diamonds" && c == "diamonds" &&
                d == "diamonds" && e == "diamonds")
    }
    private fun isHearts(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "hearts" && b == "hearts" && c == "hearts" &&
                d == "hearts" && e == "hearts")
    }
    private fun isBlack(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "spades" || a == "clubs" &&
                b == "spades" || b == "clubs" &&
                c == "spades" || c == "clubs" &&
                d == "spades" || d == "clubs" &&
                e == "spades" || e == "clubs")
    }
    private fun isRed(a: String, b: String, c: String, d: String, e: String): Boolean {
        return (a == "hearts" || a == "diamonds" &&
                b == "hearts" || b == "diamonds" &&
                c == "hearts" || c == "diamonds" &&
                d == "hearts" || d == "diamonds" &&
                e == "hearts" || e == "diamonds")
    }

    private fun isMountain(a: Int, b: Int, c: Int, d: Int, e: Int): Boolean {
        return (a == 8 && b == 9 && c == 10 && d == 11 && e == 12)  // 9,10,J,Q,K
    }
    private fun isBackStraight(a: Int, b: Int, c: Int, d: Int, e: Int): Boolean {
        return (a == 0 && b == 1 && c == 2 && d == 3 && e == 4) // A,2,3,4,5
    }
    private fun isStraight(a: Int, b: Int, c: Int, d: Int, e: Int): Boolean {
        return if(e-d==1 && d-c==1 && c-b==1 && b-a==1) // 2,3,4,5,6~ 8,9,10,J,Q
            true
        else if(a == 0 && b == 1 && c == 2 && d == 3 && e == 12)    // K,A,2,3,4
            true
        else if(a == 0 && b == 1 && c == 2 && d == 11 && e == 12)   // Q,K,A,2,3
            true
        else if(a == 0 && b == 1 && c == 10 && d == 11 && e == 12)   // J,Q,K,A,2
            true
        else if(a == 0 && b == 9 && c == 10 && d == 11 && e == 12)   // 10,J,Q,K,A
            true
        else
            false
    }
//    private fun temp (a: Int, b: Int, c: Int, d: Int, e: Int): String {
//        val (aS, aN) = cardID(a)
//        val (bS, bN) = cardID(b)
//        val (cS, cN) = cardID(c)
//        val (dS, dN) = cardID(d)
//        val (eS, eN) = cardID(e)
//        //val num = intArrayOf(aN, bN, cN, dN, eN)
//
//
//        if(isRed(aS, bS, cS, dS, eS) && isStraight(aN, bN, cN, dN, eN) &&
//            isBlack(aS, bS, cS, dS, eS) && isStraight(aN, bN, cN, dN, eN))
//    }
    private fun getCardRank(a: Int, b: Int, c: Int, d: Int, e: Int): String {
        val r = arrayOf(a, b, c, d, e)
        Arrays.sort(r)  // 오름차순 정렬

        val (aS, aN) = cardID(r[0])
        val (bS, bN) = cardID(r[1])
        val (cS, cN) = cardID(r[2])
        val (dS, dN) = cardID(r[3])
        val (eS, eN) = cardID(r[4])


        //return "${cardID(r[0])}"

        return if() "Royal Straight Flush"
        else if() "Back Straight Flush"
        else if() "Straight Flush"
        else if() "Four Card"
        else if() "Full House"
        else if() "Flush"
        else if() "Mountain"
        else if() "Back Straight"
        else if() "Straight"
        else if() "Triple"
        else if() "Two Pair"
        else if() "One Pair"
        else "Top"

        //return "${ranks[0]} ${ranks[1]} ${ranks[2]} ${ranks[3]} ${ranks[4]}"

        }
    }

    private fun cardID(c: Int): Pair<String, Int> {
        val shape = when(c / 13) {  // switch문 대신
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        val number = when (c % 13) {
            in 0..12 -> c
            else -> -1
        }
        return Pair(shape, number)
    }

    private fun getCardName(c: Int): String {
        val shape = when(c / 13) {  // switch문 대신
            -1 -> ""
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }
        val number = when (c % 13) {
            -1 -> "backside"
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> "jack"
            11 -> "queen"
            12 -> "king"
            else -> "error"
        }
        return if (number in arrayOf("jack", "queen", "king"))
            "c_${number}_of_${shape}2"
        else if (c == -1)
            "c_backside"
        else
            "c_${number}_of_${shape}"

    }
