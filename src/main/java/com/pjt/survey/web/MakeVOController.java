package com.pjt.survey.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alexlee.comment.CommentReplace;
import com.alexlee.comment.LabelSearcher;
import com.pjt.survey.service.ExcelLoadService;
import com.pjt.survey.service.GenerateHtmlService;
import com.pjt.survey.service.MakeVOService;
import com.pjt.survey.service.SearchLabelSvc;
import com.pjt.survey.vo.comLabelVo;


@Controller
public class MakeVOController {

	@Autowired
	private MakeVOService makeVOService;

	@Autowired
	private SearchLabelSvc searchLabelSvc;

	@Autowired
	private ExcelLoadService excelLoadService;

	@Autowired
	private GenerateHtmlService generateHtmlService;

	private CommentReplace commentReplacer = new CommentReplace();
	private LabelSearcher labelSearcher = new LabelSearcher();

	private String labelSearcherDicPath  = null;

	@RequestMapping(value="/")
	public String rootContext(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {
		return "redirect:/index";
	}

	/***************************************************************************
	* Method Name : makeVO
	* Desc        : VO 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/index")
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {
		System.out.println(">>>>>>>> ");

		model.addAttribute("result", "");

        return "index";
	}

	/***************************************************************************
	* Method Name : makeVO
	* Desc        : VO 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/makeVO")
	public String makeVO(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {

		model.addAttribute("result", "");

        return "makeVO";
	}


	@RequestMapping(value="/selectMakeVO")
	public String selectSqlMap(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		HashMap<String, String> retMap = null;
		String voStr = "";
		String tblId 			= (String)paramMap.get("tblId");
		String pkgPath 			= (String)paramMap.get("pkgPath");
		String addItem 			= (String)paramMap.get("addItem");
		String arrayItem 		= (String)paramMap.get("arrayItem");
		String checkTrgt 		= (String)paramMap.get("checkTrgt");

		System.out.println("controll tblId >>> ["+tblId+"]");

		retMap = makeVOService.selectMakeVO(paramMap);

		System.out.println("controll voStr >>> ["+voStr+"]");

		model.addAttribute("tblId", tblId);
		model.addAttribute("pkgPath", pkgPath);
		model.addAttribute("addItem", addItem);
		model.addAttribute("arrayItem", arrayItem);
		model.addAttribute("checkTrgt", checkTrgt);
		model.addAttribute("voStr", retMap.get("voStr"));
		model.addAttribute("svVoStr", retMap.get("svVoStr"));


		return "makeVO";
	}

	/***************************************************************************
	* Method Name : makeSQL
	* Desc        : SQL 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/makeSQL")
	public String makeSQL(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {

		model.addAttribute("result", "");

        return "makeSQL";
	}

	@RequestMapping(value="/selectMakeSQL")
	public String selectMakeSQL(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		HashMap<String, String> retMap = null;
		String voStr = "";

		String tblId 			= (String)paramMap.get("tblId");
		String inparam 			= (String)paramMap.get("inparam");
		String outparam 		= (String)paramMap.get("outparam");
		String gubun 			= (String)paramMap.get("gubun");
		String checkTrgt 			= (String)paramMap.get("checkTrgt");

		System.out.println("controll tblId >>> ["+tblId+"]");

		retMap = makeVOService.selectMakeSQL(paramMap);

		System.out.println("controll voStr >>> ["+voStr+"]");

		model.addAttribute("tblId", tblId);
		model.addAttribute("inparam", inparam);
		model.addAttribute("outparam", outparam);
		model.addAttribute("gubun", gubun);
		model.addAttribute("checkTrgt", checkTrgt);
		model.addAttribute("sqlStr", retMap.get("sqlStr"));


		return "makeSQL";
	}

	/***************************************************************************
	* Method Name : saveComVrfc
	* Desc        : 공통검증 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/saveComVrfc")
	public String saveComVrfc(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		HashMap<String, String> retMap = null;
		String tblId 			= (String)paramMap.get("tblId");
		String bfhnVrfcPrgmId 	= (String)paramMap.get("bfhnVrfcPrgmId");
		String docCd 			= (String)paramMap.get("docCd");
		String docNm 			= (String)paramMap.get("docNm");
		String checkTrgt 			= (String)paramMap.get("checkTrgt");
		String vrfcTblId 			= (String)paramMap.get("vrfcTblId");

		if(tblId != null && !"".equals(tblId)){
			retMap = makeVOService.saveComVrfc(paramMap);
			model.addAttribute("insCnt", retMap.get("insCnt"));
		}

		model.addAttribute("tblId", tblId);
		model.addAttribute("bfhnVrfcPrgmId", bfhnVrfcPrgmId);
		model.addAttribute("docCd", docCd);
		model.addAttribute("docNm", docNm);
		model.addAttribute("checkTrgt", checkTrgt);
		model.addAttribute("vrfcTblId", vrfcTblId);

		return "saveComVrfc";
	}

	/***************************************************************************
	* Method Name : selectTblMap
	* Desc        : vo 매핑
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/selectTblMap")
	public String selectTblMap(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		String tobeSql = "";
		String fromTblId = (String)paramMap.get("fromTblId");
		String toTblId = (String)paramMap.get("toTblId");
		String fromVo = (String)paramMap.get("fromVo");
		String toVo = (String)paramMap.get("toVo");

		tobeSql = makeVOService.selectTblMap(paramMap);

		model.addAttribute("fromTblId", fromTblId);
		model.addAttribute("toTblId", toTblId);
		model.addAttribute("fromVo", fromVo);
		model.addAttribute("toVo", toVo);
		model.addAttribute("tobeSql", tobeSql);

		return "tblmap";
	}

	/***************************************************************************
	 * Method Name : getDic
	 * Desc        : 사전조회
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/getDic")
	public String getDic(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		String replacedText = "";

		String dicPath 	= (String)paramMap.get("dicPath");
		String srcText = (String)paramMap.get("srcTxt");
		if (srcText != null && dicPath != null) {

			commentReplacer.setMapPath(dicPath);
			commentReplacer.loadProperties();

			replacedText = commentReplacer.getWordFromDic (srcText);
		}

		model.addAttribute("srcTxt", srcText);
		model.addAttribute("replacedText", replacedText != null ? replacedText : srcText);

		System.out.println(replacedText);

		return "getDic";
	}

	/***************************************************************************
	 * Method Name : getLabel
	 * Desc        : 라벨조회
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/getLabel")
	public String getLabel(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		String[] labels = null;
		String replacedText = "";

		String dicPath 	= (String)paramMap.get("dicPath");
		String srcText = (String)paramMap.get("srcTxt");
		if (srcText != null && dicPath != null) {

			labelSearcher.setMapPath(dicPath);
			if (labelSearcherDicPath == null || labelSearcherDicPath != dicPath) {
				labelSearcher.loadProperties();
			}
			labels = labelSearcher.getLabelFromDic(srcText);

			replacedText = labels != null ? LabelSearcher.toLabelLogString(labels) : "조회내용없음";
		}

		model.addAttribute("srcTxt", srcText);
		model.addAttribute("replacedText", replacedText != null ? replacedText : srcText);

		System.out.println(replacedText);

		return "getLabel";
	}



	/***************************************************************************
	 * Method Name : transComment
	 * Desc        : 다국어 주석
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/transComment")
	public String transComment2(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		String replacedText = "";

		String dicPath 	= (String)paramMap.get("dicPath");
		String srcText = (String)paramMap.get("srcTxt");
		if (srcText != null && dicPath != null) {
//			dicPath 	= "C:/Temp/dic.xlsx";

			commentReplacer.setMapPath(dicPath);
			commentReplacer.loadProperties();

			replacedText = commentReplacer.replaceCommentString (srcText);
		}

		model.addAttribute("srcTxt", srcText);
		model.addAttribute("replacedText", replacedText);

		System.out.println(replacedText);

		return "transComment";
	}

	/***************************************************************************
	 * Method Name : transCommentDir
	 * Desc        : 다국어 주석 (File 2 File, Dir 2 Dir)
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/transCommentDir")
	public String transCommentDir(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		return "transCommentDir";
	}

	/***************************************************************************
	 * Method Name : transCommentDirData
	 * Desc        : 다국어 주석 (File 2 File, Dir 2 Dir)
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/transCommentDirData")
	public String transCommentDirData(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		String dicPath 	= (String)paramMap.get("dicPath");
		String srcDir 	= (String)paramMap.get("srcDir");
		String tgDir 	= (String)paramMap.get("tgDir");
		String reportText = "";

		String fileExt 	= null;
		Object[] reports = null;

		if (dicPath != null && srcDir != null) {
			if (tgDir == null) {
				tgDir = new File((new File(srcDir)).getParentFile(), (new File(srcDir)).getName() + "_translated").getAbsolutePath();
			}
			reports = commentReplacer.batch(dicPath, srcDir, tgDir, fileExt);

			reportText = this.makeReport(reports);
		}

		model.addAttribute("dicPath", dicPath);
		model.addAttribute("srcDir", srcDir);
		model.addAttribute("tgDir", tgDir);

		model.addAttribute("reportText", reportText);

		System.out.println(reportText);

		return "transCommentDir";
	}

	private static final String PRE_LINE = "* ";
	private static final String POST_LINE = "\r\n";

	private String makeReport(Object[] reports) {
		StringBuffer sbReport = new StringBuffer();

		sbReport.append(PRE_LINE).append("전체 대상 파일 수 : " + reports[CommentReplace.TOTAL]).append(POST_LINE);
		sbReport.append(PRE_LINE).append("변경 파일 수 : " + reports[CommentReplace.CHANGED]).append(POST_LINE);

		@SuppressWarnings("unchecked")
		ArrayList<String> alList = (ArrayList<String>)reports[CommentReplace.FILE_NM];

		sbReport.append(PRE_LINE).append("변경 파일 목록 : ").append(POST_LINE);
		for (int inx = 0; inx < alList.size(); inx ++) {
			sbReport.append("  [" + (inx + 1)+ "] " + alList.get(inx)).append(POST_LINE);
		}

		return sbReport.toString();
	}

	/***************************************************************************
	 * Method Name : db기반 다국어 라벨 조회 및 주석
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/dbToLabelCreate")
	public String dbToLabelCreate(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		return "dbToLabelCreate";
	}

	/***************************************************************************
	 * Method Name : db기반 다국어 라벨 조회 및 주석
	 * @param
	 * @returns
	 ***************************************************************************/
	@RequestMapping(value="/dbToLabelCreateData")
	public String dbToLabelCreateData(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {
		String lblNm 			= (String)paramMap.get("lblNm");
		String langCd 			= (String)paramMap.get("langCd");

		 System.out.println( "lblNm --->" + lblNm );

		comLabelVo paramVo = new comLabelVo();

		paramVo.setLblNm(lblNm);
		paramVo.setLangCd(langCd);

		List<comLabelVo> resultList = searchLabelSvc.selectLabelInfo(paramVo);

		System.out.println("resultList size ==> "+resultList.size()+"::: + ");

		model.addAttribute("resultList",resultList);
		return "jsonView";
	}

	/***************************************************************************
	* Method Name : selectMakeSelectInsert
	* Desc        : 외부 select 내부 insert sql make
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/selectMakeSelectInsert")
	public String selectMakeSelectInsert(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		String tobeSql = "";
		String fromTblId = (String)paramMap.get("fromTblId");
		String toTblId = (String)paramMap.get("toTblId");

		tobeSql = makeVOService.selectMakeSelectInsert(paramMap);

		model.addAttribute("fromTblId", fromTblId);
		model.addAttribute("toTblId", toTblId);
		model.addAttribute("tobeSql", tobeSql);

		return "makeSelectInsert";
	}

	/***************************************************************************
	* Method Name : excelDown
	* Desc        : 공통검증 excel down
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/excelDown")
	public String excelDown(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		String docCd = (String)paramMap.get("docCd");

		excelLoadService.excelDown(paramMap);

		model.addAttribute("docCd", docCd);

		return "saveComVrfc";
	}

	/***************************************************************************
	* Method Name : excelUpload
	* Desc        : 공통검증 excel upload
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/excelUpload")
	public String excelUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		String docCd = (String)paramMap.get("docCd");

		excelLoadService.excelUpload(paramMap);

		model.addAttribute("docCd", docCd);

		return "saveComVrfc";
	}

	/***************************************************************************
	* Method Name : generateHtml
	* Desc        : VO 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/generateHtml")
	public String generateHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {

		model.addAttribute("result", "");

        return "generateHtml";
	}

	/***************************************************************************
	* Method Name : runSenerateHtml
	* Desc        : html 생성
	* @param
	* @returns
	***************************************************************************/
	@RequestMapping(value="/runGenerateHtml")
	public String runSenerateHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<?, ?> paramMap) throws Exception {

		System.out.println("enter>>>>>>>>>>>>>>>>>");
		String filePath = (String)paramMap.get("filePath");

		generateHtmlService.runGenerateHtml(paramMap);

		model.addAttribute("filePath", filePath);

		return "generateHtml";
	}

	/***************************************************************************
	* Method Name : makeVO
	* Desc        : VO 생성
	* @param
	* @returns
	***************************************************************************/
//	@RequestMapping(value="/runSQL")
//	public String runSQL(HttpServletRequest request, HttpServletResponse response, ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {
//		HashMap<String, String> retMap = null;
//		String voStr = "";
//		String sqlStr 			= (String)paramMap.get("sqlStr");
//
//		System.out.println("controll sqlStr >>> ["+sqlStr+"]");
//
//		retMap = makeVOService.runSQL(paramMap);
//
//		model.addAttribute("sqlStr", sqlStr);
//		model.addAttribute("sqlResult", retMap.get("sqlResult"));
//
//
//		return "sqlResult";
//	}

}
