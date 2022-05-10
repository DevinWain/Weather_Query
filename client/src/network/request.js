// 通过对axios进行一层封装,减少项目对第三方框架的依赖性
import axios from 'axios'

// 设置默认请求头加上传输数据为json格式,字符串为utf-8
axios.defaults.headers["Content-Type"] = "application/json;charset=utf-8s"

//不使用default的话就可以导出多个自定义的axios模块
export function myRequest(config){
  //1.创建axios实例,而不要使用全局默认的
  const myService = axios.create({
    // 基础路径，这里以后拼接url的时候前面要额外加多一个'/'
    baseURL: 'http://localhost:8080/api',
    // 超时时间,单位为毫秒
    timeout:5000
  })

  //返回axios实例，该实例是promise，可直接使用
  return myService(config)
}
