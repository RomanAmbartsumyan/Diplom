(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["addText"],{"42ff":function(t,e,s){"use strict";var n=s("4f50"),i=s.n(n);i.a},"4f50":function(t,e,s){},ce13:function(t,e,s){"use strict";s.r(e);var n=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"AddText",class:t.className},[s("div",{staticClass:"AddText-Edit"},[s("Vueditor",{ref:"editor"})],1)])},i=[],o={props:{className:{type:String,required:!1}},computed:{shouldSendComment:function(){return this.$store.getters.shouldSendComment}},watch:{shouldSendComment:function(){if(this.shouldSendComment){var t=this.$refs.editor.getContent();this.$emit("comment-is-send",t),this.$refs.editor.setContent("")}}}},d=o,r=(s("42ff"),s("2877")),c=Object(r["a"])(d,n,i,!1,null,null,null);e["default"]=c.exports}}]);
//# sourceMappingURL=addText.207bea4b.js.map