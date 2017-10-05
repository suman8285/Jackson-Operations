package com.tavant.finstudent.integration.adapter.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.tavant.finstudent.integration.model.CreditLiability;

public class TestYaml {
	
public static void main(String args[]) {
	TestYaml test = new TestYaml();
	ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
	//test.convertYamlToJson("");
	try {
	YamlReader reader = new YamlReader(new FileReader("D:/CODE/test.yml"));
	while (true) {
		Map contact = (Map) reader.read();
		if (contact == null) break;
		//System.out.println(contact.get("response"));
		((List) contact.get("response")).forEach((temp) -> {
			Map contact3 = (Map)temp;
			((List) contact3.get("responsedata")).forEach((temp1) -> {
				Map contact4 = (Map)temp1;
				Map contact5 = (Map)contact4.get("creditresponse");
				((List) contact5.get("creditliability")).forEach((temp2) -> {
				System.out.println("with Streams :-"+temp2);
				});
			});
		});

		System.out.println(contact.get("submittingparty"));
		System.out.println(contact.get("transactionDetailExtn"));
		System.out.println(contact.get("RGRept000001"));
		System.out.println(contact.get("respondingparty"));
		System.out.println(contact.get("respondtoparty"));
		
		
		
		
	}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (YamlException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public String convertYamlToJson(String yaml) {
	
	
	try {
	    ObjectMapper mapper = new ObjectMapper();
	    JsonFactory f = new JsonFactory();
	    List<CreditLiability> lstUser = null;
	    JsonParser jp = f.createJsonParser(new File("D:/CODE/test.yml"));
	    TypeReference<List<CreditLiability>> tRef = new TypeReference<List<CreditLiability>>() {};
	    lstUser = mapper.readValue(jp, tRef);
	    for (CreditLiability user : lstUser) {
	        System.out.println(user.toString());
	    }

	} catch (JsonGenerationException e) {
	    e.printStackTrace();
	} catch (JsonMappingException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	
	String response = null;
	try {
    ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
    Object obj = yamlReader.readValue(yaml, Object.class);

    ObjectMapper jsonWriter = new ObjectMapper();
     response = jsonWriter.writeValueAsString(obj);
	}catch(Exception e) {
		
	}
    return response;
}

}
