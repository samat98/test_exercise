package com.test.testExe.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "response")
@JsonPropertyOrder({"id", "dts", "p_id", "status", "message"})
public class ResponseDto implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    private Long id;

    @JacksonXmlProperty
    private String dts;

    @JacksonXmlProperty(localName = "p_id")
    private Long pId;

    @JacksonXmlProperty
    private int status;

    @JacksonXmlProperty
    private String message;
}
