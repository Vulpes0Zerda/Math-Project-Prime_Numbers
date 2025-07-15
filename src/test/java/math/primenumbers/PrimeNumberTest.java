package math.primenumbers;


import org.junit.jupiter.api.Test;
import math.primenumbers.PrimeNumberUtils.isPrime;
import math.primenumbers.PrimeNumberUtils.countPrimes;

public class PrimeNumberTest {
  @Test
  public void testIsPrime() {
    assert isPrime(2);
    assert isPrime(3);
    assert isPrime(5);
    assert isPrime(7);
    assert isPrime(11);
    assert isPrime(13);
    assert isPrime(17);
    assert isPrime(19);
    assert isPrime(23);
    assert isPrime(29);

    assert !isPrime(1);
    assert !isPrime(4);
    assert !isPrime(6);
    assert !isPrime(8);
    assert !isPrime(9);
    assert !isPrime(10);
  }

  public void testCountPrimes() {
    assert countPrimes(10) == 4;
    assert countPrimes(20) == 8;
    assert countPrimes(30) == 10;
    assert countPrimes(100000) == 9592;
    assert countPrimes(1) == 0;
    assert countPrimes(0) == 0;
  }

}
