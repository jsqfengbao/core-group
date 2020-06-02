<template>
  <!--<div class="antv-chart-mini">-->
    <!--<div class="chart-wrapper" :style="{ height: 46 }">-->
      <!--<v-chart :force-fit="true" :height="height" :data="data" :scale="scale" :padding="[36, 0, 18, 0]">-->
        <!--<v-tooltip/>-->
        <!--<v-smooth-area position="fundTypeName*fundTypeMoney"/>-->
      <!--</v-chart>-->
    <!--</div>-->
  <!--</div>-->
    <ve-ring :data="thisMonthPayTypeData" :settings="chartSettings" style="top:160px;"></ve-ring>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import VeRing from 'v-charts/lib/ring.common.js'

  export default {
    name: 'MiniArea',
    components: {VeRing},
    data() {
      return {
        chartSettings: {
          radius: [10,40]
        },
        ringExtend: {
          series: {
            center: [160,20,0,20]
          }
        },
        thisMonthPayTypeData: {
          columns: ['fundTypeName', 'fundTypeMoney'],
          rows: []
        },
        height: 100,
        url: {
          getThisMonthPayTypeDataUrl: '/finance/statis/getThisMonthPayTypeData',
        }
      }
    },
    created() {
      this.getThisMonthPayTypeData()
    },
    methods: {
      getThisMonthPayTypeData() {
        let httpurl = this.url.getThisMonthPayTypeDataUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.thisMonthPayTypeData.rows = res.result
          }else{
            this.$message.warning(res.message);
          }
        })
      },
    }
  }
</script>

<style lang="scss" scoped>
  @import "chart";
</style>
