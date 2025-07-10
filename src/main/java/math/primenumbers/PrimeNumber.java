package math.primenumbers;

import java.math.BigInteger;

public class PrimeNumber {;
  // according to https://miller-rabin.appspot.com/ the lowest set of bases for 64 bit numbers
  private static final int[] SPRS_NUMBERS_FOR_64_BIT = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};
  private static final int[] SPRS_NUMBERS_FOR_32_BIT = {2, 7, 61};

  public static boolean isPrime(Long n) {
    // checks if number is even
    // basically the same as n%2=0 but more optimized
    if ((n & 1) == 0){
      return false;
    }
    if (n < 2){
      return false;
    }
    if (n == 2 || n == 3){
      return true;
    }

    long d = n - 1;
    int s = 0;
    // n-1=d*2^s
    while ((d & 1) == 0) {
      d >>= 1;
      s++;
    }
    // deterministic bases for 32 and 64 bit numbers
    // the miller-rabin needs to run through these bases to ensure 100% accuracy 
    if(n<Integer.MAX_VALUE){
      for (int a : PrimeNumber.SPRS_NUMBERS_FOR_32_BIT) {
        if (a >= n){
          break;
        }
        if (!millerRabinTest(a, d, s, n)){
          return false;
        }
      }
    }else{
      for (int a : PrimeNumber.SPRS_NUMBERS_FOR_64_BIT) {
        if (a >= n){
          break;
        }
        if (!millerRabinTest(a, d, s, n)){
          return false;
        }
      }
    }
    return true;
  }

  public static boolean millerRabinTest(int a, long d, int s, long n) {
    BigInteger bigN = BigInteger.valueOf(n);
    BigInteger bigA = BigInteger.valueOf(a);
    BigInteger x = bigA.modPow(BigInteger.valueOf(d), bigN);

    if (x.equals(BigInteger.valueOf(1)) || x.equals(bigN.subtract(BigInteger.valueOf(1)))) {
      return true;
    }

    for (int r = 1; r < s; r++) {
      x = x.modPow(BigInteger.valueOf(2), bigN);

      if (x.equals(bigN.subtract(BigInteger.valueOf(1)))) {
        return true;
      }
    }

    return false;
  }

  // this is very ineffective and should be done with a sieve
  public static int sumOfPrimeNumbers(long upperBound){
    int sum = 0;
    for(long i = 0; i<=upperBound; i++){
      if(isPrime(i)){
        sum++;
        System.out.println(i);
      }
    }
    return sum;
  }

}
