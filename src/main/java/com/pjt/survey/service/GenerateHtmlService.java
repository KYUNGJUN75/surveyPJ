package com.pjt.survey.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjt.survey.dao.MakeVODao;
import com.pjt.survey.vo.CodeVO;

@Service
public class GenerateHtmlService {

	@Autowired
	private MakeVODao makeVODao;
	private String newLine = "\n";

	public void runGenerateHtml(Map<?,?> paramMap) throws Exception{

//		String mExcelFilePath = "C:\\Temp\\colInfo.xls";
		String mExcelFilePath = (String)paramMap.get("filePath");
		System.out.println("mExcelFilePath >>>>>> ["+mExcelFilePath+"]");
		int mRows;
		Workbook mWorkBook;
		CodeVO codeVo = new CodeVO();
		HashMap<String,CodeVO> cdMap = new HashMap<String,CodeVO>();

		try{

			mWorkBook = Workbook.getWorkbook(new File(mExcelFilePath));

			HashMap<String,Object> arrMap = new HashMap<String,Object>();
			String jct = "";
			Sheet[] sheets = mWorkBook.getSheets();
			System.out.println("sheets.length >>>>>>>>>> ["+sheets.length+"]");
			Sheet menuSheet;
			Sheet scrnSheet;
			ArrayList<String> actor 		= new ArrayList<String>(); // 액터
			ArrayList<String> lvl1MenuKr 	= new ArrayList<String>(); // 메뉴레벨1 KR
			ArrayList<String> lvl1MenuEn 	= new ArrayList<String>(); // 메뉴레벨1 EN
			ArrayList<String> lvl1MenuFr 	= new ArrayList<String>(); // 메뉴레벨1 FR
			ArrayList<String> lv21MenuKr 	= new ArrayList<String>(); // 메뉴레벨2 KR
			ArrayList<String> lv21MenuEn 	= new ArrayList<String>(); // 메뉴레벨2 EN
			ArrayList<String> lv21MenuFr 	= new ArrayList<String>(); // 메뉴레벨2 FR
			ArrayList<String> lv31MenuKr 	= new ArrayList<String>(); // 메뉴레벨3 KR
			ArrayList<String> lv31MenuEn 	= new ArrayList<String>(); // 메뉴레벨3 EN
			ArrayList<String> lv31MenuFr 	= new ArrayList<String>(); // 메뉴레벨3 FR
			ArrayList<String> scrnId 		= new ArrayList<String>(); // 화면ID
			ArrayList<String> scrnNmKr 		= new ArrayList<String>(); // 화면명 KR
			ArrayList<String> scrnNmEn 		= new ArrayList<String>(); // 화면명 EN
			ArrayList<String> scrnNmFr 		= new ArrayList<String>(); // 화면명 FR
			ArrayList<String> menuUrl1Kr	= new ArrayList<String>(); // 메뉴 URL KR
			ArrayList<String> menuUrl1En	= new ArrayList<String>(); // 메뉴 URL EN
			ArrayList<String> menuUrl1Fr	= new ArrayList<String>(); // 메뉴 URL FR

//			ArrayList<String> scrnId    = new ArrayList<String>(); // 업무기능코드
//			ArrayList<String> scrnNmKr  = new ArrayList<String>(); // 화면명(KR)
//			ArrayList<String> scrnNmEn  = new ArrayList<String>(); // 화면명(EN)
//			ArrayList<String> scrnNmFr  = new ArrayList<String>(); // 화면명(FR)
			ArrayList<String> mainGrpKr = new ArrayList<String>(); // 화면메인그룹명(KR)
			ArrayList<String> mainGrpEn = new ArrayList<String>(); // 화면메인그룹명(EN)
			ArrayList<String> mainGrpFr = new ArrayList<String>(); // 화면메인그룹명(FR)
			ArrayList<String> subGrpKr  = new ArrayList<String>(); // 화면서브그룹명(KR)
			ArrayList<String> subGrpEn  = new ArrayList<String>(); // 화면서브그룹명(EN)
			ArrayList<String> subGrpFr  = new ArrayList<String>(); // 화면서브그룹명(FR)
			ArrayList<String> subTabKr  = new ArrayList<String>(); // 화면서브탭명(KR)
			ArrayList<String> subTabEn  = new ArrayList<String>(); // 화면서브탭명(EN)
			ArrayList<String> subTabFr  = new ArrayList<String>(); // 화면서브탭명(FR)
			ArrayList<String> scrnGrpTp = new ArrayList<String>(); // 화면그룹유형
			ArrayList<String> colNm     = new ArrayList<String>(); // 컬럼명
			ArrayList<String> itemNmKr  = new ArrayList<String>(); // 항목명(KR)
			ArrayList<String> itemNmEn  = new ArrayList<String>(); // 항목명(EN)
			ArrayList<String> itemNmFr  = new ArrayList<String>(); // 항목명(FR)
			ArrayList<String> itemTp    = new ArrayList<String>(); // 항목작성타입
//			ArrayList<String> lblId     = new ArrayList<String>(); // 라벨ID
			ArrayList<String> itemDtlTp = new ArrayList<String>(); // 항목작성세부타입
			ArrayList<String> icon      = new ArrayList<String>(); // 아이콘
			ArrayList<String> style     = new ArrayList<String>(); // 스타일
			ArrayList<String> oneLineYn = new ArrayList<String>(); // 한줄여부
			ArrayList<String> itemManYn = new ArrayList<String>(); // 항목필수여부
			ArrayList<String> editPsbYn = new ArrayList<String>(); // 편집가능여부
			ArrayList<String> code      = new ArrayList<String>(); // 코드
			ArrayList<String> exSelKr   = new ArrayList<String>(); // 항목작성선택값(KR)
			ArrayList<String> exSelEn   = new ArrayList<String>(); // 항목작성선택값(EN)
			ArrayList<String> exSelFr   = new ArrayList<String>(); // 항목작성선택값(FR)
			ArrayList<String> exTxtKr   = new ArrayList<String>(); // 항목작성예제(KR)
			ArrayList<String> exTxtEn   = new ArrayList<String>(); // 항목작성예제(EN)
			ArrayList<String> exTxtFr   = new ArrayList<String>(); // 항목작성예제(FR)


			for(int z = 0; z < sheets.length ; z++){

				if(z == 0){

					menuSheet = mWorkBook.getSheet(z);

					mRows = menuSheet.getRows();
					//mColumns = sheet.getColumns();

					for(int i=2; i< mRows; i++){
						Cell cell0  = menuSheet.getCell(0 ,i); // 액터
						Cell cell1  = menuSheet.getCell(1 ,i); // 메뉴레벨1 KR
						Cell cell2  = menuSheet.getCell(2 ,i); // 메뉴레벨1 EN
						Cell cell3  = menuSheet.getCell(3 ,i); // 메뉴레벨1 FR
						Cell cell4  = menuSheet.getCell(4 ,i); // 메뉴레벨2 KR
						Cell cell5  = menuSheet.getCell(5 ,i); // 메뉴레벨2 EN
						Cell cell6  = menuSheet.getCell(6 ,i); // 메뉴레벨2 FR
						Cell cell7  = menuSheet.getCell(7 ,i); // 메뉴레벨3 KR
						Cell cell8  = menuSheet.getCell(8 ,i); // 메뉴레벨3 EN
						Cell cell9  = menuSheet.getCell(9 ,i); // 메뉴레벨3 FR
						Cell cell10 = menuSheet.getCell(10,i); // 화면ID
						Cell cell11 = menuSheet.getCell(11,i); // 화면명 KR
						Cell cell12 = menuSheet.getCell(12,i); // 화면명 EN
						Cell cell13 = menuSheet.getCell(13,i); // 화면명 FR
						Cell cell14 = menuSheet.getCell(14,i); // 메뉴 URL KR
						Cell cell15 = menuSheet.getCell(15,i); // 메뉴 URL EN
						Cell cell16 = menuSheet.getCell(16,i); // 메뉴 URL FR

						actor 		.add(cell0.getContents()); // 액터
						lvl1MenuKr 	.add(cell1.getContents()); // 메뉴레벨1 KR
						lvl1MenuEn 	.add(cell2.getContents()); // 메뉴레벨1 EN
						lvl1MenuFr 	.add(cell3.getContents()); // 메뉴레벨1 FR
						lv21MenuKr 	.add(cell4.getContents()); // 메뉴레벨2 KR
						lv21MenuEn 	.add(cell5.getContents()); // 메뉴레벨2 EN
						lv21MenuFr 	.add(cell6.getContents()); // 메뉴레벨2 FR
						lv31MenuKr 	.add(cell7.getContents()); // 메뉴레벨3 KR
						lv31MenuEn 	.add(cell8.getContents()); // 메뉴레벨3 EN
						lv31MenuFr 	.add(cell9.getContents()); // 메뉴레벨3 FR
						scrnId 		.add(cell10.getContents()); // 화면ID
						scrnNmKr 	.add(cell11.getContents()); // 화면명 KR
						scrnNmEn 	.add(cell12.getContents()); // 화면명 EN
						scrnNmFr 	.add(cell13.getContents()); // 화면명 FR
						menuUrl1Kr	.add(cell14.getContents()); // 메뉴 URL KR
						menuUrl1En	.add(cell15.getContents()); // 메뉴 URL EN
						menuUrl1Fr	.add(cell16.getContents()); // 메뉴 URL FR

//						System.out.println("scrnId : scrnNmKr : scrnNmFr >>>>>>>>>>>> ["+scrnId+":"+scrnNmKr+":"+scrnNmFr+"]");

					}


				}else{

					scrnSheet = mWorkBook.getSheet(z);

					mRows = scrnSheet.getRows();
					//mColumns = sheet.getColumns();
					System.out.println("mRows >>>>>>> ["+mRows+"]");

					for(int i=1; i< mRows; i++){

						Cell cell0  = scrnSheet.getCell(0 ,i); // 업무기능코드
						Cell cell1  = scrnSheet.getCell(1 ,i); // 화면명(KR)
						Cell cell2  = scrnSheet.getCell(2 ,i); // 화면명(EN)
						Cell cell3  = scrnSheet.getCell(3 ,i); // 화면명(FR)
						Cell cell4  = scrnSheet.getCell(4 ,i); // 화면메인그룹명(KR)
						Cell cell5  = scrnSheet.getCell(5 ,i); // 화면메인그룹명(EN)
						Cell cell6  = scrnSheet.getCell(6 ,i); // 화면메인그룹명(FR)
						Cell cell7  = scrnSheet.getCell(7 ,i); // 화면서브그룹명(KR)
						Cell cell8  = scrnSheet.getCell(8 ,i); // 화면서브그룹명(EN)
						Cell cell9  = scrnSheet.getCell(9 ,i); // 화면서브그룹명(FR)
						Cell cell10 = scrnSheet.getCell(10,i); // 화면서브탭명(KR)
						Cell cell11 = scrnSheet.getCell(11,i); // 화면서브탭명(EN)
						Cell cell12 = scrnSheet.getCell(12,i); // 화면서브탭명(FR)
						Cell cell13 = scrnSheet.getCell(13,i); // 화면그룹유형
						Cell cell14 = scrnSheet.getCell(14,i); // 컬럼명
						Cell cell15 = scrnSheet.getCell(15,i); // 항목명(KR)
						Cell cell16 = scrnSheet.getCell(16,i); // 항목명(EN)
						Cell cell17 = scrnSheet.getCell(17,i); // 항목명(FR)
						Cell cell18 = scrnSheet.getCell(18,i); // 항목작성타입
//						Cell cell19 = scrnSheet.getCell(19,i); // 라벨ID
						Cell cell19 = scrnSheet.getCell(19,i); // 항목작성세부타입   
						Cell cell20 = scrnSheet.getCell(20,i); // 아이콘        
						Cell cell21 = scrnSheet.getCell(21,i); // 스타일        
						Cell cell22 = scrnSheet.getCell(22,i); // 한줄여부       
						Cell cell23 = scrnSheet.getCell(23,i); // 항목필수여부     
						Cell cell24 = scrnSheet.getCell(24,i); // 편집가능여부     
						Cell cell25 = scrnSheet.getCell(25,i); // 코드         
						Cell cell26 = scrnSheet.getCell(26,i); // 항목작성선택값(KR)
						Cell cell27 = scrnSheet.getCell(27,i); // 항목작성선택값(EN)
						Cell cell28 = scrnSheet.getCell(28,i); // 항목작성선택값(FR)
						Cell cell29 = scrnSheet.getCell(29,i); // 항목작성예제(KR) 
						Cell cell30 = scrnSheet.getCell(30,i); // 항목작성예제(EN) 
						Cell cell31 = scrnSheet.getCell(31,i); // 항목작성예제(FR) 


						System.out.println("jct : ["+jct+"], cell0.getContents() : ["+cell0.getContents()+"]");
						if(i != 1 && !"".equals(jct) && !jct.equals(cell0.getContents())){
							
							arrMap = new HashMap();

							arrMap.put("scrnId"		, scrnId    );
							arrMap.put("scrnNmKr"	, scrnNmKr  );
							arrMap.put("scrnNmEn"	, scrnNmEn  );
							arrMap.put("scrnNmFr"	, scrnNmFr  );
							arrMap.put("mainGrpKr"	, mainGrpKr );
							arrMap.put("mainGrpEn"	, mainGrpEn );
							arrMap.put("mainGrpFr"	, mainGrpFr );
							arrMap.put("subGrpKr"	, subGrpKr  );
							arrMap.put("subGrpEn"	, subGrpEn  );
							arrMap.put("subGrpFr"	, subGrpFr  );
							arrMap.put("subTabKr"	, subTabKr  );
							arrMap.put("subTabEn"	, subTabEn  );
							arrMap.put("subTabFr"	, subTabFr  );
							arrMap.put("scrnGrpTp"	, scrnGrpTp );
							arrMap.put("colNm"		, colNm     );
							arrMap.put("itemNmKr"	, itemNmKr  );
							arrMap.put("itemNmEn"	, itemNmEn  );
							arrMap.put("itemNmFr"	, itemNmFr  );
							arrMap.put("itemTp"		, itemTp    );
//							arrMap.put("lblId"		, lblId     );
							arrMap.put("itemDtlTp"	, itemDtlTp );
							arrMap.put("icon"		, icon      );
							arrMap.put("style"		, style     );
							arrMap.put("oneLineYn"	, oneLineYn );
							arrMap.put("itemManYn"	, itemManYn );
							arrMap.put("editPsbYn"	, editPsbYn );
							arrMap.put("code"		, code      );
							arrMap.put("exSelKr"	, exSelKr   );
							arrMap.put("exSelEn"	, exSelEn   );
							arrMap.put("exSelFr"	, exSelFr   );
							arrMap.put("exTxtKr"	, exTxtKr   );
							arrMap.put("exTxtEn"	, exTxtEn   );
							arrMap.put("exTxtFr"	, exTxtFr   );

							if(this.makeHtml(arrMap)){

								scrnId    = new ArrayList<String>(); // 화면ID
								scrnNmKr  = new ArrayList<String>(); // 화면명 KR
								scrnNmEn  = new ArrayList<String>(); // 화면명 EN
								scrnNmFr  = new ArrayList<String>(); // 화면명 FR
								mainGrpKr = new ArrayList<String>(); // 화면메인그룹명(KR)
								mainGrpEn = new ArrayList<String>(); // 화면메인그룹명(EN)
								mainGrpFr = new ArrayList<String>(); // 화면메인그룹명(FR)
								subGrpKr  = new ArrayList<String>(); // 화면서브그룹명(KR)
								subGrpEn  = new ArrayList<String>(); // 화면서브그룹명(EN)
								subGrpFr  = new ArrayList<String>(); // 화면서브그룹명(FR)
								subTabKr  = new ArrayList<String>(); // 화면서브탭명(KR)
								subTabEn  = new ArrayList<String>(); // 화면서브탭명(EN)
								subTabFr  = new ArrayList<String>(); // 화면서브탭명(FR)
								scrnGrpTp = new ArrayList<String>(); // 화면그룹유형
								colNm     = new ArrayList<String>(); // 컬럼명
								itemNmKr  = new ArrayList<String>(); // 항목명(KR)
								itemNmEn  = new ArrayList<String>(); // 항목명(EN)
								itemNmFr  = new ArrayList<String>(); // 항목명(FR)
								itemTp    = new ArrayList<String>(); // 항목작성타입
//								lblId     = new ArrayList<String>(); // 라벨ID
								itemDtlTp = new ArrayList<String>(); // 항목작성세부타입
								icon      = new ArrayList<String>(); // 아이콘
								style     = new ArrayList<String>(); // 스타일
								oneLineYn = new ArrayList<String>(); // 한줄여부
								itemManYn = new ArrayList<String>(); // 항목필수여부
								editPsbYn = new ArrayList<String>(); // 편집가능여부
								code      = new ArrayList<String>(); // 코드
								exSelKr   = new ArrayList<String>(); // 항목작성선택값(KR)
								exSelEn   = new ArrayList<String>(); // 항목작성선택값(EN)
								exSelFr   = new ArrayList<String>(); // 항목작성선택값(FR)
								exTxtKr   = new ArrayList<String>(); // 항목작성예제(KR)
								exTxtEn   = new ArrayList<String>(); // 항목작성예제(EN)
								exTxtFr   = new ArrayList<String>(); // 항목작성예제(FR)

								scrnId    .add(cell0.getContents());  // 업무기능코드
								scrnNmKr  .add(cell1.getContents());  // 화면명(KR)
								scrnNmEn  .add(cell2.getContents());  // 화면명(EN)
								scrnNmFr  .add(cell3.getContents());  // 화면명(FR)
								mainGrpKr .add(cell4.getContents());  // 화면메인그룹명(KR)
								mainGrpEn .add(cell5.getContents());  // 화면메인그룹명(EN)
								mainGrpFr .add(cell6.getContents());  // 화면메인그룹명(FR)
								subGrpKr  .add(cell7.getContents());  // 화면서브그룹명(KR)
								subGrpEn  .add(cell8.getContents());  // 화면서브그룹명(EN)
								subGrpFr  .add(cell9.getContents());  // 화면서브그룹명(FR)
								subTabKr  .add(cell10.getContents()); // 화면서브탭명(KR)
								subTabEn  .add(cell11.getContents()); // 화면서브탭명(EN)
								subTabFr  .add(cell12.getContents()); // 화면서브탭명(FR)
								scrnGrpTp .add(cell13.getContents()); // 화면그룹유형
								colNm     .add(cell14.getContents()); // 컬럼명
								itemNmKr  .add(cell15.getContents()); // 항목명(KR)
								itemNmEn  .add(cell16.getContents()); // 항목명(EN)
								itemNmFr  .add(cell17.getContents()); // 항목명(FR)
								itemTp    .add(cell18.getContents()); // 항목작성타입
//								lblId     .add(cell19.getContents()); // 라벨ID
								itemDtlTp .add(cell19.getContents()); // 항목작성세부타입
								icon      .add(cell20.getContents()); // 아이콘
								style     .add(cell21.getContents()); // 스타일
								oneLineYn .add(cell22.getContents()); // 한줄여부
								itemManYn .add(cell23.getContents()); // 항목필수여부
								editPsbYn .add(cell24.getContents()); // 편집가능여부
								code      .add(cell25.getContents()); // 코드
								exSelKr   .add(cell26.getContents()); // 항목작성선택값(KR)
								exSelEn   .add(cell27.getContents()); // 항목작성선택값(EN)
								exSelFr   .add(cell28.getContents()); // 항목작성선택값(FR)
								exTxtKr   .add(cell29.getContents()); // 항목작성예제(KR)
								exTxtEn   .add(cell30.getContents()); // 항목작성예제(EN)
								exTxtFr   .add(cell31.getContents()); // 항목작성예제(FR)
								
								jct = cell0.getContents();

								break;
							}else{
								break;
							}

						}else{
							
							if(i == 1){
								jct = cell0.getContents();
							}

							scrnId    .add(cell0.getContents());  // 업무기능코드
							scrnNmKr  .add(cell1.getContents());  // 화면명(KR)
							scrnNmEn  .add(cell2.getContents());  // 화면명(EN)
							scrnNmFr  .add(cell3.getContents());  // 화면명(FR)
							mainGrpKr .add(cell4.getContents());  // 화면메인그룹명(KR)
							mainGrpEn .add(cell5.getContents());  // 화면메인그룹명(EN)
							mainGrpFr .add(cell6.getContents());  // 화면메인그룹명(FR)
							subGrpKr  .add(cell7.getContents());  // 화면서브그룹명(KR)
							subGrpEn  .add(cell8.getContents());  // 화면서브그룹명(EN)
							subGrpFr  .add(cell9.getContents());  // 화면서브그룹명(FR)
							subTabKr  .add(cell10.getContents()); // 화면서브탭명(KR)
							subTabEn  .add(cell11.getContents()); // 화면서브탭명(EN)
							subTabFr  .add(cell12.getContents()); // 화면서브탭명(FR)
							scrnGrpTp .add(cell13.getContents()); // 화면그룹유형
							colNm     .add(cell14.getContents()); // 컬럼명
							itemNmKr  .add(cell15.getContents()); // 항목명(KR)
							itemNmEn  .add(cell16.getContents()); // 항목명(EN)
							itemNmFr  .add(cell17.getContents()); // 항목명(FR)
							itemTp    .add(cell18.getContents()); // 항목작성타입
//							lblId     .add(cell19.getContents()); // 라벨ID
							itemDtlTp .add(cell19.getContents()); // 항목작성세부타입
							icon      .add(cell20.getContents()); // 아이콘
							style     .add(cell21.getContents()); // 스타일
							oneLineYn .add(cell22.getContents()); // 한줄여부
							itemManYn .add(cell23.getContents()); // 항목필수여부
							editPsbYn .add(cell24.getContents()); // 편집가능여부
							code      .add(cell25.getContents()); // 코드
							exSelKr   .add(cell26.getContents()); // 항목작성선택값(KR)
							exSelEn   .add(cell27.getContents()); // 항목작성선택값(EN)
							exSelFr   .add(cell28.getContents()); // 항목작성선택값(FR)
							exTxtKr   .add(cell29.getContents()); // 항목작성예제(KR)
							exTxtEn   .add(cell30.getContents()); // 항목작성예제(EN)
							exTxtFr   .add(cell31.getContents()); // 항목작성예제(FR)

						}


//						System.out.println("scrnId : scrnNmKr : scrnNmFr >>>>>>>>>>>> ["+scrnId+":"+scrnNmKr+":"+scrnNmFr+"]");

					}


				}


			}



		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * HTML 파일 생성
	 * @param map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean makeHtml(HashMap<?,?> map) throws Exception{
		boolean result = true;
		HashMap paramMap = new HashMap();
		
		ArrayList<String> scrnId    = new ArrayList<String>(); // 업무기능코드
		ArrayList<String> scrnNmKr  = new ArrayList<String>(); // 화면명(KR)
		ArrayList<String> scrnNmEn  = new ArrayList<String>(); // 화면명(EN)
		ArrayList<String> scrnNmFr  = new ArrayList<String>(); // 화면명(FR)
		ArrayList<String> mainGrpKr = new ArrayList<String>(); // 화면메인그룹명(KR)
		ArrayList<String> mainGrpEn = new ArrayList<String>(); // 화면메인그룹명(EN)
		ArrayList<String> mainGrpFr = new ArrayList<String>(); // 화면메인그룹명(FR)
		ArrayList<String> subGrpKr  = new ArrayList<String>(); // 화면서브그룹명(KR)
		ArrayList<String> subGrpEn  = new ArrayList<String>(); // 화면서브그룹명(EN)
		ArrayList<String> subGrpFr  = new ArrayList<String>(); // 화면서브그룹명(FR)
		ArrayList<String> subTabKr  = new ArrayList<String>(); // 화면서브탭명(KR)
		ArrayList<String> subTabEn  = new ArrayList<String>(); // 화면서브탭명(EN)
		ArrayList<String> subTabFr  = new ArrayList<String>(); // 화면서브탭명(FR)
		ArrayList<String> scrnGrpTp = new ArrayList<String>(); // 화면그룹유형
		ArrayList<String> colNm     = new ArrayList<String>(); // 컬럼명
		ArrayList<String> itemNmKr  = new ArrayList<String>(); // 항목명(KR)
		ArrayList<String> itemNmEn  = new ArrayList<String>(); // 항목명(EN)
		ArrayList<String> itemNmFr  = new ArrayList<String>(); // 항목명(FR)
		ArrayList<String> itemTp    = new ArrayList<String>(); // 항목작성타입
		ArrayList<String> lblId     = new ArrayList<String>(); // 라벨ID
		ArrayList<String> itemDtlTp = new ArrayList<String>(); // 항목작성세부타입
		ArrayList<String> icon      = new ArrayList<String>(); // 아이콘
		ArrayList<String> style     = new ArrayList<String>(); // 스타일
		ArrayList<String> oneLineYn = new ArrayList<String>(); // 한줄여부
		ArrayList<String> itemManYn = new ArrayList<String>(); // 항목필수여부
		ArrayList<String> editPsbYn = new ArrayList<String>(); // 편집가능여부
		ArrayList<String> code      = new ArrayList<String>(); // 코드
		ArrayList<String> exSelKr   = new ArrayList<String>(); // 항목작성선택값(KR)
		ArrayList<String> exSelEn   = new ArrayList<String>(); // 항목작성선택값(EN)
		ArrayList<String> exSelFr   = new ArrayList<String>(); // 항목작성선택값(FR)
		ArrayList<String> exTxtKr   = new ArrayList<String>(); // 항목작성예제(KR)
		ArrayList<String> exTxtEn   = new ArrayList<String>(); // 항목작성예제(EN)
		ArrayList<String> exTxtFr   = new ArrayList<String>(); // 항목작성예제(FR)
		
		ArrayList pageItemList   = new ArrayList(); // PAGE ITEM LIST
		ArrayList pageExList     = new ArrayList(); // PAGE EX LIST
		
		try{
		
			scrnId    = (ArrayList)map.get("scrnId"		);
			scrnNmKr  = (ArrayList)map.get("scrnNmKr"	);
			scrnNmEn  = (ArrayList)map.get("scrnNmEn"	);
			scrnNmFr  = (ArrayList)map.get("scrnNmFr"	);
			mainGrpKr = (ArrayList)map.get("mainGrpKr"	);
			mainGrpEn = (ArrayList)map.get("mainGrpEn"	);
			mainGrpFr = (ArrayList)map.get("mainGrpFr"	);
			subGrpKr  = (ArrayList)map.get("subGrpKr"	);
			subGrpEn  = (ArrayList)map.get("subGrpEn"	);
			subGrpFr  = (ArrayList)map.get("subGrpFr"	);
			subTabKr  = (ArrayList)map.get("subTabKr"	);
			subTabEn  = (ArrayList)map.get("subTabEn"	);
			subTabFr  = (ArrayList)map.get("subTabFr"	);
			scrnGrpTp = (ArrayList)map.get("scrnGrpTp"	);
			colNm     = (ArrayList)map.get("colNm"		);
			itemNmKr  = (ArrayList)map.get("itemNmKr"	);
			itemNmEn  = (ArrayList)map.get("itemNmEn"	);
			itemNmFr  = (ArrayList)map.get("itemNmFr"	);
			itemTp    = (ArrayList)map.get("itemTp"		);
			lblId     = (ArrayList)map.get("lblId"		);
			itemDtlTp = (ArrayList)map.get("itemDtlTp"	);
			icon      = (ArrayList)map.get("icon"		);
			style     = (ArrayList)map.get("style"		);
			oneLineYn = (ArrayList)map.get("oneLineYn"	);
			itemManYn = (ArrayList)map.get("itemManYn"	);
			editPsbYn = (ArrayList)map.get("editPsbYn"	);
			code      = (ArrayList)map.get("code"		);
			exSelKr   = (ArrayList)map.get("exSelKr"	);
			exSelEn   = (ArrayList)map.get("exSelEn"	);
			exSelFr   = (ArrayList)map.get("exSelFr"	);
			exTxtKr   = (ArrayList)map.get("exTxtKr"	);
			exTxtEn   = (ArrayList)map.get("exTxtEn"	);
			exTxtFr   = (ArrayList)map.get("exTxtFr"	);
			
			String scrnNm = (String)scrnNmKr.get(0);
			int grpCnt = 0;
			String strGrpNm 	= "";
			String strSubGrpNm 	= "";
			String strSubTabNm 	= "";
			String strItemNm 	= "";
			String strItemTp 	= "";
			String strItemDtlTp = "";
			String strOneLineYn = "";
			String strItemManYn = "";
			String strExSel 	= "";
			String strExTxt 	= "";
			String strScrnGrpTp = "";
			String strIcon 		= "";
			String strStyle 	= "";
			String bfScrnGrpTp  = "";
			String grpItemNm 	= "";
			String grpSubNm 	= "";
			String lastSubGrpNm = "";
			String lastSubTabNm = "";
			ArrayList arrGrpNm = new ArrayList();
			ArrayList arrTabNm = new ArrayList();
			HashMap<String,Integer> idNumMap = new HashMap<String,Integer>();
			HashMap<String,Object> tabMap = new HashMap<String,Object>();
			HashMap<String,Integer> tabCntMap = new HashMap<String,Integer>();
			int cnt = 0;
//			int tabCnt = 0;
			int grpCntVal = 0;
			int strtPos = 0;
			int endPos = 0;
			int tabStrtPos = 0;
			int tabEndPos = 0;
			boolean isBtn = false;
			boolean endRow = false;
			boolean isSubGrp = false;
			
			System.out.println("mainGrpKr.size() >>>>>> ["+mainGrpKr.size()+"]");
			for(int i = 0; i < mainGrpKr.size() ; i++){
				
				if(i == 0){
					strGrpNm = (String)mainGrpKr.get(i);
					arrGrpNm.add((String)mainGrpKr.get(i));
					grpCnt ++;
					cnt = 1;
				}else{
					
					cnt++;
					
					if(!strGrpNm.equals((String)mainGrpKr.get(i))){
						idNumMap.put(String.valueOf(grpCnt), cnt);
						cnt = 0;
						strGrpNm = (String)mainGrpKr.get(i);
						arrGrpNm.add((String)mainGrpKr.get(i));
						grpCnt++;
					}
					
					if(i == mainGrpKr.size() - 1){
						idNumMap.put(String.valueOf(grpCnt), cnt);
					}
				}
				
				if(!"".equals((String)subTabKr.get(i))){
//					tabCnt++;
					
					if(!strSubTabNm.equals((String)subTabKr.get(i))){
//						idNumMap.put(strSubTabNm, tabCnt);
//						tabCnt = 0;
						strSubTabNm = (String)subTabKr.get(i);
						arrTabNm.add((String)subTabKr.get(i));
					}
					
//					tabCntMap.put(String.valueOf(grpCnt), tabCnt);
					tabMap.put(String.valueOf(grpCnt), arrTabNm);
				}else{
					if(!"".equals(strSubTabNm)){
//						idNumMap.put(strSubTabNm, tabCnt);
						strSubTabNm = "";
					}
				}
				
//				System.out.println("strGrpNm >>>>>> ["+strGrpNm+"]");
				
			}
	
			StringBuffer sb = new StringBuffer();
			
			paramMap.put("scrnId", scrnId.get(0)); // SCREEN ID
			paramMap.put("scrnNm", scrnNm); // SCREEN NAME
			
			sb = this.getBaseStart(paramMap); // BASE LAYOUT START
			
			sb.append(this.getTitle(paramMap)).append(newLine); // TITLE LAYOUT
			
			sb.append(this.getTitleBlock(paramMap)).append(newLine); // TITLE BLOCK
			
			sb.append(this.getMainGroupRowStart(paramMap)).append(newLine); // MAIN GROUP ROW START
			
			System.out.println("grpCnt >>>>>>> ["+grpCnt+"]");
			
			for(int i = 0; i < grpCnt ; i++){ // GROUP CNT 만큼 LOOP
				
				strGrpNm = (String)arrGrpNm.get(i);
				paramMap.put("grpNm", arrGrpNm.get(i)); // GROUP NAME
//				System.out.println("strGrpNm >>>>>>> ["+strGrpNm+"]");
//				System.out.println("grpCntVal >>>>>>> ["+String.valueOf(i + 1)+"]");
				grpCntVal = (int)idNumMap.get(String.valueOf(i + 1));
				arrTabNm = (ArrayList)tabMap.get(String.valueOf(i + 1)); // tab array
//				tabCnt = (int)tabCntMap.get(String.valueOf(i + 1)); // tab count
				System.out.println("grpCntVal >>>>>>>> ["+grpCntVal+"]");
				
				if(arrTabNm != null && arrTabNm.size() > 0){
//					paramMap.put("tabCnt", tabCnt);
					paramMap.put("arrTabNm", arrTabNm);
				}else{
//					paramMap.remove("tabCnt");
					paramMap.remove("arrTabNm");
				}
				
				if(i == 0){
					strtPos = i;
					endPos = grpCntVal - 1;
				}else{
					strtPos = endPos;
					endPos = endPos + grpCntVal;
				}
				
				if(!"".equals(strGrpNm)){
				
					sb.append(this.getPanelStart(paramMap)).append(newLine); // PANEL START
					
					sb.append(this.getMainGroupStart(paramMap)).append(newLine); // MAIN GROUP START
				
				}
				
//				System.out.println("scrnId.size() >>>>>>>> ["+scrnId.size()+"]");
				System.out.println("strtPos >>>>>>>> ["+strtPos+"]");
				System.out.println("endPos >>>>>>>> ["+endPos+"]");
				for(int j = strtPos; j < endPos ; j++){
	
					strSubGrpNm 	= (String)subGrpKr.get(j); 		// SUB 그룹
					strItemTp 		= (String)itemTp.get(j); 		// 항목 작성 타입
					strItemDtlTp 	= (String)itemDtlTp.get(j); 	// 항목 작성 상세타입
					strItemNm 		= (String)itemNmKr.get(j); 		// 항목명
					strOneLineYn 	= (String)oneLineYn.get(j); 	// 한줄여부
					strItemManYn 	= (String)itemManYn.get(j); 	// 필수항목여부
					strExSel 		= (String)exSelKr.get(j); 		// 예제 select
					strExTxt 		= (String)exTxtKr.get(j); 		// 예제 value
					strScrnGrpTp	= (String)scrnGrpTp.get(j);		// 화면 그룹 유형
					strIcon			= (String)icon.get(j);			// icon
					strStyle		= (String)style.get(j);			// style
					strSubTabNm 	= (String)subTabKr.get(j);		// TAB 이름
					grpSubNm = scrnNm + "_" + strGrpNm + "_" + strSubGrpNm;
					grpItemNm = strGrpNm + "_" + strSubGrpNm + "_" + strItemNm + "_" + j;
	
					paramMap.put("mainGrpNm" , strGrpNm);
					paramMap.put("subGrpNm"	 , strSubGrpNm);
					paramMap.put("itemNm"	 , strItemNm);
					paramMap.put("itemDtlTp" , strItemDtlTp);
					paramMap.put("itemManYn" , strItemManYn);
					paramMap.put("exSel"	 , strExSel);
					paramMap.put("exTxt"	 , strExTxt);
					paramMap.put("icon" 	 , strIcon);
					paramMap.put("style" 	 , strStyle);
					paramMap.put("grpItemNm" , grpItemNm);
					paramMap.put("grpSubNm"  , grpSubNm);
					paramMap.put("oneLineYn" , strOneLineYn);

//					System.out.println("strItemNm 	>>>>>> ["+strItemNm+"]");
					
					/*
					System.out.println("subGrpNm 	>>>>>> ["+strSubGrpNm+"]");
					System.out.println("itemNm 		>>>>>> ["+strItemNm+"]");
					System.out.println("itemDtlTp 	>>>>>> ["+strItemDtlTp+"]");
					System.out.println("itemManYn 	>>>>>> ["+strItemManYn+"]");
					System.out.println("exSel 		>>>>>> ["+strExSel+"]");
					System.out.println("exTxt 		>>>>>> ["+strExTxt+"]");
					System.out.println("grpItemNm 	>>>>>> ["+grpItemNm+"]");
					System.out.println("grpSubNm 	>>>>>> ["+grpSubNm+"]");
					*/
					
					// 서브 그룹명이 있을 경우
					if(!"".equals(strSubGrpNm)){
						
						if(!strSubGrpNm.equals(lastSubGrpNm)){
						
							if(!"".equals(lastSubGrpNm)){
								
								// 서브 그룹명이 있을 경우
								if(isSubGrp){
									
									paramMap.put("subGrpNm", lastSubGrpNm);
									
									sb.append(this.getSubGroupContentRowEnd(paramMap)).append(newLine); // SUB GROUP CONTENT ROW END
									
								}
								
							}
							
							paramMap.put("subGrpNm", strSubGrpNm);
							
							sb.append(this.getSubGroupTitleRow(paramMap)).append(newLine); // SUB GROUP TITLE ROW
							
							sb.append(this.getSubGroupContentRowStart(paramMap)).append(newLine); // SUB GROUP CONTENT ROW START
							
							lastSubGrpNm = strSubGrpNm;
							
							isSubGrp = true;
							
						
						}
						
					}else{
						
						// 서브 그룹명이 있을 경우
						if(isSubGrp){
							
							paramMap.put("subGrpNm", lastSubGrpNm);
							
							sb.append(this.getSubGroupContentRowEnd(paramMap)).append(newLine); // SUB GROUP CONTENT ROW END
							
							lastSubGrpNm = "";
							isSubGrp = false;
							
							paramMap.put("subGrpNm", strSubGrpNm);
						}
						
					}
					
					// 서브 TAB 명이 있을 경우
					if(!"".equals(strSubTabNm)){
						
						// TAB 시작
						if("".equals(lastSubTabNm)){

							sb.append(this.getSubTabMain(paramMap)).append(newLine); // SUB TAB MAIN
							
							sb.append(this.getTabGrpStart(paramMap)).append(newLine); // TAB GROUP START
						}
						
						if(!lastSubTabNm.equals(strSubTabNm)){
						
							if("".equals(lastSubTabNm)){
								paramMap.put("tabNm", strSubTabNm);
								paramMap.put("active", "in active");
							}else{
								
								if(endRow){
									sb.append(this.getBaseRowEnd(paramMap)).append(newLine); // ROW END
									endRow = false;
								}

								paramMap.put("tabNm", lastSubTabNm);
								sb.append(this.getTabEnd(paramMap)).append(newLine); // TAB END
								
								paramMap.put("active", "");
							}
							
							paramMap.put("tabNm", strSubTabNm);
							sb.append(this.getTabStart(paramMap)).append(newLine); // TAB START
							
//							tabStrtPos = j;
//							tabEndPos = j + (Integer)idNumMap.get(strSubTabNm);
							
							lastSubTabNm = strSubTabNm;
						
						}
						
					}else{
						
						if(!"".equals(lastSubTabNm)){
							
							if(endRow){
								sb.append(this.getBaseRowEnd(paramMap)).append(newLine); // ROW END
								endRow = false;
							}
							
							paramMap.put("tabNm", lastSubTabNm);
							sb.append(this.getTabEnd(paramMap)).append(newLine); // TAB END
							
							sb.append(this.getTabGrpEnd(paramMap)).append(newLine); // TAB GROUP END
							lastSubTabNm = "";
						}
						
					}

//					System.out.println("strScrnGrpTp >>>>>>>> ["+strScrnGrpTp+"]");
					// 화면그룹유형이 없을 경우
					if("".equals(strScrnGrpTp)){

						System.out.println("scrnId >>>>>>>> ["+scrnId.get(0)+"]");
//						System.out.println("pageItemList.size() >>>>> ["+pageItemList.size()+"]");
						// 테이블 페이지 가 있으면 
						if(pageItemList != null && pageItemList.size() > 0){
							System.out.println("bfScrnGrpTp >>>>>> ["+bfScrnGrpTp+"]");
							paramMap.put("pageItemList", pageItemList);
							paramMap.put("pageExList", pageExList);
							paramMap.put("scrnGrpTp", strScrnGrpTp);
							if("테이블_페이징".equals(bfScrnGrpTp)){
								sb.append(this.getListPaging(paramMap)); // 테이블 페이징
							}else if("테이블".equals(bfScrnGrpTp)){
								sb.append(this.getListTable(paramMap)); // 테이블
							}else{
								System.out.println(">>>>>>>>>> 화면그룹유형 오류 <<<<<<<<<<<<");
							}
							pageItemList = new ArrayList();
							pageExList = new ArrayList();
							bfScrnGrpTp = strScrnGrpTp;
						}
						
						if(strItemTp != null && !"".equals(strItemTp)){
							
//							System.out.println("strItemTp    >>>>>>> ["+strItemTp+"]");
//							System.out.println("strItemDtlTp >>>>>>> ["+strItemDtlTp+"]");
					
							if("BUTTON".equals(strItemTp)){ // BUTTON
								
								if(!isBtn){
									sb.append(this.getBtnGrpRowStart(paramMap)).append(newLine); // BUTTON GROUP ROW START
	
									sb.append(this.getBtnGrpStart(paramMap)).append(newLine); // BUTTON GROUP  START
								}

								sb.append(this.getButton(paramMap)).append(newLine);
								
								isBtn = true;
								
								if(j == (endPos - 1)){

									sb.append(this.getBtnGrpEnd(paramMap)).append(newLine); // BUTTON GROUP END
	
									sb.append(this.getBtnGrpRowEnd(paramMap)).append(newLine); // BUTTON GROUP ROW END
									
									isBtn = false;
								}
								
							}else if("LABEL".equals(strItemTp) && "LINE".equals(strItemDtlTp)){
							
								sb.append(this.getLabel(paramMap)).append(newLine);
								
								isBtn = false;
								endRow = false;
							
							}else{
								
								if(isBtn){

									sb.append(this.getBtnGrpEnd(paramMap)).append(newLine); // BUTTON GROUP END
	
									sb.append(this.getBtnGrpRowEnd(paramMap)).append(newLine); // BUTTON GROUP ROW END
									
									isBtn = false;
								}
								
								if(!endRow){
							
									sb.append(this.getBaseRowStart(paramMap)).append(newLine); // ROW START
								
									endRow = true;
								
								}else{
									endRow = false;
								}
								
								if("DATE".equals(strItemTp)){ // DATE
									
									if("RANGE".equals(strItemDtlTp)){ // RANGE DATE
										paramMap.put("inline", " form-inline");
									}else{
										paramMap.put("inline", "");
									}
									
								}else if("SELECT".equals(strItemTp)){ // SELECT BOX
									paramMap.put("inline", "");
								}else if("TEXT".equals(strItemTp)){ // INPUT TEXT
									
									if("SELECT".equals(strItemDtlTp)){
										paramMap.put("inline", " form-inline");
									}else if("AUTO_COMPLETE".equals(strItemDtlTp)){
										paramMap.put("inline", " form-inline");
									}else{
										paramMap.put("inline", "");
									}
									
								}else if("TEXTAREA".equals(strItemTp)){ // TEXTAREA
									paramMap.put("inline", "");
								}else if("LABEL".equals(strItemTp)){ // LABEL
									paramMap.put("inline", "");
								}else if("RADIO".equals(strItemTp)){ // RADIO BUTTON
									paramMap.put("inline", " form-inline");
								}else if("BUTTON".equals(strItemTp)){ // BUTTON
									paramMap.put("inline", "");
								}else if("FILE".equals(strItemTp)){ // FILE
									paramMap.put("inline", " form-inline");
								}
								
								if("Y".equals(strOneLineYn)){ // 한줄여부
									sb.append(this.getSm12Start(paramMap)).append(newLine); // SM12 START
									sb.append(this.getFormGroupSmStart(paramMap)).append(newLine); // FORM GROUP START
									sb.append(this.getFormSm2Start(paramMap)).append(newLine); // FORM START
									endRow = false;
								}else{
									sb.append(this.getSm6Start(paramMap)).append(newLine); // SM6 START
									sb.append(this.getFormGroupSmStart(paramMap)).append(newLine); // FORM GROUP START
									sb.append(this.getFormSm4Start(paramMap)).append(newLine); // FORM START
								}
	
								
								
								if("DATE".equals(strItemTp)){ // DATE
									
									if("RANGE".equals(strItemDtlTp)){ // RANGE DATE
										
										sb.append(this.getDateRange(paramMap)).append(newLine);
										
									}else{
										
										sb.append(this.getDate(paramMap)).append(newLine);
										
									}
									
								}else if("SELECT".equals(strItemTp)){ // SELECT BOX
									
									sb.append(this.getSelect(paramMap)).append(newLine);
									
								}else if("TEXT".equals(strItemTp)){ // INPUT TEXT
									
									sb.append(this.getText(paramMap)).append(newLine);
									
								}else if("TEXTAREA".equals(strItemTp)){ // TEXTAREA
									
									sb.append(this.getTextarea(paramMap)).append(newLine);
									
								}else if("LABEL".equals(strItemTp)){ // LABEL
									
									sb.append(this.getLabel(paramMap)).append(newLine);
									
								}else if("RADIO".equals(strItemTp)){ // RADIO BUTTON
									
									sb.append(this.getRadio(paramMap)).append(newLine);
									
								}else if("BUTTON".equals(strItemTp)){ // BUTTON
									
									sb.append(this.getButton(paramMap)).append(newLine);
									
								}else if("FILE".equals(strItemTp)){ // FILE
									
									sb.append(this.getFile(paramMap)).append(newLine);
									
								}
								
//								sb.append(this.getFormSm4End(paramMap)).append(newLine); // FORM END
								
								sb.append(this.getFormGroupSmEnd(paramMap)).append(newLine); // FORM GROUP END
								
								if("Y".equals(strOneLineYn)){ // 한줄여부
									sb.append(this.getSm12End(paramMap)).append(newLine); // SM12 END
								}else{
									sb.append(this.getSm6End(paramMap)).append(newLine); // SM6 END
								}
								
								if(!endRow){
									sb.append(this.getBaseRowEnd(paramMap)).append(newLine); // ROW END
								}
							}
						
						}
					}else{
						
						if(isBtn){

							sb.append(this.getBtnGrpEnd(paramMap)).append(newLine); // BUTTON GROUP END

							sb.append(this.getBtnGrpRowEnd(paramMap)).append(newLine); // BUTTON GROUP ROW END
							
							isBtn = false;
						}
						
						if("테이블_페이징".equals(strScrnGrpTp) || "테이블".equals(strScrnGrpTp)){
	
							pageItemList.add(strItemNm);
							pageExList.add(strExTxt);
							bfScrnGrpTp = strScrnGrpTp;
							continue;
							
						}else{// 테이블_세로2
							
						}
					}
				
				}
				
				if(!"".equals(strGrpNm)){
				
					sb.append(this.getMainGroupEnd(paramMap)).append(newLine); // MAIN GROUP END
					
					sb.append(this.getPanelEnd(paramMap)).append(newLine); // PANEL END
				
				}
			}
			
			
			sb.append(this.getMainGroupRowEnd(paramMap)).append(newLine); // MAIN GROUP ROW END
			
			sb.append(this.getBaseEnd(paramMap)).append(newLine); // BASE LAYOUT END
			
			System.out.println(">>>>>>>>>>>>>> html start >>>>>>>>>>>>>>>>>>>>>");
			System.out.println(sb.toString());
			System.out.println(">>>>>>>>>>>>>>> html end   >>>>>>>>>>>>>>>>>>>>");
	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		result = false;
		return result;
	}

	/**
	 * 기본 레이아웃 START
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBaseStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("<!DOCTYPE html>").append(newLine);
		sb.append("<html>").append(newLine);
		sb.append("<head>").append(newLine);
		sb.append("<title>"+map.get("scrnNm")+"</title>").append(newLine);
		sb.append("<meta charset='utf-8'>").append(newLine);
		sb.append("<meta name='viewport' content='width=device-width, initial-scale=1'>").append(newLine);
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>").append(newLine);
		sb.append("<script src='./lib/jquery/jquery-1.12.4.js'></script>").append(newLine);
		sb.append("<script src='./lib/moment/moment-2.14.1.min.js'></script>").append(newLine);
		sb.append("<script src='./lib/moment/moment-with-locales-2.14.1.js'></script>").append(newLine);
		sb.append("<script src='./lib/bootstrap/3.3.7/dist/js/bootstrap.min.js'></script>").append(newLine);
		sb.append("<script src='./lib/eonasdan-bootstrap-datetimepicker/4.17.37/build/js/bootstrap-datetimepicker.min.js'></script>").append(newLine);
		sb.append("<script src='./lib/custom/custom.js'></script>").append(newLine);
		sb.append("<link rel='stylesheet' href='./lib/bootstrap/3.3.7/dist/css/bootstrap.min.css' />").append(newLine);
		sb.append("<link rel='stylesheet' href='./lib/eonasdan-bootstrap-datetimepicker/4.17.37/build/css/bootstrap-datetimepicker.min.css' />").append(newLine);
		sb.append("<link rel='stylesheet' href='./lib/custom/custom.css' />").append(newLine);
		sb.append("<link rel='stylesheet' href='./lib/custom/style.css' />").append(newLine);
		sb.append("</head>").append(newLine);
		sb.append("<body>").append(newLine);
		sb.append("<div class='container-fluid'><!--최상위 컨테이너 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * 기본 레이아웃 END
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBaseEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("</div><!--최상위 컨테이너 종료-->").append(newLine);
		sb.append("</body>").append(newLine);
		sb.append("</html>").append(newLine);

		return sb;
	}

	/**
	 * TITLE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTitle(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("<div class='row fixedTitle'><!--화면명 Row 시작-->").append(newLine);
		sb.append("  <div class='col-sm-12 headerExt'>").append(newLine);
		sb.append("    <h1><span class='glyphicon glyphicon-th-large'></span>"+map.get("scrnNm")+"</h1>").append(newLine);
		sb.append("    <div class='pull-right'><a href='"+map.get("scrnId")+"(KR).html'>KR</a> | <a href='"+map.get("scrnId")+"(EN).html'>EN</a> | <a href='"+map.get("scrnId")+"(FR).html'>FR</a></div>").append(newLine);
		sb.append("  </div>").append(newLine);
		sb.append("</div><!--화면명 Row 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * TITLE BLOCK 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTitleBlock(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("<div class='fixedTitleBlock'></div>").append(newLine);

		return sb;
	}
	
	/**
	 * MAIN GROUP ROW START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getMainGroupRowStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class='main-group row'><!--화면메인그룹 Row 시작-->").append(newLine);
		sb.append("    <div class='col-sm-12'>").append(newLine);
		
		return sb;
	}
	
	/**
	 * MAIN GROUP ROW END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getMainGroupRowEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("    </div>").append(newLine);
		sb.append("</div><!--화면메인그룹 Row 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * SUB GROUP TITLE ROW 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSubGroupTitleRow(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		String grpSubNm = (String)map.get("grpSubNm");
		String subGrpNm = (String)map.get("subGrpNm");
		
		sb.append("<div class='sub-group-title row'><!--화면서브그룹 Title Row 시작-->").append(newLine);
		sb.append("  <div class='col-sm-12'>").append(newLine);
		sb.append("    <h3 onclick=\"$('#").append(grpSubNm).append("').toggle();\" id='").append(subGrpNm).append("'><span class='glyphicon glyphicon-menu-hamburger'></span>").append(subGrpNm).append("</h3>").append(newLine);
		sb.append("  </div>").append(newLine);
		sb.append("</div>").append(newLine);
		
		return sb;
	}
	
	/**
	 * SUB GROUP CONTENT ROW START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSubGroupContentRowStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		String grpSubNm = (String)map.get("grpSubNm");
		String subGrpNm = (String)map.get("subGrpNm");
		
		sb.append("<div class='sub-group-content row'><!--화면서브그룹 Content Row 시작-->").append(newLine);
		sb.append("  <div id='").append(grpSubNm).append("' class='panel-body'><!--").append(subGrpNm).append(" 화면서브그룹 Content 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * SUB GROUP CONTENT ROW END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSubGroupContentRowEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		String subGrpNm = (String)map.get("subGrpNm");
		
		sb.append("  </div><!--").append(subGrpNm).append(" 화면서브그룹 Content 종료-->").append(newLine);
		sb.append("</div><!--화면서브그룹 Content Row 종료-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * PANEL START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getPanelStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String scrnNm = (String)map.get("scrnNm");
		String grpNm = (String)map.get("grpNm");
		String scrnNmGrNm = scrnNm + "_" + grpNm;
		
		sb.append("<div class='panel panel-default'><!--").append(grpNm).append(" 패널 시작-->").append(newLine);
		sb.append("<div class='panel-heading clearfix'>").append(newLine);
		sb.append("<h2 onclick=\"$('#").append(scrnNmGrNm).append("').toggle();\"").append(" id='").append(grpNm).append("'><span class='glyphicon glyphicon-chevron-right'></span> ").append(grpNm).append("</h2>").append(newLine);
		sb.append("</div>").append(newLine);
		
		return sb;
	}
	
	/**
	 * PANEL END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getPanelEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String grpNm = (String)map.get("grpNm");
		
		sb.append("</div><!--").append(grpNm).append(" 패널 종료-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * MAIN GROUP START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getMainGroupStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String scrnNm = (String)map.get("scrnNm");
		String grpNm = (String)map.get("grpNm");
		String scrnNmGrNm = scrnNm + "_" + grpNm;
		
		sb.append("<div id='").append(scrnNmGrNm).append("' class='panel-body'><!--").append(grpNm).append("화면메인그룹 시작-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * MAIN GROUP END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getMainGroupEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String grpNm = (String)map.get("grpNm");
		
//		sb.append("<hr>");
		sb.append("</div><!--").append(grpNm).append(" 화면메인그룹 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * BASE ROW START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBaseRowStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<div class='row'><!--Row 시작-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * BASE ROW END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBaseRowEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("</div><!--Row 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON GROUP ROW START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnGrpRowStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("    <div class='row'><!--버튼그룹 Row 시작-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * BUTTON GROUP ROW END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnGrpRowEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("    </div><!--버튼그룹 Row 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * SUB TAB MAIN 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSubTabMain(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		String mainGrpNm = (String)map.get("mainGrpNm");
//		int tabCnt = (Integer)map.get("tabCnt");
		ArrayList arrTabNm = (ArrayList)map.get("arrTabNm");
		String tabNm = "";
		
		sb.append("    <ul class='nav nav-tabs'>").append(newLine);
		
		for(int i = 0; i < arrTabNm.size(); i++ ){
			
			tabNm = (String)arrTabNm.get(i);
			
			if(i == 0){
				sb.append("      <li class='active'>");
			}else{
				sb.append("      <li>");
			}
			sb.append("      <a data-toggle='tab' href='#").append(mainGrpNm).append("_").append(tabNm).append("'>").append(tabNm).append("</a></li>").append(newLine);
			
		}
		sb.append("    </ul>").append(newLine);
		
		return sb;
	}
	
	/**
	 * TAB GROUP START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTabGrpStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("      <div class='tab-content'><!--탭그룹 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * TAB GROUP END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTabGrpEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("      </div><!--탭그룹 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * TAB GROUP START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTabStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		String mainGrpNm = (String)map.get("mainGrpNm");
		String tabNm = (String)map.get("tabNm");
		String active = (String)map.get("active");

		sb.append("      <div id='").append(mainGrpNm).append("_").append(tabNm).append("' class='tab-pane fade ").append(active).append("'><!--").append(mainGrpNm).append("_").append(tabNm).append(" 탭 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * TAB GROUP END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTabEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String mainGrpNm = (String)map.get("mainGrpNm");
		String tabNm = (String)map.get("tabNm");

		sb.append("      </div><!--").append(mainGrpNm).append("_").append(tabNm).append(" 탭 종료-->").append(newLine);

		return sb;
	}

	
	/**
	 * SM6 START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSm6Start(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("  <div class=col-sm-6>").append(newLine);
		
		return sb;
	}
	
	/**
	 * SM6 END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSm6End(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("  </div>").append(newLine);

		return sb;
	}
	
	/**
	 * SM12 START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSm12Start(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
		sb.append("  <div class=col-sm-12>").append(newLine);
		
		return sb;
	}
	
	/**
	 * SM12 END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSm12End(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("  </div>").append(newLine);

		return sb;
	}
	
	/**
	 * FORM GROUP SM START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFormGroupSmStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
//		String inline = "form-inline";
		
		sb.append("    <div class='form-group").append(map.get("inline")).append(" form-group-sm '><!--항목내의 폼그룹 시작-->").append(newLine);
		
		return sb;
	}
	
	/**
	 * FORM GROUP END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFormGroupSmEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("    </div><!--항목내의 폼그룹 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * FORM SM4 START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFormSm4Start(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String itemNm = (String)map.get("itemNm");
		String grpItemNm = (String)map.get("grpItemNm");
		
		sb.append("      <label class='control-label col-sm-4 ' for='").append(grpItemNm).append("'>").append(itemNm);
		if("Y".equals(map.get("itemManYn"))){
			sb.append("<span class='mandatory'> *</span>");
		}
		sb.append("</label>").append(newLine);
		
		return sb;
	}
	
	/**
	 * FORM SM2 START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFormSm2Start(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String itemNm = (String)map.get("itemNm");
		String grpItemNm = (String)map.get("grpItemNm");
		
		sb.append("      <label class='control-label col-sm-2 ' for='").append(grpItemNm).append("'>").append(itemNm);
		if("Y".equals(map.get("itemManYn"))){
			sb.append("<span class='mandatory'> *</span>");
		}
		sb.append("</label>").append(newLine);
		
		return sb;
	}
	
	/**
	 * FORM SM4 END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFormSm4End(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("      </div>").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON ROW START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnRowStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("    <div class='row'><!--버튼그룹 Row 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON ROW END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnRowEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("    </div>").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON GROUP START 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnGrpStart(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("      <div class='col-sm-12 text-right'><!--버튼그룹 시작-->").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON GROUP END 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getBtnGrpEnd(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		sb.append("      </div><!--버튼그룹 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * BUTTON 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getButton(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String itemNm = (String)map.get("itemNm");
		String icon = (String)map.get("icon");
		String exTxt = (String)map.get("exTxt");

		sb.append("      <button class='btn btn-default ");
		if(exTxt != null && !"".equals(exTxt)){
			sb.append("btn-").append(exTxt.toLowerCase());
		}
		sb.append("' type='button' value='").append(itemNm).append("' ><span class='glyphicon glyphicon-").append(icon.toLowerCase()).append("'></span> ").append(itemNm).append("</button>").append(newLine);

		return sb;
	}

	/**
	 * CHECKBOX 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getCheckbox(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}

		String grpItemNm = (String)map.get("grpItemNm");

        sb.append("    <input name='").append(grpItemNm).append("' id='").append(grpItemNm).append("' type='checkbox' value='' >").append(newLine);
        sb.append("    <label class='label-for-select ' for='").append(grpItemNm).append("' /></label>").append(newLine);

		return sb;
	}

	/**
	 * DATE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getDate(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
        String exTxt = (String)map.get("exTxt");

        sb.append("  <div class='col-sm-8 input-group'>").append(newLine);
        sb.append("    <div class='input-group date' id='").append(grpItemNm).append("_div'>").append(newLine);
        sb.append("      <input type='text' class='form-control '  id='").append(grpItemNm).append("' value='").append(exTxt).append("' />").append(newLine);
        sb.append("      <span class='input-group-addon'>").append(newLine);
        sb.append("        <span class='glyphicon glyphicon-calendar'></span>").append(newLine);
        sb.append("      </span>").append(newLine);
        sb.append("    </div>").append(newLine);
        sb.append("  </div>").append(newLine);
        sb.append("  <script type='text/javascript'>").append(newLine);
        sb.append("    $(function () { $('#").append(grpItemNm).append("_div').datetimepicker({format: 'DD/MM/YYYY'}); });").append(newLine);
        sb.append("  </script>").append(newLine);

		return sb;
	}
	
	/**
	 * DATE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getDateRange(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");

        sb.append("  <div class='col-sm-8 input-group'>").append(newLine);
        sb.append("    <div class='input-group date' id='").append(grpItemNm).append("_div'>").append(newLine);
        sb.append("      <input type='text' class='form-control '  id='").append(grpItemNm).append("' value='' />").append(newLine);
        sb.append("      <span class='input-group-addon'>").append(newLine);
        sb.append("        <span class='glyphicon glyphicon-calendar'></span>").append(newLine);
        sb.append("      </span>").append(newLine);
        sb.append("    </div>").append(newLine);
        sb.append("          ~").append(newLine);
        sb.append("    <div class='input-group date' id='").append(grpItemNm).append("_div'>").append(newLine);
        sb.append("      <input type='text' class='form-control '  id='").append(grpItemNm).append("' value='' />").append(newLine);
        sb.append("      <span class='input-group-addon'>").append(newLine);
        sb.append("        <span class='glyphicon glyphicon-calendar'></span>").append(newLine);
        sb.append("      </span>").append(newLine);
        sb.append("    </div>").append(newLine);
        sb.append("  </div>").append(newLine);
        sb.append("  <script type='text/javascript'>").append(newLine);
        sb.append("    $(function () { $('#").append(grpItemNm).append("_div').datetimepicker({format: 'DD/MM/YYYY'}); });").append(newLine);
        sb.append("    $(function () { $('#").append(grpItemNm).append("_div').datetimepicker({format: 'DD/MM/YYYY'}); });").append(newLine);
        sb.append("  </script>").append(newLine);

		return sb;
	}

	/**
	 * DATETIME 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getDatetime(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
        String exTxt = (String)map.get("exTxt");
		String grpItemNm = (String)map.get("grpItemNm");

        sb.append("  <div class='col-sm-8 input-group'>").append(newLine);
        sb.append("    <div class='input-group date' id='").append(grpItemNm).append("_div'>").append(newLine);
        sb.append("      <input type='text' class='form-control '  id='").append(grpItemNm).append("' value='").append(exTxt).append("' />").append(newLine);
        sb.append("      <span class='input-group-addon'>").append(newLine);
        sb.append("        <span class='glyphicon glyphicon-calendar'></span>").append(newLine);
        sb.append("      </span>").append(newLine);
        sb.append("    </div>").append(newLine);
        sb.append("  </div>").append(newLine);
        sb.append("  <script type='text/javascript'>").append(newLine);
        sb.append("    $(function () { $('#").append(grpItemNm).append("_div').datetimepicker({format: 'DD/MM/YYYY HH:mm'}); });").append(newLine);
        sb.append("  </script>").append(newLine);
		
		return sb;
	}

	/**
	 * FILE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getFile(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
		String style = (String)map.get("style");

        sb.append("    <input class='form-control input-file ").append(style.toLowerCase()).append("' type='text' id='").append(grpItemNm).append("' value='' readonly>").append(newLine);
        sb.append("    <label class='input-group-btn'>").append(newLine);
        sb.append("      <span class='btn btn-primary btn-sm'>").append(newLine);
        sb.append("        &hellip; <span class='glyphicon glyphicon-open-file'><input type='file' style='display: none;' multiple>").append(newLine);
        sb.append("      </span>").append(newLine);
        sb.append("    </label>").append(newLine);
		
		return sb;
	}

	/**
	 * LABEL 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getLabel(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
		String strItemDtlTp = (String)map.get("itemDtlTp");
		
		System.out.println("strItemDtlTp >>>>>>>>> ["+strItemDtlTp+"]");

		if("LINE".equals(strItemDtlTp)){
			
			sb.append(this.getBaseRowStart(map)).append(newLine); // ROW START
		
			sb.append("  <div class=col-sm-12><!--단일 라인 시작-->").append(newLine);
	        sb.append("    <div class='form-group form-inline form-group-sm'><!--단일 라인 폼그룹 시작-->").append(newLine);
	        sb.append("      <label class='control-label label-line ' for='").append(grpItemNm).append("'></label>").append(newLine);
	        sb.append("    </div><!--단일 라인 폼그룹 종료-->").append(newLine);
	        sb.append("  </div><!--단일 라인 종료-->").append(newLine);
	        
	        sb.append(this.getBaseRowEnd(map)).append(newLine); // ROW END
        
		}else{
			
	        sb.append("  <div class='col-sm-8 input-group'>").append(newLine);
	        sb.append("    <div class='text-group col-sm-12' id=''>").append(newLine);
	        sb.append("      <input class='form-control ' type='text' id='").append(grpItemNm).append("' size='10' >").append(newLine);
	        sb.append("    </div>").append(newLine);
	        sb.append("  </div>").append(newLine);
			
		}

		return sb;
	}

	/**
	 * RADIO 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getRadio(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
		
		String exSel = (String)map.get("exSel");
		String exTxt = (String)map.get("exTxt");
		String checked = "";
		
		System.out.println("Radio exSel >>>> ["+exSel+"]");
		
		String[] arrEx = exSel.split("\\|");

		System.out.println("Radio arrEx.length >>>> ["+arrEx.length+"]");

        sb.append("        <div class='col-sm-8'>").append(newLine);
        if(arrEx != null && arrEx.length > 0){
        	for(int i = 0; i < arrEx.length ; i++){
        		
        		if(arrEx[i].equals(exTxt)){
        			checked = "checked";
        		}else{
        			checked = "";
        		}
        		
        		sb.append("          <input name='").append(grpItemNm).append("' id='").append(grpItemNm).append("_").append(i+1).append("' type='radio' ").append(checked).append(" value='").append(arrEx[i]).append("'>").append(newLine);
        		sb.append("          <label class='label-for-select ' for='").append(grpItemNm).append("'>").append(arrEx[i]).append("</label>").append(newLine);
        	}
        }
        sb.append("        </div>");

		return sb;
	}

	/**
	 * SELECT 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSelect(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
		String exSel = (String)map.get("exSel");
		String exTxt = (String)map.get("exTxt");
		String selected = "";
		
		System.out.println("exSel >>>>>>> ["+exSel+"]");
		String[] arrEx = exSel.split("\\|");
		System.out.println("arrEx.length >>>>>>> ["+arrEx.length+"]");

        sb.append("        <div class='col-sm-8'>").append(newLine);
        sb.append("          <select class='form-control ' id='").append(grpItemNm).append("' >").append(newLine);
        if(arrEx != null && arrEx.length > 0){
        	for(int i = 0; i < arrEx.length ; i++){
        		
        		if(arrEx[i].equals(exSel)){
        			selected = "selected";
        		}else{
        			selected = "";
        		}
        		
        		sb.append("            <option ").append(selected).append(">").append(arrEx[i]).append("</option>").append(newLine);
        	}
        }
        sb.append("          </select>").append(newLine);
        sb.append("        </div>").append(newLine);

		return sb;
	}

	/**
	 * TEXT 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getText(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		
//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
		String grpItemNm = (String)map.get("grpItemNm");
		String strItemDtlTp = (String)map.get("itemDtlTp");
        String exSel = (String)map.get("exSel");
        String exTxt = (String)map.get("exTxt");
        String txt1 = "";
        String txt2 = "";
        String oneLineYn = (String)map.get("oneLineYn");
        String strClass = "col-sm-8";
        
        if("Y".equals(oneLineYn)){
        	strClass = "col-sm-10";
        }
		
		String[] arrEx = exSel.split("\\|");
		String[] arrTxt = exTxt.split("!!");
		System.out.println("exTxt >>>>>>> ["+exTxt+"]");
		
		if("SELECT".equals(strItemDtlTp)){ // TEXT 옆 SELECT BOX
			
			sb.append("        <div class='").append(strClass).append(" input-group'><!--항목내의 텍스트 시작-->").append(newLine);
	        sb.append("          <div class='input-group col-sm-7' id=''>").append(newLine);
	        sb.append("          <input class='form-control ' type='text' id='").append(grpItemNm).append("' value='").append(exTxt).append("'  >").append(newLine);
	        sb.append("          </div>").append(newLine);
	        sb.append("          <div class='input-group col-sm-4' id='").append(grpItemNm).append("' ><!--텍스트 옆 SELECT 시작-->").append(newLine);
	        sb.append("            <select class='form-control ' id='").append(grpItemNm).append("' >").append(newLine);
	        if(arrEx != null && arrEx.length > 0){
	        	for(int i = 0; i < arrEx.length ; i++){
	        		sb.append("            <option >").append(arrEx[i]).append("</option>").append(newLine);
	        	}
	        }
	        sb.append("            </select>").append(newLine);
	        sb.append("          </div><!--텍스트 옆 SELECT 종료-->").append(newLine);
	        sb.append("        </div><!--항목내의 텍스트 종료-->").append(newLine);
			
		}else if("AUTO_COMPLETE".equals(strItemDtlTp)){ // TEXT 옆 POPUP
			
			if(arrTxt.length > 0){
				txt1 = arrTxt[0];
				if(arrTxt.length > 1){
					txt2 = arrTxt[1];
				}
			}
			
			sb.append("        <div class='").append(strClass).append(" input-group'><!--항목내의 텍스트 시작-->").append(newLine);
	        sb.append("          <div class='input-group col-sm-4' id=''>").append(newLine);
	        sb.append("          <input class='form-control ' type='text' id='").append(grpItemNm).append("' value='").append(txt1).append("'  >").append(newLine);
	        sb.append("          <a class='input-group-addon' href='javascript://return false;'><span class='glyphicon glyphicon-search'></span> </a>").append(newLine);
	        sb.append("          </div>").append(newLine);
	        sb.append("          <div class='input-group text-center' id='").append(grpItemNm).append("' ></div>").append(newLine);
	        sb.append("          <div class='input-group col-sm-7' id=''>").append(newLine);
	        sb.append("          <input class='form-control ' type='text' id='").append(grpItemNm).append("' value='").append(txt2).append("' readonly >").append(newLine);
	        sb.append("          </div>").append(newLine);
	        sb.append("        </div><!--항목내의 텍스트 종료-->").append(newLine);
			
		}else{
		
	        sb.append("        <div class='").append(strClass).append(" input-group'><!--항목내의 텍스트 시작-->").append(newLine);
	        sb.append("          <div class='input-group col-sm-12' id=''>").append(newLine);
	        sb.append("          <input class='form-control ' type='text' id='").append(grpItemNm).append("' value='").append(exTxt).append("'  >").append(newLine);
	        sb.append("          </div>").append(newLine);
	        sb.append("        </div><!--항목내의 텍스트 종료-->").append(newLine);
		
		}


		return sb;
	}

	/**
	 * TEXTAREA 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getTextarea(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

//		String grpNm = (String)map.get("grpNm");
//		String subGrpNm = (String)map.get("subGrpNm");
//		String itemNm = (String)map.get("itemNm");
//		String grpItemNm = grpNm + "_" + subGrpNm + "_" + itemNm;
//		
//		if(idNumMap.containsKey(grpItemNm)){
//			grpItemNm = idNumMap.get(grpItemNm) + "_1";
//			idNumMap.put(grpItemNm, grpItemNm);
//		}
        String exTxt = (String)map.get("exTxt");
		String grpItemNm = (String)map.get("grpItemNm");
        String oneLineYn = (String)map.get("oneLineYn");
        String strClass = "col-sm-8";
        
        if("Y".equals(oneLineYn)){
        	strClass = "col-sm-10";
        }

        sb.append("        <div class='").append(strClass).append(" input-group'><!--항목내의 텍스트 시작-->").append(newLine);
        sb.append("          <div class='input-group col-sm-12' id=''>").append(newLine);
        sb.append("          <textarea class='form-control y' rows='4' id='").append(grpItemNm).append("' >").append(exTxt).append("</textarea>").append(newLine);
        sb.append("          </div>").append(newLine);
        sb.append("        </div><!--항목내의 텍스트 종료-->").append(newLine);

		return sb;
	}
	
	/**
	 * LIST PAGING 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getListPaging(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		ArrayList itemList 	= (ArrayList)map.get("pageItemList");
		ArrayList exList 	= (ArrayList)map.get("pageExList");
		String[] arrEx = null;
		
		String exTxt = (String)exList.get(0);
		arrEx = exTxt.split("\\|");
		int exCnt = arrEx.length;

		String grpNm = (String)map.get("grpNm");
		
		sb.append("    <table class='table table-bordered table-hover'><!--목록 테이블 시작-->").append(newLine);
		               
		sb.append("      <thead>").append(newLine);
		sb.append("        <tr>").append(newLine);
		
		for(int i = 0; i < itemList.size() ; i++){
			
			System.out.println("itemList.get("+i+") >>>>>>>> ["+itemList.get(i)+"]");
			
			sb.append("          <th class='text-center'>").append(itemList.get(i)).append("</th>").append(newLine);
			
		}

		sb.append("        </tr>").append(newLine);
		sb.append("      </thead>").append(newLine);
		               
		sb.append("      <tbody>").append(newLine);
		
		for(int i = 0; i < 9 ; i++){
			sb.append("    <tr>").append(newLine);
			
			for(int j = 0; j < itemList.size() ; j++){

				sb.append("      <td class=''>").append(newLine);

				if(i < exCnt){
				
					exTxt = (String)exList.get(j);
					arrEx = exTxt.split("\\|");
					sb.append(arrEx[i]);
				}
				
				sb.append("      </td>").append(newLine);
			}

			sb.append("    </tr>").append(newLine);
		}
		
		sb.append("    </table><!--목록 테이블 종료-->").append(newLine);
		
		sb.append("<div class='text-center'><!--목록 테이블 페이지 시작-->").append(newLine);
		sb.append("  <ul class='pagination pagination-sm'>").append(newLine);
		sb.append("    <li class='disabled'><a href='javascript://return false;'><span class='glyphicon glyphicon-chevron-left'></span></a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>1</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>2</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>3</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>4</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>5</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>6</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>7</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>8</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>9</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'>10</a></li>").append(newLine);
		sb.append("    <li><a href='javascript://return false;'><span class='glyphicon glyphicon-chevron-right'></span></a></li>").append(newLine);
		sb.append("  </ul>").append(newLine);
		sb.append("</div><!--목록 테이블 페이지 종료-->").append(newLine);
		

		return sb;
	}
	
	/**
	 * LIST TABLE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getListTable(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		ArrayList itemList 	= (ArrayList)map.get("pageItemList");
		ArrayList exList 	= (ArrayList)map.get("pageExList");
		String[] arrEx = null;
		
		String exTxt = (String)exList.get(0);
		arrEx = exTxt.split("\\|");
		int exCnt = arrEx.length;

		String grpNm = (String)map.get("grpNm");
		
		sb.append("<table class='table table-bordered table-hover'>").append(newLine);
		
		sb.append("  <thead>").append(newLine);
		sb.append("    <tr>").append(newLine);
		
		for(int i = 0; i < itemList.size() ; i++){
			
			sb.append("      <th class='text-center'>").append(itemList.get(i)).append("</th>").append(newLine);
			
		}

		sb.append("    </tr>").append(newLine);
		sb.append("  </thead>").append(newLine);
		
		sb.append("  <tbody>").append(newLine);
		
		for(int i = 0; i < exCnt ; i++){
			sb.append("    <tr>").append(newLine);
			
			for(int j = 0; j < itemList.size() ; j++){

				sb.append("      <td class=''>").append(newLine);

				exTxt = (String)exList.get(j);
				sb.append(exTxt);
				
				sb.append("      </td>").append(newLine);
			}

			sb.append("    </tr>").append(newLine);
		}
		
		sb.append("</table>").append(newLine);
				

		return sb;
	}
	
	/**
	 * 정정 TABLE 레이아웃
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getMdfyTable(HashMap<?,?> map) throws Exception{
		StringBuffer sb = new StringBuffer();

		String strItemTp 	= (String)map.get("itemTp");
		String strItemDtlTp = (String)map.get("itemDtlTp");
		ArrayList itemList 	= (ArrayList)map.get("pageItemList");
		ArrayList exList 	= (ArrayList)map.get("pageExList");
		HashMap paramMap = new HashMap();
		String[] arrEx = null;
		
		String exTxt = "";
		String inlineStyle = "";

		String grpNm = (String)map.get("grpNm");
		
		sb.append("<table class='table table-bordered table-hover'>").append(newLine);
		
		
		for(int i = 0; i < itemList.size() ; i++){

			exTxt = (String)exList.get(i);
			
			if(i%2 == 0){
				sb.append("    <tr>").append(newLine);
			}

			sb.append("      <th >").append(itemList.get(i)).append("</th>").append(newLine);
			if(i < 2){
				sb.append("      <td style='text-align: left;' class=''>").append(exTxt).append("</td>").append(newLine);
			}else {
				sb.append("      <td style='text-align: left;' class=''>").append(newLine);
				
				if(i == 0){
					inlineStyle = "form-inline ";
				}else{
					inlineStyle = "";
				}
				
				sb.append("      <div class='form-group ").append(inlineStyle).append("form-group-sm '>").append(newLine);
				
				if("DATE".equals(strItemTp)){ // DATE
					
					if("RANGE".equals(strItemDtlTp)){ // RANGE DATE
						
						sb.append(this.getDateRange(paramMap)).append(newLine);
						
					}else{
						
						sb.append(this.getDate(paramMap)).append(newLine);
						
					}
					
				}else if("SELECT".equals(strItemTp)){ // SELECT BOX
					
					sb.append(this.getSelect(paramMap)).append(newLine);
					
				}else if("TEXT".equals(strItemTp)){ // INPUT TEXT
					
					sb.append(this.getText(paramMap)).append(newLine);
					
				}else if("TEXTAREA".equals(strItemTp)){ // TEXTAREA
					
					sb.append(this.getTextarea(paramMap)).append(newLine);
					
				}else if("LABEL".equals(strItemTp)){ // LABEL
					
					sb.append(this.getLabel(paramMap)).append(newLine);
					
				}else if("RADIO".equals(strItemTp)){ // RADIO BUTTON
					
					sb.append(this.getRadio(paramMap)).append(newLine);
					
				}else if("BUTTON".equals(strItemTp)){ // BUTTON
					
					sb.append(this.getButton(paramMap)).append(newLine);
					
				}
				
				sb.append("      </div>").append(newLine);
				
				sb.append("      </td>").append(newLine);
			}

			if(i%2 == 0){
				sb.append("    </tr>").append(newLine);
			}
		}

		
		sb.append("  <tbody>").append(newLine);
		
//		for(int i = 0; i < exCnt ; i++){
//			sb.append("    <tr>").append(newLine);
//			
//			for(int j = 0; j < itemList.size() ; j++){
//
//				sb.append("      <td class=''>").append(newLine);
//
//				exTxt = (String)exList.get(j);
//				arrEx = exTxt.split("\\|");
//				sb.append(arrEx[i]);
//				
//				sb.append("      </td>").append(newLine);
//			}
//
//			sb.append("    </tr>").append(newLine);
//		}
		
		sb.append("</table>").append(newLine);
				

		return sb;
	}

}
