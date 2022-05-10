var option = {
    title: {
        text: '未来10小时温度折线图'
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
		data: ['现在', '1：00', '2：00', '3：00', '4：00', '6：00', '7：00', '8：00', '9：00', '10：00'],
		axisLabel: { //坐标轴刻度标签的相关设置。
			interval: 'auto',//设置为 1，表示『隔一个标签显示一个标签』
			textStyle: {
				color: '#1B253A',
				fontStyle: 'normal',
				fontFamily: '微软雅黑',
				fontSize: 12,
			},
			formatter: '{value}s'
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
			name: "皮肤温度/°C",
			splitNumber: 5,
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
            data: [15,16,18,20,15,14,15,16,17,16],
        }
    ]
};