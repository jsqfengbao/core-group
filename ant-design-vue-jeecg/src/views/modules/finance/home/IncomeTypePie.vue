<template>
    <!--<v-chart :forceFit="width==null" :height="height" :data="data" padding="0">
      <v-tooltip/>
      <v-bar position="x*y"/>
    </v-chart>-->
      <ve-pie
              :data="chartData"
              :settings="chartSettings"
              :events="chartEvents" style="top:160px;">
      </ve-pie>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import VePie from 'v-charts/lib/pie.common.js'
  export default {
    name: 'IncomeTypePie',
    components: {VePie},
    data() {
      this.chartSettings = {
        selectedMode: 'single',
        hoverAnimation: false,
        radius: 40
      }
      var self = this
      this.chartEvents = {
        click: function (e) {
          self.name = e.name
        }
      }
      return {
        chartData: {
          columns: ['fundTypeName', 'fundTypeMoney'],
          rows: []
        },
        name: ''
      }
    },
    created() {
      this.getThisMonthIncomeTypeData()
    },

    methods: {
      getThisMonthIncomeTypeData () {
        let httpurl = '/finance/statis/getThisMonthIncomeTypeData'
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.chartData.rows = res.result
          }else{
            this.$message.warning(res.message);
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  @import "chart";
</style>
