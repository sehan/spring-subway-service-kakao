package subway.station.domain;

import java.util.Objects;

public class Station {

  private final Long id;
  private final String name;

  public Station(String name) {
    this(null, name);
  }

  public Station(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Station{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Station station = (Station) o;
    return id.equals(station.id) && name.equals(station.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
