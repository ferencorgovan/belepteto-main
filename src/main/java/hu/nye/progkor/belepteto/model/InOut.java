package hu.nye.progkor.belepteto.model;

import java.util.Objects;

public class InOut {
  private Long id;
  private String time;
  private String in;

  public InOut() {
  }

  public InOut(Long id, String time, String in) {
    this.id = id;
    this.time = time;
    this.in = in;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getIn() {
    return in;
  }

  public void setIn(String in) {
    this.in = in;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InOut inOut = (InOut) o;
    return Objects.equals(id, inOut.id) && Objects.equals(time, inOut.time)
            && Objects.equals(in, inOut.in);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, time, in);
  }

  @Override
  public String toString() {
    return "InOut{"
            + "id="
            + id
            + ", date="
            + time
            + ", in="
            + in
            + '}';
  }
}


