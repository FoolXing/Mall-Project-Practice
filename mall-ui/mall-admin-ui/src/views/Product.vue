<script setup>
    import productApi  from "@/api/product.js";
    import {ref, reactive} from "vue";
    //图片上传
    import {Plus} from '@element-plus/icons-vue'
    import {ElMessage, ElMessageBox} from "element-plus";
    //表格数据
    const list = ref([])
    const total = ref(0)
    //分页信息和搜索条件
    const productQuery = reactive({
        name: '',
        page: 1,
        limit: 10
    })
    /*productApi.list(productQuery).then(result => {
        if (result.code == 0) {
            list.value = result.data.list
            total.value = result.data.total
        }
    })*/

    const loadData = () => {
        productApi.list(productQuery).then(result => {
            if (result.code == 0) {
                list.value = result.data.records
                total.value = result.data.total
            }
        })
    }
    loadData()

    const onSearch = () => {
        productQuery.page = 1
        loadData()
    }

    //根据id删除
    const deleteById = (id) => {
        ElMessageBox.confirm(
            '您确认要删除么?',
            '警告',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
                lockScroll: false //防止抖动
            }
        ).then(() => {
            productApi.deleteById(id).then(result => {
                if (result.code == 0) {
                    ElMessage.success(result.msg)
                    loadData()
                } else {
                    ElMessage.error(result.msg)
                }
            })
        })
    }

    const ids = ref([])
    const handleSelectionChange = (rows) => {
        console.log(rows)
        ids.value = rows.map(row => row.id)
        console.log(ids.value)
    }

    const deleteAll = () => {
        ElMessageBox.confirm(
            '您确认要删除么?',
            '警告',
            {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
                lockScroll: false //防止抖动
            }
        ).then(() => {
            productApi.deleteAll(ids.value).then(result => {
                if (result.code == 0) {
                    ElMessage.success(result.msg)
                    loadData()
                } else {
                    ElMessage.error(result.msg)
                }
            })
        })
    }

    //添加、 编辑
    const dialogFormVisible = ref(false)
    const product = ref({})
    const title = ref('')

    const showAddDialog = () => {
        dialogFormVisible.value = true
        title.value = '添加'
        product.value = {}
    }

    const showUpdateDialog = (id) => {
        dialogFormVisible.value = true
        title.value = '编辑'
        product.value = {}
        productApi.selectById(id).then(result => {
            product.value = result.data
        })
    }
    const addOrUpdate = () => {
        if (product.value.id) {//编辑
            productApi.update(product.value).then(result => {
                if (result.code == 0) {
                    ElMessage.success(result.msg)
                    dialogFormVisible.value = false
                    loadData()
                } else {
                    ElMessage.error(result.msg)
                }
            })
        } else {//添加
            productApi.add(product.value).then(result => {
                if (result.code == 0) {
                    ElMessage.success(result.msg)
                    dialogFormVisible.value = false
                    loadData()
                } else {
                    ElMessage.error(result.msg)
                }
            })
        }

    }

    const handleAvatarSuccess = (result, uploadFile) => {
        product.value.mainImage = '/api/pic/' + result.data
    }

    import {useTokenStore} from "@/store/token.js";
    // import AuthImg from "@/components/AuthImg.vue";
    const tokenStore = useTokenStore()
    const headers = ref({
        //在请求头里面携带token传递到后台
        Authorization: tokenStore.token
    })

    import WangEditor from "@/components/WangEditor.vue";
    //富文本编辑器
    const onEditorChange = (detail) => {
        console.log(detail)
        product.value.detail = detail
    }

    //分类
    import categoryApi from "@/api/category.js";
    const selectCategory=ref(null);
    const topCategoryList=ref({});
    categoryApi.selectTopCategoryList().then(result => {
        if (result.code == 0) {
            topCategoryList.value = result.data
        }
    })
    const secondCategoryList = ref({})
    const selectChange = (value) => {
      categoryApi.selectSecondCategoryListByParentId(value).then(result => {
          if (result.code == 0) {
              secondCategoryList.value = result.data
          }
      })
    }
</script>

<template>
    <el-card class="">
        <template #header>
            <div class="header">
                <el-button type="primary" @click="showAddDialog">添加</el-button>
                <el-button type="primary" @click="deleteAll">批量删除</el-button>
            </div>
        </template>
        <el-form :inline="true">
            <el-form-item label="名字">
                <el-input v-model="productQuery.name" placeholder="请输入名字" clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" style="width: 100%" ref="multipleTableRef" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column fixed prop="id" label="ID"/>
            <el-table-column prop="name" label="名字"/>
            <el-table-column prop="categoryId" label="分类id"/>
            <el-table-column prop="categoryName" label="分类名称"/>
            <el-table-column prop="subtitle" label="商品副标题"/>
            <el-table-column prop="price" label="价格"/>
            <el-table-column prop="stock" label="库存数量"/>
            <el-table-column prop="status" label="商品状态"/>
            <el-table-column prop="mainImage" label="产品主图">
                <template #default="scope">
                    <img :src="scope.row.mainImage" style="max-height: 40px; max-width: 120px;"/>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations">
                <template #default="{ row }">
                    <el-button type="primary" @click="showUpdateDialog(row.id)">编辑</el-button>
                    <el-button type="danger" @click="deleteById(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            v-model:current-page="productQuery.page"
            v-model:page-size="productQuery.limit"
            :page-sizes="[10, 20, 30, 40]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @change="loadData"
            style="margin-top: 20px; justify-content: flex-end"
        />
    </el-card>

    <!--添加、编辑弹出框-->
    <el-dialog v-model="dialogFormVisible" :title="title" width="70%" :lock-scroll="false">
        <el-form :model="product">
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="product.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="分类id" :label-width="60">
<!--                <el-input v-model="product.categoryId" autocomplete="off" />-->
                <el-select v-model="selectCategory" clearable placeholder="请选择一级分类" @change="selectChange" style="width: 200px">
                    <el-option
                        v-for="category in topCategoryList"
                        :key="category.id"
                        :label="category.name"
                        :value="category.id"
                    />
                </el-select>
                <el-select v-model="product.categoryId" clearable placeholder="请选择二级分类" style="width: 200px">
                    <el-option
                        v-for="category in secondCategoryList"
                        :key="category.id"
                        :label="category.name"
                        :value="category.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="商品副标题" :label-width="90">
                <el-input v-model="product.subtitle" autocomplete="off" />
            </el-form-item>
            <el-form-item label="价格" :label-width="60">
                <el-input v-model="product.price" autocomplete="off" />
            </el-form-item>
            <el-form-item label="库存数量" :label-width="70">
                <el-input v-model="product.stock" autocomplete="off" />
            </el-form-item>
            <el-form-item label="商品状态" :label-width="70">
                <el-input v-model="product.status" autocomplete="off" />
            </el-form-item>
            <el-form-item label="产品主图" :label-width="70">
                <el-upload
                    class="avatar-uploader"
                    action="/api/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :headers="headers"
                >
                    <img v-if="product.mainImage" :src="product.mainImage" class="avatar"/>
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus/>
                    </el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="商品详情" :label-width="70">
<!--                <el-input v-model="product.detail" autocomplete="off" />-->
                <WangEditor :initValue="product.detail" @getEditorContent="onEditorChange"
                            v-if="dialogFormVisible" @close="dialogFormVisible = false"></WangEditor>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="addOrUpdate">确认</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style>
    .avatar-uploader .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed var(--el-border-color);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
        transition: var(--el-transition-duration-fast);
    }

    .avatar-uploader .el-upload:hover {
        border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        text-align: center;
    }
</style>