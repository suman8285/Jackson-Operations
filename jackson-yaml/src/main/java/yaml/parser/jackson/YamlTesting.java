package yaml.parser.jackson;
import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ParseContext;
public class YamlTesting {
    public static void main(String[] args) {
    	
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        
        try {
            //User user = mapper.readValue(new File("user.yaml"), User.class);
            //System.out.println(ReflectionToStringBuilder.toString(user,ToStringStyle.MULTI_LINE_STYLE));
            //String response ="{'transactionDetailExtn':{'correlationId':'1c750328-67ed-11e7-ab1b-bb8a9f4a485f'}}";
        	long startTime=System.currentTimeMillis();
        	String response =  mapper.readTree(new File("response.yaml")).toString();
        	System.out.println(response);
        	Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
        	
        	Filter liabilityFilter = filter(where("accountStatusType").is("OPEN").and("accountOwnershipType").ne("TERMINATED"));
        	
        	Object liabilities =JsonPath.read(document, "$.response[0].responsedata[0].creditresponse.creditliability[?]",liabilityFilter);
        	int length =JsonPath.read(liabilities,"$.length()");
        	
        	Configuration conf = Configuration.builder().options(Option.DEFAULT_PATH_LEAF_TO_NULL).build();
        	ParseContext jsonParser = JsonPath.using(conf);
        	DocumentContext liabilityDoc = jsonParser.parse(liabilities);
        	
        	for(int i=0;i<length;i++){
        		
        		System.out.println(" Test :"+liabilityDoc.read("$["+i+"]"));
        		System.out.println("      :"+liabilityDoc.read("$["+i+"]"+".creditor.name"));
        		System.out.println("      :"+liabilityDoc.read("$["+i+"]"+".unpaidBalanceAmount"));
        		System.out.println("      :"+liabilityDoc.read("$["+i+"]"+".monthlyPaymentAmount"));
        		System.out.println("      :"+liabilityDoc.read("$["+i+"]"+".creditLoanType"));
        	}
        	long endTime=System.currentTimeMillis();
        	System.out.println("Length :"+length +" Duration :"+ (endTime-startTime));
        	


            //System.out.println(mapper.readTree(new File("response.yaml")));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}