package com.pjt.survey.vo;

import java.math.BigDecimal;

public class ComVrfcBaseDtlVo {

	private static final long serialVersionUID = 1L;

	private String docNm;//문서코드명
	private String docCd;//문서코드
	private String trgtTblNm;//대상테이블명
	private String trgtColNm;//대상컬럼명
	private String bfhnVrfcClusNm;//사전검증항목명
	private BigDecimal clusSortOrdr;//항목정렬순서
	private String comnCdVrfcYn;//공통코드검증여부
	private String comnNm;//공통코드 명
	private String comnCd;//공통코드 
	private String indvCdVrfcYn;//개별코드검증여부
	private String indvNm;//개별코드 명
	private String indvCd;//개별코드 
	private String indvCdTblNm;//개별코드테이블명
	private String indvCdColNm;//개별코드컬럼명
	private String indvCdAditQuryCn;//개별코드추가쿼리내용
	private String esntInpVrfcYn;//필수입력검증여부
	private String amtQtyVrfcYn;//금액수량검증여부
	private String dtVldtVrfcYn;//일자유효성검증여부
	private String dtVldtTpCn;//일자유효성구분내용 
	private String numVldtVrfcYn;//숫자유효성검증여부
	private String fxngRscoVrfcYn;//고정자릿수검증여부 
	private BigDecimal fxngRsco;//고정자릿수 
	private String rscoVrfcYn;//자릿수검증여부
	private BigDecimal alRsco;//전체자릿수
	private BigDecimal dcmlUndrRsco;//소수점미만자릿수
	private BigDecimal erkyOrdr;
	private String prtlVrfcYn;
	private String delYn;//삭제여부
	private String frstRegstId;//최초등록자ID
	private String frstRgsrDttmFrom;//최초등록일시시작
	private String frstRgsrDttmTo;//최초등록일시종료
	private String frstRgsrDttm;//최초등록일시
	private String lastChprId;//최종변경자ID
	private String lastChgDttmFrom;//최종변경일시시작
	private String lastChgDttmTo;//최종변경일시종료
	private String lastChgDttm;//최종변경일시

	public String getDocNm() {
		return docNm;
	}
	public void setDocNm(String docNm) {
		this.docNm = docNm;
	}
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
	public String getBfhnVrfcClusNm() {
		return bfhnVrfcClusNm;
	}
	public void setBfhnVrfcClusNm(String bfhnVrfcClusNm) {
		this.bfhnVrfcClusNm = bfhnVrfcClusNm;
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
	public String getComnNm() {
		return comnNm;
	}
	public void setComnNm(String comnNm) {
		this.comnNm = comnNm;
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
	public String getIndvNm() {
		return indvNm;
	}
	public void setIndvNm(String indvNm) {
		this.indvNm = indvNm;
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
	public String getFxngRscoVrfcYn() {
		return fxngRscoVrfcYn;
	}
	public void setFxngRscoVrfcYn(String fxngRscoVrfcYn) {
		this.fxngRscoVrfcYn = fxngRscoVrfcYn;
	}
	public BigDecimal getFxngRsco() {
		return fxngRsco;
	}
	public void setFxngRsco(BigDecimal fxngRsco) {
		this.fxngRsco = fxngRsco;
	}
	public String getRscoVrfcYn() {
		return rscoVrfcYn;
	}
	public void setRscoVrfcYn(String rscoVrfcYn) {
		this.rscoVrfcYn = rscoVrfcYn;
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
	
	private String koColName;
	private String enColName;
	private String frColName;

	/**
	 * @return the koColName
	 */
	public String getKoColName() {
		return koColName;
	}
	/**
	 * @param koColName the koColName to set
	 */
	public void setKoColName(String koColName) {
		this.koColName = koColName;
	}
	/**
	 * @return the enColName
	 */
	public String getEnColName() {
		return enColName;
	}
	/**
	 * @param enColName the enColName to set
	 */
	public void setEnColName(String enColName) {
		this.enColName = enColName;
	}
	/**
	 * @return the frColName
	 */
	public String getFrColName() {
		return frColName;
	}
	/**
	 * @param frColName the frColName to set
	 */
	public void setFrColName(String frColName) {
		this.frColName = frColName;
	}
}


