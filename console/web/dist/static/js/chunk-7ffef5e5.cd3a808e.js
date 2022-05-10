(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7ffef5e5"],{1958:function(e,t,r){"use strict";r.r(t);var a=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"reserve-create"},[r("h1",{staticClass:"reserve-create__title"},[e._v("创建预约记录")]),r("el-form",{ref:"reserveForm",attrs:{disabled:!e.saveVisible,model:e.form,rules:e.rules,"label-width":"100px"}},[r("el-form-item",{attrs:{label:"会议室",prop:"roomId"}},[r("el-select",{attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.roomId,callback:function(t){e.$set(e.form,"roomId",t)},expression:"form.roomId"}},e._l(e.roomList,(function(e){return r("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1)],1),r("el-form-item",{attrs:{label:"内容",prop:"content"}},[r("el-input",{staticStyle:{width:"500px"},attrs:{rows:4,type:"textarea"},model:{value:e.form.content,callback:function(t){e.$set(e.form,"content",t)},expression:"form.content"}})],1),r("el-form-item",{attrs:{label:"预约人",prop:"accountId"}},[r("el-select",{attrs:{filterable:"",placeholder:"请选择"},model:{value:e.form.accountId,callback:function(t){e.$set(e.form,"accountId",t)},expression:"form.accountId"}},e._l(e.accountList,(function(e){return r("el-option",{key:e.id,attrs:{label:e.passport,value:e.id}})})),1)],1),r("el-form-item",{attrs:{label:"开始时间",prop:"reserveStartTime"}},[r("el-date-picker",{attrs:{"default-time":"12:00:00",placeholder:"选择日期",type:"datetime","value-format":"timestamp"},model:{value:e.form.reserveStartTime,callback:function(t){e.$set(e.form,"reserveStartTime",t)},expression:"form.reserveStartTime"}})],1),r("el-form-item",{attrs:{label:"结束时间",prop:"reserveEndTime"}},[r("el-date-picker",{attrs:{"default-time":"12:00:00",placeholder:"选择日期",type:"datetime","value-format":"timestamp"},model:{value:e.form.reserveEndTime,callback:function(t){e.$set(e.form,"reserveEndTime",t)},expression:"form.reserveEndTime"}})],1)],1),r("el-form",[r("el-form-item",[e.$hasPermission("RESERVE_CREATE")&&e.saveVisible?r("el-button",{staticStyle:{"margin-left":"100px"},attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("保存\n      ")]):e._e()],1)],1)],1)},s=[],o=r("2824"),i=r("5433"),n=r("ccf4"),l=r("968f"),u={name:"ReserveDetail",props:{defaultValues:{type:Object,default(){return{id:"",content:"",reserveStartTime:"",reserveEndTime:"",roomName:"",accountPassport:""}}}},data(){return{form:Object.assign({},this.defaultValues),rules:i["a"],accountList:[],roomList:[],saveVisible:!0}},created(){this.queryAccountList(),this.queryRoomList()},watch:{$route(e,t){e.path.includes("/$tool.firstLowerCase($foreignModel)-create")&&(this.form=this.defaultValues,this.defaultValues.imageList=[],this.saveVisible=!0)}},methods:{async queryAccountList(){const{data:e}=await n["a"].query({pageSize:1e3,pageNum:1});this.accountList=e.rows},async queryRoomList(){const{data:e}=await l["a"].query({pageSize:1e3,pageNum:1});this.roomList=e.rows},async submitForm(){try{await this.$refs.reserveForm.validate()}catch(e){return Promise.reject(new Error("表单校验失败"))}try{let e=Object.assign({},this.form);this.saveVisible=!1,await o["a"].create(e),this.$emit("success"),this.$message.success("创建成功！")}catch(t){this.$message.error("创建失败！原因："+t.message)}}}},c=u,m=(r("7855"),r("2877")),f=Object(m["a"])(c,a,s,!1,null,null,null);t["default"]=f.exports},2824:function(e,t,r){"use strict";var a=r("1c1e");function s(e){return a["a"].post("/api/reserve",e)}function o(e){return a["a"].remove("/api/reserve",e)}function i(e,t){return a["a"].put("/api/reserve",e,t)}function n(e){return a["a"].get("/api/reserve",e)}function l(e){return a["a"].get("/api/reserve",null,e)}function u(e){return a["a"].get("/api/reserve/events",null,e)}function c(e){return a["a"].get("/api/reserve/myEvents",null,e)}function m(e){return a["a"].download("/api/reserve/exportExcel",e)}function f(e){return a["a"].post("/api/qr/generate",e)}t["a"]={create:s,remove:o,update:i,get:n,query:l,events:u,myEvents:c,exportExcel:m,loginQrCode:f}},5433:function(e,t,r){"use strict";t["a"]={roomId:[{required:!0,message:"请输入会议室",trigger:"blur"}],accountId:[{required:!0,message:"请输入预约人",trigger:"blur"}],reserveStartTime:[{required:!0,message:"请输入开始时间",trigger:"blur"}],reserveEndTime:[{required:!0,message:"请输入结束时间",trigger:"blur"}],createTime:[{required:!0,message:"请输入创建时间",trigger:"blur"}]}},7855:function(e,t,r){"use strict";r("e5d5")},"968f":function(e,t,r){"use strict";var a=r("1c1e");function s(e){return a["a"].post("/api/room",e)}function o(e){return a["a"].remove("/api/room",e)}function i(e,t){return a["a"].put("/api/room",e,t)}function n(e){return a["a"].get("/api/room",e)}function l(e){return a["a"].get("/api/room",null,e)}function u(e){return a["a"].get("/api/room/resource",null,e)}function c(e){return a["a"].download("/api/room/exportExcel",e)}t["a"]={create:s,remove:o,update:i,get:n,query:l,resource:u,exportExcel:c}},e5d5:function(e,t,r){}}]);