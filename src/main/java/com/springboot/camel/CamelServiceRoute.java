package com.springboot.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelServiceRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		System.out.println("Inside configure method");
		
		from("timer:foo?fixedRate=true&period=5000&delay=3000")
		//.to("restlet:http://localhost:8081/employees?restletMethod=get")
		.to("restlet:http://empdetailmaster-prigyashukla-accenture-fis.cloudapps.na.openshift.opentlc.com/employees?restletMethod=get") //fis lab
		
			.process(new Processor(){
				@Override
		         public void process(Exchange exchange) throws Exception {
		             //getting the request object
					 String req = (String) exchange.getIn().getBody();
		             System.out.println("Message Body from prigya's service :" + req);
		             //req = req.substring(1,req.length()-1);
		             System.out.println("req =" + req);
		             exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/json");
			            // exchange.getOut().setBody("{\"id\":\"01\",\"name\":\"Joe\",\"age\":32}");
			         exchange.getOut().setBody(req);
				}
		       })
		.to("restlet:http://emptimerecordservice-prigyashukla-accenture-fis.cloudapps.na.openshift.opentlc.com?restletMethod=post"); //fis lab
		
		
		
	}
}
