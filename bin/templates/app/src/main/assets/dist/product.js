// { "framework": "Vue"} 

!function(t){function e(r){if(i[r])return i[r].exports;var o=i[r]={i:r,l:!1,exports:{}};return t[r].call(o.exports,o,o.exports,e),o.l=!0,o.exports}var i={};e.m=t,e.c=i,e.i=function(t){return t},e.d=function(t,i,r){e.o(t,i)||Object.defineProperty(t,i,{configurable:!1,enumerable:!0,get:r})},e.n=function(t){var i=t&&t.__esModule?function(){return t.default}:function(){return t};return e.d(i,"a",i),i},e.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},e.p="",e(e.s=22)}({13:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("slider",{staticClass:["slider"]},[t._l(t.imageList,function(t){return i("div",{key:t,staticClass:["frame"]},[i("image",{staticClass:["image"],attrs:{resize:"cover",src:t.src}})])}),i("indicator",{staticClass:["indicator"]})],2),i("text",{staticClass:["product-name"]},[t._v(t._s(t.productDetail.name))]),i("div",{staticClass:["row"]},[i("text",{staticClass:["price"]},[t._v("￥"+t._s(t.productDetail.price))]),i("text",{staticClass:["original-price"]},[t._v("原价"+t._s(t.productDetail.originalPrice))])]),i("div",{staticClass:["divider"]}),i("div",{staticClass:["row"]},[i("text",{staticClass:["item"]},[t._v("运费："+t._s(t.productDetail.freight))]),i("text",{staticClass:["item"]},[t._v("销量："+t._s(t.productDetail.salesVolume)+"件")])]),i("div",{staticClass:["divider"]})])},staticRenderFns:[]},t.exports.render._withStripped=!0},22:function(t,e,i){var r,o,s=[];s.push(i(8)),r=i(3);var a=i(13);o=r=r||{},"object"!=typeof r.default&&"function"!=typeof r.default||(Object.keys(r).some(function(t){return"default"!==t&&"__esModule"!==t})&&console.error("named exports are not supported in *.vue files."),o=r=r.default),"function"==typeof o&&(o=o.options),o.__file="C:\\work\\visualization\\xiudian1\\src\\product.vue",o.render=a.render,o.staticRenderFns=a.staticRenderFns,o._scopeId="data-v-8190233c",o.style=o.style||{},s.forEach(function(t){for(var e in t)o.style[e]=t[e]}),"function"==typeof __register_static_styles__&&__register_static_styles__(o._scopeId,s),t.exports=r,t.exports.el="true",new Vue(t.exports)},3:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:{productDetail:{name:"【店主福利】日本花王尿不湿NB90片",price:"99.00",originalPrice:"158.00",freight:0,salesVolume:1324},imageList:[{type:"img",src:"https://gd2.alicdn.com/bao/uploaded/i2/T14H1LFwBcXXXXXXXX_!!0-item_pic.jpg"},{type:"img",src:"https://gd1.alicdn.com/bao/uploaded/i1/TB1PXJCJFXXXXciXFXXXXXXXXXX_!!0-item_pic.jpg"},{type:"img",src:"https://gd3.alicdn.com/bao/uploaded/i3/TB1x6hYLXXXXXazXVXXXXXXXXXX_!!0-item_pic.jpg"}]},methods:{update:function(t){this.target="Weex",console.log("target:",this.target)}},components:{}}},8:function(t,e){t.exports={divider:{height:1,width:750,backgroundColor:"#dddddd"},row:{height:30,flexDirection:"row",alignItems:"center"},item:{flex:1,fontSize:24},"product-name":{fontSize:30},price:{fontSize:42,color:"rgb(250,128,10)"},"original-price":{paddingLeft:10,fontSize:30,fontStyle:"italic",textDecoration:"line-through",textAlign:"center"},slider:{width:750,height:750},image:{width:750,height:750},frame:{width:750,height:750,position:"relative"},indicator:{position:"absolute",width:750,height:750,top:350,left:200,itemColor:"#dddddd",itemSelectedColor:"rgb(250, 128, 10)",itemSize:15}}}});