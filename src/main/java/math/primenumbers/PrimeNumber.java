package math.primenumbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// PrimeNumber - a non instancable helper class made for working with primes
// The only reason this is not made instanceable, is because we don't have number chashing (eg. the array from the sieve could be cashed for further calls
public class PrimeNumber{

  // according to https://miller-rabin.appspot.com/ the lowest set of bases for 64 and 32 bit numbers
  public static final int[] SPRS_NUMBERS_FOR_64_BIT = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};
  public static final int[] SPRS_NUMBERS_FOR_32_BIT = {2, 7, 61};

  public static boolean isPrime ( long number ) {

    if ( number < 2 ){
      return false;
    }

    if ( number == 2 || number == 3 ){
      return true;
    }

    // checks if number is even
    // basically the same as number%2=0 but more optimized
    if ( (number & 1) == 0 ){
      return false;
    }

    long d = number - 1;
    int s = 0;

    // number-1=d*2^s
    while ( (d & 1) == 0 ) {
      d >>= 1;
      s++;
    }

    // deterministic bases for 32 and 64 bit numbers
    // the miller-rabin needs to run through these bases to ensure 100% accuracy 
    if ( number < Integer.MAX_VALUE ){
      for ( int a : PrimeNumber.SPRS_NUMBERS_FOR_32_BIT ) {
        if (a >= number){
          break;
        }
        if ( !millerRabinTest( a, d, s, number ) ){
          return false;
        }
      }
    } else {
      for ( int a : PrimeNumber.SPRS_NUMBERS_FOR_64_BIT ) {
        if (a >= number){
          break;
        }
        if ( !millerRabinTest( a, d, s, number ) ){
          return false;
        }
      }
    }
    return true;
  }

  public static boolean millerRabinTest( int a, long d, int itterations, long number ) {
    BigInteger bigN = BigInteger.valueOf( number );
    BigInteger bigA = BigInteger.valueOf( a );
    BigInteger x = bigA.modPow(BigInteger.valueOf( d ), bigN );

    if ( x.equals( BigInteger.valueOf( 1 ) ) || x.equals( bigN.subtract( BigInteger.valueOf( 1 ) ) ) ) {
      return true;
    }

    for ( int r = 1; r < itterations; r++ ) {
      x = x.modPow( BigInteger.valueOf( 2 ), bigN );

      if ( x.equals( bigN.subtract( BigInteger.valueOf( 1 ) ) ) ) {
        return true;
      }
    }

    return false;
  }

  // a very basic (and slow) isPrime checker
  // not used and only for runtime testing
  public static boolean isPrimeBasic( long number ) {
    if ( number == 0 || number == 1 ){
      return false;
    }

    if ( number == 2 ){
      return true;
    }

    if ( (number & 1) == 0 ){
      return false;
    }

    for ( int i = 3; i <= Math.sqrt(number); i+=2 ) {
      if ( number % i == 0 ) return false;
    }

    return true;
  }

  // sadly here I can only work with integer as arrays only support 2^63-8
  public static Boolean[] eratosthenes ( int upperBound ) throws IllegalArgumentException {

    if ( upperBound > Integer.MAX_VALUE-8 || upperBound < 1 ) {
      throw new IllegalArgumentException( "Please choose a number between 1 and " + (Integer.MAX_VALUE-9) + "." );
    }

    // I like to work with clearly named variable names and can't use P as one as it is upper case and voilates java naming convention
    // but isPrimeArray is P[] from the task c) and works as such
    Boolean[] isPrimeArray = new Boolean[upperBound+1];

    for ( int i = 0; i <= upperBound; i++ ) {
      isPrimeArray[i] = true;
    }

    if ( isPrimeArray.length>0 ) {
      isPrimeArray[0] = false;
    }

    if ( isPrimeArray.length>1 ) {
      isPrimeArray[1] = false;
    }
 
    // I ran out of time to make this multi-threadable so the comparison is going to be unfair
    for ( int i = 2; i <= upperBound; i++ ) {
      if ( isPrimeArray[i] && (long)(i) * (long)(i) <= upperBound ) {
        for ( long j = (long)(i) * (long)(i); j <= upperBound; j += i ) {
          isPrimeArray[(int)j] = false;
        }
      }
    }
 
    return isPrimeArray;
  }

  // this is very ineffective and should be done using a sieve
  public static Long countPrimes( long upperBound ){

    int availableCores = Runtime.getRuntime().availableProcessors();
    ExecutorService es = Executors.newFixedThreadPool(availableCores*4);
    ArrayList<Future<Integer>> futures = new ArrayList<>();
    long sum = 0;
    
    // runs callables in ranges of 1 mil.
    // means less runs for the que loop and longer thread times, reducing runtime
    final int batchSize = 1000000;

    for( long startIndex = 0; startIndex<upperBound; startIndex+=batchSize ){

      long endIndex = Math.min( startIndex+batchSize, upperBound );

      // optimized for multi threading
      // uses the methods from PrimeNumbers but packs it in it's own object
      // this makes sure sums are consitent and can be worked with in their own object
      Callable<Integer> millerCallable = new MillerRabinThread( startIndex, endIndex );
      futures.add( es.submit( millerCallable ) );

      // nobody should really do this in true multi threading but it is enough for this case
      if ( futures.size() > availableCores*4 ) {
        try {
          sum += futures.removeFirst().get();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    // calculates the rest of the results in the arraylist
    for ( Future<Integer> f : futures ) {
      try {
        sum += f.get();
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    es.shutdown();

    return sum;
  }

  public static <T extends Comparable<T>> int countArray ( T[] array, T toCompare ) {

    int sum = 0;

    for (T e : array){
      if(e.compareTo(toCompare)==0){
        sum++;
      }
    }

    return sum;
  }
}
