<template>
    <el-dialog :inline="true" :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">

        <el-form :model="dataForm" ref="model" class="" :rules="dataRule" label-width="120px">

            <el-form-item label="公交公司" size="mini" prop="companyId">
                <el-select
                    style="margin-bottom: 4px;"
                    v-model="dataForm.companyId"
                    filterable
                    clearable
                    default-first-option
                    :disabled="disabledLine"
                    @change="changeCompany"
                    placeholder="请选择公交公司">
                    <el-option
                        v-for="item in companyList"
                        :key="item.companyId"
                        :label="item.companyName"
                        :value="item.companyId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-row>
                <el-col :span="20">
                    <el-form-item label="线路编码" prop="companyLineCode">
                        <el-input v-model="dataForm.companyLineCode" @change="changeLineCode" placeholder="根据公司所在地区的线路编码(例:5路)获取“线路名称”" ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                        <el-button type="warning" size="small" style="float: right;margin-top: 5px" @click="searchLineCode()">获取线路</el-button>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="20">
                    <el-form-item label="线路名称">
                        <el-select
                            style="margin-bottom: 4px;"
                            v-model="dataForm.companyLineKey"
                            filterable
                            default-first-option
                            :disabled="disabledLine"
                            @change="changeCompanyLine"
                            placeholder="根据线路名称获取公交车站列表">
                            <el-option
                                v-for="item in companyLinaNameList"
                                :key="item.companyLineKey"
                                :label="item.companyLineName"
                                :value="item.companyLineKey">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                        <el-button type="warning" size="small" style="float: right;margin-top: 5px" @click="searchCompanyLine()">获取车站</el-button>
                </el-col>

            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="线路类型" prop="lineType">
                        <el-select
                            style="width:165px;"
                            v-model="dataForm.lineType"
                            clearable
                            default-first-option
                            placeholder="请选择线路类型">
                            <el-option
                                v-for="item in lineTypeList"
                                :key="item.configParamId"
                                :label="item.paramName"
                                :value="item.configParamId">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="总里程(公里)" prop="lineMileage">
                        <el-input v-model.number="dataForm.lineMileage" style="width:165px;" placeholder="线路里程" maxlength="30"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="夏季开始时间" prop="summerStartTime">
                        <el-date-picker style="width:165px"
                                        arrow-control
                                        format="MM 月 dd 日"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.summerStartTime"
                                        placeholder="夏季开始时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="结束时间" prop="summerEndTime">
                        <el-date-picker style="width:165px"
                                        arrow-control
                                        format="MM 月 dd 日"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.summerEndTime"
                                        placeholder="夏季结束时间">
                        </el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                     <el-form-item label="夏季首班时间" prop="summerFirstTime">
                        <el-time-picker style="width:165px"
                            arrow-control
                            format="HH 点 mm 分"
                            value-format="yyyy-MM-dd HH:mm"
                            v-model="dataForm.summerFirstTime"
                            placeholder="夏季首班时间">
                        </el-time-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="末班时间" prop="summerLastTime">
                        <el-time-picker style="width:165px"
                                        arrow-control
                                        format="HH 点 mm 分"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.summerLastTime"
                                        placeholder="夏季末班时间">
                        </el-time-picker>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="冬季首班时间" prop="winterFirstTime">
                        <el-time-picker style="width:165px"
                                        arrow-control
                                        format="HH 点 mm 分"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.winterFirstTime"
                                        placeholder="冬季首班时间">
                        </el-time-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="末班时间" prop="winterLastTime">
                        <el-time-picker style="width:165px"
                                        arrow-control
                                        format="HH 点 mm 分"
                                        value-format="yyyy-MM-dd HH:mm"
                                        v-model="dataForm.winterLastTime"
                                        placeholder="冬季末班时间">
                        </el-time-picker>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item label="是否启用" prop="value" >
                <el-switch  v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>

            <el-card class="box-card">
                <div id='container'></div>
                <div slot="header" class="clearfix">
                    <span>公交车站列表</span>
                    <el-button type="button" size="mini" style="float: right;" @click="addLine('device_color_list')">添加</el-button>
                </div>
                <div style="height: 200px;overflow-y:auto">
                    <section v-for="(v,key) in model.device_color_list" style="width: 100%;overflow: hidden;">
                        <el-form-item label="公交车站" size="mini" label-width="80px" :inline="true"
                                      style="width: 280px;float: left;display: inline-block;">
                            <el-select
                                    style="width:210px"
                                    v-model="v.busStationId"
                                    filterable
                                    default-first-option
                                    placeholder="请选择公交车站">
                                <el-option
                                        v-for="item in busStationIdOptions"
                                        :key="item.busStationId"
                                        :label="item.busStationCode+'：'+item.busStationName"
                                        :value="item.busStationId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label-width="30px"  style="float: left;margin-bottom:5px; display: inline-block;">
                            <el-button  type="danger" size="small" @click="deleteLine('device_color_list',key)">删除</el-button>
                            <el-button v-if="key!=0"  type="primary" size="small" @click="sortUpLine('device_color_list',key)">向上</el-button>
                            <el-button v-if="key!=model.device_color_list.length-1" type="primary" size="small" @click="sortDownLine('device_color_list',key)">向下</el-button>
                        </el-form-item>
                    </section>
                </div>
            </el-card>

        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="visible = false">取消</el-button>
          <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
        </span>
    </el-dialog>

</template>

<script>

    import {getLineTypeS} from '@/api/selectionApi';
    import {getInfoBusCompanyListAll,saveInfoCompanyLineBatch,editInfoCompanyLine} from "@/api/parameter";
    import {getInfoCompanyLineDetail} from "@/api/parameter";

    export default {
        data(){
            return {
                title: '新增',
                visible: false,
                value: false,
                companyList: [],
                areaId:0,
                map:null,
                companyLongitude: 0,
                companyLatitude: 0,
                companyLinaNameListCount: 0,
                companyLinaNameList: [],
                lineTypeList: [],
                disabledLine: false,
                busStationIdOptions: [],
                dataForm: {
                    companyId: '',
                    companyLineId: '',
                    companyLineKey: '',
                    companyLineName: '',
                    companyLineCode: '',
                    summerStartTime: '',
                    summerEndTime: '',
                    summerFirstTime: '',
                    summerLastTime: '',
                    winterFirstTime: '',
                    winterLastTime: '',
                    lineType: '',
                    lineMileage: '',
                    busStationIds: [],
                    busStationList:[],
                    isEnabled: 0,
                },
                model: {
                    device_color_list: []
                },
                dataRule: {
                    companyId: [
                        {required: true, message: '请选择公交公司', trigger: 'change'}
                    ],
                    // companyLineName: [
                    //     {required: true, message: '请选择线路名称', trigger: 'change'}
                    // ],
                    companyLineCode: [
                        {required: true, message: '请输入线路编码', trigger: 'blur'},
                        {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                    ],
                    lineType: [
                        {required: true, message: '请选择线路类型', trigger: 'change'}
                    ],
                    lineMileage: [
                        {required: true, message: '请输入线路里程', trigger: 'blur'},
                        {type: 'number', message: '线路里程必须为数字值'}
                    ],
                    summerStartTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    summerEndTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    summerFirstTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    summerLastTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    winterFirstTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ],
                    winterLastTime: [
                        {required: true, message: '请选择时间', trigger: 'change'}
                    ]
                }
            }
        },
        computed: {
            jsonString() {
                return JSON.stringify(this.model, null, 2).trim();
            }
        },
        methods: {
            changeCompany: function (companyId) {
                // 查询公司经纬度
                let longitude,latitude,areaId;
                this.companyList.forEach(function (item) {
                    if(item.companyId == companyId){
                        longitude = item.companyLongitude;
                        latitude = item.companytLatitude;
                        areaId = item.areaId;
                        return;
                    }
                })
                this.companyLongitude = longitude;
                this.companyLatitude = latitude;
                this.areaId = areaId;
                this.companyLinaNameList = [];
                this.model.device_color_list = [];
                this.dataForm.companyLineKey = null;
            },
            changeLineCode(companyLineCode){
                console.log("code:"+companyLineCode);
                this.companyLinaNameList = [];
                this.model.device_color_list = [];
                this.dataForm.companyLineKey = null;
            },
            searchLineCode(){ // 查询线路名称信息
                console.log(this.dataForm.companyId);
                if (this.dataForm.companyId == '') {
                    this.$message({
                        message: '请先选择公交公司！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else if (this.dataForm.companyLineCode == '') {
                    this.$message({
                        message: '请输入公交线路编码！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else{
                    const loading = this.$loading({
                        lock: true,
                        text: '正在获取公交线路名称.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    var thisNameListCount = 0;
                    var thisNameList = [];
                    var _this = this

                    // 创建Map实例
                    _this.map = new BMapGL.Map("container");
                    _this.map.centerAndZoom(new BMapGL.Point(this.companyLongitude, this.companyLatitude), 12);
                    console.log('start');
                    var busline = new BMapGL.BusLineSearch(_this.map,{
                        // renderOptions:{map:map,panel:"r-result"},
                        onGetBusListComplete: function(result){
                            if(result) {
                                console.log('size:'+result.getNumBusList());
                                thisNameListCount = result.getNumBusList();
                                thisNameList = result._listItems;
                                var fstLine = result.getBusListItem(0);//获取第一个公交列表显示到map上
                                busline.getBusLine(fstLine);
                            }
                        }
                    });
                    console.log('end');

                    var busCode = this.dataForm.companyLineCode;
                    if(busCode.indexOf("路") > -1){
                        busCode = busCode.substr(0,busCode.indexOf("路"));
                    }
                    console.log('code:'+busCode);
                    busline.getBusList(busCode);

                    console.log('startLine');
                    function busSearch(){
                        busline.setGetBusLineCompleteCallback(function (result) {
                            console.log(result);//查询的完整结果
                        });
                    }
                    console.log('endLine');

                    setTimeout(function(){
                        busSearch();
                    },1500);


                    // 数据处理
                    function getInfo(){
                        _this.companyLinaNameList = [];
                        _this.model.device_color_list = [];
                        if(thisNameListCount > 0){
                            for(var key = 0;key < thisNameListCount;key++){
                                var obj = new Object();
                                obj.companyLineKey = key;
                                obj.companyLineName = thisNameList[key].name;

                                _this.companyLinaNameList.push(obj);
                            }
                        }
                        console.log(_this.companyLinaNameList);
                        _this.dataForm.companyLineKey = null;
                        console.log(_this.dataForm.companyLineKey);
                        loading.close();
                    }
                    setTimeout(function(){
                        getInfo();
                    },3000);
                }
            },
            changeCompanyLine(companyLineKey){
                console.log("companyLineKey:"+companyLineKey);
                this.model.device_color_list = [];
            },
            searchCompanyLine:function(){
                console.log(this.dataForm.companyLineKey);
                if (this.dataForm.companyId == '') {
                    this.$message({
                        message: '请先选择公交公司！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else if (this.dataForm.companyLineCode == '') {
                    this.$message({
                        message: '请先输入公交线路编码！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else if (this.dataForm.companyLineKey == null) {
                    this.$message({
                        message: '请先点击“获取线路”或选择已有“线路名称”！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else{
                    const loading = this.$loading({
                        lock: true,
                        text: '正在获取线路站点信息.......',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });

                    var objectLine;
                    var _this = this
                    console.log('change:'+_this.dataForm.companyLineKey);
                    // 创建Map实例
                    // var map = new BMapGL.Map("container");
                    // map.centerAndZoom(new BMapGL.Point(this.companyLongitude, this.companyLatitude), 12);
                    console.log('start');
                    var busline = new BMapGL.BusLineSearch(_this.map,{
                        // renderOptions:{map:map,panel:"r-result"},
                        onGetBusListComplete: function(result){
                            if(result) {
                                console.log('size:'+result.getNumBusList());
                                var fstLine = result.getBusListItem(_this.dataForm.companyLineKey);//获取第一个公交列表显示到map上
                                busline.getBusLine(fstLine);

                                busline.setGetBusLineCompleteCallback(function (result) {
                                    console.log(result);//查询的完整结果
                                    objectLine = result;
                                });
                            }
                        }
                    });
                    console.log('end');

                    var busCode = this.dataForm.companyLineCode;
                    if(busCode.indexOf("路") > -1){
                        busCode = busCode.substr(0,busCode.indexOf("路"));
                    }
                    console.log('code:'+busCode);
                    busline.getBusList(busCode);

                    // 数据处理
                    function getInfo(){
                        console.log(objectLine);
                        console.log(objectLine.name);
                        console.log('size:'+ objectLine.getNumBusStations());
                        _this.dataForm.companyLineName = objectLine.name;
                        _this.dataForm.summerFirstTime = '1970-01-01 '+objectLine.startTime;
                        _this.dataForm.summerLastTime = '2050-01-01 '+objectLine.endTime;
                        _this.dataForm.winterFirstTime = '1970-01-01 '+objectLine.startTime;
                        _this.dataForm.winterLastTime = '2050-01-01 '+objectLine.endTime;
                        _this.busStationIdOptions = [];
                        for(var key = 0;key < objectLine.getNumBusStations();key++){
                            var obj = new Object();
                            obj.busStationId = key;
                            obj.busStationName = objectLine._stations[key].name;
                            obj.busStationCode = objectLine._stations[key].name;
                            if(objectLine._stations[key].position.lng.toString().length >= 10){
                                obj.busStationLongitude = objectLine._stations[key].position.lng.toString().
                                substring(0,objectLine._stations[key].position.lng.toString().indexOf(".")+7);
                            }else{
                                obj.busStationLongitude = objectLine._stations[key].position.lng;
                            }

                            if(objectLine._stations[key].position.lat.toString().length >= 10){
                                obj.busStationLatitude = objectLine._stations[key].position.lat.toString().
                                substring(0,objectLine._stations[key].position.lat.toString().indexOf(".")+7);
                            }else{
                                obj.busStationLatitude = objectLine._stations[key].position.lat;
                            }
                            obj.busStationDeviation = 50;
                            obj.areaId = _this.areaId;
                            _this.busStationIdOptions.push(obj);
                        }
                        _this.dataForm.busStationList = _this.busStationIdOptions;
                        console.log(_this.dataForm.busStationList);

                        _this.model.device_color_list = [];
                        for(var i in _this.busStationIdOptions){
                            var obj = new Object();
                            obj.busStationId = _this.busStationIdOptions[i].busStationId;
                            _this.model.device_color_list.push(obj);
                        }
                        loading.close();
                    }
                    setTimeout(function(){
                        getInfo();
                    },1000);
                }
            },
            addLine(key,index){
                this.model[key].push({busStationId:"",busStationName:"" });
            },
            deleteLine(key,index){
                // 删除下拉站点
                this.model[key].splice(index,1);

                // 删除提交时线路站点
                this.dataForm.busStationList.splice(index,1);
            },
            sortUpLine(key,index){
                this.model[key][index] = this.model[key].splice(index-1, 1, this.model[key][index])[0];
                this.dataForm.busStationList[index] = this.dataForm.busStationList.splice(index-1, 1, this.dataForm.busStationList[index])[0];
            },
            sortDownLine(key,index){
                this.model[key][index] = this.model[key].splice(index+1, 1, this.model[key][index])[0];
                this.dataForm.busStationList[index] = this.dataForm.busStationList.splice(index+1, 1, this.dataForm.busStationList[index])[0];
            },
            initData(id){
                getInfoBusCompanyListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.companyList = res.data.data.list
                    }
                });
                getLineTypeS().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.lineTypeList = res.data.data.selection;
                    }
                });
                this.dataForm.companyLineId = '';
                this.dataForm.companyLineName = '';
                this.dataForm.companyLineCode = '';
                this.dataForm.summerStartTime = '';
                this.dataForm.summerEndTime = '';
                this.dataForm.summerFirstTime = '';
                this.dataForm.summerLastTime = '';
                this.dataForm.winterFirstTime = '';
                this.dataForm.winterLastTime = '';
                this.dataForm.busStationList = [];
                this.dataForm.companyLineKey = null;
                this.companyLinaNameList = [];
                this.busStationIdOptions = [];


                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                this.$nextTick(() => {
                    if (this.$refs['model'] !== undefined) {
                        this.$refs['model'].resetFields()
                    }
                });
                if(id){
                }else {

                    this.title = '新增';
                    this.visible = true

                    loading.close();
                    this.dataForm.isEnabled=0
                    this.dataForm.summerStartTime = '1970-05-01 00:00';
                    this.dataForm.summerEndTime = '2050-10-01 00:00';
                    this.dataForm.summerFirstTime = '1970-01-01 06:00';
                    this.dataForm.summerLastTime = '2050-01-01 21:00';
                    this.dataForm.winterFirstTime = '1970-01-01 06:00';
                    this.dataForm.winterLastTime = '2050-01-01 20:00';
                    this.value = true;
                    this.model.device_color_list=[];
                }
            },
            dataFormSubmit(){
                if (this.dataForm.busStationList.length == 0) {
                    this.$message({
                        message: '请添加线路上的公交车站！',
                        type: "error",
                        offset: 300,
                        duration: 2000
                    });
                }else{
                    if (this.value == true) {
                        this.dataForm.isEnabled = 1;
                    } else {
                        this.dataForm.isEnabled = 0;
                    }
                    this.$refs['model'].validate((valid)=>{
                        if(valid){
                            const loading = this.$loading({
                                lock: true,
                                text: '加载中.......',
                                spinner: 'el-icon-loading',
                                background: 'rgba(0,0,0,0.7)'
                            });

                            console.log("保存");
                            console.log(JSON.stringify(this.dataForm));
                            //return;
                            if (this.title == '新增') {
                                saveInfoCompanyLineBatch(this.dataForm).then(resp=>{
                                    loading.close();
                                    if (resp.data && resp.data.code === 0) {
                                        this.$message({
                                            message: '操作成功',
                                            type: 'success',
                                            offset: 300,
                                            duration: 1500,
                                            onClose: () => {
                                                this.visible = false
                                                this.$emit('refreshDataList')
                                            }
                                        })
                                    } else {
                                        this.$message({
                                            message: resp.data.message,
                                            type: 'error',
                                            offset: 300
                                        })
                                    }
                                });
                            }else{
                                editInfoCompanyLine(this.dataForm).then(resp=>{
                                    loading.close();
                                    if (resp.data && resp.data.code === 0) {
                                        this.$message({
                                            message: '操作成功',
                                            type: 'success',
                                            offset: 300,
                                            duration: 1500,
                                            onClose: () => {
                                                this.visible = false
                                                this.$emit('refreshDataList')
                                            }
                                        })
                                    } else {
                                        this.$message({
                                            message: resp.data.message,
                                            type: 'error',
                                            offset: 300
                                        })
                                    }
                                });
                            }
                        }
                    });
                }
            }
        },
    };
</script>
<style scoped>

    /*.cards  >>>.el-form-item{
        margin-bottom: 5px !important;
    }
    .cards >>> .el-input__inner{
        width:  205px !important;
    }
    .color_input>>> .el-input__inner{
        width: 150px !important;
        padding: 0px 2px !important;
    }
    .cards >>> .box-card{
        margin: 0px -20px;
    }
    .cards >>>.el-input-group__append ,.cards >>>.el-input-group__prepend{
        padding: 0px 5px !important;
    }
    .cards >>> .el-card__body{
        padding-right: 0px !important;
    }
    .list__error{
        font-size: 12px;
        color: #F56C6C;
        display: none;
    }*/
</style>
