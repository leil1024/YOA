<template>
    <div>
        <el-carousel indicator-position="outside" height="440px">
            <el-carousel-item v-for="item in CarouselUrl" :key="item">
<!--                <h3>{{ item }}</h3>-->
                <img :src="item" style="width: 100%;height: 440px"/>
            </el-carousel-item>
        </el-carousel>>
        <div style="position: absolute;left: 71%;top: 25%">
            <el-progress type="dashboard" :percentage="percentage" :color="colors"></el-progress>
            <div>
                <el-button-group>
                    <el-button icon="el-icon-minus" @click="decrease"></el-button>
                    <el-button icon="el-icon-plus" @click="increase"></el-button>
                </el-button-group>
            </div>
        </div>
        <div style="position: absolute;left: 85%;top: 25%">
            <el-progress type="dashboard" :percentage="percentage" :color="colors"></el-progress>
            <div>
                <el-button-group>
                    <el-button icon="el-icon-minus" @click="decrease"></el-button>
                    <el-button icon="el-icon-plus" @click="increase"></el-button>
                </el-button-group>
            </div>
        </div>
        <div style="position: absolute;left: 71%;top: 65%">
            <el-progress type="dashboard" :percentage="percentage" :color="colors"></el-progress>
            <div>
                <el-button-group>
                    <el-button icon="el-icon-minus" @click="decrease"></el-button>
                    <el-button icon="el-icon-plus" @click="increase"></el-button>
                </el-button-group>
            </div>
        </div>
        <div style="position: absolute;left: 85%;top: 65%">
            <el-progress type="dashboard" :percentage="percentage" :color="colors"></el-progress>
            <div>
                <el-button-group>
                    <el-button icon="el-icon-minus" @click="decrease"></el-button>
                    <el-button icon="el-icon-plus" @click="increase"></el-button>
                </el-button-group>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Carousel",
        data(){
            return{
                CarouselUrl:[
                    "http://localhost:9001/home/2.webp",
                    "http://localhost:9001/home/3.webp",
                    "http://localhost:9001/home/4.webp",
                    "http://localhost:9001/home/6.webp",
                ],
                percentage: 10,
                colors: [
                    {color: '#f56c6c', percentage: 20},
                    {color: '#e6a23c', percentage: 40},
                    {color: '#5cb87a', percentage: 60},
                    {color: '#1989fa', percentage: 80},
                    {color: '#6f7ad3', percentage: 100}
                ]
            }
        },
        methods:{
            increase() {
                this.percentage += 10;
                if (this.percentage > 100) {
                    this.percentage = 100;
                }
            },
            decrease() {
                this.percentage -= 10;
                if (this.percentage < 0) {
                    this.percentage = 0;
                }
            }
        }
    }
</script>

<style lang="scss" scoped>

    .el-carousel__item h3 {
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }

    /*.el-carousel__container {*/
    /*    position: relative;*/
    /*    height: 440px;*/
    /*}*/

    .el-carousel--horizontal {
        overflow-x: hidden;
        width: 700px;
    }

</style>