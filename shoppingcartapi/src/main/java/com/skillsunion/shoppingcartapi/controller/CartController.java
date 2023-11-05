package com.skillsunion.shoppingcartapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillsunion.shoppingcartapi.entity.Cart;
import com.skillsunion.shoppingcartapi.repository.CartRepository;



/*
    Assignment:
    - Apply @RestController to the class.
    - Apply @GetMapping and @PostMapping to themethods.
    - Apply @PathVariable to the method parameter of get(int).

    Test:
    - Use a client HTTP Tool like YARC to consume the API you have just created.
    E.g. https://chrome.google.com/webstore/detail/yet-another-rest-client/ehafadccdcdedbhcbddihehiodgcddpl?hl=en
*/



@RestController
public class CartController {
    
    private Cart cart;
    @Autowired CartRepository repo; // Added

    @GetMapping("/carts/{id}")
    public ResponseEntity<Optional<Cart>> get(@PathVariable int id){
        Optional<Cart> result = (Optional<Cart>) repo.findById(id);
        if(result.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(result);
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> create(@RequestBody RequestBodyTempData data){
        Cart newRecord = new Cart();
        newRecord.setId(data.getId());
        newRecord.setQuantity(data.getQuantity());
        
        try {
        	Cart created = repo.save(newRecord);
            return ResponseEntity.status(HttpStatus.OK).body(created);
        }catch(Exception e) {
        	System.out.println(e);
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    class RequestBodyTempData {
        Integer id;
        Integer quantity;
    
        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    
        public int getQuantity() {
            return this.quantity;
        }
    
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
}
}