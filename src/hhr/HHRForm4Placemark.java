package hhr;

import org.w3c.dom.Element;

public class HHRForm4Placemark extends HHRPlacemark{
	
	@Override
	public void setName() {
		// TODO Auto-generated method stub
		Element transGroup = (Element) root.getElementsByTagName("about_trans_group").item(0);
		String transName = transGroup.getElementsByTagName("translatorname").item(0).getTextContent();
		setChecker_name_name("translater_name");
		setChecker_name(transName);
	}

	@Override
	public void setEmail() {
		// TODO Auto-generated method stub
		Element transGroup = (Element) root.getElementsByTagName("about_trans_group").item(0);
		String transEmail = transGroup.getElementsByTagName("translatoremail").item(0).getTextContent();
		setChecker_email_name("translater_email");
		setChecker_email(transEmail);
	}

	@Override
	public void setAck() {
		// TODO Auto-generated method stub
		Element transGroup = (Element) root.getElementsByTagName("about_trans_group").item(0);
		String ack = transGroup.getElementsByTagName("ack_trans").item(0).getTextContent();
		setAck(ack);
	}

	@Override
	public void setType() {
		// TODO Auto-generated method stub
		setSpoken_sign("spoken");
		setSpoken_sign_name("spoken/sign?");
	}

	@Override
	public void setStyle() {
		// TODO Auto-generated method stub
		setStyle("#odk_style");
	}
}
