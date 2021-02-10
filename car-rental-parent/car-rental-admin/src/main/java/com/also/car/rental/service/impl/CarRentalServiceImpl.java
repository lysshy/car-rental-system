package com.also.car.rental.service.impl;

import com.also.car.rental.emuns.BrandEnum;
import com.also.car.rental.emuns.CarRentalRecordStatus;
import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.Car;
import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.exception.CustomException;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import com.also.car.rental.service.CarRentalRecordService;
import com.also.car.rental.service.CarRentalService;
import com.also.car.rental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarRentalServiceImpl implements CarRentalService {

    private final CarService carService;

    private final CarRentalRecordService carRentalRecordService;

    @Autowired
    public CarRentalServiceImpl(CarService carService, CarRentalRecordService carRentalRecordService) {
        this.carService = carService;
        this.carRentalRecordService = carRentalRecordService;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int rentAndReturnCarId(CarRentalReq carRentalReq) {
//        int stockId = carRentalReq.getStockId();
//        CarStock carStock = carStockService.selectForUpdate(stockId);
//        if (carStock == null) {
//            throw new CustomException(ResultCode.CAR_STOCK_NOT_EXIST);
//        }
//        if (carStock.getStock() == 0) {
//            throw new CustomException(ResultCode.CAR_STOCK_ZERO);
//        }
//
//        List<Car> cars = carService.selectByStockId(stockId);
//        if (CollectionUtils.isEmpty(cars)) {
//            throw new CustomException(ResultCode.CAR_STOCK_ZERO);
//        }
//
//        Car selectedCar = null;
//        for (Car car : cars) {
//            Car updateCar = carService.selectForUpdate(car.getId());
//            if (updateCar == null) {
//                //已被其他人选中，直接跳过
//                continue;
//            }
//            selectedCar = updateCar;
//        }
//        if (selectedCar == null) {
//            throw new CustomException(ResultCode.CAR_ALREADY_SELECT);
//        }
//
//        carService.updateCarStatus(selectedCar.getId(), CarStatus.RENTED.getCode());
//        carStockService.decreaseStock(stockId);
//        return selectedCar.getId();
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int reserveCar(CarRentalReq carRentalReq) {
        List<Car> cars = carService.selectUnRentalCars(carRentalReq);
        if (CollectionUtils.isEmpty(cars)) {
            throw new CustomException(ResultCode.CAR_STOCK_ZERO);
        }
        Car selectedCar = null;
        try {
            for (Car car : cars) {
                Car updateCar = carService.selectForUpdateNoWait(car.getId());
                if (updateCar == null) {
                    //car is selected, next car
                    continue;
                }
                selectedCar = updateCar;
            }
            if (selectedCar == null) {
                throw new CustomException(ResultCode.CAR_ALREADY_SELECT);
            }
            int rentDays = (int)ChronoUnit.DAYS.between(carRentalReq.getRentTime(), carRentalReq.getReturnTime());
            int rentPrice = selectedCar.getRentPrice();
            CarRentalRecord carRentalRecord = new CarRentalRecord();
            carRentalRecord.setCarId(selectedCar.getId());
            carRentalRecord.setMobile(carRentalReq.getMobile());
            carRentalRecord.setRentTime(carRentalReq.getRentTime());
            carRentalRecord.setReturnTime(carRentalReq.getReturnTime());
            carRentalRecord.setStatus(CarRentalRecordStatus.RESERVE.getCode());
            carRentalRecord.setUserName(carRentalReq.getUserName());
            carRentalRecord.setTotalPrice(rentPrice * rentDays);
            carRentalRecord.setCreateTime(LocalDateTime.now());
            carRentalRecordService.add(carRentalRecord);

            return carRentalRecord.getId();
        } finally {
            //the selected car must update to release the lock
            if (selectedCar != null) {
                carService.modifyUpdateTime(selectedCar.getId());
            }
        }

    }

    @Override
    public List<CarRentalView> listCars(CarQueryReq carQueryReq) {
        if (carQueryReq.getRentTime().isAfter(carQueryReq.getReturnTime())) {
            throw new CustomException(ResultCode.DATETIME_INVALID);
        }
        List<CarRentalView> carRentalViews = carService.selectCarRentalViews(carQueryReq);
        if (CollectionUtils.isEmpty(carRentalViews)) {
            return new ArrayList<>();
        }
        carRentalViews.forEach(i -> i.setBrandName(BrandEnum.getByCode(i.getBrandType())));
        return carRentalViews;
    }
}
