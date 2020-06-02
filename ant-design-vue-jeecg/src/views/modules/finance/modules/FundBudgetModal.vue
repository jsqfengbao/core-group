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

        <a-form-item label="资金类型名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select v-decorator="['fundTypeId', validatorRules.fundTypeName]" placeholder="请选择资金类型">
                <a-select-option v-for="type in fundTypeList" :key="type.id" :value="type.id">{{type.name}}</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="预算金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'money', validatorRules.money]" placeholder="请输入预算金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="预算月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'monthNum', validatorRules.monthNum]" placeholder="请输入预算月份" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="预算年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'yearNum', validatorRules.yearNum]" placeholder="请输入预算年份" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "FundBudgetModal",
    components: {
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
          fundTypeName: {rules: [
            {required: true, message: '请输入资金类型名!'},
          ]},
          money: {rules: [
            {required: true, message: '请输入预算金额!'},
           {pattern:/^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},
          ]},
          monthNum: {rules: [
            {required: true, message: '请输入预算月份!'},
            {pattern:/^-?\d+$/, message: '请输入整数!'},
          ]},
          yearNum: {rules: [
            {required: true, message: '请输入预算年份!'},
           {pattern:/^-?\d+\.?\d*$/, message: '请输入数字!'},
          ]},
        },
        url: {
          add: "/finance/fundBudget/add",
          edit: "/finance/fundBudget/edit",
          getFundTypeUrl: "/finance/fundType/list?type=1&pageNum=20"
        }
      }
    },
    created () {
      this.getFundType()
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
          this.form.setFieldsValue(pick(this.model,'fundTypeName','money','monthNum','yearNum'))
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
        this.form.setFieldsValue(pick(row,'fundTypeName','money','monthNum','yearNum'))
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
