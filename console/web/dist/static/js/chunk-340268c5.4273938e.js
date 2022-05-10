(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-340268c5"],{"0035":function(e,t,i){"use strict";i.r(t);var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",[i("div",{staticClass:"crumbs"},[i("el-breadcrumb",{attrs:{separator:"/"}},[i("el-breadcrumb-item",[i("i",{staticClass:"el-icon-lx-cascades"}),e._v(" 部门管理\n      ")])],1)],1),i("div",{staticClass:"container"},[i("div",{staticClass:"handle-box"},[i("el-form",{attrs:{inline:""}},[i("el-form-item",{attrs:{label:"部门"}},[i("el-input",{staticClass:"handle-input mr10",staticStyle:{width:"180px"},model:{value:e.filter.name,callback:function(t){e.$set(e.filter,"name",t)},expression:"filter.name"}})],1),i("el-form-item",[e.$hasPermission("DEPARTMENT_QUERY")?i("el-button",{attrs:{icon:"el-icon-search",type:"primary"},on:{click:function(t){return e.queryList()}}},[e._v("查询\n          ")]):e._e(),e.$hasPermission("DEPARTMENT_EXPORT")?i("el-button",{attrs:{icon:"el-icon-search",type:"primary"},on:{click:function(t){return e.exportExcel()}}},[e._v("导出\n          ")]):e._e(),e.$hasPermission("DEPARTMENT_CREATE")?i("el-button",{attrs:{icon:"el-icon-search",type:"primary"},on:{click:function(t){return e.create()}}},[e._v("创建\n          ")]):e._e()],1)],1)],1),i("paged-table",{attrs:{"current-page":e.filter.pageNum,data:e.tableData,"page-size":e.filter.pageSize,total:e.total},on:{"current-change":e.queryList,"size-change":e.pageSizeChange}},[i("el-table-column",{attrs:{label:"部门",prop:"name"}}),i("el-table-column",{attrs:{label:"备注",prop:"description","show-overflow-tooltip":""}}),i("el-table-column",{attrs:{label:"创建时间"},scopedSlots:e._u([{key:"default",fn:function(t){var i=t.row;return[e._v(e._s(e._f("formatDate")(i.createTime)))]}}])}),i("el-table-column",{attrs:{label:"修改时间"},scopedSlots:e._u([{key:"default",fn:function(t){var i=t.row;return[e._v(e._s(e._f("formatDate")(i.updateTime)))]}}])}),i("el-table-column",{attrs:{align:"center",label:"操作",width:"180"},scopedSlots:e._u([{key:"default",fn:function(t){return[e.$hasPermission("DEPARTMENT_UPDATE")?i("el-button",{attrs:{size:"small",type:"text"},on:{click:function(i){return e.edit(t.row)}}},[e._v("编辑\n          ")]):e._e(),e.$hasPermission("DEPARTMENT_DELETE")?i("el-button",{attrs:{size:"small",type:"text"},on:{click:function(i){return e.remove(t.row.id)}}},[e._v("删除\n          ")]):e._e()]}}])})],1)],1)])},a=[],s=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("af-dialog",{staticClass:"system-dialog",attrs:{"on-confirm":e.submitForm,title:e.isEdit?"编辑部门":"创建部门",visible:e.visible},on:{"update:visible":function(t){e.visible=t},close:function(t){return e.$emit("close")}}},[i("el-form",{ref:"departmentForm",attrs:{model:e.form,rules:e.rules,"label-width":"100px"}},[i("el-form-item",{attrs:{label:"部门",prop:"name"}},[i("el-input",{model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),i("el-form-item",{attrs:{label:"备注",prop:"description"}},[i("el-input",{staticStyle:{width:"500px"},attrs:{rows:4,type:"textarea"},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1)],1)],1)},r=[],o=i("3f7a"),l=i("64f1"),c={name:[{required:!0,message:"请输入部门",trigger:"blur"},{type:"string",max:255,trigger:"blur",message:"部门长度不能超过255"}],createTime:[{required:!0,message:"请输入创建时间",trigger:"blur"}]},u={name:"DepartmentDialog",components:{AfDialog:o["a"]},props:{defaultValues:{type:Object,default(){return{name:"",description:""}}},isEdit:{type:Boolean,dafault:()=>!1}},data(){return{rules:c,form:this.defaultValues,visible:!1}},methods:{async submitForm(){try{await this.$refs.departmentForm.validate()}catch(e){return Promise.reject(new Error("表单校验失败"))}try{let e=Object.assign({},this.form);this.isEdit?await l["a"].update(e.id,e):await l["a"].create(e),this.$emit("success"),this.$message.success((this.isEdit?"编辑":"创建")+"成功！")}catch(t){this.$message.error((this.isEdit?"编辑":"创建")+"失败！原因："+t.message)}}}},d=u,m=(i("e869"),i("2877")),p=Object(m["a"])(d,s,r,!1,null,null,null),f=p.exports,g=i("0748"),b={name:"Department",components:{PagedTable:g["a"]},data(){return{filter:{name:"",pageNum:1,pageSize:20},total:0,tableData:[]}},created(){this.$dialog.register("department-dialog",f),this.queryList()},methods:{async queryList(e=1){this.filter.pageNum=e;const t=Object.assign({},this.filter),{data:i}=await l["a"].query(t);this.total=i.count,this.tableData=i.rows},async exportExcel(){this.filter.pageNum=1;const e=Object.assign({},this.filter);await l["a"].exportExcel(e)},pageSizeChange(e){this.filter.pageSize=e,this.queryList()},edit(e){const t={isEdit:!0};t.defaultValues=Object.assign({},e),this.openDialog("department-dialog",t)},openDialog(e,t={},i={success:this.queryList}){this.$dialog.open(e,{props:t,on:i})},create(){const e={isEdit:!1};this.openDialog("department-dialog",e)},async remove(e){await this.$confirm("是否确认删除？","删除",{type:"warn"});try{await l["a"].remove(e),this.$message.success("删除成功！"),this.queryList()}catch(t){this.$message.error("删除失败！原因："+t.message)}}}},h=b,v=(i("2c53"),Object(m["a"])(h,n,a,!1,null,"1c98fac3",null));t["default"]=v.exports},"06cf":function(e,t,i){},"1ddd":function(e,t,i){"use strict";i("2e2f")},"2c53":function(e,t,i){"use strict";i("06cf")},"2e2f":function(e,t,i){},"3f7a":function(e,t,i){"use strict";var n=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("el-dialog",e._g(e._b({staticClass:"dialog",attrs:{"close-on-click-modal":!1,"modal-append-to-body":!1,visible:e.elDialogVisible},on:{close:e.onClose,"update:visible":e.toggleVisible}},"el-dialog",e.$attrs,!1),e.listeners),[e.$attrs.title?e._e():i("template",{slot:"title"},[e._t("title")],2),e._t("default"),e.noFooter?e._e():i("template",{slot:"footer"},[e.$slots.footer?e._t("footer",null,{closeDialog:function(){return e.toggleVisible(!1)}}):[e.cancelBtn?i("el-button",{on:{click:function(t){return e.toggleVisible(!1)}}},[e._v("\n        取消\n      ")]):e._e(),i("el-button",{attrs:{disabled:e.disabledBtn,loading:e.pending,type:"primary"},on:{click:e.confirm}},[e._v("\n        "+e._s(e.confirmText)+"\n      ")])]],2)],2)},a=[];let s,r;function o(){return void 0!==s||(s=void 0!==window.ontransitionend?"transitionend":void 0!==window.onwebkittransitionend?"webkittransitionend":""),s}function l(){return void 0!==r||(r=void 0!==window.onanimationend?"animationend":void 0!==window.onwebkitanimationend?"webkitanimationend":""),r}var c={name:"VDialog",inheritAttrs:!1,props:{visible:{type:Boolean,default:!0},onConfirm:Function,cancelBtn:{type:Boolean,default:!0},disabledBtn:{type:Boolean,dafault:!1},noFooter:{type:Boolean,default:!1},confirmText:{type:String,default:"确定"}},data(){return{elDialogVisible:!1,pending:!1}},computed:{listeners(){let e={};for(let t in this.$listeners)-1===["update:visible","close","confirm"].indexOf(t)&&(e[t]=this.$listeners[t]);return e}},watch:{visible:{handler(e){e!==this.elDialogVisible&&(this.elDialogVisible=e)},immediate:!0}},methods:{toggleVisible(e){this.elDialogVisible=e,this.$emit("update:visible",e)},onClose(){let e=o(),t=l();if(e&&t){let i=()=>{this.$emit("close"),this.$el.removeEventListener(e,i),this.$el.removeEventListener(t,i)};this.$el.addEventListener(e,i),this.$el.addEventListener(t,i)}else this.$nextFrame(()=>this.$emit("close"))},confirm(){const e=e=>{this.pending=!1,!(e instanceof Error)&&this.toggleVisible(!1)};this.pending=!0,this.onConfirm&&this.onConfirm().then(e,e)}}},u=c,d=(i("1ddd"),i("2877")),m=Object(d["a"])(u,n,a,!1,null,"08c14ed2",null);t["a"]=m.exports},"4cde":function(e,t,i){},"64f1":function(e,t,i){"use strict";var n=i("1c1e");function a(e){return n["a"].post("/api/department",e)}function s(e){return n["a"].remove("/api/department",e)}function r(e,t){return n["a"].put("/api/department",e,t)}function o(e){return n["a"].get("/api/department",e)}function l(e){return n["a"].get("/api/department",null,e)}function c(e){return n["a"].download("/api/department/exportExcel",e)}t["a"]={create:a,remove:s,update:r,get:o,query:l,exportExcel:c}},e869:function(e,t,i){"use strict";i("4cde")}}]);