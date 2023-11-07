package kr.ac.kumoh.ce.s20160042.s23w04carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20160042.s23w04carddealer.databinding.ActivityMainBinding
import java.util.Arrays

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

    private fun shapeID(a: Array<String>, b:String): Boolean {
        var count = 0
        for(i in 0..4)
            if(a[i] == b)
                count++
        return count==4
    }
    private fun isClubs(a: Array<String>): Boolean {
        return shapeID(a, "clubs")
    }
    private fun isSpades(a: Array<String>): Boolean {
        return shapeID(a, "spades")
    }
    private fun isDiamonds(a: Array<String>): Boolean {
        return shapeID(a, "diamonds")
    }
    private fun isHearts(a: Array<String>): Boolean {
        return shapeID(a, "hearts")
    }
    private fun isBlack(a: Array<String>): Boolean {
        var count = 0
        for(i in 0..4)
            if(a[i] == "spades" || a[i] == "clubs")
                count++
        return count==4
    }
    private fun isRed(a: Array<String>): Boolean {
        var count = 0
        for(i in 0..4)
            if(a[i] == "hearts" || a[i] == "diamonds")
                count++
        return count==4
    }
    private fun isMountain(a: Array<Int>): Boolean {
        return (a[0] == 8 && a[1] == 9 && a[2] == 10 && a[3] == 11 && a[4] == 12)  // 9,10,J,Q,K
    }
    private fun isBackStraight(a: Array<Int>): Boolean {
        return (a[0] == 0 && a[1] == 1 && a[2] == 2 && a[3] == 3 && a[4] == 4) // A,2,3,4,5
    }
    private fun isStraight(a: Array<Int>): Boolean {
        return if(a[4]-a[3]==1 && a[3]-a[2]==1 && a[2]-a[1]==1 && a[1]-a[0]==1) // 2,3,4,5,6~ 8,9,10,J,Q
            true
        else if(a[0] == 0 && a[1] == 1 && a[2] == 2 && a[3] == 3 && a[4] == 12)    // K,A,2,3,4
            true
        else if(a[0] == 0 && a[1] == 1 && a[2] == 2 && a[3] == 11 && a[4] == 12)   // Q,K,A,2,3
            true
        else if(a[0] == 0 && a[1] == 1 && a[2] == 10 && a[3] == 11 && a[4] == 12)   // J,Q,K,A,2
            true
        else if(a[0] == 0 && a[1] == 9 && a[2] == 10 && a[3] == 11 && a[4] == 12)   // 10,J,Q,K,A
            true
        else
            false
    }
    private fun isFourCard(a: Array<Int>): Boolean {
        //TODO: 11122일떄도 fourcard됨
        return Counter(a) == "Four Card"
    }
    private fun isTriple(a: Array<Int>): Boolean {
        //TODO: 11223일때도 triple나옴
        return Counter(a) == "Triple"
    }
    private fun isTwoPair(a: Array<Int>): Boolean {
        //TODO: triple과 차이점
        return Counter(a) == "Two Pair"
    }
    private fun isOnePair(a: Array<Int>): Boolean {
        return Counter(a) == "One Pair"
    }
//    private fun isTop(a: Array<Int>):Boolean {
//        return pairCounter(a) == "Top"
//    }
    private fun Counter(a: Array<Int>): String{
        var count = IntArray(5){0}
        var pairCount = 0

        for (i in 0..4)
        {
            for(j in 0..4)
            {
                if(j == i) continue
                if(a[i] == a[j])
                    count[i]++
            }
        }
        for(i in 0..4)
            if(count[i] == 2)
                pairCount++ // pairCount

        return if(count.contains(4))
            return "Four Card"
        else if(count.contains(3) && count.contains(2)) // FullHouse!!
            return "Full House"
        else if(count.contains(3) && !count.contains(2)) // Triple!!
            return "Triple"
        else if(pairCount == 4)  // abbcc, aabbc, aabcc
            return "Two Pair"
        else if(pairCount == 2)  // One Pair,,,
            return "One Pair"
        else
            return "Top"
    }

    private fun getCardRank(a: Int, b: Int, c: Int, d: Int, e: Int): String {
        val r = arrayOf(a, b, c, d, e)
        Arrays.sort(r)  // 오름차순 정렬

        val (aS, aN) = cardID(r[0])
        val (bS, bN) = cardID(r[1])
        val (cS, cN) = cardID(r[2])
        val (dS, dN) = cardID(r[3])
        val (eS, eN) = cardID(r[4])

        val shapes = arrayOf(aS, bS, cS, dS, eS)
        val numbers = arrayOf(aN, bN, cN, dN, eN)

        //return "${cardID(r[0])}"

        return if(isBlack(shapes)&&isMountain(numbers)||
            isRed(shapes)&&isMountain(numbers)
            "Royal Straight Flush"
        else if(isBlack(shapes)&&isBackStraight(numbers)||
            isRed(shapes)&&isBackStraight(numbers)
            "Back Straight Flush"
        else if(isBlack(shapes)&&isStraight(numbers)||
            isRed(shapes)&&isStraight(numbers))
            "Straight Flush"
        else if(isFourCard(numbers))
            "Four Card"
        else if()
            "Full House"
        else if(isHearts(shapes)||isDiamonds(shapes)||isClubs(shapes)||isSpades(shapes))
            "Flush"
        else if(isMountain(numbers))
            "Mountain"
        else if(isBackStraight(numbers))
            "Back Straight"
        else if(isStraight(numbers))
            "Straight"
        else if(isTriple(numbers))
            "Triple"
        else if(isTwoPair(numbers))
            "Two Pair"
        else if(isOnePair(numbers))
            "One Pair"
        else
            "Top"

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
