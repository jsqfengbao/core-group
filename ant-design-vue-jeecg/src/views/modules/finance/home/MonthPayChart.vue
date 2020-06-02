<template>
    <div :style="{ padding: '0 0 32px 32px' }">
        <h4 :style="{ marginBottom: '20px' }">{{ title }}</h4>
        <v-chart :data="data" :height="height" :force-fit="true" :onClick="handleClick">
            <v-tooltip/>
            <v-axis/>
            <v-legend/>
            <v-bar position="x*y" color="type" :adjust="adjust"/>
        </v-chart>
    </div>
</template>

<script>
  import { DataSet } from '@antv/data-set'
  import { ChartEventMixins } from './mixins/ChartMixins'
  import { httpAction } from '@/api/manage'

  export default {
    name: 'BarMultid',
    mixins: [ChartEventMixins],
    props: {
      title: {
        type: String,
        default: ''
      },
      dataSource: {
        type: Array,
        default: () => [
          // { type: '2019', '1月': 25996.55, '2月': 39966.5, '3月': 2825.98, '4月': 4623.6, '5月': 10721.57, '6月': 5507.21, '7月': 3282.15, '8月': 4575.71,'9月': 5594.05,'10月': 7463.01,'11月': 15179.7,'12月':6675.86 },
          // { type: '2020', '1月': 16635.26, '2月': 34318.48, '3月': 1486.73, '4月': 0, '5月': 0, '6月': 0, '7月': 0, '8月': 0,'9月': 0,'10月': 0,'11月': 0,'12月':0 }
        ]
      },
      fields: {
        type: Array,
        default: () => ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月','9月','10月','11月','12月']
      },
      // 别名，需要的格式：[{field:'name',alias:'姓名'}, {field:'sex',alias:'性别'}]
      aliases: {
        type: Array,
        default: () => []
      },
      height: {
        type: Number,
        default: 254
      }
    },
    data() {
      return {
        url: {
          getThisYearPerMonthPayDataUrl: '/finance/statis/getThisYearPerMonthPayData',
          getThisYearPerMonthIncomeDataUrl: '/finance/statis/getThisYearPerMonthIncomeData',
        },
        adjust: [{
          type: 'dodge',
          marginRatio: 1 / 32
        }]
      }
    },
    created() {
      this.getThisYearPerMonthPayData()
    },
    computed: {
      data() {
        const dv = new DataSet.View().source(this.dataSource)
        dv.transform({
          type: 'fold',
          fields: this.fields,
          key: 'x',
          value: 'y'
        })

        // bar 使用不了 - 和 / 所以替换下
        let rows = dv.rows.map(row => {
          if (typeof row.x === 'string') {
            row.x = row.x.replace(/[-/]/g, '_')
          }
          return row
        })
        // 替换别名
        rows.forEach(row => {
          for (let item of this.aliases) {
            if (item.field === row.type) {
              row.type = item.alias
              break
            }
          }
        })
        return rows
      }
    },
    methods: {
      getThisYearPerMonthPayData () {
        let httpurl = this.url.getThisYearPerMonthPayDataUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res) => {
          if(res.success) {
            let preYearMonthMoney = res.result.preYearMonthMoney
            let thisYearMonthMoney = res.result.thisYearMonthMoney

            this.dataSource.push(preYearMonthMoney)
            this.dataSource.push(thisYearMonthMoney)
          }else{
            this.$message.warning(res.message)
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
