<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="支出类型名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <!--<j-dict-select-tag v-decorator="['fundTypeId',validatorRules.fundTypeId]" placeholder="请选择资金类型" dictCode="fund_type,name,id,type='1'"></j-dict-select-tag>-->
            <a-select v-decorator="['fundTypeId', validatorRules.fundTypeName]" placeholder="请选择资金类型">
                <a-select-option v-for="type in fundTypeList" :key="type.id" :value="type.id">{{type.name}}</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'content', validatorRules.content]" placeholder="请输入内容"></a-input>
        </a-form-item>
        <a-form-item label="金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'money', validatorRules.money]" placeholder="请输入金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="支付方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['payType', validatorRules.payType]" :trigger-change="true" dictCode="payWay" placeholder="请选择支付方式"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="支付时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择支付时间" v-decorator="[ 'payTime', validatorRules.payTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="是否有效" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag type="radio" v-decorator="['enabled', {}]" :trigger-change="true" dictCode="enabledOrNot" :defaultValue="1"/>
          <!--<a-input-number v-decorator="[ 'enabled', validatorRules.enabled]" placeholder="请输入是否有效" style="width: 100%"/>-->
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import Moment from 'moment'

  export default {
    name: "FundPayModal",
    components: {
      JDate,
      JDictSelectTag,
    },
    data () {
      return {
        fundTypeList: [],
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          fundTypeName: {
            rules: [{required: true, message: '请输入支出类型名!'}],
            initialValue: '1235218856326987777'
          },
          content: {rules: []},
          money: {rules: [
            {required: true, message: '请输入金额!'},
           {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ]},
          payType: {rules: [{required: true, message: '请输入支付方式!'}],initialValue: 'alipay'},
          remark: {rules: []},
          payTime: {rules: [{required: true, message: '请输入支付时间!'}],
              initialValue: Moment(Date.now()).format('HH-MM-SS HH:mm:ss')+""
          },
          enabled: {rules: [],initialValue: '1'},
        },
        url: {
          add: "/finance/fundPay/add",
          edit: "/finance/fundPay/edit",
          getFundTypeUrl: "/finance/fundType/list?type=1&pageNum=20"
        }
      }
    },
    created () {
      this.getFundType()
      console.log(Moment(Date.now()).format('HH-MM-SS HH:mm:ss'))
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'fundTypeName','content','money','payType','remark','payTime','enabled'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }

        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'fundTypeName','content','money','payType','remark','payTime','enabled'))
      },
      getFundType () {
        let httpurl = this.url.getFundTypeUrl
        let formData = {}
        let method = "get"
        httpAction(httpurl,formData,method).then((res)=>{
          if(res.success){
            this.fundTypeList = res.result.records
          }else{
            this.$message.warning(res.message);
          }
        })
      }
    }
  }
</script>
