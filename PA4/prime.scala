  var _list: List[Int] = List();
  
  /**
   * Check if a given number is prime or not.
   */
  def isprime(div: Int, num:Int)
  {
    //if the div is greater or equal than half of given number, then it is prime.
    if(div >= num/2)
    { 
      //we shouldn't do this, because the List is immutable.
      //the correct way should be ListBuffer. But this assignment doesn't allow other object. 
      _list = _list ::: List(num);
    }
    else if(num % div != 0) 
    {//if num can't be divided by the div, we increase the div by one, and recurring. 
      isprime(div+1, num);
    }
    //else it is not a prime, we quite from this call
  }
  
  /**
  * find prime number between n and m. This include n and m
  */
  def findprime(n: Int, m: Int)
  {//assume 1<n<m  
    if(n < m)
    {
        if(n <= 2)
        {//get rid of negative value, 0 and 1
            findprime(2, m);
        }
        else
        {
          //check if n is prime
          isprime(2, n);
          //recurring call to check the next number of n
          findprime(n+1, m);
        }
    }
      else if(n == m)
      {//when reach m, we check if it is prime. then quite the recurring.
        if(n > 0) isprime(2, n);
      }
  }


/**
* find the primes number between start and end value. 
* Note: this include the start and end. 
*/
def primesBetween(start: Int, end: Int)
{
  findprime(start, end)
  print("primesbetween("+start+", "+end+") = ")
  println(_list)


}

//println("Enter values for n and m such that 0 < n < m:");
//val start = scala.io.StdIn.readInt();
//val end = scala.io.StdIn.readInt();
//primesBetween(start, end);