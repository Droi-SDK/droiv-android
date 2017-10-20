// { "framework": "Vue"} 

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 21);
/******/ })
/************************************************************************/
/******/ ({

/***/ 11:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["wxc-demo"]
  })
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 16:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(34)
)

/* script */
__vue_exports__ = __webpack_require__(30)

/* template */
var __vue_template__ = __webpack_require__(38)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "C:\\work\\visualization\\xiudian1\\node_modules\\weex-ui\\packages\\wxc-slider-bar\\index.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-1d3b6ce4"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__


/***/ }),

/***/ 2:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _wxcSliderBar = __webpack_require__(27);

var _wxcSliderBar2 = _interopRequireDefault(_wxcSliderBar);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  components: { WxcSliderBar: _wxcSliderBar2.default },
  data: function data() {
    return {
      barValue: 0,
      minValue: 0,
      maxValue: 0,
      sliderBarCfg1: {
        length: 400,
        range: false,
        min: 0,
        max: 10000,
        value: 2000,
        defaultValue: 50,
        disabled: false
      },
      sliderBarCfg2: {
        length: 500,
        range: true,
        min: 0,
        max: 100,
        value: [20, 70],
        defaultValue: [30, 60],
        disabled: false
      },
      sliderBarCfg3: {
        length: 400,
        range: false,
        min: 0,
        max: 200,
        value: 50,
        defaultValue: 100,
        disabled: true
      },
      sliderBarCfg4: {
        length: 500,
        range: true,
        min: 0,
        max: 200,
        value: 20,
        defaultValue: [20, 70],
        disabled: true
      }
    };
  },
  created: function created() {
    //setTitle('SliderBar');
    this.barValue = this.sliderBarCfg1.value || this.sliderBarCfg1.defaultValue;
    this.minValue = this.sliderBarCfg2.value[0] || this.sliderBarCfg1.defaultValue[0];
    this.maxValue = this.sliderBarCfg2.value[1] || this.sliderBarCfg1.defaultValue[1];
  },

  methods: {
    updateValue: function updateValue(value) {
      if (typeof value === 'number') {
        this.barValue = value;
      } else if (value.length && value.length === 2) {
        this.minValue = value[0];
        this.maxValue = value[1];
      }
    }
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),

/***/ 21:
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(6)
)

/* script */
__vue_exports__ = __webpack_require__(2)

/* template */
var __vue_template__ = __webpack_require__(11)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "C:\\work\\visualization\\xiudian1\\src\\index2.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-2ab69e7d"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__
module.exports.el = 'true'
new Vue(module.exports)


/***/ }),

/***/ 27:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__index_vue__ = __webpack_require__(16);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__index_vue__);
/* harmony reexport (default from non-hamory) */ __webpack_require__.d(__webpack_exports__, "default", function() { return __WEBPACK_IMPORTED_MODULE_0__index_vue___default.a; });


/***/ }),

/***/ 28:
/***/ (function(module, exports) {

/**
 * 工具方法库
 * @namespace Utils
 * @example
 *
 */
const Utils = {

  env: {

    /**
     * 是否是手淘容器
     * @method
     * @memberOf Utils.env
     * @returns {boolean}
     * @example
     *
     * const isTaobao = env.isTaobao();
     */
    isTaobao () {
      let { appName } = weex.config.env;
      return /(tb|taobao|淘宝)/i.test(appName);
    },


    /**
     * 是否是旅客容器
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isTrip = env.isTrip();
     */
    isTrip () {
      let { appName } = weex.config.env;
      return appName === 'LX';
    },
    /**
     * 是否是 web 环境
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isWeb = env.isWeb();
     */
    isWeb () {
      let { platform } = weex.config.env;
      return typeof(window) === 'object' && platform.toLowerCase() === 'web';
    },
    /**
     * 是否是 iOS 系统
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isIOS = env.isIOS();
     */
    isIOS () {
      let { platform } = weex.config.env;
      return platform.toLowerCase() === 'ios';
    },
    /**
     * 是否是 Android 系统
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isAndroid = env.isAndroid();
     */
    isAndroid () {
      let { platform } = weex.config.env;
      return platform.toLowerCase() === 'android';
    },

    /**
     * 是否是支付宝容器
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isAlipay = env.isAlipay();
     */
    isAlipay () {
      let { appName } = weex.config.env;
      return appName === 'AP';
    },

    /**
     * 是否是支付宝H5容器(防止以后支付宝接入weex)
     * @memberOf Utils.env
     * @method
     * @returns {boolean}
     * @example
     *
     * const isAlipayWeb = env.isAlipayWeb();
     */
    isAlipayWeb () {
      return Utils.env.isAlipay() && Utils.env.isWeb();
    },


    /**
     * 判断是否支持expressionBinding
     * 当weex版本大于0.10.1.6，为客户端即可以支持expressionBinding
     * @returns {Boolean}
     */
    supportsEB () {
      const weexVersion = weex.config.env.weexVersion || '0';
      const isHighWeex = Utils.compareVersion(weexVersion, '0.10.1.4') && (Utils.env.isIOS() || Utils.env.isAndroid());
      const expressionBinding = weex.requireModule('expressionBinding');
      return expressionBinding && expressionBinding.enableBinding && isHighWeex;
    },

    /**
     * 判断Android容器是否支持是否支持expressionBinding(处理方式很不一致)
     * @returns {boolean}
     */
    supportsEBForAndroid () {
      return (Utils.env.isAndroid()) && Utils.env.supportsEB();
    },

    /**
     * 判断IOS容器是否支持是否支持expressionBinding
     * @returns {boolean}
     */
    supportsEBForIos () {
      return (Utils.env.isIOS()) && Utils.env.supportsEB();
    },

    /**
     * 获取weex屏幕真实的设置高度，需要减去导航栏高度
     * @returns {Number}
     */
    getPageHeight () {
      const { env } = weex.config;
      const navHeight = Utils.env.isWeb() ? 0 : 130;
      return env.deviceHeight / env.deviceWidth * 750 - navHeight;
    }
  },

  /**
   * 版本号比较
   * @memberOf Utils
   * @param currVer {string}
   * @param promoteVer {string}
   * @returns {boolean}
   * @example
   *
   * const { compareVersion } = Utils;
   * console.log(compareVersion('0.1.100', '0.1.11')); // 'true'
   */
  compareVersion (currVer = "0.0.0", promoteVer = "0.0.0") {
    if (currVer === promoteVer) return true;
    const currVerArr = currVer.split(".");
    const promoteVerArr = promoteVer.split(".");
    const len = Math.max(currVerArr.length, promoteVerArr.length);
    for (let i = 0; i < len; i++) {
      let proVal = ~~promoteVerArr[i];
      let curVal = ~~currVerArr[i];
      if (proVal < curVal) {
        return true;
      } else if (proVal > curVal) {
        return false;
      }
    }
    return false;
  }
};

module.exports = Utils;


/***/ }),

/***/ 30:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var Utils = __webpack_require__(28);
var EB = weex.requireModule('expressionBinding');
var animation = weex.requireModule('animation');
var dom = weex.requireModule('dom');

exports.default = {
  data: function data() {
    return {
      env: 'weex',
      diffX1: 0,
      diffX2: 0,
      barWidth: 0,
      preventMoveEvent: true,
      timeout: 100,
      minDiffX: 0,
      selectRange: [0, 0]
    };
  },
  props: {
    length: {
      type: Number,
      default: 500
    },
    height: {
      type: Number,
      default: 4
    },
    // 是否双滑块模式
    range: {
      type: Boolean,
      default: false
    },
    // 最小值
    min: {
      type: Number,
      default: 0
    },
    // 最大值
    max: {
      type: Number,
      default: 100
    },
    // 最小取值范围，用于范围选择范围最小差值
    minDiff: {
      type: Number,
      default: 5
    },
    // 设置当前取值。当 range 为 false 时，使用 number，否则用 [number, number]
    value: {
      type: [Number, Array],
      default: 0
    },
    // 设置初始取值。当 range 为 false 时，使用 number，否则用 [number, number]
    defaultValue: {
      type: [Number, Array],
      default: 0
    },
    // 值为 true 时，滑块为禁用状态
    disabled: {
      type: Boolean,
      default: false
    },
    invalidColor: {
      type: String,
      default: '#E0E0E0'
    },
    validColor: {
      type: String,
      default: '#EE9900'
    },
    disabledColor: {
      type: String,
      default: '#AAA'
    }
  },
  created: function created() {
    if (Utils.env.isWeb()) {
      this.env = 'web';
    }
  },
  mounted: function mounted() {
    var _this = this;

    this.block1 = this.$refs['slide-block-1']; // 左侧滑块
    this.block2 = this.$refs['slide-block-2']; // 右侧滑块
    this.valueBar = this.$refs['value-bar']; // 黄色值条
    this.barContainer = this.$refs['bar-container']; // 滚动条容器

    if (!this.range) {
      this.diffX1 = this._getDiffX(this.value || this.defaultValue);
    } else {
      this.diffX1 = this._getDiffX(this.value[0] || this.defaultValue[0]);
      this.diffX2 = this._getDiffX(this.value[1] || this.defaultValue[1]);
      this.barWidth = this.diffX2 - this.diffX1;
    }
    // 是否支持expresstionBinding
    if (Utils.env.supportsEB()) {
      this.block1 && EB.enableBinding(this.block1.ref, 'pan');
      this.block2 && EB.enableBinding(this.block2.ref, 'pan');
      this.valueBar && EB.enableBinding(this.valueBar.ref, 'pan');
    }
    if (Utils.env.isAndroid()) {
      this.timeout = 250;
    }
    if (this.range) {
      this.selectRange = this.value || this.defaultValue; // 初始化范围选择返回数据
      this.minDiffX = this.minDiff / (this.max - this.min) * this.length; // 滑块1、2之前最小间距
    }
    // 由于weex在mounted后渲染是异步的不能确保元素渲染完成，需要异步执行
    setTimeout(function () {
      dom.getComponentRect(_this.barContainer, function (option) {
        var left = option.size.left;

        _this.leftDiffX = left;
      });
    }, 100);

    this.bindExp();
  },

  computed: {
    containerStyle: function containerStyle() {
      return {
        width: this.length + 56 + 'px',
        height: '56px'
      };
    },
    rangeBarStyle: function rangeBarStyle() {
      return {
        width: this.length + 'px',
        height: this.height + 'px',
        flexDirection: 'row',
        backgroundColor: this.invalidColor,
        overflow: 'hidden'
      };
    },
    valueBarStyle: function valueBarStyle() {
      var left = 0;
      var width = 0;
      if (!this.range) {
        width = this.diffX1;
      } else {
        left = this.diffX1;
        width = this.diffX2 - this.diffX1;
      }
      return {
        width: width + 'px',
        height: this.height + 'px',
        transform: 'translateX(' + left + 'px)',
        backgroundColor: this.disabled ? this.disabledColor : this.validColor
      };
    },
    blockStyle1: function blockStyle1() {
      return {
        transform: 'translateX(' + this.diffX1 + 'px)'
      };
    },
    blockStyle2: function blockStyle2() {
      return {
        transform: 'translateX(' + this.diffX2 + 'px)'
      };
    }
  },
  methods: {
    dispatchPan: function dispatchPan() {},


    // 更新单选值或最小值
    _weexStartHandler1: function _weexStartHandler1() {
      var _this2 = this;

      this.firstInterval = setInterval(function () {
        if (!_this2.range) {
          dom.getComponentRect(_this2.valueBar, function (option) {
            var width = option.size.width;

            var value = _this2._getValue(width);
            _this2.$emit('updateValue', value);
          });
        } else {
          dom.getComponentRect(_this2.block1, function (option) {
            var left = option.size.left;

            _this2.selectRange[0] = _this2._getValue(left - _this2.leftDiffX);
            _this2.$emit('updateValue', _this2.selectRange);
          });
        }
      }, this.timeout);
    },


    // 更新最大值
    _weexStartHandler2: function _weexStartHandler2() {
      var _this3 = this;

      this.secondInterval = setInterval(function () {
        dom.getComponentRect(_this3.block2, function (option) {
          var left = option.size.left;

          _this3.selectRange[1] = _this3._getValue(left - _this3.leftDiffX);
          _this3.$emit('updateValue', _this3.selectRange);
        });
      }, this.timeout);
    },


    // 清除定时器
    _weexEndHandler: function _weexEndHandler() {
      this.firstInterval && clearInterval(this.firstInterval);
      this.secondInterval && clearInterval(this.secondInterval);
    },
    _webStartHandler: function _webStartHandler(e) {
      if (this.env === 'weex') {
        return;
      }
      this.startX = e.touch.clientX;
      this.startDiffX1 = this.diffX1;
      this.startDiffX2 = this.diffX2;
    },
    _webMoveHandler1: function _webMoveHandler1(e) {
      if (this.env === 'weex' || this.disabled) {
        return;
      }
      var deltaX = e.touch.clientX - this.startX;
      var diff = this.startDiffX1 + deltaX;
      var max = this.length;
      if (this.range) {
        max = this.diffX2 - this.minDiffX;
      }
      if (diff > 0 && diff < max) {
        this.diffX1 = diff;
        animation.transition(this.block1, {
          styles: {
            transform: 'translateX(' + this.diffX1 + 'px)'
          }
        }, function () {});
        if (!this.range) {
          this.$emit('updateValue', this._getValue(this.diffX1));
        } else {
          this.selectRange[0] = this._getValue(this.diffX1);
          this.$emit('updateValue', this.selectRange);
        }
      }
    },
    _webMoveHandler2: function _webMoveHandler2(e) {
      if (this.env === 'weex' || this.disabled) {
        return;
      }
      var deltaX = e.touch.clientX - this.startX;
      var diff = this.startDiffX2 + deltaX;
      var min = this.diffX1 + this.minDiffX;
      var max = this.length;
      if (diff > min && diff < max) {
        this.diffX2 = diff;
        animation.transition(this.block2, {
          styles: {
            transform: 'translateX(' + this.diffX2 + 'px)'
          }
        }, function () {});
        if (!this.range) {
          this.$emit('updateValue', this._getValue(this.diffX2));
        } else {
          this.selectRange[1] = this._getValue(this.diffX2);
          this.$emit('updateValue', this.selectRange);
        }
      }
    },
    bindExp: function bindExp() {
      var self = this;

      // 如果禁用，不行进行表达式绑定
      if (self.disabled) {
        return;
      }

      // 初始化按钮&条的大小范围
      var blockMax1 = 0;
      if (self.range) {
        blockMax1 = self.diffX2 - self.minDiffX;
      } else {
        blockMax1 = self.length;
      }

      var blockMax2 = self.length;
      var blockMin2 = self.diffX1 + self.minDiffX;
      var barMax1 = self.diffX2;
      var barMax2 = self.length - self.diffX1;

      // 滑块1表达式
      var expBlock1 = '{"type":"CallExpression","children":[{"type":"Identifier","value":"min"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":' + blockMax1 + '},{"type":"CallExpression","children":[{"type":"Identifier","value":"max"},{"type":"Arguments","children":[{"type":"+","children":[{"type":"Identifier","value":"x"},{"type":"NumericLiteral","value":' + self.diffX1 + '}]},{"type":"NumericLiteral","value":0}]}]}]}]}';
      // 滑块2表达式
      var expBlock2 = '{"type":"CallExpression","children":[{"type":"Identifier","value":"min"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":' + blockMax2 + '},{"type":"CallExpression","children":[{"type":"Identifier","value":"max"},{"type":"Arguments","children":[{"type":"+","children":[{"type":"Identifier","value":"x"},{"type":"NumericLiteral","value":' + self.diffX2 + '}]},{"type":"NumericLiteral","value":' + blockMin2 + '}]}]}]}]}';
      // valuebar表达式
      var expBar1 = '{"type":"CallExpression","children":[{"type":"Identifier","value":"min"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":' + barMax1 + '},{"type":"CallExpression","children":[{"type":"Identifier","value":"max"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":0},{"type":"-","children":[{"type":"NumericLiteral","value":' + self.barWidth + '},{"type":"Identifier","value":"x"}]}]}]}]}]}';
      // valuebar 范围表达式
      var expBar2 = '{"type":"CallExpression","children":[{"type":"Identifier","value":"min"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":' + barMax2 + '},{"type":"CallExpression","children":[{"type":"Identifier","value":"max"},{"type":"Arguments","children":[{"type":"NumericLiteral","value":0},{"type":"+","children":[{"type":"NumericLiteral","value":' + self.barWidth + '},{"type":"Identifier","value":"x"}]}]}]}]}]}';

      if (!self.range) {
        // 单选
        var args = [{
          element: self.block1.ref,
          property: 'transform.translateX',
          expression: expBlock1
        }, {
          element: self.valueBar.ref,
          property: 'width',
          expression: expBlock1
        }];
        EB && EB.createBinding(self.block1.ref, 'pan', '', args, function (e) {
          if (e.state === 'end') {
            var range = self.getRange();
            // 限制diffX1范围
            self.diffX1 = self._restrictValue(range.rangeX1, self.diffX1 + e.deltaX);
            self.bindExp();
          }
        });
      } else {
        // 选范围
        var _args = [{
          element: self.block1.ref,
          property: 'transform.translateX',
          expression: expBlock1
        }, {
          element: self.valueBar.ref,
          property: 'transform.translateX',
          expression: expBlock1
        }, {
          element: self.valueBar.ref,
          property: 'width',
          expression: expBar1
        }];

        var args2 = [{
          element: self.block2.ref,
          property: 'transform.translateX',
          expression: expBlock2
        }, {
          element: self.valueBar.ref,
          property: 'width',
          expression: expBar2
        }];

        EB && EB.createBinding(self.block1.ref, 'pan', '', _args, function (e) {
          if (e.state === 'end') {
            var range = self.getRange();
            self.barWidth = self._restrictValue(range.rangeX1, self.barWidth - e.deltaX);
            self.diffX1 = self._restrictValue(range.rangeX1, self.diffX1 + e.deltaX);
            self.bindExp();
          }
        });

        EB && EB.createBinding(self.block2.ref, 'pan', '', args2, function (e) {
          if (e.state === 'end') {
            var range = self.getRange();
            self.diffX2 = self._restrictValue(range.rangeX2, self.diffX2 + e.deltaX);
            self.barWidth = self._restrictValue([0, self.length - self.diffX1], self.barWidth + e.deltaX);
            self.bindExp();
          }
        });
      }
    },


    // 获取diffx1 diffx2 取值范围
    getRange: function getRange(deltaX) {
      if (!this.range) {
        return {
          rangeX1: [0, this.length]
        };
      } else {
        return {
          rangeX1: [0, this.diffX2 - this.minDiffX],
          rangeX2: [this.diffX1 + this.minDiffX, this.length]
        };
      }
    },


    // 限制取值范围
    _restrictValue: function _restrictValue(range, value) {
      if (range && range.length && range.length === 2) {
        if (value < range[0]) {
          return range[0];
        } else if (value > range[1]) {
          return range[1];
        } else {
          return value;
        }
      }
      return;
    },


    // 根据x方向偏移量计算value
    _getValue: function _getValue(diffX) {
      return Math.round(diffX / this.length * (this.max - this.min) + this.min);
    },


    // 根据value和length计算x方向偏移值
    _getDiffX: function _getDiffX(value) {
      return (value - this.min) / (this.max - this.min) * this.length;
    }
  }
};

/***/ }),

/***/ 34:
/***/ (function(module, exports) {

module.exports = {
  "wxc-slider-bar": {
    "userSelect": "none"
  },
  "slider-bar-container": {
    "height": 56,
    "display": "flex",
    "justifyContent": "center",
    "alignItems": "center"
  },
  "value-bar": {
    "height": 4
  },
  "slide-block": {
    "width": 56,
    "height": 56,
    "backgroundColor": "#ffffff",
    "borderRadius": 28,
    "borderWidth": 1,
    "borderColor": "rgba(0,0,0,0.1)",
    "boxShadow": "0 6px 12px rgba(0, 0, 0, 0.05)",
    "position": "absolute",
    "left": 0,
    "bottom": 0
  }
}

/***/ }),

/***/ 38:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: ["wxc-slider-bar"]
  }, [_c('div', {
    ref: "bar-container",
    staticClass: ["slider-bar-container"],
    style: _vm.containerStyle
  }, [_c('div', {
    staticClass: ["range-bar"],
    style: _vm.rangeBarStyle
  }, [_c('div', {
    ref: "value-bar",
    staticClass: ["value-bar"],
    style: _vm.valueBarStyle
  }, [_c('div')])]), _c('div', {
    ref: "slide-block-1",
    staticClass: ["slide-block"],
    style: _vm.blockStyle1,
    attrs: {
      "preventMoveEvent": _vm.preventMoveEvent
    },
    on: {
      "touchstart": _vm._weexStartHandler1,
      "panstart": _vm._webStartHandler,
      "panmove": _vm._webMoveHandler1,
      "touchend": _vm._weexEndHandler,
      "horizontalpan": _vm.dispatchPan
    }
  }, [_c('div')]), (_vm.range) ? _c('div', {
    ref: "slide-block-2",
    staticClass: ["slide-block"],
    style: _vm.blockStyle2,
    attrs: {
      "preventMoveEvent": _vm.preventMoveEvent
    },
    on: {
      "touchstart": _vm._weexStartHandler2,
      "panstart": _vm._webStartHandler,
      "panmove": _vm._webMoveHandler2,
      "touchend": _vm._weexEndHandler,
      "horizontalpan": _vm.dispatchPan
    }
  }, [_c('div')]) : _vm._e()])])
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ }),

/***/ 6:
/***/ (function(module, exports) {

module.exports = {
  "wxc-demo": {
    "position": "absolute",
    "top": 0,
    "bottom": 0,
    "left": 0,
    "right": 0,
    "backgroundColor": "#FFFFFF"
  },
  "scroller": {
    "flex": 1
  },
  "wrapper": {
    "alignItems": "center",
    "marginTop": 60
  },
  "title": {
    "fontSize": 48,
    "color": "#0072DB",
    "borderBottomWidth": 1,
    "borderBottomStyle": "solid",
    "borderBottomColor": "#0072DB",
    "marginBottom": 30
  },
  "label": {
    "color": "#999999",
    "fontSize": 26,
    "marginBottom": 10
  },
  "value-text": {
    "color": "#999999",
    "fontSize": 26,
    "marginBottom": 10
  },
  "demo-container": {
    "marginBottom": 70,
    "width": 600
  }
}

/***/ })

/******/ });