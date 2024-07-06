package mechanic.shop.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import mechanic.shop.entity.Customer;
import mechanic.shop.entity.Vehicle;

@Data
@NoArgsConstructor
public class MechanicShopCustomer {

  private Long customerId;
  private String customerFirstName;
  private String customerLastName;
  private String customerPhone;
  private Set<MechanicShopVehicle> vehicles = new HashSet<>();

  public MechanicShopCustomer(Customer customer) {
    customerId = customer.getCustomerId();
    customerFirstName = customer.getCustomerFirstName();
    customerLastName = customer.getCustomerLastName();
    customerPhone = customer.getCustomerPhone();
    
    for (Vehicle vehicle : customer.getVehicles()) {
      vehicles.add(new MechanicShopVehicle(vehicle));
    }
  }
}
