package com.naviworks.invt.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.naviworks.invt.dao.EmployeeDAO;
import com.naviworks.invt.model.EmployeeInfo;
import com.naviworks.invt.model.ResponseMessage;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class EISController
{
	@Autowired
	private EmployeeDAO dao;
	
	@GetMapping()
	public String mainPage()
	{
		return "main";
	}
	
	@GetMapping("/Calc")
	@ApiOperation(value="Calc", notes="number calc")
	@ApiImplicitParams({
		@ApiImplicitParam(name="num1", value="첫번째 숫자",dataType="int"),
		@ApiImplicitParam(name="num2", value="두번째 숫자", dataType="int")
	})
	public ResponseEntity<ResponseMessage> Calc(
			@RequestParam(value="num1", required=true , defaultValue="0")int num1,
			@RequestParam(value="num2", required=true , defaultValue="0")int num2)
	{
		String strMsg = "Result : " + String.valueOf(num1+num2);
		ResponseMessage message = new ResponseMessage("Success", strMsg, "200", "계산 결과");
		// Send Client
		return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
	}
	
	@GetMapping("/Search")
	@ApiOperation(value="Search", notes="Search at Employee in Naviworks Co.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="name", value="임직원 이름",dataType="string"),
		@ApiImplicitParam(name="cid", value="임직원 사번", dataType="string")
	})
	public ResponseEntity<ResponseMessage> Search(
			@RequestParam(value="name", required=false , defaultValue="")String name,
			@RequestParam(value="cid", required=false , defaultValue="")String cid)
	{
		EmployeeInfo info = null;
		ResponseMessage message = null;
		
		// RDB 조회
		if(name.length() > 1)
		{
			info = dao.getInfoByName(name);	
		}
		else if(cid.length() > 1)
		{
			info = dao.getInfoByCID(cid);
		}
		else
		{
			message = new ResponseMessage("Failed", "등록되지 않은 임직원입니다.", "505", "등록되지 않은 임직원입니다.");
		}
		
		if(null != info)
		{			
			// MongoDB 접속
			MongoClientURI uri = new MongoClientURI(
				    "mongodb+srv://server:Navi123@cluster0-ye3i7.azure.mongodb.net/naviworks?retryWrites=true&w=majority");
				
			MongoClient mongoClient = new MongoClient(uri);
			MongoDatabase database = mongoClient.getDatabase("naviworks");
				
			MongoCollection<Document> collection = database.getCollection("employee");
			// MongoDB 검색
			FindIterable<Document> findDoc = collection.find(Filters.eq("name",info.getEmployeeName()));
			
			
			// Make JSON
			MongoCursor<Document> cursor = findDoc.iterator();
			List<String> list = new ArrayList<String>(); 

			while(cursor.hasNext())
			    list.add(cursor.next().toJson());
				
			message = new ResponseMessage("Success",list.toString(), "", "");
			
			mongoClient.close();
		}
		else
		{
			message = new ResponseMessage("Failed", "등록되지 않은 임직원입니다.", "505", "등록되지 않은 임직원입니다.");
		}
		
		// Send Client
		return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
		
	}
}
