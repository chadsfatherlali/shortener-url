package com.api.shortenerUrl.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.Repository;

import com.api.shortenerUrl.entities.UrlEntity;

public interface UrlRepositoryTextSearch extends Repository<UrlEntity, String> {

	Page<UrlEntity> findAllBy(TextCriteria criteria, Pageable paging);

}
