import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'


//用户登录
export function loginSystem(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/login`,
        method: 'POST',
        data: params
    })
}
//用户退出
export function logoutSystem() {
    return fetch({
        url: `${API_SERVER_URL}/sys/logout`,
        method: 'POST',
        data: {}
    })
}
//用户修改密码
export function updateUserPassword(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/password`,
        method: 'POST',
        data: params
    })
}
//用户导航栏
export function getRightNav() {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/nav`,
        method: 'GET',
        data: {}
    })
}
//获取登录用户信息
export function getLoginUser() {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/info`,
        method: 'GET',
        data: {}
    })
}
//用户管理

// 用户列表
export function getUserList(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/list`,
        method: 'GET',
        data: params
    })
}
//某个用户的信息
export function getUserByUserId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/info/` + params,
        method: 'GET',
        data: {}
    })
}
//用户增加或更新
export function saveOrUpdateUser(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/` + params.url,
        method: 'POST',
        data: params
    })
}
//用户删除
export function deleteUserByUserId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/delete`,
        method: 'POST',
        data: params
    })
}
//角色管理
// 角色列表
export function getRoleList(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/role/list`,
        method: 'GET',
        data: params
    })
}
// 用户拥有的角色列表
export function getRoleListByUser(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/role/select/` + params,
        method: 'GET',
        data: params
    })
}
//某个角色的信息
export function getRoleByRoleId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/role/info/` + params,
        method: 'GET',
        data: {}
    })
}
//角色增加或更新
export function saveOrUpdateRole(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/role/` + params.url,
        method: 'POST',
        data: params
    })
}
//角色删除
export function deleteRoleByRoleId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/role/delete`,
        method: 'POST',
        data: params
    })
}
// 岗位列表
export function getPositionList(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/list`,
        method: 'GET',
        data: params
    })
}
// 用户拥有的岗位列表
export function getPositionListByUser(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/select/` + params,
        method: 'GET',
        data: params
    })
}
//某个岗位的信息
export function getPositionByPositionId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/info/` + params,
        method: 'GET',
        data: {}
    })
}
//岗位增加或更新
export function saveOrUpdatePosition(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/` + params.url,
        method: 'POST',
        data: params
    })
}
//岗位删除
export function deletePositionByPositionId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/delete`,
        method: 'POST',
        data: params
    })
}
// 菜单列表
export function getRightList() {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/list`,
        method: 'GET',
        data: {}
    })
}
//菜单路径是否存在
export function isPathExist(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/path`,
        method: 'GET',
        data: params
    })
}
// 用户拥有的菜单列表
export function getRightListByUser(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/select`,
        method: 'GET',
        data: params
    })
}
//菜单删除
export function deleteRightByRightId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/delete/` + params,
        method: 'POST',
        data: {}
    })
}
//某个菜单的信息
export function getRightByRightId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/info/` + params,
        method: 'GET',
        data: {}
    })
}
//菜单增加或更新
export function saveOrUpdateRight(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/right/` + params.pathUrl,
        method: 'POST',
        data: params
    })
}
// 操作日志列表
export function getOperationList(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/log/list`,
        method: 'GET',
        data: params
    })
}


//------------------------------------------------
//用户查单条
export function getSysUserDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/detail/` + params,
        method: 'GET',
        data: {}
    })
}

// 用户增加
export function getSysUserSave(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/save`,
        method: 'POST',
        data: params
    })
}

// 用户修改
export function getSysUserEdir(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/updateUserRole`,
        method: 'POST',
        data: params
    })
}

export function getUserTree() {
    return fetch({
        url:`${API_SERVER_URL}/sys/user/tree`,
        method:"GET"
    })
}
