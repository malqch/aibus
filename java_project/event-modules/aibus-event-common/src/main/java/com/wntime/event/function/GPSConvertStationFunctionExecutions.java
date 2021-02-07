package com.wntime.event.function;

import com.wntime.modules.gemfire.vo.LineStationInfoVo;
import com.wntime.modules.gemfire.vo.Location;
import org.springframework.data.gemfire.function.annotation.Filter;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;
import org.springframework.data.gemfire.function.annotation.RegionData;

import java.util.Iterator;
import java.util.Set;

/**
 * @author 79448
 * @date 2020/9/17 15:13
 */
//@OnRegion(region = "bus_line_station")
public interface GPSConvertStationFunctionExecutions {

    @FunctionId("GPSConvertStationFunction")
    Iterable<LineStationInfoVo> convert(@Filter Set<?> busId, @RegionData Location location);
}
