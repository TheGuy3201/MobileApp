package com.vinay.kotlinlangbasics

class ClassLab {
    fun Exercise1() {
        //Creating Variables
        val x: Int = 1
        var y: Int = 2
        y = x + y

        //Adding Control Structures (if, when, etc)
        if(x == y)
        {
            print("they are equal Yippee")
        }

        var ans: String = when(x){
            1 -> "One"
            2 -> "Two"
            else -> "Other"
        }

        for(i in x .. y)
        {
            print(i)
        }

        //Creating Functions (Calling done here)
        Ex1_3_Sum(x, y)

        //Initializing Classes
        val cindy = Person("Cindy", 36)

        //Collections
        var listW = listOf(1, 2, 3, 4, 5)
        val listZ = listOf("A", "B", "C", "D", "E")
    }

    //Creating the function
    fun Ex1_3_Sum(x: Int, y:Int): Int
    {
        return x + y
    }

    class Person(val name: String, val age: Int)
    {
        fun Ex1_5_Display()
        {
            val person = Person("John", 30)
            print("Name: ${person.name}, Age: ${person.age}")
        }
    }
}