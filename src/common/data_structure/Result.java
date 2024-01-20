package common.data_structure;

import java.util.Map;

public class Result {
    Map<String, Map<String, Number>> resultMap;

    public Result(Map<String, Map<String, Number>> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<String, Map<String, Number>> getResultMap() {
        return resultMap;
    }
}
