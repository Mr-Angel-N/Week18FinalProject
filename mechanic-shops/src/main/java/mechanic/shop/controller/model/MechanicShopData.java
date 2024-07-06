package mechanic.shop.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import mechanic.shop.entity.Customer;
import mechanic.shop.entity.Employee;
import mechanic.shop.entity.MechanicShop;

@Data
@NoArgsConstructor
public class MechanicShopData {

  private Long mechanicShopId;
  private String mechanicShopName;
  private String mechanicShopAddress;
  private String mechanicShopCity;
  private String mechanicShopState;
  private String mechanicShopZip;
  private String mechanicShopPhone;
  private Set<MechanicShopCustomer> customers = new HashSet<>();
  private Set<MechanicShopEmployee> employees = new HashSet<>();

  public MechanicShopData(MechanicShop mechanicShop) {
    mechanicShopId = mechanicShop.getMechanicShopId();
    mechanicShopName = mechanicShop.getMechanicShopName();
    mechanicShopAddress = mechanicShop.getMechanicShopAddress();
    mechanicShopCity = mechanicShop.getMechanicShopCity();
    mechanicShopState = mechanicShop.getMechanicShopState();
    mechanicShopZip = mechanicShop.getMechanicShopZip();
    mechanicShopPhone = mechanicShop.getMechanicShopPhone();

    for (Customer customer : mechanicShop.getCustomers()) {
      customers.add(new MechanicShopCustomer(customer));
    }
    for (Employee employee : mechanicShop.getEmployees()) {
      employees.add(new MechanicShopEmployee(employee));
    }
  }
}
