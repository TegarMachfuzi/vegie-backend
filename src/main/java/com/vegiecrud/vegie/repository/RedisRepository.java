package com.vegiecrud.vegie.repository;

import com.vegiecrud.vegie.model.RedisModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<RedisModel, String> {

}
