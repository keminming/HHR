package hhr;

import org.w3c.dom.Element;

public class HHRForm5Placemark extends HHRPlacemark{

	@Override
	public void setName() {
		// TODO Auto-generated method stub
		Element checkerGroup = (Element) root.getElementsByTagName("about_checker_group").item(0);
		String checkerName = checkerGroup.getElementsByTagName("checkername").item(0).getTextContent();
		setChecker_name_name("checker_name");
		setChecker_name(checkerName);		
	}

	@Override
	public void setEmail() {
		// TODO Auto-generated method stub
		Element checkerGroup = (Element) root.getElementsByTagName("about_checker_group").item(0);
		String checkerEmail = checkerGroup.getElementsByTagName("checkeremail").item(0).getTextContent();
		setChecker_email_name("checker_email");
		setChecker_email(checkerEmail);
	}

	@Override
	public void setAck() {
		// TODO Auto-generated method stub
		Element checkerGroup = (Element) root.getElementsByTagName("about_checker_group").item(0);
		String ack = checkerGroup.getElementsByTagName("ack_checker").item(0).getTextContent();
		setAck(ack);
		
	}

	@Override
	public void setType() {
		// TODO Auto-generated method stub
		setSpoken_sign("sign");
		setSpoken_sign_name("spoken/sign?");
	}

	@Override
	public void setStyle() {
		// TODO Auto-generated method stub
		setStyle("#odk_style1");
	}
}
