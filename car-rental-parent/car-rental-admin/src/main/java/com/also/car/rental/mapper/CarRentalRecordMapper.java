package com.also.car.rental.mapper;

import com.also.car.rental.entity.CarRentalRecord;
import com.also.car.rental.model.CarRentalRecordView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CarRentalRecordMapper extends BaseMapper<CarRentalRecord> {

    @Select("select rr.id,rr.rent_time,rr.return_time,rr.user_name,rr.mobile,sc.brand_type,sc.plate_number from sys_car_rental_record rr left join sys_car sc on sc.id=rr.car_id ${ew.customSqlSegment}")
    Page<CarRentalRecordView> selectCarRentalRecordPage(Page<CarRentalRecordView> page, @Param("ew")QueryWrapper<CarRentalRecordView> queryWrapper);

    @Select("select rr.id,rr.rent_time,rr.return_time,rr.user_name,rr.mobile,sc.brand_type,sc.plate_number,rr.status from sys_car_rental_record rr left join sys_car sc on sc.id=rr.car_id ${ew.customSqlSegment}")
    List<CarRentalRecordView> selectCarRentalRecordViews(@Param("ew")QueryWrapper<CarRentalRecordView> queryWrapper);
}
