package com.offsec1.bbhsearch.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "domains")
@Getter @Setter
public class Domain {

    @Id
    private String id;

    private String host;
    private String source;
}