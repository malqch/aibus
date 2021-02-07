package com.wntime.fault.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 前端雷达图
 * @author ysc
 * 2020/8/25 16:56
 */

@ApiModel
@Data
@AllArgsConstructor
public class Radar {

    private List<Integer> data;
    private List<RadarIndicator> indicatorList;
    private int total;

    public static class RadarIndicator {

        private String text;
        private int max;

        public RadarIndicator(String text, int max) {
            this.text = text;
            this.max = max;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
}
