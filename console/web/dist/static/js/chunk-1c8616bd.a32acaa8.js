(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1c8616bd"],{"0e26":function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"repair-create"},[r("h1",{staticClass:"repair-create__title"},[e._v("创建报修")]),r("el-form",{ref:"repairForm",attrs:{disabled:!e.saveVisible,model:e.form,rules:e.rules,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"会议室",prop:"roomId"}},[r("el-select",{attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.roomId,callback:function(t){e.$set(e.form,"roomId",t)},expression:"form.roomId"}},e._l(e.roomList,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1)],1),r("el-form-item",{attrs:{label:"报修人",prop:"accountId"}},[r("el-select",{attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.accountId,callback:function(t){e.$set(e.form,"accountId",t)},expression:"form.accountId"}},e._l(e.accountList,(function(e){return r("el-option",{key:e.id,attrs:{label:e.passport,value:e.id}})})),1)],1),r("el-form-item",{attrs:{label:"内容",prop:"content"}},[r("el-input",{staticStyle:{width:"500px"},attrs:{rows:4,type:"textarea"},model:{value:e.form.content,callback:function(t){e.$set(e.form,"content",t)},expression:"form.content"}})],1)],1),r("el-form",[r("el-form-item",[e.$hasPermission("REPAIR_CREATE")&&e.saveVisible?r("el-button",{staticStyle:{"margin-left":"100px"},attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("保存\n      ")]):e._e()],1)],1)],1)},o=[],i=r("611a"),s=r("cb79"),n=r("ccf4"),u=r("968f"),c={name:"RepairDetail",props:{defaultValues:{type:Object,default(){return{id:"",status:"",content:"",roomName:"",accountPassport:""}}}},data(){return{form:Object.assign({},this.defaultValues),rules:s["a"],accountList:[],roomList:[],saveVisible:!0}},created(){this.queryAccountList(),this.queryRoomList()},watch:{$route(e,t){e.path.includes("/$tool.firstLowerCase($foreignModel)-create")&&(this.form=this.defaultValues,this.defaultValues.imageList=[],this.saveVisible=!0)}},methods:{async queryAccountList(){const{data:e}=await n["a"].query({pageSize:1e3,pageNum:1});this.accountList=e.rows},async queryRoomList(){const{data:e}=await u["a"].query({pageSize:1e3,pageNum:1});this.roomList=e.rows},async submitForm(){try{await this.$refs.repairForm.validate()}catch(e){return Promise.reject(new Error("表单校验失败"))}try{let e=Object.assign({},this.form);this.saveVisible=!1,await i["a"].create(e),this.$emit("success"),this.$message.success("创建成功！")}catch(t){this.$message.error("创建失败！原因："+t.message)}}}},l=c,m=(r("2a8a"),r("2877")),p=Object(m["a"])(l,a,o,!1,null,null,null);t["default"]=p.exports},"2a8a":function(e,t,r){"use strict";r("d6a6")},"611a":function(e,t,r){"use strict";var a=r("1c1e");function o(e){return a["a"].post("/api/repair",e)}function i(e){return a["a"].remove("/api/repair",e)}function s(e,t){return a["a"].put("/api/repair",e,t)}function n(e){return a["a"].get("/api/repair",e)}function u(e){return a["a"].get("/api/repair",null,e)}function c(e){return a["a"].download("/api/repair/exportExcel",e)}t["a"]={create:o,remove:i,update:s,get:n,query:u,exportExcel:c}},"968f":function(e,t,r){"use strict";var a=r("1c1e");function o(e){return a["a"].post("/api/room",e)}function i(e){return a["a"].remove("/api/room",e)}function s(e,t){return a["a"].put("/api/room",e,t)}function n(e){return a["a"].get("/api/room",e)}function u(e){return a["a"].get("/api/room",null,e)}function c(e){return a["a"].get("/api/room/resource",null,e)}function l(e){return a["a"].download("/api/room/exportExcel",e)}t["a"]={create:o,remove:i,update:s,get:n,query:u,resource:c,exportExcel:l}},cb79:function(e,t,r){"use strict";t["a"]={status:[{required:!0,message:"请输入状态;1:未处理 2:处理中 3: 已完成",trigger:"blur"},{type:"integer",min:0,trigger:"blur",message:"状态;1:未处理 2:处理中 3: 已完成不能小于0"}],content:[{required:!0,message:"请输入内容",trigger:"blur"}],roomId:[{required:!0,message:"请输入会议室",trigger:"blur"}],accountId:[{required:!0,message:"请输入报修人",trigger:"blur"}],createTime:[{required:!0,message:"请输入创建时间",trigger:"blur"}]}},d6a6:function(e,t,r){}}]);