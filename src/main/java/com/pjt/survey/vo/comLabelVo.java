package com.pjt.survey.vo;

import java.io.Serializable;

/** 
 * @Description : 공통 라벨을 조회하기 위한 Vo class
 * @author 송상구
 * @since 2018. 4. 11. 오전 10:42:38
 * @version 1.0 
 * @see 
 * 
 * <pre> 
 * == 개정이력(Modification Information) == 
 * 
 * 	  수정일 			수정자 				수정내용 
 * -----------	-------------	--------------------------- 
 * 2018. 4. 11.		송상구				최초생성 
 * 
 * </pre> 
 */
public class comLabelVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7194451142266050194L;
	
	/**
	 * 라벨ID
	 */
	private String lblId;
	
	/**
	 * 라벨구분코드
	 */
	private String lblTpcd;
	
	/**
	 * 언어코드
	 */
	private String langCd;
	
	/**
	 * 라벨명
	 */
	private String lblNm;
	
	private String prgComment;
	
	/**
	 * @return the prgComment
	 */
	public String getPrgComment() {
		this.prgComment = "/** ";
		
		this.prgComment = this.prgComment + ((this.frLblNm != null)?this.frLblNm:"") + " !=="+((this.lblNm != null)?this.lblNm:"") + "==!";
		
		this.prgComment = this.prgComment + " */";
		return prgComment;
	}


	private String htmlComment;
	
	/**
	 * @return the htmlComment
	 */
	public String getHtmlComment() {
		
		this.htmlComment = "<!-- ";
		
		this.htmlComment = this.htmlComment + ((this.frLblNm != null)?this.frLblNm:"") + " !=="+((this.lblNm != null)?this.lblNm:"") + "==!";
		
		this.htmlComment = this.htmlComment + " -->";
		
		return htmlComment;
	}


	/**
	 * 업무구분
	 */
	private String bsopClsfCd;
	
	/**
	 * 불어기준 라벨명
	 */
	private String frLblNm;
	
	/**
	 * 라벨ID
	 * @return the lblId
	 */
	public String getLblId() {
		return lblId;
	}

	/**
	 * 라벨ID
	 * @param lblId the lblId to set
	 */
	public void setLblId(String lblId) {
		this.lblId = lblId;
	}

	/**
	 * 라벨구분코드
	 * @return the lblTpcd
	 */
	public String getLblTpcd() {
		return lblTpcd;
	}

	/**
	 * 라벨구분코드
	 * @param lblTpcd the lblTpcd to set
	 */
	public void setLblTpcd(String lblTpcd) {
		this.lblTpcd = lblTpcd;
	}

	/**
	 * 언어코드
	 * @return the langCd
	 */
	public String getLangCd() {
		return langCd;
	}

	/**
	 * 언어코드
	 * @param langCd the langCd to set
	 */
	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	/**
	 * 라벨명
	 * @return the lblNm
	 */
	public String getLblNm() {
		return lblNm;
	}

	/**
	 * 라벨명
	 * @param lblNm the lblNm to set
	 */
	public void setLblNm(String lblNm) {
		this.lblNm = lblNm;
	}
	
	/**
	 * @return the bsopClsfCd
	 */
	public String getBsopClsfCd() {
		return bsopClsfCd;
	}

	/**
	 * @param bsopClsfCd the bsopClsfCd to set
	 */
	public void setBsopClsfCd(String bsopClsfCd) {
		this.bsopClsfCd = bsopClsfCd;
	}
	
	/**
	 * 불어기준 라벨명
	 * @return the frLblNm
	 */
	public String getFrLblNm() {
		return frLblNm;
	}

	/**
	 * 불어기준 라벨명
	 * @param frLblNm the frLblNm to set
	 */
	public void setFrLblNm(String frLblNm) {
		this.frLblNm = frLblNm;
	}
}
