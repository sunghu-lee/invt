package com.naviworks.invt.controller;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naviworks.invt.dao.EmployeeDTO;
import com.naviworks.invt.model.ResponseMessage;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class EAMController
{
	RestTemplate restTemplate;
	
	ResponseMessage message = null;
	private static final Logger logger = LoggerFactory.getLogger(EAMController.class);
	
	@GetMapping()
	public String mainPage()
	{
		return "main";
	}
	
	@GetMapping("/Approval")
	@ApiOperation(value="Approval", notes="Make Approval Information at Employee DB in Naviworks Co.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="cid", value="임직원 사번", dataType="string")
	})
	    
	public ResponseEntity<ResponseMessage> Search(@RequestParam(value="cid", required=false , defaultValue="")String cid) throws JsonProcessingException, IOException
	{
		message = restTemplate.getForObject("http://localhost:8080/Search?cid=" + cid, ResponseMessage.class);
		
//		String res = message.getMessage();
//		ObjectMapper mapper = new ObjectMapper();
//		//전달해 주는 JSON 값을 모두 다 받아서 처리할 이유가 없다면 내가 필요한 값만 DTO 클래스에 정의하여 바인드
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);		
//		
//	    List<EmployeeDTO> employees = mapper.reader()
//	      .forType(new TypeReference<List<EmployeeDTO>>() {})
//	      .readValue(res);
//	
//	    Assert.assertEquals(1, employees.size());
	    
	    // 값은 잘 나옴
	    // 1. 로그로 name, head, department 잘 파싱되었는지 따로 다 확인필요	    
	    // logger.debug(employees.get(0).getName(););
	    
	    // 경영혁신본부 - 1 / 연구개발본부 - 2
	    // 품질서비스혁신팀 - 4 (경영,솔루션,신사업,품질) / 제1연구소  - 1, 제2연구소  - 2
	    // 없음 / 미래기술연구팀 - 1, 차세대기술연구팀 - 1
	    // 없음 / 없음, RealBX 파트 - 1
	    // 일반 - 1, 파트장  - 2, 팀장 - 3, 본부장 - 4
		
		return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
					
	}
	//} // Search
} // class EAMController
