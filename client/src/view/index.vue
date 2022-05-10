<template>
  <div class="total">
    
    <div class="header">
      <van-field
        v-model="fieldValue"
        is-link
        readonly
        label="地区"
        placeholder="请选择所在地区"
        @click="show = true"
      />
      <van-popup v-model="show" round position="bottom">
        <van-area
          title="请选择位置"
          :area-list="areaList"
          :columns-num="2"
          :columns-placeholder="['自动定位']"
          @confirm = "onConfirm"
          @cancel = "onCancel"
        />
      </van-popup>
    </div>

    <div class="content">
      <div class="box">
        <div style="display: flex;align-items: center;"><van-icon name="info" size="20"/>&nbsp;&nbsp;空气质量</div>

        <div style="font-size: 40px;display: flex;align-items: center;">
          {{AQI}}-<div class="AQIbox" :style="changeAQIboxColor()">{{AQIdescript}}</div>
         </div>

        <div>当前AQI（CN）为{{AQI}}</div>
      </div>

      <div id="AQIChart" class="myChart"></div>
      <br>
      <div id="TChart" class="myChart"></div>

    </div>
  </div>
</template>

<script>
  import * as echarts from 'echarts'
  import {myRequest} from '../network/request.js'
  import { areaList } from '@vant/area-data';

  export default {
    name: 'index',
    data(){
      return{
        show: false,
        fieldValue: '',
        weather: '晴',
        temperature: '20°C',
        fut_10_temp: [15,16,18,20,15,14,15,16,17,16],
        AQI: 10,
        areaList,
        myChart1:"",
        myChart2:"",
        AQIdescript: '空气质量优',
        options: [
            {
              text: '自动定位',              
            },
            
            
        ],
        AQIOption: {
          dataset: {
              source: [
                ['score', 'amount', 'product'],
                [289.3, 289.3, '现在'],
                [157.1, 157.1, '17:00'],
                [374.4, 374.4, '18:00'],
                [150.1, 150.1, '19:00'],
                [289.7, 289.7, '20:00'],
                [68.1, 68.1, '21:00'],
                [19.6, 19.6, '22:00'],
                [10.6, 10.6, '23:00'],
                [132.7, 132.7, '24:00'],
                [32.7, 32.7, '1:00'],
                [32.7, 32.7, '2:00']
              ]
            },
            grid: { containLabel: true },
            xAxis: {
              type: 'category' ,
              axisLabel:{
                textStyle:{
                  fontSize:7
                }
              }
            },
            yAxis: {
              name: 'AQI(空气质量)' ,
              min: 40,
              axisLabel:{
                textStyle:{
                  fontSize:7
                }
              }
            },
            tooltip: {
              trigger: 'axis',
              // formatter: '时间：{b0} s:<br />温度：{c0} °C'
          },
            visualMap: {
              orient: 'horizontal',
              left: 'center',
              min: 0,
              max: 500,
              text: ['严重污染', '优'],
              // Map the score column to color
              dimension: 0,
              inRange: {
                color: ['#65B581', '#FFCE34', '#FD665F']
              }
            },
            series: [
              {
                type: 'bar',
                encode: {
                  // Map the "amount" column to X axis.
                  x: 'product',
                  // Map the "product" column to Y axis
                  y: 'amount'
                }
              }
            ]
        },

        TOption:{
        title: {
            text: '未来10小时温度折线图',
            textStyle: {
            color: '#1B253A',
            fontStyle: 'normal',
            fontFamily: '微软雅黑',
            fontSize: 12,
          },
            
        },
        tooltip: {
            trigger: 'axis',
            // formatter: '时间：{b0} s:<br />温度：{c0} °C'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
          type: 'category',
            boundaryGap: false,//坐标轴两边留白
          // data: ['现在', '1：00', '2：00', '3：00', '4：00', '6：00', '7：00', '8：00', '9：00', '10：00'],
          data: ['现在', '1：00', '2：00', '3：00', '4：00', '6：00', '7：00', '8：00', '9：00', '10：00'],
          axisLabel: { //坐标轴刻度标签的相关设置。
            // interval: ,//设置为 1，表示『隔一个标签显示一个标签』
            textStyle: {
              color: '#1B253A',
              fontStyle: 'normal',
              fontFamily: '微软雅黑',
              fontSize: 12,
            },
          },
          axisTick:{//坐标轴刻度相关设置。
            show: false,
          },
          axisLine:{//坐标轴轴线相关设置
            lineStyle:{
              color:'#E5E9ED',
            }
          },
          splitLine: { //坐标轴在 grid 区域中的分隔线。
            show: true,
            lineStyle: {
              color: '#E5E9ED',
            }
          }
        },
        yAxis: [
        {
          type: 'value',
          name: "温度/°C",
          splitNumber:5,
          axisLabel: {
            textStyle: {
              color: '#a8aab0',
              fontStyle: 'normal',
              fontFamily: '微软雅黑',
              fontSize: 12,
              formatter: '{value} °C'
            }
          },
          axisLine:{
            show: false
          },
          axisTick:{
            show: false
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: '#E5E9ED',
            // 	opacity:0.1
            }
          }

		}
	],
        series: [
          {
                name: '温度',
                type: 'line',
                itemStyle: {
                normal: {
                    color:'#3A84FF',
                    lineStyle: {
                    color: "#3A84FF",
                    width:1
                    },
                areaStyle: { 
                  color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [{
                  offset: 0,
                  color: 'rgba(58,132,255,0)'
                }, {
                  offset: 1,
                  color: 'rgba(58,132,255,0.35)'
                }]),
                    }
                }
              },
                data: [15,16,18,25,15,14,15,16,17,16],
          }
        ]
        }
      }
    },
      mounted() {
      setTimeout(this.drawLine(),500)
    },
      beforeCreate(){
        myRequest({
            url: '/location',
            method: 'post',
          }).then(res =>{
            this.fieldValue = res.data.data.region + "省 / " + res.data.data.city + "市";
          }).catch(err => {
            console.log(err)
          });
        // myRequest({
        //     url: '/weather',
        //     method: 'post',
        //   }).then(res =>{
        //     this.AQI = res.data.aqi;
        //     // this.TOption.xAxis.data = res.data.fut_10_hour;
        //     this.TOption.xAxis.data = ['现在', '1：00', '2：00', '3：00', '4：00', '6：00', '7：00', '8：00', '9：00', '10：00'];
        //     // this.TOption.series.data = res.data.fut_10_temp;
        //     this.TOption.series.data = [15,16,18,20,15,14,15,16,17,16];
        //     console.log(res.data.fut_10_hour);
        //     console.log(res.data.fut_10_temp);
        //   }).catch(err => {
        //     console.log(err)
        //   });
        myRequest({
            url: '/test',
            method: 'post',
          }).then(res =>{
            this.AQI = res.data.aqi;
            this.TOption.xAxis.data = res.data.fut_10_hour;
            this.TOption.series[0].data = res.data.fut_10_temp;
            this.AQIOption.xAxis.data = res.data.fut_10_hour;
            this.AQIOption.dataset.source = this.getSource(res.data.fut_10_aqi, res.data.fut_10_hour);
            this.AQIOption.yAxis.min = Math.floor(Math.min(...res.data.fut_10_aqi)*0.9);
            this.TOption.yAxis[0].min = Math.floor(Math.min(...res.data.fut_10_temp)*0.9);
            this.AQIOption.yAxis.max = Math.floor(Math.max(...res.data.fut_10_aqi));
            // console.log(this.getSource(res.data.fut_10_aqi, res.data.fut_10_hour));
            // console.log(this.AQIOption.series[0]);
            this.myChart1.setOption(this.AQIOption);
            this.myChart2.setOption(this.TOption);
          }).catch(err => {
            console.log(err)
          });

    },
    methods:{
      onConfirm(arr){
        if(arr[0]["name"].length == 0){
          myRequest({
            url: '/location',
            method: 'post',
          }).then(res =>{
            this.fieldValue = res.data.data.region + "省 / " + res.data.data.city + "市";
            this.show = false;
            this.myChart2.setOption(this.TOption)
          }).catch(err => {
            console.log(err)
            this.show = false;
          });
        }
        else{
            this.fieldValue = arr[0]["name"] + "/" + arr[1]["name"];
            this.show = false;
        }
      },
      onCancel(){
          this.show = false;
      },
      changeAQIboxColor(){
        if(this.AQI>=0&&this.AQI<=50){
          this.AQIdescript = "优"
          return "background-color: green;"
        }else if(this.AQI<=100){
          this.AQIdescript = "良好"
          return "background-color: yellow;"
        }else if(this.AQI<=150){
          this.AQIdescript = "轻度污染"
          return "background-color: orange;"
        }else if(this.AQI<=200){
          this.AQIdescript = "中度污染"
          return "background-color: red;"
        }else if(this.AQI<300){
          this.AQIdescript = "重度污染"
          return "background-color: purple;"
        }else{
          this.AQIdescript = "严重污染"
          return "background-color: black;"
        }
      },
//  ['score', 'amount', 'product'],
      getSource(fut_10_aqi, fut_10_hour){
        var a = new Array();
        a[0] = new Array();
        a[0][0] = 'score';
        a[0][1] = 'amount';
        a[0][2] = 'product';
        for(var i=1;i<11;i++){        //一维长度为5        
          a[i] = new Array();        
          for(var j=0;j<3;j++){    //二维长度为5                        
                a[i][0] = fut_10_aqi[i-1];
                a[i][1] = fut_10_aqi[i-1];
                a[i][2] = fut_10_hour[i-1];
          }        
        }
        return a;
      },

      drawLine(){
        this.myChart1 = echarts.init(document.getElementById('AQIChart'))
        // this.myChart1.setOption(this.AQIOption)
        this.myChart2 = echarts.init(document.getElementById('TChart'))
        // this.myChart2.setOption(this.TOption)
      }
    }
  }
</script>

<style scoped="scoped">
  .box{
    width: 300px;
    margin: 20px;
    padding: 20px;
    border: 2px gainsboro;
    border-radius: 15px;
    background-color: whitesmoke;
    box-shadow: 5px 5px 5px rgb(0,0,0,.5);

    line-height:2.5em;color:#4e4e4e;
  }

  .content{
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
  }

  .AQIbox{
    height: 45px;
    font-size: 40px;
    color: white;
    padding: 0 20px;
    display: flex;
    align-items: center;
  }

  .cojei{
    color: gre;
  }

  .myChart{
    width: 350px;
    height: 350px;
  }
</style>
