package com.also.car.rental.service;

import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;

import java.util.List;

public interface CarRentalService {

    int reserveCar(CarRentalReq carRentalReq);

    List<CarRentalView> listCars(CarQueryReq carQueryReq);
}
