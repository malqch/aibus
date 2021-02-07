<template>
    <el-dialog :title="title" :close-on-click-modal="false" :visible.sync="visible" style="color:#fff">

        <el-form :model="dataForm" ref="model" class="" :rules="dataRule" label-width="120px">

            <el-form-item label="公交线路名称" prop="companyLineName">
                <el-input v-model="dataForm.companyLineName" placeholder="公交线路名称" ></el-input>
            </el-form-item>
            <el-form-item label="公交线路编码" prop="companyLineCode">
                <el-input v-model="dataForm.companyLineCode" placeholder="公交线路编码" ></el-input>
            </el-form-item>
             <el-form-item label="夏季开始时间" prop="summerStartTime">
                <el-date-picker style="width:207px"
                    arrow-control
                    format="MM 月 dd 日"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.summerStartTime"
                    placeholder="夏季开始时间">
                </el-date-picker>
            </el-form-item>
             <el-form-item label="夏季结束时间" prop="summerEndTime">
                <el-date-picker style="width:207px"
                    arrow-control
                    format="MM 月 dd 日"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.summerEndTime"
                    placeholder="夏季结束时间">
                </el-date-picker>
            </el-form-item>
             <el-form-item label="夏季首班时间" prop="summerFirstTime">
                <el-time-picker style="width:207px"
                    arrow-control
                    format="HH 点 mm 分"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.summerFirstTime"
                    placeholder="夏季首班时间">
                </el-time-picker>
            </el-form-item>
             <el-form-item label="夏季末班时间" prop="summerLastTime">
                <el-time-picker style="width:207px"
                    arrow-control
                    format="HH 点 mm 分"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.summerLastTime"
                    placeholder="夏季末班时间">
                </el-time-picker>
            </el-form-item>
             <el-form-item label="冬季首班时间" prop="winterFirstTime">
                <el-time-picker style="width:207px"
                    arrow-control
                    format="HH 点 mm 分"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.winterFirstTime"
                    placeholder="冬季首班时间">
                </el-time-picker>
            </el-form-item>
             <el-form-item label="冬季末班时间" prop="winterLastTime">
                <el-time-picker style="width:207px"
                    arrow-control
                    format="HH 点 mm 分"
                    value-format="yyyy-MM-dd HH:mm"
                    v-model="dataForm.winterLastTime"
                    placeholder="冬季末班时间">
                </el-time-picker>
            </el-form-item>

            <el-form-item label="是否启用" prop="value" style="width:310px" >
                <el-switch  v-model="value" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </el-form-item>

            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>公交车站</span>
                    <el-button type="button" size="mini" style="float: right;" @click="addLine('device_color_list')">添加</el-button>
                </div>
                <div>
                    <section v-for="(v,key) in model.device_color_list" style="width: 100%;overflow: hidden;">
                        <el-form-item label="公交车站" size="mini"  label-width="80px" :inline="true" style="width: 260px;float: left;display: inline-block;">
                            <el-select
                                    style="margin-bottom: 4px;width:180px"
                                    v-model="v.busStationId"
                                    filterable
                                    default-first-option
                                    placeholder="请选择公交车站">
                                <el-option
                                        v-for="item in busStationIdOptions"
                                        :key="item.busStationId"
                                        :label="item.busStationName"
                                        :value="item.busStationId">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label-width="20px"  style="float: left;display: inline-block;">
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

    import {getAllBusStationS} from '@/api/selectionApi';
    import {saveInfoCompanyLine,editInfoCompanyLine} from "@/api/parameter";
    import {getInfoCompanyLineDetail} from "@/api/parameter";

    export default {
        data: () => ({
            title:'新增',
            visible: false,
            value: false,
            busStationIdOptions: [],
            dataForm:{
                companyId:'',
                companyLineId:'',
                companyLineName:'',
                companyLineCode:'',
                summerStartTime:'',
                summerEndTime:'',
                summerFirstTime:'',
                summerLastTime:'',
                winterFirstTime:'',
                winterLastTime:'',
                busStationIds:[],

                isEnabled : 0,
            },
            model: {
                device_color_list: []
            },
            dataRule :{
                companyId: [
                    {required: true, message: '请选择所属公司', trigger: 'change'}
                ],
                companyLineName:[
                    {required: true, message: '请输入线路名称', trigger: 'blur'},
                    {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                ],
                companyLineCode:[
                    {required: true, message: '请输入线路编码', trigger: 'blur'},
                    {max: 20, message: '长度不能超过20个字符', trigger: 'blur'}
                ],
                summerStartTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ],
                summerEndTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ],
                summerFirstTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ],
                summerLastTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ],
                winterFirstTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ],
                winterLastTime:[
                    {required: true, message: '请选择时间', trigger: 'change'}
                ]
            },
        }),
        computed: {
            jsonString() {
                return JSON.stringify(this.model, null, 2).trim();
            }
        },
        methods: {
            addLine(key,index){
                this.model[key].push({busStationId:"",busStationName:"" });
            },
            deleteLine(key,index){
                this.model[key].splice(index,1)
            },
            sortUpLine(key,index){
                this.model[key][index] = this.model[key].splice(index-1, 1, this.model[key][index])[0]
            },
            sortDownLine(key,index){
                this.model[key][index] = this.model[key].splice(index+1, 1, this.model[key][index])[0]
            },
            initData(id,companyId){
                this.dataForm.companyLineId = id;
                this.dataForm.companyId = companyId;
                this.dataForm.companyLineName = '';
                this.dataForm.companyLineCode = '';
                this.dataForm.summerStartTime = '';
                this.dataForm.summerEndTime = '';
                this.dataForm.summerFirstTime = '';
                this.dataForm.summerLastTime = '';
                this.dataForm.winterFirstTime = '';
                this.dataForm.winterLastTime = '';

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
                    this.title = '修改';
                    this.visible = true;
                    getAllBusStationS({'companyId':this.dataForm.companyId}).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.busStationIdOptions = res.data.data.list
                            return;
                        }
                    });

                    getInfoCompanyLineDetail(id).then(res=>{
                        loading.close();
                        if(res.data && res.data.code == 0) {
                            const data = res.data.data.data;
                            this.dataForm.isEnabled = data.isEnabled;
                            this.dataForm.companyLineName = data.companyLineName;
                            this.dataForm.companyLineCode = data.companyLineCode;
                            this.dataForm.summerStartTime = data.summerStartTime;
                            this.dataForm.summerEndTime = data.summerEndTime;
                            this.dataForm.summerFirstTime = data.summerFirstTime;
                            this.dataForm.summerLastTime = data.summerLastTime;
                            this.dataForm.winterFirstTime = data.winterFirstTime;
                            this.dataForm.winterLastTime = data.winterLastTime;
                            if (data.isEnabled == 1) {
                                this.value = true
                            } else {
                                this.value = false
                            }
                            //this.model = JSON.parse(data.configContent);
                            console.log(data);
                            console.log(data.busStationIds);

                            this.model.device_color_list = [];
                            for(var i in data.busStationIds){
                                var obj = new Object();
                                obj.busStationId = data.busStationIds[i];
                                this.model.device_color_list.push(obj);
                            }
                            console.log("公交车站:"+JSON.stringify(this.model.device_color_list));
                        }
                    })
                }else {

                    getAllBusStationS({'companyId':this.dataForm.companyId}).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.busStationIdOptions = res.data.data.list
                            return;
                        }
                    });

                    this.title = '新增';
                    this.visible = true

                    loading.close();
                    this.dataForm.isEnabled=0
                    this.value = true;
                    this.model.device_color_list=[
                        {busStationId:"",busStationName:""}
                    ];
                }
            },
            dataFormSubmit(){
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
                            background: 'rgba(0, 0, 0, 0.7)'
                        });

                        this.dataForm.busStationIds = [];
                        var a = this.model.device_color_list;
                        for(var i in a){
                            console.log(a[i].busStationId);
                            this.dataForm.busStationIds.push(a[i].busStationId);
                        }

                        console.log("保存");
                        console.log(JSON.stringify(this.dataForm));
                        //return;
                        if (this.title == '新增') {
                            saveInfoCompanyLine(this.dataForm).then(resp=>{
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
        },
    };
</script>
<style scoped>

    .cards  >>>.el-form-item{
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
    }
</style>
