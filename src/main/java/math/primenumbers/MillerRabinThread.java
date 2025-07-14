package math.primenumbers;

import java.util.concurrent.Callable;

// a callable class, made for faster computation of the miller rabin thread
public class MillerRabinThread implements Callable<Integer>{

  private long startIndex;
  private long endIndex;
  private int sum;

  public MillerRabinThread(long startIndex, long endIndex) {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
  }
  
  @Override public Integer call() throws Exception{
    for (long startIndex = this.startIndex; startIndex <= this.endIndex; startIndex++) {
      if(PrimeNumber.isPrime(startIndex)){
        this.sum++;
      }
    }
    return this.sum;
  }

}