<script setup>
    import categoryApi  from "@/api/category.js";
    import {ref, reactive} from "vue";
    //图片上传
    import {Plus} from '@element-plus/icons-vue'
    import {ElMessage, ElMessageBox} from "element-plus";
    //表格数据
    const list = ref([])
    const total = ref(0)
    //分页信息和搜索条件
    const categoryQuery = reactive({
        name: '',
        page: 1,
        limit: 10
    })
    /*categoryApi.list(categoryQuery).then(result => {
        if (result.code == 0) {
            list.value = result.data.list
            total.value = result.data.total
        }
    })*/

    const loadData = () => {
        categoryApi.list(categoryQuery).then(result => {
            if (result.code == 0) {
                list.value = result.data.records
                total.value = result.data.total
            }
        })
    }
    loadData()

    const onSearch = () => {
        categoryQuery.page = 1
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
            categoryApi.deleteById(id).then(result => {
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
            categoryApi.deleteAll(ids.value).then(result => {
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
    const category = ref({})
    const title = ref('')

    const showAddDialog = () => {
        dialogFormVisible.value = true
        title.value = '添加'
        category.value= {}
    }

    const showUpdateDialog = (id) => {
        dialogFormVisible.value = true
        title.value = '编辑'
        category.value = {}
        categoryApi.selectById(id).then(result => {
            category.value = result.data
        })
    }
    const addOrUpdate = () => {
        if (category.value.id) {//编辑
            categoryApi.update(category.value).then(result => {
                if (result.code == 0) {
                    ElMessage.success(result.msg)
                    dialogFormVisible.value = false
                    loadData()
                } else {
                    ElMessage.error(result.msg)
                }
            })
        } else {//添加
            categoryApi.add(category.value).then(result => {
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
                <el-input v-model="categoryQuery.name" placeholder="请输入名字" clearable/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="list" style="width: 100%" ref="multipleTableRef" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column fixed prop="id" label="类别Id"/>
            <el-table-column prop="name" label="类别名称"/>
            <el-table-column prop="parentId" label="父类别id"/>
            <el-table-column prop="status" label="状态"/>
            <el-table-column prop="deleted" label="逻辑删除"/>
            <el-table-column fixed="right" label="Operations">
                <template #default="{ row }">
                    <el-button type="primary" @click="showUpdateDialog(row.id)">编辑</el-button>
                    <el-button type="danger" @click="deleteById(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            v-model:current-page="categoryQuery.page"
            v-model:page-size="categoryQuery.limit"
            :page-sizes="[10, 20, 30, 40]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @change="loadData"
            style="margin-top: 20px; justify-content: flex-end"
        />
    </el-card>

    <!--添加、编辑弹出框-->
    <el-dialog v-model="dialogFormVisible" :title="title" width="60%" :lock-scroll="false">
        <el-form :model="category">
            <el-form-item label="类别名称" :label-width="60">
                <el-input v-model="category.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="父类别id" :label-width="90">
                <el-input v-model="category.parentId" autocomplete="off" />
            </el-form-item>
            <el-form-item label="状态" :label-width="60">
                <el-input v-model="category.status" autocomplete="off" />
            </el-form-item>
            <el-form-item label="逻辑删除" :label-width="70">
                <el-input v-model="category.deleted" autocomplete="off" />
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