package mechanic.shop.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mechanic.shop.entity.Vehicle;

@Data
@NoArgsConstructor
public class MechanicShopVehicle {
  
  private Long vehicleId;
  private String vehicleYear;
  private String vehicleMake;
  private String vehicleModel;
  
  public MechanicShopVehicle(Vehicle vehicle) {
    vehicleId = vehicle.getVehicleId();
    vehicleYear = vehicle.getVehicleYear();
    vehicleMake = vehicle.getVehicleMake();
    vehicleModel = vehicle.getVehicleModel();
  }
}
