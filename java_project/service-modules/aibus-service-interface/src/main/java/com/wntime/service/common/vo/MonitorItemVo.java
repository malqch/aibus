package com.wntime.service.common.vo;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

/**
 * @author 79448
 * @date 2020/8/26 13:16
 */

public class MonitorItemVo implements PdxSerializable {

    private int count;

    private String name;

    public MonitorItemVo() {
    }

    public MonitorItemVo(String name, int count) {
        this.count = count;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrementCount(){
        this.count+=1;
    }

    @Override
    public void toData(PdxWriter pdxWriter) {
        pdxWriter.writeInt("count",count);
        pdxWriter.writeString("name",name);
    }

    @Override
    public void fromData(PdxReader pdxReader) {
        this.count=pdxReader.readInt("count");
        this.name=pdxReader.readString("name");
    }
}
