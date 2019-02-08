package com.gates.standstrong.domain.data.proximity;

public interface ProximityChart {

    public Long getProximityId();
    public String getChartDay();
    public String getChartHour();
    public String getChartEvent();
    public String getChartValue();
    public Long getMotherId();

}
