package mechanic.shop.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import mechanic.shop.entity.Employee;
import mechanic.shop.entity.Vehicle;

@Data
@NoArgsConstructor
public class MechanicShopEmployee {

  private Long employeeId;
  private String employeeFirstName;
  private String employeeLastName;
  private String employeeJobTitle;
  private String employeePhone;
  private Set<MechanicShopVehicle> vehicles = new HashSet<>();


  public MechanicShopEmployee(Employee employee) {
    employeeId = employee.getEmployeeId();
    employeeFirstName = employee.getEmployeeFirstName();
    employeeLastName = employee.getEmployeeLastName();
    employeeJobTitle = employee.getEmployeeJobTitle();
    employeePhone = employee.getEmployeePhone();
    
    for (Vehicle vehicle : employee.getVehicles()) {
      vehicles.add(new MechanicShopVehicle(vehicle));
    }
  }
}
