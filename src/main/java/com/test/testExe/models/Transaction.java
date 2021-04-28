package com.test.testExe.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.testExe.DAO.AccountDao;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    public Transaction(RequestDto requestDto, Account account) {
        this.id = requestDto.getId();
        this.account = account;
        this.amount = requestDto.getAmount();
        this.supplier_id = requestDto.getSupplier_id();
        this.persistDateTime = requestDto.getDate();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    private Long supplier_id;

    @ManyToOne
    @JoinColumn(name="account_num", referencedColumnName="num")
    private Account account;

    private Double amount;

//    @Type(type = "org.hibernate.type.LocalDateTimeType")
//    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private String persistDateTime;

}
