// { "framework": "Vue"} 

!function(e){function t(r){if(n[r])return n[r].exports;var o=n[r]={i:r,l:!1,exports:{}};return e[r].call(o.exports,o,o.exports,t),o.l=!0,o.exports}var n={};t.m=e,t.c=n,t.i=function(e){return e},t.d=function(e,n,r){t.o(e,n)||Object.defineProperty(e,n,{configurable:!1,enumerable:!0,get:r})},t.n=function(e){var n=e&&e.__esModule?function(){return e.default}:function(){return e};return t.d(n,"a",n),n},t.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},t.p="",t(t.s=19)}([function(e,t){e.exports=function(e,t,n,r){var o,s=e=e||{},i=typeof e.default;"object"!==i&&"function"!==i||(o=e,s=e.default);var a="function"==typeof s?s.options:s;if(t&&(a.render=t.render,a.staticRenderFns=t.staticRenderFns),n&&(a._scopeId=n),r){var u=a.computed||(a.computed={});Object.keys(r).forEach(function(e){var t=r[e];u[e]=function(){return t}})}return{esModule:o,exports:s,options:a}}},function(e,t){e.exports=function(){var e=[];return e.toString=function(){for(var e=[],t=0;t<this.length;t++){var n=this[t];n[2]?e.push("@media "+n[2]+"{"+n[1]+"}"):e.push(n[1])}return e.join("")},e.i=function(t,n){"string"==typeof t&&(t=[[null,t,""]]);for(var r={},o=0;o<this.length;o++){var s=this[o][0];"number"==typeof s&&(r[s]=!0)}for(o=0;o<t.length;o++){var i=t[o];"number"==typeof i[0]&&r[i[0]]||(n&&!i[2]?i[2]=n:n&&(i[2]="("+i[2]+") and ("+n+")"),e.push(i))}},e}},function(e,t,n){function r(e){for(var t=0;t<e.length;t++){var n=e[t],r=l[n.id];if(r){r.refs++;for(var o=0;o<r.parts.length;o++)r.parts[o](n.parts[o]);for(;o<n.parts.length;o++)r.parts.push(s(n.parts[o]));r.parts.length>n.parts.length&&(r.parts.length=n.parts.length)}else{for(var i=[],o=0;o<n.parts.length;o++)i.push(s(n.parts[o]));l[n.id]={id:n.id,refs:1,parts:i}}}}function o(){var e=document.createElement("style");return e.type="text/css",p.appendChild(e),e}function s(e){var t,n,r=document.querySelector('style[data-vue-ssr-id~="'+e.id+'"]');if(r){if(h)return v;r.parentNode.removeChild(r)}if(m){var s=f++;r=d||(d=o()),t=i.bind(null,r,s,!1),n=i.bind(null,r,s,!0)}else r=o(),t=a.bind(null,r),n=function(){r.parentNode.removeChild(r)};return t(e),function(r){if(r){if(r.css===e.css&&r.media===e.media&&r.sourceMap===e.sourceMap)return;t(e=r)}else n()}}function i(e,t,n,r){var o=n?"":r.css;if(e.styleSheet)e.styleSheet.cssText=g(t,o);else{var s=document.createTextNode(o),i=e.childNodes;i[t]&&e.removeChild(i[t]),i.length?e.insertBefore(s,i[t]):e.appendChild(s)}}function a(e,t){var n=t.css,r=t.media,o=t.sourceMap;if(r&&e.setAttribute("media",r),o&&(n+="\n/*# sourceURL="+o.sources[0]+" */",n+="\n/*# sourceMappingURL=data:application/json;base64,"+btoa(unescape(encodeURIComponent(JSON.stringify(o))))+" */"),e.styleSheet)e.styleSheet.cssText=n;else{for(;e.firstChild;)e.removeChild(e.firstChild);e.appendChild(document.createTextNode(n))}}var u="undefined"!=typeof document;if("undefined"!=typeof DEBUG&&DEBUG&&!u)throw new Error("vue-style-loader cannot be used in a non-browser environment. Use { target: 'node' } in your Webpack config to indicate a server-rendering environment.");var c=n(3),l={},p=u&&(document.head||document.getElementsByTagName("head")[0]),d=null,f=0,h=!1,v=function(){},m="undefined"!=typeof navigator&&/msie [6-9]\b/.test(navigator.userAgent.toLowerCase());e.exports=function(e,t,n){h=n;var o=c(e,t);return r(o),function(t){for(var n=[],s=0;s<o.length;s++){var i=o[s],a=l[i.id];a.refs--,n.push(a)}t?(o=c(e,t),r(o)):o=[];for(var s=0;s<n.length;s++){var a=n[s];if(0===a.refs){for(var u=0;u<a.parts.length;u++)a.parts[u]();delete l[a.id]}}}};var g=function(){var e=[];return function(t,n){return e[t]=n,e.filter(Boolean).join("\n")}}()},function(e,t){e.exports=function(e,t){for(var n=[],r={},o=0;o<t.length;o++){var s=t[o],i=s[0],a=s[1],u=s[2],c=s[3],l={id:e+":"+o,css:a,media:u,sourceMap:c};r[i]?r[i].parts.push(l):n.push(r[i]={id:i,parts:[l]})}return n}},function(e,t,n){n(49);var r=n(0)(n(14),n(40),"data-v-19400a41",null);r.options.__file="C:\\work\\visualization\\xiudian1\\src\\index.vue",r.esModule&&Object.keys(r.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),r.options.functional&&console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions."),e.exports=r.exports},,,,,,function(e,t,n){"use strict";function r(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}var o="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"==typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},s=n(57);e.exports={props:{type:{type:String,default:"errorPage"},show:{type:Boolean,default:!0},wrapStyle:Object,paddingTop:{type:[Number,String],default:232},customSet:{type:Object,default:function(){return{}}}},computed:{resultType:function(){var e=this.type,t=this.customSet,n=this.isEmptyObject(t)?s:this.mergeDeep(s,t),r=n.errorPage;return["errorPage","noGoods","noNetwork","errorLocation"].indexOf(e)>-1&&(r=n[e]),r},setPaddingTop:function(){return this.paddingTop+"px"}},methods:{handleTouchEnd:function(e){"Web"===weex.config.env.platform&&e.preventDefault&&e.preventDefault()},onClick:function(){var e=this.type;this.$emit("wxcResultButtonClicked",{type:e})},isObject:function(e){return e&&"object"===(void 0===e?"undefined":o(e))&&!Array.isArray(e)},isEmptyObject:function(e){return 0===Object.keys(e).length&&e.constructor===Object},mergeDeep:function(e){for(var t=arguments.length,n=Array(t>1?t-1:0),o=1;o<t;o++)n[o-1]=arguments[o];if(!n.length)return e;var s=n.shift();if(this.isObject(e)&&this.isObject(s))for(var i in s)this.isObject(s[i])?(e[i]||Object.assign(e,r({},i,{})),this.mergeDeep(e[i],s[i])):Object.assign(e,r({},i,s[i]));return this.mergeDeep.apply(this,[e].concat(n))}}}},,,,function(e,t,n){"use strict";var r=n(56),o=function(e){return e&&e.__esModule?e:{default:e}}(r),s=weex.requireModule("modal");e.exports={components:{WxcResult:o.default},data:function(){return{show:!0,type:"errorPage"}},computed:{},created:function(){},methods:{resultButtonClick:function(e){s.toast({message:"你点击了按钮",duration:1})}}}},,,,,function(e,t,n){"use strict";var r=n(4);r.el="#root",new Vue(r)},,,,,,,,function(e,t,n){t=e.exports=n(1)(),t.push([e.i,"\n.wxc-demo[data-v-19400a41] {\n  position: absolute;\n  top: 0;\n  right: 0;\n  left: 0;\n  bottom: 0;\n  background-color: #fff;\n}\n",""])},,,,,,function(e,t,n){t=e.exports=n(1)(),t.push([e.i,"\n.wrap[data-v-9befa6a2] {\n  position: absolute;\n  top: 0;\n  left: 0;\n  right: 0;\n  bottom: 0;\n}\n.wxc-result[data-v-9befa6a2] {\n  width: 750px;\n  flex: 1;\n  align-items: center;\n  background-color: #f2f3f4;\n}\n.result-image[data-v-9befa6a2] {\n  width: 320px;\n  height: 320px;\n}\n.result-content[data-v-9befa6a2] {\n  margin-top: 36px;\n  align-items: center;\n}\n.content-text[data-v-9befa6a2] {\n  font-size: 30px;\n  color: #A5A5A5;\n  height: 42px;\n  line-height: 42px;\n  text-align: center;\n}\n.content-desc[data-v-9befa6a2] {\n  margin-top: 10px;\n}\n.result-button[data-v-9befa6a2] {\n  margin-top: 60px;\n  border-width: 1px;\n  border-color: #979797;\n  background-color: #FFFFFF;\n  border-radius: 6px;\n  width: 240px;\n  height: 72px;\n  flex-direction: row;\n  align-items: center;\n  justify-content: center;\n}\n.button-text[data-v-9befa6a2] {\n  color: #666666;\n  font-size: 30px;\n}\n",""])},function(e,t,n){n(55);var r=n(0)(n(10),n(46),"data-v-9befa6a2",null);r.options.__file="C:\\work\\visualization\\xiudian1\\node_modules\\weex-ui\\packages\\wxc-result\\index.vue",r.esModule&&Object.keys(r.esModule).some(function(e){return"default"!==e&&"__esModule"!==e})&&console.error("named exports are not supported in *.vue files."),r.options.functional&&console.error("[vue-loader] index.vue: functional components are not supported with templates, they should use render functions."),e.exports=r.exports},,,,,,function(e,t,n){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"wxc-demo"},[n("wxc-result",{attrs:{type:e.type,"padding-top":"232","custom-set":e.customSet,show:e.show},on:{wxcResultButtonClicked:e.resultButtonClick}})],1)},staticRenderFns:[]},e.exports.render._withStripped=!0},,,,,,function(e,t,n){e.exports={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return e.show?n("div",{staticClass:"wrap",style:e.wrapStyle},[n("div",{staticClass:"wxc-result",style:{paddingTop:e.setPaddingTop}},[n("image",{staticClass:"result-image",attrs:{src:e.resultType.pic}}),e._v(" "),e.resultType.content?n("div",{staticClass:"result-content"},[n("text",{staticClass:"content-text"},[e._v(e._s(e.resultType.content))]),e._v(" "),e.resultType.desc?n("text",{staticClass:"content-text content-desc"},[e._v(e._s(e.resultType.desc))]):e._e()]):e._e(),e._v(" "),e.resultType.button?n("div",{staticClass:"result-button",on:{touchend:e.handleTouchEnd,click:e.onClick}},[n("text",{staticClass:"button-text"},[e._v(e._s(e.resultType.button))])]):e._e()])]):e._e()},staticRenderFns:[]},e.exports.render._withStripped=!0},,,function(e,t,n){var r=n(27);"string"==typeof r&&(r=[[e.i,r,""]]),r.locals&&(e.exports=r.locals);n(2)("1f27ed8b",r,!1)},,,,,,function(e,t,n){var r=n(33);"string"==typeof r&&(r=[[e.i,r,""]]),r.locals&&(e.exports=r.locals);n(2)("4ff1beac",r,!1)},function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=n(34),o=n.n(r);n.d(t,"default",function(){return o.a})},function(e,t){e.exports={errorPage:{pic:"//gtms01.alicdn.com/tfs/TB1HH4TSpXXXXauXVXXXXXXXXXX-320-320.png",content:"抱歉出错了，飞猪正在全力解决中",button:"再试一次",title:"出错啦"},noGoods:{pic:"//gw.alicdn.com/tfs/TB1QXlEQXXXXXcNXFXXXXXXXXXX-320-320.png",content:"主人，这里什么都没有找到",button:"再试一次",title:"暂无商品"},noNetwork:{pic:"//gw.alicdn.com/tfs/TB1rs83QXXXXXcBXpXXXXXXXXXX-320-320.png",content:"哎呀，没有网络了......",button:"刷新一下",title:"无网络"},errorLocation:{pic:"//gw.alicdn.com/tfs/TB1rs83QXXXXXcBXpXXXXXXXXXX-320-320.png",content:"哎呀，定位失败了......",button:"刷新一下",title:"定位失败"}}}]);