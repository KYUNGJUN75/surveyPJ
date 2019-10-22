package com.pjt.survey.vo;

public class SqlMapVO {

	private String asisTblId = "";
	private String asisTblNm = "";
	private String asisColId = "";
	private String asisColNm = "";
	private String tobeTblId = "";
	private String tobeTblNm = "";
	private String tobeColId = "";
	private String tobeColNm = "";
	private String ditc = "";
	private String tableName      = "";
	private String columnName     = "";
	private String dataType       = "";
	private int dataLength     = 0;
	private int dataPrecision  = 0;
	private int dataScale      = 0;
	private String nullable       = "";
	private int columnId       = 0;
	private String comments       = "";
	private String tblId = "";
	private String contType = "";
	private String koCmmt = "";
	private String frCmmt = "";
	private String tabCmmt = "";
	
	
	/**
	 * asisTblId attribute를 리턴한다.
	 * @return asisTblId String
	 */
	public String getAsisTblId() {
		return asisTblId;
	}
	/**
	 * asisTblId attribute 값을 설정한다.
	 * @param asisTblId String
	 */
	public void setAsisTblId(String asisTblId) {
		this.asisTblId = asisTblId;
	}
	/**
	 * asisTblNm attribute를 리턴한다.
	 * @return asisTblNm String
	 */
	public String getAsisTblNm() {
		return asisTblNm;
	}
	/**
	 * asisTblNm attribute 값을 설정한다.
	 * @param asisTblNm String
	 */
	public void setAsisTblNm(String asisTblNm) {
		this.asisTblNm = asisTblNm;
	}
	/**
	 * asisColId attribute를 리턴한다.
	 * @return asisColId String
	 */
	public String getAsisColId() {
		return asisColId;
	}
	/**
	 * asisColId attribute 값을 설정한다.
	 * @param asisColId String
	 */
	public void setAsisColId(String asisColId) {
		this.asisColId = asisColId;
	}
	/**
	 * tobeTblId attribute를 리턴한다.
	 * @return tobeTblId String
	 */
	public String getTobeTblId() {
		return tobeTblId;
	}
	/**
	 * tobeTblId attribute 값을 설정한다.
	 * @param tobeTblId String
	 */
	public void setTobeTblId(String tobeTblId) {
		this.tobeTblId = tobeTblId;
	}
	/**
	 * tobeTblNm attribute를 리턴한다.
	 * @return tobeTblNm String
	 */
	public String getTobeTblNm() {
		return tobeTblNm;
	}
	/**
	 * tobeTblNm attribute 값을 설정한다.
	 * @param tobeTblNm String
	 */
	public void setTobeTblNm(String tobeTblNm) {
		this.tobeTblNm = tobeTblNm;
	}
	/**
	 * tobeColId attribute를 리턴한다.
	 * @return tobeColId String
	 */
	public String getTobeColId() {
		return tobeColId;
	}
	/**
	 * tobeColId attribute 값을 설정한다.
	 * @param tobeColId String
	 */
	public void setTobeColId(String tobeColId) {
		this.tobeColId = tobeColId;
	}
	/**
	 * tobeColNm attribute를 리턴한다.
	 * @return tobeColNm String
	 */
	public String getTobeColNm() {
		return tobeColNm;
	}
	/**
	 * tobeColNm attribute 값을 설정한다.
	 * @param tobeColNm String
	 */
	public void setTobeColNm(String tobeColNm) {
		this.tobeColNm = tobeColNm;
	}
	/**
	 * asisColNm attribute를 리턴한다.
	 * @return asisColNm String
	 */
	public String getAsisColNm() {
		return asisColNm;
	}
	/**
	 * asisColNm attribute 값을 설정한다.
	 * @param asisColNm String
	 */
	public void setAsisColNm(String asisColNm) {
		this.asisColNm = asisColNm;
	}
	/**
	 * ditc attribute를 리턴한다.
	 * @return ditc String
	 */
	public String getDitc() {
		return ditc;
	}
	/**
	 * ditc attribute 값을 설정한다.
	 * @param ditc String
	 */
	public void setDitc(String ditc) {
		this.ditc = ditc;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public int getDataPrecision() {
		return dataPrecision;
	}
	public void setDataPrecision(int dataPrecision) {
		this.dataPrecision = dataPrecision;
	}
	public int getDataScale() {
		return dataScale;
	}
	public void setDataScale(int dataScale) {
		this.dataScale = dataScale;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public String getTblId() {
		return tblId;
	}
	public void setTblId(String tblId) {
		this.tblId = tblId;
	}
	public String getContType() {
		return contType;
	}
	public void setContType(String contType) {
		this.contType = contType;
	}
	public String getKoCmmt() {
		return koCmmt;
	}
	public void setKoCmmt(String koCmmt) {
		this.koCmmt = koCmmt;
	}
	public String getFrCmmt() {
		return frCmmt;
	}
	public void setFrCmmt(String frCmmt) {
		this.frCmmt = frCmmt;
	}
	
	private String enCmmt;


	/**
	 * @return the enCmmt
	 */
	public String getEnCmmt() {
		return enCmmt;
	}
	/**
	 * @param enCmmt the enCmmt to set
	 */
	public void setEnCmmt(String enCmmt) {
		this.enCmmt = enCmmt;
	}
	
	
	private String vrfcTblId;


	/**
	 * @return the vrfcTblId
	 */
	public String getVrfcTblId() {
		return vrfcTblId;
	}
	/**
	 * @param vrfcTblId the vrfcTblId to set
	 */
	public void setVrfcTblId(String vrfcTblId) {
		this.vrfcTblId = vrfcTblId;
	}
	public String getTabCmmt() {
		return tabCmmt;
	}
	public void setTabCmmt(String tabCmmt) {
		this.tabCmmt = tabCmmt;
	}
}
