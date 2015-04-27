package tool;

import hhr.HHRPlacemark;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import de.micromata.opengis.kml.v_2_2_0.Data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.ExtendedData;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;

public class KMLGenerator {

	private Kml kml;
	
	private Document document;
	
	private static KMLGenerator instance = new KMLGenerator();
	
	public static void setInstance(KMLGenerator instance) {
		KMLGenerator.instance = instance;
	}

	public static KMLGenerator getInstance(){
		return instance;
	}
	
	private KMLGenerator(){};
	
	@SuppressWarnings("unchecked")
	public void load(String kmlFileStr){
		kml = Kml.unmarshal(new File(kmlFileStr));
		document = (Document) kml.getFeature();
		List<Feature> features = (List<Feature>) document.getFeature();
		
		for(int i=features.size()-1;i>=0;i--){
			Feature f = features.get(i);
			if(f.getClass().equals(Placemark.class))
				features.remove(i);
		}
	}
		
	@SuppressWarnings("deprecation")
	public synchronized void insert(HHRPlacemark placemark){	
		ExtendedData extData = KmlFactory.createExtendedData();
		Data title = KmlFactory.createData(placemark.getSign_language());
		title.setName("__title");
		extData.addToData(title);
		Data data = KmlFactory.createData(placemark.getTemplate());
		data.setName("__data");
		extData.addToData(data);
		
		document.createAndAddPlacemark()
		.withExtendedData(extData)
		.withId(placemark.getID())
		.withStyleUrl(placemark.getStyle())
		.withSnippet(KmlFactory.createSnippet().withMaxLines(0))
		.withName(placemark.getCountry())
		.createAndSetPoint().addToCoordinates(placemark.getCal_longitude(),placemark.getCal_latitude());
	}
	
	public void dump(String kmlFile) throws IOException{
		try {
			FileOutputStream fos =  new FileOutputStream(new File(kmlFile));
			kml.marshal(fos);
			fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
