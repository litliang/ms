package com.yzb.card.king.ui.transport.activity;

import com.alibaba.fastjson.JSON;
import com.yzb.card.king.ui.transport.bean.SpecialCar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShuttleTrainCarTypeActivity extends BaseCarTypeActivity
{


    private String trainStationId;

    @Override
    protected SpecialCar getParamObj()
    {
        SpecialCar specialCar = (SpecialCar) getIntent().getSerializableExtra("specialCar");
        trainStationId = getIntent().getStringExtra("trainStationId");
        return specialCar;
    }

    @Override
    protected Map<String, Object> setParams(SpecialCar specialCar, boolean isLoadMore)
    {
        Map<String, Object> param = new HashMap<>();
        param.put("startPlace", specialCar.startPlace);//出发地点
        param.put("endPlace", specialCar.endPlace);//目的地
        param.put("startDate", specialCar.startDate.getTime());//取车时间
        param.put("trainStationId", trainStationId);
        param.put("source", 2);
        return param;
    }

    @Override
    protected List<SpecialCar> onResult(Object result)
    {
        return JSON.parseArray(String.valueOf(result), SpecialCar.class);
    }
}
