package com.pjt.survey.vo;

import java.math.BigDecimal;

public class ComVrfcBaseVo {

	private static final long serialVersionUID = 1L;

	private String docCd;//문서코드
	private String bfhnVrfcPrgmId;//사전검증프로그램ID
	private String bsopClsfNm;//업무분류코드명
	private String bsopClsfCd;//업무분류코드
	private String docNm;//문서명
	private String bfhnVrfcYn;//사전검증여부
	private String delYn;//삭제여부
	private String frstRegstId;//최초등록자ID
	private String frstRgsrDttmFrom;//최초등록일시시작
	private String frstRgsrDttmTo;//최초등록일시종료
	private String frstRgsrDttm;//최초등록일시
	private String lastChprId;//최종변경자ID
	private String lastChgDttmFrom;//최종변경일시시작
	private String lastChgDttmTo;//최종변경일시종료
	private String lastChgDttm;//최종변경일시

	public String getDocCd() {
		return docCd;
	}
	public void setDocCd(String docCd) {
		this.docCd = docCd;
	}
	public String getBfhnVrfcPrgmId() {
		return bfhnVrfcPrgmId;
	}
	public void setBfhnVrfcPrgmId(String bfhnVrfcPrgmId) {
		this.bfhnVrfcPrgmId = bfhnVrfcPrgmId;
	}
	public String getBsopClsfNm() {
		return bsopClsfNm;
	}
	public void setBsopClsfNm(String bsopClsfNm) {
		this.bsopClsfNm = bsopClsfNm;
	}
	public String getBsopClsfCd() {
		return bsopClsfCd;
	}
	public void setBsopClsfCd(String bsopClsfCd) {
		this.bsopClsfCd = bsopClsfCd;
	}
	public String getDocNm() {
		return docNm;
	}
	public void setDocNm(String docNm) {
		this.docNm = docNm;
	}
	public String getBfhnVrfcYn() {
		return bfhnVrfcYn;
	}
	public void setBfhnVrfcYn(String bfhnVrfcYn) {
		this.bfhnVrfcYn = bfhnVrfcYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getFrstRegstId() {
		return frstRegstId;
	}
	public void setFrstRegstId(String frstRegstId) {
		this.frstRegstId = frstRegstId;
	}
	public String getFrstRgsrDttmFrom() {
		return frstRgsrDttmFrom;
	}
	public void setFrstRgsrDttmFrom(String frstRgsrDttmFrom) {
		this.frstRgsrDttmFrom = frstRgsrDttmFrom;
	}
	public String getFrstRgsrDttmTo() {
		return frstRgsrDttmTo;
	}
	public void setFrstRgsrDttmTo(String frstRgsrDttmTo) {
		this.frstRgsrDttmTo = frstRgsrDttmTo;
	}
	public String getFrstRgsrDttm() {
		return frstRgsrDttm;
	}
	public void setFrstRgsrDttm(String frstRgsrDttm) {
		this.frstRgsrDttm = frstRgsrDttm;
	}
	public String getLastChprId() {
		return lastChprId;
	}
	public void setLastChprId(String lastChprId) {
		this.lastChprId = lastChprId;
	}
	public String getLastChgDttmFrom() {
		return lastChgDttmFrom;
	}
	public void setLastChgDttmFrom(String lastChgDttmFrom) {
		this.lastChgDttmFrom = lastChgDttmFrom;
	}
	public String getLastChgDttmTo() {
		return lastChgDttmTo;
	}
	public void setLastChgDttmTo(String lastChgDttmTo) {
		this.lastChgDttmTo = lastChgDttmTo;
	}
	public String getLastChgDttm() {
		return lastChgDttm;
	}
	public void setLastChgDttm(String lastChgDttm) {
		this.lastChgDttm = lastChgDttm;
	}
}

