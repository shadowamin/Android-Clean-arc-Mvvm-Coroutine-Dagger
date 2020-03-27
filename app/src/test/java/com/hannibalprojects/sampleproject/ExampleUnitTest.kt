package com.hannibalprojects.sampleproject

import org.junit.Test

import org.junit.Assert.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *git
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest(computeFoo: () -> String) {

    val memoizedFoo by lazy(computeFoo)
    fun fuky(x : Int) : Boolean{
        return true
    }

    fun myfunC(x: Int, y: Int, lom: (Int) -> Boolean) {
        if (x < y) lom(x)
        val list = ArrayList<String>()
        list.toArray()
    }

    @Test
    fun addition_isCorrect() {
        val numbers = mutableListOf("one", "two", "three")
        numbers.count()
        val sum: Int.(Int) -> Int = { other -> plus(other) }
        myfunC(3, 5,::fuky)
        var sd = ArrayList<List<Int>>()
        var ed = IntArray(3)
        val s = "fsdf"
        val bla: String.(String) -> String = { string1 -> plus(string1) }
        val zd: (String, String) -> String = String::plus
        val sg: Int.(Int) -> Int = { num -> plus(num) }
        val dsd = zd("sf", "ss")
        val inn = "bingo".bla("kjlkj")
        val ki: ArrayList<List<Int>>.(Int) -> String = { n -> "hjgjhjhj" }
        var hm = "wdfsfw"

        sd.ki(5)
        Int.MAX_VALUE
        fun isOdd(x: Int) = x % 2 != 0
        println(ed.filter(::isOdd))


        assertEquals(lengthOfLongestSubstring("azertyuikjhasdzxvgnhrtertfdhsjhghknbmoghj"), 17)
    }


    fun <T> listEqualsIgnoreOrder(list1: List<T>, list2: List<T>): Boolean {
        return HashSet(list1).equals(HashSet(list2))
    }



    fun lengthOfLongestSubstring(s: String): Int {
        var confirmedString = ""
        val stringBuilder = StringBuilder()

        for (i in 0 until s.length - 1) {

            if (stringBuilder.indexOf(s[i]) >= 0) {
                if (confirmedString.length < stringBuilder.length) {
                    confirmedString = stringBuilder.toString()
                }
                System.out.println(stringBuilder.toString() + " $i  " + stringBuilder.indexOf(s[i]))
                stringBuilder.delete(0, stringBuilder.indexOf(s[i]) + 1)
            }
            stringBuilder.append(s[i])
            val listStock = ArrayList<Char>()
            listStock.size
        }

        if (confirmedString.length < stringBuilder.length) {
            return stringBuilder.length
        } else {
            return confirmedString.length
        }

    }
}
