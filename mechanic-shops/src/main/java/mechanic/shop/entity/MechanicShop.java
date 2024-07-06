package mechanic.shop.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MechanicShop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mechanicShopId;
  private String mechanicShopName;
  private String mechanicShopAddress;
  private String mechanicShopCity;
  private String mechanicShopState;
  private String mechanicShopZip;
  private String mechanicShopPhone;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToMany(mappedBy = "mechanicShop", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<Customer> customers = new HashSet<>();

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @OneToMany(mappedBy = "mechanicShop", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<Employee> employees = new HashSet<>();
}
