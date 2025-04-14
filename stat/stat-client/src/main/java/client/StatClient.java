package client;

import enw.ParamDto;
import enw.ViewStats;

import java.util.List;

public interface StatClient {

    void hit(String app, String uri, String ip);

    List<ViewStats> getStat(ParamDto paramDto);
}
