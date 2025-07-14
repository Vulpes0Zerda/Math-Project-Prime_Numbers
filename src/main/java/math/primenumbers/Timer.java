package math.primenumbers;

import java.time.Duration;

public class Timer {

  long start;
  Duration duration;
  
  public Timer(){
    this.start = System.nanoTime();
  }

  public void start(){
    this.start = System.nanoTime();
  }

  public void stop(){
    this.duration = Duration.ofNanos(System.nanoTime() - this.start);
  }

  public Duration getDuration(){
    return duration;
  }

  public String getFormatedString(){
    if(duration.getSeconds()<8){
      return duration.toMillis()+" ms";
    }else{
      return (duration.toHours()==0?"":String.format("%02d", duration.toHours()) + "h ") + (duration.toMinutes()==0?"":(String.format("%02d", duration.toMinutes()%60) + "m ")) + String.format("%02d", duration.toSeconds()%60) + "s";
    }
  }
}
