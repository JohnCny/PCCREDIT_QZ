package com.cardpay.pccredit.system.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wicresoft.jrad.modules.privilege.business.DeptUserManager;
import com.wicresoft.jrad.modules.privilege.business.UserManager;
import com.wicresoft.jrad.modules.privilege.filter.DepartmentFilter;
import com.wicresoft.jrad.modules.privilege.filter.OrganizationFilter;
import com.wicresoft.jrad.modules.privilege.model.Department;
import com.wicresoft.jrad.modules.privilege.model.DeptUser;
import com.wicresoft.jrad.modules.privilege.model.Organization;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.DepartmentService;
import com.wicresoft.jrad.modules.privilege.service.OrganizationService;
import com.wicresoft.jrad.modules.dao.modulesComdao;

@Service
public class ImportQZUserData {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private DeptUserManager deptUserManager;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private modulesComdao modulesComdao;
	
	public void insertUser() throws Exception{
		File file = new File("E://乾康文件//用户机构数据.xls");
		String[][] result = getData(file, 1);
		
		int rowLength  = result.length;
		for(int i = 0;i < rowLength;i++){
			List<User> tempList = modulesComdao.checkExternalIdDupInsert(result[i][1].trim());
			if(tempList != null && tempList.size()>0){
				System.out.println("工号"+result[i][1]+"已存在  ,跳过");
				continue;
			}
			User user = new User();
			user.setUserType(1);//用户类型 1客户经理
			user.setExternalId(result[i][1].trim());//工号
			user.setLogin(result[i][1].trim());//工号登陆
			user.setDisplayName(result[i][2].trim());//姓名
			user.setGender("男");//默认男
			user.setIsDeleted(false);//是否注销
			
			//insert 关联角色为客户经理
			String userId = userManager.insertUser(user, "402880f9493b762e01493c00562e0009");
			
			//查找用户对应机构
			//OrganizationFilter filter = new OrganizationFilter();
			//filter.setOrganizationId(result[i][5].trim());
			//List<Organization> res = organizationService.findOrganizationByFilter(filter).getItems();
			Organization org = organizationService.getOrganizationById(result[i][5].trim());
			//if(res != null && res.size() > 0){
			//	for(Organization org :res){
					//if(org.getId().equals(result[i][5].trim())){
						//查找机构下的唯一部门
						DepartmentFilter tmpFilter = new DepartmentFilter();
						tmpFilter.setOrgId(result[i][5].trim());
						List<Department> tmpres = departmentService.findDepartmentFilter(tmpFilter).getItems();
						if(tmpres != null && tmpres.size()>0){
							Department dep = tmpres.get(0);
							System.out.println("dep:"+dep.getName());
							//insert 用户部门关联表
							DeptUser du = new DeptUser();
							du.setDeptId(dep.getId());
							du.setUserId(userId);
							userManager.insertDeptUser(du);
						}
						//break;
					//}
			//	}
			//}
			
			
		}
	}
	
	/**
	 * 读取数据
	 * @param file
	 * @param ignoreRows
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getData(File file,int ignoreRows) throws FileNotFoundException,IOException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		//打开HSSWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		//for(int sheetIndex = 0;sheetIndex < wb.getNumberOfSheets();sheetIndex++){
		//机构在第0个sheet
		for(int sheetIndex = 1;sheetIndex < 2;sheetIndex++){
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			//第一行为标题 ，不取
			for(int rowIndex = ignoreRows;rowIndex <= st.getLastRowNum();rowIndex++){
				HSSFRow row = st.getRow(rowIndex);
				if(row == null){
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if(tempRowSize > rowSize){
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for(short columnIndex = 0;columnIndex <= row.getLastCellNum();columnIndex++){
					String value = "";
					cell = row.getCell(columnIndex);
					if(cell != null){
						//注意：一定要设置成这个，否则会出现乱码
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch(cell.getCellType()){
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								Date date = cell.getDateCellValue();
								if(date != null){
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							//导入时如果为公式生成的数据则无值
							if(!cell.getStringCellValue().equals("")){
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					if(columnIndex == 0 && value.trim().equals("")){
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
					
				}
				if(hasValue){
					result.add(values);
				}
			}
		}
		
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for(int i = 0;i < returnArray.length;i++){
			returnArray[i] = (String[]) result.get(i);
		}
		
		return returnArray;
	}
	
	/**
	 * 去空格
	 */
	public static String rightTrim(String str){
		if(str == null){
			return "";
		}
		
		int length = str.length();
		for(int i = length - 1;i >= 0;i--){
			if(str.charAt(i) != 0x20){
				break;
			}
			length--;
		}
		return str.substring(0,length);
	}
	
}
