(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["moderationBlock"],{2007:function(t,e,i){"use strict";var o=i("c201"),c=i.n(o);c.a},"32d2":function(t,e,i){"use strict";i.r(e);var o=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"ModerationBlock",class:t.className},[i("div",{staticClass:"ModerationBlock-Section"},[i("router-link",{staticClass:"ModerationBlock-Link",attrs:{to:{name:"edit",params:{id:""+t.id}}}},[t._v(" Редактировать ")])],1),i("div",{staticClass:"ModerationBlock-Section"},[i("div",{staticClass:"ModerationBlock-Link ModerationBlock-Decline",on:{click:t.onDeclne}},[t._v(" Отклонить ")]),i("div",{staticClass:"ModerationBlock-Link",on:{click:t.onAccept}},[t._v(" Утвердить ")])])])},c=[],n=(i("a9e3"),{props:{className:{type:String,required:!1},id:{type:Number,required:!0}},methods:{onDeclne:function(){this.$emit("moderated","decline")},onAccept:function(){this.$emit("moderated","accept")}}}),a=n,s=(i("2007"),i("2877")),l=Object(s["a"])(a,o,c,!1,null,null,null);e["default"]=l.exports},c201:function(t,e,i){}}]);
//# sourceMappingURL=moderationBlock.7a3a4d30.js.map