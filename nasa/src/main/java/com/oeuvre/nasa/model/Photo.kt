package com.oeuvre.nasa.model

class Photo(val title: String, val description: String) {



	fun test(){
		val num = 5
		val test1: String = when (num) {
			5 -> "It's my birthday"
			3 -> "Close"
			else -> "You wrong"
		}
		println(test1)

		val test2: String = when (num) {
			5 -> "Yes"
			3 -> "Close"
			else -> "Not Close"
		}

		println(test2)




		fun pabloTestFunction(input1: Int): String =
				when (input1){
					5 -> "It's my birthday"
					3 -> "Close"
					else -> "You wrong"
				}


		val test3 = pabloTestFunction(5)
		println(test3)


		val anonymousFunc: (String) -> Int = {
			valuePassed -> valuePassed.length
		}

		fun higherOrderFunc (stringVal: String, someFunc: (String) -> Int): String {
			return someFunc(stringVal).toString()
		}

		val result: String = higherOrderFunc("Blah", anonymousFunc)




		val anom3: (Int) -> String = { input ->
			val result1: String
			if (input == 0){
				result1 = "zero"
			} else {
				result1 = "other number"
			}
			result1
		}

		val isZero: String = anom3(0)
	}
}