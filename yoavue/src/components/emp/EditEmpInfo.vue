<template>
    <div>
        <el-drawer
                title="我是标题"
                :visible.sync="drawer"
                :direction="direction"
                :before-close="handleClose">
            <span>我来啦!</span>
        </el-drawer>
    </div>
</template>

<script>
    export default {
        name: "EditEmpInfo",
        data() {
            return {
                drawer: this.$store.state.drawer,
                direction: 'rtl',
            };
        },
        methods: {
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            alterDrawer(res){
                this.drawer = res
            }
        }
    }
</script>

<style scoped>

</style>