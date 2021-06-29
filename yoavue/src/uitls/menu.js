//根据用户获取菜单
import {getRequest, postRequest} from "../axios";


export const initMenu = (router,store)=>{
    //判断statede的routes参数中是否有数据，有则不会请求菜单
    if (store.state.routes.length>0){
        return;
    }
    //如果没有，则加载菜单
    postRequest("/MenuList?id=" + localStorage.getItem("userid")).then(res => {
        let data = res.data
        //如果有返回菜单数据
        if(data){
            //对数据进行格式化
            let fmtRoutes = formatRoutes(data)
            //将菜单添加到路由
            // router.add(fmtRoutes)
            router.addRoutes(fmtRoutes)
            //将数据保存到store`
            store.commit('initRoutes',fmtRoutes)
        }
    })
}

//格式化返回的菜单
export const formatRoutes = (routes) =>{
    let fmtRoutes = [];
    //遍历后端返回的数据
    routes.forEach(router =>{
        let {
            objId,
            path,
            component,
            name,
            icon,
            children,
        } = router;
        //如果children存在且为列表，递归调用
        if(children && children instanceof Array){
            children = formatRoutes(children);
        }
        let fmRouter = {
            objId:objId,
            path:path,
            name:name,
            icon:icon,
            children:children,
            component(resolve){
                //拼接组件
                require(['../views'+path+'.vue'],resolve);
            }
        }
        fmtRoutes.push(fmRouter)
    });
    return fmtRoutes;
}

