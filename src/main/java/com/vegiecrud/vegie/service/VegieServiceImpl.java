package com.vegiecrud.vegie.service;

import com.google.gson.Gson;
import com.vegiecrud.vegie.dto.VegieDto;
import com.vegiecrud.vegie.exception.ResponseException;
import com.vegiecrud.vegie.model.RedisModel;
import com.vegiecrud.vegie.model.Vegie;
import com.vegiecrud.vegie.repository.RedisRepository;
import com.vegiecrud.vegie.repository.VegieRepository;
import com.vegiecrud.vegie.constant.ConstantRespCode;
import com.vegiecrud.vegie.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VegieServiceImpl implements VegieService {

    private final Gson gson = new Gson();
    private VegieRepository vegieRepository;

    @Autowired
    private RedisRepository redisRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.datasource.redis.timeout}")
    private Long redisTimeout;

    public VegieServiceImpl(VegieRepository vegieRepository) {
        this.vegieRepository = vegieRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<Vegie> createVegie(VegieDto vegieDto) throws ResponseException {
           try {
               Vegie vegie = saveVegieToDb(vegieDto);
               saveVegieToRedis(vegie);

               return ResponseEntity.ok(vegie);
           }catch (ResponseException e){
               throw new ResponseException(ConstantRespCode.ConstantRcUtils.RC_10, ConstantRespCode.ConstantMSGUtils.MSG_BAD_REQUEST_RC10, HttpStatus.BAD_REQUEST);
           }
    }


    public Vegie saveVegieToDb(VegieDto vegieDto) {
        validationRequest(vegieDto);
        String vegetableId = CommonUtils.generateVegetableId(vegieDto.getName());
        Timestamp date = Timestamp.valueOf(LocalDateTime.now());
        String timestamp = CommonUtils.generateTimestemp(date);
        Vegie vegieEntity = new Vegie();
        vegieEntity.setVegetableId(vegetableId);
        vegieEntity.setName(vegieDto.getName());
        vegieEntity.setStock(vegieDto.getStock());
        vegieEntity.setDescription(vegieDto.getDescription());
        vegieEntity.setPrice(vegieDto.getPrice());
        vegieEntity.setTimestamp(timestamp);

        vegieRepository.save(vegieEntity);
        return vegieEntity;
    }

    private void validationRequest(VegieDto vegieDto) throws ResponseException {
        if (vegieDto.getName().isEmpty() || vegieDto.getPrice().isEmpty() || vegieDto.getStock().isEmpty()) {
            throw new ResponseException(ConstantRespCode.ConstantRcUtils.RC_10, ConstantRespCode.ConstantMSGUtils.MSG_BAD_REQUEST_RC10, HttpStatus.BAD_REQUEST);
        }
    }

    public Vegie saveVegieToRedis(Vegie vegieEntity) {
        RedisModel save = new RedisModel();
        save.setId(vegieEntity.getVegetableId());
        save.setName(vegieEntity.getName());
        save.setDescription(vegieEntity.getDescription());
        save.setStock(vegieEntity.getStock());
        save.setPrice(vegieEntity.getPrice());
        save.setTimeToLive(redisTimeout);
        redisRepository.save(save);
        return vegieEntity;
    }

    @Transactional
    @Override
    public List<Vegie> getAllVegie() {
        return vegieRepository.findAll();
    }

    @Transactional
    @Override
    public boolean deleteVegie(String id) {
        Vegie vegie = vegieRepository.findById(id).get();
        vegieRepository.delete(vegie);
        return true;
    }

    @Transactional
    @Override
    public VegieDto updateVegie(String id, VegieDto vegieDto) throws ResponseException {
        Vegie vegie = vegieRepository.findById(id).get();
        vegie.setName(vegieDto.getName());
        vegie.setDescription(vegieDto.getDescription());
        vegie.setPrice(vegieDto.getPrice());
        vegie.setStock(vegieDto.getStock());
        vegieRepository.save(vegie);
        return vegieDto;
    }

    @Transactional
    public VegieDto getVegieById(String id) throws ResponseException {
        Vegie vegie = vegieRepository.findById(id).get();
        VegieDto vegieDto = new VegieDto();
        BeanUtils.copyProperties(vegie, vegieDto);
        if (vegie.getVegetableId()==null){
            throw new ResponseException(ConstantRespCode.ConstantRcUtils.RC_10, ConstantRespCode.ConstantMSGUtils.MSG_BAD_REQUEST_RC10, HttpStatus.BAD_REQUEST);
        }
        return vegieDto;
    }
}
