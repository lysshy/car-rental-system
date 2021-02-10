package com.also.car.rental.service;

import com.also.car.rental.entity.Car;
import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.exception.CustomException;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerReserveTest {

    @Autowired
    private CarRentalService carRentalService;

    @MockBean
    private CarService carService;

    @MockBean
    private CarRentalRecordService carRentalRecordService;


    @Test
    public void should_throwException_when_carStockIsZero() {
        CarRentalReq carRentalReq = buildCarRentalReq();
        given(carService.selectUnRentalCars(carRentalReq)).willReturn(new ArrayList<>());
        Assert.assertThrows(CustomException.class,
                () -> carRentalService.reserveCar(carRentalReq));
    }

    @Test
    public void should_throwException_when_carIsSelectedByOtherCustomer() {
        CarRentalReq carRentalReq = buildCarRentalReq();
        Car bmw = new Car();
        bmw.setId(1);

        given(carService.selectUnRentalCars(carRentalReq)).willReturn(Collections.singletonList(bmw));
        given(carService.selectForUpdateNoWait(1)).willReturn(null);

        Assert.assertThrows(CustomException.class,
                () -> carRentalService.reserveCar(carRentalReq));
    }

    @Test
    public void should_reserveSuccess_when_carStockIsOk() {
        int carId = 1;
        CarRentalReq carRentalReq = buildCarRentalReq();
        Car bmw = new Car();
        bmw.setId(carId);
        bmw.setRentPrice(200);

        CarRentalRecord carRentalRecord = new CarRentalRecord();
        doAnswer(i -> {
            CarRentalRecord r = i.getArgument(0);
            r.setId(2);
            return null;
        }).when(carRentalRecordService).add(carRentalRecord);

        given(carService.selectUnRentalCars(carRentalReq)).willReturn(Collections.singletonList(bmw));
        given(carService.selectForUpdateNoWait(carId)).willReturn(bmw);

        Assert.assertEquals(2, carRentalService.reserveCar(carRentalReq));
        verify(carService, times(1)).modifyUpdateTime(carId);
    }

    private CarRentalReq buildCarRentalReq() {
        LocalDateTime now = LocalDateTime.now();
        CarRentalReq carRentalReq = new CarRentalReq();
        carRentalReq.setRentTime(now);
        carRentalReq.setReturnTime(now.plusDays(2));
        return carRentalReq;
    }

    @Test
    public void should_queryCarListFailed_when_dateTimeInvalid() {
        LocalDateTime now = LocalDateTime.now();
        CarQueryReq carQueryReq = new CarQueryReq();
        carQueryReq.setRentTime(now);
        carQueryReq.setReturnTime(now.plusDays(-1));

        Assert.assertThrows(CustomException.class,
                () -> carRentalService.listCars(carQueryReq));
    }

    @Test
    public void should_queryCarListSuccess_when_paramValid() {
        LocalDateTime now = LocalDateTime.now();
        CarQueryReq carQueryReq = new CarQueryReq();
        carQueryReq.setRentTime(now);
        carQueryReq.setReturnTime(now.plusDays(1));

        CarRentalView car1 = new CarRentalView();
        CarRentalView car2 = new CarRentalView();

        given(carService.selectCarRentalViews(carQueryReq)).willReturn(Arrays.asList(car1, car2));

        Assert.assertEquals(2, carService.selectCarRentalViews(carQueryReq).size());

    }

}
