(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["mainPage"],{"08fb":function(e,t,a){},4385:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("main",{staticClass:"MainPage Wrapper"},[a("Articles",{attrs:{navItems:e.navItems,className:"MainPage-Articles",tagSelected:e.tagSelected,postByDate:e.postByDate}}),a("Tags",{attrs:{className:"MainPage-Tags"},on:{"select-tag":e.onClickTag}})],1)},s=[],c=(a("d3b7"),function(){return a.e("articles").then(a.bind(null,"6de4"))}),l=function(){return a.e("tags").then(a.bind(null,"3855"))},r={name:"mainPage",components:{Articles:c,Tags:l},data:function(){return{navItems:[{name:"Новые",value:"recent"},{name:"Самые обсуждаемые",value:"popular"},{name:"Лучшие",value:"best"},{name:"Старые",value:"early"}],tagSelected:""}},computed:{postByDate:function(){return this.$route.params.date?this.$route.params.date:""}},methods:{onClickTag:function(e){this.tagSelected=e}}},i=r,u=(a("b49c"),a("2877")),o=Object(u["a"])(i,n,s,!1,null,null,null);t["default"]=o.exports},b49c:function(e,t,a){"use strict";var n=a("08fb"),s=a.n(n);s.a}}]);
//# sourceMappingURL=mainPage.1a5be6b7.js.map