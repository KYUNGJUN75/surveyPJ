package com.pjt.survey.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.survey.dao.ExcelLoadDao;
import com.pjt.survey.vo.VrfcBaseDVo;

import jxl.read.biff.BiffException;

/** 
 * @Description : 
 * @author 장경준
 * @since 2018. 6. 11.
 * @version 1.0 
 * @see 
 * 
 * <pre> 
 * == 개정이력(Modification Information) == 
 * 
 * 	  수정일 			수정자 				수정내용 
 * -----------	-------------	--------------------------- 
 * 2018. 6. 11.		장경준				최초생성 
 * 
 * </pre> 
 */
@Service
public class ExcelLoadService {

	@Autowired
	private ExcelLoadDao excelLoadDao;

	/**
	 * 공통 검증 엑셀 다운로드
	 * @return 
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public void excelDown(Map map) throws Exception {
	
		VrfcBaseDVo data = new VrfcBaseDVo();
		HashMap paramMap = (HashMap)map;

		HashMap colMap = null;
		List<HashMap> collist = excelLoadDao.selectComVrfcBaseDCol(paramMap);
		List<VrfcBaseDVo> list = excelLoadDao.selectVrfcBaseD(paramMap);
	
//		Workbook xlsWb = new HSSFWorkbook(); // Excel 2007 이전 버전
		Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상
	
		ServletOutputStream sos = null;
		VrfcBaseDVo dto = new VrfcBaseDVo();
	
		
		try {
	
			Sheet sheet = xlsxWb.createSheet("Excel");
	
			sheet.setColumnWidth(0, 5000);						//시트 길이조정  첫번째는 cell , 두번째는 크기
			sheet.setColumnWidth(1, 8000);
			sheet.setColumnWidth(2, 8000);
			
			for(int i = 3; i <= 22 ; i++ ){
				sheet.setColumnWidth(i, 4000);
			}
	
			Row row = null;
			Cell cell = null;
			
			row = sheet.createRow(0);
	
			for(int i = 0; i < collist.size() ; i++){

				colMap = (HashMap)collist.get(i);
				
				cell = row.createCell(i);
				
				cell.setCellValue((String)colMap.get("COLUMNNM"));
			}

			row = sheet.createRow(1);
			
			for(int i = 0; i < collist.size() ; i++){

				colMap = (HashMap)collist.get(i);
				
				cell = row.createCell(i);
				
				cell.setCellValue((String)colMap.get("COLUMNID"));
			}

			System.out.println("docCd >>>>> ["+paramMap.get("docCd")+"]");
	
			for(int i = 0 ; i < list.size(); i++){
	
				dto = list.get(i);

				row = sheet.createRow(i+2);					//세로 칸
	
				cell = row.createCell(0);					//가로 칸
				cell.setCellValue(dto.getDocCd());

				cell = row.createCell(1);					//가로 칸
				cell.setCellValue(dto.getTrgtTblNm());

				cell = row.createCell(2);					//가로 칸
				cell.setCellValue(dto.getTrgtColNm());

				cell = row.createCell(3);					//가로 칸
				if(dto.getClusSortOrdr() != null){//가로 칸
					cell.setCellValue(dto.getClusSortOrdr().toString());
				}else{
					cell.setCellValue("");
				}

				cell = row.createCell(4);					//가로 칸
				cell.setCellValue(dto.getComnCdVrfcYn());

				cell = row.createCell(5);					//가로 칸
				cell.setCellValue(dto.getComnCd());

				cell = row.createCell(6);					//가로 칸
				cell.setCellValue(dto.getIndvCdVrfcYn());

				cell = row.createCell(7);					//가로 칸
				cell.setCellValue(dto.getIndvCd());

				cell = row.createCell(8);					//가로 칸
				cell.setCellValue(dto.getIndvCdTblNm());

				cell = row.createCell(9);					//가로 칸
				cell.setCellValue(dto.getIndvCdColNm());

				cell = row.createCell(10);					//가로 칸
				cell.setCellValue(dto.getIndvCdAditQuryCn());

				cell = row.createCell(11);					//가로 칸
				cell.setCellValue(dto.getEsntInpVrfcYn());

				cell = row.createCell(12);					//가로 칸
				cell.setCellValue(dto.getAmtQtyVrfcYn());

				cell = row.createCell(13);					//가로 칸
				cell.setCellValue(dto.getDtVldtVrfcYn());

				cell = row.createCell(14);					//가로 칸
				cell.setCellValue(dto.getDtVldtTpCn());

				cell = row.createCell(15);					//가로 칸
				cell.setCellValue(dto.getNumVldtVrfcYn());

				cell = row.createCell(16);					//가로 칸
				cell.setCellValue(dto.getClusLenVrfcYn());

				cell = row.createCell(17);	
				if(dto.getClusLen() != null){//가로 칸
					cell.setCellValue(dto.getClusLen().toString());
				}else{
					cell.setCellValue("");
				}

				cell = row.createCell(18);					//가로 칸
				cell.setCellValue(dto.getNumRscoVrfcYn());

				cell = row.createCell(19);					//가로 칸
				if(dto.getAlRsco() != null){
					cell.setCellValue(dto.getAlRsco().toString());
				}else{
					cell.setCellValue("");
				}

				cell = row.createCell(20);					//가로 칸
				if(dto.getDcmlUndrRsco() != null){
					cell.setCellValue(dto.getDcmlUndrRsco().toString());
				}else{
					cell.setCellValue("");
				}

				cell = row.createCell(21);					//가로 칸
				if(dto.getErkyOrdr() != null){
					cell.setCellValue(dto.getErkyOrdr().toString());
				}else{
					cell.setCellValue("");
				}

				cell = row.createCell(22);					//가로 칸
				if(dto.getPrtlVrfcYn() != null){
					cell.setCellValue(dto.getPrtlVrfcYn().toString());
				}else{
					cell.setCellValue("");
				}
	
	
			}
	
	
			File xlsFile = new File("C:/Temp/"+paramMap.get("docCd")+".xlsx");
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsxWb.write(fileOut);

	
		} catch (Exception e) {
	
			System.out.println(e.getMessage());
	
		} finally{
	
			if(sos != null) sos.close();
	
		}
	}
	
	/**
	 * 공통검증 엑셀 upload
	 * @return
	 * @throws Exception
	 */
	public void excelUpload(Map map) throws Exception{
	
		try{

			HashMap paramMap = (HashMap)map;
			FileInputStream inputStream = new FileInputStream("C:/Temp/"+paramMap.get("docCd")+".xlsx");// docCd.xlsx에 대한 입력 스트림 생성
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);   // 입력 스트림의 내용으로 xlsx 파일 생성
			ArrayList colIdList = new ArrayList();
			HashMap colMap = new HashMap();
			int cellCnt = 0;

			// 삭제처리
			excelLoadDao.updateDelYnComVrfcBaseD(paramMap);
			
			int sheetCnt = workbook.getNumberOfSheets();  // sheet 개수를 얻어옴

//			System.out.println("sheetCnt >>>>>>>> ["+sheetCnt+"]");
			for(int i = 0 ; i < sheetCnt ; i++){
				XSSFSheet sheet = workbook.getSheetAt(i);  // i번째 sheet 정보를 얻어옴
				int rowCnt = sheet.getPhysicalNumberOfRows(); // row 개수 얻어옴

				System.out.println("rowCnt >>>>>>>> ["+rowCnt+"]");
				
				for(int r = 1 ; r < rowCnt ; r++){
					XSSFRow row = sheet.getRow(r);    // row 정보 얻어옴
					System.out.println("row >>>>>>>> ["+r+"]");

					if(r == 1){
						cellCnt = sheet.getRow(r).getPhysicalNumberOfCells(); // cell 개수 얻어옴
						for(int c = 0 ; c < cellCnt ; c++){
							XSSFCell cell = row.getCell(c);   //cell 정보 얻어옴
							colIdList.add(cell.getStringCellValue());
						}
					}else{
						System.out.println("cellCnt >>>>>>>> ["+cellCnt+"]");
						System.out.println("colIdList.size >>>>>>>> ["+colIdList.size()+"]");

						colMap = new HashMap();
						for(int c = 0 ; c < cellCnt ; c++){
							System.out.println("row.getCell(c) >>>>>>>> ["+row.getCell(c)+"]");
							System.out.println("colIdList.get(c) >>>>>>>> ["+colIdList.get(c)+"]");
							XSSFCell cell = row.getCell(c);   //cell 정보 얻어옴
							if(cell == null){
								colMap.put(colIdList.get(c), "");
							}else{
								colMap.put(colIdList.get(c), cell.getStringCellValue());
							}
						}
	
//						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						System.out.println(colMap.toString());
						excelLoadDao.mergeInsertComVrfcBaseDMap(colMap);
					}
					
				}
			}
		} catch (Exception e) {

			throw new Exception(e.toString());
//			e.printStackTrace();
		}

	}
}
