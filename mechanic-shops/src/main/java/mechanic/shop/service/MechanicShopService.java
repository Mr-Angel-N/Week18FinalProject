package mechanic.shop.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mechanic.shop.controller.model.MechanicShopCustomer;
import mechanic.shop.controller.model.MechanicShopData;
import mechanic.shop.controller.model.MechanicShopEmployee;
import mechanic.shop.controller.model.MechanicShopVehicle;
import mechanic.shop.dao.CustomerDao;
import mechanic.shop.dao.EmployeeDao;
import mechanic.shop.dao.MechanicShopDao;
import mechanic.shop.dao.VehicleDao;
import mechanic.shop.entity.Customer;
import mechanic.shop.entity.Employee;
import mechanic.shop.entity.MechanicShop;
import mechanic.shop.entity.Vehicle;

@Service
public class MechanicShopService {

  @Autowired
  private MechanicShopDao mechanicShopDao;

  @Autowired
  private EmployeeDao employeeDao;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private VehicleDao vehicleDao;

  @Transactional(readOnly = false)
  public MechanicShopData saveMechanicShop(MechanicShopData mechanicShopData) {
    Long mechanicShopId = mechanicShopData.getMechanicShopId();
    MechanicShop mechanicShop = findOrCreateMechanicShop(mechanicShopId);
    setFieldsInMechanicShop(mechanicShop, mechanicShopData);
    return new MechanicShopData(mechanicShopDao.save(mechanicShop));
  }

  private void setFieldsInMechanicShop(MechanicShop mechanicShop,
      MechanicShopData mechanicShopData) {
    mechanicShop.setMechanicShopName(mechanicShopData.getMechanicShopName());
    mechanicShop.setMechanicShopAddress(mechanicShopData.getMechanicShopAddress());
    mechanicShop.setMechanicShopCity(mechanicShopData.getMechanicShopCity());
    mechanicShop.setMechanicShopPhone(mechanicShopData.getMechanicShopPhone());
    mechanicShop.setMechanicShopState(mechanicShopData.getMechanicShopState());
    mechanicShop.setMechanicShopZip(mechanicShopData.getMechanicShopZip());

  }

  private MechanicShop findOrCreateMechanicShop(Long mechanicShopId) {
    MechanicShop mechanicShop;
    if (Objects.isNull(mechanicShopId)) {
      mechanicShop = new MechanicShop();
    } else {
      mechanicShop = findMechanicShopById(mechanicShopId);
    }
    return mechanicShop;
  }

  private MechanicShop findMechanicShopById(Long mechanicShopId) {
    return mechanicShopDao.findById(mechanicShopId).orElseThrow(() -> new NoSuchElementException(
        "MechanicShop with ID=" + mechanicShopId + " was not found."));
  }

  @Transactional(readOnly = true)
  public List<MechanicShopData> retrieveAllMechanicShops() {
    List<MechanicShop> mechanicShops = mechanicShopDao.findAll();
    List<MechanicShopData> response = new LinkedList<>();
    for (MechanicShop mechanicShop : mechanicShops) {
      response.add(new MechanicShopData(mechanicShop));
    }
    return response;
  }
  
  public MechanicShopData retrieveMechanicShopById(Long mechanicShopId) {
    MechanicShop mechanicShop = findMechanicShopById(mechanicShopId);
    return new MechanicShopData(mechanicShop);
  }
  
  public MechanicShopEmployee retrieveEmployeeById(Long employeeId) {
    Employee employee = findEmployeeById(employeeId, employeeId);
    return new MechanicShopEmployee(employee);
  }
  
  public MechanicShopCustomer retrieveCustomerById(Long customerId) {
    Customer customer = findCustomerById(customerId, customerId);
    return new MechanicShopCustomer(customer);
  }

  public void deleteMechanicShopById(Long mechanicShopId) {
    MechanicShop mechanicShop = findMechanicShopById(mechanicShopId);
    mechanicShopDao.delete(mechanicShop);
  }
  
  public void deleteEmployeeById(Long employeeId) {
    Employee employee = findEmployeeById(employeeId);
    employeeDao.delete(employee);
  }
  
  private Employee findEmployeeById(Long employeeId) {
    return employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException(
        "Employee with ID=" + employeeId + " was not found."));
  }

  public void deleteCustomerById(Long customerId) {
    Customer customer = findCustomerById(customerId);
    customerDao.delete(customer);
  }

  private Customer findCustomerById(Long customerId) {
    return customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException(
        "Customer with ID=" + customerId + " was not found."));
  }

  @Transactional(readOnly = false)
  public MechanicShopEmployee saveEmployee(Long mechanicShopId,
      MechanicShopEmployee mechanicShopEmployee) {
    MechanicShop mechanicShop = findMechanicShopById(mechanicShopId);
    Employee employee = findOrCreateEmployee(mechanicShopEmployee.getEmployeeId(), mechanicShopId);
    copyEmployeeFields(employee, mechanicShopEmployee);
    employee.setMechanicShop(mechanicShop);
    mechanicShop.getEmployees().add(employee);
    employee = employeeDao.save(employee);
    return convertToMechanicShopEmployee(employee);
  }

  private Employee findEmployeeById(Long mechanicShopId, Long employeeId) {
    Employee employee = employeeDao.findById(employeeId)
        .orElseThrow(() -> new NoSuchElementException("Employee not found"));
    if (!employee.getMechanicShop().getMechanicShopId().equals(mechanicShopId)) {
      throw new IllegalArgumentException("Employee does not belong to the specified mechanic shop");
    }
    return employee;
  }

  private Employee findOrCreateEmployee(Long employeeId, Long mechanicShopId) {
    if (employeeId == null) {
      return new Employee();
    } else {
      return findEmployeeById(mechanicShopId, employeeId);
    }
  }

  private void copyEmployeeFields(Employee employee, MechanicShopEmployee mechanicShopEmployee) {
    employee.setEmployeeId(mechanicShopEmployee.getEmployeeId());
    employee.setEmployeeFirstName(mechanicShopEmployee.getEmployeeFirstName());
    employee.setEmployeeLastName(mechanicShopEmployee.getEmployeeLastName());
    employee.setEmployeeJobTitle(mechanicShopEmployee.getEmployeeJobTitle());
    employee.setEmployeePhone(mechanicShopEmployee.getEmployeePhone());

  }

  private MechanicShopEmployee convertToMechanicShopEmployee(Employee employee) {
    MechanicShopEmployee mechanicShopEmployee = new MechanicShopEmployee();
    mechanicShopEmployee.setEmployeeId(employee.getEmployeeId());
    mechanicShopEmployee.setEmployeeFirstName(employee.getEmployeeFirstName());
    mechanicShopEmployee.setEmployeeLastName(employee.getEmployeeLastName());
    mechanicShopEmployee.setEmployeeJobTitle(employee.getEmployeeJobTitle());
    mechanicShopEmployee.setEmployeePhone(employee.getEmployeePhone());
    return mechanicShopEmployee;
  }

  @Transactional(readOnly = false)
  public MechanicShopCustomer saveCustomer(Long mechanicShopId,
      MechanicShopCustomer mechanicShopCustomer) {
    MechanicShop mechanicShop = findMechanicShopById(mechanicShopId);
    Customer customer = findOrCreateCustomer(mechanicShopCustomer.getCustomerId(), mechanicShopId);
    copyCustomerFields(customer, mechanicShopCustomer);
    customer.setMechanicShop(mechanicShop);
    mechanicShop.getCustomers().add(customer);
    customer = customerDao.save(customer);
    return convertToMechanicShopCustomer(customer);
  }

  private Customer findCustomerById(Long mechanicShopId, Long customerId) {
    Customer customer = customerDao.findById(customerId)
        .orElseThrow(() -> new NoSuchElementException("Customer not found"));
    if (!customer.getMechanicShop().getMechanicShopId().equals(mechanicShopId)) {
      throw new IllegalArgumentException("Customer does not belong to the specified mechanic shop");
    }
    return customer;
  }

  private Customer findOrCreateCustomer(Long customerId, Long mechanicShopId) {
    if (customerId == null) {
      return new Customer();
    } else {
      return findCustomerById(mechanicShopId, customerId);
    }
  }

  private void copyCustomerFields(Customer customer, MechanicShopCustomer mechanicShopCustomer) {
    customer.setCustomerId(mechanicShopCustomer.getCustomerId());
    customer.setCustomerFirstName(mechanicShopCustomer.getCustomerFirstName());
    customer.setCustomerLastName(mechanicShopCustomer.getCustomerLastName());
    customer.setCustomerPhone(mechanicShopCustomer.getCustomerPhone());
  }

  private MechanicShopCustomer convertToMechanicShopCustomer(Customer customer) {
    MechanicShopCustomer mechanicShopCustomer = new MechanicShopCustomer();
    mechanicShopCustomer.setCustomerId(customer.getCustomerId());
    mechanicShopCustomer.setCustomerFirstName(customer.getCustomerFirstName());
    mechanicShopCustomer.setCustomerLastName(customer.getCustomerLastName());
    mechanicShopCustomer.setCustomerPhone(customer.getCustomerPhone());
    return mechanicShopCustomer;
  }
  
  @Transactional(readOnly = false)
  public void saveVehicleToEmployee(Long vehicleId,
      Long employeeId) {
    Employee employee = findEmployeeById(employeeId);
    Vehicle vehicle = findVehicleById(vehicleId);
    vehicle.getEmployees().add(employee);
    employee.getVehicles().add(vehicle);
    //vehicle = vehicleDao.save(vehicle);
  }
  
  private Vehicle findVehicleById(Long vehicleId) {
      return vehicleDao.findById(vehicleId)
          .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));
    }

  @Transactional(readOnly = false)
  public MechanicShopVehicle saveVehicleToCustomer(Long customerId,
      MechanicShopVehicle mechanicShopVehicle) {
    Customer customer = findCustomerById(customerId);
    Vehicle vehicle = findOrCreateVehicle(mechanicShopVehicle.getVehicleId(), customerId);
    copyVehicleFields(vehicle, mechanicShopVehicle);
    vehicle.setCustomer(customer);
    customer.getVehicles().add(vehicle);
    vehicle = vehicleDao.save(vehicle);
    return convertToCustomerVehicle(vehicle);
  }

  private Vehicle findVehicleById(Long customerId, Long vehicleId) {
    Vehicle vehicle = vehicleDao.findById(vehicleId)
        .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));
    if (!vehicle.getCustomer().getCustomerId().equals(customerId)) {
      throw new IllegalArgumentException("Vehicle does not belong to the specified customer");
    }
    return vehicle;
  }

  private Vehicle findOrCreateVehicle(Long vehicleId, Long customerId) {
    if (vehicleId == null) {
      return new Vehicle();
    } else {
      return findVehicleById(customerId, vehicleId);
    }
  }

  private void copyVehicleFields(Vehicle vehicle, MechanicShopVehicle mechanicShopVehicle) {
    vehicle.setVehicleId(mechanicShopVehicle.getVehicleId());
    vehicle.setVehicleYear(mechanicShopVehicle.getVehicleYear());
    vehicle.setVehicleMake(mechanicShopVehicle.getVehicleMake());
    vehicle.setVehicleModel(mechanicShopVehicle.getVehicleModel());
  }

  private MechanicShopVehicle convertToCustomerVehicle(Vehicle vehicle) {
    MechanicShopVehicle mechanicShopVehicle = new MechanicShopVehicle();
    mechanicShopVehicle.setVehicleId(vehicle.getVehicleId());
    mechanicShopVehicle.setVehicleYear(vehicle.getVehicleYear());
    mechanicShopVehicle.setVehicleMake(vehicle.getVehicleMake());
    mechanicShopVehicle.setVehicleModel(vehicle.getVehicleModel());
    return mechanicShopVehicle;
  }
}
