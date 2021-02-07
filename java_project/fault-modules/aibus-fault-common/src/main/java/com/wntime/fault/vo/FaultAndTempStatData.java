package com.wntime.fault.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author psl
 * 2020/8/26 11:22
 */
public class FaultAndTempStatData {
    private List<String> horizontalShowList = new ArrayList<>(); // 横坐标数据 分组名称
    private List<String> verticalShowList = new ArrayList<>(); // 纵坐标数据的分类
    private List<VerValueData> verValueList = new ArrayList<>(); // 纵坐标数据列表


    public FaultAndTempStatData(){
        horizontalShowList = new ArrayList<>();
        verticalShowList = new ArrayList<>();
        verValueList = new ArrayList<>();
    }

    public List<String> getHorizontalShowList() {
        return horizontalShowList;
    }

    public void setHorizontalShowList(List<String> horizontalShowList) {
        this.horizontalShowList = horizontalShowList;
    }

    public List<String> getVerticalShowList() {
        return verticalShowList;
    }

    public void setVerticalShowList(List<String> verticalShowList) {
        this.verticalShowList = verticalShowList;
    }

    public List<VerValueData> getVerValueList() {
        return verValueList;
    }

    public void setVerValueList(List<VerValueData> verValueList) {
        this.verValueList = verValueList;
    }
}
