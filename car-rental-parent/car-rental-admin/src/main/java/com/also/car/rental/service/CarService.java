package com.also.car.rental.service;

import com.also.car.rental.entity.Car;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarService {

    void updateCarStatus(@Param("id") int id, @Param("status") int status);

    Car selectForUpdateNoWait(@Param("id") int id);

    Car selectForUpdateWait(@Param("id") int id);

    List<Car> selectByStockId(@Param("stockId") int stockId);

    List<CarRentalView> selectCarRentalViews(CarQueryReq carQueryReq);

    List<Car> selectUnRentalCars(CarRentalReq carRentalReq);

    void modifyUpdateTime(int id);
}
