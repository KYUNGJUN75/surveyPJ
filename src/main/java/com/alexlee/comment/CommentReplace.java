package com.alexlee.comment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 카메룬 사업 소스 코멘트 다국어 자동화 작업용 프로그램
 * 
 * TODO !== 한국어설명 ==! 형태의 주석(java, sql xml, html, ts) 을
 * 변환된 프랑스어 !== 한국어설명 ==! 형태로 변경해준다.
 * 
 * SRC    : <!-- TODO !== 화물구분 ==! -->
 * RESULT : <!-- Nature !== 화물구분 ==! -->
 *
 *
 *
 * =========================================================================
 * [소스 내 주석 가이드] 가능한 주석형태는 아래와 같습니다.
 * =========================================================================
 * sample 1 - 
 * 	TODO !== 화물구분 ==! 
 * 
 * sample 2 - 
 *   TODO 
 *   !== 화물구분 ==! 
 *  
 * sample 3 - TODO 기호, 한국어 주석 열고닫는구분자('!==', '==!') 사이에 엔터 들어가도 무방합니다. (주석내 줄바꿈 시, 별표 '*' 가 들어가도 관계없습니다.) 
 *   TODO 
 *   !== 
 *   화물구분
 *   ==! 
 *   
 * sample 4 - 한국어 주석 열고닫는구분자('!==', '==!') 혹은 ('!=', '=!') 모두 가능합니다.
 *   TODO 
 *   != 
 *   화물구분
 *   =! 
 *   
 * sample 5 - 소스 내 존재하는 "TODO 번역필요" 라는 키워드는 (번역문이 치환된 경우에 한해) 자동삭제합니다. 
 *   TODO 번역필요
 *   !== 
 *   화물구분
 *   ==!
 *    
 * sample 6 - 자동생성된 소스내 존재하는 "TODO 외국어 주석 작성" 라는 키워드는 (번역문이 치환된 경우에 한해) 자동삭제합니다. 
 *   TODO 외국어 주석 작성
 *   !==
 *   화물구분
 *   ==! 
 *   
 * @author leedongho
 *
 */
public class CommentReplace {
	public static final int KEY = 0;
	public static final int VALUE = KEY + 1;
	public static final String CR = "\r\n";
	
	public static final int TOTAL = 0;
	public static final int CHANGED = TOTAL + 1;
	public static final int FILE_NM = CHANGED + 1;
	
	private Hashtable<String, String> htMap = new Hashtable<String, String>();
	
	//private String mapPath = "C:\\DICES_DEV\\comment\\dic.xlsx";
	private String mapPath = "/Project/bin/dices/dic.xlsx";
	
	/**
	 * [시작] 코멘트 변환 시작
	 * 
	 * @param mapPath
	 * @param path
	 * @param tgDirPath
	 * @param fileExt
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	public Object[] batch(String mapPath, String path, String tgDirPath, String fileExt) throws IOException, EncryptedDocumentException, InvalidFormatException {
		// 1. load properties
		loadProperties(mapPath);
		// 2. recursive replacement 
		Object reports[] = new Object[3]; 
		reports[TOTAL] = new Integer(0);
		reports[CHANGED] = new Integer(0);
		reports[FILE_NM] = new ArrayList<String>();
		
		replaceDir(new File(path), new File(tgDirPath), fileExt, reports);
		return reports;
	}
	
	/**
	 * [변환] 디렉터리 내 전체 변환처리한다.
	 * 
	 * @param dir
	 * @param tgDir
	 * @param fileExt
	 * @throws IOException
	 */
	private Object[] replaceDir(File dir, File tgDir, String fileExt, Object[] reports) throws IOException {
		File[] files = dir.listFiles();
		String fileName = null;
		if (!tgDir.exists()) {
			tgDir.mkdirs();
		}
		ArrayList<String> alLiterals = null;
		
		for (int inx = 0; inx < files.length; inx ++) {
			if (files[inx].isDirectory()) {
				replaceDir(files[inx], new File(tgDir, files[inx].getName()), fileExt, reports);
			} else {
				fileName = files[inx].getName();
				if (fileExt != null) {
					if (!fileName.toUpperCase().endsWith(fileExt.toUpperCase())) {
						continue;
					}
				}
				
				alLiterals = inspectFile(files[inx]);
				replaceFile(files[inx], tgDir, alLiterals, reports);
			}
		}
		
		return reports;
	}

	/**
	 * TODO 키워드 존재여부 반환
	 * @param text
	 * @return
	 */
	private static boolean isTodo (String text) {
		return text.indexOf("TODO") >= 0;
	}
	
	/**
	 * 한국어 여는태그 존재여부 반환
	 * @param text
	 * @return
	 */
	private static boolean isOpenTag (String text) {
		return text.indexOf("!==") >= 0;
	}
	
	/**
	 * 한국어 닫는태그 존재여부 반환
	 * @param text
	 * @return
	 */
	private static boolean isCloseTag (String text) {
		return text.indexOf("==!") >= 0;
	}
	
	
	
	/**
	 * [변환준비] 대상파일에서 TODO 및 원문 리터럴 정보 채취한다.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> inspectFile(File file) throws IOException {
		this.debug("# [" + file.getAbsolutePath() + "]======================");
		
		ArrayList<String> alLiterals = new ArrayList<String>();
		String buffer = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		
		boolean isStart		= false;
		boolean isKeySrchStart	= false;
		
		boolean isTodo 		= false;
		boolean isOpenTag 	= false;
		boolean isCloseTag 	= false;
		
		int indexTodo = -1;
		int indexOpenTag = -1;
		int indexCloseTag = -1;
		
		int openTagLength = 3;
		int closeTagLength = 3;
		
		StringBuffer sbBuffer = new StringBuffer();
		
		while ((buffer = reader.readLine()) != null) {
			isTodo 		= isTodo(buffer);
			isOpenTag 	= isOpenTag(buffer);
			isCloseTag 	= isCloseTag(buffer);
			
			indexOpenTag 	= isOpenTag ? buffer.indexOf("!==") : -1;
			openTagLength 	= 3;
			if (isOpenTag && indexOpenTag == -1) {
				indexOpenTag 	= isOpenTag ? buffer.indexOf("!=") : -1;
				openTagLength 	= 2;
			}
			indexCloseTag 	= isCloseTag ? buffer.indexOf("==!") : -1;
			if (isCloseTag && indexCloseTag == -1) {
				indexCloseTag 	= isCloseTag ? buffer.indexOf("=!") : -1;
				closeTagLength 	= 2;
			}
			
			if (isTodo) {
				// 이미 확인중인 리터럴이 있는데 키를 못찾은 상황에서 다시 TODO가 나타난 경우
				if (isStart) {
					// 이전 건 종료처리
					
					alLiterals.add("");
					sbBuffer = new StringBuffer();
				}
				
				isStart = true;
			}
			
			if (isStart) {
				if (isKeySrchStart) {
					if (isCloseTag) {
						if (isOpenTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength, indexCloseTag));
						} else {
							sbBuffer.append(buffer.substring(0, indexCloseTag));
						}
						
						alLiterals.add(sbBuffer.toString().trim().replaceAll("[\\s\\*\\/]", ""));
						sbBuffer = new StringBuffer();
						// 종료
						isKeySrchStart = false;
						isStart = false;
					} else {
						if (isOpenTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength));
						} else {
							sbBuffer.append(buffer);
						}
					}
				} else {
					if (isOpenTag) {
						isKeySrchStart = true;
						
						if (isCloseTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength, indexCloseTag));
							
							alLiterals.add(sbBuffer.toString().trim().replaceAll("[\\s\\*\\/]", ""));
							
							sbBuffer = new StringBuffer();
							// 종료
							isKeySrchStart = false;
							isStart = false;
							
							
						} else {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength));
						}
					} else {
						// 무시
					}
				}
			} else {
				// skip
			}
			
		}
		
		if (reader != null) {
			reader.close();
		}
		
		return alLiterals;
	}
	
	/**
	 * [변환준비] 대상문자열에서 TODO 및 원문 리터럴 정보 채취한다.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private ArrayList<String> inspectString(StringReader sReader) throws IOException {
//		this.debug("# [" + file.getAbsolutePath() + "]======================");
		
		ArrayList<String> alLiterals = new ArrayList<String>();
		String buffer = null;
		BufferedReader reader = new BufferedReader(sReader);
		
		boolean isStart		= false;
		boolean isKeySrchStart	= false;
		
		boolean isTodo 		= false;
		boolean isOpenTag 	= false;
		boolean isCloseTag 	= false;
		
		int indexTodo = -1;
		int indexOpenTag = -1;
		int indexCloseTag = -1;
		
		int openTagLength = 3;
		int closeTagLength = 3;
		
		StringBuffer sbBuffer = new StringBuffer();
		
		while ((buffer = reader.readLine()) != null) {
			isTodo 		= isTodo(buffer);
			isOpenTag 	= isOpenTag(buffer);
			isCloseTag 	= isCloseTag(buffer);
			
			indexOpenTag 	= isOpenTag ? buffer.indexOf("!==") : -1;
			openTagLength 	= 3;
			if (isOpenTag && indexOpenTag == -1) {
				indexOpenTag 	= isOpenTag ? buffer.indexOf("!=") : -1;
				openTagLength 	= 2;
			}
			indexCloseTag 	= isCloseTag ? buffer.indexOf("==!") : -1;
			if (isCloseTag && indexCloseTag == -1) {
				indexCloseTag 	= isCloseTag ? buffer.indexOf("=!") : -1;
				closeTagLength 	= 2;
			}
			
			if (isTodo) {
				// 이미 확인중인 리터럴이 있는데 키를 못찾은 상황에서 다시 TODO가 나타난 경우
				if (isStart) {
					// 이전 건 종료처리
					
					alLiterals.add("");
					sbBuffer = new StringBuffer();
				}
				
				isStart = true;
			}
			
			if (isStart) {
				if (isKeySrchStart) {
					if (isCloseTag) {
						if (isOpenTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength, indexCloseTag));
						} else {
							sbBuffer.append(buffer.substring(0, indexCloseTag));
						}
						
						alLiterals.add(sbBuffer.toString().trim().replaceAll("[\\s\\*\\/]", ""));
						sbBuffer = new StringBuffer();
						// 종료
						isKeySrchStart = false;
						isStart = false;
					} else {
						if (isOpenTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength));
						} else {
							sbBuffer.append(buffer);
						}
					}
				} else {
					if (isOpenTag) {
						isKeySrchStart = true;
						
						if (isCloseTag) {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength, indexCloseTag));
							
							alLiterals.add(sbBuffer.toString().trim().replaceAll("[\\s\\*\\/]", ""));
							
							sbBuffer = new StringBuffer();
							// 종료
							isKeySrchStart = false;
							isStart = false;
							
							
						} else {
							sbBuffer.append(buffer.substring(indexOpenTag + openTagLength));
						}
					} else {
						// 무시
					}
				}
			} else {
				// skip
			}
			
		}
		
		if (reader != null) {
			reader.close();
		}
		
		return alLiterals;
	}
	
	/**
	 * [변환] 대상파일을 변환한다.
	 * 
	 * @param file
	 * @param tgDir
	 * @param alLiterals
	 * @throws IOException
	 */
	private void replaceFile(File file, File tgDir, ArrayList<String> alLiterals, Object[] reports) throws IOException {
		reports[TOTAL] = ((Integer)reports[TOTAL]) + 1;
		
		boolean needReplace = alLiterals != null && alLiterals.size() > 0;
		String buffer = null;
		String wBuffer = null;
		
		Boolean[] isChanged = new Boolean[1];isChanged[0] = false;
		boolean isChangedAtLeastOnce = false;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(tgDir, file.getName())), "UTF-8"));
		while ((buffer = reader.readLine()) != null) {
			if (needReplace) {
				wBuffer = this.replaceLine(buffer, alLiterals, isChanged);
				isChangedAtLeastOnce |= isChanged[0];
			} else {
				wBuffer = buffer;
			}
			
			writer.write(wBuffer + CR);
		}
		
		if (isChangedAtLeastOnce) {
			reports[CHANGED] = ((Integer)reports[CHANGED]) + 1;
			((ArrayList<String>)reports[FILE_NM]).add(file.getAbsolutePath());
		}
		if (reader != null) {
			reader.close();
		}
		if (writer != null) {
			writer.close();
		}
	}
	
	/**
	 * [변환] 대상문자열을 변환한다.
	 * 
	 * @param file
	 * @param tgDir
	 * @param alLiterals
	 * @throws IOException
	 */
	private void replaceString(StringReader sReader, StringWriter sWriter, ArrayList<String> alLiterals) throws IOException {
		
		boolean needReplace = alLiterals != null && alLiterals.size() > 0;
		String buffer = null;
		String wBuffer = null;
		BufferedReader reader = new BufferedReader(sReader);
		BufferedWriter writer = new BufferedWriter(sWriter);
		Boolean[] isChanged = new Boolean[1];
		isChanged[0] = false;
		
		boolean isChangedAtLeastOnce = false;
		
		while ((buffer = reader.readLine()) != null) {
			if (needReplace) {
				wBuffer = this.replaceLine(buffer, alLiterals, isChanged);
				isChangedAtLeastOnce |= isChanged[0];
			} else {
				wBuffer = buffer;
			}
			
			writer.write(wBuffer + CR);
		}
		
		if (isChangedAtLeastOnce) {
			
		}
		
		if (reader != null) {
			reader.close();
		}
		if (writer != null) {
			writer.close();
		}
	}

	/**
	 * [변환] 라인단위 치환
	 * 
	 * @param buffer
	 * @param alLiterals
	 * @return
	 */
	private String replaceLine(String buffer, ArrayList<String> alLiterals, Boolean[] isChanged) {
		String replacedStr = buffer;
		String korean = null;
		String french = null;
		if (isContainTodo(buffer)) {
			korean = alLiterals.size() > 0 ? alLiterals.remove(0) : null;
			
			if (korean != null && korean.length() > 0) {
				french = this.htMap.get(korean);

				System.out.println("KR:" + korean + ", FR:" + french);
				if (french != null && french.length() > 0) {
					replacedStr = replacedStr.replaceAll("번역\\s*필요", "");
					replacedStr = replacedStr.replaceAll("외국어\\s*주석\\s*작성", "");
					replacedStr = replacedStr.replaceAll("TODO", french);
					
					isChanged[0] = true;
				}
			} else {
				// error
			}
		}
		
		return replacedStr;
	}

	/**
	 * 'TODO' 라는 키워드 존재여부 반환한다.
	 * 
	 * @param text
	 * @return
	 */
	private static boolean isContainTodo (String text) {
//		return text != null && text.matches(".*TODO\\s*!=.*=!.*");
		return text != null && text.indexOf("TODO") >= 0;
	}
	
//	private static String extractKorean(String buffer) {
//		String korean = buffer.replaceAll("(.*TODO\\s*!=)=?([^=]*)=?(=!.*)", "$2");
//		return korean != null ? korean.trim() : korean;
//	}

	/**
	 * [사전파일용] 설정 사전파일 로드
	 * 
	 * @param mapPath 사전파일 경로
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	public void loadProperties() throws IOException, EncryptedDocumentException, InvalidFormatException {
		if (this.mapPath.toLowerCase().endsWith(".xlsx")) {
			loadPropertiesExcel(this.mapPath);
		} else {
			loadPropertiesText(this.mapPath);
		}
	}
	
	/**
	 * [사전파일용] 설정 사전파일 로드
	 * 
	 * @param mapPath 사전파일 경로
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	private void loadProperties(String mapPath) throws IOException, EncryptedDocumentException, InvalidFormatException {
		if (mapPath.toLowerCase().endsWith(".xlsx")) {
			loadPropertiesExcel(mapPath);
		} else {
			loadPropertiesText(mapPath);
		}
	}
	
	/**
	 * [사전파일용] 엑셀타입 사전파일 로드
	 * @param mapPath 사전파일 경로
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	private void loadPropertiesExcel(String mapPath) throws IOException, EncryptedDocumentException, InvalidFormatException {
		Workbook workbook = WorkbookFactory.create(new File(mapPath));
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
		
		DataFormatter dataFormatter = new DataFormatter();
		
		Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        String[] keyValue = new String[2];
        
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
            Iterator<Row> rowIterator = sheet.rowIterator();
            int colIndex = -1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Now let's iterate over the columns of the current row
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = cell.getStringCellValue();//dataFormatter.formatCellValue(cell);
                    colIndex = cell.getColumnIndex();
                    
                    if (colIndex == 0) {
                    	keyValue[KEY] = cellValue;
                    } else if (colIndex == 1) {
                    	keyValue[VALUE] = cellValue;
                    }
                }
                
                if (row.getRowNum() > 0) {
	        		if (keyValue != null) {
	        			if (htMap.containsKey(keyValue[KEY])) {
	        				this.errorExists(row.getRowNum() + 1, keyValue[KEY], keyValue[VALUE]);
	        			} else {
	        				this.debug(row.getRowNum() + 1, keyValue[KEY], keyValue[VALUE]);
	        				// 키를 공백제거 후 추가
	        				htMap.put(keyValue[KEY].replaceAll("\\s", ""), keyValue[VALUE]);
	        			}
	        		}
	            }
            }
        }
	}
	
	/**
	 * [사전파일용] 텍스트 타입 사전파일 로드
	 * @param mapPath
	 * @throws IOException
	 */
	private void loadPropertiesText(String mapPath) throws IOException {
		int lineNo = 1;
		
		String buffer = null;
		String[] keyValue = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(mapPath), "UTF-8"));
		while ((buffer = reader.readLine()) != null) {
			keyValue = this.extractData(buffer);
			if (keyValue != null) {
				if (htMap.containsKey(keyValue[KEY])) {
					this.errorExists(lineNo, keyValue[KEY], keyValue[VALUE]);
				} else {
					this.debug(lineNo, keyValue[KEY], keyValue[VALUE]);
					// 키를 공백제거 후 추가
					htMap.put(keyValue[KEY].replaceAll("\\s", ""), keyValue[VALUE]);
				}
			}
			lineNo ++;
		}
		
		if (reader != null) {
			reader.close();
		}
	}
	
	/**
	 * [텍스트 타입:사전파일용] 한 라인에서 데이터 분리 (키, value 쌍)
	 * @param buffer
	 * @return
	 */
	private String[] extractData(String buffer) {
		String[] keyValue = null;
		if (buffer != null) {
			keyValue = buffer.split("\\t");
		}
		return keyValue;
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
	 * 로그출력
	 * @param text
	 */
	private void debug(String text) {
		System.out.println("[DEBUG]" + text);
	}

	/** 
	 * 한글주석에 대응하는 프랑스어 주석 자동치환
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public String replaceCommentString (String text) throws IOException {
		StringReader sReader = new StringReader(text);
		ArrayList<String> alLiterals = this.inspectString(sReader);
		sReader = new StringReader(text);
		
		StringWriter sWriter = new StringWriter();
		
		this.replaceString(sReader, sWriter, alLiterals);
		
		return sWriter.toString();
	}
	
	/** 
	 * 한글주석에 대응하는 프랑스어 사전조회
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public String getWordFromDic (String text) throws IOException {
		return text != null ? this.htMap.get(text.replaceAll("\\s", "")) : text;
	}
	
	public String getMapPath() {
		return mapPath;
	}

	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {
		CommentReplace replacement = new CommentReplace();
//		String mapPath 	= "C:\\DICES_DEV\\comment\\dic.txt";
//		String mapPath 	= "C:\\DICES_DEV\\comment\\dic.xlsx";
//		String path 	= "C:\\DICES_DEV\\workspaces\\dices-ept-client\\src\\app\\care";
//		path 	= "C:\\DICES_DEV\\workspaces\\dices-ept-svr\\src\\main\\java\\dices\\care\\mnfs\\";
//		String tgDirPath = "C:\\DICES_DEV\\comment\\result_java";
		
		String mapPath 	= "/Projetc/bin/dices/dic.xlsx";
		String path 	= "/Project/workspace/dices-ept-client/src/app/care";
		path 	= "/Project/workspace/dices-ept-svr/src/main/java/dices/care/mnfs/";
		String tgDirPath = "/data/comment/result_java";
		String fileExt 	= null;
		
		/***
		replacement.batch(mapPath, path, tgDirPath, fileExt);
		/***/
		
		/***/
		String text = "AA  TODO !== 화물구분코드 ==! 입니다.";
		String replacedText = replacement.replaceCommentString (text);
		
		System.out.println("SRC : " + text);
		System.out.println("RES : " + replacedText);
		/***/
		/***/
	}



}
