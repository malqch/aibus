<template>
    <!--百度地图-->
    <el-dialog title="定位设置" :visible.sync="mapData.mapVisible" :fullscreen="true" id="mapDialog"
               :close-on-click-modal="false">

        <label>关键词：<input v-model="keyword"></label>
        <label>地区：<input v-model="location"></label>

        <baidu-map class="map" :center="mapData.center" :zoom="mapData.zoom" @ready="handler"
                   :scroll-wheel-zoom="true"
                   @click="clickEvent">
            <!--地图控件位置-->
            <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
            <!--城市列表-->
            <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
            <!--定位当前位置-->
            <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"
                            @locationSuccess="getLoctionSuccess"></bm-geolocation>
            <!--地图容器-->
            <bm-view
                :style="{width:'100%',height: mapData.clientHeight+'px',flex: 1,marginBottom:'0'}"></bm-view>
            <div slot="" class="dialog-footer" style="margin:15px 0" >
                <div class="demo-input-suffix">
                    <el-link type="info">经度：</el-link>
                    <el-input class="lineinput" style="width:200px" size="mini"
                              v-model.number="mapData.locData.longitude"></el-input>
                    <el-link type="info">维度：</el-link>
                    <el-input class="lineinput" style="width:200px" size="mini"
                              v-model.number="mapData.locData.latitude"></el-input>
                    <!--<el-link type="info">位置：</el-link>-->
                    <!--<el-input class="lineinput" style="width:200px" size="mini" v-model="mapData.locData.address"></el-input>-->
                </div>
                <el-button type="warning" size="small" icon="el-icon-close" @click.native="cancelLocation">取消
                </el-button>
                <el-button type="primary" size="small" icon="el-icon-check" @click.native="submitLocation">确定</el-button>
            </div>
            <bm-local-search :keyword="keyword" :auto-viewport="true" :location="location"></bm-local-search>
        </baidu-map>
        <!-- <div slot="footer" class="dialog-footer">
            <div class="demo-input-suffix">
                <el-link type="info">经度：</el-link>
                <el-input class="lineinput" style="width:200px" size="mini"
                          v-model.number="mapData.locData.longitude"></el-input>
                <el-link type="info">维度：</el-link>
                <el-input class="lineinput" style="width:200px" size="mini"
                          v-model.number="mapData.locData.latitude"></el-input>
                <!--<el-link type="info">位置：</el-link>-->
                <!--<el-input class="lineinput" style="width:200px" size="mini" v-model="mapData.locData.address"></el-input>-->
            <!-- </div>
            <el-button type="warning" size="small" icon="el-icon-close" @click.native="cancelLocation">取消
            </el-button>
            <el-button type="primary" size="small" icon="el-icon-check" @click.native="submitLocation">确定</el-button>
        </div> -->
    </el-dialog>
</template>

<script>
    export default {
        name: "Map",
        data() {
            return {
                location: '北京',
                keyword: '百度',
                map: null,
                mapData: {
                    mapVisible: false,
                    initCenter: {lng: 0, lat: 0},
                    center: {lng: 116.406568, lat: 39.91582},
                    zoom: 15,
                    locData: {
                        longitude: '',
                        latitude: '',
                        address: '',
                    },
                    clientHeight: document.documentElement.clientHeight - 185, // 屏幕高度
                    iconUrl: require('./location.png')
                }
            }
        },
        methods: {
            handler({BMap, map}) {
                console.log('初始化地图实例')

                var _this = this
                _this.map = map;

                var longitude = 0
                var latitude = 0
                var geolocation = new BMap.Geolocation();
                geolocation.getCurrentPosition(function (r) {
                    if (_this.mapData.initCenter.lng && _this.mapData.initCenter.lat) {
                        longitude = _this.mapData.initCenter.lng
                        latitude = _this.mapData.initCenter.lat
                    } else {
                        longitude = r.longitude
                        latitude = r.latitude
                    }

                    _this.setLocation(longitude, latitude)
                }, {enableHighAccuracy: true})
            },
            //点击地图监听
            clickEvent(e) {
                this.setLocation(e.point.lng, e.point.lat)

                //用所定位的经纬度查找所在地省市街道等信息
//                var point = new BMap.Point(e.point.lng, e.point.lat);
//                var gc = new BMap.Geocoder();
//                gc.getLocation(point, function (rs) {
//                    var addComp = rs.addressComponents;
//                    //console.log(rs.address);//地址信息
//                    _this.mapData.locData.address = rs.address;
//                })
            },
            //定位成功回调
            getLoctionSuccess(point, AddressComponent, marker) {
                var _this = this

                console.log("定位成功回调", point)
                if ((!_this.mapData.center.lng) && (!_this.mapData.center.lat)) {
                    _this.setLocation(point.point.lng, point.point.lat)
                }
            },
            setLocation(longitude, latitude) {
                var _this = this
                _this.map.clearOverlays();

                _this.mapData.center = {lng: longitude, lat: latitude};
                _this.mapData.locData.longitude = longitude
                _this.mapData.locData.latitude = latitude

                let Icon_0 = new BMap.Icon(require('./location.png'), new BMap.Size(64, 64), {
                    anchor: new BMap.Size(18, 32),
                    imageSize: new BMap.Size(36, 36)
                });
                var myMarker = new BMap.Marker(new BMap.Point(longitude, latitude), {icon: Icon_0});
                _this.map.addOverlay(myMarker);
            },
            cancelLocation(){
                this.setLocation(this.mapData.initCenter.lng, this.mapData.initCenter.lat)
                this.mapData.mapVisible = false
            },
            submitLocation() {
//                this.setLocation(this.mapData.center.lng, this.mapData.center.lat)
                this.$emit("callback", this.mapData.locData)
                this.mapData.mapVisible = false
            },
            init(longitude, latitude) {
                this.mapData.mapVisible = true
                if (longitude && (longitude != 0)) {
                    this.mapData.initCenter.lng = longitude
                }
                if (latitude && (latitude != 0)) {
                    this.mapData.initCenter.lat = latitude
                }

                if (this.map && this.mapData.initCenter.lng && this.mapData.initCenter.lat) {
                    console.log("come...............")
                    this.setLocation(this.mapData.initCenter.lng, this.mapData.initCenter.lat)
                }
            }
        }
    }
</script>

<style scoped>
    .demo-input-suffix {
        display: inline-block;
        float: left;
    }
</style>
