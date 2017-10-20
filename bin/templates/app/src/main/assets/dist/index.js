// { "framework": "Vue"} 

!function(t){function e(o){if(n[o])return n[o].exports;var r=n[o]={i:o,l:!1,exports:{}};return t[o].call(r.exports,r,r.exports,e),r.l=!0,r.exports}var n={};e.m=t,e.c=n,e.i=function(t){return t},e.d=function(t,n,o){e.o(t,n)||Object.defineProperty(t,n,{configurable:!1,enumerable:!0,get:o})},e.n=function(t){var n=t&&t.__esModule?function(){return t.default}:function(){return t};return e.d(n,"a",n),n},e.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},e.p="",e(e.s=22)}({0:function(t,e,n){"use strict";var o=n(29),r=function(t){return t&&t.__esModule?t:{default:t}}(o),s=weex.requireModule("modal"),i=weex.requireModule("xdnavigator"),c=(weex.requireModule("xdevent"),weex.requireModule("xdshare"));t.exports={components:{WxcResult:r.default},data:function(){return{show:!0,type:"errorPage",src:"http://flv2.bn.netease.com/videolib3/1611/01/XGqSL5981/SD/XGqSL5981-mobile.mp4"}},computed:{},created:function(){},methods:{jump:function(t){console.log("will jump"),i.push({url:"http://dotwe.org/raw/dist/dc980b84437ec8f079fd2981e9f67d6a.bundle.wx",animated:"true"},function(t){s.toast({message:"callback: "+t,duration:1})})},openUrl:function(){c.share()},resultButtonClick:function(t){s.toast({message:"你点击了按钮",duration:1})}}}},15:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:["wxc-demo"]},[n("xdvideo",{staticClass:["xdvideo"],attrs:{src:t.src}}),n("button",{staticClass:["button-style"],on:{click:t.jump}},[t._v("jump")]),n("button",{staticClass:["button-style"],on:{click:t.openUrl}},[t._v("openUrl")])],1)},staticRenderFns:[]},t.exports.render._withStripped=!0},18:function(t,e,n){var o,r,s=[];s.push(n(39)),o=n(33);var i=n(43);r=o=o||{},"object"!=typeof o.default&&"function"!=typeof o.default||(Object.keys(o).some(function(t){return"default"!==t&&"__esModule"!==t})&&console.error("named exports are not supported in *.vue files."),r=o=o.default),"function"==typeof r&&(r=r.options),r.__file="C:\\work\\visualization\\xiudian1\\node_modules\\weex-ui\\packages\\wxc-result\\index.vue",r.render=i.render,r.staticRenderFns=i.staticRenderFns,r._scopeId="data-v-321ef984",r.style=r.style||{},s.forEach(function(t){for(var e in t)r.style[e]=t[e]}),"function"==typeof __register_static_styles__&&__register_static_styles__(r._scopeId,s),t.exports=o},22:function(t,e,n){var o,r,s=[];s.push(n(9)),o=n(0);var i=n(15);r=o=o||{},"object"!=typeof o.default&&"function"!=typeof o.default||(Object.keys(o).some(function(t){return"default"!==t&&"__esModule"!==t})&&console.error("named exports are not supported in *.vue files."),r=o=o.default),"function"==typeof r&&(r=r.options),r.__file="C:\\work\\visualization\\xiudian1\\src\\index.vue",r.render=i.render,r.staticRenderFns=i.staticRenderFns,r._scopeId="data-v-43707b85",r.style=r.style||{},s.forEach(function(t){for(var e in t)r.style[e]=t[e]}),"function"==typeof __register_static_styles__&&__register_static_styles__(r._scopeId,s),t.exports=o,t.exports.el="true",new Vue(t.exports)},29:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=n(18),r=n.n(o);n.d(e,"default",function(){return r.a})},30:function(t,e){t.exports={errorPage:{pic:"//gtms01.alicdn.com/tfs/TB1HH4TSpXXXXauXVXXXXXXXXXX-320-320.png",content:"抱歉出错了，飞猪正在全力解决中",button:"再试一次",title:"出错啦"},noGoods:{pic:"//gw.alicdn.com/tfs/TB1QXlEQXXXXXcNXFXXXXXXXXXX-320-320.png",content:"主人，这里什么都没有找到",button:"再试一次",title:"暂无商品"},noNetwork:{pic:"//gw.alicdn.com/tfs/TB1rs83QXXXXXcBXpXXXXXXXXXX-320-320.png",content:"哎呀，没有网络了......",button:"刷新一下",title:"无网络"},errorLocation:{pic:"//gw.alicdn.com/tfs/TB1rs83QXXXXXcBXpXXXXXXXXXX-320-320.png",content:"哎呀，定位失败了......",button:"刷新一下",title:"定位失败"}}},33:function(t,e,n){"use strict";function o(t,e,n){return e in t?Object.defineProperty(t,e,{value:n,enumerable:!0,configurable:!0,writable:!0}):t[e]=n,t}var r="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},s=n(30);t.exports={props:{type:{type:String,default:"errorPage"},show:{type:Boolean,default:!0},wrapStyle:Object,paddingTop:{type:[Number,String],default:232},customSet:{type:Object,default:function(){return{}}}},computed:{resultType:function(){var t=this.type,e=this.customSet,n=this.isEmptyObject(e)?s:this.mergeDeep(s,e),o=n.errorPage;return["errorPage","noGoods","noNetwork","errorLocation"].indexOf(t)>-1&&(o=n[t]),o},setPaddingTop:function(){return this.paddingTop+"px"}},methods:{handleTouchEnd:function(t){"Web"===weex.config.env.platform&&t.preventDefault&&t.preventDefault()},onClick:function(){var t=this.type;this.$emit("wxcResultButtonClicked",{type:t})},isObject:function(t){return t&&"object"===(void 0===t?"undefined":r(t))&&!Array.isArray(t)},isEmptyObject:function(t){return 0===Object.keys(t).length&&t.constructor===Object},mergeDeep:function(t){for(var e=arguments.length,n=Array(e>1?e-1:0),r=1;r<e;r++)n[r-1]=arguments[r];if(!n.length)return t;var s=n.shift();if(this.isObject(t)&&this.isObject(s))for(var i in s)this.isObject(s[i])?(t[i]||Object.assign(t,o({},i,{})),this.mergeDeep(t[i],s[i])):Object.assign(t,o({},i,s[i]));return this.mergeDeep.apply(this,[t].concat(n))}}}},39:function(t,e){t.exports={wrap:{position:"absolute",top:0,left:0,right:0,bottom:0},"wxc-result":{width:750,flex:1,alignItems:"center",backgroundColor:"#f2f3f4"},"result-image":{width:320,height:320},"result-content":{marginTop:36,alignItems:"center"},"content-text":{fontSize:30,color:"#A5A5A5",height:42,lineHeight:42,textAlign:"center"},"content-desc":{marginTop:10},"result-button":{marginTop:60,borderWidth:1,borderColor:"#979797",backgroundColor:"#FFFFFF",borderRadius:6,width:240,height:72,flexDirection:"row",alignItems:"center",justifyContent:"center"},"button-text":{color:"#666666",fontSize:30}}},43:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return t.show?n("div",{staticClass:["wrap"],style:t.wrapStyle},[n("div",{staticClass:["wxc-result"],style:{paddingTop:t.setPaddingTop}},[n("image",{staticClass:["result-image"],attrs:{src:t.resultType.pic}}),t.resultType.content?n("div",{staticClass:["result-content"]},[n("text",{staticClass:["content-text"]},[t._v(t._s(t.resultType.content))]),t.resultType.desc?n("text",{staticClass:["content-text","content-desc"]},[t._v(t._s(t.resultType.desc))]):t._e()]):t._e(),t.resultType.button?n("div",{staticClass:["result-button"],on:{touchend:t.handleTouchEnd,click:t.onClick}},[n("text",{staticClass:["button-text"]},[t._v(t._s(t.resultType.button))])]):t._e()])]):t._e()},staticRenderFns:[]},t.exports.render._withStripped=!0},9:function(t,e){t.exports={"button-style":{fontSize:30,margin:60},"wxc-demo":{position:"absolute",top:0,right:0,left:0,bottom:0,backgroundColor:"#ffffff"},xdvideo:{width:630,height:350,marginTop:60,marginLeft:60}}}});