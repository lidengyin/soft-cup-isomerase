webpackJsonp([10],{"5va6":function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=n("zWVa"),a={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"rightContent"},[n("div",{staticClass:"navTile"},[e._v("\n    当前位置：\n    "),n("router-link",{attrs:{to:{name:"Main"}}},[e._v("首页")]),e._v("\n     >> \n    "),n("router-link",{attrs:{to:{name:"Introduction",query:{typeName:e.typeName,index:0}}}},[e._v("课程介绍")]),e._v("\n     >> \n    "),n("router-link",{attrs:{to:{name:"IntroductionDetail",query:{typeName:e.typeName,index:e.index}}}},[e._v(e._s(e.menuName))])],1),e._v(" "),n("div",{staticClass:"content"},[n("div",{staticClass:"myDetail"},[n("div",{staticClass:"introduce",attrs:{id:"introduce"}},[e._v("\n        "+e._s(e.setIntroduce("introduce",e.file.content))+"\n      ")])])])])},staticRenderFns:[]};var u=function(e){n("R/NJ")},o=n("VU/8")(i.a,a,!1,u,null,null);t.default=o.exports},"R/NJ":function(e,t){},zWVa:function(e,t,n){"use strict";(function(e){var i=n("Dd8w"),a=n.n(i),u=n("2cg0"),o=n.n(u),s=n("NYxO");t.a={name:"",data:function(){return{List:[],menus:[],menuName:"",typeName:"Introduction",index:0,file:{}}},methods:a()({},Object(s.b)(["choiceMenuId","choiceTypeMenuId"]),{setIntroduce:function(t,n){n?e("#"+t).html(n):e("#"+t).html("此栏目下没有数据")},chanceUrlQuery:function(e){this.$router.push({query:o()({},{})}),this.$router.push({query:o()(this.$route.query,e)})},handleId:function(){var e=this.List,t=this.menus,n=this.typeName,i=this.index;this.file=e[i],this.menuName=t[i].menuName,this.choiceMenuId(n),this.choiceTypeMenuId(n+i)}}),watch:{$route:{handler:function(){this.index=1*this.$route.query.index,this.handleId()},deep:!0}},mounted:function(){if(this.$route.query.typeName)this.typeName=this.$route.query.typeName,this.index=1*this.$route.query.index;else{var e=this.typeName,t=this.index;this.chanceUrlQuery({typeName:e,index:t})}this.menus=[{typeName:"Introduction",menuName:"课程简介",name:"IntroductionDetail"},{typeName:"Introduction",menuName:"历史沿革",name:"IntroductionDetail"},{typeName:"Introduction",menuName:"课程特色",name:"IntroductionDetail"},{typeName:"Introduction",menuName:"课程设计",name:"IntroductionDetail"}],this.List=[{id:"0",courseName:"课程简介",content:"本课程是在《动物遗传学》和《生物统计学》课程基础上，在动物科学专业课程体系中起承上启下作用。一方面它与前期所学生物统计学、动物遗传学密切相关，另一方面又是后期动物生产各论课的铺垫。本课程主要内容包括两大部分：选种和选配。通过本课程的学习，使学生掌握家畜育种的基本原理和方法，育种措施的设计和优化，育种技术的新发展及运用和家畜遗传资源的保护与利用等。为学生将来从事动物育种、动物生产和动物保护等相关行业奠定基础。"},{id:"1",courseName:"历史沿革",content:"（1）杜炳旺（硕导）教授对本课程评价：《家畜育种学》课程注重教学改革，吸收科研成果开设“育种新技术专题讲座”，课程实验课程密切结合生产实际，设计合理，有效地对种用家畜选种方法进行了训练，教学效果较好。\n\n（2）校内督导贾汝敏教授评价：张丽老师讲课教学态度认真，概念阐述清晰，举例得当，教学内容丰富，注重启发式教学，图片内容丰富，多媒体技术教学手段运用较好，教学效果好。"},{id:"2",courseName:"课程特色",content:"本课程组建立了《家畜育种学》课程网站，通过该网站学生可以随时学习育种学相关知识，了解育种新技术的发展现状。同时该网站建立了和本课程紧密相关的资源网址连接，例如：家养动物种质资源平台：（http://www.cdad-is.org.cn/），联合国粮农组织家畜多样性信息系统（http://www.fao.org/dad-is）和以动物科学专业为优势学科的美国俄克拉荷马州立大学（Oklahoma State University, http://www.ansi.okstate.edu/breeds）等。通过这些连接可以拓展学习内容，了解国内外家畜育种的动态。\n\n其次，本课程积累了几届《家畜育种学》教学改革的资料，包括教改论文，教师育种新技术讲座资料和学生育种知识报告资料等，通过这些资料的积累，使得该课程的学习形成了独特的“基本知识讲授+教师育种新技术专题报告+学生育种知识报告”授课和学习模式。\n\n第三，本课程组教师利用各自主持的科学研究项目来带动学生的学习，使学生参与到教师的科研工作中，进一步从育种学书本理论接触到育种学问题的研究中，提高了学习的兴趣。"},{id:"3",courseName:"课程设计",content:""}],this.handleId()}}}).call(t,n("7t+N"))}});
//# sourceMappingURL=10.291d2472c83d6d1ea6cb.js.map