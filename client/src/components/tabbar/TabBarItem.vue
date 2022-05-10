<template>
	<div class="tab-bar-item">
		<slot name="item-icon"></slot>
		<slot name="item-text"></slot>
	</div>
</template>

<script>
	export default {
		name: "TabBarItem"
	}
</script>

<style>
	.tab-bar-item{
		flex: 1;
		text-align: center;
		height: 49px;
		font-size: 14px;
	}
	
	.tab-bar-item img{
		width: 24px;
		height: 24px;
		margin-top: 3px;
		/* 用于去除图片下面的空白 */
		vertical-align: middle;
		/* 如果又想要留一些像素 */
		margin-bottom: 2px;
	}
</style>
