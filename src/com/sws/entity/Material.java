package com.sws.entity;

/**
 * 物料实体类
 * @author Ben
 *
 */
public class Material {

	private int id;
	private int materialCode;  //物料代码
	private String materialName;  //物料名称
	private String materialAlias; //物料别名
	private String materialSpecifition; //物料规格
	private String materialDimension;   //物料寸别
	private float dimension;       //尺寸--字段已废弃
	private float outerDiameter;  //外径
	private float wallThickness;  //壁厚
	private float length;         //长度
	private float dxxs;           //镀锌系数
	private float zz;             //支重
	private int zs;             //支数
	private int js;             //件数
	private int szs;            //散支数
	private int zzs;            //总支数
	private float pieceWeight;    //件重
	private float weight;         //物料重量
	private float minWeight;      //最小重量
	private float maxWeight;      //最大重量
	private String produceDate;   //生产日期
	private String isMatch;       //是否匹配
	private String modifyTime;    //修改时间
	private String nozzleState;   //管口状态
	private String materialQuality;  //材质
	private String measureWay;   //计量方式
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(int materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialAlias() {
		return materialAlias;
	}
	public void setMaterialAlias(String materialAlias) {
		this.materialAlias = materialAlias;
	}
	public String getMaterialSpecifition() {
		return materialSpecifition;
	}
	public void setMaterialSpecifition(String materialSpecifition) {
		this.materialSpecifition = materialSpecifition;
	}
	public float getOuterDiameter() {
		return outerDiameter;
	}
	public void setOuterDiameter(float outerDiameter) {
		this.outerDiameter = outerDiameter;
	}
	public float getWallThickness() {
		return wallThickness;
	}
	public void setWallThickness(float wallThickness) {
		this.wallThickness = wallThickness;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getDxxs() {
		return dxxs;
	}
	public void setDxxs(float dxxs) {
		this.dxxs = dxxs;
	}
	public float getZz() {
		return zz;
	}
	public void setZz(float zz) {
		this.zz = zz;
	}
	public int getZs() {
		return zs;
	}
	public void setZs(int zs) {
		this.zs = zs;
	}
	public float getPieceWeight() {
		return pieceWeight;
	}
	public void setPieceWeight(float pieceWeight) {
		this.pieceWeight = pieceWeight;
	}
	public float getMinWeight() {
		return minWeight;
	}
	public void setMinWeight(float minWeight) {
		this.minWeight = minWeight;
	}
	public float getMaxWeight() {
		return maxWeight;
	}
	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getIsMatch() {
		return isMatch;
	}
	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public float getDimension() {
		return dimension;
	}
	public void setDimension(float dimension) {
		this.dimension = dimension;
	}
	public int getJs() {
		return js;
	}
	public void setJs(int js) {
		this.js = js;
	}
	public int getSzs() {
		return szs;
	}
	public void setSzs(int szs) {
		this.szs = szs;
	}
	public int getZzs() {
		return zzs;
	}
	public void setZzs(int zzs) {
		this.zzs = zzs;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public String getNozzleState() {
		return nozzleState;
	}
	public void setNozzleState(String nozzleState) {
		this.nozzleState = nozzleState;
	}
	public String getMaterialQuality() {
		return materialQuality;
	}
	public void setMaterialQuality(String materialQuality) {
		this.materialQuality = materialQuality;
	}
	public String getMeasureWay() {
		return measureWay;
	}
	public void setMeasureWay(String measureWay) {
		this.measureWay = measureWay;
	}
	public String getMaterialDimension() {
		return materialDimension;
	}
	public void setMaterialDimension(String materialDimension) {
		this.materialDimension = materialDimension;
	}
	@Override
	public String toString() {
		return "Material [id=" + id + ", materialCode=" + materialCode + ", materialName=" + materialName
				+ ", materialAlias=" + materialAlias + ", materialSpecifition=" + materialSpecifition
				+ ", materialDimension=" + materialDimension + ", dimension=" + dimension + ", outerDiameter="
				+ outerDiameter + ", wallThickness=" + wallThickness + ", length=" + length + ", dxxs=" + dxxs + ", zz="
				+ zz + ", zs=" + zs + ", js=" + js + ", szs=" + szs + ", zzs=" + zzs + ", pieceWeight=" + pieceWeight
				+ ", weight=" + weight + ", minWeight=" + minWeight + ", maxWeight=" + maxWeight + ", produceDate="
				+ produceDate + ", isMatch=" + isMatch + ", modifyTime=" + modifyTime + ", nozzleState=" + nozzleState
				+ ", materialQuality=" + materialQuality + ", measureWay=" + measureWay + "]";
	}
	
}
