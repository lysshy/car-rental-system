package com.also.car.rental.service.impl;

import com.also.car.rental.entity.Car;
import com.also.car.rental.mapper.CarMapper;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import com.also.car.rental.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Override
    public void updateCarStatus(int id, int status) {
        this.getBaseMapper().updateCarStatus(id, status);
    }

    @Override
    public Car selectForUpdateNoWait(int id) {
        return this.getBaseMapper().selectForUpdateNoWait(id);
    }

    @Override
    public Car selectForUpdateWait(int id) {
        return this.getBaseMapper().selectForUpdateWait(id);
    }

    @Override
    public List<Car> selectByStockId(int stockId) {
        return this.getBaseMapper().selectByStockId(stockId);
    }

    @Override
    public List<CarRentalView> selectCarRentalViews(CarQueryReq carQueryReq) {
        return this.getBaseMapper().selectCarRentalViews(carQueryReq);
    }

    @Override
    public List<Car> selectUnRentalCars(CarRentalReq carRentalReq) {
        return this.getBaseMapper().selectUnRentalCars(carRentalReq);
    }

    @Override
    public void modifyUpdateTime(int id) {
        Car update = new Car();
        update.setId(id);
        update.setUpdateTime(LocalDateTime.now());
        this.updateById(update);
    }
}
