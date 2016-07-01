package com.cardpay.pccredit.QZBankInterface.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.QZBankInterface.model.Circle;
import com.cardpay.pccredit.divisional.model.Divisional;
import com.cardpay.pccredit.intopieces.model.QuotaProcessSx;
import com.cardpay.pccredit.intopieces.service.QuotaFreezeOrThawService;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.dc.eai.data.Array;
import com.dc.eai.data.CompositeData;
import com.dc.eai.data.Field;
import com.dc.eai.data.FieldAttr;
import com.dc.eai.data.FieldType;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.modules.privilege.model.User;

@Service
public class IESBForSXInfo {
	@Autowired
	private CommonDao commonDao;
	
    /**
     * 组装CompositeData报文
     * @return
     */
    public CompositeData createRequest(String type,Circle circle,QuotaProcessSx process,Divisional divisional){
    	SimpleDateFormat formatter2 = new SimpleDateFormat("dd");
    	SimpleDateFormat formatter8 = new SimpleDateFormat("yyyyMMdd");
    	SimpleDateFormat formatter10 = new SimpleDateFormat("yyyy-MM-dd");
    	
        CompositeData cd = new CompositeData();

        //SYS_HEAD
        CompositeData syaHead_struct = new CompositeData();
        //在syaHead_struct中加SERVICECODE
        Field serviceCodeField = new Field(new FieldAttr(FieldType.FIELD_STRING, 11));
        serviceCodeField.setValue("02002000010");//
        syaHead_struct.addField("SERVICE_CODE", serviceCodeField);

        //在syaHead_struct中加SERVICESCENE
        Field serviceSceneField = new Field(new FieldAttr(FieldType.FIELD_STRING, 2));
        serviceSceneField.setValue("11"); //对应服务场景
        syaHead_struct.addField("SERVICE_SCENE", serviceSceneField);
        
       //在syaHead_struct中加TRAN_DATE
        Field tran_datefield = new Field(new FieldAttr(FieldType.FIELD_STRING, 8));
        tran_datefield.setValue(formatter8.format(new Date())); //交易日期
        syaHead_struct.addField("TRAN_DATE", tran_datefield);
        
        //在syaHead_struct中加CONSUMER_ID
        Field consumer_idField = new Field(new FieldAttr(FieldType.FIELD_STRING, 6));
        consumer_idField.setValue("300025"); //消费系统编号
        syaHead_struct.addField("CONSUMER_ID", consumer_idField);
        
        cd.addStruct("SYS_HEAD", syaHead_struct);

        //BODY
        CompositeData body_struct = new CompositeData();

        //客户号
        Field CLIENT_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 20));
        CLIENT_NO.setValue(circle.getClientNo());
        body_struct.addField("CLIENT_NO", CLIENT_NO);
        
        Field OPERATION_TYPE=new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
        OPERATION_TYPE.setValue(type);
        body_struct.addField("OPERATION_TYPE", OPERATION_TYPE);
        

    	CompositeData CLIENT_BELONG_INFO_STRUCT = new CompositeData();
        if(type.equals("01")){
            Field ACCT_EXEC=new Field(new FieldAttr(FieldType.FIELD_STRING, 10));
            User user = commonDao.findObjectById(User.class, divisional.getCustomerManagerId());
            ACCT_EXEC.setValue(user.getLogin());
            CLIENT_BELONG_INFO_STRUCT.addField("ACCT_EXEC", ACCT_EXEC);
            Field HIGHER_ORG_NO=new Field(new FieldAttr(FieldType.FIELD_STRING, 32));
            HIGHER_ORG_NO.setValue(divisional.getCurrentOrganizationId());
            CLIENT_BELONG_INFO_STRUCT.addField("HIGHER_ORG_NO", HIGHER_ORG_NO);
        }

    	CompositeData PERSON_CLIENT_INFO_STRUCT = new CompositeData();
        if(type.equals("02")){ 
            Field GLOBAL_TYPE=new Field(new FieldAttr(FieldType.FIELD_STRING, 5));
            GLOBAL_TYPE.setValue("0");
            PERSON_CLIENT_INFO_STRUCT.addField("GLOBAL_TYPE", GLOBAL_TYPE);
            Field GLOBAL_ID=new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
            GLOBAL_ID.setValue(circle.getGlobalId());
            PERSON_CLIENT_INFO_STRUCT.addField("GLOBAL_ID", GLOBAL_ID);
            Field REG_PERM_RESIDENCE=new Field(new FieldAttr(FieldType.FIELD_STRING, 60));
            REG_PERM_RESIDENCE.setValue(process.getRegPermResidence());
            PERSON_CLIENT_INFO_STRUCT.addField("REG_PERM_RESIDENCE", REG_PERM_RESIDENCE);
            Field ADDRESS=new Field(new FieldAttr(FieldType.FIELD_STRING, 300));
            ADDRESS.setValue(process.getAddress());
            PERSON_CLIENT_INFO_STRUCT.addField("ADDRESS", ADDRESS);
            Field MOBILE=new Field(new FieldAttr(FieldType.FIELD_STRING, 30));
            MOBILE.setValue(process.getMobile());
            PERSON_CLIENT_INFO_STRUCT.addField("MOBILE", MOBILE);
        }
        
        body_struct.addStruct("CLIENT_BELONG_INFO_STRUCT",CLIENT_BELONG_INFO_STRUCT);
        body_struct.addStruct("PERSON_CLIENT_INFO_STRUCT",PERSON_CLIENT_INFO_STRUCT);

        cd.addStruct("BODY",body_struct);
        
        return cd;
    }
    
    /**
     * 返回成功与否
     * @param resp
     * @return
     * @throws Exception 
     */
	public String parseResponse(CompositeData resp){
		if(resp == null){
			return "ESB接口调用失败";//接口调用失败
		}
		CompositeData SYS_HEAD = resp.getStruct("SYS_HEAD");

        //根据数组名称去获取数组
        Array RET = SYS_HEAD.getArray("RET");
        
        String RET_CODE = "";
        String RET_MSG = "";
        if(null != RET && RET.size() > 0){
            int m = RET.size();
            CompositeData array_element = null;
            for (int i = 0; i < m; i++) {
                //数组中的元素也是CompositeData，这是固定的写法。根据游标就可以获取到数组中的所有元素
                array_element = RET.getStruct(i);

                RET_CODE = array_element.getField("RET_CODE").strValue();
                RET_MSG = array_element.getField("RET_MSG").strValue();
            }
        }
        //存在前一笔授信协议
        if(IpadConstant.RET_CODE_SUCCESS.equals(RET_CODE)){
        	return IpadConstant.RET_CODE_SUCCESS;
        }else{
        	return RET_MSG;
        }
	}
	
}
