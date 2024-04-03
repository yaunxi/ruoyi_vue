<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="图书名称" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入图书名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者名"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publishing">
        <el-input
          v-model="queryParams.publishing"
          placeholder="请输入出版社名"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别" prop="bookType">
        <el-input
          v-model="queryParams.bookType"
          placeholder="请输入书籍类别"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
              v-hasPermi="['book:bookInfo:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['book:bookInfo:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
 
    <el-table
      v-loading="loading"
      :data="bookInfoList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="图书名称"
        align="center"
        prop="bookName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="作者"
        align="center"
        prop="author"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="出版社"
        align="center"
        prop="publishing"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="总数量"
        align="center"
        prop="number"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="在馆册数"
        align="center"
        prop="inLibraryNum"
        :show-overflow-tooltip="true"
      />
      <!-- <el-table-column label="书籍类别" align="center" prop="bookType" :show-overflow-tooltip="true" />
      <el-table-column label="价格" align="center" prop="bookPrice" :show-overflow-tooltip="true" />
      <el-table-column label="存放位置" align="center" prop="bookPlace" :show-overflow-tooltip="true" /> -->
      <el-table-column
        label="入馆时间"
        align="center"
        prop="createTime"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="infoDetail(scope.row)"
            v-hasPermi="['book:bookInfo:detail']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:config:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:config:remove']"
            >删除</el-button
          >
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


    <!-- 图书详情/添加/修改 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="书籍名称" prop="bookName">
          <el-input v-model="form.bookName" placeholder="请输入书籍名称" :disabled="show"/>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者名称" :disabled="show"/>
        </el-form-item>
         <el-form-item label="出版社" prop="publishing">
          <el-input v-model="form.publishing" placeholder="请输入出版社名称" :disabled="show"/>
        </el-form-item>
         <el-form-item label="总数量" prop="number">
          <el-input v-model="form.number" placeholder="请输入总数量" :disabled="show"/>
        </el-form-item>
        <el-form-item label="书籍类型" prop="bookType">
          <el-input v-model="form.bookType" placeholder="请输入书籍类型" :disabled="show"/>
        </el-form-item>
        <el-form-item label="书籍价格" prop="bookPrice">
          <el-input v-model="form.bookPrice" placeholder="请输入书籍价格" :disabled="show"/>
        </el-form-item>
         <el-form-item label="存放位置" prop="bookPlace">
          <el-input v-model="form.bookPlace" placeholder="请输入存放位置" :disabled="show"/>
        </el-form-item>
        <el-form-item label="在馆册数" prop="inLibraryNum">
          <el-input v-model="form.inLibraryNum" placeholder="请输入在馆册数" :disabled="show"/>
        </el-form-item>
        <el-form-item label="创建日期" prop="createTime">
          <el-date-picker v-model="form.createTime" type="date" placeholder="请输入创建日期" :picker-options="pickerOptions0" :disabled="show"></el-date-picker>
        </el-form-item>
        
        
       
        </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="ifVueShow">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import { listBookInfo, detail ,updateBookInfo,addBookInfo} from "@/api/book/bookInfo";
export default {
  name: "BookInfo",
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
      // 参数表格数据
      bookInfoList: [],
      bookInfoList2: [],
     
      userInfoListShow:[],
      // 弹出层标题
      title: "",
      show:false,
      ifVueShow: true,
      //用户弹窗界面
      showUserView:false,
      //是否显示序号列
      ifShow:false,
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bookName: undefined,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        bookName: undefined,
      },
      currentPage:1, //初始页
      pagesize:10,    //    每页的数据　
      total:0,
      
      // 表单参数
      form: {},

      //人员表单
      userForm: {userName:""},
      // 表单校验
      rules: {
        bookName: [
          { required: true, message: "参数名称不能为空", trigger: "blur" },
        ],
      },
      pickerOptions0: { 
         disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;//如果没有后面的-8.64e7就是不可以选择今天的 
         }
  	  },
    };
  },
  
  created() {
    this.getList();
  },
  methods: {
getRowKey(row) {
  return row.id
},
    showUser(){
      //打开选择人人员界面
      this.showUserView = true;
      //不显示序号列
      this.ifShow = false;
    },
    showUsers(){
      this.showUserView = true;
      this.ifShow=true;
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listBookInfo(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.bookInfoList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
     // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
   // 表单重置
    reset() {
      this.form = {
        id: undefined,
        bookName: undefined,
        author: undefined,
        number: undefined,
        publishing: undefined,
        bookType: undefined,
        bookPrice: undefined,
        bookPlace: undefined,
        inLibraryNum: undefined,
        createBy: undefined,
        createTime: undefined
        
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 详情按钮操作 */
    infoDetail(row) {
      this.reset();
      const id = row.id 
      detail(id).then((response) => {
        this.form = response; 
        this.show=true ;
        this.ifVueShow = false;  
        this.open = true;
        this.title = "图书详情";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const configId = row.id
      detail(configId).then((response) => {
        this.form = response;
        this.open = true;
        this.ifVueShow = true;
        this.show = false;
        this.title = "修改图书信息";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset(); 
        this.open = true;
        this.title = "添加图书信息";
        this.ifVueShow = true;
        this.show = false;     
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateBookInfo(this.form).then(response => {
               console.log("修改成功！！！！")
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
             
            });
          } else {
            addBookInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 刷新缓存按钮操作 */
    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("刷新成功");
      });
    },
  },
};
</script>