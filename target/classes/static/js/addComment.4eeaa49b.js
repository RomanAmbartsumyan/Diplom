(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["addComment"],{"2cab":function(t,n,e){},"9cf9":function(t,n,e){"use strict";var o=e("2cab"),d=e.n(o);d.a},d6de:function(t,n,e){"use strict";e.r(n);var o=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",{staticClass:"AddComment"},[t.isReply?t._e():e("div",{staticClass:"Title AddComment-Title"},[t._v(" Добавить комментарий ")]),e("AddText",{attrs:{className:"AddComment-Edit"},on:{"comment-is-send":t.onSendComment}}),e("div",{staticClass:"AddComment-Send"},[e("BaseButton",{attrs:{onClickButton:t.onShouldSendComment}},[t._v(" Отправить ")])],1)],1)},d=[],s=(e("d3b7"),function(){return e.e("addText").then(e.bind(null,"ce13"))}),i=function(){return e.e("baseButton").then(e.bind(null,"82ea"))},m={components:{AddText:s,BaseButton:i},props:{isReply:{type:Boolean,required:!1,default:!1}},methods:{onShouldSendComment:function(){this.$store.commit("sendComment")},onSendComment:function(t){this.$emit("comment-is-send",t)}}},a=m,c=(e("9cf9"),e("2877")),u=Object(c["a"])(a,o,d,!1,null,null,null);n["default"]=u.exports}}]);
//# sourceMappingURL=addComment.4eeaa49b.js.map