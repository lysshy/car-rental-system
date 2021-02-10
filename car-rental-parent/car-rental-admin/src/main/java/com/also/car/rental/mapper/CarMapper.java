package com.also.car.rental.mapper;

import com.also.car.rental.entity.Car;
import com.also.car.rental.model.CarQueryReq;
import com.also.car.rental.model.CarRentalReq;
import com.also.car.rental.model.CarRentalView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CarMapper extends BaseMapper<Car> {

    @Update("update sys_car set status=#{status} where id=#{id}")
    void updateCarStatus(@Param("id") int id, @Param("status") int status);

    @Select("select * from sys_car where id= #{id} for update nowait")
    Car selectForUpdateNoWait(@Param("id") int id);

    @Select("select * from sys_car where id= #{id} for update")
    Car selectForUpdateWait(@Param("id") int id);

    @Select("select * from sys_car where stock_id=#{stockId} and status=0")
    List<Car> selectByStockId(@Param("stockId") int stockId);

    List<CarRentalView> selectCarRentalViews(@Param("req") CarQueryReq carQueryReq);

    List<Car> selectUnRentalCars(@Param("req") CarRentalReq carRentalReq);
}
