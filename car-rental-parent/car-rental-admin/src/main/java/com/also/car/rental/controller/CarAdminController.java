package com.also.car.rental.controller;

import com.also.car.rental.entity.base.BaseResult;
import com.also.car.rental.model.CarRentalRecordQueryReq;
import com.also.car.rental.model.CarRentalRecordView;
import com.also.car.rental.model.IdReq;
import com.also.car.rental.service.CarRentalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@Api(tags = "manager function interface")
public class CarAdminController {

    @Autowired
    private CarRentalRecordService carRentalRecordService;

    @PostMapping("/carRentalList")
    @ApiOperation("car rental record list")
    public BaseResult<List<CarRentalRecordView>> getCarRentalList(@RequestBody CarRentalRecordQueryReq carRentalRecordQueryReq) {
        return BaseResult.ok(carRentalRecordService.selectCarRentalRecordViews(carRentalRecordQueryReq));
    }

    @PostMapping("/takeCar")
    @ApiOperation("take the car to customer")
    public BaseResult<List<CarRentalRecordView>> takeCar(@RequestBody IdReq idReq) {
        carRentalRecordService.takeCar(idReq.getId());
        return BaseResult.ok();
    }

    @PostMapping("/returnCar")
    @ApiOperation("return the car")
    public BaseResult<List<CarRentalRecordView>> returnCar(@RequestBody IdReq idReq) {
        carRentalRecordService.returnCar(idReq.getId());
        return BaseResult.ok();
    }
}
