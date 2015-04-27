package hhr;

import org.w3c.dom.Element;

public abstract class HHRPlacemark {
	
	protected Element root;

	private String ID;

	private String country;
	
	private String sign_language;
	
	private String spoken_sign;
	
	private String checker_name;
	
	private String checker_email;
	
	private double cal_latitude;
	
	private double cal_longitude;
	
	private String start;
	
	private String online;
	
	private String country_name;
	
	private String sign_language_name;
	
	private String spoken_sign_name;
	
	private String checker_name_name;
	
	private String checker_email_name;
	
	private String cal_latitude_name;
	
	private String cal_longitude_name;
	
	private String start_name;
	
	private String online_name;
	
	private String style;
	
	private String ack;
	
	
	public HHRPlacemark() {
	}
	
	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	private StringBuilder template = new StringBuilder();
	
	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getSign_language_name() {
		return sign_language_name;
	}

	public void setSign_language_name(String sign_language_name) {
		this.sign_language_name = sign_language_name;
	}

	public String getSpoken_sign_name() {
		return spoken_sign_name;
	}

	public void setSpoken_sign_name(String spoken_sign_name) {
		this.spoken_sign_name = spoken_sign_name;
	}

	public String getChecker_name_name() {
		return checker_name_name;
	}

	public void setChecker_name_name(String checker_name_name) {
		this.checker_name_name = checker_name_name;
	}

	public String getChecker_email_name() {
		return checker_email_name;
	}

	public void setChecker_email_name(String checker_email_name) {
		this.checker_email_name = checker_email_name;
	}

	public String getCal_latitude_name() {
		return cal_latitude_name;
	}

	public void setCal_latitude_name(String cal_latitude_name) {
		this.cal_latitude_name = cal_latitude_name;
	}

	public String getCal_longitude_name() {
		return cal_longitude_name;
	}

	public void setCal_longitude_name(String cal_longitude_name) {
		this.cal_longitude_name = cal_longitude_name;
	}

	public String getStart_name() {
		return start_name;
	}

	public void setStart_name(String start_name) {
		this.start_name = start_name;
	}

	public String getOnline_name() {
		return online_name;
	}

	public void setOnline_name(String online_name) {
		this.online_name = online_name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSign_language() {
		return sign_language;
	}

	public void setSign_language(String sign_language) {
		this.sign_language = sign_language;
	}

	public String getSpoken_sign() {
		return spoken_sign;
	}

	public void setSpoken_sign(String spoken_sign) {
		this.spoken_sign = spoken_sign;
	}

	public String getChecker_name() {
		return checker_name;
	}

	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}

	public String getChecker_email() {
		return checker_email;
	}

	public void setChecker_email(String checker_email) {
		this.checker_email = checker_email;
	}

	public double getCal_latitude() {
		return cal_latitude;
	}

	public void setCal_latitude(double cal_latitude) {
		this.cal_latitude = cal_latitude;
	}

	public double getCal_longitude() {
		return cal_longitude;
	}

	public void setCal_longitude(double cal_longitude) {
		this.cal_longitude = cal_longitude;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getTemplate() {
		String countryPrefix = country.substring(0,country.indexOf("_"));
		String languagePrefix = sign_language.substring(0,sign_language.indexOf("_"));
		String protectedEmail = checker_email.replace("@", " (at) ");
		String truncatedTimeStamp = start.substring(0,start.indexOf("T"));
		
		if(!ack.equals("yes")){
			checker_name = "contact Bob Arcgill";
			protectedEmail = "BobAchgill (at) hotmail (.) com";
		}
		
		online = "http://www.hishandsreader.org/1" + country.replace("_", "-") + "-languages.html"; 
		template.append("<table border='1' style='border-collapse: collapse;' >" +
				"<tr>" +
					"<td><b> thankyou </b></td>" +
					"<td> </td></tr>" +
				"<tr>" +
					"<td><b>" + country_name + "</b></td>" +
					"<td><a href = 'http://www.ethnologue.com/country/" + countryPrefix + "'>" + country + "</a></td></tr>" +
				"<tr>" +
					"<td><b>" + sign_language_name + "</b></td>" +
					"<td><a href = 'http://www.ethnologue.com/language/" + languagePrefix + "'>" + sign_language + "</a></td></tr>" +
				"<tr>" +
					"<td><b>" + spoken_sign_name + "</b></td>" +
					"<td>" + spoken_sign + " </td></tr>" +
				"<tr>" +
					"<td><b>" + checker_name_name + "</b></td>" +
					"<td>" + checker_name + "</td></tr>" +
				"<tr>" +
					"<td><b>" + checker_email_name + "</b></td>" +
					"<td> " + protectedEmail + " </td></tr>" +
				"<tr>" +
					"<td><b>" + cal_latitude_name + "</b></td>" +
					"<td> " + cal_latitude + " </td></tr>" +
				"<tr>" +
					"<td><b>" + cal_longitude_name + "</b></td>" +
					"<td> " + cal_longitude + " </td></tr>" +
				"<tr>" +
					"<td><b>" + start_name + "</b></td>" +
					"<td> " + truncatedTimeStamp + " </td></tr>" +
				"<tr>" +
					"<td><b>" + "view online" + "</b></td>" +
					"<td> " + online + " </td></tr>" +
			"</table>");
		return template.toString();
	}
	
	public void setTemplate() {
		this.template = new StringBuilder();
		this.template.append(template);
	}
	
	public void setCountry(){
		String country = root.getElementsByTagName("country").item(0).getTextContent();
		setCountry_name("country");
		setCountry(country);
	}
	
	public void setLatitude(){
		String latitude = root.getElementsByTagName("cal_latitude").item(0).getTextContent();
		setCal_latitude_name("cal_latitude");
		setCal_latitude(Double.parseDouble(latitude));
	}
	
	public void setLongtitude(){
		String latitude = root.getElementsByTagName("cal_latitude").item(0).getTextContent();
		setCal_latitude_name("cal_latitude");
		setCal_latitude(Double.parseDouble(latitude));
	}
	
	public void setMeta(){
		Element meta = (Element) root.getElementsByTagName("meta").item(0);
		String ID = meta.getElementsByTagName("instanceID").item(0).getTextContent();
		setID(ID);
	}
	
	public void setLanguage(){
		String spokenLanguage = root.getElementsByTagName("spoken_language").item(0).getTextContent();
		setSign_language_name("sign_language");
		setSign_language(spokenLanguage);
	}


	public void setStart(){
		Element misc = (Element) root.getElementsByTagName("misc_start_group").item(0);
		String start = misc.getElementsByTagName("start").item(0).getTextContent();
		setStart(start);
		setStart_name("start");
	}
	
	public void build(){
		setCountry();
		setLatitude();
		setLongtitude();
		setMeta();
		setLanguage();
		setName();
		setEmail();
		setAck();
		setStart();
		setType();
		setStyle();
	}
	
	public abstract void setName();
	
	public abstract void setEmail();
	
	public abstract void setAck();

	public abstract void setType();

	public abstract void setStyle();
}
