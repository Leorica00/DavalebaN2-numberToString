package com.example.davaleban2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberText = findViewById<TextView>(R.id.numberTextView)
        val numberButton = findViewById<Button>(R.id.numberButton)

        numberButton.setOnClickListener {
            val inputText = findViewById<EditText?>(R.id.numberEditText).text.toString()
            numberText.text = numberToString(inputText)
        }
    }
    private fun numberToString(number: String?): String? {
        try {
            val numb = number?.toIntOrNull() ?: throw NumberFormatException()
            if (numb in 1..1000) {
                val map = mapOf(
                    1 to "ერთი",
                    2 to "ორი",
                    3 to "სამი",
                    4 to "ოთხი",
                    5 to "ხუთი",
                    6 to "ექვსი",
                    7 to "შვიდი",
                    8 to "რვა",
                    9 to "ცხრა",
                    10 to "ათი",
                    11 to "თერთმეტი",
                    12 to "თორმეტი",
                    13 to "ცამეტი",
                    14 to "თოთხმეტი",
                    15 to "თხუთმეტი",
                    16 to "თექვსმეტი",
                    17 to "ჩვიდმეტი",
                    18 to "თვრამეტი",
                    19 to "ცხრამეტი",
                    20 to "ოც",
                    40 to "ორმოც",
                    60 to "სამოც",
                    80 to "ოთხმოც",
                    100 to "ას"
                )


                // calculate how many hundreds does a number have and what is the reminder part other than hundreds of the number
                val hundredNumb: Int = numb.div(100)
                val hundredNumbReminder: Int = numb.rem(100)

                // calculate a dozen part of the hundredNumbReminder (20, 40, 60, 80) and ones part other than tens.
                val dozen: Int = (hundredNumbReminder / 20) * 20
                val dozenReminder: Int = hundredNumbReminder % 20

                // it should end with for example ოც-"დახუთი" if the number is not 20, 40, 60, 80 in which case it should end with ი - "ოც-ი"
                var dozenAddText = "და${map[dozenReminder]}"
                if (dozenReminder == 0) {
                    dozenAddText = "ი"
                }

                // calculating tens and ones part for more than a hundred numbers.
                // In this case if variable dozen is 0 then it should just return <20 part f.e. - 107 "ას-შვიდი", since there is no tens part here.
                val finalDozen: String = if (dozen == 0 && dozenReminder != 0) {
                    " ${map[dozenReminder]}"
                } else if (dozenReminder != 0 || dozen != 0){
                    " ${map[dozen]}$dozenAddText"
                }else {
                    "ი"
                }

                // calculating the whole number for more than 100 numbers. there are 3 different cases: case of 100, case of 200..700
                // and case of 800,900. in case of 100 it should return "ას" + finalDozen. in the second case we need to take last letter
                // from the ones part in hundreds like we need "სამ" instead of "სამი" when we write "სამ-ასი".  and the last case is 800
                // and 900, since we don't need to take last letter and write it fully as it is - "რვა-ასი".
                var hundredNumbAddText = ""
                if (hundredNumb != 0) {
                    if (hundredNumb == 1) {
                        hundredNumbAddText = "${map[100].toString()}$finalDozen"
                    } else if (hundredNumb < 8) {
                        hundredNumbAddText = "${
                            map[hundredNumb]!!.substring(
                                0,
                                map[hundredNumb]!!.length - 1
                            )
                        }${map[100]}${finalDozen}"
                    } else if (hundredNumb < 10) {
                        hundredNumbAddText = "${map[hundredNumb]}${map[100]}${finalDozen}"
                    }
                }

                // Here is when statement to print the final number according to different cases

                return when {
                    numb < 20 -> map[numb]
                    numb < 100 -> finalDozen
                    numb < 1000 -> hundredNumbAddText
                    else -> "ათასი"

                }
            }
            else return "რიცხვი საზღვრებს გარეთაა"
        }catch (e: NumberFormatException){
            return "გთხოვთ შეიყვანეთ მთელი რიცხვი"
        }
    }
}

