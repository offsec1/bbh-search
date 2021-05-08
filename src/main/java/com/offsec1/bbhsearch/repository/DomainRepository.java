package com.offsec1.bbhsearch.repository;

import com.offsec1.bbhsearch.model.Domain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends ElasticsearchRepository<Domain, String> {
}