webpackJsonp([31],{0:function(e,n){},"5LIk":function(e,n){},N3JW:function(e,n){},NHnr:function(e,n,t){"use strict";Object.defineProperty(n,"__esModule",{value:!0});var i=t("7+uW"),o={render:function(){var e=this.$createElement,n=this._self._c||e;return n("div",[n("transition",{attrs:{name:"component-fade",mode:"out-in"}},[n("router-view")],1)],1)},staticRenderFns:[]};var r=t("VU/8")({},o,!1,function(e){t("N3JW")},null,null).exports,a=t("/ocq");i.default.use(a.a);var c=a.a.prototype.push;a.a.prototype.push=function(e){return c.call(this,e).catch(function(e){return e})};var u,l=new a.a({routes:[{path:"/course",name:"course",component:function(){return t.e(17).then(t.bind(null,"Z/FV"))},children:[{path:"user",name:"user",component:function(){return t.e(1).then(t.bind(null,"GXbb"))},children:[{path:"main",name:"Main",component:function(){return t.e(20).then(t.bind(null,"5qyR"))}},{path:"resource",name:"Resource",component:function(){return t.e(15).then(t.bind(null,"8DI3"))},children:[{path:"files",name:"Files",component:function(){return Promise.all([t.e(0),t.e(21)]).then(t.bind(null,"7bAE"))}},{path:"filedetail",name:"FileDetail",component:function(){return Promise.all([t.e(0),t.e(25)]).then(t.bind(null,"FbIA"))}},{path:"videos",name:"Videos",component:function(){return Promise.all([t.e(0),t.e(14)]).then(t.bind(null,"UV7K"))}},{path:"videotail",name:"VideoDetail",component:function(){return Promise.all([t.e(0),t.e(2)]).then(t.bind(null,"Tq7q"))}}],redirect:{name:"Videos"}},{path:"teacher",name:"Teacher",component:function(){return t.e(27).then(t.bind(null,"5xep"))},children:[{path:"teacherdetail",name:"TeacherDetail",component:function(){return Promise.all([t.e(0),t.e(5)]).then(t.bind(null,"k3UR"))}},{path:"teacheronedetail",name:"TeacherOneDetail",component:function(){return Promise.all([t.e(0),t.e(4)]).then(t.bind(null,"2dGF"))}}],redirect:{name:"TeacherDetail"}},{path:"material",name:"Material",component:function(){return t.e(22).then(t.bind(null,"jHe/"))},children:[{path:"materialoutline",name:"MaterialOutLine",component:function(){return Promise.all([t.e(0),t.e(7)]).then(t.bind(null,"o0cA"))}},{path:"materialdetail",name:"MaterialDetail",component:function(){return Promise.all([t.e(0),t.e(9)]).then(t.bind(null,"qY9i"))}},{path:"materialform",name:"MaterialForm",component:function(){return Promise.all([t.e(0),t.e(8)]).then(t.bind(null,"Smik"))}}],redirect:{name:"MaterialOutLine"}},{path:"calendar",name:"Calendar",component:function(){return t.e(23).then(t.bind(null,"vLS9"))},children:[{path:"calendarlist",name:"CalendarList",component:function(){return Promise.all([t.e(0),t.e(26)]).then(t.bind(null,"nKhO"))}},{path:"calendardetail",name:"CalendarDetail",component:function(){return t.e(13).then(t.bind(null,"3zqi"))}}],redirect:{name:"CalendarList"}},{path:"introduction",name:"Introduction",component:function(){return t.e(19).then(t.bind(null,"J6jZ"))},children:[{path:"introductiondetail",name:"IntroductionDetail",component:function(){return Promise.all([t.e(0),t.e(10)]).then(t.bind(null,"5va6"))}}],redirect:{name:"IntroductionDetail"}},{path:"center",name:"Center",component:function(){return t.e(29).then(t.bind(null,"1VQ/"))},children:[{path:"centerlist",name:"CenterList",component:function(){return Promise.all([t.e(0),t.e(28)]).then(t.bind(null,"efQp"))}},{path:"centerdetail",name:"CenterDetail",component:function(){return Promise.all([t.e(0),t.e(12)]).then(t.bind(null,"I91U"))}}],redirect:{name:"CenterList"}},{path:"scientific",name:"Scientific",component:function(){return t.e(18).then(t.bind(null,"4THH"))},children:[{path:"scientificList",name:"ScientificList",component:function(){return Promise.all([t.e(0),t.e(16)]).then(t.bind(null,"X8UN"))}},{path:"scientificdetail",name:"ScientificDetail",component:function(){return t.e(6).then(t.bind(null,"x+Yt"))}}],redirect:{name:"ScientificList"}},{path:"effect",name:"Effect",component:function(){return t.e(24).then(t.bind(null,"nhdx"))},children:[{path:"effectdetail",name:"EffectDetail",component:function(){return Promise.all([t.e(0),t.e(11)]).then(t.bind(null,"r0JP"))}}],redirect:{name:"EffectDetail"}}],redirect:{name:"Main"}}],redirect:{name:"user"}},{path:"/error",name:"error",component:function(){return t.e(3).then(t.bind(null,"1eh5"))}},{path:"/",redirect:{name:"course"}},{path:"*",redirect:{name:"course"}}]}),d=t("NYxO"),s={uploadFile:function(e,n){(0,e.commit)("uploadFile",n)},choiceMenuId:function(e,n){(0,e.commit)("choicemenuid",n)},choiceTypeMenuId:function(e,n){(0,e.commit)("choicetypoemenuid",n)}},m=t("bOdI"),h=t.n(m),p=(u={},h()(u,"uploadFile",function(e,n){e.uploadFile=n}),h()(u,"choicemenuid",function(e,n){e.menuId=n}),h()(u,"choicetypoemenuid",function(e,n){e.typeId=n}),u);i.default.use(d.a);var f=new d.a.Store({state:{uploadFile:"",menuId:"",typeId:""},getters:{},mutations:p,actions:s}),b=(t("7t+N"),t("zL8q")),v=t.n(b),g=(t("tvR6"),t("qb6w"),t("Bb4J"),t("Qbok"),t("iqGf")),j=t.n(g);t("Jg0P"),t("4CPX");i.default.use(v.a),i.default.use(d.a),t("g3Gj"),t("5LIk"),i.default.use(j.a),i.default.config.productionTip=!1,new i.default({el:"#app",router:l,store:f,render:function(e){return e(r)}})},Qbok:function(e,n){},g3Gj:function(e,n){},qb6w:function(e,n){},tvR6:function(e,n){},vA7V:function(e,n){e.exports={_from:"videojs-swf@5.4.2",_id:"videojs-swf@5.4.2",_inBundle:!1,_integrity:"sha1-aWSpv/kDtzLz5GUxSuR4oCoX6Ks=",_location:"/videojs-swf",_phantomChildren:{},_requested:{type:"version",registry:!0,raw:"videojs-swf@5.4.2",name:"videojs-swf",escapedName:"videojs-swf",rawSpec:"5.4.2",saveSpec:null,fetchSpec:"5.4.2"},_requiredBy:["/videojs-flash"],_resolved:"https://registry.npm.taobao.org/videojs-swf/download/videojs-swf-5.4.2.tgz",_shasum:"6964a9bff903b732f3e465314ae478a02a17e8ab",_spec:"videojs-swf@5.4.2",_where:"D:\\资料\\新建文件夹\\空Vue\\精品课程\\team\\node_modules\\videojs-flash",author:{name:"Brightcove"},bugs:{url:"https://github.com/videojs/video-js-swf/issues"},bundleDependencies:!1,copyright:"Copyright 2014 Brightcove, Inc. https://github.com/videojs/video-js-swf/blob/master/LICENSE",deprecated:!1,description:"The Flash-fallback video player for video.js (http://videojs.com)",devDependencies:{async:"~0.2.9",chg:"^0.3.2","flex-sdk":"4.6.0-0",grunt:"~0.4.0","grunt-bumpup":"~0.5.0","grunt-cli":"~0.1.0","grunt-connect":"~0.2.0","grunt-contrib-jshint":"~0.4.3","grunt-contrib-qunit":"~0.2.1","grunt-contrib-watch":"~0.1.4","grunt-npm":"~0.0.2","grunt-prompt":"~0.1.2","grunt-shell":"~0.6.1","grunt-tagrelease":"~0.3.1",qunitjs:"~1.12.0","video.js":"^5.9.2"},homepage:"http://videojs.com",keywords:["flash","video","player"],name:"videojs-swf",repository:{type:"git",url:"git+https://github.com/videojs/video-js-swf.git"},scripts:{version:"chg release -y && grunt dist && git add -f dist/ && git add CHANGELOG.md"},version:"5.4.2"}}},["NHnr"]);
//# sourceMappingURL=app.c0b53137d6c42771baf5.js.map