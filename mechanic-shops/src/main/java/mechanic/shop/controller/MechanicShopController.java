package mechanic.shop.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import mechanic.shop.controller.model.MechanicShopCustomer;
import mechanic.shop.controller.model.MechanicShopData;
import mechanic.shop.controller.model.MechanicShopEmployee;
import mechanic.shop.controller.model.MechanicShopVehicle;
import mechanic.shop.service.MechanicShopService;

@RestController
@RequestMapping("/mechanic_shop")
@Slf4j
public class MechanicShopController {

  @Autowired
  private MechanicShopService mechanicShopService;

  @PostMapping
  public MechanicShopData saveMechanicShop(@RequestBody MechanicShopData mechanicShopData) {
    log.info("Creating mechanicShopData{}", mechanicShopData);
    return mechanicShopService.saveMechanicShop(mechanicShopData);
  }
  
  @PostMapping("/{mechanicShopId}/employee")
  @ResponseStatus(HttpStatus.CREATED)
  public MechanicShopEmployee addEmployeeToMechanicShop(@PathVariable Long mechanicShopId,
      @RequestBody MechanicShopEmployee employee) {
    log.info("Adding employee to mechanic shop with ID: " + mechanicShopId);

    return mechanicShopService.saveEmployee(mechanicShopId, employee);
  }

  @PostMapping("/{mechanicShopId}/customer")
  @ResponseStatus(HttpStatus.CREATED)
  public MechanicShopCustomer addCustomerToMechanicShop(@PathVariable Long mechanicShopId,
      @RequestBody MechanicShopCustomer customer) {
    log.info("Adding customer to mechanic shop with ID: " + mechanicShopId);

    return mechanicShopService.saveCustomer(mechanicShopId, customer);
  }
  
  @PostMapping("/customer/{customerId}/vehicle")
  @ResponseStatus(HttpStatus.CREATED)
  public MechanicShopVehicle addVehicleToCustomer(@PathVariable Long customerId,
      @RequestBody MechanicShopVehicle vehicle) {
    log.info("Adding vehicle to customer with ID: " + customerId);

    return mechanicShopService.saveVehicleToCustomer(customerId, vehicle);
  }
  
  @PostMapping("/vehicle/{vehicleId}/employee/{employeeId}")
  public Map<String, String> addVehicleToEmployee(@PathVariable Long vehicleId,@PathVariable Long employeeId) {
    log.info("Adding vehicle to employee with ID: " + employeeId);
    mechanicShopService.saveVehicleToEmployee(vehicleId, employeeId);
    return Map.of("message",
        "assigned vehicle to employee with ID: " + employeeId + " successfully");
  }
  
  @GetMapping
  public List<MechanicShopData> retrieveAllMechanicShops() {
    log.info("Retrieve all mechanic shops called");
    return mechanicShopService.retrieveAllMechanicShops();
  }

  @GetMapping("/{mechanicShopId}")
  public MechanicShopData retrieveMechanicShopById(@PathVariable Long mechanicShopId) {
    log.info("Retrieving mechanic shop with ID={}", mechanicShopId);
    return mechanicShopService.retrieveMechanicShopById(mechanicShopId);
  }
  
  @GetMapping("/employee/{employeeId}")
  public MechanicShopEmployee retrieveEmployeeById(@PathVariable Long employeeId) {
    log.info("Retrieving employee shop with ID={}", employeeId);
    return mechanicShopService.retrieveEmployeeById(employeeId);
  }
  
  @GetMapping("/customer/{customerId}")
  public MechanicShopCustomer retrieveCustomerById(@PathVariable Long customerId) {
    log.info("Retrieving customer shop with ID={}", customerId);
    return mechanicShopService.retrieveCustomerById(customerId);
  }

  @PutMapping("/{mechanicShopId}")
  public MechanicShopData updateMechanicShop(@PathVariable Long mechanicShopId,
      @RequestBody MechanicShopData mechanicShopData) {
    mechanicShopData.setMechanicShopId(mechanicShopId);
    log.info("Updating mechanicShop {}", mechanicShopData);
    return mechanicShopService.saveMechanicShop(mechanicShopData);
  }
  
  @PutMapping("/{mechanicShopId}/employee/{employeeId}")
  public MechanicShopEmployee updateEmployee(@PathVariable Long mechanicShopId,@PathVariable Long employeeId,
      @RequestBody MechanicShopEmployee mechanicShopEmployee) {
    mechanicShopEmployee.setEmployeeId(employeeId);
    log.info("Updating employee {}", mechanicShopEmployee);
    return mechanicShopService.saveEmployee(mechanicShopId, mechanicShopEmployee);
  }
  
  @PutMapping("/{mechanicShopId}/customer/{customerId}")
  public MechanicShopCustomer updateCustomer(@PathVariable Long mechanicShopId,@PathVariable Long customerId,
      @RequestBody MechanicShopCustomer mechanicShopCustomer) {
    mechanicShopCustomer.setCustomerId(customerId);
    log.info("Updating customer {}", mechanicShopCustomer);
    return mechanicShopService.saveCustomer(mechanicShopId, mechanicShopCustomer);
  }
  
  @DeleteMapping("/mechanic_Shop")
  public void deleteAllMechanicShops() {
    log.info("Attemping to delete all mechanic shops");
    throw new UnsupportedOperationException("Deleting all mechanic shops is NOT allowed.");
  }
  
  @DeleteMapping("/mechanic_Shop/employee")
  public void deleteAllEmployees() {
    log.info("Attemping to delete all employees");
    throw new UnsupportedOperationException("Deleting all employees is NOT allowed.");
  }
  
  @DeleteMapping("/mechanic_Shop/customer")
  public void deleteAllCustomers() {
    log.info("Attemping to delete all customers");
    throw new UnsupportedOperationException("Deleting all customers is NOT allowed.");
  }

  @DeleteMapping("/{mechanicShopId}")
  public Map<String, String> deleteMechanicShopById(@PathVariable Long mechanicShopId) {
    log.info("Deleting mechanic shop with ID={}", mechanicShopId);

    mechanicShopService.deleteMechanicShopById(mechanicShopId);

    return Map.of("message",
        "Deletion of mechanic shop with ID=" + mechanicShopId + " was successful.");
  }
  
  @DeleteMapping("/employee/{employeeId}")
  public Map<String, String> deleteEmployeeById(@PathVariable Long employeeId) {
    log.info("Deleting employee with ID={}", employeeId);

    mechanicShopService.deleteEmployeeById(employeeId);

    return Map.of("message",
        "Deletion of employee with ID=" + employeeId + " was successful.");
  }
  
  @DeleteMapping("/customer/{customerId}")
  public Map<String, String> deleteCustomerById(@PathVariable Long customerId) {
    log.info("Deleting customer with ID={}", customerId);

    mechanicShopService.deleteCustomerById(customerId);

    return Map.of("message",
        "Deletion of customer with ID=" + customerId + " was successful.");
  }
}
