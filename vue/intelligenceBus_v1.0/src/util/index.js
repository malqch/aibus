// import router from '@/router'
// import store from '@/store'

/**
 * 获取uuid
 */
export function getUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
        return (c === 'x' ? (Math.random() * 16 | 0) : ('r&0x3' | '0x8')).toString(16)
    })
}

/**
 * 是否有权限
 * @param {*} key
 */
// export function isAuth (key) {
//   return JSON.parse(sessionStorage.getItem('permissions') || '[]').indexOf(key) !== -1 || false
// }

//菜单列表树形结构
export function treeDataTranslate(data, id = 'id', pid = 'parentId') {
    var res = []
    var temp = {}
    if (!data || data.length < 1) {
        return res;
    }
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            res.push(data[k])
        }
    }
    // console.log(res);
    return res
}

export function treeDataTranslate1(data, id = 'areaId', pid = 'parentAreaId') {
    var res = []
    var temp = {}
    if (!data || data.length < 1) {
        return res;
    }
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            res.push(data[k])
        }
    }
    //console.log(res);
    return res
}

export function treeDataTranslate2(data, id = 'eventTypeId', pid = 'parentTypeId') {
    var res = []
    var temp = {}
    if (!data || data.length < 1) {
        return res;
    }
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            res.push(data[k])
        }
    }
    //console.log(res);
    return res
}

export function treeDataTranslate3(data, id = 'icabId', pid = 'parentIcabId') {
    var res = []
    var temp = {}
    if (!data || data.length < 1) {
        return res;
    }
    for (var i = 0; i < data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    for (var k = 0; k < data.length; k++) {
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            res.push(data[k])
        }
    }
    //console.log(res);
    return res
}


/**
 * 清除登录信息
 */
// export function clearLoginInfo () {
//   Vue.cookie.delete('token')
//   store.commit('resetStore')
//   router.options.isAddDynamicMenuRoutes = false
// }
//获取菜单图标
export function getNameList() {
    return iconList.map(item => item.default.id.replace('icon-', ''))
}
