package org.mytest.monix.walkthrough

object SimpleFunc
{
    def countPrimes(num: Int): Int = num match {
        case n if n < 1 => throw new Exception("nums should be > 0")
        case n if n <= 2 => 0 // there is no prime for < 2
        case n if n == 3 => 1 // 2 is the only prime < 3
        case _ =>
            // assume all primes initially, 0 -> num - 1
            val primeFlags = Array.fill[Int](num)(1) // 1 is used for sum
            primeFlags(0) = 0 // 0 is not a prime
            primeFlags(1) = 0 // 1 is not a prime
            val upTo = math.ceil(math.sqrt(num)).toInt
            for (i <- 2 to upTo) { // to includes right bound, until doesn't
                if (primeFlags(i) != 0)
                    for (j <- i*2 until num by i)
                        primeFlags(j) = 0
            }
            primeFlags.sum
    }
}
