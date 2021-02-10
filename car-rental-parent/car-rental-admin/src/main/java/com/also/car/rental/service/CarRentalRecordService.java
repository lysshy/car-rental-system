package com.also.car.rental.service;

import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.model.CarRentalRecordQueryReq;
import com.also.car.rental.model.CarRentalRecordView;

import java.util.List;

public interface CarRentalRecordService {

    void add(CarRentalRecord carRentalRecord);

    List<CarRentalRecordView> selectCarRentalRecordViews(CarRentalRecordQueryReq carRentalRecordQueryReq);

    void takeCar(int id);

    void returnCar(int id);
}
