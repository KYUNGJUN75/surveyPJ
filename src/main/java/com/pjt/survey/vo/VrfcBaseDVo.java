package com.pjt.survey.vo;

import java.math.BigDecimal;

public class VrfcBaseDVo {

	private static final long serialVersionUID = 1L;

	/** Code de document !== 문서코드 ==! */
	private String docCd;
	/** 대상테이블명 !== 대상테이블명 ==! */
	private String trgtTblNm;
	/** 대상컬럼명 !== 대상컬럼명 ==! */
	private String trgtColNm;
	/** 항목정렬순서(FR) !== 항목정렬순서 ==! */
	private BigDecimal clusSortOrdr;
	/** !==  ==! */
	private String comnCdVrfcYn;
	/** Table de référence !== 공통코드 ==! */
	private String comnCd;
	/** 개별코드검증여부(FR) !== 개별코드검증여부 ==! */
	private String indvCdVrfcYn;
	/** !==  ==! */
	private String indvCd;
	/** 개별코드테이블명 !== 개별코드테이블명 ==! */
	private String indvCdTblNm;
	/** 개별코드컬럼명(FR) !== 개별코드컬럼명 ==! */
	private String indvCdColNm;
	/** 개별코드추가쿼리(FR) !== 개별코드추가쿼리 ==! */
	private String indvCdAditQuryCn;
	/** 필수검증여부(FR) !== 필수검증여부 ==! */
	private String esntInpVrfcYn;
	/** !==  ==! */
	private String amtQtyVrfcYn;
	/** 일자유효성검증여부(FR) !== 일자유효성검증여부 ==! */
	private String dtVldtVrfcYn;
	/** 일자유효성검증형식(FR) !== 일자유효성검증형식 ==! */
	private String dtVldtTpCn;
	/** 숫자유효성검증(FR) !== 숫자유효성검증 ==! */
	private String numVldtVrfcYn;
	/** Nombre de caractères du nom de champ validé O/N !== 항목길이검증여부 ==! */
	private String clusLenVrfcYn;
	/** Nombre de caractères du nom du champ !== 항목길이 ==! */
	private BigDecimal clusLen;
	/** 숫자자리수검증여부(FR) !== 숫자자리수검증여부 ==! */
	private String numRscoVrfcYn;
	/** 숫자전체자릿수(FR) !== 숫자전체자릿수 ==! */
	private BigDecimal alRsco;
	/** 소수점미만자리수(FR) !== 소수점미만자리수 ==! */
	private BigDecimal dcmlUndrRsco;
	/** !==  ==! */
	private BigDecimal erkyOrdr;
	/** !==  ==! */
	private String prtlVrfcYn;
	/** Suppression ON !== 삭제여부 ==! */
	private String delYn;
	/** ID du premier enregistrant  !== 최초등록자ID ==! */
	private String frstRegstId;
	private String frstRgsrDttmFrom;//시작
	private String frstRgsrDttmTo;//종료
	/** Date et heure de premier enregistrement !== 최초등록일시 ==! */
	private String frstRgsrDttm;
	/** ID du modificateur final !== 최종변경자ID ==! */
	private String lastChprId;
	private String lastChgDttmFrom;//시작
	private String lastChgDttmTo;//종료
	/** Date et heure de modification finale !== 최종변경일시 ==! */
	private String lastChgDttm;

	public String getDocCd() {
		return docCd;
	}
	public void setDocCd(String docCd) {
		this.docCd = docCd;
	}
	public String getTrgtTblNm() {
		return trgtTblNm;
	}
	public void setTrgtTblNm(String trgtTblNm) {
		this.trgtTblNm = trgtTblNm;
	}
	public String getTrgtColNm() {
		return trgtColNm;
	}
	public void setTrgtColNm(String trgtColNm) {
		this.trgtColNm = trgtColNm;
	}
	public BigDecimal getClusSortOrdr() {
		return clusSortOrdr;
	}
	public void setClusSortOrdr(BigDecimal clusSortOrdr) {
		this.clusSortOrdr = clusSortOrdr;
	}
	public String getComnCdVrfcYn() {
		return comnCdVrfcYn;
	}
	public void setComnCdVrfcYn(String comnCdVrfcYn) {
		this.comnCdVrfcYn = comnCdVrfcYn;
	}
	public String getComnCd() {
		return comnCd;
	}
	public void setComnCd(String comnCd) {
		this.comnCd = comnCd;
	}
	public String getIndvCdVrfcYn() {
		return indvCdVrfcYn;
	}
	public void setIndvCdVrfcYn(String indvCdVrfcYn) {
		this.indvCdVrfcYn = indvCdVrfcYn;
	}
	public String getIndvCd() {
		return indvCd;
	}
	public void setIndvCd(String indvCd) {
		this.indvCd = indvCd;
	}
	public String getIndvCdTblNm() {
		return indvCdTblNm;
	}
	public void setIndvCdTblNm(String indvCdTblNm) {
		this.indvCdTblNm = indvCdTblNm;
	}
	public String getIndvCdColNm() {
		return indvCdColNm;
	}
	public void setIndvCdColNm(String indvCdColNm) {
		this.indvCdColNm = indvCdColNm;
	}
	public String getIndvCdAditQuryCn() {
		return indvCdAditQuryCn;
	}
	public void setIndvCdAditQuryCn(String indvCdAditQuryCn) {
		this.indvCdAditQuryCn = indvCdAditQuryCn;
	}
	public String getEsntInpVrfcYn() {
		return esntInpVrfcYn;
	}
	public void setEsntInpVrfcYn(String esntInpVrfcYn) {
		this.esntInpVrfcYn = esntInpVrfcYn;
	}
	public String getAmtQtyVrfcYn() {
		return amtQtyVrfcYn;
	}
	public void setAmtQtyVrfcYn(String amtQtyVrfcYn) {
		this.amtQtyVrfcYn = amtQtyVrfcYn;
	}
	public String getDtVldtVrfcYn() {
		return dtVldtVrfcYn;
	}
	public void setDtVldtVrfcYn(String dtVldtVrfcYn) {
		this.dtVldtVrfcYn = dtVldtVrfcYn;
	}
	public String getDtVldtTpCn() {
		return dtVldtTpCn;
	}
	public void setDtVldtTpCn(String dtVldtTpCn) {
		this.dtVldtTpCn = dtVldtTpCn;
	}
	public String getNumVldtVrfcYn() {
		return numVldtVrfcYn;
	}
	public void setNumVldtVrfcYn(String numVldtVrfcYn) {
		this.numVldtVrfcYn = numVldtVrfcYn;
	}
	public String getClusLenVrfcYn() {
		return clusLenVrfcYn;
	}
	public void setClusLenVrfcYn(String clusLenVrfcYn) {
		this.clusLenVrfcYn = clusLenVrfcYn;
	}
	public BigDecimal getClusLen() {
		return clusLen;
	}
	public void setClusLen(BigDecimal clusLen) {
		this.clusLen = clusLen;
	}
	public String getNumRscoVrfcYn() {
		return numRscoVrfcYn;
	}
	public void setNumRscoVrfcYn(String numRscoVrfcYn) {
		this.numRscoVrfcYn = numRscoVrfcYn;
	}
	public BigDecimal getAlRsco() {
		return alRsco;
	}
	public void setAlRsco(BigDecimal alRsco) {
		this.alRsco = alRsco;
	}
	public BigDecimal getDcmlUndrRsco() {
		return dcmlUndrRsco;
	}
	public void setDcmlUndrRsco(BigDecimal dcmlUndrRsco) {
		this.dcmlUndrRsco = dcmlUndrRsco;
	}
	public BigDecimal getErkyOrdr() {
		return erkyOrdr;
	}
	public void setErkyOrdr(BigDecimal erkyOrdr) {
		this.erkyOrdr = erkyOrdr;
	}
	public String getPrtlVrfcYn() {
		return prtlVrfcYn;
	}
	public void setPrtlVrfcYn(String prtlVrfcYn) {
		this.prtlVrfcYn = prtlVrfcYn;
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
