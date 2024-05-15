package com.vegiecrud.vegie.controller;

import com.vegiecrud.vegie.dto.VegieDto;
import com.vegiecrud.vegie.model.Vegie;
import com.vegiecrud.vegie.service.VegieService;
import com.vegiecrud.vegie.service.VegieServiceAsyncImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
public class VegieController {

    @Autowired
    private VegieService vegieService;

    @Autowired
    private VegieServiceAsyncImpl vegieServiceAsync;

    @PostMapping("/vegie")
    public ResponseEntity<Vegie> createVegie(@RequestBody VegieDto vegie)  {
        Vegie response= vegieService.createVegie(vegie).getBody();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/vegie")
    public List<Vegie> getAllVegie() {
        return vegieService.getAllVegie();
    }
    @DeleteMapping("vegie/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteVegie(@PathVariable String id) {
        boolean deleted = false;
        deleted = vegieService.deleteVegie(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/vegie/{id}")
    public ResponseEntity<VegieDto> updateVegie(@PathVariable String id, @RequestBody VegieDto vegieDto) {
        vegieDto = vegieService.updateVegie(id, vegieDto);
        return ResponseEntity.ok(vegieDto);
    }

    @GetMapping("/vegie/{id}")
    public ResponseEntity<VegieDto> getVegieById(@PathVariable String id) {
        VegieDto vegieDto = null;
        vegieDto = vegieService.getVegieById(id);
        return ResponseEntity.ok(vegieDto);
    }

    @PostMapping("/vegie/async")
    public ResponseEntity<Vegie> processFuture(@RequestBody VegieDto vegieDto) throws InterruptedException, ExecutionException {
        System.out.println("Processing Started");
        long startTime = System.currentTimeMillis();
        CompletableFuture<Vegie> firstAsyncResult = vegieServiceAsync.saveOrderDetailsFuture(vegieDto);
        CompletableFuture<Vegie> secondAsyncResult = firstAsyncResult.thenComposeAsync(vegie -> {
            try {
                return vegieServiceAsync.saveOrderDetilsRedisFuture(vegie);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture.allOf(firstAsyncResult, secondAsyncResult).join();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time taken: " + elapsedTime + " milliseconds");
        return ResponseEntity.ok(firstAsyncResult.get());
    }
}
