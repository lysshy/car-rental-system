package com.also.car.rental.service;

import com.also.car.rental.emuns.CarRentalRecordStatus;
import com.also.car.rental.emuns.CarStatus;
import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.exception.CustomException;
import com.also.car.rental.mapper.CarRentalRecordMapper;
import com.also.car.rental.model.CarRentalRecordQueryReq;
import com.also.car.rental.service.impl.CarRentalRecordServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffManageReserveOrderTest {

    private CarRentalRecordServiceImpl carRentalRecordService;

    @MockBean
    private CarService carService;

    @MockBean
    private CarRentalRecordMapper carRentalRecordMapper;

    @Before
    public void before() {
        carRentalRecordService = new CarRentalRecordServiceImpl(carService, carRentalRecordMapper);
    }

    @Test
    public void should_listRecordSuccess_when_paramIsOk() {
        CarRentalRecordQueryReq carRentalRecordQueryReq = new CarRentalRecordQueryReq();
        carRentalRecordService.selectCarRentalRecordViews(carRentalRecordQueryReq);

        verify(carRentalRecordMapper, times(1)).selectCarRentalRecordViews(any());
    }

    @Test
    public void should_takeCarSuccess_when_paramIsOk() {
        int recordId = 1;
        int carId = 1;
        CarRentalRecord carRentalRecord = new CarRentalRecord();
        carRentalRecord.setStatus(CarRentalRecordStatus.RESERVE.getCode());
        carRentalRecord.setCarId(carId);
        given(carRentalRecordMapper.selectById(recordId)).willReturn(carRentalRecord);
        carRentalRecordService.takeCar(recordId);

        verify(carService, times(1)).updateCarStatus(carId, CarStatus.OUT_STOCK.getCode());
        verify(carRentalRecordMapper, times(1)).updateById(carRentalRecord);
    }

    @Test
    public void should_takeCarFail_when_statusInvalid() {
        int recordId = 1;
        int carId = 1;
        CarRentalRecord carRentalRecord = new CarRentalRecord();
        carRentalRecord.setStatus(CarRentalRecordStatus.USING_CAR.getCode());
        carRentalRecord.setCarId(carId);
        given(carRentalRecordMapper.selectById(recordId)).willReturn(carRentalRecord);

        Assert.assertThrows(CustomException.class, () -> carRentalRecordService.takeCar(recordId));
    }

    @Test
    public void should_takeCarFail_when_recordNotExist() {
        int recordId = 1;
        given(carRentalRecordMapper.selectById(recordId)).willReturn(null);

        Assert.assertThrows(CustomException.class, () -> carRentalRecordService.takeCar(recordId));
    }

    @Test
    public void should_returnCarSuccess_when_paramIsOk() {
        int recordId = 1;
        int carId = 1;
        CarRentalRecord carRentalRecord = new CarRentalRecord();
        carRentalRecord.setStatus(CarRentalRecordStatus.USING_CAR.getCode());
        carRentalRecord.setCarId(carId);
        given(carRentalRecordMapper.selectById(recordId)).willReturn(carRentalRecord);
        carRentalRecordService.returnCar(recordId);

        verify(carService, times(1)).updateCarStatus(carId, CarStatus.IN_STOCK.getCode());
        verify(carRentalRecordMapper, times(1)).updateById(carRentalRecord);
    }

    @Test
    public void should_returnCarFail_when_statusInvalid() {
        int recordId = 1;
        int carId = 1;
        CarRentalRecord carRentalRecord = new CarRentalRecord();
        carRentalRecord.setStatus(CarRentalRecordStatus.FINISHED.getCode());
        carRentalRecord.setCarId(carId);
        given(carRentalRecordMapper.selectById(recordId)).willReturn(carRentalRecord);

        Assert.assertThrows(CustomException.class, () -> carRentalRecordService.returnCar(recordId));
    }

    @Test
    public void should_returnCarFail_when_recordNotExist() {
        int recordId = 1;
        given(carRentalRecordMapper.selectById(recordId)).willReturn(null);

        Assert.assertThrows(CustomException.class, () -> carRentalRecordService.returnCar(recordId));
    }
}
