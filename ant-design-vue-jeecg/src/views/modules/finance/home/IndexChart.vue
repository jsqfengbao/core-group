<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="总支出额" :total="totalYearPay">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <trend flag="up" style="margin-right: 16px;">
              <span slot="term">年预算</span>
              {{totalYearBudgetPay}}
            </trend>
            <trend flag="down">
              <span slot="term">支出占比</span>
              {{payBudgetRatio}}
            </trend>
          </div>
          <template slot="footer">月均支出额<span>{{everyMonthPayRatio}}</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当月支出" :total="thisMonthPayTotalMoney">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-area/>
          </div>
          <template slot="footer">支出种类<span> {{ payTypeNum }}</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="当月收入" :total="thisMonthIncomeTotalMoney">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
              <!--<IncomeType :height="80" :width="200"></IncomeType>-->
            <income-type-pie :height="40"/>
          </div>
          <template slot="footer">工资占比 <span>{{salaryRatio}}</span></template>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="年收入总额" :total="totalYearIncome">
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <mini-progress color="rgb(19, 194, 194)" :target="100" :percentage="incomeBudgetRatio" :height="8" />
          </div>
          <template slot="footer">
            <trend flag="up">
              <span slot="term">月均收入</span>
              {{everyMonthIncomeRatio}}
            </trend>
          </template>
        </chart-card>
      </a-col>
    </a-row>

    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <a-tab-pane loading="true" tab="支出额" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                  <MonthPayChart></MonthPayChart>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="当月支出前七排行榜" :list="thisMonthHeadPayMoney"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="收入额" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                  <MonthIncomeChart></MonthIncomeChart>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="当月收入额排行榜" :list="thisMonthHeadIncomeMoney"/>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>
  </div>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from './MiniArea'
  import IncomeTypePie from './IncomeTypePie'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from './RankList'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import VeHistogram from 'v-charts/lib/histogram.common.js'
  import BarAndLine from '@/components/chart/BarAndLine'
  import BarMultid from '@/components/chart/BarMultid'

  import Trend from '@/components/Trend'
  import MonthPayChart from './MonthPayChart'
  import MonthIncomeChart from './MonthIncomeChart'
  import IncomeType from './IncomeType'

  export default {
    name: "IndexChart",
    components: {
      IncomeType,
      BarAndLine,
      MonthPayChart,
      MonthIncomeChart,
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      'income-type-pie': IncomeTypePie,
      MiniProgress,
      RankList,
      Bar,
      Trend,
      LineChartMultid,
      HeadInfo,
      VeHistogram,
      BarMultid,
      IncomeType
    },
    data() {
      this.chartSettingsPay = {
        xAxisType: 'value'
      }
      this.chartSettingsIncome = {
        xAxisType: 'value'
      }
      return {
        totalYearPay: '0', //总支出额度
        totalYearBudgetPay: '0', //年度预算总额
        payBudgetRatio: '0%',  //支出与预算比例
        everyMonthPayRatio: '0', //平均每月支出额度
        thisMonthPayTotalMoney: '0', //当月总支出
        thisMonthIncomeTotalMoney: '0', //当月总收入
        totalYearIncome: '0',   //总收入额度
        totalYearBudgetIncome: '0',  //年度收入预算总额
        incomeBudgetRatio: 0,   //收入与预算比例
        everyMonthIncomeRatio: '0', //平均每月收入额度
        thisMonthHeadPayMoney: [],
        thisMonthHeadIncomeMoney: [],
        salaryRatio: '0',//工资占比例
        loading: true,
        center: null,
        rankList: [],
        barData: [],
        loginfo:{},
        visitFields:['ip','visit'],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />,
        url: {
              getTotalPayUrl: '/finance/statis/getTotalPay',
              getTotalIncomeUrl: '/finance/statis/getTotalIncome',
              getThisMonthHeadPayMoneyUrl: '/finance/statis/getThisMonthHeadPayMoney',
              getThisMonthHeadIncomeMoneyUrl: '/finance/statis/getThisMonthHeadIncomeMoney',
              getThisMonthPayTotalMoneyUrl: '/finance/statis/getThisMonthPayTotalMoney',
              getThisMonthIncomeTotalMoneyUrl: '/finance/statis/getThisMonthIncomeTotalMoney'
          }
      }
    },
    mounted() {
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.getTotalPay()
      this.getTotalIncome()
      this.getThisMonthPayTotalMoney()
      this.getThisMonthIncomeTotalMoney()
      this.getThisMonthHeadPayMoney()
      this.getThisMonthHeadIncomeMoney()
      this.getThisMonthIncomeTypeData()
    },
    methods: {
      getTotalPay () {
        let httpurl = this.url.getTotalPayUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.totalYearPay = res.result.totalYearPay
            this.totalYearBudgetPay = res.result.totalYearBudgetPay
            this.payBudgetRatio = res.result.payBudgetRatio
            this.everyMonthPayRatio = res.result.everyMonthPayRatio
          }else{
            this.$message.warning(res.message);
          }
        })
      },
      getTotalIncome () {
        let httpurl = this.url.getTotalIncomeUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.totalYearIncome = res.result.totalYearIncome
            this.totalYearBudgetIncome = res.result.totalYearBudgetIncome
            this.incomeBudgetRatio = parseInt(res.result.incomeBudgetRatio.replace('%',''))
            this.everyMonthIncomeRatio = res.result.everyMonthIncomeRatio
          }else{
            this.$message.warning(res.message);
          }
        })
      },
      getThisMonthPayTotalMoney () {
        let httpurl = this.url.getThisMonthPayTotalMoneyUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res) => {
          if(res.success) {
            this.thisMonthPayTotalMoney = res.message
          }else{
            this.$message.warning(res.message)
          }
        })
      },
      getThisMonthIncomeTotalMoney () {
        let httpurl = this.url.getThisMonthIncomeTotalMoneyUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res) => {
          if(res.success) {
            this.thisMonthIncomeTotalMoney = res.message
          }else{
            this.$message.warning(res.message)
          }
        })
      },
      getThisMonthHeadPayMoney () {
        let httpurl = this.url.getThisMonthHeadPayMoneyUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res) => {
          if(res.success) {
            this.thisMonthHeadPayMoney = res.result
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      getThisMonthHeadIncomeMoney () {
        let httpurl = this.url.getThisMonthHeadIncomeMoneyUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res) => {
          if(res.success) {
            this.thisMonthHeadIncomeMoney = res.result
          } else {
            this.$message.warning(res.message)
          }
        })
      },
      getThisMonthIncomeTypeData () {
        let httpurl = '/finance/statis/getThisMonthIncomeTypeData'
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            let results  = res.result
            for(let index = 0;index<results.length;index++){
              let fundTypeName = results[index].fundTypeName;
              let fundTypeMoney = results[index].fundTypeMoney;
              if(fundTypeName === '工资'){
                this.salaryRatio = (fundTypeMoney / this.thisMonthIncomeTotalMoney).toFixed(4)* 100+'%'
              }
            }
          }else{
            this.$message.warning(res.message);
          }
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>
