package client;

import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;

import java.util.List;

public interface StatClient {

    void hit(ParamHitDto dto);

    List<ViewStats> getStat(ParamDto paramDto);
}
