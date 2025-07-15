package math.primenumbers;


import org.junit.jupiter.api.Test;

public class PrimeNumberTest {
  @Test
  public void testIsPrime() {
    assert PrimeNumber.isPrime(2);
    assert PrimeNumber.isPrime(3);
    assert PrimeNumber.isPrime(5);
    assert PrimeNumber.isPrime(7);
    assert PrimeNumber.isPrime(11);
    assert PrimeNumber.isPrime(13);
    assert PrimeNumber.isPrime(17);
    assert PrimeNumber.isPrime(19);
    assert PrimeNumber.isPrime(23);
    assert PrimeNumber.isPrime(29);

    assert !PrimeNumber.isPrime(1);
    assert !PrimeNumber.isPrime(4);
    assert !PrimeNumber.isPrime(6);
    assert !PrimeNumber.isPrime(8);
    assert !PrimeNumber.isPrime(9);
    assert !PrimeNumber.isPrime(10);
  }

  public void testCountPrimes() {
    assert PrimeNumber.countPrimes(10) == 4;
    assert PrimeNumber.countPrimes(20) == 8;
    assert PrimeNumber.countPrimes(30) == 10;
    assert PrimeNumber.countPrimes(100000) == 9592;
    assert PrimeNumber.countPrimes(1) == 0;
    assert PrimeNumber.countPrimes(0) == 0;
  }

  public void testEratosthenes() {
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(10), true) == 4;
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(20), true) == 8;
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(30), true) == 10;
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(100000), true)  == 9592;
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(1), true) == 0;
    assert PrimeNumber.countArray(PrimeNumber.eratosthenes(0), true) == 0;
  }

}
