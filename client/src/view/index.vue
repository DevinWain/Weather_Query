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
        <van-cascader
          v-model="cascaderValue"
          title="请选择所在地区"
          :options="options"
          @close="show = false"
          @finish="onFinish"
        />
      </van-popup>
      {{fieldValue}}{{cascaderValue}}
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
      <div id="TChart" class="myChart"></div>

    </div>
  </div>
</template>

<script>
  import * as echarts from 'echarts'
  import {myRequest} from '../network/request.js'

  export default {
    name: 'index',
    data(){
      return{
        show: false,
        fieldValue: '',
        cascaderValue: '',
        weather: '晴',
        temperature: '20°C',
        AQI: 10,
        AQIdescript: '空气质量优',
        options: [
            {
              text: '浙江省',
              value: '330000',
              children: [{ text: '杭州市', value: '330100' }],
            },
            {
              text: '江苏省',
              value: '320000',
              children: [{ text: '南京市', value: '320100' }],
            },
        ],
        AQIOption: {
          dataset: {
              source: [
                ['score', 'amount', 'product'],
                [89.3, 450, '现在'],
                [57.1, 240, '17:00'],
                [74.4, 370, '18:00'],
                [50.1, 50, '19:00'],
                [89.7, 150, '20:00'],
                [68.1, 150, '21:00'],
                [19.6, 340, '22:00'],
                [10.6, 140, '23:00'],
                [32.7, 370, '24:00'],
                [32.7, 420, '1:00'],
                [32.7, 500, '2:00']
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
              axisLabel:{
                textStyle:{
                  fontSize:7
                }
              }
            },
            visualMap: {
              orient: 'horizontal',
              left: 'center',
              min: 10,
              max: 100,
              text: ['High level', 'Low level'],
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
          xAxis: {
              type: 'category',
              data: ['现在', '1：00', '2：00', '3：00', '4：00', '6：00', '7：00', '8：00', '9：00', '10：00'],
              axisLabel:{
                interval: 0,
                textStyle:{
                  fontSize:7
                }
              }
            },
            yAxis: {
              type: 'value',
              axisLabel:{
                textStyle:{
                  fontSize:7
                }
              }
            },
            series: [
              {
                data: [15,16,18,20,15,14,15,16,17,16],
                type: 'line'
              }
            ]
        }
      }
    },
    mounted() {
      setTimeout(this.drawLine(),500)
      // this.$dialog.alert({
      //   title: '更多信息',
      //   message: this.moreMsg,
      // });
    },
    methods:{
      onFinish({ selectedOptions }) {
        this.show = false;
        this.fieldValue = selectedOptions.map((option) => option.text).join('/');
        myRequest({
          url: '/location',
          method: 'post',
          // data:{
          //   fieldValue: this.fieldValue
          // }
        }).then(res =>{
          console.log(res.data);
        }).catch(err => {
          console.log(err)
        });
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

      drawLine(){
        let myChart = echarts.init(document.getElementById('AQIChart'))
        myChart.setOption(this.AQIOption)
        myChart = echarts.init(document.getElementById('TChart'))
        myChart.setOption(this.TOption)
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
