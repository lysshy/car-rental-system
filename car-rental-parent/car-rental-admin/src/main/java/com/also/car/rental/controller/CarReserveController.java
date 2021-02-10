package com.also.car.rental.controller;

import com.alibaba.fastjson.JSONObject;
import com.also.car.rental.entity.base.BaseResult;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import com.also.car.rental.service.CarRentalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@Api(tags = "Customer reserve car interface")
public class CarReserveController {

    @Autowired
    private CarRentalService carRentalService;

    @PostMapping("/car/list")
    @ApiOperation("Query useful car brand")
    public BaseResult<List<CarRentalView>> listCars(@RequestBody @Validated CarQueryReq carQueryReq) {
        return BaseResult.ok(carRentalService.listCars(carQueryReq));
    }

    @PostMapping("/car/reserve")
    @ApiOperation("Reserve a car")
    public BaseResult<Integer> reserveCar(@RequestBody @Validated CarRentalReq carRentalReq) {
        return BaseResult.ok(carRentalService.reserveCar(carRentalReq));
    }

    @GetMapping("currentUser")
    public JSONObject currentUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Xu Huang");
        jsonObject.put("userid", "00000002");
        jsonObject.put("email", "huangxulysshy@126.com");
        jsonObject.put("phone", "18098999428");
        return jsonObject;
    }

}
