<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >公交车卡片</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(tableData.busId)" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>修改</el-button>

                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                    <el-button type="primary" @click="goBack" size="small" style="float: right;margin-right:10px;margin-left:0px !important;" plain>
                        <i class=" fa fa-mail-reply-all"></i>返回</el-button>
                </div>
            </div>
            <div class="table_div card-text" >
                <el-row  class="card-title-box card-title-box-h2">
                    <el-col :span="5">
                        <div>
                            <el-row>
                                <el-col :span="24"  class="title-box-svg">
                                    <div  class="title-bg-box-svg">
                                        <svg t="1577506349693" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="15997" width="100%" height="100%"><path d="M828.7 887.5V316.7l-310.6-82.4-145.7 44.6v-215l-110.3 56.4v50.9L183.3 217v670.5h-49.7v72.8h756v-72.8h-60.9z m-567-37.9c-12.5 2.5-25 5-37.4 7.5v-31.6c12.5-2.5 24.9-5 37.4-7.5v31.6z m0-58.1c-12.5 2.7-25 5.4-37.4 8.1V768c12.5-2.7 24.9-5.4 37.4-8.2v31.7z m0-58.2c-12.5 2.9-25 5.9-37.4 8.8v-31.6c12.5-2.9 24.9-5.9 37.4-8.8v31.6z m0-58.3c-12.5 3.2-25 6.3-37.4 9.5v-31.6c12.5-3.1 24.9-6.3 37.4-9.4V675z m0-58.1c-12.5 3.4-25 6.8-37.4 10.1v-31.6c12.5-3.4 24.9-6.8 37.4-10.2v31.7z m0-58.3c-12.5 3.6-25 7.2-37.4 10.8v-31.6c12.5-3.6 24.9-7.2 37.4-10.8v31.6z m0-58.1c-12.5 3.8-25 7.7-37.4 11.5v-31.6c12.5-3.8 24.9-7.6 37.4-11.4v31.5z m0-58.2c-12.5 4-25 8.1-37.4 12.1v-31.6c12.5-4 24.9-8.1 37.4-12.1v31.6z m0-58.2c-12.5 4.3-25 8.5-37.4 12.8v-31.6c12.5-4.2 24.9-8.5 37.4-12.8v31.6z m0-58.2c-12.5 4.5-25 8.9-37.4 13.4v-31.6c12.5-4.5 24.9-9 37.4-13.4v31.6z m0-58.2c-12.5 4.7-25 9.4-37.4 14.1v-31.6c12.5-4.7 24.9-9.4 37.4-14.1v31.6z m65.1 569.7c-13.6 2.7-27.2 5.4-40.8 8.2v-34.4c13.6-2.7 27.2-5.4 40.8-8.2v34.4z m0-59.4c-13.6 3-27.2 5.9-40.8 8.9v-34.4c13.6-3 27.2-5.9 40.8-8.9V778z m0-59.3l-40.8 9.6v-34.4l40.8-9.6v34.4z m0-59.4c-13.6 3.4-27.2 6.9-40.8 10.3v-34.4c13.6-3.4 27.2-6.9 40.8-10.3v34.4z m0-59.3c-13.6 3.7-27.2 7.4-40.8 11v-34.4c13.6-3.7 27.2-7.4 40.8-11V600z m0-59.4c-13.6 3.9-27.2 7.8-40.8 11.8V518c13.6-3.9 27.2-7.8 40.8-11.8v34.4z m0-59.3c-13.6 4.2-27.2 8.3-40.8 12.5v-34.4c13.6-4.2 27.2-8.3 40.8-12.5v34.4z m0-59.4L286 435.1v-34.4l40.8-13.2v34.4z m0-59.3c-13.6 4.6-27.2 9.3-40.8 13.9v-34.4c13.6-4.7 27.2-9.3 40.8-13.9v34.4z m0-59.3c-13.6 4.9-27.2 9.8-40.8 14.6v-34.4c13.6-4.9 27.2-9.8 40.8-14.6v34.4z m0-59.4L286 259.2v-34.4c13.6-5.1 27.2-10.2 40.8-15.4v34.5z m85.6 99.9l49.9-9.5 0.3 56.5-49.9 9.5-0.3-56.5z m0.7 127.5l49.9-9.5 0.3 56.7-49.9 9.5-0.3-56.7z m0.8 127.6l49.9-9.5 0.3 56.5-49.9 9.5-0.3-56.5z m0.8 124.2l49.9-6.2 0.4 56.6-49.9 6.2-0.4-56.6z m1 179.6l-0.3-56.5 49.9-1.6 0.3 56.5-49.9 1.6z m379.4 8.9l-235.3-7.9v-54.1l235.3 7.9v54.1z m0-120.9l-234.8-25.4-0.5-58.2 235.3 29.5v54.1z m0-117.6l-235.3-39.8v-54.1L795.1 619v54.1z m4.7-119.3l-240.1-49.3v-54.1l240.1 49.3v54.1z m0-127.2l-236.9-60.4v-54.1l236.9 60.4v54.1z" p-id="15998" fill="#0F95FF"></path></svg>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24"  class="title-text-box-svg">
                                    <div class="title-text-content-svg">{{tableData.vinCode}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="10"  class="card-right-box-h2">
                        <div  class="card-right-content card-right-content-h2">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">车辆VIN码: {{tableData.vinCode}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">车辆编号 ：{{tableData.busCode}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">公交车型 ：{{tableData.busTypeName}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建人 : {{tableData.createUserName}}</span>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="9">
                        <div class="">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">车牌号 ：{{tableData.plateCode}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">车辆状态 ：{{tableData.busStatusDesc}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">是否启用 : {{tableData.isEnabled == 1 ? '是':'否'}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建时间 : {{tableData.createdDate}}</span>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </div>


            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >公交设备列表</p><p class="title" style="font-size: 14px;color: #909399">（通过公交车上边缘计算设备可自动获取）</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandleDevice('device')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                    <el-button type="danger" @click="handleDeleteAll('device')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>
                    <!--<el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>-->
                </div>
            </div>
            <div class="table_div" >
                <div style="position: relative">
                    <el-table :data="deviceList" style="width: 100%" max-height="300" @selection-change="handleSelectionChangeDevice">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center"  label="序号" width="50px"></el-table-column>

                        <!--<el-table-column prop="plateCode" label="公交车牌" min-width="100"></el-table-column>
                        <el-table-column prop="busTypeName" label="公交车型" min-width="100"></el-table-column>-->
                        <el-table-column prop="vinCode" label="公交车VIN" min-width="120"></el-table-column>
                        <el-table-column prop="deviceTypeName" label="设备类型" min-width="100"></el-table-column>
                        <el-table-column prop="deviceName" label="设备名称" min-width="120"></el-table-column>
                        <el-table-column prop="deviceCode" label="设备编号" min-width="100"></el-table-column>
                        <el-table-column prop="deviceDescCode" label="设备描述编码" min-width="100"></el-table-column>
                        <el-table-column label="设备状态" min-width="100">
                            <template slot-scope="scope">{{scope.row.deviceStatus == 1?'在线':'离线' }}</template>
                        </el-table-column>

                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createUserName}}</span><br>
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.modifiedUserName}}</span><br>
                                <span>{{scope.row.modifiedDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning" @click="addOrUpdateHandleDevice('device',scope.row.busDeviceId)">
                                    <i class="el-icon-edit"></i>修改</el-button>
                                <el-button size="mini" type="danger" @click="handleDelete('device',scope.row.busDeviceId, scope.row.deviceName)">
                                    <i class="el-icon-delete"></i>删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>

            <!-- 刷新、批量删除 -->
            <div class="show_table_controller"  >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title">公交电池列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandleDevice('battery')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                    <el-button type="danger" @click="handleDeleteAll('battery')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>
                    <!--<el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>-->
                </div>
            </div>

            <!-- 分页列表 -->
            <div class="table_div" >
                <div style="position: relative;">
                    <el-table :data="batteryList" style="width: 100%" max-height="450" @selection-change="handleSelectionChangeBattery">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>

                        <!--<el-table-column prop="plateCode" label="公交车牌" min-width="100"></el-table-column>-->
                        <el-table-column prop="vinCode" label="公交车VIN" min-width="120"></el-table-column>
                        <!--<el-table-column prop="busTypeName" label="公交车型" min-width="100"></el-table-column>-->
                        <el-table-column prop="batteryTypeName" label="电池类型" min-width="140"></el-table-column>
                        <el-table-column prop="busBatteryCode" label="电池编号" min-width="100"></el-table-column>

                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createUserName}}</span><br>
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.modifiedUserName}}</span><br>
                                <span>{{scope.row.modifiedDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning" @click="addOrUpdateHandleDevice('battery',scope.row.busBatteryId)">
                                    <i class="el-icon-edit"></i>修改</el-button>
                                <el-button size="mini" type="danger" @click="handleDelete('battery',scope.row.busBatteryId, scope.row.busBatteryCode)">
                                    <i class="el-icon-delete"></i>删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                </div>
            </div>

            <!-- 刷新、批量删除 -->
            <div class="show_table_controller"  >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title">公交电机列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandleDevice('motor')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                    <el-button type="danger" @click="handleDeleteAll('motor')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>
                    <!--<el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>-->
                </div>
            </div>

            <!-- 分页列表 -->
            <div class="table_div" >
                <div style="position: relative;">
                    <el-table :data="motorList" style="width: 100%" max-height="450" @selection-change="handleSelectionChangeMotor">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center"  label="序号" width="50px"></el-table-column>

                        <!--<el-table-column prop="plateCode" label="公交车牌" min-width="100"></el-table-column>-->
                        <el-table-column prop="vinCode" label="公交车VIN" min-width="120"></el-table-column>
                       <!-- <el-table-column prop="busTypeName" label="公交车型" min-width="100"></el-table-column>-->
                        <el-table-column prop="motorTypeName" label="电机类型" min-width="140"></el-table-column>
                        <el-table-column prop="busMotorCode" label="电机编号" min-width="100"></el-table-column>

                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createUserName}}</span><br>
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.modifiedUserName}}</span><br>
                                <span>{{scope.row.modifiedDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning" @click="addOrUpdateHandleDevice('motor',scope.row.busMotorId)">
                                    <i class="el-icon-edit"></i>修改</el-button>
                                <el-button size="mini" type="danger" @click="handleDelete('motor',scope.row.busMotorId, scope.row.busMotorCode)">
                                    <i class="el-icon-delete"></i>删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>

            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(1)"></Dialog>
            <DialogDevice v-show="DialogDeviceVisible" ref="DialogDevice" @refreshDataList="getData(1)"></DialogDevice>
            <DialogBattery v-show="DialogBatteryVisible" ref="DialogBattery" @refreshDataList="getData(1)"></DialogBattery>
            <DialogMotor v-show="DialogMotorVisible" ref="DialogMotor" @refreshDataList="getData(1)"></DialogMotor>
            <el-dialog
                title="输入用户密码"
                :visible.sync="visible"
                width="30%">
                <el-form  :model="deleteAllForm" >
                    <el-form-item label="">
                        <el-input v-model="deleteAllForm.validatePassword" placeholder="请输入用户密码" maxlength="64" type="password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="visible = false">取 消</el-button>
                    <el-button type="danger" @click="dataFormSubmit">删除</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import {getInfoBusDetail,getInfoBusDeviceListByBusId, deleteInfoBusDevice, deleteInfoBusDeviceAll,
        getInfoBusBatteryListByBusId, deleteInfoBusBattery, deleteInfoBusBatteryAll,
        getInfoBusMotorListByBusId, deleteInfoBusMotor, deleteInfoBusMotorAll} from "@/api/parameter";
    import Dialog from "../Bus/Dialog";
    import DialogDevice from "../BusDevice/Dialog";
    import DialogBattery from "../BusBattery/Dialog";
    import DialogMotor from "../BusMotor/Dialog";
    export default {
        name: "BusInfo",
        data() {
            return {
                form: {
                    name: "",
                    page: 1,
                    limit: 10
                },
                busId:'',
                deleteType:'',
                tableData: {},
                deviceList : [],
                batteryList : [],
                motorList : [],
                checkedList :[],
                DialogVisible: false,
                DialogDeviceVisible : false,
                DialogBatteryVisible : false,
                DialogMotorVisible : false,
                visible : false,
                checkedDeviceList :[],
                checkedBatteryList :[],
                checkedMotorList :[],
                deleteAllForm : {
                    validatePassword : '',
                    ids : []
                }
            };
        },
        components: {
            Dialog,
            DialogBattery,
            DialogDevice,
            DialogMotor
        },
        activated : function() {
            //this.getData();
        },
        methods: {
            handleReload : function(){
                this.getData();
            },
            dataFormSubmit : function(){
                if(this.deleteAllForm.validatePassword == ''){
                    this.$message({
                        message: '用户密码不能为空,请输入用户密码！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                }else{
                    if(this.deleteType == 'device'){
                        this.deleteAllForm.ids = [];
                        if(this.checkedDeviceList.length > 0){
                            for(var i=0;i<this.checkedDeviceList.length;i++){
                                this.deleteAllForm.ids.push(this.checkedDeviceList[i].busDeviceId)
                            }
                        }
                        deleteInfoBusDeviceAll(this.deleteAllForm).then((res) => {
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.getData();
                                    }
                                })
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }else if(this.deleteType == 'battery'){
                        this.deleteAllForm.ids = [];
                        if(this.checkedBatteryList.length > 0){
                            for(var i=0;i<this.checkedBatteryList.length;i++){
                                this.deleteAllForm.ids.push(this.checkedBatteryList[i].busBatteryId)
                            }
                        }
                        deleteInfoBusBatteryAll(this.deleteAllForm).then((res) => {
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.getData();
                                    }
                                })
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }else if(this.deleteType == 'motor'){
                        this.deleteAllForm.ids = [];
                        if(this.checkedMotorList.length > 0){
                            for(var i=0;i<this.checkedMotorList.length;i++){
                                this.deleteAllForm.ids.push(this.checkedMotorList[i].busMotorId)
                            }
                        }
                        deleteInfoBusMotorAll(this.deleteAllForm).then((res) => {
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: '操作成功',
                                    type: 'success',
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.getData();
                                    }
                                })
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: 'error',
                                    offset: 300
                                })
                            }
                        })
                    }
                }
            },
            handleDeleteAll : function(type){
                let typeName = '';
                console.log(type);
                if(type =='device'){
                    typeName = '车辆设备';
                    console.log(this.checkedDeviceList);
                    this.checkedList = this.checkedDeviceList;
                }else if(type == 'battery'){
                    console.log(this.checkedBatteryList);
                    this.checkedList = this.checkedBatteryList;
                    typeName = '车辆电池';
                }else if(type == 'motor'){
                    console.log(this.checkedMotorList);
                    this.checkedList = this.checkedMotorList;
                    typeName = '车辆电机';
                }
                if(this.checkedList.length == 0){
                    this.$message({
                        message: '请选择删除的'+typeName+'数据！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                }else{
                    this.deleteType = type;
                    this.visible = true;
                    this.deleteAllForm.validatePassword = '';
                }

            },
            handleSelectionChangeDevice : function(val){
                console.log(val);
                this.checkedDeviceList = val;
                // this.deleteAllForm.ids = [];
                // if(this.checkedDeviceList.length > 0){
                //     for(var i=0;i<this.checkedDeviceList.length;i++){
                //         thisdeleteAllForm..ids.push(this.checkedDeviceList[i].busDeviceId)
                //     }
                // }
                console.log(this.checkedDeviceList);
            },
            handleSelectionChangeBattery : function(val){
                console.log(val);
                this.checkedBatteryList = val;
                // this.deleteAllForm.ids = [];
                // if(this.checkedBatteryList.length > 0){
                //     for(var i=0;i<this.checkedBatteryList.length;i++){
                //         this.deleteAllForm.ids.push(this.checkedBatteryList[i].busBatteryId)
                //     }
                // }
                console.log(this.checkedBatteryList);
            },
            handleSelectionChangeMotor : function(val){
                console.log(val);
                this.checkedMotorList = val;
                // this.deleteAllForm.ids = [];
                // if(this.checkedMotorList.length > 0){
                //     for(var i=0;i<this.checkedMotorList.length;i++){
                //         this.deleteAllForm.ids.push(this.checkedMotorList[i].busMotorId)
                //     }
                // }
                console.log(this.checkedMotorList);
            },
            goBack : function(){
                this.$router.push({
                    path : './Bus'
                })
            },
            //获取基础列表数据函数
            getData : function() {
                this.busId = this.$route.query.busId;
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getInfoBusDetail(this.busId).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.tableData = res.data.data.data;
                    }
                });
                getInfoBusDeviceListByBusId(this.busId).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.deviceList = res.data.data.list;
                    }
                });
                getInfoBusBatteryListByBusId(this.busId).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.batteryList = res.data.data.list;
                    }
                });
                getInfoBusMotorListByBusId(this.busId).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.motorList = res.data.data.list;
                    }
                });
            },
            //更改每页的条数触发的table渲染
            handleSizeChange : function(val){
                this.form.limit = val;
                this.getData(1);
            },
            //点击分页渲染页面函数
            handleCurrentChange : function(val){
                this.form.page = val;
                this.getData()
            },

            //修改车辆
            addOrUpdateHandle : function(id) {
                this.DialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.Dialogs.initData(id);
                });
            },
            addOrUpdateHandleDevice: function(type,id) {
                if(type == 'device'){
                    this.DialogDeviceVisible = true;
                    this.$nextTick(() => {
                        this.$refs.DialogDevice.initData(this.busId,id);
                    });
                }else if(type == 'battery'){
                    this.DialogBatteryVisible = true;
                    this.$nextTick(() => {
                        this.$refs.DialogBattery.initData(this.busId,id);
                    });
                }else if(type == 'motor'){
                    this.DialogMotorVisible = true;
                    this.$nextTick(() => {
                        this.$refs.DialogMotor.initData(this.busId,id);
                    });
                }
            },
            //删除当前条的数据操作
            handleDelete : function(type,id, name) {
                this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    if(type == 'device'){
                        deleteInfoBusDevice(id)
                            .then((res) => {
                                console.log('ok');
                                if (res.data && res.data.code == 0) {
                                    console.log(res);
                                    this.$message({
                                        message: "操作成功",
                                        type: "success",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                } else {
                                    this.$message({
                                        message: res.data.message,
                                        type: "error",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                }
                            });
                    }else if(type == 'battery'){
                        deleteInfoBusBattery(id)
                            .then((res) => {
                                console.log('ok');
                                if (res.data && res.data.code == 0) {
                                    console.log(res);
                                    this.$message({
                                        message: "操作成功",
                                        type: "success",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                } else {
                                    this.$message({
                                        message: res.data.message,
                                        type: "error",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                }
                            });
                    }else if(type == 'motor'){
                        deleteInfoBusMotor(id)
                            .then((res) => {
                                console.log('ok');
                                if (res.data && res.data.code == 0) {
                                    console.log(res);
                                    this.$message({
                                        message: "操作成功",
                                        type: "success",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                } else {
                                    this.$message({
                                        message: res.data.message,
                                        type: "error",
                                        offset: 300,
                                        duration: 1500,
                                        onClose: () => {
                                            this.getData(1);
                                        }
                                    });
                                }
                            });
                    }

                })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除"
                        });
                    });
            }
        },
        mounted : function() {
            this.getData();
        }
    };
</script>

<style scoped>
    /deep/ .red_table .el-table th{
        background-color:#E54444 !important;
    }
</style>
