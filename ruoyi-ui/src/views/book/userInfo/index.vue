<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="identity">
        <el-input
          v-model="queryParams.identity"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['book:userInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['book:userInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="联系方式" align="center" prop="mobile" />
      <el-table-column label="身份证号" align="center" prop="identity" />
      <el-table-column label="余额" align="center" prop="balance" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
        <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="resetPassword(scope.row)"
            v-hasPermi="['book:userInfo:remove']"
          >密码重置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="balanceTop(scope.row)"
            v-hasPermi="['book:userInfo:remove']"
          >余额充值</el-button>
        <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="detail(scope.row)"
            v-hasPermi="['book:userInfo:edit']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['book:userInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['book:userInfo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户信息管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName" >
          <el-input v-model="form.userName" placeholder="请输入用户名"  :disabled ="show"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别" :disabled ="show">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" :disabled ="show"/>
        </el-form-item>
        <el-form-item label="出生日期" prop="birth">
          <el-input v-model="form.birth" placeholder="请输入出生日期" type= "Date" :disabled ="show"  />
        </el-form-item>
        <el-form-item label="联系方式" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入联系方式" :disabled ="show" />
        </el-form-item>
        <el-form-item label="身份证号" prop="identity">
          <el-input v-model="form.identity" placeholder="请输入身份证号" :disabled ="show" />
        </el-form-item>
        <el-form-item label="登录账号" prop="accontNo">
          <el-input v-model="form.accontNo" placeholder="请输入登录账号" :disabled ="show" />
        </el-form-item>
        <el-form-item label="登录密码" prop="password" v-if="isAddShow">
          <el-input v-model="form.password" placeholder="请输入登录密码" v-if = "isAddShow" type="password" show-password/>
        </el-form-item>
        <!-- <el-form-item label="余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入余额" :disabled ="show" />
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if ="isShow ">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="resetPwdOpen" width="500px" append-to-body>
        <el-form ref="pwdform" :model="pwdform" :rules="rules1" label-width="80px">
          <el-form-item label="登录密码" prop="password" >
            <el-input v-model="pwdform.password" placeholder="请输入登录密码"  type="password" />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword" >
            <el-input v-model="pwdform.newPassword" placeholder="请输入新登录密码"  type="password" show-password/>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword" >
            <el-input v-model="pwdform.confirmPassword" placeholder="请再一次输入新密码"  type="password" show-password/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPwd">确 定</el-button>
        <el-button @click="cancelPwd">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
        <el-form ref="form2" :model="form2"  label-width="80px">
          <el-form-item label="余额" prop="balance" >
          <el-input v-model="form2.balance2" placeholder="请输入余额"  type="number"/>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit2">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo,resetPwd } from "@/api/book/userInfo";
import { number } from 'echarts';

export default {
  name: "Info",
  dicts: ['sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户信息管理表格数据
      infoList: [],
      //输入框是否禁用
      show:true,
      isShow:false,
      isAddShow: false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open2: false,
      resetPwdOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        sex: null,
        age: null,
        birth: null,
        mobile: null,
        identity: null,
        accontNo: null,
        balance: null,
        password: null
      },
      // 表单参数
      form: {},
      form2:{},
      pwdform:{},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        mobile: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        identity: [
          { required: true, message: "身份证号不能为空", trigger: "blur" }
        ],
        accontNo: [
          { required: true, message: "登录账号不能为空", trigger: "blur" },
          { min: 2, max: 20, message: '登录账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        password: [
         { required: true, message: "用户密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' },
          { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
        ],
      },
      rules1: {
        newPassword: [
         { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' },
          { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
        ],
        confirmPassword: [
         { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' },
          { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" },
          {
            validator: (rule,value,callBack)=>{
              if(value !== this.pwdform.newPassword ){
                callBack(new Error('两次输入的密码不一致'));
              }else{
                callBack();
              }
            },
            trigger: 'blur' 
          }
        ],
      }
    };
  },
  
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户信息管理列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
     cancel2() {
      this.open2 = false;
      this.reset2();
    },
    cancelPwd(){
      this.resetPwdOpen = false;
      this.resetP();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userName: null,
        sex: null,
        age: null,
        birth: null,
        mobile: null,
        identity: null,
        accontNo: null,
        balance: null,
        password: null
      };
      this.resetForm("form");
    },
    reset2(){
      this.form2 = {
        balance: null
      };
      this.resetForm("form2");
    },
    resetP(){
      this.pwdform = {
        password: null
      };
      this.resetForm("pwdform");
    },
     /** 重置密码提交按钮 */
    submitPwd() {
      this.$refs["pwdform"].validate(valid => {
        if (valid) {
          //  console.log("==========="+JSON.stringify(this.pwdform),"++++++++"+this.pwdform.newPassword);
            resetPwd(this.pwdform).then(response => {
              this.$modal.msgSuccess("密码重置成功");
              this.resetPwdOpen = false;
              this.getList();
            });
          
        }
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.show =false;
      this.isShow =true;
      this.isAddShow =true;
      this.title = "添加用户信息管理";
    },
     /** 详情按钮操作 */
    detail(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.show =true;
        this.isShow =false;
        this.isAddShow=false;
        this.open = true;
        this.title = "用户信息详情";
      });
    },
     /** 重置密码操作 */
    resetPassword(row) {
      this.resetP();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.pwdform = response.data;
        this.resetPwdOpen = true;
        this.title = " 密码重置";
      });
    },
    /** 充值余额操作 */
    balanceTop(row) {
      this.reset2();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form2 = response.data;
        this.open2 = true;
        this.title = " 余额充值";
      });
    },
    /** 充值余额提交按钮 */
    submit2() {
      this.$refs["form2"].validate(valid => {
        if (valid) {
           this.form2.balance = parseFloat(this.form2.balance);
           this.form2.balance2 = parseFloat(this.form2.balance2);
           this.form2.balance += this.form2.balance2;
           console.log("2222222222======="+JSON.stringify(this.form2)) ;
            updateInfo(this.form2).then(response => {
              this.$modal.msgSuccess("充值成功");
              this.open2 = false;
              this.getList();
            });
          
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.show =false;
        this.isShow =true;
        this.isAddShow=false;
        this.open = true;
        this.title = "修改用户信息管理";
      });
    },
    
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户信息管理编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
