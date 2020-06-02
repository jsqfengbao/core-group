<template>
    <div :style="{'width':width==null?'auto':width+'px'}">
        <v-chart :forceFit="width==null" :height="height" :data="data" padding="0">
            <v-scale y :options="yOptions" />
            <v-tooltip disabled />
            <v-pie :radius="0.85" series-field="name" />
            <v-legend :options="legendOptions" />
        </v-chart>
    </div>
</template>

<script type="text/ecmascript-6">
  import { VChart, VLine, VArea, VTooltip, VLegend, VBar, VPie, VScale } from 'vux'

  const map = {
    '芳华': '40%',
    '妖猫传': '20%',
    '机器之血': '18%',
    '心理罪': '15%',
    '寻梦环游记': '5%',
    '其他': '2%'
  }

  export default {
    props: {
      width: {
        type: Number,
        default: null
      },
      height: {
        type: Number,
        default: 200
      }
    },
    components: {
      VChart,
      VLine,
      VArea,
      VTooltip,
      VLegend,
      VBar,
      VPie,
      VScale
    },
    data () {
      return {
        legendOptions: {
          position: 'right',
          itemFormatter (val) {
            return val + '  ' + map[val]
          }
        },
        yOptions: {
          formatter (val) {
            return val * 100 + '%'
          }
        },
        map,
        data: [
          { name: '芳华', percent: 0.4, a: '1' },
          { name: '妖猫传', percent: 0.2, a: '1' },
          { name: '机器之血', percent: 0.18, a: '1' },
          { name: '心理罪', percent: 0.15, a: '1' },
          { name: '寻梦环游记', percent: 0.05, a: '1' },
          { name: '其他', percent: 0.02, a: '1' }
        ]
      }
    }
  }
</script>

<style lang="scss" scoped>
    @import "chart";
</style>
