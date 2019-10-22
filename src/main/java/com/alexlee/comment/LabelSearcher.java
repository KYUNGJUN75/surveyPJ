package com.alexlee.comment;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 카메룬 사업 소스 코멘트 다국어 자동화 작업용 프로그램
 * 
 * label 조회유틸
 *   
 * @author leedongho
 *
 */
public class LabelSearcher {
	public static final int KEY 		= 0;
	public static final int LABEL 		= KEY + 1;
	public static final int LABEL_FR 	= LABEL + 1;
	public static final int GROUP 		= LABEL_FR + 1;
	public static final int GROUP_FR 	= GROUP + 1;
	public static final int BUTTON 		= GROUP_FR + 1;
	public static final int BUTTON_FR 	= BUTTON + 1;
	
	public static final int SIZE_MAP 	= BUTTON_FR + 1;
	
	public static final String CR = "\r\n";
	
	public static final int TOTAL = 0;
	public static final int CHANGED = TOTAL + 1;
	public static final int FILE_NM = CHANGED + 1;
	
	private Hashtable<String, String[]> htMap = new Hashtable<String, String[]>();
	
	private String mapPath = "/Project/bin/dices/CAMPASS-CAR-서식-적하목록.xlsx";

	/**
	 * 서식파일 로드
	 * 
	 * @param mapPath 서식파일 경로
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	public void loadProperties() throws IOException, EncryptedDocumentException, InvalidFormatException {
		if (this.mapPath.toLowerCase().endsWith(".xlsx")) {
			loadPropertiesExcel(this.mapPath);
		}
	}
	
	/**
	 * 서식파일 로드
	 * @param mapPath 서식파일 경로
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	private void loadPropertiesExcel(String mapPath) throws IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook workbook = WorkbookFactory.create(new File(mapPath));
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
		
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        
        String sheetName 		= null;
        String key 		= null;
        String frComment = null;
        String labelId 	= null;
        
        String keyValue[] = null;
        
        boolean isButtonSheet = false;
        boolean isGroupSheet = false;
        boolean isLabelSheet = false;
        
        // 버튼 1, 3, 6
        // 그룹 1, 3, 5
        // 라벨 1, 3, 5
        int indexExlKey 		= 1;
        int indexExlFrComment 	= 3;
        int indexExlLabelId 	= 5;
        
        int indexMapKey 		= KEY;
        int indexMapFrComment 	= BUTTON_FR;
        int indexMapLabelId 	= BUTTON;
        
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            sheetName = sheet.getSheetName();
            System.out.println("=> " + sheetName);
            
            isButtonSheet 	= "버튼".equals(sheetName);
            isGroupSheet 	= "그룹".equals(sheetName);
            isLabelSheet 	= "라벨".equals(sheetName);
            
            
            if (isButtonSheet || isGroupSheet || isLabelSheet) {
                indexExlLabelId 	= isButtonSheet ? 6 : 5;
            
                if (isButtonSheet) {
                	indexMapFrComment 	= BUTTON_FR;
                	indexMapLabelId 	= BUTTON;
                } else if (isGroupSheet) {
                	indexMapFrComment 	= GROUP_FR;
                	indexMapLabelId 	= GROUP;
                } else if (isLabelSheet) {
                	indexMapFrComment 	= LABEL_FR;
                	indexMapLabelId 	= LABEL;
                }
            	Iterator<Row> rowIterator = sheet.rowIterator();
            	int colIndex = -1;
            	while (rowIterator.hasNext()) {
            		key 		= null;
            		frComment 	= null;
            		labelId 	= null;

            		Row row = rowIterator.next();

            		// Now let's iterate over the columns of the current row
            		Iterator<Cell> cellIterator = row.cellIterator();

            		while (cellIterator.hasNext()) {
            			Cell cell = cellIterator.next();
            			String cellValue = cell.getStringCellValue();//dataFormatter.formatCellValue(cell);
            			colIndex = cell.getColumnIndex();

            			if (colIndex == indexExlKey) {
            				key 		= cellValue;
            				key = key != null ? key.replaceAll("\\s", "") : key;
            			} else if (colIndex == indexExlFrComment) {
            				frComment 	= cellValue;
            			} else if (colIndex == indexExlLabelId) {
            				labelId 	= cellValue;
            			}
            		}

            		if (row.getRowNum() > 0) {
            			if (key != null) {
            				if (htMap.containsKey(key)) {
            					keyValue = htMap.get(key);
            					
            					if (keyValue[indexMapLabelId] != null) {
            						this.errorExists(row.getRowNum() + 1, key, labelId);
            					} else {
            						keyValue[indexMapKey] = key;
            						keyValue[indexMapFrComment] = frComment;
            						keyValue[indexMapLabelId] = labelId;
            						
            						htMap.put(key, keyValue);
            					}
            				} else {
            					keyValue = new String[SIZE_MAP];
            					this.debug(row.getRowNum() + 1, key, labelId);
            					
            					keyValue[indexMapKey] = key;
        						keyValue[indexMapFrComment] = frComment;
        						keyValue[indexMapLabelId] = labelId;
        						
            					htMap.put(key, keyValue);
            				}
            			}
            		}
            	}
            }
        }
	}
	
	/**
	 * [사전파일용]에러 로그 출력
	 * @param lineNo 라인 번호
	 * @param korean 키 명 (한국어)
	 * @param french 대응되는 프랑스어
	 */
	private void errorExists(int lineNo, String korean, String french) {
		System.out.println("[ERROR] The key (\"" + korean + "\") already exists @line:" + lineNo + ", @value:" + french);
	}
	
	/**
	 * [사전파일용] 로그출력
	 * @param lineNo 라인 번호
	 * @param korean 키 명 (한국어)
	 * @param french 대응되는 프랑스어
	 */
	private void debug(int lineNo, String string, String string2) {
		System.out.println("[DEBUG]" + lineNo + "\t:, key (\"" + string + "\")\t@value:" + string2);
	}
	
	/** 
	 * 한글에 대응하는 label id 조회
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public String[] getLabelFromDic (String text) throws IOException {
		return text != null ? this.htMap.get(text.replaceAll("\\s", "")) : null;
	}
	
	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
	public static String toLabelLogString (String[] labels) {
		String res = null;
		
		if (labels != null) {
			res = 
				"# KEY:[" + labels[KEY] + "]\t" + CR + 
				(labels[LABEL] != null ?  ("  * 라벨 - LABEL ID[" + labels[LABEL]  + "],\tFR[" + labels[LABEL_FR]  + "]" + CR) : "") +  
				(labels[BUTTON] != null ? ("  * 버튼 - LABEL ID[" + labels[BUTTON] + "],\tFR[" + labels[BUTTON_FR] + "]" + CR) : "") +  
				(labels[GROUP] != null ?  ("  * 그룹 - LABEL ID[" + labels[GROUP]  + "],\tFR[" + labels[GROUP_FR]  + "]" + CR) : "");
		} else {
			res = "내용없음";
		}
		
		return res;
	}
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		LabelSearcher replacement = new LabelSearcher();
//		String mapPath 	= "C:\\DICES_DEV\\comment\\dic.txt";
		String mapPath 	= "/Project/bin/dices/CAMPASS-CAR-서식-적하목록.xlsx";
		
		/***
		replacement.batch(mapPath, path, tgDirPath, fileExt);
		/***/
		
		/***/
		String text = "B/L:부족";
		replacement.setMapPath(mapPath);
		replacement.loadProperties();
		String[] replacedText = replacement.getLabelFromDic(text);
		
		System.out.println("SRC : " + text);
		System.out.println("RES : " + LabelSearcher.toLabelLogString(replacedText));
		/***/
		/***/
	}



}
