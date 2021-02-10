package com.also.car.rental.service.impl;

import com.also.car.rental.emuns.BrandEnum;
import com.also.car.rental.emuns.CarRentalRecordStatus;
import com.also.car.rental.emuns.CarStatus;
import com.also.car.rental.emuns.ResultCode;
import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.exception.CustomException;
import com.also.car.rental.mapper.CarRentalRecordMapper;
import com.also.car.rental.model.CarRentalRecordQueryReq;
import com.also.car.rental.model.CarRentalRecordView;
import com.also.car.rental.service.CarRentalRecordService;
import com.also.car.rental.service.CarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarRentalRecordServiceImpl implements CarRentalRecordService {

    private final CarService carService;
    private final CarRentalRecordMapper carRentalRecordMapper;

    @Autowired
    public CarRentalRecordServiceImpl(CarService carService, CarRentalRecordMapper carRentalRecordMapper) {
        this.carService = carService;
        this.carRentalRecordMapper = carRentalRecordMapper;
    }

    @Override
    public void add(CarRentalRecord carRentalRecord) {
        carRentalRecordMapper.insert(carRentalRecord);
    }

    public List<CarRentalRecordView> selectCarRentalRecordViews(CarRentalRecordQueryReq carRentalRecordQueryReq) {
        QueryWrapper<CarRentalRecordView> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(carRentalRecordQueryReq.getBrandType() != null, "sr.brand_type", carRentalRecordQueryReq.getBrandType())
                .ge(carRentalRecordQueryReq.getRentTime() != null, "rr.rent_time", carRentalRecordQueryReq.getRentTime())
                .le(carRentalRecordQueryReq.getReturnTime() != null, "rr.return_time", carRentalRecordQueryReq.getReturnTime())
                .orderByAsc("rr.rent_time");
        List<CarRentalRecordView> carRentalRecordViews = carRentalRecordMapper.selectCarRentalRecordViews(queryWrapper);
        if (!CollectionUtils.isEmpty(carRentalRecordViews)) {
            carRentalRecordViews.forEach(i -> i.setBrandName(BrandEnum.getByCode(i.getBrandType())));
        }
        return carRentalRecordViews;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void takeCar(int id) {
        CarRentalRecord carRentalRecord = carRentalRecordMapper.selectById(id);
        if (carRentalRecord == null) {
            throw new CustomException(ResultCode.RECORD_NOT_EXIST);
        }

        if (carRentalRecord.getStatus() != CarRentalRecordStatus.RESERVE.getCode()) {
            throw new CustomException(ResultCode.CAR_ALREADY_TAKE);
        }

        LocalDateTime now = LocalDateTime.now();
        if (carRentalRecord.getRentTime().isAfter(now)) {
            throw new CustomException(ResultCode.TAKE_CAR_EARLY);
        }

        carService.updateCarStatus(carRentalRecord.getCarId(), CarStatus.OUT_STOCK.getCode());
        carRentalRecord.setStatus(CarRentalRecordStatus.USING_CAR.getCode());
        carRentalRecordMapper.updateById(carRentalRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnCar(int id) {
        CarRentalRecord carRentalRecord = carRentalRecordMapper.selectById(id);
        if (carRentalRecord == null) {
            throw new CustomException(ResultCode.RECORD_NOT_EXIST);
        }

        if (carRentalRecord.getStatus() != CarRentalRecordStatus.USING_CAR.getCode()) {
            throw new CustomException(ResultCode.CAR_ALREADY_RETURN);
        }

        carService.updateCarStatus(carRentalRecord.getCarId(), CarStatus.IN_STOCK.getCode());
        carRentalRecord.setStatus(CarRentalRecordStatus.FINISHED.getCode());
        carRentalRecord.setActualReturnTime(LocalDateTime.now());
        carRentalRecordMapper.updateById(carRentalRecord);
    }
}
