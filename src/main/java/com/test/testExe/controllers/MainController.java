package com.test.testExe.controllers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.testExe.DAO.AccountDao;
import com.test.testExe.DAO.TransactionDao;
import com.test.testExe.models.RequestDto;
import com.test.testExe.models.ResponseDto;
import com.test.testExe.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

@RestController
@RequestMapping("/")
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final AccountDao accountService;
    private final TransactionDao transactionService;

    @Autowired
    public MainController(AccountDao accountService, TransactionDao transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> payAndCheck(@RequestBody RequestDto request) throws Exception {
        if (request.getCommand().equals("check")) {
            if (accountService.findByNum(request.getAccount()).isPresent()) {
                ResponseDto response = new ResponseDto(request.getId(), request.getDate(),
                        111222L, 0, "ACCOUNT_EXISTS");
                return new ResponseEntity<String>(getXml(response), HttpStatus.OK);
            } else {
                ResponseDto response = new ResponseDto(request.getId(), request.getDate(),
                        111222L, 1, "ACCOUNT_NOT_EXISTS");
                return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
            }
        } else if(request.getCommand().equals("pay")) {
            if(accountService.findByNum(request.getAccount()).isPresent()) {
                Transaction transaction = new Transaction(request, accountService.findByNum(request.getAccount()).get());
                transactionService.save(transaction);
                ResponseDto response = new ResponseDto(request.getId(), request.getDate(),
                        111222L, 1, "PAYMENT CONFIRMED");
                return new ResponseEntity<String>(getXml(response), HttpStatus.OK);
            } else {
                ExceptionDto exceptionDto = new ExceptionDto("This account is not exist");
                return new ResponseEntity<ExceptionDto>(exceptionDto, HttpStatus.BAD_REQUEST);
            }
        }
        ExceptionDto exceptionDto = new ExceptionDto("It is either not CHECK or PAY command, " +
                "please consider to use only this commands");
        return new ResponseEntity<ExceptionDto>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
    public String getXml(ResponseDto response) throws Exception{
        StringWriter stringWriter = new StringWriter();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(stringWriter);

        XmlMapper mapper = new XmlMapper();

        sw.writeStartDocument();

        mapper.writeValue(sw, response);
        sw.writeEndDocument();
        sw.flush();
        return stringWriter.getBuffer().toString();

    }
}
