package com.pjt.survey.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.survey.dao.MakeVODao;
import com.pjt.survey.vo.CodeVO;
import com.pjt.survey.vo.ComVrfcBaseDtlVo;
import com.pjt.survey.vo.ComVrfcBaseVo;
import com.pjt.survey.vo.SqlMapVO;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Service
public class MakeVOService {
	
	@Autowired
	private MakeVODao makeVODao;
	
	/**
	 * make vo
	 * @return
	 */
	public HashMap selectMakeVO(Map<?, ?> commandMap){ 

		List<SqlMapVO> colList = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		HashMap retMap = new HashMap();
		String pkgPath = (String)commandMap.get("pkgPath");
		String addItem = (String)commandMap.get("addItem");
		String tblId = (String)commandMap.get("tblId");
		String arrayItem = (String)commandMap.get("arrayItem");
		String checkTrgt = (String)commandMap.get("checkTrgt");
		System.out.println("pkgPath ["+pkgPath+"]");
		String[] voNmArr = pkgPath.split("\\.");
		
		String[] addItemArr = null;
		String[] addItemDetlArr = null;
		String[] arrayItemArr = null;
		
		if(!"".equals(addItem)) {
			addItemArr = addItem.split("\\,");
			//System.out.println("addItemArr length ["+addItemArr.length+"]");
		}
		if(!"".equals(arrayItem)) {
			arrayItemArr = arrayItem.split("\\,");
			//System.out.println("addItemArr length ["+addItemArr.length+"]");
		}
		
		String tblNm = tblId.substring(3).substring(0, 1).toUpperCase()+this.convert2CamelCase(tblId.substring(4));
		String voNm = /*convert2CamelCase(voNmArr[1]+"_"+voNmArr[2]).substring(0, 1).toUpperCase() + convert2CamelCase(voNmArr[1]+"_"+voNmArr[2]).substring(1) + */ tblNm + "Vo";
		
		//테이블 컬럼 목록 조회
		SqlMapVO sqlMapVO = new SqlMapVO();
		sqlMapVO.setTblId(tblId);
		sqlMapVO.setVrfcTblId(tblId);
		
		System.out.println("service tblId >>> ["+sqlMapVO.getTblId()+"]");
		System.out.println("service checkTrgt >>> ["+checkTrgt+"]");
		System.out.println("voNm >>> ["+voNm+"]");
		if ( checkTrgt != null && checkTrgt.equals("I") ) {
			colList = makeVODao.selectMakeVO_IN(sqlMapVO);
		} else {
			colList = makeVODao.selectMakeVO(sqlMapVO);
		}

		SqlMapVO colVO = null;
		String type = "";
		String colNm = "";
		String addCol = "";
		String addColId = "";
		String addColComment = "";
		String addColType = "";
		
		String koCmmt = "";
		String colId = "";
		String frCmmt = "";
		String commentStr = "";
		
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();
		
		try{

			cdMap = this.getComment();
						
			sb.append("import { DefaultVo } from './../../../cmm/model/DefaultVo';\n");
			sb.append("import { ValidUtil } from '../../../cmm/utils/ValidUtil';\n");
			//sb.append("import { CmmUploadInf } from '../../../cmm/components/cmm-file-upload/cmm-upload-inf';\n");
			sb.append("\n");
			
			sb.append("/**\n");
			sb.append("* <PRE>\n");
			sb.append("* ").append(cdMap.get(tblId).getFrCmmt()).append("\n");
			sb.append("* !==\n");
			sb.append("* 테이블 정보를 담는 Vo ( ").append(cdMap.get(tblId).getKoCmmt()).append(" )\n");
			sb.append("* ==!\n");
			sb.append("* </PRE>\n");
			sb.append("*\n");
//			sb.append("* @author Kyungjun,Jang\n");
			sb.append("* @author Sangkoo,Song\n");
			sb.append("* @version 1.0\n");
			sb.append("* @since 25-07-2019\n");
			sb.append("*\n");
			sb.append("*/\n");
			
			sb.append("export class ").append(voNm).append(" extends DefaultVo {\n");
			sb.append("\n");
			
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				frCmmt = colVO.getFrCmmt();
				koCmmt = colVO.getKoCmmt();
				
				if("NUMBER".equals(colVO.getDataType())) {
					type = "number";
				}else {
					type = "string";
				}
				
				colNm = colVO.getColumnName();
				
				if(!"".equals(frCmmt)){
					commentStr = "  /** "+frCmmt+" !== "+koCmmt+" ==! */";
				}else{
				
					codeVo = new CodeVO();
					
					if(cdMap.containsKey(colNm)){
						codeVo = cdMap.get(colNm);
						koCmmt = codeVo.getKoCmmt();
						frCmmt = codeVo.getFrCmmt();
						
						commentStr = "  /** "+frCmmt+" !== "+koCmmt+" ==! */";
					}else{
						
						koCmmt = colVO.getComments();
						commentStr = "  /** !== "+koCmmt+" ==! */";
					}
				}
				
				if(colNm.length() >= 4) {
					
					if("DT".equals(colNm.substring(colNm.length() - 2)) || "DTTM".equals(colNm.substring(colNm.length() - 4))) {
						sb.append("  public ").append(this.convert2CamelCase(colVO.getColumnName())).append("From: ").append(type).append(";").append(" // ").append(koCmmt).append("시작\n");
						sb.append("  public ").append(this.convert2CamelCase(colVO.getColumnName())).append("To: ").append(type).append(";").append(" // ").append(koCmmt).append("종료\n");
					}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
						sb.append("  public ").append(this.convert2CamelCase(colVO.getColumnName().substring(0, colNm.length() - 3))).append("Nm: ").append(type).append(";").append(" // ").append(koCmmt).append("명\n");
					}
					
				}
				
				//sb.append(rpad(sb.append("  public ").append(this.convert2CamelCase(colVO.getColumnName())).append(": ").append(type).append(";").toString(),40," ")).append("//").append(colVO.getComments()).append("\n");
				sb.append(commentStr).append("\n");
				sb.append("  public ").append(this.convert2CamelCase(colVO.getColumnName())).append(": ").append(type).append(";").append("\n");
				
			}
			//추가 항목
			if(addItemArr != null && addItemArr.length > 0) {
				
				for(int j = 0; j < addItemArr.length ; j++) {
					addCol = addItemArr[j];
					
					addItemDetlArr = addCol.split("\\|");
					
					for(int k = 0; k < addItemDetlArr.length ; k++) {
						addColId = addItemDetlArr[0];
						addColType = addItemDetlArr[1];
						addColComment = addItemDetlArr[2];
					}
					sb.append("  public ").append(this.convert2CamelCase(addColId)).append(": ").append(addColType.toLowerCase()).append(";").append(" // ").append(addColComment).append("\n");
				}
			}
			
			//체크박스 항목
			if(arrayItemArr != null && arrayItemArr.length > 0) {
				
				for(int j = 0; j < arrayItemArr.length ; j++) {
					addColId = arrayItemArr[j];

					sb.append("  public ").append(this.convert2CamelCase(addColId)).append(": ").append("Array<string>").append(";\n");
				}
			}
			
			
			sb.append("\n");
			sb.append("  constructor(){\n");
			sb.append("    super();\n");
			sb.append("\n");
			
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
								
				if("NUMBER".equals(colVO.getDataType())) {
					sb.append("    this.").append(this.convert2CamelCase(colVO.getColumnName())).append(" = 0;").append("\n");
				}else {
					
					colNm = colVO.getColumnName();
					if(colNm.length() >= 4) {
						
						if("DT".equals(colNm.substring(colNm.length() - 2)) || "DTTM".equals(colNm.substring(colNm.length() - 4))) {
							sb.append("    this.").append(this.convert2CamelCase(colVO.getColumnName())).append("From = '';").append("\n");
							sb.append("    this.").append(this.convert2CamelCase(colVO.getColumnName())).append("To = '';").append("\n");
						}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
							sb.append("    this.").append(this.convert2CamelCase(colVO.getColumnName().substring(0, colNm.length() - 3))).append("Nm = '';").append("\n");
						}
						
					}
					
					sb.append("    this.").append(this.convert2CamelCase(colVO.getColumnName())).append(" = '';").append("\n");
				}
				
			}
			
			//추가 항목
			if(addItemArr != null && addItemArr.length > 0) {
				
				for(int j = 0; j < addItemArr.length ; j++) {
					addCol = addItemArr[j];
					
					addItemDetlArr = addCol.split("\\|");
					
					for(int k = 0; k < addItemDetlArr.length ; k++) {
						addColId = addItemDetlArr[0];
						addColType = addItemDetlArr[1];
						addColComment = addItemDetlArr[2];
					}
					if("NUMBER".equals(addColType)) {
						sb.append("    this.").append(this.convert2CamelCase(addColId)).append(" = 0;").append("\n");
					}else if("BOOLEAN".equals(addColType)) { 
						sb.append("    this.").append(this.convert2CamelCase(addColId)).append(" = false;").append("\n");
				    }else {
						sb.append("    this.").append(this.convert2CamelCase(addColId)).append(" = '';").append("\n");
					}
				}
			}
			
			//체크박스 항목
			if(arrayItemArr != null && arrayItemArr.length > 0) {
				
				for(int j = 0; j < arrayItemArr.length ; j++) {
					addColId = arrayItemArr[j];

					sb.append("    this.").append(this.convert2CamelCase(addColId)).append(" = ").append("new Array<string>()").append(";\n");
				}
			}
			
			sb.append("  }\n");
			sb.append("\n");
			sb.append("  public searchValid = {\n");
			//sb.append("    searchKeywordFrom : ValidUtil.required,\n");
			//sb.append("    searchKeywordTo : ValidUtil.required,\n");
			

			String comma = "";
			
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);

				if(i == colList.size() -1) {
					comma = "";
				} else {
					comma = ",";
				}

				/*
				colNm = colVO.getColumnName();
				if(colNm.length() >= 4) {
					
					if("DT".equals(colNm.substring(colNm.length() - 2)) || "DTTM".equals(colNm.substring(colNm.length() - 4))) {
						sb.append("    ").append(this.convert2CamelCase(colVO.getColumnName())).append("From: ValidUtil.maxLength(").append(8).append(")").append(comma).append("\n");
						sb.append("    ").append(this.convert2CamelCase(colVO.getColumnName())).append("To: ValidUtil.maxLength(").append(8).append(")").append(comma).append("\n");
					}
					
				}
				*/
				
				sb.append("    ").append(this.convert2CamelCase(colVO.getColumnName())).append(": [ValidUtil.maxLength(").append(colVO.getDataLength()).append(")]").append(comma).append("\n");
			}
			
			sb.append("  };\n");
			sb.append("\n");
			sb.append("  public inputValid = {\n");
			
			
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				
				if(i == colList.size() -1) {
					comma = "";
				} else {
					comma = ",";
				}
				
				if("N".equals(colVO.getNullable())) {
					sb.append("    ").append(this.convert2CamelCase(colVO.getColumnName())).append(": [ValidUtil.required, ValidUtil.maxLength(").append(colVO.getDataLength()).append(")]").append(comma).append("\n");
				}else {
					sb.append("    ").append(this.convert2CamelCase(colVO.getColumnName())).append(": [ValidUtil.maxLength(").append(colVO.getDataLength()).append(")]").append(comma).append("\n");
				}
			}
			
			sb.append("  };\n");
			sb.append("}\n");
			
			
			if(pkgPath == null || "".equals(pkgPath)) {
				sb2.append("package dices.com.code.vo;//패키지는 변경해주세요.\n");
			} else {
				sb2.append("package ").append(pkgPath).append(".vo;\n");
			}
            sb2.append("\n");
            //체크박스 항목
			if(arrayItemArr != null && arrayItemArr.length > 0) {
            sb2.append("import import java.util.List;\n");
			}
            sb2.append("import java.math.BigDecimal;\n");
			sb2.append("import comn.fw.vo.ComnFwDefaultVo;\n");
            sb2.append("\n");
            
            sb2.append("/**\n");
			sb2.append("* ").append(cdMap.get(tblId).getFrCmmt()).append("\n");
			sb2.append("* !==\n");
			sb2.append("* 테이블 정보를 담는 Vo ( ").append(cdMap.get(tblId).getKoCmmt()).append(" )\n");
			sb2.append("* ==!\n");
            sb2.append("*\n");
//            sb2.append("* @author Kyungjun,Jang\n");
			sb2.append("* @author Sangkoo,Song\n");
            sb2.append("* @version 1.0\n");
            sb2.append("* @since 25-07-2019\n");
            sb2.append("*\n");
            sb2.append("*/\n");
            
			sb2.append("public class ").append(voNm).append(" extends ComnFwDefaultVo {\n");
            sb2.append("\n");
			sb2.append("	private static final long serialVersionUID = 1L;\n");
            sb2.append("\n");
            
            for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				frCmmt = colVO.getFrCmmt();
				koCmmt = colVO.getKoCmmt();
				
				if("NUMBER".equals(colVO.getDataType())) {
					type = "BigDecimal";
				}else {
					type = "String";
				}
				
				colNm = colVO.getColumnName();
				
				if(!"".equals(frCmmt)){
					commentStr = "	/** "+frCmmt+" !== "+koCmmt+" ==! */";
				}else{
				
					codeVo = new CodeVO();
					
					if(cdMap.containsKey(colNm)){
						codeVo = cdMap.get(colNm);
						koCmmt = codeVo.getKoCmmt();
						frCmmt = codeVo.getFrCmmt();
						
						commentStr = "	/** "+frCmmt+" !== "+koCmmt+" ==! */";
					}else{
						
						koCmmt = colVO.getComments();
						commentStr = "	/** !== "+koCmmt+" ==! */";
					}
				
				}
				
				if(colNm.length() >= 4) {
					
					if("DT".equals(colNm.substring(colNm.length() - 2)) || "DTTM".equals(colNm.substring(colNm.length() - 4))) {
						sb2.append("	private ").append(type).append(" ").append(this.convert2CamelCase(colVO.getColumnName())).append("From;").append("//").append(koCmmt).append("시작\n");
						sb2.append("	private ").append(type).append(" ").append(this.convert2CamelCase(colVO.getColumnName())).append("To;").append("//").append(koCmmt).append("종료\n");
					}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
						sb2.append("	private ").append(type).append(" ").append(this.convert2CamelCase(colVO.getColumnName().substring(0, colNm.length() - 3))).append("Nm;").append("//").append(koCmmt).append("명\n");
					}
					
				}
				
				sb2.append(commentStr).append("\n");
				sb2.append("	private ").append(type).append(" ").append(this.convert2CamelCase(colVO.getColumnName())).append(";").append("\n");
			
            }
            
            //추가 항목
			if(addItemArr != null && addItemArr.length > 0) {
				
				for(int j = 0; j < addItemArr.length ; j++) {
					addCol = addItemArr[j];
					addItemDetlArr = addCol.split("\\|");
					
					for(int k = 0; k < addItemDetlArr.length ; k++) {
						addColId = addItemDetlArr[0];
						addColType = addItemDetlArr[1];
						addColComment = addItemDetlArr[2];
					}
					
					if("NUMBER".equals(addColType)) {
						type = "BigDecimal";
					} else if("BOOLEAN".equals(addColType)){
						type = "boolean";
					} else {
						type = "String";
					}
					
					sb2.append("	private ").append(type).append(" ").append(this.convert2CamelCase(addColId)).append(";").append("//").append(addColComment).append("\n");
				}
			}
			
			//체크박스 항목
			if(arrayItemArr != null && arrayItemArr.length > 0) {
				
				for(int j = 0; j < arrayItemArr.length ; j++) {
					addColId = arrayItemArr[j];
					sb2.append("	private List<String>").append(this.convert2CamelCase(addColId)).append(" = new ArrayList<String>();\n");
				}
			}
            
            sb2.append("\n");
            String id = "";
            String fId = "";
            for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				
				id = this.convert2CamelCase(colVO.getColumnName());
				fId = id.substring(0,1).toUpperCase() + id.substring(1);
				
				if("NUMBER".equals(colVO.getDataType())) {
					type = "BigDecimal";
				}else {
					type = "String";
				}
				
				colNm = colVO.getColumnName();
				if(colNm.length() >= 4) {
					
					if("DT".equals(colNm.substring(colNm.length() - 2)) || "DTTM".equals(colNm.substring(colNm.length() - 4))) {
						sb2.append("	public ").append(type).append(" get").append(fId).append("From() {\n");
						sb2.append("		return ").append(id).append("From;\n");
						sb2.append("	}\n");
						sb2.append("	public void set").append(fId).append("From(").append(type).append(" ").append(id).append("From) {\n");
						sb2.append("		this.").append(id).append("From = ").append(id).append("From;\n");
						sb2.append("	}\n");
						sb2.append("	public ").append(type).append(" get").append(fId).append("To() {\n");
						sb2.append("		return ").append(id).append("To;\n");
						sb2.append("	}\n");
						sb2.append("	public void set").append(fId).append("To(").append(type).append(" ").append(id).append("To) {\n");
						sb2.append("		this.").append(id).append("To = ").append(id).append("To;\n");
						sb2.append("	}\n");
					}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
						
						id = this.convert2CamelCase(colVO.getColumnName().substring(0, colNm.length() - 3));
						fId = id.substring(0,1).toUpperCase() + id.substring(1);
						
						sb2.append("	public ").append(type).append(" get").append(fId).append("Nm() {\n");
						sb2.append("		return ").append(id).append("Nm;\n");
						sb2.append("	}\n");
						sb2.append("	public void set").append(fId).append("Nm(").append(type).append(" ").append(id).append("Nm) {\n");
						sb2.append("		this.").append(id).append("Nm = ").append(id).append("Nm;\n");
						sb2.append("	}\n");
						
						id = this.convert2CamelCase(colVO.getColumnName());
						fId = id.substring(0,1).toUpperCase() + id.substring(1);
						
					}
					
				}
				
				sb2.append("	public ").append(type).append(" get").append(fId).append("() {\n");
				sb2.append("		return ").append(id).append(";\n");
				sb2.append("	}\n");
				sb2.append("	public void set").append(fId).append("(").append(type).append(" ").append(id).append(") {\n");
				sb2.append("		this.").append(id).append(" = ").append(id).append(";\n");
				sb2.append("	}\n");
				
            }
            
            //추가 항목
			if(addItemArr != null && addItemArr.length > 0) {
				
				for(int j = 0; j < addItemArr.length ; j++) {
					addCol = addItemArr[j];
					addItemDetlArr = addCol.split("\\|");
					
					for(int k = 0; k < addItemDetlArr.length ; k++) {
						addColId = addItemDetlArr[0];
						addColType = addItemDetlArr[1];
						addColComment = addItemDetlArr[2];
					}
					
					id = this.convert2CamelCase(addColId);
					fId = id.substring(0,1).toUpperCase() + id.substring(1);
					
					if("NUMBER".equals(addColType)) {
						type = "BigDecimal";
					} else if("BOOLEAN".equals(addColType)){
						type = "boolean";
					} else {
						type = "String";
					}
					
					sb2.append("	public ").append(type).append(" get").append(fId).append("() {\n");
					sb2.append("		return ").append(id).append(";\n");
					sb2.append("	}\n");
					sb2.append("	public void set").append(fId).append("(").append(type).append(" ").append(id).append(") {\n");
					sb2.append("		this.").append(id).append(" = ").append(id).append(";\n");
					sb2.append("	}\n");
					
				}
			}
			
			//체크박스 항목
			if(arrayItemArr != null && arrayItemArr.length > 0) {
				
				for(int j = 0; j < arrayItemArr.length ; j++) {
					addColId = arrayItemArr[j];

					id = this.convert2CamelCase(addColId);
					fId = id.substring(0,1).toUpperCase() + id.substring(1);
					
					type = "List<String>";
					
					sb2.append("	public ").append(type).append(" get").append(fId).append("() {\n");
					sb2.append("		return ").append(id).append(";\n");
					sb2.append("	}\n");
					sb2.append("	public void set").append(fId).append("(").append(type).append(" ").append(id).append(") {\n");
					sb2.append("		this.").append(id).append(" = ").append(id).append(";\n");
					sb2.append("	}\n");
				}
			}
            
			sb2.append("}\n");
			

			retMap.put("voStr", sb.toString());
			retMap.put("svVoStr", sb2.toString());
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return retMap;
	}
	
	/**
     * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
     * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
     * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
     * 함.)
     * @param underScore
     *        - '_' 가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    public static String convert2CamelCase(String underScore) {
 
        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
            && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();
 
        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
	
    private static String rpad(String orgstr, int length, String strToPad){

		while(orgstr.length() < length){
			orgstr = orgstr + strToPad;
		}

		return orgstr;
	}
    
	/**
	 * make sql
	 * @return
	 */
	public HashMap selectMakeSQL(Map<?, ?> commandMap) throws Exception{ 

		List<SqlMapVO> colList = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		StringBuffer sbPK = new StringBuffer();
		StringBuffer sbPK2 = new StringBuffer();
		StringBuffer sbCol = new StringBuffer();
		HashMap retMap = new HashMap();
		String inparam = (String)commandMap.get("inparam");
		String outparam = (String)commandMap.get("outparam");
		String gubun = (String)commandMap.get("gubun");
		
		String tblId = (String)commandMap.get("tblId");
		String checkTrgt = (String)commandMap.get("checkTrgt");
		String tblNm = tblId.substring(3).substring(0, 1).toUpperCase()+this.convert2CamelCase(tblId.substring(4));
		
		//테이블 컬럼 목록 조회
		SqlMapVO sqlMapVO = new SqlMapVO();
		sqlMapVO.setTblId(tblId);
		sqlMapVO.setVrfcTblId(tblId);
		
		System.out.println("service tblId >>> ["+sqlMapVO.getTblId()+"]");
		
		if (checkTrgt != null && checkTrgt.equals("I")) {
			colList = makeVODao.selectMakeVO_IN(sqlMapVO);
		} else {
			colList = makeVODao.selectMakeVO(sqlMapVO);
		}
		SqlMapVO colVO = null;
		String colNm = "";
		String contType = "";
		String koCmmt = "";
		String colId = "";
		String frCmmt = "";
		String commentStr = "";
		
		String mExcelFilePath = "C:\\Temp\\colInfo.xls";
		//String mExcelFilePath = "/Project/bin/dices/colInfo.xls";
		int mRows;
		//int mColumns;
		Workbook mWorkBook;
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();
				
		try{
			
			mWorkBook = Workbook.getWorkbook(new File(mExcelFilePath));
			
			Sheet sheet = mWorkBook.getSheet(2);
			mRows = sheet.getRows();
			//mColumns = sheet.getColumns();
			
			for(int i=0; i< mRows; i++){
				Cell cell0 = sheet.getCell(1,i);//한글 COMMENT
				Cell cell1 = sheet.getCell(4,i);//컬럼ID
				Cell cell2 = sheet.getCell(12,i);//불어 COMMENT
				koCmmt = cell0.getContents();//한글 COMMENT
				colId = cell1.getContents();//컬럼ID
				frCmmt = cell2.getContents();//불어 COMMENT
				
				codeVo = new CodeVO();
				codeVo.setColId(colId);
				codeVo.setKoCmmt(koCmmt);
				codeVo.setFrCmmt((frCmmt.replaceAll("N�", "N°")).replaceAll("�", "é"));
				
				if(!"".equals(colId)){
					cdMap.put(colId, codeVo);
					System.out.println(i + " colId = "+colId+" koCmmt = "+koCmmt+" frCmmt = "+frCmmt);
				}
				
			}
		
			sb.append("<select id=\""+gubun+"_select"+tblNm+"List\" parameterType=\""+inparam+"\" resultType=\""+outparam+"\">\n");
			sb.append("/* "+gubun+"_select"+tblNm+"List */\n");
			sb.append("    <include refid=\"commonInclude.pagerHeader\" />\n");
			sb.append("    SELECT\n");
			for(int i=0; i < colList.size() ; i++){
				
				colVO = (SqlMapVO)colList.get(i);
				colNm = colVO.getColumnName();
				codeVo = new CodeVO();
				
				if(cdMap.containsKey(colNm)){
					codeVo = cdMap.get(colNm);
					koCmmt = codeVo.getKoCmmt();
					frCmmt = codeVo.getFrCmmt();
					
					commentStr = "/** "+frCmmt+" !== "+koCmmt+" ==! */";
				}else{
					
					koCmmt = colVO.getComments();
					commentStr = "/** !== "+koCmmt+" ==! */";
				}
						
				if(i == 0 ) {
					sb.append(rpad("           A."+colNm,70," ")).append(commentStr).append("\n");
				}else {
					
					if("_CD".equals(colNm.substring(colNm.length() - 3))) {
//						colCdNm = colNm.substring(0, colNm.length() - 2) + "NM";
					}
					
					if("_DT".equals(colNm.substring(colNm.length() - 3))) {
						sb.append(rpad("         , TO_CHAR(TO_DATE(A."+colNm+", 'YYYYMMDD'),'DD/MM/YYYY') AS "+colNm,70," ")).append(commentStr).append(" \n");
					}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
//						
//						if(cdMap.containsKey(colNm)){
//							codeVo = cdMap.get(colNm);
//							
//							cdNm = codeVo.getCdNm();//코드명
//							colId = codeVo.getColId();//컬럼ID
//							cdDiv = codeVo.getCdDiv();//코드분류(INDV,COMN,"")
//							comCdId = codeVo.getComCdId();//적용코드
//
//							System.out.println("colNm >>> ["+colNm+"]");
//							System.out.println("cdDiv >>> ["+cdDiv+"]");
//							
//							if(cdDiv == null || "".equals(cdDiv)){
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//							}else if("INDV".equals(cdDiv)){
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , "+comCdId+"(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else{
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_CD_VDVAL_NM(\'"+comCdId+"\',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}
//							
//						}else{
//							if(colNm.length() > 7 && ("_CNTY_CD".equals(colNm.substring(colNm.length() - 8)) || "_NAT_CD".equals(colNm.substring(colNm.length() - 7)))) {
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_CNTY_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else if(colNm.length() > 7 && "_PORT_CD".equals(colNm.substring(colNm.length() - 8))) {
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_PORT_CD_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else if(colNm.length() > 8 && "_AIRPT_CD".equals(colNm.substring(colNm.length() - 9))) {
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_AIRPT_CD_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else if(colNm.length() > 7 && "_CURR_CD".equals(colNm.substring(colNm.length() - 8))) {
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_CURR_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else if(colNm.length() > 6 && "CSTM_CD".equals(colNm.substring(colNm.length() - 7))) {//세관코드
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0005',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}else if(colNm.length() > 6 && "SCTR_CD".equals(colNm.substring(colNm.length() - 7))) {//섹터코드
//								sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//								sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0004',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//							}
//						}
//						
						sb.append(rpad("         , A."+colNm,70," ")).append(commentStr).append("\n");
					}
					
//					else if(colNm.length() > 7 && ("_CNTY_CD".equals(colNm.substring(colNm.length() - 8)) || "_NAT_CD".equals(colNm.substring(colNm.length() - 7)))) {
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CNTY_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 7 && "_PORT_CD".equals(colNm.substring(colNm.length() - 8))) {
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_PORT_CD_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 8 && "_AIRPT_CD".equals(colNm.substring(colNm.length() - 9))) {
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_AIRPT_CD_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 7 && "_CURR_CD".equals(colNm.substring(colNm.length() - 8))) {
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CURR_NM(A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 6 && "CSTM_CD".equals(colNm.substring(colNm.length() - 7))) {//세관코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0005',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 6 && "SCTR_CD".equals(colNm.substring(colNm.length() - 7))) {//섹터코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0004',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 11 && "PRCS_STTS_CD".equals(colNm.substring(colNm.length() - 12))) {//처리상태코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0017',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 6 && "WRHS_CD".equals(colNm.substring(colNm.length() - 7))) {//보세창고코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0003',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 9 && "PCKG_UT_CD".equals(colNm.substring(colNm.length() - 10))) {//포장단위코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0018',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 9 && ("WGHT_UT_CD".equals(colNm.substring(colNm.length() - 10)) || "NTWG_UT_CD".equals(colNm.substring(colNm.length() - 10)))) {//중량단위코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('COM_0018',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 9 && "CNTR_TP_CD".equals(colNm.substring(colNm.length() - 10))) {//컨테이너구분코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0012',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 9 && "PNLT_TP_CD".equals(colNm.substring(colNm.length() - 10))) {//과태료구분코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0023',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 11 && "TRNP_MEAN_CD".equals(colNm.substring(colNm.length() - 12))) {//운송방법코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0013',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}else if(colNm.length() > 16 && "CNTR_STFN_STTS_CD".equals(colNm.substring(colNm.length() - 17))) {//컨테이너적입상태코드
//						sb.append(rpad("        , A."+colNm,70," ")).append(" /* ").append(colVO.getComments()).append("*/ \n");
//						sb.append(rpad("        , FN_GET_CD_VDVAL_NM('CAG_0011',A."+colNm+", #{langCd}) AS "+colCdNm,70," ")).append(" /* ").append(colVO.getComments()).append("명 */ \n");
//					}
				    else{
						sb.append(rpad("         , A."+colNm,70," ")).append(commentStr).append("\n");
					}
					
				}		
			}
			sb.append("    FROM   "+tblId+" A\n");
			sb.append("    WHERE  1 = 1\n");
			sb.append("    <include refid=\"commonInclude.sortPagerFooter\" />\n");
			sb.append("</select>\n\n\n");
			
			
			sb.append("<insert id=\""+gubun+"_insert"+tblNm+"\" parameterType=\""+inparam+"\">\n");
			sb.append("/* "+gubun+"_insert"+tblNm+" */\n");
			sb.append("    INSERT INTO "+tblId+"\n");
			sb.append("    (\n");
			
			sb3.append("         LAST_CHPR_ID = #{lastChprId}\n");
			sb3.append("       , LAST_CHG_DTTM = SYSTIMESTAMP\n");
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				colNm = colVO.getColumnName();
				contType = colVO.getContType();
				
				codeVo = new CodeVO();
				
				if(cdMap.containsKey(colNm)){
					codeVo = cdMap.get(colNm);
					koCmmt = codeVo.getKoCmmt();
					frCmmt = codeVo.getFrCmmt();
					
					commentStr = "/** "+frCmmt+" !== "+koCmmt+" ==! */";
				}else{
					koCmmt = colVO.getComments();
					commentStr = "/** !== "+koCmmt+" ==! */";
				}
				
				if("FRST_RGSR_DTTM".equals(colNm) || "LAST_CHG_DTTM".equals(colNm)){
					//sb.append("        , "+colNm+"\n");	
					//sbCol.append("       , "+colNm+"\n");		
					//sb2.append("       , SYSTIMESTAMP\n");	
					
					sb.append(rpad("        , "+colNm,70," ")).append(commentStr).append("\n");	
					sbCol.append(rpad("       , "+colNm,70," ")).append(commentStr).append("\n");	
					sb2.append(rpad("       , SYSTIMESTAMP",70," ")).append(commentStr).append("\n");
					
				}else {
					if(i == 0 ) {
						sb.append(rpad("          "+colNm,70," ")).append(commentStr).append("\n");
						sbCol.append(rpad("         "+colNm,70," ")).append(commentStr).append("\n");
						sb2.append(rpad("         #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");
						
						if(!"FRST_RGSR_DTTM".equals(colNm) && !"LAST_CHG_DTTM".equals(colNm) && !"FRST_REGST_ID".equals(colNm) && !"LAST_CHPR_ID".equals(colNm) && !"P".equals(contType)){
							sb3.append("       <if test=\"").append(this.convert2CamelCase(colNm)).append(" != null\">\n");
							sb3.append(rpad("         "+colNm+" = #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");
							sb3.append("       </if>\n");
						}
					}else {
						sb.append(rpad("        , "+colNm,70," ")).append(commentStr).append("\n");
						sbCol.append(rpad("       , "+colNm,70," ")).append(commentStr).append("\n");
						sb2.append(rpad("       , #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");	
						
						if(!"FRST_RGSR_DTTM".equals(colNm) && !"LAST_CHG_DTTM".equals(colNm) && !"FRST_REGST_ID".equals(colNm) && !"LAST_CHPR_ID".equals(colNm) && !"P".equals(contType)){
							sb3.append("       <if test=\"").append(this.convert2CamelCase(colNm)).append(" != null\">\n");
							sb3.append(rpad("       , "+colNm+" = #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");	
							sb3.append("       </if>\n");
						}
					}		
				}
				
				if("P".equals(contType)) {
					sbPK.append(rpad("     AND    "+colNm+" = #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");
					if(i == 0 ) {
						sbPK2.append(rpad("           "+colNm+" = #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");
					}else {
						sbPK2.append(rpad("       AND "+colNm+" = #{"+this.convert2CamelCase(colNm)+"}",70," ")).append(commentStr).append("\n");
					}
				}
				
			}
			sb.append("    ) VALUES ( \n");
			sb.append(sb2);
			sb.append("    )\n");
			sb.append("</insert>\n\n\n");
			
			sb.append("<insert id=\""+gubun+"_mergeInsert"+tblNm+"\" parameterType=\""+inparam+"\">\n");
			sb.append("/* "+gubun+"_mergeInsert"+tblNm+" */\n");
			sb.append("    MERGE INTO "+tblId+"\n");
            sb.append("    USING DUAL\n");
            sb.append("    ON (\n");
            sb.append(sbPK2);
            sb.append("    )\n");
            sb.append("    WHEN MATCHED THEN\n");
            sb.append("    UPDATE SET\n");
            sb.append(sb3);
            sb.append("    WHEN NOT MATCHED THEN\n");
            sb.append("    INSERT (\n");
            sb.append(sbCol);
    		sb.append("    ) VALUES ( \n");
			sb.append(sb2);
			sb.append("    )\n");
			sb.append("</insert>\n\n\n");
			
			
			sb.append("<update id=\""+gubun+"_update"+tblNm+"\" parameterType=\""+inparam+"\">\n");
			sb.append("/* "+gubun+"_update"+tblNm+" */\n");
			sb.append("     UPDATE  "+tblId+" SET \n");
			sb.append(sb3);
			sb.append("     WHERE	1 = 1\n");
			sb.append(sbPK);
			sb.append("</update>\n\n\n");
			
			sb.append("<delete id=\""+gubun+"_delete"+tblNm+"\" parameterType=\""+inparam+"\">\n");
			sb.append("/* "+gubun+"_delete"+tblNm+" parameterType=\""+inparam+"*/\n");
			sb.append("     DELETE FROM   "+tblId+"  \n");
			sb.append("     WHERE	1 = 1\n");
			sb.append(sbPK);
			sb.append("</delete>\n\n\n");
			
			
			retMap.put("sqlStr", sb.toString());
			
			
		}catch (BiffException e){
			System.out.println("BiffException");
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("IOException");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return retMap;
	}
	
	/**
	 * 공통검증 저장
	 * @return
	 */
	public HashMap saveComVrfc(Map<?, ?> commandMap) throws Exception{ 

		List<SqlMapVO> colList = null;
		List<SqlMapVO> colList2 = null;
		HashMap retMap = new HashMap();
		
		ComVrfcBaseVo baseVo = new ComVrfcBaseVo();
		ComVrfcBaseDtlVo dtlVo = new ComVrfcBaseDtlVo();
		String tblId = (String)commandMap.get("tblId");
		String bfhnVrfcPrgmId = (String)commandMap.get("bfhnVrfcPrgmId");
		String docCd = (String)commandMap.get("docCd");
		String docNm = (String)commandMap.get("docNm");
		String checkTrgt = (String)commandMap.get("checkTrgt");
		String vrfcTblId = (String)commandMap.get("vrfcTblId");
		
		//테이블 컬럼 목록 조회
		SqlMapVO sqlMapVO = new SqlMapVO();
		sqlMapVO.setTblId(tblId);
		sqlMapVO.setVrfcTblId(vrfcTblId);
		
		System.out.println("service tblId >>> ["+sqlMapVO.getTblId()+"]");
		
		colList = makeVODao.selectMakeVO(sqlMapVO);
		
		if ( checkTrgt != null && checkTrgt.equals("I") ) {
			colList2 = makeVODao.selectMakeVO_IN(sqlMapVO);
		}

		SqlMapVO colVO = null;
		SqlMapVO colVO2 = null;
		String colNm = "";
		String cmmtNm = "";
		int resultCnt = 0;
		
		String mExcelFilePath = "C:\\Temp\\comCd.xls";
//		String mExcelFilePath = "/Project/bin/dices/comCd.xls";
		int mRows;
		String cdNm = "";
		String colId = "";
		String cdDiv = "";
		String comCdId = "";
		String indvCdId = "";
		//int mColumns;
		Workbook mWorkBook;
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();
		
		try{
			
			mWorkBook = Workbook.getWorkbook(new File(mExcelFilePath));
			
			Sheet sheet = mWorkBook.getSheet(0);
			mRows = sheet.getRows();
			//mColumns = sheet.getColumns();
			
			for(int i=0; i< mRows; i++){
				Cell cell0 = sheet.getCell(0,i);
				Cell cell1 = sheet.getCell(1,i);
				Cell cell2 = sheet.getCell(2,i);
				Cell cell3 = sheet.getCell(3,i);
				Cell cell4 = sheet.getCell(4,i);
				cdNm = cell0.getContents();//코드명
				colId = cell1.getContents();//컬럼ID
				cdDiv = cell2.getContents();//코드분류(INDV,COMN,"")
				comCdId = cell3.getContents();//적용코드
				indvCdId = cell4.getContents();//적용코드
				
				codeVo = new CodeVO();
				codeVo.setCdNm(cdNm);
				codeVo.setColId(colId);
				codeVo.setCdDiv(cdDiv);
				codeVo.setComCdId(comCdId);
				codeVo.setIndvCdId(indvCdId);
				
				if(!"".equals(colId)){
					cdMap.put(colId, codeVo);
					System.out.println(i + " cdNm = "+cdNm+" colId = "+colId+" cdDiv = "+cdDiv+" comCdId = "+comCdId);
				}
				
			}
			
			baseVo.setDocCd(docCd);							//문서코드   (ex: 적하목록 = 200)               
			baseVo.setBfhnVrfcPrgmId(bfhnVrfcPrgmId);		//사전검증프로그램ID (ex: comBizvrfcStuffSvc)
			baseVo.setBsopClsfCd("CAG");					//업무분류코드           
			baseVo.setDocNm(docNm);							//문서명  (ex: 적하목록)                 
			baseVo.setBfhnVrfcYn("Y");						//사전검증여부           
			baseVo.setDelYn("N");							//삭제여부                  
			baseVo.setFrstRegstId("SYSTEM");				//최초등록자ID        
			baseVo.setLastChprId("SYSTEM");					//최종변경자ID          
			
			resultCnt = makeVODao.mergeInsertComVrfcBase(baseVo);

			dtlVo = new ComVrfcBaseDtlVo();
			
			dtlVo.setDocCd(docCd);//문서코드
			dtlVo.setTrgtTblNm(tblId);//대상테이블명
			makeVODao.deleteComVrfcBaseDtl(dtlVo);
			
			for(int i=0; i < colList.size() ; i++){
				colVO = (SqlMapVO)colList.get(i);
				colNm = colVO.getColumnName();
				cmmtNm = colVO.getComments();
				
				if ( checkTrgt != null && checkTrgt.equals("I") ) {
					for (int j=0; colList2 != null && j < colList2.size(); j++) {
						if ( colList2.get(j).getColumnName().equals(colNm) ) {
							colVO2 = (SqlMapVO)colList2.get(j);
						}
					}
				}
				
				if("FRST_RGSR_DTTM".equals(colNm) || "LAST_CHG_DTTM".equals(colNm) || "FRST_REGST_ID".equals(colNm) || "LAST_CHPR_ID".equals(colNm) ){
					continue;
				}
				
				dtlVo = new ComVrfcBaseDtlVo();
				
				dtlVo.setDocCd(docCd);//문서코드
				dtlVo.setTrgtTblNm(tblId);//대상테이블명
				dtlVo.setTrgtColNm(colNm);//대상컬럼명
				dtlVo.setBfhnVrfcClusNm(cmmtNm);//사전검증항목명
				
				dtlVo.setKoColName(colVO.getKoCmmt());//사전검증항목명
				dtlVo.setEnColName(colVO.getEnCmmt());//사전검증항목명
				dtlVo.setFrColName(colVO.getFrCmmt());//사전검증항목명
				
				
				dtlVo.setClusSortOrdr(new BigDecimal(i+1));//항목정렬순서
				dtlVo.setRscoVrfcYn("N");//자릿수검증여부
				dtlVo.setAlRsco(null);//전체자릿수
				
				dtlVo.setErkyOrdr(new BigDecimal("0")); //오류키순번
				
				if ( colVO.getContType() != null && colVO.getContType().equals("P") ) {
					dtlVo.setErkyOrdr(new BigDecimal("1")); //오류키순번
				}
				
				if("_DT".equals(colNm.substring(colNm.length() - 3))) {
					dtlVo.setDtVldtVrfcYn("Y");//일자유효성검증여부
					dtlVo.setDtVldtTpCn("yyyyMMdd");//일자유효성구분내용 
				}if(colNm.length() > 4 && "_DTTM".equals(colNm.substring(colNm.length() - 5))) {
					dtlVo.setDtVldtVrfcYn("N");//일자유효성검증여부
					dtlVo.setDtVldtTpCn("yyyyMMddHHmmss");//일자유효성구분내용 
				}else if("_CD".equals(colNm.substring(colNm.length() - 3))) {
					

					dtlVo.setComnCdVrfcYn("Y");//공통코드검증여부
					dtlVo.setIndvCdVrfcYn("N");//개별코드검증여부
					dtlVo.setIndvCd("");//개별코드검증여부
					
					if(cdMap.containsKey(colNm)){
						codeVo = cdMap.get(colNm);
						
						cdNm = codeVo.getCdNm();//코드명
						colId = codeVo.getColId();//컬럼ID
						cdDiv = codeVo.getCdDiv();//코드분류(INDV,COMN,"")
						comCdId = codeVo.getComCdId();//적용코드
						indvCdId = codeVo.getIndvCdId();//개별코드항목id
	
						System.out.println("colNm >>> ["+colNm+"]");
						System.out.println("cdDiv >>> ["+cdDiv+"]");
						
						if(cdDiv == null || "".equals(cdDiv)){
							if ( comCdId == null || comCdId.equals("") ) {
								dtlVo.setComnCdVrfcYn(null);//공통코드검증여부
							}
						}else if("INDV".equals(cdDiv)){
							dtlVo.setComnCdVrfcYn("N");//공통코드검증여부
							dtlVo.setIndvCdVrfcYn("Y");//개별코드검증여부
							dtlVo.setIndvCd(colNm);//개별코드 
							dtlVo.setIndvCdTblNm(comCdId);//개별코드테이블명
							dtlVo.setIndvCdColNm(indvCdId);//개별코드컬럼명
						}else{
							dtlVo.setComnCd(comCdId);//공통코드
						}
						
					}else{
						if(colNm.length() > 7 && ("_CNTY_CD".equals(colNm.substring(colNm.length() - 8)) || "_NAT_CD".equals(colNm.substring(colNm.length() - 7)))) {
							//국가 코드
							dtlVo.setIndvCdTblNm("TB_COM_CNTY_CD");//개별코드테이블명
							dtlVo.setIndvCdColNm("CNTY_CD");//개별코드컬럼명
						}else if(colNm.length() > 7 && "_PORT_CD".equals(colNm.substring(colNm.length() - 8))) {
							//항구 코드
							dtlVo.setIndvCdTblNm("TB_COM_PORT_AIRPT_CD");//개별코드테이블명
							dtlVo.setIndvCdColNm("PORT_AIRPT_CD");//개별코드컬럼명
						}else if(colNm.length() > 8 && "_AIRPT_CD".equals(colNm.substring(colNm.length() - 9))) {
							//공항 코드
							dtlVo.setIndvCdTblNm("TB_COM_PORT_AIRPT_CD");//개별코드테이블명
							dtlVo.setIndvCdColNm("PORT_AIRPT_CD");//개별코드컬럼명
						}else if(colNm.length() > 7 && "_CURR_CD".equals(colNm.substring(colNm.length() - 8))) {
							//통화 코드
							dtlVo.setIndvCdTblNm("TB_COM_CURR_CD");//개별코드테이블명
							dtlVo.setIndvCdColNm("CURR_CD");//개별코드컬럼명
						}else{
							dtlVo.setComnCdVrfcYn("N");//공통코드검증여부
							dtlVo.setIndvCdVrfcYn("N");//개별코드검증여부
						}
					}
					
					
				}else if("_YN".equals(colNm.substring(colNm.length() - 3))) {
					dtlVo.setComnCdVrfcYn("Y");//공통코드검증여부
					dtlVo.setComnCd("COM_0016");//공통코드(섹터)
					
					dtlVo.setIndvCdVrfcYn("N");//개별코드검증여부
					//dtlVo.setIndvCd("");//개별코드 
					//dtlVo.setIndvCdTblNm("");//개별코드테이블명
					//dtlVo.setIndvCdColNm("");//개별코드컬럼명
					//dtlVo.setIndvCdAditQuryCn("");//개별코드추가쿼리내용
				}else{
					dtlVo.setRscoVrfcYn("N");//자릿수검증여부
					//dtlVo.setRscoVrfcYn("Y");//자릿수검증여부
					//dtlVo.setAlRsco(new BigDecimal(colVO.getDataLength()));//전체자릿수
				}
				
				if ( checkTrgt != null && checkTrgt.equals("I") ) {
					if(colVO2 != null && "N".equals(colVO2.getNullable())) {
						dtlVo.setEsntInpVrfcYn("Y");//필수입력검증여부
					}else{
						dtlVo.setEsntInpVrfcYn("N");//필수입력검증여부
					}
				} else {
					if("N".equals(colVO.getNullable())) {
						dtlVo.setEsntInpVrfcYn("Y");//필수입력검증여부
					}else{
						dtlVo.setEsntInpVrfcYn("N");//필수입력검증여부
					}
				}
				
				
				if("NUMBER".equals(colVO.getDataType())) {
					dtlVo.setAmtQtyVrfcYn("N");//금액수량검증여부
					dtlVo.setNumVldtVrfcYn("Y");//숫자유효성검증여부
					//dtlVo.setDcmlUndrRsco("200");//소수점미만자릿수
					dtlVo.setRscoVrfcYn("Y");//자릿수검증여부
					dtlVo.setAlRsco(new BigDecimal(colVO.getDataLength()));//전체자릿수
				}
				
				//dtlVo.setFxngRscoVrfcYn("200");//고정자릿수검증여부 
				//dtlVo.setFxngRsco("200");//고정자릿수 
				
				
				dtlVo.setDelYn("N");//삭제여부
				dtlVo.setFrstRegstId("SYSTEM");//최초등록자ID
				dtlVo.setLastChprId("SYSTEM");//최종변경자ID
				
				makeVODao.mergeInsertComVrfcBaseDtl(dtlVo);
				
			}
			
			retMap.put("insCnt", resultCnt);
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return retMap;
	}
	
	/**
	 * 외부 테이블 내부 테이블 매핑
	 * @return
	 */
	public String selectTblMap(Map<?, ?> commandMap){ 
		
		StringBuffer sb = new StringBuffer();
		
		HashMap paramMap = new HashMap();
		
		SqlMapVO sqTmVo = new SqlMapVO();
		SqlMapVO sqTbVo = new SqlMapVO();
		String fromTblId = (String)commandMap.get("fromTblId");
		String toTblId = (String)commandMap.get("toTblId");
		String fromVo = (String)commandMap.get("fromVo");
		String toVo = (String)commandMap.get("toVo");
		
		SqlMapVO colVO = null;
		String colNm = "";
		String contType = "";
		String koCmmt = "";
		String colId = "";
		String frCmmt = "";
		String commentStr = "";
		
		String mExcelFilePath = "C:\\Temp\\colInfo.xls";
		//String mExcelFilePath = "/Project/bin/dices/colInfo.xls";
		int mRows;
		//int mColumns;
		Workbook mWorkBook;
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();
				
		try{
			
			mWorkBook = Workbook.getWorkbook(new File(mExcelFilePath));
			
			Sheet sheet = mWorkBook.getSheet(2);
			mRows = sheet.getRows();
			//mColumns = sheet.getColumns();
			
			for(int i=0; i< mRows; i++){
				Cell cell0 = sheet.getCell(1,i);//한글 COMMENT
				Cell cell1 = sheet.getCell(4,i);//컬럼ID
				Cell cell2 = sheet.getCell(12,i);//불어 COMMENT
				koCmmt = cell0.getContents();//한글 COMMENT
				colId = cell1.getContents();//컬럼ID
				frCmmt = cell2.getContents();//불어 COMMENT
				
				codeVo = new CodeVO();
				codeVo.setColId(colId);
				codeVo.setKoCmmt(koCmmt);
				codeVo.setFrCmmt((frCmmt.replaceAll("N�", "N°")).replaceAll("�", "é"));
				
				if(!"".equals(colId)){
					cdMap.put(colId, codeVo);
					System.out.println(i + " colId = "+colId+" koCmmt = "+koCmmt+" frCmmt = "+frCmmt);
				}
				
			}
						
		
			if((String)commandMap.get("fromTblId") == null || (String)commandMap.get("toTblId") == null){
				return "";
			}
			
			String fromVoNm = this.convert2CamelCase(fromTblId.substring(3)) + "Vo";
			String toVoNm = this.convert2CamelCase(toTblId.substring(3)) + "Vo";
			
			if("".equals(fromVo)){
				fromVo = fromVoNm;
			}
			
			if("".equals(toVo)){
				toVo = toVoNm;
			}
			
			paramMap.put("tblId", ((String)commandMap.get("fromTblId")).toUpperCase());
			
			List<SqlMapVO> fromTableMapList = makeVODao.selectBusiTableMap(paramMap);
			
			paramMap.put("tblId", ((String)commandMap.get("toTblId")).toUpperCase());
			
			List<SqlMapVO> toTableMapList = makeVODao.selectBusiInTableMap(paramMap);
			
			System.out.println("fromTableMapList.size >>>> ["+fromTableMapList.size()+"]");
			System.out.println("toTableMapList.size   >>>> ["+toTableMapList.size()+"]");
			
			String fromColId = "";
			String toColId = "";
			String toColNm = "";
			String id = "";
			String fId = "";
			
			
			for(int i=0; i < fromTableMapList.size(); i++){
				
				sqTmVo = fromTableMapList.get(i);
				fromColId = sqTmVo.getColumnName();
	
				id = this.convert2CamelCase(fromColId);
				fId = id.substring(0,1).toUpperCase() + id.substring(1);
				
				for(int j=0; j < toTableMapList.size(); j++){
										
					sqTbVo = toTableMapList.get(j);
					toColId = sqTbVo.getColumnName();
					
					if(cdMap.containsKey(toColId)){
						codeVo = cdMap.get(toColId);
						koCmmt = codeVo.getKoCmmt();
						frCmmt = codeVo.getFrCmmt();
						
						commentStr = "/** "+frCmmt+" !== "+koCmmt+" ==! */";
					}else{

						toColNm = sqTbVo.getComments();
						commentStr = "/** !== "+toColNm+" ==! */";
					}
					
					if(fromColId.equals(toColId)){
	
						sb.append(rpad(toVo+".set"+fId+"("+fromVo+".get"+fId+"());", 70, " "));
						sb.append(" ").append(commentStr);
						sb.append("\n");
						
					}
					
				}
				
			}
		}catch (BiffException e){
			System.out.println("BiffException");
			e.printStackTrace();
		} catch (IOException e){
			System.out.println("IOException");
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
	 * 외부 select 내부 insert sql make
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public String selectMakeSelectInsert(Map<?, ?> commandMap) throws BiffException, IOException{ 
		
		StringBuffer sb = new StringBuffer();
		
		HashMap paramMap = new HashMap();
		
		SqlMapVO sqTmVo = new SqlMapVO();
		SqlMapVO sqTbVo = new SqlMapVO();
		String toTblId = (String)commandMap.get("toTblId");
		String fromTblId = (String)commandMap.get("fromTblId");
		
		if((String)commandMap.get("fromTblId") == null || (String)commandMap.get("toTblId") == null){
			return "";
		}
		
		String contType = "";
		String koCmmt = "";
		String colId = "";
		String frCmmt = "";
		String commentStr = "";

		CodeVO codeVo = new CodeVO();
		HashMap cdMap = this.getComment();
		
		paramMap.put("tblId", ((String)commandMap.get("toTblId")).toUpperCase());
		
//		List<SqlMapVO> fromTableMapList = makeVODao.selectBusiInTableMap(paramMap);
		List<SqlMapVO> fromTableMapList = makeVODao.selectBusiTableMap(paramMap);
		
		paramMap.put("tblId", ((String)commandMap.get("fromTblId")).toUpperCase());
		
		List<SqlMapVO> toTableMapList = makeVODao.selectBusiTableMap(paramMap);

		System.out.println("toTblId 	>>>> ["+((String)commandMap.get("toTblId")).toUpperCase()+"]");
		System.out.println("fromTblId   >>>> ["+((String)commandMap.get("fromTblId")).toUpperCase()+"]");
		System.out.println("fromTableMapList.size >>>> ["+fromTableMapList.size()+"]");
		System.out.println("toTableMapList.size   >>>> ["+toTableMapList.size()+"]");
		
		String fromColId = "";
		String fromColNm = "";
		String toColId = "";
		String toColNm = "";
		ArrayList<String> pkList = new ArrayList<String>();
		ArrayList<String> colList = new ArrayList<String>();
		HashMap toCalMap = new HashMap();
		
		sb.append("INSERT INTO ").append(toTblId.toUpperCase()).append("@LN_ICAR");
		sb.append("\n");
		sb.append("(").append("\n");
		for(int i=0; i < fromTableMapList.size(); i++){
			
			sqTmVo = fromTableMapList.get(i);
			fromColId = sqTmVo.getColumnName();
			contType = sqTmVo.getContType();
			fromColNm = sqTmVo.getComments();

			if(cdMap.containsKey(fromColId)){
				codeVo = (CodeVO)cdMap.get(fromColId);
				koCmmt = codeVo.getKoCmmt();
				frCmmt = codeVo.getFrCmmt();
				
				commentStr = "/** "+frCmmt+" !== "+koCmmt+" ==! */";
			}else{
				commentStr = "/** "+fromColNm+" ==! */";
			}
			
			colList.add(fromColId);
			
			if(i == 0){
				sb.append(rpad("      "+fromColId, 70, " ")).append(commentStr).append("\n");
			}else{
				sb.append(rpad("    , "+fromColId, 70, " ")).append(commentStr).append("\n");
			}
			
		}
		sb.append(")").append("\n");
		sb.append("SELECT").append("\n");
		
		
		for(int j=0; j < toTableMapList.size(); j++){
			
			sqTbVo = toTableMapList.get(j);
			toColId = sqTbVo.getColumnName();
			toColNm = sqTbVo.getComments();
			contType = sqTbVo.getContType();
			
			if("P".equals(contType)){
				pkList.add(toColId);
			}
			
			System.out.println("toColId >>>> ["+toColId+"], toColNm >>>> ["+toColNm+"]");
			toCalMap.put(toColId, toColNm);
			
		}
		
		for(int z= 0; z < colList.size(); z++){
			
			colId = colList.get(z);
			
			if(cdMap.containsKey(colId)){
				codeVo = (CodeVO)cdMap.get(colId);
				koCmmt = codeVo.getKoCmmt();
				frCmmt = codeVo.getFrCmmt();
				
				commentStr = "/** "+frCmmt+" !== "+koCmmt+" ==! */";
			}else{
				commentStr = "/** !== NULL ==! */";
			}
		
			System.out.println("colId >>>> ["+colId+"]");
			if(toCalMap.containsKey(colId)){
				
				toColNm = (String)toCalMap.get(colId);
				
				if(z == 0){
					sb.append(rpad("      "+colId, 70, " ")).append(commentStr);
					sb.append("\n");
				}else{
					
					if("FRST_REGST_ID".equals(colId) || "LAST_CHPR_ID".equals(colId)){
						sb.append("    , 'SYSTEM'").append("\n");
					}else if("FRST_RGSR_DTTM".equals(colId) || "LAST_CHG_DTTM".equals(colId)){
						sb.append("    , SYSTIMESTAMP").append("\n");
					}else if("DEL_YN".equals(colId)){
						sb.append("    , 'N'").append("\n");
					}else{
						sb.append(rpad("    , "+colId, 70, " ")).append(commentStr);
						sb.append("\n");
					}
				}
			}else{
				sb.append(rpad("    , NULL", 70, " ")).append(" /* NULL */");
				sb.append("\n");
			}
			
		}
		System.out.println("pkList.size() >>>> ["+pkList.size()+"]");
		sb.append("FROM  ").append(fromTblId.toUpperCase()).append("\n");
		sb.append("WHERE 1=1").append("\n");
		sb.append("AND   DEL_YN = 'N'").append("\n");
		for(int k=0; k < pkList.size(); k++){
			sb.append("AND   ").append(pkList.get(k)).append(" = #{"+convert2CamelCase(pkList.get(k))+"}").append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 엑셀에서 comment 조회
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public HashMap getComment() throws BiffException, IOException{
	
		String tblId = "";
		String tblKoCmmt = "";
		String tblFrCmmt = "";
		String koCmmt = "";
		String colId = "";
		String frCmmt = "";
		
		String mExcelFilePath = "C:\\Temp\\colInfo.xls";
//		String mExcelFilePath = "/Project/bin/dices/colInfo.xls";
		int mRows0;
		int mRows;
		//int mColumns;
		Workbook mWorkBook;
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();
		
		mWorkBook = Workbook.getWorkbook(new File(mExcelFilePath));
		
		Sheet sheet0 = mWorkBook.getSheet(1);
		mRows0 = sheet0.getRows();
		
		for(int i=0; i< mRows0; i++){
			
			Cell cell0 = sheet0.getCell(1,i);//테이블 한글 COMMENT
			Cell cell1 = sheet0.getCell(3,i);//테이블 불어 COMMENT
			Cell cell2 = sheet0.getCell(9,i);//테이블 ID
			
			tblId = cell2.getContents();//테이블 ID
			tblKoCmmt = cell0.getContents();//테이블 한글 COMMENT
			tblFrCmmt = cell1.getContents();//테이블 불어 COMMENT
			
			codeVo = new CodeVO();
			codeVo.setColId(colId);
			codeVo.setKoCmmt(tblKoCmmt);
			codeVo.setFrCmmt((tblFrCmmt.replaceAll("N�", "N°")).replaceAll("�", "é"));
			
			cdMap.put(tblId, codeVo);
		}
		
		Sheet sheet = mWorkBook.getSheet(2);
		mRows = sheet.getRows();
		
		for(int i=0; i< mRows; i++){
			Cell cell0 = sheet.getCell(1,i);//한글 COMMENT
			Cell cell1 = sheet.getCell(4,i);//컬럼ID
			Cell cell2 = sheet.getCell(12,i);//불어 COMMENT
			koCmmt = cell0.getContents();//한글 COMMENT
			colId = cell1.getContents();//컬럼ID
			frCmmt = cell2.getContents();//불어 COMMENT
			
			codeVo = new CodeVO();
			codeVo.setColId(colId);
			codeVo.setKoCmmt(koCmmt);
			codeVo.setFrCmmt((frCmmt.replaceAll("N�", "N°")).replaceAll("�", "é"));
			
			if(!"".equals(colId)){
				cdMap.put(colId, codeVo);
				System.out.println(i + " colId = "+colId+" koCmmt = "+koCmmt+" frCmmt = "+frCmmt);
			}
			
		}
		
		return cdMap;
	}
	
//	/**
//	 * 외부 select 내부 insert sql make
//	 * @return
//	 * @throws IOException 
//	 * @throws BiffException 
//	 */
//	public String selectMakeSelectInsert(Map<?, ?> commandMap) throws BiffException, IOException{ 
//		
//		StringBuffer sb = new StringBuffer();
//		
//		HashMap paramMap = new HashMap();
//		
//		SqlMapVO sqTmVo = new SqlMapVO();
//		SqlMapVO sqTbVo = new SqlMapVO();
//		String fromTblId = (String)commandMap.get("toTblId");
//		String toTblId = (String)commandMap.get("fromTblId");
//		
//		makeVODao.selectMakeVO_IN(sqlMapVO);
//		
//		sb.append(")").append("\n");
//		sb.append("SELECT").append("\n");
//		
//		return sb.toString();
//	}

}
